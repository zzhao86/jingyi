<template>
  <div class="app-main">
    <div class="main-header">
      <div class="title">用户详情</div>
      <div class="buttons">
        <el-button type="default" size="small" @click="$router.back()">返回</el-button>
        <el-button type="success" size="small" @click="onPreviewClick">预览</el-button>
        <el-button type="primary" size="small" @click="onSaveClick">保存并发送</el-button>
      </div>
    </div>
    <el-form ref="form" :model="detailData" label-width="100px">
      <el-form-item label="标题">
        <el-input v-model="detailData.title"></el-input>
      </el-form-item>
      <el-form-item label="作者">
        <el-input v-model="detailData.author"></el-input>
      </el-form-item>
      <el-form-item label="封面图片">
        <el-upload
          class="cover-uploader"
          with-credentials
          :action="uploadImageUrl"
          :show-file-list="false"
          :on-success="onCoverUploadSuccess"
          :before-upload="beforeCoverUpload"
          accept="image/jpeg, image/png"
        >
          <div class="cover" v-if="detailData.coverUrl">
            <div class="cover-mask"><i class="el-icon-delete" @click.stop="onCoverDeleteClick"></i></div>
            <img :src="$global.baseUrl + detailData.coverUrl" />
          </div>
          <i v-else class="el-icon-plus cover-uploader-icon"></i>
        </el-upload>
      </el-form-item>
      <el-form-item label="发送范围">
        <contact-tag :data="detailData.scopeJson" @choosed-scope="onChoosedScope"></contact-tag>
        <el-button type="primary" size="small" v-show="viewContactButtonShow" @click="onReadListShowClick">查看接收详情</el-button>
      </el-form-item>
      <el-form-item label="正文">
        <ueditor :content="detailData.content" @content-change="onUEContentChange"></ueditor>
      </el-form-item>
      <el-form-item label="附件">
        <el-upload
          :action="uploadAttachUrl"
          multiple
          with-credentials
          :limit="5"
          :file-list="attachs"
          :on-success="uploadAttachSuccess"
          :before-remove="uploadAttachBeforeRemove"
          :on-exceed="uploadAttachExceed"
          accept=".doc,.docx,.pdf,.xls,.xlsx,.ppt,.pptx,.zip,.rar"
        >
          <el-button size="small" type="primary">点击上传</el-button>
          <div slot="tip" class="el-upload__tip">最多可以上传5个附件，支持20M以内的.doc，.docx，.pdf，.xls，.xlsx，.ppt，.pptx，.zip，.rar类型文件</div>
        </el-upload>
      </el-form-item>
    </el-form>
    <!-- 预览Modal -->
    <el-dialog title="预览" append-to-body :visible.sync="dialogPreviewVisible" custom-class="preview-dialog" width="375px">
      <div class="prview-body">
        <iframe id="previewIframe" frameborder="0" width="375" height="600" @load="onPreviewIframeLoaded" src="../../../static/html/noticePreview.html"></iframe>
      </div>
    </el-dialog>

    <!-- 已读未读人员列表Modal -->
    <read-list :visible="readListVisible" type="notice" :readed="detailData.readCount" :total="detailData.totalCount" :relId="detailData.id" @close="onReadListCloseClick"></read-list>
  </div>
</template>
<script>
  import ContactTag from '../utils/components/ContactTag';
  import Ueditor from '../utils/components/Ueditor';
  import ReadList from '../utils/components/ReadList';
  export default {
    name: 'NoticeDetail',
    components: {
      ContactTag,
      Ueditor,
      ReadList
    },
    created() {
      const mode = this.$route.params.mode;
      const query = this.$route.query;
      if (query.id) {
        this.loadDetailData(query.id);
      }
      if (mode == 'edit' || mode == 'view') {
        this.viewContactButtonShow = true;
      }
    },
    data() {
      return {
        uploadImageUrl: this.$global.baseUrl + 'back/notice/upload_cover',
        uploadAttachUrl: this.$global.baseUrl + 'back/notice/upload_attach',
        detailData: {},
        contactVisible: false,
        viewContactButtonShow: false,
        dialogPreviewVisible: false,
        readListVisible: false
      };
    },
    computed: {
      attachs: {
        get: function() {
          let array = [];
          if (this.detailData.attachList && this.detailData.attachList.length > 0) {
            for (let i = 0; i < this.detailData.attachList.length; i++) {
              let item = this.detailData.attachList[i];
              array.push({
                name: item.attachName,
                url: item.attachUrl
              });
            }
          }
          return array;
        }
      }
    },
    methods: {
      loadDetailData: function(id) {
        this.$get('back/notice/detail', {
          params: {
            id: id
          }
        }).then(res => {
          if (res.isSuccess && res.data != null) {
            this.detailData = res.data;
          }
        });
      },
      // 封面上传成功
      onCoverUploadSuccess: function(res, file) {
        if (res.isSuccess) {
          this.detailData.coverUrl = res.data[0].url;
        }
        return false;
      },
      // 封面上传验证
      beforeCoverUpload: function(file) {
        if (!(file.type == 'image/jpeg' || file.type == 'image/png')) {
          this.$message.error('封面图片只能是 JPG或PNG 格式!');
          return false;
        }
        if (file.size / 1024 / 1024 > 1) {
          this.$message.error('封面图片大小不得超过1M');
          return false;
        }
      },
      // 封面删除
      onCoverDeleteClick: function() {
        this.detailData.coverUrl = '';
      },
      // 发送范围选择
      onChoosedScope: function(scope) {
        this.detailData.scopeJson = scope;
      },
      // 富文本编辑框内容改变事件
      onUEContentChange: function(content) {
        this.detailData.content = content;
      },
      // 附件上传成功
      uploadAttachSuccess(res, file, fileList) {
        if (res.isSuccess) {
          for (let i = 0; i < res.data.length; i++) {
            let data = res.data[i];
            this.detailData.attachList.push({
              attachName: data.name,
              attachUrl: data.url
            });
          }
        }
      },
      // 附件超出限制
      uploadAttachExceed(files, fileList) {
        this.$message.warning('最多上传5个附件');
      },
      // 附件删除确认
      uploadAttachBeforeRemove(file, fileList) {
        const self = this;
        this.$confirm(`确定移除附件 ${file.name}？`, function() {
          let index = fileList.indexOf(file);
          fileList.splice(index, 1);
          self.detailData.attachList.splice(index, 1);
        });
        return false;
      },
      // 预览dialog显示
      onPreviewClick: function() {
        this.dialogPreviewVisible = true;
      },
      // 在iframe加载完成后，向iframe中传入公告内容
      onPreviewIframeLoaded: function() {
        var iframe = document.getElementById('previewIframe');
        iframe.contentWindow.postMessage(this.detailData, '*');
      },
      // 显示已读未读人员列表
      onReadListShowClick: function() {
        this.readListVisible = true;
      },
      // 关闭已读未读人员列表
      onReadListCloseClick: function() {
        this.readListVisible = false;
      },
      // 保存并发送
      onSaveClick: function() {
        this.$post('back/notice/save', this.detailData).then(res => {
          if (res.isSuccess) {
            this.$success('保存成功');
          } else {
            this.$error(res.message);
          }
        });
      }
    }
  };
</script>
<style>
  .cover-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    width: 120px;
    height: 120px;
    line-height: 120px;
    background-color: #fbfdff;
  }
  .cover-uploader .el-upload:hover {
    border-color: #409eff;
  }
  .cover-uploader .cover-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 120px;
    height: 120px;
    line-height: 120px;
    text-align: center;
  }
  .cover-uploader .cover {
    width: 120px;
    height: 120px;
  }
  .cover-uploader .cover .cover-mask {
    position: absolute;
    width: 120px;
    height: 120px;
    display: none;
    background-color: rgba(0, 0, 0, 0.5);
    font-size: 28px;
    color: #ffffff;
  }
  .cover-uploader .cover:hover .cover-mask {
    display: block;
  }
  .cover-uploader .cover img {
    max-width: 120px;
    max-height: 120px;
  }
  .preview-dialog .el-dialog__body {
    padding: 0;
  }
</style>

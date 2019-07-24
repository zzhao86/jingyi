<template>
  <div class="app-main">
    <div class="main-header">
      <div class="title">用户详情</div>
      <div class="buttons">
        <el-button type="default" size="small" @click="$router.back()">返回</el-button>
        <el-button type="primary" size="small" @click="onSaveClick">保存</el-button>
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
          class="avatar-uploader"
          :action="uploadImageUrl"
          :show-file-list="false"
          :on-success="onCoverUploadSuccess"
          :before-upload="beforeCoverUpload"
          accept="image/jpeg, image/png"
        >
          <img v-if="detailData.coverUrl" :src="$global.baseUrl + detailData.coverUrl" class="avatar" />
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>
      <el-form-item label="发送范围">
        <contact-tag :data="detailData.scopeJson" @choosed-scope="onChoosedScope"></contact-tag>
      </el-form-item>
      <el-form-item label="正文">
        <ueditor :content="detailData.content" @content-change="onUEContentChange"></ueditor>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  import ContactTag from '../utils/components/ContactTag';
  import Ueditor from '../utils/components/Ueditor';
  export default {
    name: 'NoticeDetail',
    components: {
      ContactTag,
      Ueditor
    },
    created() {
      const mode = this.$route.params.mode;
      const query = this.$route.query;
      if (query.id) {
        this.loadDetailData(query.id);
      }
    },
    data() {
      return {
        uploadImageUrl: this.$global.baseUrl + 'upload/image',
        detailData: {},
        contactVisible: false
      };
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
      onCoverUploadSuccess: function(res, file) {
        console.log(res);
        console.log(file);
      },
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
      onChoosedScope: function(scope) {
        this.detailData.scopeJson = scope;
      },
      onUEContentChange: function(content) {
        this.detailData.content = content;
      },
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
<style></style>

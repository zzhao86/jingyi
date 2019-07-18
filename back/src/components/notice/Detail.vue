<template>
  <div class="app-main">
    <div class="main-header">
      <div class="title">用户详情</div>
      <div class="buttons">
        <el-button type="default" size="small" @click="$router.back()">返回</el-button>
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
          action="https://jsonplaceholder.typicode.com/posts/"
          :show-file-list="false"
          :on-success="onCoverUploadSuccess"
          :before-upload="beforeCoverUpload"
          accept="image/jpeg, image/png"
        >
          <img v-if="detailData.coverUrl" :src="$global.baseUrl + detailData.coverUrl" class="avatar" />
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="detailData.tel"></el-input>
      </el-form-item>
      <el-form-item label="职位">
        <el-input v-model="detailData.position"></el-input>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  import dd from 'dingtalk-jsapi';
  export default {
    name: 'NoticeDetail',
    created() {
      dd.biz.contact.choose({
        multiple: true, //是否多选：true多选 false单选； 默认true
        corpId: 'ding88b58049aa34141835c2f4657eb6378f', //企业id
        max: 10, //人数限制，当multiple为true才生效，可选范围1-1500
        onSuccess: function(data) {
          console.log(data);
        },
        onFail: function(err) {}
      });
      const mode = this.$route.params.mode;
      const query = this.$route.query;

      if (query.id) {
        this.loadDetailData(query.id);
      }
    },
    data() {
      return {
        detailData: {}
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
      onCoverUploadSuccess: function(res, file) {},
      beforeCoverUpload: function(file) {
        console.log(file);
        if (!(file.type == 'image/jpeg' || file.type == 'image/png')) {
          this.$message.error('封面图片只能是 JPG或PNG 格式!');
          return false;
        }
        if (file.size / 1024 / 1024 > 1) {
          this.$message.error('封面图片大小不得超过1M');
          return false;
        }
      }
    }
  };
</script>
<style></style>

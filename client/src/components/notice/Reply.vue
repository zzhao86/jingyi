<template>
  <div class="container">
    <mt-field label="内容" placeholder="请输入回复内容，200字以内" type="textarea" rows="4" v-model="detail.content"></mt-field>
    <mt-field label="附件" placeholder="选择要上传的附件" readonly @click.native="onUploadClick" v-model="detail.file.name">
      <label ref="fileUpload">
        <input
          type="file"
          name="attach"
          accept="application/pdf,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.openxmlformats-officedocument.presentationml.presentation,application/vnd.ms-powerpoint,application/zip,application/x-rar-compressed"
          @change="onFileUploadChange"
        />
      </label>
    </mt-field>
    <div class="form-button">
      <mt-button type="primary" size="large" @click.native="onReplyClick">回复</mt-button>
    </div>
  </div>
</template>
<script>
  export default {
    name: 'NoticeReply',
    data() {
      return {
        detail: {
          content: '',
          file: {}
        }
      };
    },
    methods: {
      onUploadClick() {
        this.$refs['fileUpload'].click();
      },
      onFileUploadChange(e) {
        this.detail.file = e.target.files[0];
        if (this.file) {
          this.detail.fileName = this.detail.file.name;
        }
      },
      onReplyClick() {
        var forms = new FormData();
        forms.append(this.detail.file.name, this.detail.file);
        this.$post('client/notice/reply', forms, {
          headers: { 'Content-Type': 'multipart/form-data' }
        }).then(res => {
          console.log(res);
        });
      }
    }
  };
</script>
<style scoped>
  .container {
    padding: 0px 10px;
  }

  .container input[type='file'] {
    display: none;
  }

  .form-button {
    margin-top: 20px;
  }
</style>

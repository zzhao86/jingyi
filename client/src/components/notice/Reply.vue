<template>
  <div class="container">
    <mt-field label="内容" placeholder="请输入回复内容，200字以内" type="textarea" rows="4" v-model="detail.content" attr="{maxlength: 200}"></mt-field>
    <mt-field label="附件" placeholder="选择要上传的附件" readonly @click.native="onUploadClick" v-model="detail.file.name">
      <label ref="fileUpload">
        <!-- <input
          type="file"
          name="attach"
          accept="application/pdf, application/msword, application/vnd.openxmlformats-officedocument.wordprocessingml.document, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-powerpoint, application/vnd.openxmlformats-officedocument.presentationml.presentation, application/zip, application/x-rar-compressed"
          @change="onFileUploadChange"
        /> -->
        <input type="file" name="attach" accept=".pdf, .doc, .docx, .xls, .xlsx, .ppt, .pptx, .zip, .rar" @change="onFileUploadChange" />
      </label>
    </mt-field>
    <div class="form-button">
      <mt-button type="primary" size="large" @click.native="onReplyClick">回复</mt-button>
    </div>
  </div>
</template>
<script>
  import { MessageBox } from 'mint-ui';
  export default {
    name: 'NoticeReply',
    created() {
      const query = this.$route.query;
      if (query.id) {
        this.id = query.id;
      }
    },
    data() {
      return {
        id: '',
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
        forms.append('noticeId', this.id);
        forms.append('userId', this.$global.user.id);
        forms.append('content', this.detail.content);
        forms.append(this.detail.file.name, this.detail.file);
        this.$post('client/notice/reply', forms, {
          headers: { 'Content-Type': 'multipart/form-data' }
        }).then(res => {
          MessageBox.alert('回复成功').then(action => {
            this.$router.back();
          });
        });
      }
    }
  };
</script>
<style scoped>
  .container input[type='file'] {
    display: none;
  }

  .form-button {
    margin-top: 20px;
  }
</style>

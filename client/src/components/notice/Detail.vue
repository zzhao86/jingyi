<template>
  <div>
    <div class="container">
      <div class="title">{{ detail.title }}</div>
      <div class="author">
        <span class="name">{{ detail.author }}</span>
        <span class="time">{{ detail.publishTime }}</span>
      </div>
      <div class="content" v-html="detail.content"></div>
      <div class="attach-container">
        <div class="attach-header">附件：</div>
        <ul class="attach-wrapper">
          <li v-for="(item, index) in detail.attachList" :key="index">
            <div class="attach-name" :href="$global.baseUrl + item.attachUrl" @click="download(item)">
              <i class="fa" v-bind:class="convert(item.attachName)"></i>
              <span>{{ item.attachName }}</span>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>
<script>
  export default {
    name: 'NoticeDetail',
    created() {
      const query = this.$route.query;
      if (query.id) {
        this.id = query.id;
      }
      this.loadDetailData();
    },
    data() {
      return {
        id: '',
        detail: {}
      };
    },
    methods: {
      loadDetailData() {
        this.$get('/client/notice/detail', {
          params: {
            id: this.id
          }
        }).then(res => {
          if (res.isSuccess) {
            this.detail = res.data;
          }
        });
      },
      download(item) {
        var url = this.$global.baseUrl + item.attachUrl;
        alert(JSON.stringify(this.$dd.biz));
        this.$dd.biz.util.downloadFile({
          url: url, //要下载的文件的url
          name: item.attachName, //定义下载文件名字
          onProgress: function(msg) {
            alert(JSON.stringify(msg));
          },
          onSuccess: function(result) {
            alert('下载成功');
            alert(JSON.stringify(result));
          },
          onFail: function() {
            alert('下载出错');
          }
        });
      },
      convert(name) {
        var icon = 'fa-file-o';
        if (!name) return icon;
        var ext = name.substring(name.lastIndexOf('.') + 1);
        switch (ext) {
          case 'png':
          case 'jpg':
          case 'jpeg':
          case 'gif':
          case 'bmp':
            icon = 'fa-file-picture-o default';
            break;
          case 'pdf':
            icon = 'fa-file-pdf-o orange';
            break;
          case 'doc':
          case 'docx':
            icon = 'fa-file-word-o blue';
            break;
          case 'xls':
          case 'xlsx':
            icon = 'fa-file-excel-o green';
            break;
          case 'ppt':
          case 'pptx':
            icon = 'fa-file-powerpoint-o orange';
            break;
          case 'txt':
            icon = 'fa-file-text-o default';
            break;
          default:
            break;
        }
        return icon;
      }
    }
  };
</script>
<style scoped>
  .container {
    padding: 0 10px;
    height: 100vh;
    overflow-y: auto;
  }
  .title {
    color: #333;
    font-size: 18px;
    text-align: left;
    font-weight: 400;
    margin: 10px 0;
  }

  .author {
    font-size: 13px;
    color: #858e99;
    margin: 10px 0;
    text-align: left;
    overflow: hidden;
  }

  .author .name {
    padding-right: 15px;
    float: left;
  }

  .author .time {
    float: left;
  }

  .content {
    clear: left;
    margin-top: 20px;
    overflow: hidden;
  }
  .attach-container {
    margin-top: 30px;
    border-top: solid 1px #ccc;
    padding-top: 10px;
  }
  .attach-wrapper {
    margin-bottom: 20px;
    padding-left: 20px;
  }
  .attach-wrapper li {
    font-size: 14px;
    list-style: none;
    padding: 0;
    margin-bottom: 10px;
  }
  .attach-wrapper .attach-name {
    color: #333;
    text-decoration: none;
    height: 30px;
  }
  .attach-wrapper .attach-name .fa {
    font-size: 30px;
  }
  .attach-wrapper .attach-name .fa.green {
    color: #39825a;
  }
  .attach-wrapper .attach-name .fa.orange {
    color: #dc6141;
  }
  .attach-wrapper .attach-name .fa.blue {
    color: #4269a5;
  }
  .attach-wrapper .attach-name .fa.default {
    color: #65676b;
  }
</style>

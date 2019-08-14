<template>
  <div class="container">
    <div class="title">
      <span class="name">{{ detail.title }}</span>
      <div class="reply">
        <mt-button type="primary" :plain="true" size="small" @click.native="onReplyClick">回复</mt-button>
      </div>
    </div>
    <div class="author">
      <span class="name">{{ detail.author }}</span>
      <span class="time">{{ detail.publishTime }}</span>
    </div>
    <div class="count" @click="onShowReadListClick">已读{{ detail.readCount }}，未读{{ detail.totalCount - detail.readCount }}</div>
    <div class="content" v-html="detail.content"></div>
    <div class="attach-container" v-show="detail.attachList && detail.attachList.length > 0">
      <div class="attach-header">附件：</div>
      <ul class="attach-wrapper">
        <li v-for="(item, index) in detail.attachList" :key="index">
          <a class="attach-info" :href="$global.baseUrl + item.attachUrl" :title="item.attachName">
            <i class="icon fa" v-bind:class="convert(item.attachType)"></i>
            <div class="attach">
              <div class="name">{{ item.attachName }}</div>
              <div class="size">{{ item.size }}</div>
            </div>
          </a>
        </li>
      </ul>
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
            id: this.id,
            userid: this.$global.user.id
          }
        }).then(res => {
          if (res.isSuccess) {
            this.detail = res.data;
          }
        });
      },
      // 查看已读未读列表
      onShowReadListClick() {
        this.$router.push(`/notice/readlist?id=${this.id}`);
      },
      // 回复
      onReplyClick() {
        this.$router.push(`/notice/reply?id=${this.id}`);
      },
      // 附件图标转换
      convert(type) {
        var icon = 'fa-file-o';
        if (!type) return icon;
        switch (type) {
          case '.png':
          case '.jpg':
          case '.jpeg':
          case '.gif':
          case '.bmp':
            icon = 'fa-file-picture-o default';
            break;
          case '.pdf':
            icon = 'fa-file-pdf-o orange';
            break;
          case '.doc':
          case '.docx':
            icon = 'fa-file-word-o blue';
            break;
          case '.xls':
          case '.xlsx':
            icon = 'fa-file-excel-o green';
            break;
          case '.ppt':
          case '.pptx':
            icon = 'fa-file-powerpoint-o orange';
            break;
          case '.zip':
          case '.rar':
            icon = 'fa-file-zip-o brown';
            break;
          case 'txt':
            icon = 'fa-file-text-o default';
            break;
          default:
            icon = 'fa-file-o';
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
    overflow-x: hidden;
    overflow-y: scroll;
    -webkit-overflow-scrolling: touch;
  }
  .title {
    color: #333;
    margin: 10px 0;
    overflow: hidden;
    display: flex;
  }
  .title .name {
    display: inline-block;
    font-size: 18px;
    text-align: left;
    font-weight: 400;
    flex: 1;
  }
  .title .reply {
    width: 80px;
    text-align: right;
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
    max-width: calc(100% - 140px);
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .author .time {
    float: left;
    width: 120px;
    text-align: right;
  }

  .count {
    padding: 10px 0;
    font-size: 12px;
    color: #3296fa;
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
  .attach-wrapper .attach-info {
    display: flex;
    text-decoration: none;
  }
  .attach-wrapper .attach {
    flex: 1;
    margin-left: 10px;
    font-family: 'Microsoft Yahei';
  }
  .attach-wrapper .attach .name {
    color: #333;
    margin-top: -3px;
  }
  .attach-wrapper .attach .size {
    color: #999;
    font-size: 11px;
  }
  .attach-wrapper .attach-info .icon {
    font-size: 30px;
    width: 32px;
    height: 32px;
  }
  .attach-wrapper .attach-info .icon.green {
    color: #39825a;
  }
  .attach-wrapper .attach-info .icon.orange {
    color: #dc6141;
  }
  .attach-wrapper .attach-info .icon.blue {
    color: #4269a5;
  }
  .attach-wrapper .attach-info .icon.brown {
    color: #e56e4d;
  }
  .attach-wrapper .attach-info .icon.default {
    color: #65676b;
  }
</style>

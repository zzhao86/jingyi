<template>
  <div>
    <div class="title">{{ detail.title }}</div>
    <div class="author">
      <span class="name">{{ detail.author }}</span>
      <span class="time">{{ detail.publishTime }}</span>
    </div>
    <div class="content" v-html="detail.content"></div>
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
      }
    }
  };
</script>
<style scoped>
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
</style>

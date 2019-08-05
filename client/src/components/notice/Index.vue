<template>
  <div>
    <div class="notice-wrapper" v-for="(item, index) in listData" :key="index" @click="onViewClick(item.id)">
      <div class="notice-info">
        <div class="notice-title">{{ item.title }}</div>
        <div class="notice-ext">
          <div class="notice-author">{{ item.author }}</div>
          <div class="notice-date">{{ item.publishTime }}</div>
        </div>
      </div>
      <div class="notice-cover">
        <!-- <div v-bind:style="'background-image: url('+$global.baseUrl + item.coverUrl+')'" class="notice-cover-image"></div> -->
        <img :src="$global.baseUrl + item.coverUrl" class="notice-cover-image" />
      </div>
    </div>
  </div>
</template>
<script>
  export default {
    name: 'NoticeIndex',
    created() {
      this.loadListData();
    },
    data() {
      return {
        params: {
          index: 1,
          size: 20,
          query: {
            userId: 'd8bdfb8204fc4499bf379026f6865999'//this.$global.user.id
          }
        },
        total: 0,
        listData: []
      };
    },
    methods: {
      loadListData() {
        this.$get('client/notice/list', {
          params: this.params
        }).then(res => {
          if (res.isSuccess) {
            for (let i = 0; i < res.data.length; i++) {
              this.listData.push(res.data[i]);
            }
            this.total = res.total;
          }
        });
      },
      onViewClick(id) {
        this.$router.push(`/notice/detail?id=${id}`);
      }
    }
  };
</script>
<style scoped>
  .notice-wrapper {
    width: 100vw;
    padding: 10px 0;
    background-color: #fff;
    border-bottom: solid 10px #efefef;
    overflow: hidden;
    clear: both;
  }

  .notice-wrapper:first-child {
    border-top: solid 10px #efefef;
  }

  .notice-info {
    float: left;
    height: 100%;
    margin-left: 15px;
    width: calc(100vw - 110px);
  }

  .notice-info .notice-title {
    color: #444;
    font-size: 16px;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
    width: 100%;
  }

  .notice-info .notice-ext {
    margin-top: 20px;
    width: 100%;
    display: flex;
  }

  .notice-info .notice-author {
    color: #999;
    font-size: 12px;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
    width: calc(100% - 160px);
  }

  .notice-info .notice-date {
    color: #999;
    font-size: 12px;
    width: 150px;
    text-align: right;
  }

  .notice-cover {
    float: right;
    height: auto;
    border-radius: 5px;
    overflow: hidden;
    margin-right: 15px;
  }

  .notice-cover .notice-cover-image {
    width: 72px;
    height: 54px;
    object-fit: cover;
    border-radius: 5px;
  }
</style>

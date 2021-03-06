<template>
  <div class="list-container" v-infinite-scroll="loadMoreData" infinite-scroll-distance="100" infinite-scroll-disabled="loading">
    <div class="no-data" v-if="listData.length == 0">
      <span>暂无公告</span>
    </div>
    <div class="list-item" v-for="(item, index) in listData" :key="index" @click="onViewClick(item.id)" v-else>
      <div class="notice-info">
        <div class="notice-title">{{ item.title }}</div>
        <div class="notice-count" v-if="item.totalCount == item.readCount">全部已读</div>
        <div class="notice-count" v-else>已读{{ item.readCount }}，未读{{ item.totalCount - item.readCount }}</div>
        <div class="notice-ext">
          <div class="notice-author">{{ item.author }}</div>
          <div class="notice-date">{{ item.publishTime }}</div>
        </div>
      </div>
      <div class="notice-cover">
        <div v-bind:style="'background-image: url('+$global.baseUrl + item.coverUrl+')'" class="notice-cover-image"></div>
      </div>
    </div>
    <list-loading v-show="loading && !isLoadAll"></list-loading>
    <divider v-show="isLoadAll"></divider>
  </div>
</template>
<script>
  import Divider from '../utils/components/Divider';
  import ListLoading from '../utils/components/ListLoading';
  export default {
    name: 'NoticeIndex',
    components: {
      Divider,
      ListLoading
    },
    created() {
      this.loadListData();
    },
    data() {
      return {
        params: {
          index: 1,
          size: 20,
          query: {
            userId: this.$global.user.id
          }
        },
        pageCount: 0,
        total: 0,
        loading: true,
        isLoadAll: false,
        listData: []
      };
    },
    methods: {
      loadListData() {
        this.$get('client/notice/list', {
          params: this.params
        }).then(res => {
          this.loading = false;
          if (res.isSuccess) {
            for (let i = 0; i < res.data.length; i++) {
              this.listData.push(res.data[i]);
            }
            this.pageCount = res.pageCount;
            this.total = res.total;
          }
        });
      },
      // 上滑加载更多
      loadMoreData() {
        if (this.params.index >= this.pageCount) {
          this.isLoadAll = true;
          return;
        }
        this.loading = true;
        this.params.index++;
        setTimeout(() => {
          this.loadListData();
        }, 1000);
      },
      // 跳转详情页
      onViewClick(id) {
        this.$router.push(`/notice/detail?id=${id}`);
      }
    }
  };
</script>
<style scoped>
  .notice-info {
    float: left;
    height: 100%;
    margin-left: 15px;
    width: calc(100vw - 120px);
  }

  .notice-info .notice-title {
    color: #444;
    font-size: 16px;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
    width: 100%;
  }

  .notice-info .notice-count {
    margin-top: 5px;
    font-size: 12px;
    color: #999;
  }

  .notice-info .notice-ext {
    margin-top: 5px;
    width: 100%;
    display: flex;
    font-size: 12px;
    color: #999;
  }

  .notice-info .notice-author {
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
    width: calc(100% - 160px);
  }

  .notice-info .notice-date {
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
    width: 80px;
    height: 60px;
    display: block;
    background-size: 100%;
    background-position: center;
    background-repeat: no-repeat;
  }
</style>

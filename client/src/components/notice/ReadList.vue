<template>
  <div class="readlist-container">
    <mt-navbar v-model="activeTab">
      <mt-tab-item v-for="(tab, index) in tabs" :key="index" :id="tab.id">{{ tab.label }}（{{ count[tab.id] }}）</mt-tab-item>
    </mt-navbar>
    <mt-tab-container v-model="activeTab">
      <mt-tab-container-item v-for="(tab, index) in tabs" :key="index" :id="tab.id">
        <div class="user-list-container">
          <ul class="user-list" v-infinite-scroll="loadListMoreData">
            <li v-for="(user, i) in listData[activeTab]" :key="i">
              <img class="avatar" :src="user.avatar" :alt="user.name" v-if="user.avatar" />
              <div class="avatar" v-else>{{ getUserName(user.name) }}</div>
              <span>{{ user.name }}</span>
            </li>
            <list-loading v-show="loading"></list-loading>
          </ul>
        </div>
      </mt-tab-container-item>
    </mt-tab-container>
  </div>
</template>
<script>
  import ListLoading from '../utils/components/ListLoading';
  export default {
    name: 'NoticeReadList',
    components: {
      ListLoading
    },
    created() {
      const query = this.$route.query;
      if (query.id) {
        this.id = query.id;
      }
      if (query.total && query.readed) {
        this.count.readed = query.readed;
        this.count.unread = query.total - query.readed;
      }
      this.loadListData();
    },
    data() {
      return {
        id: '',
        activeTab: 'unread',
        params: {
          unread: {
            index: 1,
            size: 40,
            query: {
              noticeId: this.id,
              isRead: false
            }
          },
          readed: {
            index: 1,
            size: 40,
            query: {
              noticeId: this.id,
              isRead: true
            }
          }
        },
        loading: false,
        dialogVisible: this.visible,
        tabs: [
          {
            id: 'unread',
            label: '未读列表'
          },
          {
            id: 'readed',
            label: '已读列表'
          }
        ],
        listData: {
          unread: [],
          readed: []
        },
        pageCount: {
          readed: 0,
          unread: 0
        },
        count: {
          readed: 0,
          unread: 0
        }
      };
    },
    watch: {
      id: function(val, old) {
        this.params.unread.query.noticeId = val;
        this.params.readed.query.noticeId = val;
      },
      activeTab: function(val, old) {
        this.listData[val] = [];
        this.loadListData();
      }
    },
    methods: {
      loadListData() {
        this.$get('client/notice/user_list', {
          params: this.params[this.activeTab]
        }).then(res => {
          this.loading = false;
          if (res.isSuccess) {
            this.count[this.activeTab] = res.total;
            this.pageCount[this.activeTab] = res.pageCount;
            var list = this.listData[this.activeTab];
            for (let i = 0; i < res.data.length; i++) {
              list.push(res.data[i]);
            }
          }
        });
      },
      loadListMoreData() {
        if (this.params[this.activeTab].index >= this.pageCount[this.activeTab]) {
          return;
        }
        this.loading = true;
        this.params[this.activeTab].index++;
        this.loadListData();
      },
      getUserName: function(name) {
        if (name && name.length > 2) {
          return name.substr(name.length - 2, 2);
        } else {
          return name;
        }
      }
    }
  };
</script>
<style scoped>
  .readlist-container {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    -webkit-overflow-scrolling: touch;
  }

  .readlist-container .mint-navbar .mint-tab-item.is-selected {
    z-index: 1;
  }

  .user-list-container .user-list {
    width: 100%;
    height: calc(100vh - 50px);
    margin: auto;
    overflow-x: hidden;
    overflow-y: scroll;
    padding: 0 5%;
  }
  .user-list-container .user-list li {
    display: inline-block;
    width: calc(90% / 4);
    list-style: none;
    padding: 0;
    margin: 10px 0;
    text-align: center;
    line-height: 35px;
  }
  .user-list-container .user-list img.avatar {
    border-radius: 50%;
    width: 40px;
    height: 40px;
    vertical-align: middle;
    border-style: none;
  }
  .user-list-container .user-list div.avatar {
    display: inline-block;
    border-radius: 50%;
    width: 40px;
    height: 40px;
    line-height: 40px;
    background-color: #3296fa;
    color: #fff;
    font-size: 12px;
    font-weight: 600;
  }
  .user-list-container .user-list span {
    display: block;
    margin: 0 auto;
    height: 20px;
    text-align: center;
    font-size: 13px;
    color: #181e25;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    line-height: 20px;
  }
</style>
<style>
  .readlist-container .mint-tab-item .mint-tab-item-label {
    font-size: 14px;
  }
</style>

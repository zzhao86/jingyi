<template>
  <div class="container">
    <mt-navbar v-model="activeTab">
      <mt-tab-item v-for="(tab, index) in tabs" :key="index" :id="tab.id">{{ tab.label }}({{ count.readed }})</mt-tab-item>
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
          </ul>
        </div>
      </mt-tab-container-item>
    </mt-tab-container>
  </div>
</template>
<script>
  export default {
    name: 'NoticeReadList',
    created() {
      const query = this.$route.query;
      if (query.id) {
        this.id = query.id;
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
            size: 100,
            query: {
              noticeId: this.id,
              isRead: false
            }
          },
          readed: {
            index: 1,
            size: 100,
            query: {
              noticeId: this.id,
              isRead: true
            }
          }
        },
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
        this.loadListData();
      }
    },
    methods: {
      loadListData() {
        this.$get('client/notice/user_list', {
          params: this.params[this.activeTab]
        }).then(res => {
          this.listData[this.activeTab] = res.data;
          this.count[this.activeTab] = res.total;
        });
      },
      loadListMoreData() {
        this.params[this.activeTab].index++;
      },
      getUserName: function(name) {
        if (name && name.length > 3) {
          return name.substr(name.length - 3, 3);
        } else {
          return name;
        }
      }
    }
  };
</script>
<style scoped>
  .container {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    -webkit-overflow-scrolling: touch;
  }
  .container .mint-navbar .mint-tab-item.is-selected {
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

<template>
  <el-dialog title="人员列表" :visible.sync="dialogVisible" append-to-body width="800px" @open="onDialogOpen" @close="onDialogClose">
    <div>
      <el-tabs v-model="activeTab" type="card" @tab-click="onTabClick">
        <el-tab-pane :label="item.label + '(' + count[item.name] + ')'" :name="item.name" v-for="(item, index) in tabs" :key="index">
          <div class="user-list-container">
            <ul class="user-list">
              <li v-for="(user, i) in listData[activeTab]" :key="i">
                <img class="avatar" :src="user.avatar" :alt="user.name" v-if="user.avatar" />
                <div class="avatar" v-else>{{ getUserName(user.name) }}</div>
                <span>{{ user.name }}</span>
              </li>
            </ul>
            <el-pagination :hide-on-single-page="true" background @current-change="onCurrentPageChange" :page-size="params[item.name].size" layout="prev, pager, next" :total="count[item.name]">
            </el-pagination>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
    </span>
  </el-dialog>
</template>
<script>
  export default {
    name: 'ReadList',
    data() {
      return {
        params: {
          unread: {
            index: 1,
            size: 100,
            query: {
              noticeId: '',
              isRead: false
            }
          },
          readed: {
            index: 1,
            size: 100,
            query: {
              noticeId: '',
              isRead: true
            }
          }
        },
        tabs: [
          {
            name: 'unread',
            label: '未读列表'
          },
          {
            name: 'readed',
            label: '已读列表'
          }
        ],
        dialogVisible: this.visible,
        activeTab: 'unread',
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
    props: {
      relId: String,
      type: String,
      visible: {
        type: Boolean,
        default: false
      },
      readed: {
        type: Number,
        default: 0
      },
      total: {
        type: Number,
        default: 0
      }
    },
    methods: {
      loadListData: function() {
        if (!this.url) return;
        this.$get(this.url, {
          params: this.params[this.activeTab]
        }).then(res => {
          this.listData[this.activeTab] = res.data;
          this.count[this.activeTab] = res.total;
        });
      },
      onTabClick: function(tab, event) {
        this.loadListData();
      },
      onDialogOpen: function() {
        this.params.unread.query.noticeId = this.relId;
        this.params.readed.query.noticeId = this.relId;
        this.loadListData();
        this.count.readed = this.readed;
        this.count.unread = this.total - this.readed;
      },
      onDialogClose: function() {
        this.$emit('close');
      },
      getUserName: function(name) {
        if (name && name.length > 3) {
          return name.substr(name.length - 3, 3);
        } else {
          return name;
        }
      },
      onCurrentPageChange: function(index) {
        this.params[this.activeTab].index = index;
        this.loadListData();
      }
    },
    watch: {
      visible: function(val, old) {
        this.dialogVisible = val;
      }
    },
    computed: {
      url: {
        get: function(val, old) {
          let url = '';
          switch (this.type) {
            case 'notice':
              url = 'back/notice/user_list';
              break;
          }
          return url;
        }
      }
    }
  };
</script>
<style scoped>
  .user-list-container {
    overflow-y: auto;
    overflow-x: hidden;
  }
  .user-list-container .user-list {
    position: relative;
    width: 100%;
    height: 400px;
    margin: 0 auto;
    padding-top: 0;
    overflow-x: hidden;
    overflow-y: auto;
    padding-left: 20px;
    padding-top: 9px;
    text-align: left;
  }
  .user-list-container .user-list li {
    display: inline-block;
    width: 40px;
    list-style: none;
    margin: 0 42px 9px 0;
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

<template>
  <div>
    <el-dialog title="通讯录选择" width="700px" append-to-body :show-close="false" :close-on-press-escape="false" :close-on-click-modal="false" :visible.sync="visible" @before-close="beforeDialogClose">
      <div class="content">
        <div class="choose-wrapper">
          <div class="title">通讯录：</div>
          <div class="choose-box">
            <!-- 搜索框 -->
            <div class="choose-search">
              <el-input v-model="keywords" placeholder="搜索" prefix-icon="fa fa-search" @change="onNextDeptClick(deptId)"></el-input>
            </div>
            <!-- 部门导航条 -->
            <div class="breadcrumb-box">
              <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item v-for="(item, i) in parentsList" :key="i">
                  <span :title="item.name" v-if="i == parentsList.length - 1">{{ item.name }}</span>
                  <span :title="item.name" v-else @click="onBreadcrumbClick(item.id)">{{ item.name }}</span>
                </el-breadcrumb-item>
              </el-breadcrumb>
            </div>
            <!-- 通讯录列表 -->
            <el-table ref="contactTable" :data="contactTable" size="small" class="contact-table" max-height="304" @select="onContactSelectionChange" @select-all="onContactSelectionChange">
              <el-table-column type="selection" width="30"></el-table-column>
              <el-table-column prop="name" label="全选">
                <template slot-scope="scope">
                  <div class="contact-info">
                    <div class="contact-avatar">
                      <i class="fa fa-folder dept-icon" v-if="scope.row.type =='dept'"></i>
                      <img :src="scope.row.avatar" v-else-if="scope.row.type == 'user' && scope.row.avatar" />
                      <div class="name-avatar" v-else>{{ scope.row.name.substr(scope.row.name.length - 2, 2) }}</div>
                    </div>
                    <span class="contact-name" :title="scope.row.name">{{ scope.row.name }}</span>
                    <span class="contact-next" v-if="scope.row.type == 'dept' && !scope.row.disabled" @click="onNextDeptClick(scope.row.id)">下级<i class="fa fa-level-down"></i></span>
                    <span class="contact-next disabled" v-else-if="scope.row.type == 'dept' && scope.row.disabled">下级<i class="fa fa-level-down"></i></span>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
        <div class="choose-wrapper choosed">
          <div class="title">已选：</div>
          <div class="choose-box">
            <!-- 已选列表 -->
            <el-table :data="chooseTable" size="small" max-height="380" :show-header="false" empty-text="暂无任何选择">
              <el-table-column>
                <template slot-scope="scope">
                  <div class="contact-info">
                    <div class="contact-avatar">
                      <i class="fa fa-folder dept-icon" v-if="scope.row.type =='dept'"></i>
                      <img :src="scope.row.avatar" v-else-if="scope.row.type == 'user' && scope.row.avatar" />
                      <div class="name-avatar" v-else>{{ scope.row.name.substr(scope.row.name.length - 2, 2) }}</div>
                    </div>
                    <span class="contact-name" :title="scope.row.name">{{ scope.row.name }}</span>
                    <span class="contact-close" @click="onCancelChooseClick(scope.row)"><i class="el-icon-circle-close"></i></span>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="beforeDialogClose">取 消</el-button>
        <el-button type="primary" @click="onOkClick">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
  export default {
    name: 'ContactChoose',
    data() {
      return {
        deptId: '0',
        keywords: '',
        contactTable: [],
        chooseTable: [],
        parentsList: []
      };
    },
    props: {
      visible: {
        type: Boolean,
        defalut: false,
        required: true
      },
      choosed: {
        type: Array,
        defalult: []
      }
    },
    watch: {
      visible: function(val, old) {
        if (val) {
          this.loadRootData();
          this.chooseTable = [];
          for (let i = 0; i < this.choosed.length; i++) {
            this.chooseTable.push(this.choosed[i]);
          }
        }
      }
    },
    methods: {
      // 加载根部门数据
      loadRootData: function() {
        this.$get('back/dept/root').then(res => {
          if (res.isSuccess) {
            this.deptId = res.data.id;
            this.loadTableData(res.data.id);
            this.loadDeptParentList(res.data.id);
          }
        });
      },
      // 加载通讯录数据
      loadTableData: function(id) {
        this.$get('back/user/choose', {
          params: {
            deptId: id,
            keywords: this.keywords
          }
        }).then(res => {
          if (res.isSuccess) {
            this.contactTable = res.data;
            this.contactIsCheckedByChoose();
          }
        });
      },
      // 加载所有父部门列表
      loadDeptParentList: function(id) {
        let root = {
          id: '0',
          name: '通讯录',
          checked: false,
          avatar: null,
          type: 'dept'
        };
        if (id == '0') {
          this.parentsList = [];
          this.parentsList.unshift(root);
          return;
        }
        this.$get('back/dept/parent_list', {
          params: {
            id: id
          }
        }).then(res => {
          if (res.isSuccess) {
            this.parentsList = res.data;
            this.parentsList.unshift(root);
          }
        });
      },
      // 点击下级按钮事件
      onNextDeptClick: function(id) {
        this.deptId = id;
        this.loadTableData(id);
        this.loadDeptParentList(id);
      },
      // 父部门导航栏点击事件
      onBreadcrumbClick: function(id) {
        this.deptId = id;
        this.loadTableData(id);
        this.loadDeptParentList(id);
      },
      // 通讯录选中改变事件
      onContactSelectionChange: function(selection) {
        for (let i = 0; i < this.contactTable.length; i++) {
          let item = this.contactTable[i];
          let index = this.getArrayIndexByItem(selection, item);
          item.checked = index > -1;
          item.disabled = index > -1;
        }
        this.setChooseTableData();
      },
      // 取消选择事件
      onCancelChooseClick: function(row) {
        let index = this.getArrayIndexByItem(this.chooseTable, row);
        this.chooseTable.splice(index, 1);
        index = this.getArrayIndexByItem(this.contactTable, row);
        if (index > -1) {
          let item = this.contactTable[index];
          item.checked = false;
        }
        this.contactIsCheckedByChoose();
      },
      // 根据已选列表判断通讯录行是否选中
      contactIsCheckedByChoose: function() {
        for (let i = 0; i < this.contactTable.length; i++) {
          let item = this.contactTable[i];
          let index = this.getArrayIndexByItem(this.chooseTable, item);
          item.checked = index > -1;
        }
        this.setContactRowChecked();
      },
      // 根据通讯录行是否选中，增删已选列表数据
      setChooseTableData: function() {
        for (let i = 0; i < this.contactTable.length; i++) {
          let item = this.contactTable[i];
          if (item.checked) {
            // 通讯录行选中，增加已选数据
            let index = this.getArrayIndexByItem(this.chooseTable, item);
            if (index == -1) {
              this.chooseTable.push(item);
            }
          } else {
            // 通讯录行未选，删除已选数据
            let index = this.getArrayIndexByItem(this.chooseTable, item);
            if (index > -1) {
              this.chooseTable.splice(index, 1);
            }
          }
        }
      },
      // 根据通讯录数据设置通讯录行选中状态
      setContactRowChecked: function() {
        this.$nextTick(function() {
          let table = this.$refs.contactTable;
          for (let i = 0; i < this.contactTable.length; i++) {
            let item = this.contactTable[i];
            table.toggleRowSelection(item, item.checked);
          }
        });
      },
      onOkClick: function() {
        this.$emit('ok', this.chooseTable);
      },
      beforeDialogClose: function() {
        this.$emit('close');
      },
      getArrayIndexByItem: function(array, item) {
        let index = -1;
        if (!array || !item) return index;

        for (let i = 0; i < array.length; i++) {
          let obj = array[i];
          if (obj.id == item.id) {
            index = i;
            break;
          }
        }
        return index;
      }
    }
  };
</script>
<style>
  .breadcrumb-box .el-breadcrumb__inner {
    display: inline-block;
    max-width: 56px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    cursor: pointer;
    color: #409eff;
  }
  .breadcrumb-box .el-breadcrumb__separator {
    margin: 0;
  }
</style>
<style scoped>
  .content {
    margin-top: -20px;
    margin-bottom: -20px;
    padding: 0 15px;
    overflow: hidden;
  }
  .content .choose-wrapper {
    width: 300px;
    float: left;
  }

  .choose-wrapper.choosed {
    float: right;
  }
  .choose-wrapper .title {
    font-size: 16px;
    margin-bottom: 10px;
  }
  .choose-box {
    height: 380px;
    overflow: hidden;
    border: solid 1px #dedede;
    border-radius: 5px;
    background-color: #f3f3f3;
    padding: 10px;
    display: flex;
    flex-direction: column;
  }
  .choose-box .choose-search {
    height: 40px;
  }
  .choose-box .breadcrumb-box {
    margin: 10px 0;
  }
  .choose-box .contact-table {
    flex: 1;
  }

  .contact-info {
    display: flex;
  }
  .contact-info .contact-avatar {
    display: inline-block;
    width: 28px;
    height: 28px;
    border-radius: 50%;
    margin-right: 5px;
  }
  .contact-info .contact-avatar .dept-icon {
    font-size: 24px;
    color: #b3d8ff;
  }
  .contact-info .contact-avatar img {
    width: 28px;
    height: 28px;
    border-radius: 50%;
  }
  .contact-info .contact-avatar .name-avatar {
    width: 28px;
    height: 28px;
    line-height: 28px;
    text-align: center;
    font-size: 12px;
    color: #fff;
    border-radius: 50%;
    background-color: #409eff;
  }
  .contact-info .contact-name {
    display: inline-block;
    width: 130px;
    line-height: 28px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  .contact-info .contact-next {
    color: #409eff;
    cursor: pointer;
    flex: 1;
    text-align: right;
  }
  .contact-next.disabled {
    color: #ccc;
  }
  .contact-info .contact-close {
    font-size: 20px;
    color: #666;
    cursor: pointer;
    flex: 1;
    text-align: right;
    padding-right: 10px;
  }
</style>

<template>
  <div>
    <el-dialog title="通讯录选择" width="700px" append-to-body :visible.sync="dialogVisible">
      <div class="content">
        <div class="choose-wrapper">
          <div class="title">通讯录：</div>
          <div class="choose-box">
            <div class="choose-search">
              <el-input v-model="keywords" placeholder="搜索" prefix-icon="fa fa-search"></el-input>
            </div>
            <div class="breadcrumb-box">
              <el-breadcrumb separator-class="el-icon-arrow-right">
                <el-breadcrumb-item @click.native="onClick">通讯录</el-breadcrumb-item>
                <el-breadcrumb-item>部门1</el-breadcrumb-item>
                <el-breadcrumb-item>部门1-1</el-breadcrumb-item>
                <el-breadcrumb-item>部门1-1-1</el-breadcrumb-item>
              </el-breadcrumb>
            </div>
            <el-table ref="contactTable" :data="contactTable" size="small" class="contact-table" max-height="304" @selection-change="onContactSelectionChange">
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
                    <span class="contact-next" v-show="scope.row.type == 'dept'">下级<i class="fa fa-level-down"></i></span>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
        <div class="choose-wrapper choosed">
          <div class="title">已选：</div>
          <div class="choose-box">
            <el-table :data="chooseTable" size="small" max-height="402" :show-header="false" empty-text="暂无任何选择">
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
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
  export default {
    name: 'ContactChoose',
    created() {
      this.loadTableData('9704b2b9c6a948dfbcef23c3e7059490');
    },
    data() {
      return {
        dialogVisible: true,
        keywords: '',
        contactTable: [],
        chooseTable:[]
      };
    },
    props: {},
    methods: {
      loadTableData: function(id) {
        this.$get('back/user/choose', {
          params: {
            deptId: id
          }
        }).then(res => {
          if (res.isSuccess) {
            this.contactTable = res.data;
          }
        });
      },
      onContactSelectionChange:function(selection){
        this.chooseTable = selection;
      },
      onCancelChooseClick:function(row){
        this.$refs['contactTable'].toggleRowSelection(row, false);
      },
      onClick: function() {
        console.log('123');
      }
    }
  };
</script>
<style>
  .breadcrumb-box .el-breadcrumb__inner {
    display: inline-block;
    max-width: 50px;
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
  .contact-info .contact-avatar .dept-icon{
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
  .contact-info .contact-close{
    font-size: 20px;
    color: #666;
    cursor: pointer;
    flex: 1;
    text-align: right;
  }
</style>

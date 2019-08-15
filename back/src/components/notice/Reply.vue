<template>
  <div class="app-main">
    <div class="main-header">
      <div class="title">公告回复列表</div>
      <div class="buttons">
        <el-button type="primary" size="small">批量下载附件</el-button>
        <el-button type="success" size="small">下载全部附件</el-button>
      </div>
    </div>
    <div class="main-container">
      <el-table :data="tableData" ref="table" stripe v-auto-height :max-height="maxHeight" @selection-change="onTableSelectionChange">
        <el-table-column type="selection" align="center" width="30"> </el-table-column>
        <el-table-column align="center" label="序号" width="50">
          <template slot-scope="scope">{{ scope.$index + (params.index - 1) * params.size + 1 }}</template>
        </el-table-column>
        <el-table-column prop="content" label="回复内容" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-button type="text" class="table-reply-content" @click="onViewClick(scope.row)">{{ scope.row.content }}</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="fileName" label="附件" show-overflow-tooltip></el-table-column>
        <el-table-column prop="userName" label="回复人" width="150" show-overflow-tooltip></el-table-column>
        <el-table-column prop="replyTime" label="回复时间" width="150"></el-table-column>
        <el-table-column label="操作" width="80">
          <template slot-scope="scope">
            <div class="options-buttons">
              <el-button type="primary" size="mini" @click="onDownLoadClick(scope.row)">下载附件</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <pagination :params="params" :total="total" @page-change="onPageChange" @size-change="onSizeChange"></pagination>

      <el-dialog title="公告回复详情" :visible.sync="dialogVisible" width="500px" append-to-body>
        <el-form :model="detail" label-width="100px">
          <el-form-item label="回复人">
            <div class="reply-user">
              <div class="avatar-wrapper">
                <img class="avatar" :src="detail.userAvatar" v-if="!detail.userAvatar" />
                <div class="avatar-icon" v-else>{{ userNameConvert(detail.userName) }}</div>
              </div>
              <div class="name">{{ detail.userName }}</div>
            </div>
          </el-form-item>
          <el-form-item label="回复内容">
            <el-input type="textarea" v-model="detail.content" rows="6" resize="none" readonly></el-input>
          </el-form-item>
          <el-form-item label="附件">
            <div class="attach-wrapper">
              <div class="icon fa" v-bind:class="fileIconConvert(detail.fileType)"></div>
              <div class="name">{{ detail.fileName }}</div>
            </div>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false" size="small">取 消</el-button>
          <el-button type="primary" @click="dialogVisible = false" size="small">下载附件</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
  import Pagination from '../utils/components/Pagination';
  export default {
    name: 'NoticeReply',
    components: {
      Pagination
    },
    created() {
      const query = this.$route.query;
      if (query.id) {
        this.params.query.noticeId = query.id;
      }
      this.loadTableData();
    },
    data() {
      return {
        params: {
          index: 1,
          size: this.$global.pageSize,
          query: {
            noticeId: ''
          }
        },
        total: 0,
        tableData: [],
        maxHeight: 500,
        tableSelected: [],
        detail: {},
        dialogVisible: false
      };
    },
    methods: {
      loadTableData() {
        this.$get('back/notice/reply_list', {
          params: this.params
        }).then(res => {
          if (res.isSuccess) {
            this.tableData = res.data;
            this.total = res.total;
          }
        });
      },
      onPageChange: function(index) {
        this.params.index = index;
        this.loadTableData();
      },
      onSizeChange: function(size) {
        this.params.size = size;
        this.loadTableData();
      },
      onViewClick: function(row) {
        this.dialogVisible = true;
        this.detail = row;
      },
      onDownLoadClick: function(row) {
        window.location.href= `${this.$global.baseUrl}back/notice/download?id=${row.id}`
        // open(`${this.$global.baseUrl}back/notice/download?url=${row.fileUrl}`);
        // this.$get('back/notice/download', {
        //   params: {
        //     id: row.id
        //   }
        // }).then(res => {
        //   console.log(res);
        // });
      },
      onTableSelectionChange: function(selection) {
        this.tableSelected = selection;
        this.deleteButtonState = selection.length == 0;
      },
      userNameConvert: function(name) {
        if (!name) return '';
        return name.substring(name.length - 2);
      },
      fileIconConvert: function(type) {
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
  .table-reply-content {
    width: 100%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    color: #409eff;
    cursor: pointer;
    text-align: left;
  }
  .table-reply-content:hover {
    color: #66b1ff;
  }
  .table-reply-content:focus,
  .table-reply-content:active {
    color: #3a8ee6;
  }
  .reply-user {
    display: flex;
  }

  .reply-user .avatar-wrapper {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    overflow: hidden;
    display: inline-block;
  }

  .reply-user .avatar-wrapper .avatar {
    width: 100%;
    height: 100%;
  }
  .reply-user .avatar-wrapper .avatar-icon {
    background-color: #409eff;
    color: #fff;
    text-align: center;
    font-size: 12px;
    font-weight: 800;
  }

  .reply-user .name {
    display: inline-block;
    line-height: 40px;
    margin-left: 15px;
  }
  .attach-wrapper {
    display: flex;
    cursor: pointer;
  }
  .attach-wrapper .icon {
    font-size: 30px;
    line-height: 40px;
    display: inline-block;
  }
  .attach-wrapper .name {
    flex: 1;
    line-height: 40px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    margin-left: 10px;
  }
  .attach-wrapper .icon.green {
    color: #39825a;
  }
  .attach-wrapper .icon.orange {
    color: #dc6141;
  }
  .attach-wrapper .icon.blue {
    color: #4269a5;
  }
  .attach-wrapper .icon.brown {
    color: #e56e4d;
  }
  .attach-wrapper .icon.default {
    color: #65676b;
  }
</style>

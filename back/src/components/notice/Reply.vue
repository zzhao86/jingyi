<template>
  <div class="app-main">
    <div class="main-header">
      <div class="title">通知公告管理</div>
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
        <el-table-column prop="content" label="回复内容"></el-table-column>
        <el-table-column prop="fileName" label="附件" show-overflow-tooltip></el-table-column>
        <el-table-column prop="userName" label="回复人" width="150" show-overflow-tooltip></el-table-column>
        <el-table-column prop="createdTime" label="回复时间" width="150"></el-table-column>
        <el-table-column label="操作" width="80">
          <template slot-scope="scope">
            <div class="options-buttons">
              <el-button type="primary" size="mini" @click="onEditClick(scope.row)">下载附件</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <pagination :params="params" :total="total" @page-change="onPageChange" @size-change="onSizeChange"></pagination>
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
        tableSelected: []
      };
    },
    methods: {
      loadTableData() {
        this.$get('back/notice/reply_list', {
          params: this.params
        }).then(res => {
          if (res.isSuccess) {
            this.tableData = res.data;
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
      onTableSelectionChange: function(selection) {
        this.tableSelected = selection;
        this.deleteButtonState = selection.length == 0;
      }
    }
  };
</script>
<style scoped></style>

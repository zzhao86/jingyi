<template>
  <div class="app-main">
    <div class="main-header">
      <div class="title">通知公告管理</div>
      <div class="buttons">
        <el-button type="primary" size="small" @click="onAddClick">新建</el-button>
        <el-button type="danger" size="small" :disabled="deleteButtonState" @click="onDeleteBatchClick">删除</el-button>
      </div>
    </div>
    <div class="main-container">
      <el-table :data="tableData" ref="table" stripe v-auto-height :max-height="maxHeight" @selection-change="onTableSelectionChange">
        <el-table-column type="selection" align="center" width="50"> </el-table-column>
        <el-table-column align="center" label="序号" width="50">
          <template slot-scope="scope">{{ scope.$index + (params.index - 1) * params.size + 1 }}</template>
        </el-table-column>
        <el-table-column prop="title" label="标题">
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="onViewClick(scope.row)">{{ scope.row.title }}</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="author" label="作者" width="150"></el-table-column>
        <el-table-column label="状态" width="100">
          <template slot-scope="scope">
            <div class="total-count">共{{ scope.row.totalCount }}人</div>
            <div class="read-count">已读{{ scope.row.readCount }}人</div>
          </template>
        </el-table-column>
        <el-table-column prop="publishTime" label="发布时间" width="150"></el-table-column>
        <el-table-column label="操作" width="120">
          <template slot-scope="scope">
            <el-button type="primary" plain size="mini" icon="fa fa-pencil" title="编辑" @click="onEditClick(scope.row)"></el-button>
            <el-button type="danger" plain size="mini" icon="fa fa-remove" title="删除" @click="onDeleteClick(scope.row)"></el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination :params="params" :total="total" @page-change="onPageChange" @size-change="onSizeChange"></pagination>
    </div>
  </div>
</template>
<script>
  import pagination from '../utils/components/Pagination';
  export default {
    name: 'NoticeIndex',
    components: {
      pagination
    },
    created() {
      this.loadTableData();
    },
    data() {
      return {
        params: {
          index: 1,
          size: this.$global.pageSize,
          query: {
            name: ''
          }
        },
        total: 0,
        tableData: [],
        maxHeight: 500,
        tableSelected: [],
        deleteButtonState: true
      };
    },
    methods: {
      // 加载Tabel数据
      loadTableData: function() {
        this.$http
          .get('back/notice/list', {
            params: this.params
          })
          .then(res => {
            this.tableData = res.data;
            this.total = res.total;
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
      onAddClick: function() {
        this.$router.push('/notice/detail/add');
      },
      onViewClick: function(row) {
        this.$router.push('/notice/detail/view?id=' + row.id);
      },
      onEditClick: function(row) {
        this.$router.push('/notice/detail/edit?id=' + row.id);
      },
      onDeleteClick: function(row) {
        this.$router.push('/notice/detail/view?id=' + row.id);
      },
      onDeleteBatchClick: function() {
        if (!this.tableSelected.length || this.tableSelected.length == 0) {
          this.$message.error('请选择要删除的数据行');
          return;
        }
        let ids = new Array();
        for (let i = 0; i < this.tableSelected.length; i++) {
          ids.push(this.tableSelected[i].id);
        }
      },
      onTableSelectionChange: function(selection) {
        this.tableSelected = selection;
        this.deleteButtonState = selection.length == 0;
      }
    }
  };
</script>
<style scoped>
  .total-count,
  .read-count {
    font-size: 12px;
    height: 18px;
  }
  .read-count {
    color: #3296fa;
  }
</style>

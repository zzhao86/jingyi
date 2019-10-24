<template>
  <div class="app-main">
    <div class="main-header">
      <div class="title">通知公告管理</div>
      <div class="buttons">
        <el-button type="primary" size="small" @click="onAddClick">新建</el-button>
        <el-button type="danger" size="small" :disabled="!tableSelected || tableSelected.length == 0" @click="onDeleteBatchClick">删除</el-button>
      </div>
    </div>
    <div class="main-table">
      <el-table :data="tableData" ref="table" stripe v-auto-height :max-height="maxHeight" @selection-change="onTableSelectionChange">
        <el-table-column type="selection" align="center" width="30"> </el-table-column>
        <el-table-column align="center" label="序号" width="50">
          <template slot-scope="scope">{{ scope.$index + (params.index - 1) * params.size + 1 }}</template>
        </el-table-column>
        <el-table-column prop="title" label="标题" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-link type="primary" @click="onViewClick(scope.row)">{{ scope.row.title }}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="author" label="作者" width="150" show-overflow-tooltip></el-table-column>
        <el-table-column label="状态" width="100">
          <template slot-scope="scope">
            <div class="total-count">共{{ scope.row.totalCount }}人</div>
            <!-- <div class="read-count">已读{{ scope.row.readCount }}人</div> -->
            <el-link type="primary" class="read-count" @click="onReadListShowClick(scope.row)">已读{{ scope.row.readCount }}人</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="publishTime" label="发布时间" width="150"></el-table-column>
        <el-table-column label="操作" width="160">
          <template slot-scope="scope">
            <div class="options-buttons">
              <el-button type="primary" size="mini" @click="onEditClick(scope.row)">编辑</el-button>
              <el-button type="danger" size="mini" @click="onDeleteClick(scope.row)">删除</el-button>
              <el-button type="success" size="mini" @click="onReplyClick(scope.row)">回复列表</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <pagination :params="params" :total="total" @page-change="onPageChange" @size-change="onSizeChange"></pagination>
      <!-- 已读未读人员列表Modal -->
      <read-list :visible="read.visible" type="notice" :readed="read.readCount" :total="read.totalCount" :relId="read.id" @close="onReadListCloseClick"></read-list>
    </div>
  </div>
</template>
<script>
  import Pagination from '../utils/components/Pagination';
  import ReadList from '../utils/components/ReadList';
  export default {
    name: 'NoticeIndex',
    components: {
      Pagination,
      ReadList
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
        read: {
          visible: false,
          readCount: 0,
          totalCount: 0,
          id: ''
        }
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
        const vue = this;
        vue.$confirm(`确定要删除公告 ${row.title} 吗？`, () => {
          vue
            .$get('back/notice/delete', {
              params: {
                id: row.id
              }
            })
            .then(res => {
              if (res.isSuccess) {
                vue.tableData.remove(row);
                vue.$success(`公告 ${row.title} 删除成功`);
              }
            });
        });
      },
      onDeleteBatchClick: function() {
        const vue = this;
        if (!vue.tableSelected.length || vue.tableSelected.length == 0) {
          vue.$message.error('请选择要删除的数据行');
          return;
        }
        let ids = new Array();
        for (let i = 0; i < vue.tableSelected.length; i++) {
          ids.push(vue.tableSelected[i].id);
        }
        vue.$confirm(`确定要删除所有选中项吗？`, () => {
          vue.$post('back/notice/delete_batch', ids).then(res => {
            if (res.isSuccess) {
              vue.tableData.removes(vue.tableSelected);
              vue.$success(`删除成功`);
            }
          });
        });
      },
      onReplyClick: function(row) {
        this.$router.push('notice/reply?id=' + row.id);
      },
      onTableSelectionChange: function(selection) {
        this.tableSelected = selection;
      },
      onReadListShowClick: function(row) {
        this.read.id = row.id;
        this.read.readCount = row.readCount;
        this.read.totalCount = row.totalCount;
        this.read.visible = true;
      },
      onReadListCloseClick: function(visible) {
        this.read.visible = visible;
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

<template>
  <div class="app-main">
    <div class="main-header">
      <div class="title">资产分类</div>
      <div class="buttons">
        <el-button type="primary" size="small" @click="onAddClick">新建</el-button>
        <el-button type="warning" size="small" @click="onImportClick">导入</el-button>
      </div>
    </div>

    <div class="main-container">
      <el-table :data="tableData" ref="table" stripe v-auto-height :max-height="maxHeight">
        <el-table-column align="center" label="序号" width="50">
          <template slot-scope="scope">{{ scope.$index + (params.index - 1) * params.size + 1 }}</template>
        </el-table-column>
        <el-table-column prop="name" label="分类名称"></el-table-column>
        <el-table-column prop="parent" label="上级分类"></el-table-column>
        <el-table-column label="操作" width="100">
          <template slot-scope="scope">
            <div class="options-buttons">
              <el-button type="primary" size="mini" @click="onEditClick(scope.row)">编辑</el-button>
              <el-button type="danger" size="mini" @click="onDeleteClick(scope.row)">删除</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <pagination :params="params" :total="total" @page-change="onPageChange" @size-change="onSizeChange"></pagination>

      <!-- 详情Dialog -->
      <el-dialog :visible.sync="dialogDetailVisible">
        <el-form :model="detail"  ref="form" :rules="rules" label-width="100px">
          <el-form-item prop="name" label="分类名称">
            <el-input></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
  import Pagination from '../utils/components/Pagination';
  export default {
    name: 'AssetsCategroy',
    components: {
      Pagination
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
        detail: {},
        dialogDetailVisible: false
      };
    },
    methods: {
      loadTableData() {
        this.$get('back/assets/category/list', {
          params: this.params
        }).then(res => {
          if (res.isSuccess) {
            this.total = res.total;
            this.tableData = res.data;
          }
        });
      },
      onPageChange(index) {
        this.params.index = index;
        this.loadTableData();
      },
      onSizeChange(size) {
        this.params.size = size;
        this.loadTableData();
      },
      onAddClick() {
        this.$router.push('/assets/detail/add');
      },
      onImportClick() {}
    }
  };
</script>
<style scoped></style>

<template>
  <div class="app-main">
    <div class="main-header">
      <div class="title">资产列表</div>
      <div class="buttons">
        <el-button type="primary" size="small" @click="onAddClick">新建</el-button>
        <el-button type="primary" size="small" @click="onImportClick">导入</el-button>
      </div>
    </div>
    <div class="main-table">
      <el-table :data="tableData" ref="table" stripe v-auto-height :max-height="maxHeight">
        <el-table-column align="center" label="序号" width="50">
          <template slot-scope="scope">{{ scope.$index + (params.index - 1) * params.size + 1 }}</template>
        </el-table-column>
        <el-table-column prop="statusName" label="资产状态" width="120">
          <template slot-scope="scope">
            <el-tag effect="dark" size="mini" type="info" v-if="scope.row.status == 100">{{ scope.row.statusName }}</el-tag>
            <el-tag effect="dark" size="mini" type="warning" v-else-if="scope.row.status == 200">{{ scope.row.statusName }}</el-tag>
            <el-tag effect="dark" size="mini" type="success" v-else-if="scope.row.status == 300">{{ scope.row.statusName }}</el-tag>
            <el-tag effect="dark" size="mini" type="warning" v-else-if="scope.row.status == 400">{{ scope.row.statusName }}</el-tag>
            <el-tag effect="dark" size="mini" type="warning" v-else-if="scope.row.status == 500">{{ scope.row.statusName }}</el-tag>
            <el-tag effect="dark" size="mini" type="success" v-else-if="scope.row.status == 600">{{ scope.row.statusName }}</el-tag>
            <el-tag effect="dark" size="mini" type="warning" v-else-if="scope.row.status == 700">{{ scope.row.statusName }}</el-tag>
            <el-tag effect="dark" size="mini" type="success" v-else-if="scope.row.status == 800">{{ scope.row.statusName }}</el-tag>
            <el-tag effect="dark" size="mini" type="warning" v-else-if="scope.row.status == 900">{{ scope.row.statusName }}</el-tag>
            <el-tag effect="dark" size="mini" type="warning" v-else-if="scope.row.status == 1000">{{ scope.row.statusName }}</el-tag>
            <el-tag effect="dark" size="mini" type="warning" v-else-if="scope.row.status == 1100">{{ scope.row.statusName }}</el-tag>
            <el-tag effect="dark" size="mini" type="warning" v-else-if="scope.row.status == 1200">{{ scope.row.statusName }}</el-tag>
            <el-tag size="mini" type="warning" v-else>未知</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="code" label="资产编码" width="100">
          <template slot-scope="scope">
            <el-link type="primary" @click="onViewClick(scope.row)">{{ scope.row.code }}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="资产名称" width="200" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-link type="primary" @click="onViewClick(scope.row)">{{ scope.row.name }}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="categoryName" label="分类" width="150" show-overflow-tooltip></el-table-column>
        <el-table-column prop="positionName" label="位置" width="100"></el-table-column>
        <el-table-column prop="brand" label="品牌" width="100"></el-table-column>
        <el-table-column prop="model" label="型号" width="100"></el-table-column>
        <el-table-column prop="useStatusName" label="使用状态" width="100"></el-table-column>
        <el-table-column prop="adminName" label="管理员" width="100"></el-table-column>
        <el-table-column prop="purchasingMethodName" label="购置方式" width="100"></el-table-column>
        <el-table-column prop="startDate" label="购置/起租日期" width="150"></el-table-column>
        <el-table-column label="操作" width="100" fixed="right">
          <template slot-scope="scope">
            <div class="options-buttons">
              <el-button type="primary" size="mini" @click="onEditClick(scope.row)">编辑</el-button>
              <el-button type="danger" size="mini" @click="onDeleteClick(scope.row)">删除</el-button>
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
    name: 'AssetsIndex',
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
        maxHeight: 500,
        tableData: []
      };
    },
    methods: {
      loadTableData() {
        this.$get('back/assets/list', {
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
      // 刷新主数据
      flushDataTable() {
        this.params.index = 1;
        this.loadTableData();
      },
      // 新建点击事件
      onAddClick() {
        this.$router.push('/assets/detail/add');
      },
      // 编辑点击事件
      onEditClick(row) {
        this.$router.push('/assets/detail/edit?id=' + row.id);
      },
      // 查看点击事件
      onViewClick(row) {
        this.$router.push('/assets/detail/view?id=' + row.id);
      },
      // 删除点击事件
      onDeleteClick(row) {
        const vue = this;
        this.$confirm(`确定要删除资产 “${row.name}” 吗？`, () => {
          this.$get('back/assets/delete', {
            params: {
              id: row.id
            }
          }).then(res => {
            if (res.isSuccess) {
              vue.tableData.remove(row);
              vue.$success('删除成功');
            }
          });
        });
      },
      // 模板导入点击事件
      onImportClick() {}
    }
  };
</script>
<style scoped></style>

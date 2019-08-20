<template>
  <div class="app-main">
    <div class="main-header">
      <div class="title">资产列表</div>
      <div class="buttons">
        <el-button type="primary" size="small" @click="onAddClick">新建</el-button>
        <el-button type="primary" size="small" @click="onImportClick">导入</el-button>
      </div>
    </div>
  </div>
</template>
<script>
  export default {
    name: 'AssetsIndex',
    created() {
      this.loadTableData();
    },
    data() {
      return {
        tableData: []
      };
    },
    methods: {
      loadTableData() {},
      onAddClick() {},
      onImportClick() {}
    }
  };
</script>
<style scoped></style>

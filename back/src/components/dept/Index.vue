<template>
  <div>
    <div class="main-header">
      <div class="title">部门管理</div>
    </div>
    <el-tree :props="props" :load="loadTreeData" lazy show-checkbox @check-change="handleCheckChange"> </el-tree>
  </div>
</template>
<script>
  export default {
    name: 'DeptIndex',
    created() {
      this.initTableData();
    },
    data() {
      return {
        tableData: []
      };
    },
    methods: {
      loadTreeData: function() {
        this.$http
          .get('back/dept/list', {
            params: {}
          })
          .then(res => {
            this.tableData = res.data;
          });
      }
    }
  };
</script>
<style></style>

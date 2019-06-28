<template>
  <div>
    <div class="main-header">
      <div class="title">成员管理</div>
    </div>

    <el-table :data="tableData" stripe :border="true" size="small">
      <el-table-column prop="name" label="姓名"></el-table-column>
      <el-table-column prop="position" label="职位" width="200"></el-table-column>
      <el-table-column prop="mobile" label="手机号" width="150"></el-table-column>
      <el-table-column prop="tel" label="办公电话" width="150"></el-table-column>
      <el-table-column prop="type" label="用户类型" width="150">
        <template slot-scope="scope">
          <span v-show="scope.row.type == 1" class="el-link el-link--danger">主管理员</span>
          <span v-show="scope.row.type == 2" class="el-link el-link--warning">子管理员</span>
          <span v-show="scope.row.type == 99">普通用户</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="75">
        <template slot-scope="scope">
          <el-button-group>
            <el-button type="success" size="mini">查看</el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
  export default {
    name: 'UserIndex',
    created() {
      this.loadTableData();
    },
    data() {
      return {
        tableData: []
      };
    },
    methods: {
      loadTableData: function() {
        this.$http
          .get('back/user/list', {
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

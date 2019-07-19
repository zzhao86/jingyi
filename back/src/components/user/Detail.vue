<template>
  <div class="app-main">
    <div class="main-header">
      <div class="title">用户详情</div>
      <div class="buttons">
        <el-button type="default" size="small" @click="$router.back()">返回</el-button>
      </div>
    </div>
    <el-form ref="form" :model="detailData" label-width="100px">
      <el-form-item label="姓名">
        <el-input v-model="detailData.name" readonly></el-input>
      </el-form-item>
      <el-form-item label="部门">
        <el-input v-model="detailData.deptNames" readonly></el-input>
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="detailData.mobile" readonly></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="detailData.tel" readonly></el-input>
      </el-form-item>
      <el-form-item label="职位">
        <el-input v-model="detailData.position" readonly></el-input>
      </el-form-item>
      <el-form-item label="类型">
        <el-radio-group v-model="detailData.type" disabled>
          <el-radio-button label="1">主管理员</el-radio-button>
          <el-radio-button label="2">子管理员</el-radio-button>
          <el-radio-button label="99">系统用户</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="头像">
        <el-avatar shape="square" :size="200" :src="detailData.avatar">
          <template default>
            <img src="../../../static/images/default-user.png" />
          </template>
        </el-avatar>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  export default {
    name: 'UserDetail',
    created() {
      const mode = this.$route.params.mode;
      const query = this.$route.query;
      if (query.id) {
        this.loadDetailData(query.id);
      }
    },
    data() {
      return {
        detailData: {}
      };
    },
    methods: {
      loadDetailData: function(id) {
        this.$http
          .get('/back/user/detail', {
            params: {
              id: id
            }
          })
          .then(res => {
            this.detailData = res.data;
          });
      }
    }
  };
</script>
<style>
  .el-avatar {
    background-color: #fafafa;
    border: solid 1px #fafafa;
  }
</style>

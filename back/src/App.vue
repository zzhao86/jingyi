<template>
  <div id="app" class="app-container">
    <div class="app-header">
      <div class="app-name">京仪物业管理系统</div>
      <div class="user-info">
        <div class="username">管理员</div>
      </div>
    </div>
    <div class="app-side">
      <el-menu :default-openeds="['3']" :router="true">
        <el-menu-item index="1" route="/notice"><i class="el-icon-bell"></i>通知公告</el-menu-item>
        <el-menu-item index="2" route="/assets"><i class="el-icon-mobile"></i>资产管理</el-menu-item>
        <el-submenu index="3">
          <template slot="title">
            <i class="el-icon-setting"></i>系统管理
          </template>
          <el-menu-item index="3-2" route="/user">成员管理</el-menu-item>
          <el-menu-item index="3-3" route="/setting">系统设置</el-menu-item>
        </el-submenu>
      </el-menu>
    </div>
    <div class="app-content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'App'
  };
</script>

<style scoped>
  
</style>

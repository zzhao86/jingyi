<template>
  <div id="app">
    <el-container class="app-container">
      <el-header class="header">
        <div class="app-name">京仪物业管理系统</div>
        <div class="user-info">
          <div class="username">管理员</div>
          <div class="corpname">美办公服务中心</div>
        </div>
      </el-header>
      <el-container>
        <el-aside class="left-side ">
          <el-menu :default-openeds="['3']">
            <el-menu-item index="1"><i class="el-icon-message"></i>通知公告</el-menu-item>
            <el-menu-item index="2"><i class="el-icon-message"></i>资产管理</el-menu-item>
            <el-submenu index="3">
              <template slot="title"><i class="el-icon-message"></i>系统管理</template>
              <el-menu-item-group>
                  <el-menu-item index="3-1">成员管理</el-menu-item>
                  <el-menu-item index="3-2">系统设置</el-menu-item>
              </el-menu-item-group>
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
  export default {
    name: 'App'
  };
</script>

<style>
  body {
    padding: 0;
    margin: 0;
  }

  #app {
    width: 100vw;
    height: 100vh;
    background-color: #fdfdfd;
  }

  .app-container {
    width: 100vw;
    height: 100vh;
  }
  .app-container .header {
    border-bottom: solid 1px #ddd;
    background-color: #1890ff;
    display: flex;
    color: rgba(255, 255, 255, 0.9);
  }
  .app-container .header .app-name {
    width: 200px;
    height: 60px;
    line-height: 60px;
    font-size: 20px;
  }

  .app-container .header .user-info {
    flex: 1;
    height: 60px;
    text-align: right;
    font-size: 14px;
  }
  .app-container .header .user-info .username,
  .app-container .header .user-info .corpname {
    height: 30px;
    line-height: 30px;
  }

  .app-container .left-side {
    width: 200px !important;
    border-right: solid 1px #e6e6e6;
    background-color: #f5f7fa;
  }
  .app-container .left-side .el-menu {
    border-right: none;
  }

  .main-header {
    margin-bottom: 20px;
    height: 40px;
    border-bottom: solid 1px #ddd;
    display: flex;
  }
  .main-header .title {
    font-size: 16px;
    font-weight: bold;
    color: #888;
    width: 300px;
  }
  .main-header .buttons {
    flex: 1;
    padding-right: 20px;
    padding-bottom: 10px;
    text-align: right;
  }

  .el-form {
    max-width: 800px;
  }
</style>

<template>
  <div id="app" class="app-container">
    <div class="app-header">
      <div class="app-name">京仪物业管理系统</div>
      <el-dropdown class="user-info" @command="userDropdownCommand" v-show="$global.user">
        <div>
          <el-avatar class="user-avatar" :src="avatar"></el-avatar>
          <div class="user-name">{{ name }}</div>
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="userCenter">用户中心</el-dropdown-item>
          <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <div class="app-content-container">
      <div class="app-side" v-show="$global.user">
        <el-menu :default-openeds="['3']" :router="true">
          <el-menu-item index="1" route="/notice"> <i class="el-icon-bell"></i>通知公告 </el-menu-item>
          <el-menu-item index="2" route="/assets"> <i class="el-icon-mobile"></i>资产管理 </el-menu-item>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-setting"></i>系统管理
            </template>
            <el-menu-item index="3-2" route="/user">用户管理</el-menu-item>
            <el-menu-item index="3-3" route="/settings">系统设置</el-menu-item>
          </el-submenu>
        </el-menu>
      </div>
      <div class="app-content">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'App',
    mounted() {
      this.loadUserData();
    },
    updated() {
      this.loadUserData();
    },
    data() {
      return {
        name: '',
        avatar: ''
      };
    },
    methods: {
      loadUserData: function() {
        if (!this.$global.user) {
          this.$http.get('back/user').then(res => {
            if (res.isSuccess) {
              this.$global.user = res.data;
              this.name = this.$global.user.name;
              this.avatar = this.$global.user.avatar;
            }
          });
        } else {
          this.name = this.$global.user.name;
          this.avatar = this.$global.user.avatar;
        }
      },
      userDropdownCommand: function(command) {
        switch (command) {
          case 'userCenter':
            break;
          case 'logout':
            this.onLogoutClick();
            break;
        }
      },
      onLogoutClick: function() {
        this.$confirm('确定要注销当前登录的用户吗？', () => {
          this.$http
            .post('logout')
            .then(res => {
              this.$global.user = null;
              this.$router.replace('/login');
            })
            .catch(err => {
              console.log(err);
            });
        });
      }
    }
  };
</script>

<style scoped></style>

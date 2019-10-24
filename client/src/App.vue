<template>
  <div id="app">
    <div class="not-dingtalk" v-if="!$global.isDingTalkClient && !$global.dev">
      <img class="logo" src="../static/images/dingtalk.svg" />
      <div>请在钉钉客户端中打开</div>
    </div>
    <mt-header fixed :title="$route.meta.title" v-show="showTitle">
      <mt-button icon="back" slot="left" @click="onGoBackClick">返回</mt-button>
    </mt-header>
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
  created(){
    
  },
  beforeUpdate(){
    this.showTitle = this.$route.path != '/';
  },
  date(){
    return{
      showTitle:false
    }
  },
  methods:{
    onGoBackClick(){
      this.$router.back(-1);
    }
  }
}
</script>

<style>
  body {
    padding: 0;
    margin: 0;
    overflow: hidden;
    background-color: #f3f3f3;
  }
  #app {
    width: 100vw;
    position: relative;
  }

  .not-dingtalk {
    margin-top: 100px;
    text-align: center;
  }
  .not-dingtalk .logo {
    width: 64px;
    height: 64px;
  }
  .container {
    padding: 10px;
    height: calc(100vh - 40px);
    padding-top: 40px;
    overflow-x: hidden;
    overflow-y: scroll;
    -webkit-overflow-scrolling: touch;
  }
  .list-container {
    overflow-y: auto;
    overflow-x: hidden;
    height: calc(100vh - 40px);
    padding-top: 40px;
    background-color: #f3f3f3;
    -webkit-overflow-scrolling: touch;
  }

  .list-item {
    width: 100vw;
    padding: 10px 0;
    background-color: #fff;
    margin-top: 10px;
    overflow: hidden;
  }
  
  .no-data{
    font-size: 20px;
    text-align: center;
    color: #999;
    height: 100vh;
    padding-top: 100px;
  }
  .mint-header{
    z-index: 999;
    background-color: #4b2937 !important;
  }
  .mint-header-title{
    font-size: 18px !important;
  }
</style>
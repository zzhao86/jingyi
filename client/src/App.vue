<template>
  <div id="app">
    <div class="not-dingtalk" v-if="!$global.isDingTalkClient && !$global.dev">
      <img class="logo" src="../static/images/dingtalk.svg" />
      <div>请在钉钉客户端中打开</div>
    </div>
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
  created(){

  }
}
</script>

<style>
  body {
    padding: 0;
    margin: 0;
    overflow: hidden;
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
</style>
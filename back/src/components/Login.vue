<template>
  <div class="app-main">
    <div id="loginContainer"></div>
  </div>
</template>
<script>
import "../assets/ddLogin";
export default {
  name: "Login",
  created() {
    var url = encodeURIComponent(this.$global.baseUrl + "/back/login/qrcode");
    var goto = encodeURIComponent(
      "https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid=appid&response_type=code&scope=snsapi_login&state=STATE&redirect_uri=" +
        url
    );
    var obj = DDLogin({
      id: "loginContainer",
      goto: goto,
      style: "border: none; background-color: #FFFFFF;",
      width: "210",
      height: "210"
    });
  },
  data() {
    return {};
  },
  methods: {}
};
</script>
<style scoped>
</style>

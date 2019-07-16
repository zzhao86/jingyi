<template>
  <div>
    <div class="login-bg"></div>
    <div class="login-container">
      <div class="title">扫码登录</div>
      <div id="loginContainer"></div>
    </div>
  </div>
</template>
<script>
  import '../assets/ddLogin';
  export default {
    name: 'Login',
    created() {
      const vue = this;
      var appid = 'dingoahwjazjqdmlt2gs5k';
      var redirect = encodeURIComponent(this.$global.baseUrl + 'account/login/dingtalk_qrcode');
      var url = 'https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid=' + appid + '&response_type=code&scope=snsapi_login&state=STATE&redirect_uri=';
      var goto = encodeURIComponent(url + redirect);
      this.$nextTick(function() {
        var obj = DDLogin({
          id: 'loginContainer',
          goto: goto,
          style: 'border: none; background-color: transparent;',
          width: '350',
          height: '300'
        });
      });

      var hanndleMessage = function(event) {
        var origin = event.origin;
        if (origin == 'https://login.dingtalk.com') {
          window.location.href = url + redirect + '&loginTmpCode=' + event.data;
        }
      };
      if (typeof window.addEventListener != 'undefined') {
        window.addEventListener('message', hanndleMessage, false);
      } else if (typeof window.attachEvent != 'undefined') {
        window.attachEvent('onmessage', hanndleMessage);
      }
    },
    data() {
      return {};
    },
    methods: {}
  };
</script>
<style scoped>
  .login-container {
    position: absolute;
    right: 300px;
    top: 50%;
    transform: translateY(-50%);
    width: 400px;
    height: 400px;
    background-color: #ffffffcf;
    border-radius: 20px;
    text-align: center;
  }
  .login-container .title {
    font-size: 20px;
    margin-top: 20px;
    text-align: center;
    font-family: '微软雅黑';
  }
  .login-bg {
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    background: url('../../static/images/login-bg.jpg') no-repeat center center;
    background-size: auto 100%;
    z-index: -1;
  }
</style>

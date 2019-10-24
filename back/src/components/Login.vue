<template>
  <div>
    <div class="login-bg"></div>
    <div class="login-container">
      <div class="login-tip-wrapper">
        <div class="login-tip">西什库31号物业系统管理后台</div>
      </div>
      <div class="login-wrapper">
        <div class="title">扫码登录</div>
        <div id="loginContainer"></div>
      </div>
    </div>
  </div>
</template>
<script>
  import '../assets/ddLogin';
  export default {
    name: 'Login',
    created() {
      const vue = this;
      if (vue.$global.user) {
        vue.$router.push('/');
        return;
      }
      this.onLogin();
    },
    data() {
      return {};
    },
    methods: {
      onLogin() {        
        var appid = 'dingoahwjazjqdmlt2gs5k';//开发        
        // var appid = 'dingoamhqztjyrjwtx7ekp';// 正式
        var redirect = encodeURIComponent(this.$global.baseUrl + 'account/login/dingtalk_qrcode?redirect=' + this.$global.redirect);
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
      }
    }
  };
</script>
<style scoped>
  .login-container {
    position: absolute;
    width: 1000px;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }
  .login-tip-wrapper {
    float: left;
    margin-top: 100px;
  }
  .login-tip-wrapper .login-tip {
    font-size: 36px;
    text-shadow: 0 0 2px #000;
    color: #fff;
  }
  .login-wrapper {
    float: right;
    width: 400px;
    height: 400px;
    background-color: rgba(255, 255, 255, 0.8);
    border-radius: 20px;
    text-align: center;
  }
  @media (max-width: 1200px) {
    .login-container {
      width: 768px;
    }
  }
  @media (max-width: 768px) {
    .login-container {
      width: 400px;
    }
    .login-tip-wrapper {
      display: none;
    }
  }
  .login-wrapper .title {
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

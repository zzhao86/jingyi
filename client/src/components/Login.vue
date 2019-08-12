<template>
  <!-- <div>{{ msg }}</div> -->
</template>
<script>
  export default {
    name: 'Login',
    created() {
      // const vue = this;
      // if (vue.$global.user) {
      //   vue.$router.push('/');
      //   return;
      // }
      this.onLogin();
    },
    data() {
      return {
        msg: ''
      };
    },
    methods: {
      onLogin() {
        const vue = this;
        // 免登
        vue.$dd.ready(function() {
          vue.$dd.runtime.permission.requestAuthCode({
            corpId: vue.$global.corpId,
            onSuccess: res => {
              vue.msg = res.code;
              vue
                .$get('account/login/dingtalk_corp', {
                  params: {
                    code: res.code
                  }
                })
                .then(res => {
                  vue.msg = JSON.stringify(res);
                  if (res.isSuccess) {
                    vue.$global.user = res.data;
                    vue.$router.push('/');
                  } else {
                    alert(`登录失败：${res.message}`);
                  }
                })
                .catch(err => {
                  alert(JSON.stringify(err));
                });
            },
            onFail: err => {
              alert(`授权失败，${JSON.stringify(err)}`);
            }
          });
        });
      }
    }
  };
</script>
<style scoped></style>

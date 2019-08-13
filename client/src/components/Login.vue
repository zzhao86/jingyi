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
          let interval = setInterval(() => {
            if (vue.$global.corpId && vue.$global.corpId == 'null') {
              clearInterval(interval);
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
                        let redirect = sessionStorage.getItem('REDIRECT') || '/';
                        vue.$router.push(redirect);
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
            }
          }, 100);
        });
      }
    }
  };
</script>
<style scoped></style>

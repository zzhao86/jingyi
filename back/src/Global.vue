<script>
  // 接口基础地址
  const baseUrl = 'http://192.168.0.8:5050/';
  // const baseUrl = 'http://132.56.179.69:5050'
  Object.defineProperties(this, {
    baseUrl: {
      get: () => {
        return baseUrl;
      }
    },
    user: {
      get: () => {
        let userSession = sessionStorage.getItem('JINGYI_BACK_USER');
        if (userSession) {
          return JSON.parse(userSession);
        } else {
          return null;
        }
      },
      set: obj => {
        sessionStorage.setItem('JINGYI_BACK_USER', JSON.stringify(obj));
      }
    },
    pageSize: {
      get: () => {
        if (localStorage.getItem('PAGE_SIZE')) {
          return parseInt(localStorage.getItem('PAGE_SIZE'));
        } else {
          return 10;
        }
      },
      set: val => {
        localStorage.setItem('PAGE_SIZE', val);
      }
    },
    redirect: {
      get: () => {
        let redirectSession = sessionStorage.getItem('REDIRECT');
        if (redirectSession) {
          return redirectSession;
        } else {
          return '';
        }
      },
      set: val => {
        if (val == '/login') {
          return;
        }
        let index = val.indexOf('/');
        if (index == 0) {
          val = val.substring(1);
        }
        sessionStorage.setItem('REDIRECT', val);
      }
    }
  });
  export default this;
</script>

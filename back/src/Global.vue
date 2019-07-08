<script>
  // 接口基础地址
  const baseUrl = 'http://192.168.0.220:5050/';
  Object.defineProperties(this, {
    baseUrl: {
      get: () => {
        return baseUrl;
      }
    },
    pageSize: {
      get: () => {
        if (localStorage.getItem('pageSize')) {
          return parseInt(localStorage.getItem('pageSize'));
        } else {
          return 10;
        }
      },
      set: val => {
        localStorage.setItem('pageSize', val);
      }
    }
  });
  export default this;
</script>

<script>
// 接口基础地址
const baseUrl = "http://192.168.0.8:5050/";
Object.defineProperties(this, {
  baseUrl: {
    get: () => {
      return baseUrl;
    }
  },
  user: {
    get: () => {
      let userSession = sessionStorage.getItem("JINGYI_BACK_USER");
      if (userSession) {
        return JSON.parse(userSession);
      } else {
        return null;
      }
    },
    set: obj => {
      sessionStorage.setItem("JINGYI_BACK_USER", JSON.stringify(obj));
    }
  },
  pageSize: {
    get: () => {
      if (localStorage.getItem("PAGE_SIZE")) {
        return parseInt(localStorage.getItem("PAGE_SIZE"));
      } else {
        return 10;
      }
    },
    set: val => {
      localStorage.setItem("PAGE_SIZE", val);
    }
  }
});
export default this;
</script>

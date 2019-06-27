import Vue from 'vue';
import App from './App';
import router from './router';

import axios from 'axios';
//钉钉jsapi
import * as dd from 'dingtalk-jsapi';
// Element UI
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

//全局变量
import Global from './Global.vue';

Vue.config.productionTip = false;
Vue.use(ElementUI);

axios.defaults.baseURL = Global.baseUrl;
axios.defaults.headers.get['Content-Type'] = 'application/x-www-form-urlencoded; charset=utf-8;';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded; charset=utf-8;';

/**
 * axios异步请求拦截处理
 */
axios.interceptors.request.use(
  function(config) {
    // vue.$vux.loading.show({
    //   text: 'Loading'
    // });
    return config;
  },
  function(error) {
    // vue.$vux.loading.hide();
    return Promise.reject(error);
  }
);

/**
 * axios异步响应拦截处理
 */
axios.interceptors.response.use(
  function(response) {
    // vue.$vux.loading.hide();
    if (response.status >= 200 && response.status < 300) {
      let data = response.data;
      if (data.isSuccess) {
        return Promise.resolve(data);
      } else {
        return Promise.reject(data);
      }
    } else {
      return Promise.reject(response);
    }
  },
  function(error) {
    // vue.$vux.loading.hide();
    return Promise.reject(error);
  }
);

new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  created() {
    // 钉钉开发设置
    Global.dev = process.env.NODE_ENV == 'development';
    Global.isDingTalkClient = dd.env.platform != 'notInDingTalk';

    Vue.prototype.$http = axios;
    Vue.prototype.$global = Global;
    Vue.prototype.$dd = dd;
    Vue.prototype.$success = function(message) {
      this.$message({
        message: message,
        type: 'success'
      });
    };
  },
  data() {
    return {
      ddConfig: {}
    };
  }
});

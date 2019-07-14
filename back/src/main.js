import Vue from "vue";
import App from "./App";
import router from "./router";

import axios from "axios";
//钉钉jsapi
import * as dd from "dingtalk-jsapi";
// Element UI
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";

//全局变量
import Global from "./Global.vue";

import "../static/css/main.css";

import tableAutoHeight from "./components/utils/directive/table-auto-height";

Vue.directive("auto-height", tableAutoHeight);

Vue.config.productionTip = false;
Vue.use(ElementUI);

axios.defaults.baseURL = Global.baseUrl;
axios.defaults.headers.get["Content-Type"] =
  "application/x-www-form-urlencoded; charset=utf-8;";
axios.defaults.headers.post["Content-Type"] =
  "application/x-www-form-urlencoded; charset=utf-8;";

/**
 * axios异步请求拦截处理
 */
var loadingInstance;
axios.interceptors.request.use(
  function(config) {
    config.withCredentials = true;
    loadingInstance = vue.$loading({
      lock: true,
      text: "加载中……",
      background: "rgba(255, 255, 2550, 0.5)",
      body: true
    });
    return config;
  },
  function(error) {
    loadingInstance.close();
    return Promise.reject(error);
  }
);

/**
 * axios异步响应拦截处理
 */
axios.interceptors.response.use(
  function(response) {
    loadingInstance.close();
    if (response.status >= 200 && response.status < 300) {
      let data = response.data;
      if (data.isSuccess) {
        return Promise.resolve(data);
      } else {
        if (data.code == 401) {
          vue.$router.replace("/login");
        } else {
          vue.$error(data.message);
        }
        return Promise.reject(data);
      }
    } else {
      return Promise.reject(response);
    }
  },
  function(error) {
    loadingInstance.close();
    return Promise.reject(error);
  }
);

var vue = new Vue({
  el: "#app",
  router,
  components: { App },
  template: "<App/>",
  created() {
    // 钉钉开发设置
    Global.dev = process.env.NODE_ENV == "development";
    Global.isDingTalkClient = dd.env.platform != "notInDingTalk";

    Vue.prototype.$http = axios;
    Vue.prototype.$global = Global;
    Vue.prototype.$dd = dd;
    Vue.prototype.$alert = function(message) {
      this.$msgbox({
        message: message,
        title: "提示",
        type: "info"
      });
    };
    Vue.prototype.$error = function(message) {
      this.$msgbox({
        message: message,
        title: "出错了",
        type: "error"
      });
    };
    Vue.prototype.$success = function(message) {
      this.$message({
        message: message,
        type: "success"
      });
    };
    Vue.prototype.$confirm = function(message, callback) {
      this.$msgbox({
        message: message,
        title: "确认提示",
        type: "warning",
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        showCancelButton: true,
        callback: function(action, instance) {
          if (action === "confirm") {
            callback();
          }
        }
      });
    };
  },
  data() {
    return {
      ddConfig: {}
    };
  }
});

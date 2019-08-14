import Vue from 'vue';
import App from './App';
import router from './router';

import axios from 'axios';
import * as dd from 'dingtalk-jsapi';
import 'font-awesome/css/font-awesome.min.css';

// Mint UI框架
import Mint from 'mint-ui';
import 'mint-ui/lib/style.css';
Vue.use(Mint);

//全局变量
import Global from './Global.vue';

Vue.config.productionTip = false;

/**
 * axios异步请求拦截处理
 */
axios.interceptors.request.use(
  function(config) {
    config.withCredentials = true;
    return config;
  },
  function(error) {
    return Promise.reject(error);
  }
);

/**
 * axios异步响应拦截处理
 */
axios.interceptors.response.use(
  function(response) {
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
    return Promise.reject(error);
  }
);

const vue = new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  created() {
    var vue = this;
    axios.defaults.baseURL = Global.baseUrl;
    axios.defaults.headers.get['Content-Type'] = 'application/x-www-form-urlencoded; charset=utf-8';

    // 钉钉开发设置
    Global.dev = process.env.NODE_ENV == 'development';
    Global.isDingTalkClient = dd.env.platform != 'notInDingTalk';

    Vue.prototype.$get = axios.get;
    Vue.prototype.$post = axios.post;
    Vue.prototype.$global = Global;
    Vue.prototype.$dd = dd;
    vue.$global.corpId = null;

    // 钉钉鉴权
    vue
      .$get('client/dingtalk/jsapi_config', {
        params: {
          url: location.href.substr(0, location.href.indexOf('#'))
        }
      })
      .then(res => {
        let data = res.data;
        vue.$global.corpId = data.corpId;
        dd.config({
          agentId: data.agentId, // 必填，微应用ID
          corpId: data.corpId, //必填，企业ID
          timeStamp: data.timeStamp, // 必填，生成签名的时间戳
          nonceStr: data.nonceStr, // 必填，生成签名的随机串
          signature: data.signature, // 必填，签名
          type: 0, //选填。0表示微应用的jsapi,1表示服务窗的jsapi；不填默认为0。该参数从dingtalk.js的0.8.3版本开始支持
          jsApiList: ['device.base.getUUID']
        }).catch(error => {
          alert(`获取钉钉配置失败：${JSON.stringify(error)}`);
        });
        dd.error(error => {
          alert(`钉钉鉴权失败: ${JSON.stringify(error)}`);
        });
        // dd.device.base.getUUID({
        //   onSuccess: function(data) {
        //     alert('success: ' + JSON.stringify(data));
        //   },
        //   onFail: function(err) {
        //     alert('fail: ' + JSON.stringify(err));
        //   }
        // });
      });
  },
  data() {
    return {
      ddConfig: {}
    };
  }
});

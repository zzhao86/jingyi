import Vue from 'vue';
import App from './App';
import router from './router';

import axios from 'axios';
import * as dd from 'dingtalk-jsapi';
import 'font-awesome/css/font-awesome.min.css';

//全局变量
import Global from './Global.vue';

Vue.config.productionTip = false;

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
    vue.$vux.loading.hide();
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
        return Promise.resolve(data.data);
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
    var self = this;
    axios.defaults.baseURL = Global.baseUrl;
    axios.defaults.headers.get['Content-Type'] = 'application/x-www-form-urlencoded; charset=utf-8';

    Vue.prototype.$http = axios;
    Vue.prototype.$global = Global;
    Vue.prototype.$dd = dd;

    // 钉钉鉴权
    self.$http
      .get('client/dingtalk/jsapi_config', {
        params: {
          // url: location.href
          url: 'http://192.168.0.218:5051'
        }
      })
      .then(res => {
        Global.corpId = res.corpId;
        // dd.config({
        //   agentId: res.agentId, // 必填，微应用ID
        //   corpId: res.corpId, //必填，企业ID
        //   timeStamp: res.timeStamp, // 必填，生成签名的时间戳
        //   nonceStr: res.nonceStr, // 必填，生成签名的随机串
        //   signature: res.signature, // 必填，签名
        //   type: 0, //选填。0表示微应用的jsapi,1表示服务窗的jsapi；不填默认为0。该参数从dingtalk.js的0.8.3版本开始支持
        //   jsApiList: ['device.base.getUUID']
        // });

        // 免登
        dd.ready(function() {
          dd.runtime.permission.requestAuthCode({
            corpId: res.corpId,
            onSuccess: res => {
              self.$http
                .get('client/dingtalk/userinfo_code', {
                  params: {
                    code: res.code
                  }
                })
                .then(res => {
                  alert(JSON.stringify(res));
                });
            },
            onFail: err => {
              alert(JSON.stringify(err));
            }
          });
        });
      });
  },
  data() {
    return {
      ddConfig: {}
    };
  }
});

<template>
  <div class="main">
    <!-- banner start -->
    <div class="swiper-container banner">
      <div class="swiper-wrapper">
        <div class="swiper-slide" v-for="(item, index) in banners" :key="index">
          <img class="banner-image" :src="item.image" alt />
        </div>
      </div>
      <div class="swiper-pagination"></div>
    </div>
    <!-- banner end -->

    <!-- notice start -->
    <div class="notice-box">
      <i class="icon fa fa-volume-up"></i>
      <div class="swiper-container notice-content">
        <div class="swiper-wrapper">
          <div
            class="swiper-slide notice-text"
            v-for="(item, index) in notices"
            :key="index"
            @click="
          onOpenNoticeClick(item)"
          >
            {{ item.title }}
          </div>
        </div>
      </div>
      <i class="next fa fa-angle-right" @click="onOpenNoticeListClick"></i>
    </div>
    <!-- notice end -->

    <!-- module start -->
    <div class="module-box">
      <div class="swiper-container">
        <div class="swiper-wrapper">
          <div class="swiper-slide" v-for="(item, index) in modules" :key="index">
            <div class="module" v-bind:style="{backgroundImage: 'url(' + item.background + ')'}">
              <img class="icon" :src="item.icon" alt />
              <div class="name">{{ item.name }}</div>
            </div>
          </div>
        </div>
      </div>
      <div class="module-pointer">
        <i class="fa fa-caret-up pointer"></i>
      </div>
    </div>
    <!-- module end -->

    <!-- item start -->
    <div class="item-container">
      <div class="header">{{current.module.name}}</div>
      <div class="item-box clearfix">
        <button class="item" v-for="(item, index) in current.items" :key="index" @click="onOpenAppClick(item)">
          <div class="icon" v-bind:style="{backgroundImage: 'url(' + item.icon + ')'}"></div>
          <div class="name">{{ item.name }}</div>
        </button>
      </div>
    </div>
    <!-- item end -->
  </div>
</template>
<script>
  import Swiper from 'swiper';
  import 'swiper/dist/css/swiper.css';

  export default {
    name: 'Home',
    created() {
      const vue = this;
      this.$nextTick(function() {
        // 初始化数据
        this.initData();

        // banner swiper
        vue.bannerSwiper = new Swiper('.banner', {
          pagination: {
            el: '.swiper-pagination'
          },
          init: false,
          loop: true,
          speed: 1000,
          autoplay: {
            delay: 3000
          }
        });

        // notice swiper
        vue.noticeSwiper = new Swiper('.notice-content', {
          direction: 'vertical',
          init: false,
          loop: true,
          allowTouchMove: false,
          speed: 1000,
          autoplay: {
            delay: 5000
          }
        });

        // module swiper
        vue.moduleSwiper = new Swiper('.module-box .swiper-container', {
          centeredSlides: true,
          slidesPerView: 3,
          init: false,
          loop: true,
          slideToClickedSlide: true,
          on: {
            slideChange: function() {
              var index = this.realIndex;
              vue.current.module = vue.modules[index];
              vue.current.items = vue.items.filter(item => item.module == vue.current.module.id);
            }
          }
        });
      });
    },
    data() {
      return {
        bannerSwiper: null,
        noticeSwiper: null,
        moduleSwiper: null,
        banners: [],
        notices: [],
        modules: [],
        items: [],
        current: {
          module: {},
          items: []
        }
      };
    },
    methods: {
      initData: function() {
        const vue = this;
        // 获取banner列表数据
        vue.$http.get('static/data/banners.json').then(res => {
          vue.banners = res;
          vue.$nextTick(function() {
            vue.bannerSwiper.init();
          });
        });

        // 获取notice列表数据
        vue.$http.get('static/data/notices.json').then(res => {
          vue.notices = res;
          vue.$nextTick(function() {
            vue.noticeSwiper.init();
          });
        });

        // 获取module列表数据
        vue.$http.get('static/data/modules.json').then(res => {
          vue.modules = res;
          if (vue.modules && vue.modules.length > 0) {
            vue.current.module = vue.modules[0];
          }
          vue.$nextTick(function() {
            vue.moduleSwiper.init();
          });
        });

        // 获取Item列表数据
        vue.$http.get('static/data/items.json').then(res => {
          vue.items = res;
          if (res && res.length > 0) {
            vue.current.items = res.filter(item => item.module == vue.current.module.id);
          }
        });
      },
      onOpenNoticeListClick: function() {
        if (this.$dd.ios || this.$dd.android) {
          this.$dd.biz.util.openLink({
            url: 'https://app.dingtalk.com/blackboard/noticeList.html?showmenu=false&dd_progress=false&dd_share=false&corpid=' + this.$global.corpId
          });
        } else {
          location.href = 'https://app.dingtalk.com/index.html?corpId=' + this.$global.corpId;
        }
      },
      onOpenNoticeClick: function(item) {
        if (this.$dd.ios || this.$dd.android) {
          this.$dd.biz.util.openLink({
            url: item.url
          });
        } else {
          location.href = item.url;
        }
      },
      onOpenAppClick: function(item) {
        const vue = this;
        if (!vue.$dd.ios && !vue.$dd.android) {
          alert('应用只能在手机钉钉中打开');
          return;
        }

        vue.$dd.ready(function() {
          if (item.agentId) {
            vue.$dd.biz.microApp.openApp({
              agentId: item.agentId
            });
          } else if (item.homepageLink) {
            vue.$dd.biz.util.openLink({
              url: item.homepageLink
            });
          } else {
            vue.$dd.device.notification.toast({
              icon: 'error',
              text: '敬请期待…',
              duration: 3
            });
          }
        });
      }
    }
  };
</script>
<style scoped>
  .main {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    overflow-y: auto;
    background: #d8e6e9 url('../../static/images/main-bg.jpg') no-repeat;
    background-position-y: bottom;
    background-size: 100%;
    padding-bottom: 50px;
  }

  .banner {
    width: 100%;
  }

  .banner .banner-image {
    width: 100%;
    height: 175px;
  }

  @supports ((--a: 0)) {
    .banner .banner-image {
      height: calc(175 * (100vw / 375));
    }
  }

  .notice-box {
    margin-top: -4px;
    background-color: #3296fa;
    position: relative;
    color: #fff;
  }

  .notice-box .icon,
  .notice-box .next {
    width: 50px;
    height: 40px;
    line-height: 40px;
    text-align: center;
    position: relative;
    font-size: 20px;
  }

  .notice-box .notice-content {
    position: absolute;
    height: 40px;
    line-height: 40px;
    left: 50px;
    right: 50px;
    top: 0;
  }

  .notice-box .notice-text {
    width: 100%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .notice-box .next {
    position: absolute;
    right: 0;
  }

  .module-box {
    position: relative;
    margin: 0 5px;
  }

  .module-box .module {
    margin: 10px 5px;
    height: 90px;
    background-color: #fff;
    background-size: 100%;
    text-align: center;
    position: relative;
  }

  .module-box .icon {
    height: 40px;
    margin-top: 15px;
    opacity: 0.6;
  }

  .module-box .name {
    color: #fff;
    text-shadow: 0 0 2px #000;
  }

  .module-pointer {
    text-align: center;
    margin-top: -10px;
    position: relative;
    opacity: 0.2;
  }

  .module-pointer::after {
    content: '';
    position: absolute;
    left: 5px;
    right: 5px;
    top: 24px;
    height: 1px;
    background-color: #000;
  }

  .module-pointer .pointer {
    color: #000;
    font-size: 30px;
  }

  .item-container {
    padding: 0 10px;
  }

  .item-container .header {
    position: relative;
    padding-left: 20px;
    margin-bottom: 10px;
    color: #666;
    font-weight: bold;
  }

  .item-container .header::before {
    content: '';
    position: absolute;
    left: 15px;
    top: 50%;
    transform: translateY(-50%);
    height: 16px;
    border-left: solid 3px #2660ff;
  }

  /* .item-container .header::after {
    content: '';
    position: absolute;
    left: 20px;
    bottom: -5px;
    width: 100px;
    border: solid 1px #2660ff;
  } */

  .item-container .item-box {
    width: 100%;
  }

  .item-container .item {
    position: relative;
    float: left;
    width: 25%;
    height: 90px;
    border: none;
    background-color: transparent;
  }

  .item-container .item:hover,
  .item-container .item:active {
    background-color: transparent;
  }

  .item-container .icon {
    margin: auto;
    width: 50px;
    height: 50px;
    background-size: 50px;
    background-repeat: no-repeat;
    background-position: center;
  }

  .item-container .name {
    color: #666;
    font-size: 13px;
    text-align: center;
    text-shadow: 0 0 3px #fff;
  }

  .clearfix {
    content: '';
    display: table;
    clear: both;
  }
</style>

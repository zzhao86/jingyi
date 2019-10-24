import Vue from 'vue';
import Router from 'vue-router';
import Global from '@/Global';

import Login from '@/components/Login';
import Home from '@/components/Home';
import NoticeIndex from '@/components/notice/Index';
import NoticeDetail from '@/components/notice/Detail';
import NoticeReadList from '@/components/notice/ReadList';
import NoticeReply from '@/components/notice/Reply';
import AssetsIndex from '@/components/assets/Index';
import AssetsDetail from '@/components/assets/Detail'

Vue.use(Router);

const router = new Router({
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/',
      name: 'Home',
      component: Home,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/notice',
      name: 'NoticeIndex',
      component: NoticeIndex,
      meta: {
        title:'公告列表',
        requireAuth: true
      }
    },
    {
      path: '/notice/detail',
      name: 'NoticeDetail',
      component: NoticeDetail,
      meta: {
        title:'公告详情',
        requireAuth: true
      }
    },
    {
      path: '/notice/readlist',
      name: 'NoticeReadList',
      component: NoticeReadList,
      meta: {
        title:'公告读取列表',
        requireAuth: true
      }
    },
    {
      path: '/notice/reply',
      name: 'NoticeReply',
      component: NoticeReply,
      meta: {
        title:'公告回复',
        requireAuth: true
      }
    },
    {
      path: '/assets',
      name: 'AssetsIndex',
      component: AssetsIndex,
      meta: {
        title:'资源管理',
        requireAuth: true
      }
    },
    {
      path: '/assets/detail',
      name: 'AssetsDetail',
      component: AssetsDetail,
      meta: {
        title:'资源详情',
        requireAuth: true
      }
    }
  ]
});
router.beforeEach((to, from, next) => {
  const requireAuth = to.meta.requireAuth;
  if (!requireAuth) {
    next();
  } else {
    if (Global.user) {
      next();
    } else {
      sessionStorage.setItem('REDIRECT', to.fullPath);
      next({
        path: '/login',
        query: { redirect: escape(to.fullPath) } // 将跳转的路由path作为参数，登录成功后跳转到该路由
      });
    }
  }
});
export default router;

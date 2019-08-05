import Vue from 'vue';
import Router from 'vue-router';
import Global from '@/Global';

import Login from '@/components/Login';
import Home from '@/components/Home';
import NoticeIndex from '@/components/notice/Index';
import NoticeDetail from '@/components/notice/Detail';

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
        // requireAuth: true
      }
    },
    {
      path: '/notice',
      name: 'NoticeIndex',
      component: NoticeIndex,
      meta: {
        // requireAuth: true
      }
    },
    {
      path: '/notice/detail',
      name: 'NoticeDetail',
      component: NoticeDetail,
      meta: {
        // requireAuth: true
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
      next({
        path: '/login',
        query: { redirect: escape(to.fullPath) } // 将跳转的路由path作为参数，登录成功后跳转到该路由
      });
    }
  }
});
export default router;

import Vue from "vue";
import Router from "vue-router";
import Home from "@/components/Home";
import Login from "@/components/Login";
import DingtalkSettings from "@/components/DingtalkSettings";
import DeptIndex from "@/components/dept/index";
import UserIndex from "@/components/user/index";
import UserDetail from "@/components/user/Detail";

Vue.use(Router);

const router = new Router({
  routes: [
    {
      path: "/",
      name: "Home",
      component: Home,
      meta: {
        requireAuth: false
      }
    },
    {
      path: "/login",
      name: "Login",
      component: Login,
      meta: {
        requireAuth: false
      }
    },
    {
      path: "/dingtalk/settings",
      name: "DingtalkSettings",
      component: DingtalkSettings
    },
    {
      path: "/dept",
      name: "DeptIndex",
      component: DeptIndex
    },
    {
      path: "/user",
      name: "UserIndex",
      component: UserIndex
    },
    {
      path: "/user/detail/:mode",
      name: "UserDetail",
      component: UserDetail
    }
  ]
});
router.beforeEach((to, from, next) => {
  const requireAuth = to.meta.requireAuth;
  if (requireAuth == false) {
    next();
  } else {
    if (sessionStorage.getItem("JINGYI_BACK_USER")) {
      next();
    } else {
      next({
        path: "/login",
        query: { redirect: escape(to.fullPath) } // 将跳转的路由path作为参数，登录成功后跳转到该路由
      });
    }
  }
});
export default router;

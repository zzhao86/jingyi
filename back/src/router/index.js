import Vue from "vue";
import Router from "vue-router";
import Home from "@/components/Home";
import Login from "@/components/Login";
import NoticeIndex from "@/components/notice/Index";
import NoticeDetail from "@/components/notice/Detail";
import NoticeReply from "@/components/notice/Reply";
import NoticeReplyViewer from "@/components/notice/Viewer";
import SettingsIndex from "@/components/settings/Index";
import UserIndex from "@/components/user/Index";
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
      path: "/notice",
      name: "NoticeIndex",
      component: NoticeIndex
    },
    {
      path: "/notice/detail/:mode",
      name: "NoticeDetail",
      component: NoticeDetail
    },
    {
      path: "/notice/reply",
      name: "NoticeReply",
      component: NoticeReply
    },
    {
      path: "/notice/viewer",
      name: "NoticeReplyViewer",
      component: NoticeReplyViewer
    },
    {
      path: "/settings",
      name: "SettingsIndex",
      component: SettingsIndex
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
// router.beforeEach((to, from, next) => {
//   const requireAuth = to.meta.requireAuth;
//   if (requireAuth == false) {
//     next();
//   } else {
//     if (sessionStorage.getItem("JINGYI_BACK_USER")) {
//       next();
//     } else {
//       next({
//         path: "/login",
//         query: { redirect: escape(to.fullPath) } // 将跳转的路由path作为参数，登录成功后跳转到该路由
//       });
//     }
//   }
// });
export default router;

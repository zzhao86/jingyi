import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import DingtalkSettings from '@/components/DingtalkSettings'
import DeptIndex from '@/components/dept/index'
import UserIndex from '@/components/user/index'
import UserDetail from '@/components/user/Detail'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/dingtalk/settings',
      name: 'DingtalkSettings',
      component: DingtalkSettings
    },
    {
      path: '/dept',
      name: 'DeptIndex',
      component: DeptIndex
    },
    {
      path: '/user',
      name: 'UserIndex',
      component: UserIndex
    },
    {
      path: '/user/detail/:mode',
      name: 'UserDetail',
      component: UserDetail
    }
  ]
})

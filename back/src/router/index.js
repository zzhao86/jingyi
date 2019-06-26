import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import DingtalkSettings from '@/components/DingtalkSettings'

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
    }
  ]
})

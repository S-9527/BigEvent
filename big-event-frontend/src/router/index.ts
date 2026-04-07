import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import Login from '@/views/Login.vue'
import Layout from '@/views/Layout.vue'
import ArticleCategory from '@/views/article/ArticleCategory.vue'
import ArticleManage from '@/views/article/ArticleManage.vue'
import UserInfo from '@/views/user/UserInfo.vue'
import UserAvatar from '@/views/user/UserAvatar.vue'
import UserResetPassword from '@/views/user/UserResetPassword.vue'
import { useTokenStore } from '@/stores/token'

const routes: RouteRecordRaw[] = [
  { path: '/login', component: Login },
  {
    path: '/',
    redirect: '/article/manage',
    component: Layout,
    children: [
      { path: '/article/category', component: ArticleCategory },
      { path: '/article/manage', component: ArticleManage },
      { path: '/user/info', component: UserInfo },
      { path: '/user/avatar', component: UserAvatar },
      { path: '/user/resetPassword', component: UserResetPassword },
    ]
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, _from, next) => {
  const tokenStore = useTokenStore()

  // 如果访问登录页且已有 token，直接跳转到首页
  if (to.path === '/login' && tokenStore.token) {
    next('/')
    return
  }

  // 如果访问需要认证的页面且没有 token，跳转到登录页
  if (to.path !== '/login' && !tokenStore.token) {
    next('/login')
    return
  }

  next()
})

export default router

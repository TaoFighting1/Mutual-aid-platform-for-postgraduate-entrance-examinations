import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/home',
      name: 'home',
      component: () => import('../views/HomeView.vue')
    },
    {
      path: '/demo1',
      name: 'demo1',
      component: () => import('../views/Demo1View.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue')
    },
    {
      path: '/admin/page',
      name: 'admin-page',
      component: () => import('../views/AdminPageView.vue')
    },
    {
      path: '/admin/detail',
      name: 'admin-detail',
      component: () => import('../views/AdminDetailView.vue')
    }
  ]
})

export default router

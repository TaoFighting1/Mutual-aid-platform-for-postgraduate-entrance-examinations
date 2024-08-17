import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    proxy: {
      // url请求 以 /api 开始，就进行代理请求
      '/api': {
        // 目标API服务器地址
        target: 'http://localhost:8081',
        //是否跨域
        changeOrigin: true,
        //修改请求路径，去掉'/api'前缀
        rewrite: (path) => path.replace(/^\/api/, '')
      },
      // 图片代理方式解决跨域问题
      '/image': {
        // 目标API服务器地址
        target: 'http://localhost:8081',
        //是否跨域
        changeOrigin: true,
      },
      // 图片代理方式解决跨域问题
      '/video': {
        // 目标API服务器地址
        target: 'http://localhost:8081',
        //是否跨域
        changeOrigin: true,
      }
    }
  }
})

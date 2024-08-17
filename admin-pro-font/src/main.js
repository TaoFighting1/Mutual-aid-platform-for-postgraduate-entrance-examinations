// import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
//导入ElementPlus组件
import ElementPlus from 'element-plus'
//引入样式
import 'element-plus/dist/index.css'
//
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

import App from './App.vue'
import router from './router'

import piniaPlugin from 'pinia-plugin-persist'

const app = createApp(App)

const pinia = createPinia()
//使用pinia使用持久化
pinia.use(piniaPlugin)
//vue使用持久化
app.use(pinia)
app.use(router)
//使用elementPlus组件
app.use(ElementPlus)
//全局注册图标库
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    //图标注册成全局组件，组件名称就是图标名称
    app.component(key, component)
}
app.mount('#app')

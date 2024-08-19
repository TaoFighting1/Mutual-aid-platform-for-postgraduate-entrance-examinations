 <!--
    * @FileDescription: 该文件的描述信息
    * @Author: 作者信息
    * @Date: 文件创建时间
    * @LastEditors: 最后更新作者
    * @LastEditTime: 最后更新时间
 -->
 

<script setup>
import { reactive } from "vue";
import { useRouter } from "vue-router";
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import { useAdminStore } from "@/stores/admin";
// import { Lock, User } from "@element-plus/icons-vue";

const store = useAdminStore();
// 不显示左边菜单栏、头、脚等，url路径有哪些，需要配置
const noMenu = ["/login", "/register"];
const router = useRouter();
const dataInfo = reactive({
  showMenu: true,
  defaultOpen: ["1", "2"],
  currentPath: "/"
});
//全局前置路由守卫，初始化的时候调用，每次路由切换之前被调用
router.beforeEach((to, from, next) => {
  if (to.path == "/login") {
    // 如果路径是 /login 则正常执行
    next();
  } else {
    // 如果不是 /login，判断是否有 token
    if (!store.getToken || store.getToken == "") {
      // 如果没有，则跳至登录页面
      next({ path: "/login" }); //相当于 router.push({path: '/login'})
    } else {
      // 否则继续执行
      next();
    }
  }
  dataInfo.showMenu = !noMenu.includes(to.path);
  dataInfo.currentPath = to.path;
});
</script>

<template>
  <div class="layout">
    <!-- 左边菜单栏、顶部、底部-->
    <el-container v-if="dataInfo.showMenu" class="container">
      <!-- 左边菜单栏 -->
      <el-aside class="aside">
        <div class="head">
          <div>
            <img src="/favicon.ico" alt="logo" />
            <span>admin</span>
          </div>
        </div>
        <div class="line" />
        <el-menu
          background-color="#222832"
          text-color="#fff"
          :router="true"
          :default-openeds="dataInfo.defaultOpen"
          :default-active="dataInfo.currentPath"
        >
          <el-sub-menu index="1">
            <template #title>
              <span>首页</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/home">
                <el-icon>
                  <Odometer />
                </el-icon>首页
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index="2">
            <template #title>
              <span>用户管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/admin/page">
                <el-icon>
                  <Picture />
                </el-icon>管理员列表
              </el-menu-item>
              <el-menu-item index="/merchants">
                <el-icon>
                  <Sell />
                </el-icon>商家列表
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index="3">
            <template #title>
              <span>AI管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/ai/question">
                <el-icon>
                  <Menu />
                </el-icon>AI问答管理
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index="4">
            <template #title>
              <span>系统管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/account">
                <el-icon>
                  <Lock />
                </el-icon>个人信息管理
              </el-menu-item>
              <el-menu-item index="/invite">
                <el-icon>
                  <User />
                </el-icon>添加管理员
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      <el-container class="content">
        <!-- Header 组件-->
        <Header />
        <!-- 变化的内部部分 -->
        <div class="main">
          <!-- 显示菜单功能对应路由的 数据区 -->
          <router-view />
        </div>
        <!-- Footer 组件-->
        <Footer />
      </el-container>
    </el-container>
    <!-- 没有 菜单、顶部、底部 ，例如：登录、注册 -->
    <el-container v-else class="container">
      <!--显示路由，除了菜单的功能之外，例如：登录、注册等等-->
      <router-view />
    </el-container>
  </div>
</template>

<style scoped>
.layout {
  min-height: 100vh;
  background-color: #ffffff;
}

.container {
  height: 100vh;
}

.aside {
  width: 200px !important;
  background-color: #222832;
}

.head {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50px;
}

.head > div {
  display: flex;
  align-items: center;
}

.head img {
  width: 50px;
  height: 50px;
  margin-right: 10px;
}

.head span {
  font-size: 20px;
  color: #ffffff;
}

.line {
  border-top: 1px solid hsla(0, 0%, 100%, 0.05);
  border-bottom: 1px solid rgba(0, 0, 0, 0.2);
}

.content {
  display: flex;
  flex-direction: column;
  max-height: 100vh;
  overflow: hidden;
}

.main {
  height: calc(100vh - 100px);
  overflow: auto;
  padding: 10px;
}
</style>

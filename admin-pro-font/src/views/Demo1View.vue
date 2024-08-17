<template>
  <div>
    <el-container>
      <!-- 上面 -->
      <el-header>
        <div class="grid-container" style="height: 40px;"></div>
      </el-header>
      <!-- 下面 -->
      <el-container>
        <el-aside>
          <div class="ep-bg3" style="height: 150px;"></div>
        </el-aside>
        <el-container>
          <el-main>
            <div class="ep-bg2" style="height: 80px;">main</div>
          </el-main>
          <el-footer class="ep-bg1" style="height: 40px;">Footer</el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>
<style scoped>
.grid-container {
  background-color: black;
  height: 50px;
}
.ep-bg1 {
  background-color: red;
}
.ep-bg2 {
  background-color: green;
}
.ep-bg3 {
  background-color: yellow;
}
</style>
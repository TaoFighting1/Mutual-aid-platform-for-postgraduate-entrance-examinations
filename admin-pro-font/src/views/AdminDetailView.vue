<script setup>
import { useRoute, useRouter } from "vue-router";
import { onMounted } from "vue";
import { findById } from "@/api/admin";
import { ref } from "vue";

//route获取参数用，router传参用
const route = useRoute();
const router = useRouter();
const dataDetail = ref({});
onMounted(() => {
  //获取路由query传的参数
  const id = route.query.id;
  console.log(id); // 打印出id参数值
  findById(id).then(res => {
    console.log(res.data);
    dataDetail.value = res.data;
  });
});
const handleBackUp = () => {
  router.back();
};
</script>

<template>
  <div>
    <div>
      管理员用户名:
      <el-input v-model="dataDetail.username" readonly />
    </div>
    <div>
      管理员头像:
      <el-image style="width: 100px;height: 100px" :src="dataDetail.imagePath" />
    </div>
    <div>创建时间</div>
    <div>
      <el-date-picker
        v-model="dataDetail.createTime"
        type="datetime"
        placeholder="Select date and time"
        value-format="YYYY-MM-DD HH:mm:ss"
        format="YYYY-MM-DD HH:mm:ss"
        readonly
      />
    </div>
    <el-button color="#626aef" :dark="isDark" @click="handleBackUp">返回</el-button>
  </div>
</template>

<style scoped>
</style>
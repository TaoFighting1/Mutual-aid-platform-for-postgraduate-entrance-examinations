<script setup>
import { reactive, ref } from "vue";
import { login } from "@/api/admin";

import { useAdminStore } from "@/stores/admin";
import { useRouter } from "vue-router";

//获取adminStore对象
const adminStore = useAdminStore();
//获取
const router = useRouter();

const ruleForm = reactive({
  username: "",
  password: ""
});
const rules = reactive({
  username: [
    { required: true, message: "请输入用户名", trigger: "blur" },
    { min: 3, max: 50, message: "用户名的长度是 3 to 50", trigger: "blur" }
  ],
  password: [
    { required: true, message: "请输入密码", trigger: "blur" },
    { min: 3, max: 20, message: "密码的长度是 3 to 20", trigger: "blur" }
  ]
});

//获取ELform的DOM对象
const ruleFormRef = ref();
const submitForm = formEl => {
  if (!formEl) return;
  formEl.validate((valid, fields) => {
    if (valid) {
      console.log("submit!");
      login(ruleForm).then(res => {
        console.log(`output->res`, res);
        adminStore.saveToken(res.message);
        adminStore.saveAdmin(res.data);
        //跳转
        router.push({
          path: "/home"
        });
      });
    } else {
      console.log("error submit!", fields);
    }
  });
};
</script>

<template>
  <el-form
    ref="ruleFormRef"
    style="max-width: 600px"
    :model="ruleForm"
    :rules="rules"
    label-width="auto"
    status-icon
  >
    <el-form-item label="用户名" prop="username">
      <el-input v-model="ruleForm.username" />
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="ruleForm.password" type="password" show-password />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm(ruleFormRef)">登录</el-button>
    </el-form-item>
  </el-form>
</template>

<style scoped>
</style>
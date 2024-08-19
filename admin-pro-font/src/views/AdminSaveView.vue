<script setup>
import { ref, onMounted, reactive } from "vue";
import { useRoute, useRouter } from "vue-router";
import { findById, save } from "@/api/admin";
import { useAdminStore } from "@/stores/admin";
import { ElMessageBox, ElMessage } from "element-plus";

const adminStore = useAdminStore();
const route = useRoute();
const router = useRouter();
//设置请求头token和对应的值
const headers = {
  token: adminStore.getToken
};
// 数据对象
const ruleForm = reactive({});
// 校验规则
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

//钩子函数获取id值
onMounted(() => {
  ruleForm.id = route.query.id;
  console.log(`AdminSaveView->id `, ruleForm.id); // 打印出id值，在控制台中可以看到
  // 判断变量存不存在，存在就返回true
  if (ruleForm.id) {
    //更新
    findById(ruleForm.id).then(res => {
      console.log(`AdminSaveView->res`, res);
      ruleForm.username = res.data.username;
      ruleForm.imagePath = res.data.imagePath;
      ruleForm.id = res.data.id;
    });
  }
});

const handleSave = formEl => {
  console.log("enter");
  console.log(formEl);
  if (!formEl) return;
  formEl.validate((valid, fields) => {
    if (valid) {
      console.log("submit!");
      console.log(`ruleForm `, ruleForm);
      // 新增和修改请求后端
      save(ruleForm).then(res => {
        //成功之后跳转AdminPageView页面
        ElMessage({
          message: res.message,
          type: "success"
        });
        router.push({
          path: "/admin/page"
        });
      });
    } else {
      console.log("error submit!", fields);
    }
  });
};
// 返回事件的回调函数
const handleBackUp = () => {
  router.back();
};

const handleAvatarSuccess = res => {
  console.log("success->response", res);
  //   if (response.state > 0) {
  //     //上传失败
  //   } else if (response.state == -1) {
  //     //登录过期或者未登录
  //   } else {
  //     //上传图片成功
  //   }

  if (res.state > 0) {
    console.log(res.message);
    ElMessage.error(res.message || "系统出错");
  } else if (res.state === -1) {
    // 未登录或者登录过期
    // to re-login
    ElMessageBox.alert("登录已经过期，请重新登录", "登录过期", {
      confirmButtonText: "重新登录",
      callback: () => {
        //跳转登录页面
        window.location.href = "/login";
      }
    });
  } else {
    //成功 正确数据
    ruleForm.imagePath = res.data;
  }
};
// 上传图片前回调数据
const handlebeforeAvatarUpload = rawFile => {
  if (
    !(
      rawFile.type == "image/jpeg" ||
      rawFile.type == "image/jpg" ||
      rawFile.type == "image/png"
    )
  ) {
    ElMessage.error("Avatar picture must be jpeg、jpg、png format!");
    return false;
  } else if (rawFile.size / 1024 / 1024 > 50) {
    ElMessage.error("Avatar picture size can not exceed 50MB!");
    return false;
  }
  // 放行
  return true;
};
const ruleFormRef = ref("");
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
    <el-form-item label="密码" prop="password" v-if="!ruleForm.id">
      <el-input v-model="ruleForm.password" type="password" show-password />
    </el-form-item>

    <el-form-item label="头像">
      <!-- 设置headers 目的是请求头上加入token，防止上传无登录 -->
      <!-- 上传文件的action是他自己的axios请求，所以需要设置token -->
      <!-- name是上传文件的参数名称 -->
      <el-upload
        class="avatar-uploader"
        action="/api/admin/upload/image"
        name="imageFile"
        :headers="headers"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="handlebeforeAvatarUpload"
      >
        <!-- 上传图片成功之后的回显 -->
        <img v-if="ruleForm.imagePath" :src="ruleForm.imagePath" class="avatar" />
        <el-icon v-else class="avatar-uploader-icon">
          <Plus />
        </el-icon>
      </el-upload>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="handleSave(ruleFormRef)">保存</el-button>
      <el-button type="primary" @click="handleBackUp">返回</el-button>
    </el-form-item>
  </el-form>
</template>

<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>
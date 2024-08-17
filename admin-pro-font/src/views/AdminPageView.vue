<script setup>
import { reactive, ref, onMounted } from "vue";
import { page } from "@/api/admin";
import { selectGroupKey } from "element-plus";
import { useRouter } from "vue-router";

const router = useRouter();

//分页查询结果数据的总条数
const total = ref(0);
const queryPageParams = reactive({
  username: "",
  pageNo: 1,
  pageSize: 5
});
//vue挂载完成之后执行onMounted钩子函数
onMounted(() => {
  queryPage();
});
const handleQueryPage = () => {
  queryPage();
};

const queryPage = () => {
  page(queryPageParams).then(res => {
    tableData.value = res.data.records;
    total.value = res.data.total;
  });
};

const tableData = ref([
  {
    username: "daa",
    imagePath: "",
    updateTime: "2022",
    createPath: "324"
  }
]);
//修改每页数量的回调函数
const handleSizeChange = sizeValue => {
  console.log(sizeValue);
  queryPageParams.pageSize = sizeValue;
  queryPage();
};

const handleCurrentChange = sizeValue => {
  console.log(sizeValue);
  queryPageParams.pageNo = sizeValue;
  queryPage();
};

const handleDetail = id => {
  router.push({
    path: "/admin/detail",
    // query方式传参
    query: { id }
  });
};
const handleEdit = () => {
  router.push({
    path: "/admin/detail",
    // query方式传参
    query: { id }
  });
};
const handleDelete = () => {
  router.push({
    path: "/admin/detail",
    // query方式传参
    query: { id }
  });
};
</script>

<template>
  <div>
    <label>用户名:</label>
    <el-input
      id="username"
      v-model="queryPageParams.username"
      placeholder="请输入查询内容"
      style="width: 200px"
    />
    <el-button type="primary" round @click="handleQueryPage" style="width:240px">查询</el-button>
    <el-table :data="tableData" style="width: 100%" stripe>
      <el-table-column fixed prop="username" label="用户名" width="180" />
      <el-table-column prop="imagePath" label="头像" width="180">
        <template #default="scope">
          <el-image style="width: 100px;height: 100px" :src="scope.row.imagePath" />
          <!-- <span>{{scope.row}}</span> -->
        </template>
      </el-table-column>
      <el-table-column prop="updateTime" label="更新时间" width="300" />
      <el-table-column prop="createTime" label="创建时间" width="300" />
      <el-table-column fixed="right" label="操作" min-width="180">
        <template #default="scope">
          <el-button size="small" @click="handleDetail(scope.row.id)">查看</el-button>
          <el-button type="primary" size="small" @click="handleEdit">编辑</el-button>
          <el-button type="primary" size="small" @click="handleDelete">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page="queryPageParams.pageNo"
      :page-size="queryPageParams.pageSize"
      :page-sizes="[5, 10, 20, 30]"
      size="default"
      background
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<style scoped>
</style>
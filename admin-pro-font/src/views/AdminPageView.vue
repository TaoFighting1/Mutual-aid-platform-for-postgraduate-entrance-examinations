<script setup>
import { reactive, ref, onMounted } from "vue";
import { page, deleteById } from "@/api/admin";
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

const HandleDeleteConfirm = id => {
  console.log(`删除id为${id}`);
  deleteById(id).then(res => {
    console.log("删除成功");
    queryPage();
  });
};
const HandleDeleteCancel = () => {
  console.log(`删除操作取消。`);
};

const handleEdit = id => {
  //跳转到新增或者更新页面
  router.push({
    path: "/admin/save",
    query: { id }
  });
};
const handleAdd = () => {
  //跳转到新增或者更新页面
  router.push({
    path: "/admin/save"
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
    <el-button type="primary" round @click="handleAdd" style="width:240px">新增</el-button>
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
          <el-button type="primary" size="small" @click="handleEdit(scope.row.id)">编辑</el-button>
          <el-popconfirm
            confirm-button-text="确认"
            cancel-button-text="取消"
            icon-color="#626AEF"
            title="是否确认删除?"
            @confirm="HandleDeleteConfirm(scope.row.id)"
            @cancel="HandleDeleteCancel"
          >
            <template #reference>
              <el-button type="danger" size="small">删除</el-button>
            </template>
          </el-popconfirm>
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
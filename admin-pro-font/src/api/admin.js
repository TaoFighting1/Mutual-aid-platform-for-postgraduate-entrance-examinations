
//导入axios对象
import request from "@/utils/request"

//登录 data对象，传后端数据
const login = (data) => {
    //调用axios
    return request({
        // url: "http://localhost:8081/login",
        //这与proxy的配置有关
        url: "/api/login",//代理成http://localhost:8081/login 进行访问
        method: "post",
        data
    })
}

//分页获取admin数据
const page = (data) => {
    return request({
        // url: "http://localhost:8081/login",
        //这与proxy的配置有关
        url: "/api/admin/page",//代理成http://localhost:8081/login 进行访问
        method: "post",
        data
    })
}

//通过id查询admin数据
const findById = (id) => {
    return request({
        // url: "http://localhost:8081/login",
        //这与proxy的配置有关
        url: `/api/admin/detail/${id}`,//代理成http://localhost:8081/login 进行访问
        method: "get",
    })
}

//通过id查询admin数据
const deleteById = (id) => {
    return request({
        // url: "http://localhost:8081/login",
        //这与proxy的配置有关
        url: `/api/admin/delete/${id}`,//代理成http://localhost:8081/login 进行访问
        method: "get",
    })
}

//通过id查询admin数据
const save = (data) => {
    return request({
        // url: "http://localhost:8081/login",
        //这与proxy的配置有关
        url: `/api/admin/save`,//代理成http://localhost:8081/login 进行访问
        method: "post",
        headers: { "Content-Type": "application/json;charset=utf-8" },
        data
    })
}

export { login, page, findById, deleteById, save }
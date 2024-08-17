import { ref, computed, reactive } from 'vue'
import { defineStore } from 'pinia'
import { tourStrategies } from 'element-plus'

export const useAdminStore = defineStore('admin', () => {
    //定义token
    const token = ref('')
    //获取token
    const getToken = computed(() => token.value)
    // 保存token
    const saveToken = (t) => {
        token.value = t
    }
    //定义admin信息
    const admin = reactive({
        username: '',
        realName: '',
        avatar: '',
        adminId: 0
    })
    //保存用户信息
    const saveAdmin = (myAdmin) => {
        admin.username = myAdmin.username
        admin.realName = myAdmin.realName
        admin.avatar = myAdmin.imagePath
        admin.adminId = myAdmin.id
    }
    //要返回token，因为要持久化
    return { getToken, saveToken, token, admin, saveAdmin }
},
    // 持久化
    {
        persist: {
            enabled: true,
            strategies: [
                {
                    // 持久化Key的名称
                    key: 'admin-info',
                    storage: localStorage,
                    // paths: ['token']
                }
            ]
        }
    }

)

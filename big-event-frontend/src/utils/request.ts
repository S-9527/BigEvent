import axios from 'axios'
import { ElMessage } from 'element-plus'
import { useTokenStore } from '@/stores/token'
import router from '@/router'

const instance = axios.create({ baseURL: '/api' })

instance.interceptors.request.use(
  config => {
    const tokenStore = useTokenStore()
    if (tokenStore.token) {
      // 添加 Bearer 前缀以适配 Spring Security
      config.headers.Authorization = `Bearer ${tokenStore.token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

instance.interceptors.response.use(
  result => {
    if (result.data.code === 0) {
      return result.data
    }
    ElMessage.error(result.data.message ? result.data.message : '服务异常')
    return Promise.reject(result.data)
  },
  error => {
    // 处理 Spring Security 的认证失败
    if (error.response && error.response.status === 401) {
      ElMessage.error('登录已过期，请重新登录')
      const tokenStore = useTokenStore()
      tokenStore.removeToken()
      router.push('/login')
    } else if (error.response && error.response.status === 403) {
      ElMessage.error('没有权限访问该资源')
    } else {
      ElMessage.error('服务异常')
    }
    return Promise.reject(error)
  }
)

export default instance

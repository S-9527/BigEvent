import { defineStore } from 'pinia'
import { ref } from 'vue'

const TOKEN_KEY = 'token'

// 从 localStorage 读取初始值
const getStoredToken = (): string => {
  return localStorage.getItem(TOKEN_KEY) || ''
}

export const useTokenStore = defineStore('token',
  () => {
    const token = ref<string>(getStoredToken())

    const setToken = (newToken: string) => {
      token.value = newToken
      localStorage.setItem(TOKEN_KEY, newToken)
    }

    const removeToken = () => {
      token.value = ''
      localStorage.removeItem(TOKEN_KEY)
    }

    return {
      token,
      setToken,
      removeToken
    }
  }
)

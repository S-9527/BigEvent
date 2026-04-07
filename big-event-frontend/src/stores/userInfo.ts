import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { User } from '@/types'

export const useUserInfoStore = defineStore('userInfo',
  () => {
    const userInfo = ref<Partial<User>>({})

    const setUserInfo = (newUserInfo: User) => {
      userInfo.value = newUserInfo
    }

    const removeUserInfo = () => {
      userInfo.value = {}
    }

    return {
      userInfo,
      setUserInfo,
      removeUserInfo
    }
  }
)

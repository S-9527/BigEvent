import request from '@/utils/request'
import type { LoginRequest, RegisterRequest, User, UpdatePasswordRequest, Result } from '@/types'

export const userRegisterService = (registerData: RegisterRequest): Promise<Result> => {
  const params = new URLSearchParams()
  for (const key in registerData) {
    params.append(key, registerData[key] as string)
  }
  return request.post('/user/register', params)
}

export const userLoginService = (loginData: LoginRequest): Promise<Result<string>> => {
  const params = new URLSearchParams()
  for (const key in loginData) {
    params.append(key, loginData[key] as string)
  }
  return request.post('/user/login', params)
}

export const userInfoService = (): Promise<Result<User>> => {
  return request.get('/user/userInfo')
}

export const userInfoUpdateService = (userInfoData: Partial<User>): Promise<Result> => {
  return request.put('/user/update', userInfoData)
}

export const userAvatarUpdateService = (avatarUrl: string): Promise<Result> => {
  const urlSearchParams = new URLSearchParams()
  urlSearchParams.append('avatarUrl', avatarUrl)
  return request.patch('/user/updateAvatar', urlSearchParams)
}

export const userPwdUpdateService = (pwdData: UpdatePasswordRequest): Promise<Result> => {
  return request.patch('/user/updatePwd', pwdData)
}

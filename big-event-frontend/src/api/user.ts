import request from '@/utils/request'
import type { LoginRequest, RegisterRequest, User, UpdatePasswordRequest, Result } from '@/types'

export const userRegisterService = (registerData: RegisterRequest): Promise<Result> => {
  return request.post('/user/register', registerData)
}

export const userLoginService = (loginData: LoginRequest): Promise<Result<string>> => {
  return request.post('/user/login', loginData)
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

// API 响应类型
export interface Result<T = any> {
  code: number;
  message: string;
  data: T;
}

// 用户类型
export interface User {
  id: number;
  username: string;
  nickname: string;
  email: string;
  userPic?: string;
  createTime: string;
  updateTime: string;
}

// 文章类型
export interface Article {
  id: number;
  title: string;
  content: string;
  coverImg: string;
  state: '已发布' | '草稿';
  categoryId: number;
  createUser: number;
  createTime: string;
  updateTime: string;
}

// 分类类型
export interface Category {
  id: number;
  categoryName: string;
  categoryAlias: string;
  createUser: number;
  createTime: string;
  updateTime: string;
}

// 分页类型
export interface PageBean<T = any> {
  total: number;
  items: T[];
}

// 登录请求类型
export interface LoginRequest {
  username: string;
  password: string;
}

// 注册请求类型
export interface RegisterRequest {
  username: string;
  password: string;
  rePassword: string;
}

// 修改密码请求类型
export interface UpdatePasswordRequest {
  old_pwd: string;
  new_pwd: string;
  re_pwd: string;
}

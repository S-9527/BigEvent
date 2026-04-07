import request from '@/utils/request'
import type { Article, Category, PageBean, Result } from '@/types'

export const articleCategoryListService = (): Promise<Result<Category[]>> => {
  return request.get('/category')
}

export const articleCategoryAddService = (categoryData: Omit<Category, 'id' | 'createUser' | 'createTime' | 'updateTime'>): Promise<Result> => {
  return request.post('/category', categoryData)
}

export const articleCategoryUpdateService = (categoryData: Category): Promise<Result> => {
  return request.put('/category', categoryData)
}

export const articleCategoryDeleteService = (id: number): Promise<Result> => {
  return request.delete('/category?id=' + id)
}

export interface ArticleListParams {
  pageNum: number
  pageSize: number
  categoryId?: number
  state?: string
}

export const articleListService = (params: ArticleListParams): Promise<Result<PageBean<Article>>> => {
  return request.get('/article', { params })
}

export const articleAddService = (articleData: Omit<Article, 'id' | 'createUser' | 'createTime' | 'updateTime'>): Promise<Result> => {
  return request.post('/article', articleData)
}

export const articleUpdateService = (articleData: Article): Promise<Result> => {
  return request.put('/article', articleData)
}

export const articleDeleteService = (id: number): Promise<Result> => {
  return request.delete('/article?id=' + id)
}

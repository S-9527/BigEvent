# 阶段1：构建 Vue 应用 (使用 pnpm)
FROM node:20-alpine AS build-stage

# 安装 pnpm
RUN corepack enable && corepack prepare pnpm@latest --activate

WORKDIR /app

# 复制依赖文件
COPY package.json pnpm-lock.yaml ./
RUN pnpm install --frozen-lockfile

# 复制源码并构建
COPY . .
RUN pnpm run build

# 阶段2：使用 Nginx 提供静态服务
FROM nginx:stable-alpine
COPY --from=build-stage /app/dist /usr/share/nginx/html
# 如果有自定义 nginx 配置，取消注释并确保文件存在
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
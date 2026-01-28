# 果实成熟度监测平台

## 后端（Spring Boot）

- 路径：`backend/`
- JDK：17+
- 数据库：MySQL

### 1. 创建数据库
```sql
CREATE DATABASE fruit_maturity DEFAULT CHARACTER SET utf8mb4;
```

### 2. 修改配置
编辑 `backend/src/main/resources/application.yml`，确认数据库账号密码。

### 3. 启动
```bash
cd backend
mvn spring-boot:run
```
默认端口：`http://localhost:8080`

默认管理员：`admin / Admin123!`

## 前端（Vue 3 + Vite）

- 路径：`frontend/`

### 1. 安装依赖
```bash
cd frontend
npm install
```

### 2. 启动
```bash
npm run dev
```
默认地址：`http://localhost:5173`

前端已通过 Vite 代理 `/api` 到后端 `http://localhost:8080`。

## SQL 初始化（可选）

- 结构脚本：`backend/sql/01_schema.sql`
- 示例数据：`backend/sql/02_seed.sql`

执行示例：
```bash
mysql -u root -p < backend/sql/01_schema.sql
mysql -u root -p fruit_maturity < backend/sql/02_seed.sql
```

## 部署到 Netlify（前端）

1. 在 Netlify 新建站点，选择本项目仓库。
2. Build 设置已在 `netlify.toml` 内配置：
   - Base: `frontend`
   - Build: `npm run build`
   - Publish: `dist`
3. 在 Netlify 站点环境变量里设置：
   - `VITE_API_BASE=https://你的后端域名`
4. 后端需要允许 Netlify 域名跨域访问，修改 `backend/src/main/resources/application.yml`：
   - `app.cors.allowed-origins: "https://你的-netlify-站点.netlify.app"`

## 功能补充说明

- 实时监测：`/realtime/events/stream`、`/realtime/env/stream` 使用 SSE 推送
- 数据保存/删除：`POST /events`、`DELETE /events/{id}`、`POST /env/metrics`、`DELETE /env/metrics/{id}`
- AI 问答：`POST /ai/chat`（前端右侧抽屉“智能问答”）

前端已集成登录流程，未登录会跳转至 `/login`。

## 账号说明

- 管理员：`admin / Admin123!`
- 使用者：`user / User123!`
- 注册入口：`/register`


## 进阶功能说明

- 视频流：前端支持 HLS（.m3u8）。推荐 RTSP -> HLS 流媒体服务器（如 ZLMediaKit）。
  - 将摄像头 HLS 地址写入设备 `streamUrl` 字段即可播放。
  - 更新接口：`PATCH /devices/{id}/stream`
- 图表：已接入 ECharts（环境趋势、视觉统计）。
- 分页筛选：`GET /events/page` 支持类型/棚号/状态/关键词/分页。
- 权限：管理员可见「系统管理/模型管理」，使用者默认隐藏。
- AI：支持 DeepSeek（配置 `ai.provider=deepseek` 与 `ai.deepseek.api-key`）。
- 报表导出：`/reports/export/excel` 与 `/reports/export/pdf`。


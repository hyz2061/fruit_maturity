<template>
  <div class="login">
    <div class="panel">
      <div class="brand">
        <div class="mark"></div>
        <div>
          <h1>果实成熟度监测</h1>
          <p>农业智能运营与可视化决策平台</p>
        </div>
      </div>
      <div class="feature">
        <div>· 多源数据融合</div>
        <div>· 视觉识别 + 环境协同</div>
        <div>· 闭环事件管理</div>
      </div>
    </div>
    <div class="card form">
      <h2>账号登录</h2>
      <label>用户名</label>
      <input class="input" v-model="username" placeholder="请输入用户名" />
      <label>密码</label>
      <input class="input" v-model="password" type="password" placeholder="请输入密码" />
      <button class="btn btn--brand" @click="handleLogin">登录</button>
      <div v-if="error" class="error">{{ error }}</div>
      <div class="tip">默认管理员：admin / Admin123!</div>
      <div class="tip">没有账号？<RouterLink to="/register">去注册</RouterLink></div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { getMe, login } from '@/api/auth';
import { useAuthStore } from '@/stores/auth';

const router = useRouter();
const auth = useAuthStore();

const username = ref('admin');
const password = ref('Admin123!');
const error = ref('');

const handleLogin = async () => {
  error.value = '';
  try {
    const res = await login({ username: username.value, password: password.value });
    const token = res?.data?.data?.token;
    if (!token) {
      error.value = '登录失败，请检查账号信息';
      return;
    }
    auth.setToken(token);
    const me = await getMe();
    const data = me?.data?.data;
    if (data?.username) {
      auth.setProfile({ username: data.username }, data.roles || []);
    }
    router.push('/layout/dashboard');
  } catch (e) {
    error.value = '登录失败，请稍后重试';
  }
};
</script>

<style scoped>
.login {
  min-height: 100vh;
  display: grid;
  grid-template-columns: 1.2fr 0.8fr;
  gap: 24px;
  align-items: center;
  padding: 40px;
}

.panel {
  background: linear-gradient(135deg, rgba(15, 118, 110, 0.15), rgba(29, 78, 216, 0.15));
  border-radius: 24px;
  padding: 32px;
  border: 1px solid rgba(148, 163, 184, 0.3);
}

.brand {
  display: flex;
  gap: 16px;
  align-items: center;
}

.mark {
  width: 52px;
  height: 52px;
  border-radius: 16px;
  background: linear-gradient(135deg, #0f766e, #1d4ed8);
  box-shadow: 0 12px 24px rgba(14, 116, 144, 0.3);
}

.brand h1 {
  margin: 0 0 6px;
  font-size: 24px;
}

.brand p {
  margin: 0;
  font-size: 12px;
  color: #64748b;
}

.feature {
  margin-top: 24px;
  display: grid;
  gap: 8px;
  color: #475569;
  font-size: 12px;
}

.form {
  display: grid;
  gap: 10px;
}

.form h2 {
  margin: 0 0 6px;
}

.form label {
  font-size: 12px;
  color: #64748b;
}

.error {
  font-size: 12px;
  color: #dc2626;
}

.tip {
  font-size: 11px;
  color: #94a3b8;
}

@media (max-width: 900px) {
  .login {
    grid-template-columns: 1fr;
    padding: 24px;
  }
}
</style>

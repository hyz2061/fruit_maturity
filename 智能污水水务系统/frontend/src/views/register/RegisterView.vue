<template>
  <div class="login">
    <div class="panel">
      <div class="brand">
        <div class="mark"></div>
        <div>
          <h1>创建账号</h1>
          <p>管理员与使用者均可注册</p>
        </div>
      </div>
      <div class="feature">
        <div>· 管理员：全局配置与策略</div>
        <div>· 使用者：事件处置与日常运营</div>
      </div>
    </div>
    <div class="card form">
      <h2>注册</h2>
      <label>用户名</label>
      <input class="input" v-model="username" placeholder="请输入用户名" />
      <label>密码</label>
      <input class="input" v-model="password" type="password" placeholder="请输入密码" />
      <label>确认密码</label>
      <input class="input" v-model="confirm" type="password" placeholder="请再次输入密码" />
      <label>角色</label>
      <select class="select" v-model="roleCode">
        <option value="ROLE_USER">使用者</option>
        <option value="ROLE_ADMIN">管理员</option>
      </select>
      <button class="btn btn--brand" @click="handleRegister">注册</button>
      <div v-if="error" class="error">{{ error }}</div>
      <div class="tip">已有账号？<RouterLink to="/login">去登录</RouterLink></div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { register } from '@/api/auth';

const router = useRouter();

const username = ref('');
const password = ref('');
const confirm = ref('');
const roleCode = ref('ROLE_USER');
const error = ref('');

const handleRegister = async () => {
  error.value = '';
  if (!username.value || !password.value) {
    error.value = '用户名和密码不能为空';
    return;
  }
  if (password.value !== confirm.value) {
    error.value = '两次输入的密码不一致';
    return;
  }
  try {
    await register({ username: username.value, password: password.value, roleCode: roleCode.value });
    router.push('/login');
  } catch (e) {
    error.value = '注册失败，用户名可能已存在';
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

<template>
  <div class="card">
    <table class="table">
      <thead>
        <tr>
          <th>缩略图</th>
          <th>类型</th>
          <th>棚号</th>
          <th>严重度</th>
          <th>状态</th>
          <th>时间</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="row in list" :key="row.id">
          <td>
            <div class="thumb">
              <LazyImage :src="row.thumbUrl || row.imageUrl" />
            </div>
          </td>
          <td>{{ row.type }}</td>
          <td>{{ row.greenhouse || '-' }}</td>
          <td><span class="pill" :class="row.severityClass">{{ row.severity || '-' }}</span></td>
          <td><span class="pill" :class="row.statusClass">{{ row.status || '-' }}</span></td>
          <td>{{ row.time || '-' }}</td>
          <td>
            <button class="btn small" @click="$emit('view', row)">查看</button>
            <button v-if="isAdmin" class="btn small" @click="$emit('delete', row)">删除</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { useAuthStore } from '@/stores/auth';
import LazyImage from '@/components/common/LazyImage.vue';

type Row = {
  id: number | string;
  type: string;
  greenhouse?: string;
  severity?: string;
  status?: string;
  time?: string;
  severityClass?: string;
  statusClass?: string;
  imageUrl?: string;
  thumbUrl?: string;
};

const props = defineProps<{ rows?: Row[] }>();

defineEmits(['view', 'delete']);

const fallback: Row[] = [
  { id: 1, type: '成熟度偏低', greenhouse: '一号棚', severity: '中', severityClass: 'mid', status: '待确认', statusClass: 'pending', time: '10:21' },
  { id: 2, type: '病害疑似', greenhouse: '二号棚', severity: '高', severityClass: 'high', status: '处理中', statusClass: 'processing', time: '09:58' },
  { id: 3, type: '成熟度达标', greenhouse: '一号棚', severity: '低', severityClass: 'low', status: '已完成', statusClass: 'done', time: '09:30' },
];

const list = computed(() => (props.rows && props.rows.length ? props.rows : fallback));
const authStore = useAuthStore();
const isAdmin = computed(() => authStore.isAdmin());
</script>

<style scoped>
.table {
  width: 100%;
  border-collapse: collapse;
  font-size: 12px;
}

th, td {
  text-align: left;
  padding: 10px 8px;
  border-bottom: 1px solid #e2e8f0;
}

th {
  color: #64748b;
  font-weight: 600;
}

.thumb {
  width: 46px;
  height: 32px;
  background: linear-gradient(135deg, #e2e8f0, #cbd5f5);
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 10px;
  color: #64748b;
}

.pill {
  padding: 2px 8px;
  border-radius: 999px;
  font-size: 11px;
  border: 1px solid transparent;
}

.low { background: #dcfce7; color: #166534; border-color: #86efac; }
.mid { background: #fef3c7; color: #92400e; border-color: #fcd34d; }
.high { background: #fee2e2; color: #991b1b; border-color: #fecaca; }

.pending { background: #e0f2fe; color: #0369a1; border-color: #7dd3fc; }
.processing { background: #ede9fe; color: #5b21b6; border-color: #c4b5fd; }
.done { background: #dcfce7; color: #166534; border-color: #86efac; }

tbody tr:hover {
  background: #f8fafc;
}

.btn.small {
  padding: 4px 8px;
  margin-right: 6px;
}
</style>

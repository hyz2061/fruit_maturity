<template>
  <div class="events">
    <EventFilterBar :filters="filters" @create="handleCreate" @search="load" />
    <div class="toolbar">
      <BatchActions />
      <span class="count">共 {{ total }} 条</span>
    </div>
    <EventListTable :rows="rows" @view="handleView" @delete="handleDelete" />
    <PaginationBar :page="page" :size="size" :total="total" @change="handlePage" />
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import EventFilterBar from '@/components/events/EventFilterBar.vue';
import BatchActions from '@/components/events/BatchActions.vue';
import EventListTable from '@/components/events/EventListTable.vue';
import PaginationBar from '@/components/events/PaginationBar.vue';
import { createEvent, deleteEvent, getEventsPage } from '@/api/events';

const router = useRouter();
const rows = ref<any[]>([]);
const total = ref(0);
const page = ref(1);
const size = ref(10);

const filters = reactive({
  greenhouseId: '',
  type: '',
  status: '',
  keyword: '',
  from: '',
  to: '',
  minRipeRatio: '',
});

const severityClass = (val?: string) => {
  if (val === '高') return 'high';
  if (val === '中') return 'mid';
  if (val === '低') return 'low';
  return '';
};

const statusClass = (val?: string) => {
  if (val === '待确认') return 'pending';
  if (val === '处理中') return 'processing';
  if (val === '已完成') return 'done';
  return '';
};

const mapRow = (e: any) => ({
  id: e.id,
  type: e.type,
  greenhouse: e.greenhouseId ? `棚${e.greenhouseId}` : '-',
  severity: e.severity,
  status: e.status,
  time: e.createdAt ? new Date(e.createdAt).toLocaleString() : '-',
  severityClass: severityClass(e.severity),
  statusClass: statusClass(e.status),
  imageUrl: e.imageUrl,
  thumbUrl: e.thumbUrl,
});

const load = async () => {
  const params: any = {
    page: page.value - 1,
    size: size.value,
  };
  if (filters.greenhouseId) params.greenhouseId = Number(filters.greenhouseId);
  if (filters.type) params.type = filters.type;
  if (filters.status) params.status = filters.status;
  if (filters.keyword) params.keyword = filters.keyword;
  if (filters.from) params.from = filters.from;
  if (filters.to) params.to = filters.to;
  if (filters.minRipeRatio) params.minRipeRatio = Number(filters.minRipeRatio);

  const res = await getEventsPage(params);
  const data = res?.data?.data || { list: [], total: 0 };
  rows.value = (data.list || []).map(mapRow);
  total.value = data.total || 0;
};

const handleCreate = async () => {
  await createEvent({
    greenhouseId: 1,
    type: '成熟度偏低',
    severity: '中',
    status: '待确认',
    modelVersion: 'v1.2.0',
    confidence: 0.92,
    ripeRatio: 0.48,
  });
  await load();
};

const handleDelete = async (row: any) => {
  if (!row?.id) return;
  await deleteEvent(row.id);
  await load();
};

const handleView = (row: any) => {
  if (!row?.id) return;
  router.push(`/layout/events/${row.id}`);
};

const handlePage = (next: number) => {
  page.value = next;
  load();
};

onMounted(load);
</script>

<style scoped>
.events {
  display: grid;
  gap: 14px;
}

.toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.count {
  font-size: 12px;
  color: #64748b;
}
</style>

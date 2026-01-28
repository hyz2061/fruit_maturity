<template>
  <div class="env">
    <div class="page-header">
      <div>
        <h2>环境数据概览</h2>
        <p>多维传感器实时监测与阈值评估</p>
      </div>
      <div class="actions">
        <button class="btn" @click="writeSample">写入测试数据</button>
        <button class="btn" @click="removeLast">删除最新</button>
        <button class="btn">导出</button>
        <button class="btn btn--brand">生成报表</button>
      </div>
    </div>

    <div class="stats">
      <div v-for="item in stats" :key="item.label" class="card stat">
        <div class="label">{{ item.label }}</div>
        <div class="value">{{ item.value }}</div>
        <div class="trend" :class="item.tone">{{ item.trend }}</div>
      </div>
    </div>

    <div class="grid">
      <div class="card chart">
        <div class="card-title">温度趋势</div>
        <div ref="chartRef" class="chart-box"></div>
      </div>
      <div class="card quality">
        <div class="card-title">数据质量</div>
        <div class="quality-item" v-for="q in quality" :key="q.label">
          <span>{{ q.label }}</span>
          <strong>{{ q.value }}</strong>
        </div>
        <div class="quality-note">近 24h 数据完整率 98.7%</div>
      </div>
    </div>

    <div class="grid">
      <div class="card">
        <div class="card-title">阈值区间</div>
        <ul class="list">
          <li>温度 24℃ ~ 28℃</li>
          <li>湿度 55% ~ 70%</li>
          <li>CO₂ 800ppm ~ 1200ppm</li>
        </ul>
      </div>
      <div class="card">
        <div class="card-title">异常监测</div>
        <ul class="list">
          <li>二号棚 CO₂ 偏高（持续 25min）</li>
          <li>一号棚 光照波动过大</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onBeforeUnmount, onMounted, ref } from 'vue';
import * as echarts from 'echarts';
import { createEnvMetric, deleteEnvMetric, getEnvMetrics } from '@/api/env';

const lastMetricId = ref<number | null>(null);
const chartRef = ref<HTMLDivElement | null>(null);
let chart: echarts.ECharts | null = null;

const stats = [
  { label: '当前温度', value: '26.4℃', trend: '+0.6℃', tone: 'up' },
  { label: '当前湿度', value: '62%', trend: '-1.2%', tone: 'down' },
  { label: 'CO₂ 平均', value: '980ppm', trend: '+80', tone: 'up' },
  { label: '光照强度', value: '1.2w/m²', trend: '稳定', tone: 'flat' },
];

const quality = [
  { label: '缺失率', value: '1.3%' },
  { label: '离群点', value: '12' },
  { label: '有效传感器', value: '18/19' },
];

const renderChart = async () => {
  if (!chartRef.value) return;
  if (!chart) chart = echarts.init(chartRef.value);

  const res = await getEnvMetrics({ greenhouseId: 1, metric: 'temperature' });
  const list = res?.data?.data || [];
  const series = list.map((item: any) => [item.ts || item.createdAt, item.value]);

  chart.setOption({
    grid: { left: 40, right: 20, top: 20, bottom: 30 },
    xAxis: { type: 'time', axisLabel: { color: '#94a3b8' } },
    yAxis: { type: 'value', axisLabel: { color: '#94a3b8' } },
    series: [{
      type: 'line',
      smooth: true,
      data: series,
      lineStyle: { color: '#0f766e', width: 2 },
      areaStyle: { color: 'rgba(14, 116, 144, 0.15)' },
      showSymbol: false,
    }],
  });
};

const writeSample = async () => {
  const res = await createEnvMetric({
    greenhouseId: 1,
    metric: 'temperature',
    value: 25 + Math.random() * 4,
  });
  lastMetricId.value = res?.data?.data?.id ?? null;
  await renderChart();
};

const removeLast = async () => {
  if (!lastMetricId.value) return;
  await deleteEnvMetric(lastMetricId.value);
  lastMetricId.value = null;
  await renderChart();
};

onMounted(() => {
  renderChart();
  window.addEventListener('resize', () => chart?.resize());
});

onBeforeUnmount(() => {
  chart?.dispose();
  chart = null;
});
</script>

<style scoped>
.env {
  display: grid;
  gap: 14px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
  flex-wrap: wrap;
}

.page-header h2 {
  margin: 0;
  font-size: 18px;
}

.page-header p {
  margin: 4px 0 0;
  font-size: 12px;
  color: #64748b;
}

.actions {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.stats {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 12px;
}

.stat .label {
  font-size: 12px;
  color: #64748b;
}

.stat .value {
  font-size: 22px;
  font-weight: 700;
  font-family: var(--font-num);
  margin: 6px 0;
}

.stat .trend {
  font-size: 11px;
}

.trend.up { color: #dc2626; }
.trend.down { color: #16a34a; }
.trend.flat { color: #475569; }

.grid {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 12px;
}

.card-title {
  font-size: 14px;
  font-weight: 600;
  margin-bottom: 10px;
}

.chart-box {
  height: 240px;
}

.quality-item {
  display: flex;
  justify-content: space-between;
  padding: 8px 0;
  border-bottom: 1px dashed #e2e8f0;
  font-size: 12px;
}

.quality-item:last-child {
  border-bottom: none;
}

.quality-note {
  margin-top: 8px;
  font-size: 11px;
  color: #94a3b8;
}

.list {
  margin: 0;
  padding-left: 16px;
  font-size: 12px;
  color: #64748b;
  display: grid;
  gap: 6px;
}

@media (max-width: 1100px) {
  .stats {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }

  .grid {
    grid-template-columns: 1fr;
  }
}
</style>

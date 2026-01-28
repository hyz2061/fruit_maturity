<template>
  <div class="vision">
    <div class="page-header">
      <div>
        <h2>视觉分析概览</h2>
        <p>识别统计、严重度趋势与空间热力分布</p>
      </div>
      <button class="btn btn--brand">导出统计</button>
    </div>

    <div class="stats">
      <div v-for="item in stats" :key="item.label" class="card stat">
        <div class="label">{{ item.label }}</div>
        <div class="value">{{ item.value }}</div>
        <div class="hint">{{ item.hint }}</div>
      </div>
    </div>

    <div class="grid">
      <div class="card">
        <div class="card-title">类别占比</div>
        <div ref="pieRef" class="chart-box"></div>
      </div>
      <div class="card">
        <div class="card-title">严重度趋势</div>
        <div ref="lineRef" class="chart-box"></div>
      </div>
    </div>

    <div class="card">
      <div class="card-title">棚区热力分布</div>
      <div ref="heatRef" class="chart-box tall"></div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onBeforeUnmount, onMounted, ref } from 'vue';
import * as echarts from 'echarts';
import { getVisionStat } from '@/api/vision';

const stats = [
  { label: '识别样本', value: '12,840', hint: '近 7 天' },
  { label: '事件命中率', value: '92.4%', hint: '较上周 +1.6%' },
  { label: '误报率', value: '3.2%', hint: '持续下降' },
  { label: '模型版本', value: 'v1.2.0', hint: '在线运行' },
];

const pieRef = ref<HTMLDivElement | null>(null);
const lineRef = ref<HTMLDivElement | null>(null);
const heatRef = ref<HTMLDivElement | null>(null);
let pieChart: echarts.ECharts | null = null;
let lineChart: echarts.ECharts | null = null;
let heatChart: echarts.ECharts | null = null;

const renderCharts = async () => {
  const res = await getVisionStat();
  const data = res?.data?.data || {};

  const pieData = data.classDistribution || [
    { name: '成熟度偏低', value: 42 },
    { name: '成熟度达标', value: 38 },
    { name: '病害疑似', value: 20 },
  ];

  const trend = data.severityTrend || [
    { time: '01-17', high: 4, mid: 8, low: 6 },
    { time: '01-18', high: 2, mid: 6, low: 8 },
    { time: '01-19', high: 5, mid: 9, low: 7 },
    { time: '01-20', high: 3, mid: 7, low: 6 },
    { time: '01-21', high: 4, mid: 10, low: 9 },
  ];

  const heat = data.heatmap || [
    { x: '一号棚', y: '成熟度偏低', value: 8 },
    { x: '一号棚', y: '病害疑似', value: 3 },
    { x: '二号棚', y: '成熟度偏低', value: 6 },
    { x: '二号棚', y: '成熟度达标', value: 9 },
  ];

  if (pieRef.value) {
    pieChart = echarts.init(pieRef.value);
    pieChart.setOption({
      tooltip: { trigger: 'item' },
      series: [{
        type: 'pie',
        radius: ['40%', '70%'],
        data: pieData,
        label: { color: '#64748b', fontSize: 11 },
      }],
    });
  }

  if (lineRef.value) {
    lineChart = echarts.init(lineRef.value);
    lineChart.setOption({
      grid: { left: 40, right: 20, top: 20, bottom: 30 },
      xAxis: { type: 'category', data: trend.map((t: any) => t.time), axisLabel: { color: '#94a3b8' } },
      yAxis: { type: 'value', axisLabel: { color: '#94a3b8' } },
      series: [
        { name: '高', type: 'line', data: trend.map((t: any) => t.high), smooth: true },
        { name: '中', type: 'line', data: trend.map((t: any) => t.mid), smooth: true },
        { name: '低', type: 'line', data: trend.map((t: any) => t.low), smooth: true },
      ],
    });
  }

  if (heatRef.value) {
    heatChart = echarts.init(heatRef.value);
    const xs = Array.from(new Set(heat.map((h: any) => h.x)));
    const ys = Array.from(new Set(heat.map((h: any) => h.y)));
    heatChart.setOption({
      grid: { left: 80, right: 20, top: 20, bottom: 30 },
      xAxis: { type: 'category', data: xs, axisLabel: { color: '#94a3b8' } },
      yAxis: { type: 'category', data: ys, axisLabel: { color: '#94a3b8' } },
      visualMap: { min: 0, max: 10, calculable: false, orient: 'horizontal', left: 'center', bottom: 10 },
      series: [{
        type: 'heatmap',
        data: heat.map((h: any) => [xs.indexOf(h.x), ys.indexOf(h.y), h.value]),
        label: { show: true, color: '#0f172a', fontSize: 10 },
      }],
    });
  }
};

onMounted(() => {
  renderCharts();
  window.addEventListener('resize', () => {
    pieChart?.resize();
    lineChart?.resize();
    heatChart?.resize();
  });
});

onBeforeUnmount(() => {
  pieChart?.dispose();
  lineChart?.dispose();
  heatChart?.dispose();
});
</script>

<style scoped>
.vision {
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

.stat .hint {
  font-size: 11px;
  color: #94a3b8;
}

.grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 12px;
}

.card-title {
  font-size: 14px;
  font-weight: 600;
  margin-bottom: 10px;
}

.chart-box {
  height: 220px;
}

.chart-box.tall {
  height: 260px;
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

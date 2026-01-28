<template>
  <div class="dashboard">
    <BaseSelectorBar />
    
    <KpiCards />

    <div class="panel-grid">
      <div class="panel-left">
        <div class="twin-container-wrap">
          <DigitalTwin3D />
        </div>
        
        <EventTimeline />
      </div>

      <div class="panel-right">
        <EnvSparkLines />
        <RiskHeatmap />
      </div>
    </div>

    <AiAssistantFloat />
  </div>
</template>

<script setup lang="ts">
// 确保路径与你实际项目结构一致
import BaseSelectorBar from '@/components/dashboard/BaseSelectorBar.vue';
import KpiCards from '@/components/dashboard/KpiCards.vue';
import DigitalTwin3D from '@/components/dashboard/DigitalTwin3D.vue'; // 引入新组件
import EnvSparkLines from '@/components/dashboard/EnvSparkLines.vue';
import EventTimeline from '@/components/dashboard/EventTimeline.vue';
import RiskHeatmap from '@/components/dashboard/RiskHeatmap.vue';
import AiAssistantFloat from '@/components/dashboard/AiAssistantFloat.vue';
</script>

<style scoped>
.dashboard {
  display: grid;
  gap: 16px;
  padding: 16px;
  background-color: #030712; /* 整体深色背景，衬托3D */
  min-height: 100vh;
  box-sizing: border-box;
}

.panel-grid {
  display: grid;
  grid-template-columns: 2fr 1fr; /* 左侧宽，右侧窄 */
  gap: 16px;
  /* 设定一个最小高度，保证在内容不足时也有撑开的效果 */
  min-height: calc(100vh - 200px); 
}

.panel-left {
  display: grid;
  /* * 关键布局调整：
   * 3fr: 3D 场景占更多高度
   * 2fr: 时间轴占较少高度 
   */
  grid-template-rows: 3fr 2fr; 
  gap: 16px;
}

.panel-right {
  display: grid;
  grid-template-rows: 1fr 1fr; /* 右侧上下均分 */
  gap: 16px;
}

/* 包装层确保组件填满网格 */
.twin-container-wrap {
  width: 100%;
  height: 100%;
  min-height: 400px; /* 3D 场景的最小高度 */
}

/* 响应式适配 */
@media (max-width: 1100px) {
  .panel-grid {
    grid-template-columns: 1fr;
    grid-template-rows: auto auto; /* 变为上下排列 */
  }
  
  .panel-left {
    height: 800px; /* 移动端给左侧面板固定高度 */
  }
}
</style>
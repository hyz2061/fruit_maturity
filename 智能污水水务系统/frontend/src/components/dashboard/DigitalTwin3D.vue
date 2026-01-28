<template>
  <div ref="containerRef" class="orchard-pro-wrapper">
    <div class="hud-top">
      <div class="app-header">
        <div class="icon-box">🌳</div>
        <div>
          <h3>SMART ORCHARD OS</h3>
          <span class="subtitle">全域数字化管理平台</span>
        </div>
      </div>

      <div class="layer-switcher">
        <button 
          v-for="mode in viewModes" 
          :key="mode.key"
          :class="{ active: currentViewMode === mode.key }"
          @click="switchViewMode(mode.key)"
        >
          <span class="dot" :style="{ background: mode.color }"></span>
          {{ mode.label }}
        </button>
      </div>
    </div>

    <transition name="slide-right">
      <div v-if="selectedTree" class="detail-panel">
        <div class="panel-header">
          <h4>树木档案 #{{ selectedTree.id }}</h4>
          <button class="close-btn" @click="selectedTree = null">×</button>
        </div>
        
        <div class="panel-content">
          <div class="info-grid">
            <div class="info-item">
              <label>品种</label>
              <span>{{ selectedTree.variety }}</span>
            </div>
            <div class="info-item">
              <label>树龄</label>
              <span>{{ selectedTree.age }} 年</span>
            </div>
          </div>

          <div class="metric-box">
            <div class="metric-row">
              <span>成熟度</span>
              <div class="progress-bg">
                <div class="progress-fill" :style="{ width: selectedTree.maturity + '%', background: '#3b82f6' }"></div>
              </div>
              <span class="val">{{ selectedTree.maturity }}%</span>
            </div>
            
            <div class="metric-row">
              <span>土壤水分</span>
              <div class="progress-bg">
                <div class="progress-fill" :style="{ width: selectedTree.moisture + '%', background: '#06b6d4' }"></div>
              </div>
              <span class="val">{{ selectedTree.moisture }}%</span>
            </div>

            <div class="metric-row">
              <span>健康评分</span>
              <div class="progress-bg">
                <div class="progress-fill" :style="{ width: selectedTree.health + '%', background: getHealthColor(selectedTree.health) }"></div>
              </div>
              <span class="val">{{ selectedTree.health }}</span>
            </div>
          </div>

          <div class="action-footer">
            <button class="action-btn primary">查看监控</button>
            <button class="action-btn warning">派发任务</button>
          </div>
        </div>
      </div>
    </transition>

    <div class="legend-panel">
      <div class="legend-title">当前图层: {{ currentModeLabel }}</div>
      <div class="gradient-bar" :style="currentGradientStyle"></div>
      <div class="legend-labels">
        <span>Low</span>
        <span>High</span>
      </div>
    </div>

    <div v-if="loading" class="loading-overlay">
      Loading GIS Data...
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, onUnmounted, ref, computed } from 'vue';
import * as THREE from 'three';
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls.js';

// --- 类型定义 ---
interface TreeData {
  id: number;
  x: number;
  z: number;
  variety: string;
  age: number;
  maturity: number;
  moisture: number;
  health: number;
}

type ViewMode = 'maturity' | 'moisture' | 'health';

// --- 状态管理 ---
const containerRef = ref<HTMLElement | null>(null);
const loading = ref(true);
const selectedTree = ref<TreeData | null>(null);
const currentViewMode = ref<ViewMode>('maturity');

const viewModes = [
  { key: 'maturity' as ViewMode, label: '果实成熟度', color: '#f59e0b' },
  { key: 'moisture' as ViewMode, label: '土壤水分', color: '#06b6d4' },
  { key: 'health' as ViewMode, label: '健康监测', color: '#10b981' },
];

const isValidViewMode = (value: string): value is ViewMode => {
  return ['maturity', 'moisture', 'health'].includes(value);
};

const switchViewMode = (mode: string) => {
  if (isValidViewMode(mode)) {
    currentViewMode.value = mode;
    updateColorsByMode(mode);
  }
};

// --- Mock Data 生成 (已修改为小规模) ---
// 修改点 1：减少行和列
const TREE_COUNT_ROW = 12; 
const TREE_COUNT_COL = 12;
// 修改点 2：增加间距，让树稀疏一点
const SPACING = 3.5; 

const treeDatabase: TreeData[] = [];

// 生成 144 棵树 (12x12)
for (let i = 0; i < TREE_COUNT_ROW; i++) {
  for (let j = 0; j < TREE_COUNT_COL; j++) {
    treeDatabase.push({
      id: i * TREE_COUNT_COL + j,
      x: (i - TREE_COUNT_ROW / 2) * SPACING,
      z: (j - TREE_COUNT_COL / 2) * SPACING,
      variety: Math.random() > 0.5 ? '富士红' : '黄金帅',
      age: 3 + Math.floor(Math.random() * 5),
      maturity: Math.floor(Math.random() * 100),
      moisture: 20 + Math.floor(Math.random() * 80),
      health: 60 + Math.floor(Math.random() * 40),
    });
  }
}

// --- Three.js 变量 ---
let scene: THREE.Scene;
let camera: THREE.PerspectiveCamera;
let renderer: THREE.WebGLRenderer;
let controls: OrbitControls;
let animationId: number | null = null;
let raycaster: THREE.Raycaster;
let mouse: THREE.Vector2;

// InstancedMesh 变量
let instancedMesh: THREE.InstancedMesh;
let dummy = new THREE.Object3D();
let highlightMesh: THREE.Mesh;

// 渲染循环
function animate() {
  controls.update();
  renderer.render(scene, camera);
  animationId = requestAnimationFrame(animate);
}

const initThree = () => {
  if (!containerRef.value) return;
  const { clientWidth: width, clientHeight: height } = containerRef.value;

  // 1. 场景
  scene = new THREE.Scene();
  scene.background = new THREE.Color('#111827');
  scene.fog = new THREE.Fog('#111827', 20, 80); // 修改点 3：调整雾效距离适配小地图

  // 2. 相机
  camera = new THREE.PerspectiveCamera(45, width / height, 0.1, 500);
  // 修改点 4：拉近相机位置 (之前是 60, 50, 60)
  camera.position.set(30, 25, 30); 
  camera.lookAt(0, 0, 0);

  // 3. 渲染器
  renderer = new THREE.WebGLRenderer({ antialias: true, powerPreference: 'high-performance' });
  renderer.setSize(width, height);
  renderer.setPixelRatio(window.devicePixelRatio);
  renderer.shadowMap.enabled = true;
  renderer.shadowMap.type = THREE.PCFSoftShadowMap;
  containerRef.value.appendChild(renderer.domElement);

  // 4. 灯光
  const hemiLight = new THREE.HemisphereLight(0xffffff, 0x444444, 0.6);
  scene.add(hemiLight);
  
  const dirLight = new THREE.DirectionalLight(0xffffff, 0.8);
  dirLight.position.set(30, 50, 20); // 调整光源位置
  dirLight.castShadow = true;
  dirLight.shadow.mapSize.set(1024, 1024);
  // 修改点 5：缩小阴影计算范围，提高清晰度
  const d = 30;
  dirLight.shadow.camera.left = -d;
  dirLight.shadow.camera.right = d;
  dirLight.shadow.camera.top = d;
  dirLight.shadow.camera.bottom = -d;
  scene.add(dirLight);

  // 5. 地面与网格
  // 修改点 6：缩小地面尺寸以适配 12x12 的树阵
  const planeGeo = new THREE.PlaneGeometry(80, 80);
  const planeMat = new THREE.MeshPhongMaterial({ color: 0x1f2937, depthWrite: false });
  const plane = new THREE.Mesh(planeGeo, planeMat);
  plane.rotation.x = -Math.PI / 2;
  plane.receiveShadow = true;
  scene.add(plane);

  // 调整网格密度
  const grid = new THREE.GridHelper(80, 40, 0x374151, 0x1f2937);
  scene.add(grid);

  // 6. 创建树
  createInstancedTrees();

  // 7. 高亮圈
  const highlightGeo = new THREE.RingGeometry(0.8, 0.9, 32);
  const highlightMat = new THREE.MeshBasicMaterial({ 
    color: 0x3b82f6, 
    transparent: true, 
    opacity: 0.8, 
    side: THREE.DoubleSide 
  });
  highlightMesh = new THREE.Mesh(highlightGeo, highlightMat);
  highlightMesh.rotation.x = -Math.PI / 2;
  highlightMesh.visible = false;
  scene.add(highlightMesh);

  // 8. 控制器
  controls = new OrbitControls(camera, renderer.domElement);
  controls.enableDamping = true;
  controls.maxPolarAngle = Math.PI / 2 - 0.1;
  // 限制缩放范围，防止用户拉得太远看穿地底
  controls.minDistance = 10;
  controls.maxDistance = 60; 

  raycaster = new THREE.Raycaster();
  mouse = new THREE.Vector2();

  renderer.domElement.addEventListener('click', onClick);

  loading.value = false;
  animate();
};

const createInstancedTrees = () => {
  const geometry = new THREE.CylinderGeometry(0, 0.8, 2, 8); 
  geometry.translate(0, 1, 0); 
  
  const material = new THREE.MeshLambertMaterial({ 
    color: 0xffffff,
  });

  const count = treeDatabase.length;
  instancedMesh = new THREE.InstancedMesh(geometry, material, count);
  instancedMesh.castShadow = true;
  instancedMesh.receiveShadow = true;

  const _color = new THREE.Color();

  treeDatabase.forEach((data, index) => {
    dummy.position.set(data.x, 0, data.z);
    const scale = 0.8 + Math.random() * 0.4;
    dummy.scale.set(scale, scale, scale);
    dummy.updateMatrix();
    instancedMesh.setMatrixAt(index, dummy.matrix);
  });

  scene.add(instancedMesh);
  updateColorsByMode('maturity'); 
};

const updateColorsByMode = (mode: ViewMode) => {
  const _color = new THREE.Color();
  
  treeDatabase.forEach((data, index) => {
    let hexColor = 0xffffff;

    if (mode === 'maturity') {
      if (data.maturity > 80) hexColor = 0xef4444;
      else if (data.maturity > 40) hexColor = 0xf59e0b;
      else hexColor = 0x10b981;
    } 
    else if (mode === 'moisture') {
      _color.setHSL(0.6, 1.0, 0.9 - (data.moisture / 100) * 0.5);
      hexColor = _color.getHex();
    }
    else if (mode === 'health') {
      if (data.health < 60) hexColor = 0xff0000;
      else hexColor = 0x10b981;
    }

    _color.setHex(hexColor);
    instancedMesh.setColorAt(index, _color);
  });

  instancedMesh.instanceColor!.needsUpdate = true;
};

const onClick = (event: MouseEvent) => {
  if (!containerRef.value) return;
  
  const rect = containerRef.value.getBoundingClientRect();
  mouse.x = ((event.clientX - rect.left) / rect.width) * 2 - 1;
  mouse.y = -((event.clientY - rect.top) / rect.height) * 2 + 1;

  raycaster.setFromCamera(mouse, camera);
  const intersection = raycaster.intersectObject(instancedMesh);

  if (intersection.length > 0) {
    const instanceId = intersection[0].instanceId;
    if (instanceId !== undefined) {
      const data = treeDatabase[instanceId];
      selectedTree.value = data;
      highlightMesh.visible = true;
      highlightMesh.position.set(data.x, 0.1, data.z);
    }
  } else {
    selectedTree.value = null;
    highlightMesh.visible = false;
  }
};

const currentModeLabel = computed(() => {
  return viewModes.find(m => m.key === currentViewMode.value)?.label;
});

const currentGradientStyle = computed(() => {
  if (currentViewMode.value === 'maturity') return 'background: linear-gradient(to right, #10b981, #f59e0b, #ef4444)';
  if (currentViewMode.value === 'moisture') return 'background: linear-gradient(to right, #cffafe, #06b6d4, #1e3a8a)';
  return 'background: linear-gradient(to right, #ef4444, #10b981)';
});

const getHealthColor = (val: number) => val < 60 ? '#ef4444' : '#10b981';

const handleResize = () => {
  if (!containerRef.value || !camera) return;
  const w = containerRef.value.clientWidth;
  const h = containerRef.value.clientHeight;
  camera.aspect = w / h;
  camera.updateProjectionMatrix();
  renderer.setSize(w, h);
};

onMounted(() => {
  initThree();
  window.addEventListener('resize', handleResize);
});

onUnmounted(() => {
  window.removeEventListener('resize', handleResize);
  if (renderer && renderer.domElement) {
    renderer.domElement.removeEventListener('click', onClick);
  }
  if (renderer) renderer.dispose();
  if (animationId != null) cancelAnimationFrame(animationId);
});
</script>

<style scoped>
.orchard-pro-wrapper {
  position: relative;
  width: 100%;
  height: 100%;
  background: #111827;
  border-radius: 12px;
  overflow: hidden;
  font-family: 'Inter', 'Helvetica Neue', Arial, sans-serif;
  border: 1px solid #374151;
}

/* --- HUD Top --- */
.hud-top {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  padding: 16px 24px;
  box-sizing: border-box;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  background: linear-gradient(to bottom, rgba(17, 24, 39, 0.95), rgba(17, 24, 39, 0));
  z-index: 10;
  pointer-events: none;
}

.app-header {
  display: flex;
  gap: 12px;
  color: white;
  pointer-events: auto;
}

.icon-box {
  width: 40px;
  height: 40px;
  background: rgba(59, 130, 246, 0.2);
  border: 1px solid rgba(59, 130, 246, 0.5);
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
}

.app-header h3 {
  margin: 0;
  font-size: 16px;
  font-weight: 700;
  letter-spacing: 0.5px;
}
.subtitle {
  font-size: 12px;
  color: #9ca3af;
}

.layer-switcher {
  display: flex;
  gap: 8px;
  background: rgba(31, 41, 55, 0.8);
  padding: 4px;
  border-radius: 8px;
  border: 1px solid #374151;
  pointer-events: auto;
  backdrop-filter: blur(8px);
}

.layer-switcher button {
  background: transparent;
  border: none;
  color: #9ca3af;
  padding: 6px 12px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 12px;
  display: flex;
  align-items: center;
  gap: 6px;
  transition: all 0.2s;
}

.layer-switcher button.active {
  background: #374151;
  color: white;
  font-weight: 600;
}

.dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
}

/* --- Detail Panel (Right) --- */
.detail-panel {
  position: absolute;
  top: 80px;
  right: 20px;
  width: 280px;
  background: rgba(17, 24, 39, 0.95);
  border: 1px solid #4b5563;
  border-radius: 12px;
  color: white;
  z-index: 20;
  backdrop-filter: blur(12px);
  box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.3);
}

.panel-header {
  padding: 12px 16px;
  border-bottom: 1px solid #374151;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.panel-header h4 { margin: 0; font-size: 14px; }
.close-btn { background: none; border: none; color: #9ca3af; cursor: pointer; font-size: 18px; }

.panel-content { padding: 16px; }

.info-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 12px;
  margin-bottom: 16px;
}
.info-item { display: flex; flex-direction: column; gap: 4px; }
.info-item label { font-size: 11px; color: #9ca3af; text-transform: uppercase; }
.info-item span { font-size: 13px; font-weight: 600; }

.metric-box { display: flex; flex-direction: column; gap: 12px; margin-bottom: 20px; }
.metric-row { display: flex; align-items: center; gap: 10px; font-size: 12px; }
.metric-row > span:first-child { width: 60px; color: #d1d5db; }
.progress-bg { flex: 1; height: 6px; background: #374151; border-radius: 3px; overflow: hidden; }
.progress-fill { height: 100%; border-radius: 3px; }
.val { width: 30px; text-align: right; font-weight: bold; }

.action-footer { display: flex; gap: 8px; }
.action-btn { flex: 1; padding: 8px; border: none; border-radius: 6px; font-size: 12px; cursor: pointer; font-weight: 600; }
.action-btn.primary { background: #2563eb; color: white; }
.action-btn.warning { background: #d97706; color: white; }

/* --- Legend (Bottom Left) --- */
.legend-panel {
  position: absolute;
  bottom: 20px;
  left: 20px;
  background: rgba(17, 24, 39, 0.8);
  padding: 12px;
  border-radius: 8px;
  border: 1px solid #374151;
  color: white;
  backdrop-filter: blur(4px);
}
.legend-title { font-size: 11px; margin-bottom: 6px; color: #d1d5db; }
.gradient-bar { height: 8px; width: 140px; border-radius: 4px; margin-bottom: 4px; }
.legend-labels { display: flex; justify-content: space-between; font-size: 10px; color: #9ca3af; }

/* --- Loading --- */
.loading-overlay {
  position: absolute;
  top: 0; left: 0; width: 100%; height: 100%;
  background: #111827;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #3b82f6;
  font-weight: bold;
  z-index: 50;
}

/* Transitions */
.slide-right-enter-active, .slide-right-leave-active { transition: transform 0.3s ease, opacity 0.3s ease; }
.slide-right-enter-from, .slide-right-leave-to { transform: translateX(20px); opacity: 0; }
</style>
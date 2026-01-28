<template>
  <div class="event-detail">
    <EventHeader :event="eventData" />

    <div class="grid">
      <div class="left">
        <ImageComparePanel :origin-url="eventData?.imageUrl" :annotated-url="eventData?.annotatedUrl" />
        <EnvBeforeAfterChart />
      </div>
      <div class="right">
        <CauseSuggestionCard />
        <WorkflowTimeline />
        <HandleForm :event="eventData" @updated="load" />
        <RelatedEvents />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import { getEventDetail } from '@/api/events';
import EventHeader from '@/components/events/EventHeader.vue';
import ImageComparePanel from '@/components/events/ImageComparePanel.vue';
import EnvBeforeAfterChart from '@/components/events/EnvBeforeAfterChart.vue';
import CauseSuggestionCard from '@/components/events/CauseSuggestionCard.vue';
import WorkflowTimeline from '@/components/events/WorkflowTimeline.vue';
import HandleForm from '@/components/events/HandleForm.vue';
import RelatedEvents from '@/components/events/RelatedEvents.vue';

const route = useRoute();
const eventData = ref<any>(null);

const load = async () => {
  const id = route.params.id as string;
  if (!id) return;
  const res = await getEventDetail(id);
  eventData.value = res?.data?.data || null;
};

onMounted(load);
</script>

<style scoped>
.event-detail {
  display: grid;
  gap: 14px;
}

.grid {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 14px;
}

.left, .right {
  display: grid;
  gap: 14px;
}

@media (max-width: 1100px) {
  .grid {
    grid-template-columns: 1fr;
  }
}
</style>

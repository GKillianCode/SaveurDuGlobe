
<script>
import "./styles/main.css";
import mobileNavigationComponent from './components/mobileNavigationComponent.vue';
import desktopNavigationComponentVue from './components/desktopNavigationComponent.vue';

export default {
  components: {
    mobileNavigationComponent,
    desktopNavigationComponentVue
  },
  data() {
    return {
      screenWidth: null
    };
  },
  mounted() {
    this.screenWidth = window.innerWidth;
    window.addEventListener('resize', this.handleResize);
  },
  beforeUnmount() {
    window.removeEventListener('resize', this.handleResize);
  },
  methods: {
    handleResize() {
      this.screenWidth = window.innerWidth;
    }
  }
}
</script>

<template>
  <div v-if="screenWidth < 1000">
    <mobileNavigationComponent />
  </div>
  <div v-else>
    <desktopNavigationComponentVue />
  </div>
  <RouterView />
</template>

<style lang="scss" scoped></style>
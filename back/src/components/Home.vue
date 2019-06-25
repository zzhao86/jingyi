<template>
  <div class="hello"></div>
</template>

<script>
  export default {
    name: 'Home',
    created() {
      console.log('router', this.$router);
      console.log('router', this.$route);
    },
    data() {
      return {};
    }
  };
</script>

<style scoped></style>

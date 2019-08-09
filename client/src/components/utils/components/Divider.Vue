<template>
  <div class="divider">
    <span>没有更多了</span>
  </div>
</template>
<script>
  export default {
    name: 'Divider'
  };
</script>
<style scoped>
  .divider {
    position: relative;
    width: 100%;
    white-space: nowrap;
    padding: 15px 0;
    overflow: hidden;
    text-align: center;
    color: #666;
    background-color: #f3f3f3;
  }
  .divider::after {
    content: '';
    position: absolute;
    top: 50%;
    width: 90vw;
    left: 5vw;
    height: 1px;
    border-top: 1px solid #ccc;
  }
  .divider span {
    position: relative;
    line-height: 1;
    font-size: 12px;
    background-color: #f3f3f3;
    padding: 0 20px;
    z-index: 1;
  }
</style>

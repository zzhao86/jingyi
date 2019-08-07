<template>
  <div class="infinite-loading">
    <mt-spinner type="fading-circle"></mt-spinner>
    <span>加载中…</span>
  </div>
</template>
<script>
  export default {
    name: 'ListLoading'
  };
</script>
<style>
  .infinite-loading {
    width: 100vw;
    font-size: 12px;
    text-align: center;
    height: 50px;
    line-height: 50px;
    background-color: #f3f3f3;
  }
  .infinite-loading .mint-spinner-fading-circle {
    display: inline-block;
    vertical-align: middle;
    margin-right: 5px;
  }
</style>

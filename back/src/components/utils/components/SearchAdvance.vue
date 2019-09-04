<template>
  <div>
    <el-drawer custom-class="advance-search" size="400px" title="高级查询" direction="rtl" :visible.sync="visibleSync">
      <div class="content">
        <slot name="content"></slot>
      </div>
      <div class="footer">
        <el-button type="warning" size="small" @click="onSearchResetClick">重置</el-button>
        <el-button type="primary" size="small" @click="onAdvanceSearchClick">查询</el-button>
      </div>
    </el-drawer>
  </div>
</template>
<script>
  export default {
    name: 'SearchAdvance',
    created() {},
    data() {
      return {
        visibleSync: false
      };
    },
    methods: {
      onSearchResetClick() {
        this.$emit('reset');
      },
      onAdvanceSearchClick() {
        // this.visibleSync = false;
        this.$emit('search');
      }
    }
  };
</script>
<style></style>

<template>
  <div class="table-page">
    <el-pagination
      background
      :current-page="params.index"
      :page-size="params.size"
      layout="total, prev, pager, next, jumper, sizes"
      :total="total"
      @current-change="onPageChange"
      @size-change="onSizeChange"
    >
    </el-pagination>
  </div>
</template>
<script>
  export default {
    name: 'Pagination',
    created() {},
    data() {
      return {};
    },
    props: {
      params: Object,
      total: Number
    },
    methods: {
      onPageChange: function(index) {
        this.$emit('page-change', index);
      },
      onSizeChange: function(size) {
        this.$emit('size-change', size);
      }
    }
  };
</script>
<style></style>

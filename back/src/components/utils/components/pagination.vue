<template>
  <div class="table-page">
    <el-pagination
      background
      :current-page="params.index"
      :page-size="params.size"
      :total="total"
      layout="sizes, total, prev, pager, next, jumper"
      :page-sizes="[10,20,50,100]"
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
      params: {
        type: Object,
        default: {
          index: 1,
          page: 10,
          total: 0
        }
      },
      total: Number
    },
    methods: {
      onPageChange: function(index) {
        this.$emit('page-change', index);
      },
      onSizeChange: function(size) {
        this.$global.pageSize = size;
        this.$emit('size-change', size);
      }
    }
  };
</script>
<style></style>

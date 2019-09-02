<template>
  <el-select v-model="value" :placeholder="placeholder" @change="onChange">
    <el-option v-for="(item, index) in options" :key="index" :label="item.label" :value="item.value"></el-option>
  </el-select>
</template>
<script>
  export default {
    name: 'StatusSelect',
    created() {},
    data() {
      return {
        options: []
      };
    },
    model: {
      prop: 'modelValue',
      event: 'returnValue'
    },
    props: {
      modelValue: {
        type: Array,
        default: []
      },
      disabled: {
        type: Boolean,
        default: false,
        required: false
      },
      placeholder: {
        type: String,
        default: '',
        required: false
      }
    },
    methods: {
      loadSelectData() {
        this.$get('back/assets/status/select').then(res => {
          if (res.isSuccess) {
            this.options = res.data;
          }
        });
      },
      onChange() {
        this.$emit('returnValue', this.value);
      }
    }
  };
</script>
<style scoped></style>

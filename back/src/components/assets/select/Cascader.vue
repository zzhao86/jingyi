<template>
  <el-cascader
    v-model="value"
    :options="options"
    :size="size"
    :clearable="clearable"
    :show-all-levels="showAllLevels"
    :props="{expandTrigger: 'hover', checkStrictly: true, emitPath: false}"
    :placeholder="placeholder"
    @change="onChange"
  >
  </el-cascader>
</template>
<script>
  export default {
    name: 'AssetsCascader',
    mounted() {},
    data() {
      return {
        value: ''
      };
    },
    watch: {
      modelValue: function(val, old) {
        this.value = val;
      }
    },
    model: {
      prop: 'modelValue',
      event: 'returnValue'
    },
    props: {
      // v-model值
      modelValue: String,
      // 下拉列表数据
      options: {
        type: Array,
        default: []
      },
      // 输入框尺寸
      size: {
        type: String,
        default: 'medium',
        required: false
      },
      // 输入框中是否显示选中值的完整路径
      showAllLevels: {
        type: Boolean,
        default: true,
        required: false
      },
      // 是否可以清空选项
      clearable: {
        type: Boolean,
        default: false,
        required: false
      },
      // 输入框占位文本
      placeholder: {
        type: String,
        default: '请选择资产分类',
        required: false
      }
    },
    watch: {
      modelValue: function(val, old) {
        this.value = val;
      }
    },
    methods: {
      onChange(v) {
        this.$emit('returnValue', this.value);
        this.$emit('change');
      }
    }
  };
</script>
<style scoped></style>

<template>
  <el-select v-model="value" :size="size" :placeholder="placeholder" :clearable="clearable" @change="onChange">
    <el-option v-for="(item, index) in options" :key="index" :label="item.label" :value="item.value"></el-option>
  </el-select>
</template>
<script>
  export default {
    name: 'StatusSelect',
    created() {
      this.value = this.modelValue;
      this.loadSelectData();
    },
    data() {
      return {
        value: '',
        options: []
      };
    },
    model: {
      prop: 'modelValue',
      event: 'returnValue'
    },
    props: {
      // v-model
      modelValue: [String, Number],
      defaultOption: {
        type: [Object, Boolean],
        default: false
      },
      // 是否可以清空选项
      clearable: {
        type: Boolean,
        default: false,
        required: false
      },
      // 是否禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      },
      // 输入框尺寸
      size: {
        type: String,
        default: 'medium',
        required: false
      },
      // 输入框占位文本
      placeholder: {
        type: String,
        default: '',
        required: false
      }
    },
    watch: {
      modelValue: function(val, old) {
        this.value = val;
      }
    },
    methods: {
      loadSelectData() {
        this.$get('back/assets/useStatus/select').then(res => {
          if (res.isSuccess) {
            this.options = res.data;
            if (this.defaultOption) {
              if (this.defaultOption === true) {
                this.options.unshift({
                  label: '请选择资产使用状态',
                  value: ''
                });
              } else {
                this.options.unshift(this.defaultOption);
              }
            }
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

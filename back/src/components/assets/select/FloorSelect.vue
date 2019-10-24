<template>
    <el-select v-model="value" :size="size" :placeholder="placeholder" :clearable="clearable" :disabled="disabled" @change="onChange">
      <el-option v-for="(item, index) in options" :key="index" :label="item.name" :value="item.id" v-select-text></el-option>
    </el-select>
  </template>
  <script>
    export default {
      name: 'FloorSelect',
      created() {
        this.value = this.modelValue;
        this.loadSelectData();
      },
      data() {
        return {
          text: '',
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
          this.$get('back/assets/floor/select').then(res => {
            if (res.isSuccess) {
              this.options = res.data;
              if (this.defaultOption) {
                if (this.defaultOption === true) {
                  this.options.unshift({
                    name: '请选择楼号',
                    id: ''
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
        },
        onTextChange(text) {
          this.$emit('text-change', text);
        }
      }
    };
  </script>
  <style scoped></style>
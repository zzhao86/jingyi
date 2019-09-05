<template>
  <assets-cascader v-model="value" :options="options" :size="size" :clearable="clearable" :show-all-levels="showAllLevels" :placeholder="placeholder" @change="onChange" v-select-text>
  </assets-cascader>
</template>
<script>
  import AssetsCascader from './Cascader';
  export default {
    name: 'AssetsCategorySelect',
    components: {
      AssetsCascader
    },
    created() {
      this.loadSelectData();
    },
    data() {
      return {
        value: '',
        options: []
      };
    },
    model: {
      prop: 'category',
      event: 'returnValue'
    },
    props: {
      // v-model值
      category: String,
      // 输入框尺寸
      size: {
        type: String,
        default: 'medium',
        required: false
      },
      // 下拉默认选项。{label,value}
      defaultOption: {
        type: [Object, Boolean],
        default: null,
        required: false
      },
      // 不可选的选项的value
      disabledOption: {
        type: String,
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
      category: function(val, old) {
        this.value = val;
      },
      disabledOption: function(val, old) {
        this.loadSelectData();
      }
    },
    methods: {
      // 加载下拉数据
      loadSelectData() {
        this.$get('back/assets/category/tree').then(res => {
          if (res.isSuccess) {
            // 添加默认选项
            if (this.defaultOption) {
              if (this.defaultOption === true) {
                res.data.unshift({
                  label: '请选择分类',
                  value: ''
                });
              } else {
                res.data.unshift(this.defaultOption);
              }
            }
            // 设置禁选选项
            if (this.disabledOption) {
              let item = findItemInTree(res.data, this.disabledOption);
              if (item) {
                item.disabled = true;
                item.children = null;
              }
            }
          }
          this.options = res.data;
          this.value = this.category;
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
  const findItemInTree = function(array, id) {
    if (!array || array.length == 0) {
      return null;
    }
    var result = null;
    for (let i = 0; i < array.length; i++) {
      let item = array[i];
      if (item.id === id) {
        result = item;
      }
      if (result) {
        return result;
      } else {
        if (item.children && item.children.length > 0) {
          result = findItemInTree(item.children, id);
          if (result) {
            return result;
          }
        }
      }
    }
  };
</script>
<style scoped></style>

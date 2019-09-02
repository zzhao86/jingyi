<template>
  <assets-cascader v-model="value" :options="options" :size="size" :show-all-levels="showAllLevels" :placeholder="placeholder" @change="onChange"> </assets-cascader>
</template>
<script>
  import AssetsCascader from './Cascader';
  export default {
    name: 'AssetsPositionSelect',
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
      prop: 'position',
      event: 'returnValue'
    },
    props: {
      // v-model值
      position: String,
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
      // 输入框占位文本
      placeholder: {
        type: String,
        default: '请选择资产位置',
        required: false
      }
    },
    watch: {
      disabledOption: function(val, old) {
        this.loadSelectData();
      }
    },
    methods: {
      // 加载下拉数据
      loadSelectData() {
        this.$get('back/assets/position/tree').then(res => {
          if (res.isSuccess) {
            // 添加默认选项
            if (this.defaultOption) {
              if (this.defaultOption === true) {
                res.data.unshift({
                  label: '请选择位置',
                  value: 'all'
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
          this.value = this.position;
        });
      },
      onChange() {
        this.$emit('returnValue', this.value == 'all' ? '' : this.value);
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

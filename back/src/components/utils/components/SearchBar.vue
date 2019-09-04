<template>
  <div class="search-bar">
    <div class="tag-wrapper">
      
    </div>
    <div class="search-wrapper">
      <el-input v-model="value" size="small" clearable :placeholder="placeholder" @clear="onSearchClick" @keyup.enter.native="onSearchClick">
        <el-button slot="append" icon="el-icon-search" @click="onSearchClick"></el-button>
      </el-input>
      <el-button type="default" size="small" @click="onShowAdvanceClick" v-show="showAdvance">高级查询</el-button>
    </div>
  </div>
</template>
<script>
  export default {
    name: 'SearchBar',
    created() {},
    data() {
      return {
        value: ''
      };
    },
    model: {
      prop: 'mValue',
      event: 'returnValue'
    },
    props: {
      mValue: String,
      // 是否需要高级查询。如果需要，必须定义advance事件
      showAdvance: {
        type: Boolean,
        default: false,
        required: false
      },
      // 输入框占位文本
      placeholder: {
        type: String,
        default: '请输入搜索关键字',
        required: false
      }
    },
    methods: {
      onShowAdvanceClick() {
        this.$emit('advance');
      },
      onSearchClick() {
        this.$emit('returnValue', this.value);
        this.$emit('search');
      }
    }
  };
</script>

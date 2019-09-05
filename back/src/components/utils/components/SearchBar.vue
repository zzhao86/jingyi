<template>
  <div class="search-bar">
    <!-- 条件tag -->
    <div class="tag-wrapper">
      <el-tag class="search-tag" effect="plain" type="primary" size="small" closable v-for="(item,index) in searchTags" :key="index" @close="onSearchTagClose(item)">{{
        item.label + '：' + item.text
      }}</el-tag>
    </div>
    <!-- 关键字 -->
    <div class="search-wrapper">
      <el-input v-model="value.keywords" size="small" clearable :placeholder="placeholder" @clear="onSearchClick" @keyup.enter.native="onSearchClick">
        <el-button slot="append" icon="el-icon-search" @click="onSearchClick"></el-button>
      </el-input>
      <el-button type="default" size="small" @click="onShowAdvanceClick" v-show="showAdvance">高级查询</el-button>
    </div>

    <!-- 高级查询 -->
    <el-drawer custom-class="advance-search" size="400px" title="高级查询" direction="rtl" :visible.sync="advanceVisible">
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
    name: 'SearchBar',
    created() {},
    data() {
      return {
        value: {},
        searchTags: [],
        advanceVisible: false
      };
    },
    model: {
      prop: 'mValue',
      event: 'returnValue'
    },
    props: {
      mValue: Object,
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
    watch: {
      tags: function(val, old) {
        console.log('tags-watch', val);
        this.searchTags = val;
      }
    },
    methods: {
      // 显示高级查询侧窗
      onShowAdvanceClick() {
        this.advanceVisible = true;
      },
      // 普通查询
      onSearchClick() {
        this.searchTags = [];
        if (this.value.keywords) {
          this.searchTags.push({
            key: 'keywords',
            label: '关键字',
            text: this.value.keywords
          });
        }
        this.$emit('returnValue', this.value);
        this.$emit('search');
      },
      // 高级查询
      onAdvanceSearchClick() {
        this.advanceVisible = false;
        this.$emit('advance-search');
      },
      // 高级查询条件重置
      onSearchResetClick() {
        this.$emit('reset');
      },
      // 查询条件Tag删除事件
      onSearchTagClose(item) {
        for (let i = 0; i < this.searchTags.length; i++) {
          let tag = this.searchTags[i];
          if (item.key == tag.key) {
            this.searchTags.remove(tag);
          }
        }
        this.mValue[item.key] = '';
        if (item.key == 'keywords') {
          this.$emit('search');
        } else {
          this.$emit('advance-search');
        }
      },
      // 组合查询条件Tag数组
      composeSearchTags(label, text) {
        this.searchTags = [];
        for (let key in this.mValue) {
          let value = this.mValue[key];
          if (!value) continue;
          let tag = {
            key: key,
            label: label[key],
            text: text[key]
          };
          this.searchTags.push(tag);
        }
      }
    }
  };
</script>

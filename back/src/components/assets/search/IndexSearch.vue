<template>
    <search-bar v-model="query" :tags="tags" show-advance placeholder="请输入资产名称或编号关键字" @search="onSearchClick" @advance-search="onAdvanceSearchClick" @reset="onResetClick">
      <template slot="content">
        <el-form :model="query" ref="advanceSearch" label-position="top" label-width="100px" size="mini">
          <!-- <el-row :gutter="10">
            <el-col :span="12">
              <el-form-item label="资产分类" prop="category">
                <assets-category v-model="query.category" clearable placeholder="资产分类" @text-change="onAssetsCategoryTextChange"></assets-category>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="资产位置" prop="position">
                <assets-position v-model="query.position" clearable placeholder="资产位置" @text-change="onAssetsPositionTextChange"></assets-position>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="10">
            <el-col :span="12">
              <el-form-item label="资产状态" prop="status">
                <assets-status v-model="query.status" clearable placeholder="资产状态" @text-change="onAssetsStatusTextChange"></assets-status>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="资产使用状态" prop="useStatus">
                <assets-usestatus v-model="query.useStatus" clearable placeholder="资产使用状态" @text-change="onAssetsUseStatusTextChange"></assets-usestatus>
              </el-form-item>
            </el-col>
          </el-row> -->
        </el-form>
      </template>
    </search-bar>
  </template>
  <script>
    import SearchBar from '@/components/utils/components/SearchBar';
    export default {
      name: 'AssetsIndexSearch',
      components: {
        SearchBar,
      },
      created() {},
      data() {
        return {
          query: {
            keywords: '',
            category: '',
            position: '',
            status: '',
            useStatus: ''
          },
          tagLabel: {
            keywords: '关键字',
            category: '分类',
            position: '位置',
            status: '状态',
            useStatus: '使用状态'
          },
          tagText: {},
          tags: []
        };
      },
      props: {},
      methods: {
        // 重置
        onResetClick() {
          this.$refs['advanceSearch'].resetFields();
        },
        // 普通查询
        onSearchClick() {
          this.$emit('search', this.query);
        },
        // 高级查询
        onAdvanceSearchClick() {
          this.$children[0].composeSearchTags(this.tagLabel, this.tagText);
          this.$emit('search', this.query);
        },
        // 资产分类下拉框本文改变事件
        onAssetsCategoryTextChange(text) {
          this.tagText['category'] = text;
        },
        // 资产位置下拉框本文改变事件
        onAssetsPositionTextChange(text) {
          this.tagText['position'] = text;
        },
        // 资产状态下拉框本文改变事件
        onAssetsStatusTextChange(text) {
          this.tagText['status'] = text;
        },
        // 资产使用状态下拉框本文改变事件
        onAssetsUseStatusTextChange(text) {
          this.tagText['useStatus'] = text;
        }
      }
    };
  </script>
  <style></style>
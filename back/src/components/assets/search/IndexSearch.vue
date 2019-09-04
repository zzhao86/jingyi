<template>
  <search-advance @search="onSearchClick" @reset="onResetClick">
    <template slot="content">
      <el-form :model="query" ref="advanceSearch" label-position="top" label-width="100px" size="mini">
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="资产分类" prop="category">
              <assets-category v-model="query.category" clearable placeholder="资产分类"></assets-category>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="资产位置" prop="position">
              <assets-position v-model="query.position" clearable placeholder="资产位置"></assets-position>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="资产状态" prop="status">
              <assets-status v-model="query.status" clearable placeholder="资产状态"></assets-status>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="资产使用状态" prop="useStatus">
              <assets-usestatus v-model="query.useStatus" clearable placeholder="资产使用状态"></assets-usestatus>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-tag type="info" closable v-for="(item,index) in tags" :key="index">{{ item.label + '：' + item.text }}</el-tag>
    </template>
  </search-advance>
</template>
<script>
  import SearchAdvance from '@/components/utils/components/SearchAdvance';
  import AssetsCategory from '../select/CategorySelect';
  import AssetsPosition from '../select/PositionSelect';
  import AssetsStatus from '../select/StatusSelect';
  import AssetsUsestatus from '../select/UseStatusSelect';

  export default {
    name: 'AssetsIndexSearch',
    components: {
      SearchAdvance,
      AssetsCategory,
      AssetsPosition,
      AssetsStatus,
      AssetsUsestatus
    },
    created() {},
    data() {
      return {
        query: {
          category: '',
          position: '',
          status: '',
          useStatus: ''
        },
        tagText: {
          category: '分类',
          position: '位置',
          status: '状态',
          useStatus: '使用状态'
        },
        tags: []
      };
    },
    props: {},
    methods: {
      open() {
        this.$children[0].visibleSync = true;
      },
      onResetClick() {
        this.$refs['advanceSearch'].resetFields();
      },
      onSearchClick() {
        this.tags = [];
        for (let item in this.query) {
          let value = this.query[item];
          if (!value) continue;
          let tag = {
            label: this.tagText[item],
            text: value
          };
          this.tags.push(tag);
        }
        // this.$emit('search', this.query);
      }
    }
  };
</script>
<style></style>

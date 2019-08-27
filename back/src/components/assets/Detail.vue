<template>
  <div class="app-main">
    <div class="main-header">
      <div class="title">资产详情</div>
      <div class="buttons">
        <el-button type="default" size="small" @click="$router.push('/assets')">返回</el-button>
        <el-button type="primary" size="small" @click="onSaveClick">确定</el-button>
      </div>
    </div>
    <el-form ref="form" :rules="rules" :disabled="disabled" :model="detailData" label-width="100px">
      <el-row>
        <el-col :span="8">
          <el-form-item prop="code" label="资产编码">
            <el-input v-model="detailData.code" disabled placeholder="资产编码将由系统自动生成"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item prop="name" label="资产名称">
            <el-input v-model="detailData.name" placeholder="请输入资产名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item prop="admin" label="管理员">
            <el-input v-model="detailData.admin" placeholder="请输入资产管理员"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item prop="categoryId" label="资产分类">
            <el-select v-model="detailData.categoryId" placeholder="请选择资产分类">
              <el-option v-for="item in categories" :key="item.value" :label="item.label" :value="item.value"> </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item prop="brand" label="品牌">
            <el-input v-model="detailData.brand" placeholder="请输入品牌"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item prop="model" label="型号">
            <el-input v-model="detailData.model" placeholder="请选择型号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item prop="positionId" label="资产位置">
            <el-input v-model="detailData.positionId" placeholder="请选择资产位置"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item prop="code" label="资产状态">
            <el-input v-model="detailData.status" placeholder="请选择资产资产状态"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item prop="code" label="使用状态">
            <el-input v-model="detailData.useStatus" placeholder="请选择资产使用状态"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item prop="purchasingMethod" label="购置方式">
            <el-input v-model="detailData.purchasingMethod" placeholder="请选择资产购置方式"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item prop="startDate" label="购置日期">
            <el-date-picker v-model="detailData.startDate" align="right" type="date" placeholder="请选择购置日期"> </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item prop="useTerm" label="预计使用期限">
            <el-input v-model="detailData.useTerm" placeholder="请输入预计使用期限">
              <template slot="suffix">
                <span>月</span>
              </template>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>
<script>
  export default {
    name: 'AssetsDetail',
    created() {
      this.loadCategorySelect();
    },
    data() {
      return {
        id: '',
        detailData: {},
        disabled: false,
        categories: [],
        rules: {}
      };
    },
    methods: {
      loadCategorySelect() {
        this.$get('back/assets/category/select').then(res => {
          console.log(res);
        });
      },
      onSaveClick() {}
    }
  };
</script>
<style scoped>
  .el-form {
    max-width: 1200px;
  }
</style>

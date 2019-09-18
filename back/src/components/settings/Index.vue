<template>
  <div class="app-main">
    <div class="main-header">
      <div class="title">系统设置</div>
      <div class="buttons"></div>
    </div>
    <el-tabs v-model="tabsDefaultName" type="card">
      <el-tab-pane :label="tab.label" :name="tab.name" v-for="tab in tabs" :key="tab.name">
        <el-form :ref="tab.name" label-width="200px">
          <el-form-item :label="item.name" :prop="item.code" v-for="(item, index) in settings.filter(o=>{return o.type === tab.name})" :key="index">
            <div class="form-item">
              <el-input v-model="item.value" :ref="item.id" :readonly="item.readonly" v-if="item.displayMode === 'text'"></el-input>
              <el-radio-group v-model="item.value" :ref="item.id" :disabled="item.readonly" v-if="item.displayMode === 'radio'">
                <el-radio border :label="o.value" v-for="o in JSON.parse(item.optionValues)" :key="o.value">{{ o.label }}</el-radio>
              </el-radio-group>
              <el-checkbox-group v-model="item.value" :ref="item.id" :disabled="item.readonly" v-if="item.displayMode === 'checkbox'">
                <el-checkbox border :label="o.value" v-for="o in JSON.parse(item.optionValues)" :key="o.value">{{ o.label }}</el-checkbox>
              </el-checkbox-group>
              <div class="form-item-buttons">
                <el-button type="primary" plain size="small" icon="fa fa-pencil-square-o" title="修改" @click="onFormItemEditClick(item)" v-if="item.readonly"></el-button>
                <el-button-group v-else>
                  <el-button type="success" size="small" @click="onSaveChange(item)">确定</el-button>
                  <el-button type="default" size="small" @click="onFormItemCancelClick(item)">取消</el-button>
                </el-button-group>
              </div>
            </div>
            <div class="form-item-desc" v-show="item.readonly">{{ item.description }}</div>
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
  export default {
    name: 'SettingsIndex',
    created() {
      this.loadFormData();
    },
    data() {
      return {
        tabsDefaultName: '',
        settings: [],
        tabs: [
          { label: '钉钉配置', name: 'DINGTALK' }
          // { label: '系统配置', name: 'SYSTEM' }
        ],
        formItemTemp: null
      };
    },
    methods: {
      loadFormData: function() {
        this.$get('back/settings/list').then(res => {
          if (res.isSuccess) {
            for (let i = 0; i < res.data.length; i++) {
              var item = res.data[i];
              item.readonly = true;
              if (item.displayMode === 'checkbox') {
                if (item.value) {
                  item.value = item.value.split(',');
                } else {
                  item.value = [];
                }
              }
            }
          }
          this.settings = res.data;
          this.tabsDefaultName = this.tabs[0].name;
        });
      },
      onFormItemEditClick: function(item) {
        for (let i = 0; i < this.settings.length; i++) {
          let item = this.settings[i];
          if (!item.readonly) {
            item.value = this.formItemTemp;
          }
          item.readonly = true;
        }
        item.readonly = false;
        this.formItemTemp = item.value;
        if (item.displayMode === 'text') {
          var element = this.$refs[item.id][0];
          element.focus();
        }
      },
      onFormItemCancelClick: function(item) {
        item.readonly = true;
        item.value = this.formItemTemp;
      },
      onSaveChange: function(item) {
        if (!item.value) {
          this.$error('配置项“' + item.name + '”不能为空');
          return;
        }
        if (item.value === this.formItemTemp) {
          item.readonly = true;
          return;
        }
        if (item.displayMode === 'checkbox') {
          var value = item.value.join(',');
          item.value = value;
        }
        this.$post('back/settings/save', item).then(res => {
          item.value = item.value.split(',');
          item.readonly = true;
          this.$success('修改成功');
        });
      }
    }
  };
</script>
<style>
  .form-item {
    display: flex;
  }
  .form-item .el-input,
  .form-item .el-radio-group,
  .form-item .el-checkbox-group {
    flex: 1;
  }
  .form-item .form-item-buttons {
    margin-left: 10px;
    width: 150px;
    text-align: left;
  }
</style>

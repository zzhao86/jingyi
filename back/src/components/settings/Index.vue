<template>
  <div class="app-main">
    <div class="main-header">
      <div class="title">系统设置</div>
      <div class="buttons"></div>
    </div>
    <el-tabs v-model="tabsDefaultName" type="card">
      <el-tab-pane label="钉钉配置" name="dingtalk">
        <el-form ref="form" label-width="200px">
          <div v-for="(item, index) in settings" :key="index">
            <el-form-item :label="item.name" v-if="item.displayMode === 'text'">
              <el-input v-model="item.value" @change="onSaveChange(item)"></el-input>
              <div class="form-item-desc">{{ item.description }}</div>
            </el-form-item>
            <el-form-item :label="item.name" v-else-if="item.displayMode === 'radio'">
              <el-radio-group v-model="item.value">
                <el-radio border :label="o.value" v-for="o in JSON.parse(item.optionValues)" :key="o.value">{{ o.label }}</el-radio>
              </el-radio-group>
              <div class="form-item-desc">{{ item.description }}</div>
            </el-form-item>
            <el-form-item :label="item.name" v-else-if="item.displayMode === 'checkbox'">
              <el-checkbox-group v-model="item.value">
                <el-checkbox border :label="o.value" v-for="o in JSON.parse(item.optionValues)" :key="o.value">{{ o.label }}</el-checkbox>
              </el-checkbox-group>
              <div class="form-item-desc">{{ item.description }}</div>
            </el-form-item>
          </div>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="系统配置" name="system">系统配置</el-tab-pane>
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
        tabsDefaultName: 'dingtalk',
        settings: []
      };
    },
    methods: {
      loadFormData: function() {
        this.$http
          .get('back/settings/list', {
            params: {
              type: 'DINGTALK'
            }
          })
          .then(res => {
            for (let i = 0; i < res.data.length; i++) {
              var item = res.data[i];
              if (item.displayMode === 'checkbox') {
                if (item.value) {
                  item.value = item.value.split(',');
                } else {
                  item.value = [];
                }
              }
            }
            this.settings = res.data;
          });
      },
      onSaveChange: function(item) {
        if (!item.value) {
          this.$error('配置项“' + item.name + '”不能为空');
          return;
        }
        this.$post('back/settings/save', item).then(res => {
          this.$success('修改成功');
          console.log(res);
        });
      }
    }
  };
</script>
<style></style>

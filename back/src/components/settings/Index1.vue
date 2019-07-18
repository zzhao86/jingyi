<template>
  <div class="app-main">
    <div class="main-header">
      <div class="title">钉钉基础设置</div>
      <div class="buttons">
        <!-- <el-button type="primary" size="small" @click="onSubmitClick">保存设置</el-button> -->
      </div>
    </div>
    <el-tabs v-model="tabsDefaultName" type="card">
      <el-tab-pane label="钉钉配置" name="dingtalk">
        <el-form ref="form" :model="form" :rules="rules" label-width="200px">
          <el-form-item label="钉钉CorpId" prop="DINGTALK_CORPID">
            <el-input v-model="form.DINGTALK_CORPID"></el-input>
            <div class="form-item-desc">12313213</div>
          </el-form-item>
          <el-form-item label="钉钉SsoSecret" prop="DINGTALK_SSOSECRET">
            <el-input v-model="form.DINGTALK_SSOSECRET"></el-input>
          </el-form-item>
          <el-form-item label="钉钉AppKey" prop="DINGTALK_APPKEY">
            <el-input v-model="form.DINGTALK_APPKEY"></el-input>
          </el-form-item>
          <el-form-item label="钉钉AppSecret" prop="DINGTALK_APPSECRET">
            <el-input v-model="form.DINGTALK_APPSECRET"></el-input>
          </el-form-item>
          <el-form-item label="钉钉应用AgentId" prop="DINGTALK_AGENTID">
            <el-input v-model="form.DINGTALK_AGENTID"></el-input>
          </el-form-item>
          <el-form-item label="钉钉扫码登录Id" prop="DINGTALK_QRCODE_ID">
            <el-input v-model="form.DINGTALK_QRCODE_ID"></el-input>
          </el-form-item>
          <el-form-item label="钉钉扫码登录Secret" prop="DINGTALK_QRCODE_SECRET">
            <el-input v-model="form.DINGTALK_QRCODE_SECRET"></el-input>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="系统配置" name="system">系统配置</el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
  import qs from 'qs';

  export default {
    name: 'SettingsIndex',
    created() {
      this.loadFormData();
    },
    data() {
      return {
        tabsDefaultName: 'dingtalk',
        settings: [],
        form: {
          DINGTALK_CORPID: '',
          DINGTALK_SSOSECRET: '',
          DINGTALK_APPKEY: '',
          DINGTALK_APPSECRET: '',
          DINGTALK_AGENTID: '',
          DINGTALK_QRCODE_ID: '',
          DINGTALK_QRCODE_SECRET: ''
        },
        rules: {
          DINGTALK_CORPID: [{ required: true, message: '请输入钉钉CorpId', trigger: 'blur' }],
          DINGTALK_SSOSECRET: [{ required: true, message: '请输入钉钉SsoSecret', trigger: 'blur' }],
          DINGTALK_APPKEY: [{ required: true, message: '请输入钉钉AppKey', trigger: 'blur' }],
          DINGTALK_APPSECRET: [{ required: true, message: '请输入钉钉AppSecret', trigger: 'blur' }],
          DINGTALK_AGENTID: [{ required: true, message: '请输入钉钉AgentId', trigger: 'blur' }],
          DINGTALK_QRCODE_ID: [{ required: true, message: '请输入钉钉扫码登录Id', trigger: 'blur' }],
          DINGTALK_QRCODE_SECRET: [{ required: true, message: '请输入钉钉扫码登录Secret', trigger: 'blur' }]
        }
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
            this.settings = res.data;
            for (var i in this.settings) {
              var item = this.settings[i];
              for (var key in this.form) {
                if (key == item.code) {
                  this.form[key] = item.value;
                  break;
                }
              }
            }
          });
      },
      onSubmitClick: function() {
        this.$refs['form'].validate(valid => {
          if (valid) {
            for (var key in this.form) {
              for (var i in this.settings) {
                var item = this.settings[i];
                if (key == item.code) {
                  item.value = this.form[key];
                  this.settings[i] = item;
                }
              }
            }
            this.$post('back/settings/save', qs.stringify({ settings: JSON.stringify(this.settings) })).then(res => {
              if (res.isSuccess) {
                this.$success('保存成功');
              }
            });
          }
        });
      }
    }
  };
</script>
<style>
</style>

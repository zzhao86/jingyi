<template>
  <div>
    <div class="main-header">
      <div class="title">钉钉基础设置</div>
      <div class="buttons">
        <el-button type="warning" size="small" @click="onResetClick">重置</el-button>
        <el-button type="primary" size="small" @click="onSubmitClick">保存设置</el-button>
      </div>
    </div>

    <el-form ref="form" :model="form" :rules="rules" label-width="200px">
      <el-form-item label="钉钉CorpId" prop="DINGTALK_CORPID">
        <el-input v-model="form.DINGTALK_CORPID"></el-input>
      </el-form-item>
      <el-form-item label="钉钉SSOSecret" prop="DINGTALK_SSOSECRET">
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
    </el-form>
  </div>
</template>
<script>
  import qs from 'qs';

  export default {
    name: 'Home',
    created() {
      this.loadFormData();
    },
    data() {
      return {
        settings: [],
        form: {
          DINGTALK_CORPID: '',
          DINGTALK_SSOSECRET: '',
          DINGTALK_APPKEY: '',
          DINGTALK_APPSECRET: '',
          DINGTALK_AGENTID: ''
        },
        rules: {
          DINGTALK_CORPID: [{ required: true, message: '请输入钉钉CorpId', trigger: 'blur' }],
          DINGTALK_SSOSECRET: [{ required: true, message: '请输入钉钉SsoSecret', trigger: 'blur' }],
          DINGTALK_APPKEY: [{ required: true, message: '请输入钉钉AppKey', trigger: 'blur' }],
          DINGTALK_APPSECRET: [{ required: true, message: '请输入钉钉AppSecret', trigger: 'blur' }],
          DINGTALK_AGENTID: [{ required: true, message: '请输入钉钉AgentId', trigger: 'blur' }]
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
            this.$http.post('back/settings/save', qs.stringify({ settings: JSON.stringify(this.settings) })).then(res => {
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
<style></style>

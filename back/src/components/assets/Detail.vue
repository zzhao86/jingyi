<template>
  <div class="app-main">
    <div class="main-header">
      <div class="title">资产详情</div>
      <div class="buttons">
        <el-button type="default" size="small" @click="$router.push('/assets')">返回</el-button>
        <el-button type="primary" size="small" @click="onSaveClick" v-show="!disabled">确定</el-button>
      </div>
    </div>
    <el-form ref="form" :rules="rules" :disabled="disabled" :model="detailData" label-width="100px">
      <fieldset>
        <legend>基本信息</legend>
        <el-row>
          <el-col :span="8">
            <el-form-item prop="code" label="资产编码">
              <el-input v-model="detailData.codeLabel" disabled placeholder="资产编码将由系统自动生成"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item prop="name" label="资产名称">
              <el-input v-model="detailData.name" placeholder="请输入资产名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item prop="admin" label="管理员">
              <el-select v-model="detailData.admin" filterable placeholder="请选择资产管理员">
                <el-option v-for="(item,index) in adminSelectList" :key="index" :label="item.name" :value="item.id"> </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item prop="categoryId" label="资产分类">
              <assets-category v-model="detailData.categoryId" placeholder="请选择资产分类"></assets-category>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item prop="brand" label="品牌">
              <el-input v-model="detailData.brand" placeholder="请输入品牌"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item prop="model" label="型号">
              <el-input v-model="detailData.model" placeholder="请输入型号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item prop="positionId" label="资产位置">
              <assets-position v-model="detailData.positionId" placeholder="请选择资产位置"></assets-position>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item prop="status" label="资产状态">
              <assets-status v-model="detailData.status" placeholder="请选择资产资产状态" disabled></assets-status>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item prop="useStatus" label="使用状态">
              <assets-usestatus v-model="detailData.useStatus" placeholder="请选择资产使用状态"></assets-usestatus>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item prop="purchasingMethod" label="购置方式">
              <Assets-purchasing-method v-model="detailData.purchasingMethod" placeholder="请选择资产购置方式"></Assets-purchasing-method>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item prop="startDate" label="购置日期">
              <el-date-picker v-model="detailData.startDate" type="date" value-format="yyyy-MM-dd HH:mm" placeholder="请选择购置日期"> </el-date-picker>
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
        <el-row>
          <el-col :span="8">
            <el-form-item prop="amount" label="资产金额">
              <el-input v-model="detailData.amount" placeholder="请输入资产金额" @input="onAmountInput">
                <template slot="suffix">
                  <span>元</span>
                </template>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="16">
            <el-form-item prop="remark" label="备注">
              <el-input v-model="detailData.remark" type="textarea" :rows="5" placeholder="请输入资产备注信息"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item prop="imageUrl" label="资产图片">
              <el-upload class="assets-uploader" :action="$global.baseUrl + 'back/assets/upload/image'" :show-file-list="false" :on-success="onUploadAssetsImageSuccess">
                <div class="cover" v-if="detailData.imageUrl">
                  <div class="cover-mask">
                    <i class="el-icon-view" @click.stop="onPreviewAssetsImageClick"></i>
                    <i class="el-icon-delete" @click.stop="onDeleteAssetsImageClick" v-show="!disabled"></i>
                  </div>
                  <img :src="$global.baseUrl + detailData.imageUrl" />
                </div>
                <i v-else class="el-icon-plus assets-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
      </fieldset>
      <fieldset>
        <legend>维保信息</legend>
        <el-row>
          <el-col :span="8">
            <el-form-item prop="supplier" label="供应商">
              <el-input v-model="detailData.supplier" placeholder="请输入供应商名称"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item prop="supplierContact" label="联系人">
              <el-input v-model="detailData.supplierContact" placeholder="请输入供应商联系人"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item prop="supplierMobile" label="联系方式">
              <el-input v-model="detailData.supplierMobile" placeholder="请输入供应商联系方式"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item prop="maintDate" label="维保到期">
              <el-date-picker v-model="detailData.maintDate" type="date" value-format="yyyy-MM-dd HH:mm" placeholder="请选择购置日期"> </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item prop="maintNotes" label="维保说明">
              <el-input v-model="detailData.maintNotes" type="textarea" :rows="3" placeholder="请输入维保说明信息"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </fieldset>
    </el-form>

    <!-- 资产图片预览图 -->
    <el-dialog title="资产图片预览" custom-class="priview-dialog" width="540px" :visible.sync="dialogPreviewVisibled">
      <img :src="$global.baseUrl + detailData.imageUrl" />
    </el-dialog>
  </div>
</template>
<script>
  import AssetsCategory from './select/CategorySelect';
  import AssetsPosition from './select/PositionSelect';
  import AssetsStatus from './select/StatusSelect';
  import AssetsUsestatus from './select/UseStatusSelect';
  import AssetsPurchasingMethod from './select/PurchasingMethodSelect';
  export default {
    name: 'AssetsDetail',
    components: {
      AssetsCategory,
      AssetsPosition,
      AssetsStatus,
      AssetsUsestatus,
      AssetsPurchasingMethod
    },
    created() {
      const mode = this.$route.params.mode;
      const query = this.$route.query;
      if (query.id) {
        this.id = query.id;
        this.loadDetailData();
      }
      this.disabled = mode == 'view';
      this.loadPurchasingMethodSelect();
      this.loadAdminListSelect();
    },
    data() {
      return {
        id: '',
        detailData: {
          status: 100,
          useStatus: 100,
          purchasingMethod: 1,
          imageUrl: '',
          admin: this.$global.user.id,
          amount: 0
        },
        disabled: false,
        purchasingMethodSelectList: [],
        adminSelectList: [],
        dialogPreviewVisibled: false,
        adminSelectLoading: false,
        rules: {
          name: [
            {
              required: true,
              message: '请输入资产名称',
              trigger: 'blur'
            }
          ],
          admin: [
            {
              required: true,
              message: '请选择资产管理员',
              trigger: 'blur'
            }
          ],
          categoryId: [
            {
              required: true,
              message: '请选择资产分类',
              trigger: 'blur'
            }
          ],
          brand: [
            {
              required: true,
              message: '请输入资产品牌',
              trigger: 'blur'
            }
          ],
          positionId: [
            {
              required: true,
              message: '请选择资产所在位置',
              trigger: 'blur'
            }
          ],
          status: [
            {
              required: true,
              message: '请选择资产状态',
              trigger: 'blur'
            }
          ],
          useStatus: [
            {
              required: true,
              message: '请选择资产使用状态',
              trigger: 'blur'
            }
          ],
          purchasingMethod: [
            {
              required: true,
              message: '请选择资产购置方式',
              trigger: 'blur'
            }
          ],
          amount: [
            {
              required: true,
              message: '请输入资产金额',
              trigger: 'blur'
            }
          ]
        }
      };
    },
    methods: {
      // 加载详情数据
      loadDetailData() {
        this.$get('back/assets/detail', {
          params: {
            id: this.id
          }
        }).then(res => {
          if (res.isSuccess) {
            this.detailData = res.data;
          }
        });
      },
      // 加载资产购置方式下拉列表数据
      loadPurchasingMethodSelect() {
        this.$get('back/assets/purchasingMethod/select').then(res => {
          if (res.isSuccess) {
            this.purchasingMethodSelectList = res.data;
          }
        });
      },
      // 远程加载管理员下拉列表
      loadAdminListSelect(keywords) {
        this.adminSelectLoading = true;
        this.$get('back/user/admin_list', {
          params: {
            keywords: keywords
          }
        }).then(res => {
          if (res.isSuccess) {
            this.adminSelectList = res.data;
          }
          this.adminSelectLoading = false;
        });
      },
      // 资产金额框输入事件
      onAmountInput() {
        this.detailData.amount = this.detailData.amount.replace(/[^\d.]/, '');
      },
      // 上传资产图片成功事件
      onUploadAssetsImageSuccess(res, file, fileList) {
        if (res.isSuccess) {
          this.detailData.imageUrl = res.data[0].url;
        }
      },
      // 删除资产图片
      onDeleteAssetsImageClick() {
        this.detailData.imageUrl = '';
      },
      // 预览资产图片大图
      onPreviewAssetsImageClick() {
        this.dialogPreviewVisibled = true;
      },
      // 保存表单
      onSaveClick() {
        const vue = this;
        this.$refs['form'].validate(valid => {
          if (valid) {
            vue.$post('back/assets/save', vue.detailData).then(res => {
              if (res.isSuccess) {
                vue.$success('保存成功', () => {
                  vue.$router.push('/assets');
                });
              }
            });
          }
        });
      }
    }
  };
</script>
<style scoped>
  .el-form {
    max-width: 1200px;
  }

  .priview-dialog img {
    max-width: 500px;
    max-height: 500px;
  }
</style>
<style>
  .priview-dialog .el-dialog__body {
    text-align: center;
  }

  .assets-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .assets-uploader .el-upload:hover {
    border-color: #409eff;
  }
  .assets-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 120px;
    height: 120px;
    line-height: 120px;
    text-align: center;
  }
  .assets-uploader .cover {
    width: 120px;
    height: 120px;
  }
  .assets-uploader .cover .cover-mask {
    position: absolute;
    width: 120px;
    height: 120px;
    line-height: 120px;
    display: none;
    background-color: rgba(0, 0, 0, 0.5);
    font-size: 28px;
    color: #ffffff;
  }
  .assets-uploader .cover:hover .cover-mask {
    display: block;
  }
  .assets-uploader .cover img {
    max-width: 120px;
    max-height: 120px;
  }
</style>

<template>
  <div class="app-main">
    <div class="main-header">
      <div class="title">资产详情</div>
      <div class="buttons">
        <el-button type="default" size="small" @click="$router.push('/assets')">返回</el-button>
        <el-button type="primary" size="small" @click="onSaveClick" v-show="!disabled">确定</el-button>
      </div>
    </div>
    <el-form ref="form" :rules="rules" :disabled="disabled" :model="detailData" label-width="150px">
      <el-form-item prop="positionId" label="位置">
        <assets-position v-model="detailData.positionId" placeholder="请选择位置"></assets-position>
      </el-form-item>
      <el-form-item prop="floorId" label="楼号">
        <assets-floor v-model="detailData.floorId" placeholder="请选择楼号"></assets-floor>
      </el-form-item>
      <el-form-item prop="name" label="资源名称">
        <el-input v-model="detailData.name" placeholder="请输入资源名称（合同约定建筑物名称）"></el-input>
      </el-form-item>
      <el-form-item prop="roomNo" label="房间号">
        <el-row>
          <el-col :span="8">
            <el-input v-model="detailData.roomNo" placeholder="请输入楼层房间号"></el-input>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item prop="leaseArea" label="租赁面积">
        <el-row>
          <el-col :span="8">
            <el-input type="number" v-model="detailData.leaseArea" placeholder="请输入租赁面积" min="0">
              <template slot="suffix">
                <span>㎡</span>
              </template>
            </el-input>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item prop="tenantCompany" label="承租单位">
        <el-input v-model="detailData.tenantCompany" placeholder="请输入承租单位"></el-input>
      </el-form-item>
      <el-form-item prop="leaseTerm" label="租赁年限">
        <el-row>
          <el-col :span="8">
            <el-input type="number" v-model="detailData.leaseTerm" min="0" placeholder="请输入租赁年限">
              <template slot="suffix">
                <span>年</span>
              </template>
            </el-input>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item prop="date" label="租赁合同时间">
        <el-date-picker
          v-model="detailData.date"
          type="daterange"
          range-separator="至"
          start-placeholder="合同起始时间"
          end-placeholder="合同截止时间"
          format="yyyy-MM-dd"
          value-format="yyyy-MM-dd HH:mm"
          @change="onLeaseDateChange"
        >
        </el-date-picker>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  import AssetsPosition from './select/PositionSelect';
  import AssetsFloor from './select/FloorSelect';
  export default {
    name: 'AssetsDetail',
    components: {
      AssetsPosition,
      AssetsFloor
    },
    created() {
      const mode = this.$route.params.mode;
      const query = this.$route.query;
      if (query.id) {
        this.id = query.id;
        this.loadTableData();
      }
      this.disabled = mode == 'view';
    },
    data() {
      return {
        id: '',
        disabled: true,
        detailData: {
          positionId: '',
          floorId: '',
          date: []
        },
        rules: {
          positionId: [
            {
              required: true,
              message: '请选择位置',
              trigger: 'blur'
            }
          ],
          floorId: [
            {
              required: true,
              message: '请选择楼号',
              trigger: 'blur'
            }
          ],
          name: [
            {
              required: true,
              message: '请输入合同约定建筑物名称',
              trigger: 'blur'
            }
          ],
          leaseArea: [
            {
              required: true,
              message: '请输入租赁面积',
              trigger: 'blur'
            }
          ],
          tenantCompany: [
            {
              required: true,
              message: '请输入承租单位',
              trigger: 'blur'
            }
          ],
          roomNo: [
            {
              required: true,
              message: '请输入楼层房间号',
              trigger: 'blur'
            }
          ],
          date: [
            {
              required: true,
              message: '请选择租赁合同时间',
              trigger: ['blur', 'change']
            }
          ],
          leaseTerm: [
            {
              required: true,
              message: '请输入租赁年限',
              trigger: 'blur'
            }
          ]
        }
      };
    },
    methods: {
      loadTableData() {
        this.$get('back/assets/detail', {
          params: {
            id: this.id
          }
        }).then(res => {
          if (res.isSuccess) {
            this.detailData = res.data;
            this.detailData.date = [res.data.startDate, res.data.endDate];
          }
        });
      },
      onLeaseDateChange(values) {
        if (values) {
          this.detailData.startDate = values[0];
          this.detailData.endDate = values[1];
        } else {
          this.detailData.startDate = '';
          this.detailData.endDate = '';
        }
      },
      onSaveClick() {
        const vue = this;
        this.$refs['form'].validate(valid => {
          if (valid) {
            vue.$post('back/assets/save', this.detailData).then(res => {
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

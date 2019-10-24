<template>
    <div class="app-main">
      <div class="main-header">
        <div class="title">楼号管理</div>
        <div class="buttons">
          <el-button type="primary" size="small" @click="onAddClick">新建</el-button>
          <el-button type="danger" size="small" :disabled="!tableSelected || tableSelected.length == 0" @click="onDeleteBatchClick">删除</el-button>
        </div>
      </div>
      <div class="main-table">
        <search-bar v-model="params.query" @search="onSearchClick"></search-bar>
        <el-table :data="tableData" ref="table" stripe v-auto-height :max-height="maxHeight" @selection-change="onTableSelectionChange">
          <el-table-column type="selection" align="center" width="30"> </el-table-column>
          <el-table-column align="center" label="序号" width="50">
            <template slot-scope="scope">{{ scope.$index + (params.index - 1) * params.size + 1 }}</template>
          </el-table-column>
          <el-table-column prop="name" label="楼号名称">
            <template slot-scope="scope">
              <el-link type="primary" @click="onViewClick(scope.row)">{{ scope.row.name }}</el-link>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="100">
            <template slot-scope="scope">
              <div class="options-buttons">
                <el-button type="primary" size="mini" @click="onEditClick(scope.row)">编辑</el-button>
                <el-button type="danger" size="mini" @click="onDeleteClick(scope.row)">删除</el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <pagination :params="params" :total="total" @page-change="onPageChange" @size-change="onSizeChange"></pagination>
      </div>
  
      <!-- 详情Dialog -->
      <el-dialog title="楼号详情" :visible.sync="dialogDetailVisible" width="600px" :before-close="onDialogDetailClose">
        <el-form :model="detail" ref="form" :rules="rules" :disabled="detailFormDisabled" label-width="100px">
          <el-form-item prop="name" label="位置名称">
            <el-input v-model="detail.name" placeholder="请输入楼号名称" autofocus></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="onDialogDetailClose">取 消</el-button>
          <el-button type="primary" @click="onSaveClick" v-show="!detailFormDisabled">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </template>
  <script>
    import Pagination from '../utils/components/Pagination';
    import SearchBar from '@/components/utils/components/SearchBar';
    export default {
      name: 'AssetsFloorIndex',
      components: {
        Pagination,
        SearchBar
      },
      created() {
        this.loadTableData();
      },
      data() {
        return {
          params: {
            index: 1,
            size: this.$global.pageSize,
            query: {
              keywords: ''
            }
          },
          total: 0,
          tableData: [],
          maxHeight: 500,
          tableSelected: [],
          detail: {},
          detailFormDisabled: false,
          dialogDetailVisible: false,
          importFile: {},
          rules: {
            name: [
              {
                required: true,
                message: '请输入楼号名称',
                trigger: 'blur'
              }
            ]
          }
        };
      },
      methods: {
        loadTableData() {
          this.$get('back/assets/floor/list', {
            params: this.params
          }).then(res => {
            if (res.isSuccess) {
              this.total = res.total;
              this.tableData = res.data;
            }
          });
        },
        onPageChange(index) {
          this.params.index = index;
          this.loadTableData();
        },
        onSizeChange(size) {
          this.params.size = size;
          this.loadTableData();
        },
        // 刷新列表数据
        flushTableData() {
          this.params.index = 1;
          this.loadTableData();
        },
        // 搜索按钮点击事件
        onSearchClick() {
          this.params.index = 1;
          this.loadTableData();
        },
        // 显示详情弹窗
        showDialogDetail() {
          this.dialogDetailVisible = true;
        },
        // 新建按钮事件
        onAddClick() {
          this.detailFormDisabled = false;
          this.showDialogDetail();
        },
        // 查看按钮事件
        onViewClick(row) {
          this.detail = row;
          this.detailFormDisabled = true;
          this.showDialogDetail();
        },
        // 编辑按钮事件
        onEditClick(row) {
          this.detail = row;
          this.detailFormDisabled = false;
          this.showDialogDetail();
        },
        // 删除按钮事件
        onDeleteClick(row) {
          const vue = this;
          this.$confirm(`确定要删除楼号 “${row.name}” 吗？`, () => {
            vue
              .$get('back/assets/floor/delete', {
                params: {
                  id: row.id
                }
              })
              .then(res => {
                this.$success('删除成功');
                vue.flushTableData();
              });
          });
        },
        onDeleteBatchClick: function() {
          const vue = this;
          if (!vue.tableSelected.length || vue.tableSelected.length == 0) {
            vue.$message.error('请选择要删除的数据行');
            return;
          }
          let ids = new Array();
          for (let i = 0; i < vue.tableSelected.length; i++) {
            ids.push(vue.tableSelected[i].id);
          }
          vue.$confirm(`确定要删除所有选中项吗？`, () => {
            vue.$post('back/assets/floor/delete_batch', ids).then(res => {
              if (res.isSuccess) {
                vue.tableData.removes(vue.tableSelected);
                vue.$success(`删除成功`);
              }
            });
          });
        },
        onTableSelectionChange: function(selection) {
          this.tableSelected = selection;
        },
        // 关闭详情弹窗事件
        onDialogDetailClose() {
          this.dialogDetailVisible = false;
          this.$refs['form'].clearValidate();
          this.detail = {};
        },
        // 详情保存
        onSaveClick() {
          this.$refs['form'].validate(valid => {
            if (valid) {
              this.$post('back/assets/floor/save', this.detail).then(res => {
                if (res.isSuccess) {
                  this.onDialogDetailClose();
                  this.flushTableData();
                  this.$success('保存成功');
                }
              });
            }
          });
        }
      }
    };
  </script>
  <style scoped></style>
  
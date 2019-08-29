<template>
  <div class="app-main">
    <div class="main-header">
      <div class="title">资产分类</div>
      <div class="buttons">
        <el-button type="primary" size="small" @click="onAddClick">新建</el-button>
        <el-button type="warning" size="small" @click="showImportClick">导入</el-button>
      </div>
    </div>

    <div class="main-container">
      <div class="tree-container">
        <el-tree ref="tree" :data="treeData" node-key="id" default-expand-all :expand-on-click-node="false" highlight-current @node-click="onTreeNodeClick"></el-tree>
      </div>

      <div class="main-table">
        <el-table :data="tableData" ref="table" stripe v-auto-height :max-height="maxHeight">
          <el-table-column align="center" label="序号" width="50">
            <template slot-scope="scope">{{ scope.$index + (params.index - 1) * params.size + 1 }}</template>
          </el-table-column>
          <el-table-column prop="name" label="分类名称">
            <template slot-scope="scope">
              <el-link type="primary" @click="onViewClick(scope.row)">{{ scope.row.name }}</el-link>
            </template>
          </el-table-column>
          <el-table-column prop="parentName" label="上级分类"></el-table-column>
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
      <el-dialog title="资产详情" :visible.sync="dialogDetailVisible" :before-close="onDialogDetailClose">
        <el-form :model="detail" ref="form" :rules="rules" :disabled="detailFormDisabled" label-width="100px">
          <el-form-item prop="name" label="分类名称">
            <el-input v-model="detail.name" placeholder="请输入资产分类名称"></el-input>
          </el-form-item>
          <el-form-item prop="parentId" label="上级分类">
            <el-cascader
              v-model="detail.parentId"
              :options="cascaderData"
              :show-all-levels="false"
              :props="{ checkStrictly: true, emitPath: false, expandTrigger: 'hover' }"
              placeholder="请选择上级分类"
            >
            </el-cascader>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="onDialogDetailClose">取 消</el-button>
          <el-button type="primary" @click="onSaveClick" v-show="!detailFormDisabled">确 定</el-button>
        </span>
      </el-dialog>

      <!-- 批量导入Dialog -->
      <el-dialog title="位置批量导入" :visible.sync="dialogImportVisible" width="600px">
        <ul class="ul-import">
          <li>
            <span>请先下载模板文件：</span>
            <a class="el-link el-link--primary" target="_blank" :href="$global.baseUrl + 'back/assets/category/template/download'">导入模板-Excel文件</a>
          </li>
          <li>
            <el-upload
              ref="upload"
              :auto-upload="false"
              :show-file-list="false"
              with-credentials
              :action="$global.baseUrl + 'back/assets/category/import'"
              accept=".xls, .xlsx"
              :on-change="onImportFileChange"
              :on-success="onImportFileSuccess"
            >
              <el-button size="mini" type="primary">选择Excel文件</el-button>
              <span>{{ importFile.name }}</span>
            </el-upload>
          </li>
        </ul>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogImportVisible = false">取 消</el-button>
          <el-button type="primary" @click="onImportClick" :disabled="importButtonDisabled">导入</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
  import Pagination from '../utils/components/Pagination';
  export default {
    name: 'AssetsCategory',
    components: {
      Pagination
    },
    created() {
      this.loadTreeData();
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
        treeData: [],
        tableData: [],
        maxHeight: 500,
        detail: {},
        detailFormDisabled: false,
        dialogDetailVisible: false,
        importButtonDisabled: true,
        dialogImportVisible: false,
        cascaderData: [],
        importFile: {},
        rules: {
          name: [
            {
              required: true,
              message: '请输入资产分类名称',
              trigger: 'blur'
            }
          ],
          parentId: [
            {
              required: true,
              message: '请选择上级分类',
              trigger: 'blur'
            }
          ]
        }
      };
    },
    methods: {
      loadTableData() {
        this.$get('back/assets/category/list', {
          params: this.params
        }).then(res => {
          if (res.isSuccess) {
            this.total = res.total;
            this.tableData = res.data;
          }
        });
      },
      // 加载Tree数据
      loadTreeData() {
        this.$get('back/assets/category/tree').then(res => {
          if (res.isSuccess) {
            res.data.unshift({
              id: null,
              label: '所有分类',
              value: null
            });
            this.treeData = res.data;
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
        this.loadTreeData();
        this.loadTableData();
      },
      // Tree节点点击事件
      onTreeNodeClick(data, node, store) {
        this.params.index = 1;
        this.params.query.treePId = data.id;
        this.loadTableData();
      },
      // 加载资产分类Cascader数据
      loadCascaderData() {
        const vue = this;
        this.$get('back/assets/category/tree').then(res => {
          if (res.isSuccess) {
            this.cascaderData = res.data;
            // 添加“无上级分类”节点
            this.cascaderData.unshift({
              id: '0',
              label: '无上级分类',
              value: '0'
            });

            // 查找自身节点
            var arrayFindItem = function(array, id) {
              if (!array || array.length == 0) {
                return null;
              }
              var result = null;
              for (let i = 0; i < array.length; i++) {
                let item = array[i];
                if (item.id === id) {
                  result = item;
                }
                if (result) {
                  return result;
                } else {
                  if (item.children && item.children.length > 0) {
                    result = arrayFindItem(item.children, id);
                    if (result) {
                      return result;
                    }
                  }
                }
              }
            };
            let item = arrayFindItem(this.cascaderData, vue.detail.id);
            if (item) {
              item.disabled = true;
              item.children = null;
            }
          }
        });
      },
      // 显示详情弹窗
      showDialogDetail() {
        this.dialogDetailVisible = true;
        this.loadCascaderData();
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
        this.$confirm(`确定要删除资产分类 “${row.name}” 及其所有下级分类吗？`, () => {
          vue
            .$get('back/assets/category/delete', {
              params: {
                id: row.id
              }
            })
            .then(res => {
              vue.flushTableData();
            });
        });
      },
      // 显示导入Dialog
      showImportClick() {
        this.dialogImportVisible = true;
      },
      // 导入文件改变事件
      onImportFileChange(file, fileList) {
        this.importFile = file;
        if (file.name) {
          this.importButtonDisabled = false;
        }
      },
      // 导入文件成功事件
      onImportFileSuccess(res, file, fileList) {
        if (res.isSuccess) {
          this.$success('导入成功');
          this.dialogImportVisible = false;
          this.flushTableData();
        }
      },
      // 上传导入的Excel文件
      onImportClick() {
        if (this.importFile) {
          this.$refs['upload'].submit();
        }
      },
      // 关闭详情弹窗事件
      onDialogDetailClose() {
        this.detail = {};
        this.$refs['form'].clearValidate();
        this.dialogDetailVisible = false;
      },
      // 详情保存
      onSaveClick() {
        this.$refs['form'].validate(valid => {
          if (valid) {
            this.$post('back/assets/category/save', this.detail).then(res => {
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

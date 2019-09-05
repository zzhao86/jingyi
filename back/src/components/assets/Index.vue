<template>
  <div class="app-main">
    <div class="main-header">
      <div class="title">资产列表</div>
      <div class="buttons">
        <el-button type="primary" size="small" @click="onAddClick">新建</el-button>
        <el-button-group>
          <el-button type="warning" size="small" @click="showImportClick">导入</el-button>
          <el-dropdown @command="onExportClick">
            <el-button type="success" size="small"> 导出<i class="el-icon-arrow-down el-icon--right"></i> </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="result">导出查询结果</el-dropdown-item>
              <el-dropdown-item command="all">导出全部资产</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-button-group>
      </div>
    </div>
    <div class="main-table">
      <index-search v-model="params.query.keywords" @search="onSearchClick"></index-search>
      <el-table :data="tableData" ref="table" stripe v-auto-height :max-height="maxHeight">
        <el-table-column align="center" label="序号" width="50">
          <template slot-scope="scope">{{ scope.$index + (params.index - 1) * params.size + 1 }}</template>
        </el-table-column>
        <el-table-column prop="statusName" label="资产状态" width="120">
          <template slot-scope="scope">
            <el-tag effect="light" size="mini" v-if="scope.row.status == 100">{{ scope.row.statusName }}</el-tag>
            <el-tag effect="dark" size="mini" type="warning" v-else-if="scope.row.status == 200">{{ scope.row.statusName }}</el-tag>
            <el-tag effect="dark" size="mini" type="success" v-else-if="scope.row.status == 300">{{ scope.row.statusName }}</el-tag>
            <el-tag effect="dark" size="mini" type="warning" v-else-if="scope.row.status == 400">{{ scope.row.statusName }}</el-tag>
            <el-tag effect="dark" size="mini" type="warning" v-else-if="scope.row.status == 500">{{ scope.row.statusName }}</el-tag>
            <el-tag effect="dark" size="mini" type="success" v-else-if="scope.row.status == 600">{{ scope.row.statusName }}</el-tag>
            <el-tag effect="dark" size="mini" type="warning" v-else-if="scope.row.status == 700">{{ scope.row.statusName }}</el-tag>
            <el-tag effect="dark" size="mini" type="success" v-else-if="scope.row.status == 800">{{ scope.row.statusName }}</el-tag>
            <el-tag effect="dark" size="mini" type="warning" v-else-if="scope.row.status == 900">{{ scope.row.statusName }}</el-tag>
            <el-tag effect="dark" size="mini" type="warning" v-else-if="scope.row.status == 1000">{{ scope.row.statusName }}</el-tag>
            <el-tag effect="dark" size="mini" type="warning" v-else-if="scope.row.status == 1100">{{ scope.row.statusName }}</el-tag>
            <el-tag effect="dark" size="mini" type="warning" v-else-if="scope.row.status == 1200">{{ scope.row.statusName }}</el-tag>
            <el-tag size="mini" type="warning" v-else>未知</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="code" label="资产编码" width="100">
          <template slot-scope="scope">
            <el-link type="primary" @click="onViewClick(scope.row)">{{ scope.row.code }}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="资产名称" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-link type="primary" @click="onViewClick(scope.row)">{{ scope.row.name }}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="categoryName" label="分类" width="150" show-overflow-tooltip></el-table-column>
        <el-table-column prop="positionName" label="位置" width="100"></el-table-column>
        <el-table-column prop="brand" label="品牌" width="100"></el-table-column>
        <el-table-column prop="model" label="型号" width="100"></el-table-column>
        <el-table-column prop="useStatusName" label="使用状态" width="100"></el-table-column>
        <el-table-column prop="adminName" label="管理员" width="100"></el-table-column>
        <el-table-column prop="purchasingMethodName" label="购置方式" width="100"></el-table-column>
        <el-table-column prop="startDate" label="购置/起租日期" width="150">
          <template slot-scope="scope">
            <span>{{ scope.row.startDate ? scope.row.startDate.substring(0, 10) : '' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100" fixed="right">
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

    <!-- 批量导入Dialog -->
    <el-dialog title="资产批量导入" :visible.sync="dialogImportVisible" width="600px">
      <ul class="ul-import">
        <li>
          <span>请先下载模板文件：</span>
          <a class="el-link el-link--primary" target="_blank" :href="$global.baseUrl + 'back/assets/template/download'">导入模板-Excel文件</a>
        </li>
        <li>
          <el-upload
            ref="upload"
            :auto-upload="false"
            :show-file-list="false"
            with-credentials
            :action="$global.baseUrl + 'back/assets/import'"
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
</template>
<script>
  import Pagination from '../utils/components/Pagination';
  import IndexSearch from './search/IndexSearch';
  export default {
    name: 'AssetsIndex',
    components: {
      Pagination,
      IndexSearch
    },
    created() {
      this.loadTableData();
    },
    data() {
      return {
        params: {
          index: 1,
          size: this.$global.pageSize,
          query: {}
        },
        total: 0,
        maxHeight: 500,
        tableData: [],
        importFile: [],
        importButtonDisabled: false,
        dialogImportVisible: false
      };
    },
    methods: {
      loadTableData() {
        this.$get('back/assets/list', {
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
      // 搜索按钮点击事件
      onSearchClick(query) {
        this.params.index = 1;
        this.params.query = query;
        this.loadTableData();
      },
      // 显示高级查询窗口
      onShowAdvanceSearch() {
        this.$refs['advanceSearch'].open();
      },
      // 高级搜索按钮点击事件
      onAdvanceSearchClick(query) {
        this.params.query = query;
        this.onSearchClick();
      },
      // 刷新主数据
      flushDataTable() {
        this.params.index = 1;
        this.loadTableData();
      },
      // 新建点击事件
      onAddClick() {
        this.$router.push('/assets/detail/add');
      },
      // 编辑点击事件
      onEditClick(row) {
        this.$router.push('/assets/detail/edit?id=' + row.id);
      },
      // 查看点击事件
      onViewClick(row) {
        this.$router.push('/assets/detail/view?id=' + row.id);
      },
      // 删除点击事件
      onDeleteClick(row) {
        const vue = this;
        this.$confirm(`确定要删除资产 “${row.name}” 吗？`, () => {
          this.$get('back/assets/delete', {
            params: {
              id: row.id
            }
          }).then(res => {
            if (res.isSuccess) {
              vue.tableData.remove(row);
              vue.$success('删除成功');
            }
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
      // 导出Excel文件
      onExportClick(command) {
        switch (command) {
          case 'result':
            console.log('导出查询结果');
            break;
          case 'all':
            console.log('导出所有资产');
            break;
        }
      }
    }
  };
</script>
<style scoped>
  .ul-import li {
    list-style: decimal;
    margin-bottom: 10px;
  }
  .ul-import .upload {
    display: inline-block;
  }
</style>

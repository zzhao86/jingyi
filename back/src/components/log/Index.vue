<template>
  <div class="app-main">
    <div class="main-header">
      <div class="title">日志管理</div>
      <div class="buttons"></div>
    </div>

    <div class="main-container">
      <div class="main-table">
        <index-search v-model="params.query" @search="onSearchClick"></index-search>
        <el-table :data="tableData" ref="table" stripe v-auto-height :max-height="maxHeight">
          <el-table-column align="center" label="序号" width="50">
            <template slot-scope="scope">{{ scope.$index + (params.index - 1) * params.size + 1 }}</template>
          </el-table-column>
          <el-table-column prop="typeName" label="日志类型"></el-table-column>
          <el-table-column prop="module" label="操作模块"></el-table-column>
          <el-table-column prop="method" label="操作方法"></el-table-column>
          <el-table-column prop="ip" label="客户端IP"></el-table-column>
          <el-table-column prop="date" label="操作时间"></el-table-column>
          <el-table-column prop="userName" label="操作人"></el-table-column>
          <el-table-column label="操作" width="100">
            <template slot-scope="scope">
              <div class="options-buttons">
                <el-button type="success" size="mini" @click="onViewClick(scope.row)">查看详情</el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <pagination :params="params" :total="total" @page-change="onPageChange" @size-change="onSizeChange"></pagination>
      </div>
    </div>

    <!-- 详情Dialog -->
    <el-dialog title="日志详情" :visible.sync="dialogDetailVisible" width="800px">
      <el-form :model="detail" ref="form" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item prop="typeName" label="日志类型">
              <el-input v-model="detail.typeName" readonly></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="module" label="操作模块">
              <el-input v-model="detail.module" readonly></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item prop="method" label="操作方法">
              <el-input v-model="detail.method" readonly></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="ip" label="客户端IP">
              <el-input v-model="detail.ip" readonly></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item prop="parameter" label="方法参数">
              <el-input v-model="detail.parameter" type="textarea" :rows="3" resize="none" readonly></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item prop="date" label="操作时间">
              <el-input v-model="detail.date" readonly></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="userName" label="操作人">
              <el-input v-model="detail.userName" readonly></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item prop="detail" label="日志详细">
              <el-input v-model="detail.detail" type="textarea" :rows="5" resize="none" readonly></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogDetailVisible = false">关 闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
  import Pagination from '../utils/components/Pagination';
  import IndexSearch from './search/IndexSearch';
  export default {
    name: 'LogIndex',
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
          query: {
            keywords: ''
          }
        },
        total: 0,
        tableData: [],
        maxHeight: 500,
        detail: {},
        dialogDetailVisible: false
      };
    },
    methods: {
      loadTableData() {
        this.$get('back/syslog/list', {
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
        this.loadTreeData();
        this.params.index = 1;
        this.loadTableData();
      },
      // 搜索按钮点击事件
      onSearchClick(query) {
        this.params.query = query;
        this.params.index = 1;
        this.loadTableData();
      },
      // 查看日志详情
      onViewClick(row) {
        this.$get('back/syslog/detail', {
          params: {
            id: row.id
          }
        }).then(res => {
          if (res.isSuccess) {
            this.detail = res.data;
            this.dialogDetailVisible = true;
          }
        });
      }
    }
  };
</script>
<style scoped></style>

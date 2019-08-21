<template>
  <div class="app-main">
    <div class="main-header">
      <div class="title">用户管理</div>
      <div class="buttons">
        <el-button type="primary" size="small" @click="onSyncUserClick">从钉钉同步用户</el-button>
      </div>
    </div>
    <div class="main-container">
      <div class="tree-container">
        <div class="tree-button">
          <el-button type="primary" size="small" @click="onSyncDeptClick">从钉钉同步部门</el-button>
        </div>
        <el-tree
          ref="tree"
          :props="props"
          :data="treeData"
          node-key="id"
          :expand-on-click-node="false"
          :default-expanded-keys="treeExpandedKey"
          highlight-current
          :render-content="treeRenderContent"
          @node-click="treeNodeClick"
        ></el-tree>
      </div>
      <div class="main-table">
        <el-table :data="tableData" ref="table" stripe border v-auto-height :max-height="maxHeight" empty-text="当前部门暂无人员">
          <el-table-column align="center" label="序号" width="50">
            <template slot-scope="scope">{{ scope.$index + (params.index - 1) * params.size + 1 }}</template>
          </el-table-column>
          <el-table-column prop="name" label="姓名">
            <template slot-scope="scope">
              <el-link type="primary" @click="onViewClick(scope.row)">{{scope.row.name}}</el-link>
            </template>
          </el-table-column>
          <el-table-column prop="mobile" label="手机号" width="150"></el-table-column>
          <el-table-column prop="tel" label="办公电话" width="150"></el-table-column>
          <el-table-column prop="position" label="职位" width="150"></el-table-column>
          <el-table-column prop="type" label="用户类型" width="80">
            <template slot-scope="scope">
              <span v-show="scope.row.type == 1" class="el-link el-link--danger">主管理员</span>
              <span v-show="scope.row.type == 2" class="el-link el-link--warning">子管理员</span>
              <span v-show="scope.row.type == 99" class="el-link el-link--info">系统用户</span>
            </template>
          </el-table-column>
        </el-table>
        <pagination :params="params" :total="total" @page-change="onPageChange" @size-change="onSizeChange"></pagination>
      </div>
    </div>
  </div>
</template>
<script>
  import pagination from '../utils/components/Pagination';
  export default {
    name: 'UserIndex',
    components: {
      pagination
    },
    created() {
      this.loadTreeData();
    },
    data() {
      return {
        params: {
          index: 1,
          size: this.$global.pageSize,
          query: {
            name: '',
            deptId: ''
          }
        },
        total: 0,
        tableData: [],
        treeData: [],
        props: {
          id: 'id',
          label: 'name',
          children: 'children'
        },
        treeExpandedKey: [],
        maxHeight: 500
      };
    },
    methods: {
      // 加载成员Tabel数据
      loadTableData: function() {
        this.$http
          .get('back/user/list', {
            params: this.params
          })
          .then(res => {
            this.tableData = res.data;
            this.total = res.total;
          });
      },
      onPageChange: function(index) {
        this.params.index = index;
        this.loadTableData();
      },
      onSizeChange: function(size) {
        this.params.size = size;
        this.loadTableData();
      },
      // 加载Tree数据
      loadTreeData: function() {
        this.$get('back/dept/tree').then(res => {
          if (res.data) {
            // 添加Tree数据
            this.treeData = res.data;
            // 设置默认展开的Tree节点
            var id = res.data[0].id;
            this.treeExpandedKey.push(id);
            // 设置Table查询的部门条件
            this.params.query.deptId = id;
            this.loadTableData();
          }
        });
      },
      // 重新渲染Tree节点文本
      treeRenderContent: function(h, { node, data, store }) {
        var label = node.label;
        return (
          <a class='dept-tree-node' title={label}>
            {label}
          </a>
        );
      },
      // Tree节点点击事件
      treeNodeClick: function(data, node, store) {
        this.params.index = 1;
        this.params.query.deptId = data.id;
        this.loadTableData();
      },
      // 同步部门按钮点击事件
      onSyncDeptClick: function() {
        const vue = this;
        this.$confirm('确定要从钉钉同步部门数据吗？', function() {
          var tree = vue.$refs['tree'];
          vue.$http
            .get('back/dept/init_dd', {
              params: {
                id: 1
              }
            })
            .then(res => {
              vue.$success('同步成功');
              vue.loadTreeData(0, null);
            });
        });
      },
      // 同步成员按钮点击事件
      onSyncUserClick: function() {
        const vue = this;
        this.$confirm('确定要从钉钉同步用户数据吗？', function() {
          vue.$http
            .get('back/user/init_dd', {
              params: {
                deptId: 1
              }
            })
            .then(res => {
              vue.$success(res.data);
              vue.loadTableData();
            });
        });
      },
      onViewClick: function(row) {
        this.$router.push('/user/detail/view?id=' + row.id);
      }
    }
  };
</script>
<style>
  .main-container {
    display: flex;
    height: 100%;
  }
  .tree-container {
    width: 200px;
    height: calc(100vh - 164px);
    margin-right: 20px;
    border: solid 1px #ebeef5;
  }
  .tree-container .tree-button {
    margin: 10px auto;
    text-align: center;
    padding-bottom: 10px;
    border-bottom: solid 1px #ebeef5;
  }
  .dept-tree-node {
    width: 180px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    font-size: 13px;
  }
  .main-table {
    flex: 1;
    overflow-x: auto;
    overflow-y: hidden;
  }
</style>

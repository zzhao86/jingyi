<template>
  <div class="app-main">
    <div class="main-header">
      <div class="title">成员管理</div>
      <div class="buttons">
        <el-button type="primary" size="small" @click="onSyncUserClick">同步成员</el-button>
      </div>
    </div>
    <div class="main-container">
      <div class="tree-container">
        <el-tree
          ref="tree"
          :props="props"
          lazy
          :data="treeData"
          node-key="id"
          highlight-current
          :render-content="treeRenderContent"
          @node-expand="treeNodeExpand"
          @node-click="treeNodeClick"
        ></el-tree>
      </div>
      <div class="main-table">
        <el-table :data="tableData" stripe :border="true">
          <el-table-column align="center" label="序号" width="50">
            <template slot-scope="scope">{{ scope.$index + (params.pageNum - 1) * params.pageSize + 1 }}</template>
          </el-table-column>
          <el-table-column prop="name" label="姓名"></el-table-column>
          <el-table-column prop="mobile" label="手机号" width="150"></el-table-column>
          <el-table-column prop="tel" label="办公电话" width="150"></el-table-column>
          <el-table-column prop="position" label="职位" width="150"></el-table-column>
          <el-table-column prop="type" label="用户类型" width="80">
            <template slot-scope="scope">
              <span v-show="scope.row.type == 1" class="el-link el-link--danger">主管理员</span>
              <span v-show="scope.row.type == 2" class="el-link el-link--warning">子管理员</span>
              <span v-show="scope.row.type == 99">普通用户</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="75">
            <template slot-scope="scope">
              <el-button-group>
                <el-button type="success" size="mini">查看</el-button>
              </el-button-group>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination background @current-change="PageChange" :current-page="params.pageNum" :page-size="params.pageSize" layout="total, prev, pager, next, jumper" :total="params.total">
        </el-pagination>
      </div>
    </div>
  </div>
</template>
<script>
  export default {
    name: 'UserIndex',
    created() {
      this.loadTableData();
      this.loadTreeData();
    },
    data() {
      return {
        params: {
          pageNum: 1,
          pageSize: 10,
          total: 0,
          condition: {
            name: '张'
          }
        },
        tableData: [],
        treeData: [],
        props: {
          id: 'id',
          label: 'name',
          isLeaf: function(data, node) {
            if (!data.childCount) {
              return true;
            }
            return !data.childCount > 0;
          }
        }
      };
    },
    methods: {
      // 加载成员Tabel数据
      loadTableData: function() {
        this.$http
          .get('back/user/list', {
            params: this.params,
          })
          .then(res => {
            this.tableData = res.data;
            this.params.total = res.total;
          });
      },
      PageChange: function(index) {
        this.params.pageNum = index;
        this.loadTableData();
      },
      // 加载Tree数据
      loadTreeData: function(pid, node) {
        this.$http
          .get('back/dept/tree', {
            params: {
              pid: pid
            }
          })
          .then(res => {
            var tree = this.$refs['tree'];
            if (!res.data || res.data.length == 0) {
              return;
            }
            if (!node) {
              // 当tree无任何节点时，添加data数组的方式添加根节点。
              if (this.treeData.length == 0) {
                this.treeData.push(res.data[0]);
              }
            } else {
              //当tree存在根节点时，用append的方式添加子节点。
              for (let i = 0; i < res.data.length; i++) {
                var isExists = false;
                var data = res.data[i];
                for (let j = 0; j < node.childNodes.length; j++) {
                  let childNode = node.childNodes[j];
                  if (childNode.data.id == data.id) {
                    isExists = true;
                    break;
                  }
                }
                if (!isExists) {
                  tree.append(data, node);
                }
              }
            }
          });
      },
      // 展开Tree节点事件
      treeNodeExpand: function(data, node, store) {
        this.loadTreeData(data.id, node);
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
        console.log(data);
        console.log(node);
      },
      // 同步成员按钮点击事件
      onSyncUserClick: function() {
        const vue = this;
        this.$confirm('确定要同步吗？', function() {
          var tree = vue.$refs['tree'];
          vue.$http
            .get('back/user/init_dd', {
              params: {
                deptId: 1
              }
            })
            .then(res => {
              vue.$success('同步成功');
              vue.loadTableData();
            });
        });
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
    height: calc(100vh - 170px);
    margin-right: 20px;
    border: solid 1px #ebeef5;
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
  }
</style>

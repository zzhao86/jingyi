<template>
  <div class="app-main">
    <div class="main-header">
      <div class="title">部门管理</div>
      <div class="buttons">
        <el-button type="primary" size="small" @click="onSyncDeptClick">同步部门</el-button>
      </div>
    </div>
    <el-tree ref="tree" :props="props" lazy :data="treeData" node-key="id" highlight-current @node-expand="treeNodeExpand"></el-tree>
  </div>
</template>
<script>
  export default {
    name: 'DeptIndex',
    created() {
      this.loadTreeData(0, null);
    },
    data() {
      return {
        keywords: '',
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
      // 加载数据
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
      // 展开节点事件
      treeNodeExpand: function(data, node, tree) {
        this.loadTreeData(data.id, node);
      },
      // 同步部门按钮点击事件
      onSyncDeptClick: function() {
        const vue = this;
        this.$confirm('确定要同步吗？', function() {
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
      }
    }
  };
</script>
<style></style>

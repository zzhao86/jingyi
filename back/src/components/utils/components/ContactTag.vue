<template>
  <div>
    <div class="contact-tag" @click="onScopeClick">
      <div class="tag-wrapper">
        <span class="tag-box" v-for="(item, index) in scope" :key="index">
          <el-tag size="mini" type="success" v-if="item.type == 'dept'">{{ item.name }}</el-tag>
          <el-tag size="mini" type="" v-else-if="item.type == 'user'">{{ item.name }}</el-tag>
        </span>
      </div>
    </div>
    <contact-choose :visible="contactVisible" :choosed="scope" @ok="onContactOk" @close="onContactClose"></contact-choose>
  </div>
</template>
<script>
  import ContactChoose from './ContactChoose';
  export default {
    name: 'TagBox',
    components: {
      ContactChoose
    },
    created() {},
    data() {
      return {
        scope: [],
        contactVisible: false
      };
    },
    watch: {
      data: function(val, old) {
        if (val) {
          this.scope = JSON.parse(val);
        }
      }
    },
    props: {
      data: {
        type: String,
        default: ''
      }
    },
    methods: {
      onScopeClick: function() {
        this.contactVisible = true;
      },
      onContactOk: function(choose) {
        this.contactVisible = false;
        this.scope = choose;
        this.$emit('choosed-scope', JSON.stringify(choose));
      },
      onContactClose: function() {
        this.contactVisible = false;
      }
    }
  };
</script>
<style scoped>
  .contact-tag {
    border: solid 1px #dcdfe6;
    border-radius: 4px;
    cursor: pointer;
    min-height: 40px;
    overflow: hidden;
  }
  .contact-tag:hover {
    border-color: #c0c4cc;
  }
  .contact-tag:focus {
    border-color: #409eff;
  }
  .contact-tag .tag-wrapper {
    padding: 10px 10px 0 10px;
    line-height: 20px;
    max-height: 100px;
    overflow-x: hidden;
    overflow-y: auto;
  }

  .contact-tag .tag-box {
    margin-right: 10px;
    display: inline-block;
    margin-bottom: 10px;
  }
</style>

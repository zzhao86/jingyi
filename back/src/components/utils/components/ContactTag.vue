<template>
  <div>
    <div class="contact-tag" v-bind:class="{ disabled: disabled }" @click="onScopeClick">
      <div class="contact-placeholder">{{ placeholder }}</div>
      <div class="tag-wrapper">
        <span class="tag-box" v-for="(item, index) in scope" :key="index">
          <el-tag size="mini" type="success" v-if="item.type == 'dept'">{{ item.name }}</el-tag>
          <el-tag size="mini" type="" v-else-if="item.type == 'user'">{{ item.name }}</el-tag>
        </span>
      </div>
      <contact-choose :visible="contactVisible" :choosed="scope" @ok="onContactOk" @close="onContactClose"></contact-choose>
    </div>
  </div>
</template>
<script>
  import ContactChoose from './ContactChoose';
  export default {
    name: 'ContactTag',
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
      },
      scope: function(val, old) {
        let placeholder = document.getElementsByClassName('contact-placeholder')[0];
        if (val && val.length > 0) {
          placeholder.style.display = 'none';
        } else {
          placeholder.style.display = 'block';
        }
      }
    },
    props: {
      data: {
        type: String,
        default: ''
      },
      disabled: {
        type: Boolean,
        default: false
      },
      placeholder: {
        type: String,
        default: ''
      }
    },
    methods: {
      onScopeClick: function() {
        if(this.disabled){
          return;
        }
        this.contactVisible = true;
      },
      onContactOk: function(choose) {
        this.contactVisible = false;
        this.scope = choose;
        let str = choose && choose.length > 0 ? JSON.stringify(choose) : '';
        this.$emit('choosed-scope', str);
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
  .contact-tag.disabled{
    background-color: #f5f7fa;
  }
  .contact-tag:hover {
    border-color: #c0c4cc;
  }
  .contact-tag:focus {
    border-color: #409eff;
  }
  .contact-tag .contact-placeholder {
    position: absolute;
    color: #c1c5cc;
    margin-left: 15px;
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

<template>
  <script id="editor" type="text/plain"></script>
</template>
<script>
  import '../../../../static/lib/ueditor/themes/default/css/ueditor.css';

  import '../../../../static/lib/ueditor/ueditor.config.js';
  import '../../../../static/lib/ueditor/ueditor.all.js';
  import '../../../../static/lib/ueditor/lang/zh-cn/zh-cn.js';
  import '../../../../static/lib/ueditor/ueditor.parse.min.js';

  export default {
    name: 'UE',
    data() {
      return {
        editor: null
      };
    },
    props: {
      content: {
        type: String
      },
      readonly: {
        type: Boolean,
        default: false
      },
      config: {
        type: Object,
        default: function() {
          return {
            serverUrl: this.$global.baseUrl + 'controller.ashx',
            toolbars: [
              [
                'undo',
                'redo',
                '|',
                'fontsize',
                'forecolor',
                'backcolor',
                'bold',
                'italic',
                'underline',
                'strikethrough',
                'removeformat',
                'formatmatch',
                '|',
                'link',
                'unlink',
                'anchor',
                '|',
                'simpleupload',
                'insertimage',
                '|',
                'justifyleft',
                'justifycenter',
                'justifyright',
                'justifyjustify',
                '|',
                'insertorderedlist',
                'insertunorderedlist',
                '|',
                'inserttable',
                'drafts'
              ]
            ],
            initialFrameWidth: 750,
            initialFrameHeight: 400,
            elementPathEnabled: false,
            wordCount: false,
            allowDivTransToP: false
          };
        },
        required: false
      }
    },
    mounted() {
      const self = this;
      if (!this.editor) {
        this.editor = UE.getEditor('editor', this.config);
      }
      this.editor.addListener('ready', function() {
        if (self.readonly) {
          self.editor.setDisabled();
        } else {
          self.editor.setEnabled();
        }
        self.editor.setContent(self.content);
      });
      this.editor.addListener('contentChange', function() {
        this.content = self.editor.getContent();
        self.$emit('content-change', this.content);
      });
    },
    methods: {
      getUEContent() {
        return this.editor.getContent();
      }
    },
    watch: {
      readonly: function(val, old) {
        const self = this;
        this.editor.addListener('ready', function() {
          if (val) {
            self.editor.setDisabled();
          } else {
            self.editor.setEnabled();
          }
        });
      }
    },
    destroyed() {
      this.editor.destroy();
    }
  };
</script>
<style>
  .edui-default{
    line-height: 1;
  }
</style>

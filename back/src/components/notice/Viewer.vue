<template>
  <div>
    <iframe :src="viewerUrl"></iframe>
  </div>
</template>
<script>
  export default {
    name: 'NoticeReplyViewer',
    created() {
      this.$nextTick(() => {
        this.loadPDF();
      });
    },
    data() {
      return {
        // pdfUlr: this.$global.baseUrl + 'upload/file/test.pdf',
        pdfUrl: this.$global.baseUrl + 'test/topdf',
        viewerUrl: ''
      };
    },
    methods: {
      loadPDF() {
        this.viewerUrl = '../../../static/lib/pdfjs/web/viewer.html?file=' + this.pdfUlr;
      }
    }
  };
</script>
<style scoped>
  iframe {
    position: fixed;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    width: 100%;
    height: 100%;
  }
</style>

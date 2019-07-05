export default {
  componentUpdated(el, binding, vnode) {
    const ctx = vnode.context;
    // if (!ctx || typeof ctx[binding.arg] === 'undefined' || ctx.autoHeightResizeListenner) return;

    ctx.autoHeightResizeListener = function() {
      let top = el.offsetTop;
      let cur = el.offsetParent;
      while (cur !== null) {
        top += cur.offsetTop;
        cur = cur.offsetParent;
      }
      // const h = window.innerHeight - top + binding.value;
      const h = window.innerHeight - top - 70;
      ctx['maxHeight'] = Math.max(h, 100);
    };
    window.addEventListener('resize', ctx.autoHeightResizeListener);
    setTimeout(ctx.autoHeightResizeListener, 50);
  },

  unbind(el, binding, vnode) {
    const ctx = vnode.context;
    if (ctx && ctx.autoHeightResizeListener) {
      window.removeEventListener('resize', ctx.autoHeightResizeListener);
      ctx.autoHeightResizeListener = null;
    }
  }
};

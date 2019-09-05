export default {
  componentUpdated(el, binding, vnode) {
    // console.log(vnode.context)
    var ctx = vnode.context;

    ctx.onTextChange(findInput(vnode.context));
  }
};

var findInput = function(node) {
  var children = node.$children;
  var value;
  if (children && children.length > 0) {
    for (let i = 0; i < children.length; i++) {
      var child = children[i];
      if (child.$vnode.tag.indexOf('ElInput') >= 0) {
        value = child.value;
        return value;
      } else {
        if (child.$children && child.$children.length > 0 && !value) {
          value = findInput(child);
          if (value) {
            return value;
          }
        }
      }
    }
  }
};

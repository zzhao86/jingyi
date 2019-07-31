/**
 * 日期格式化
 */
Date.prototype.format = function(format) {
  let o = {
    'M+': this.getMonth() + 1, //月份
    'd+': this.getDate(), //日
    'h+': this.getHours(), //小时
    'm+': this.getMinutes(), //分
    's+': this.getSeconds(), //秒
    'q+': Math.floor((this.getMonth() + 3) / 3), //季度
    S: this.getMilliseconds() //毫秒
  };
  if (/(y+)/.test(format)) {
    format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
  }
  for (let k in o) {
    if (new RegExp('(' + k + ')').test(format)) {
      format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ('00' + o[k]).substr(('' + o[k]).length));
    }
  }
  return format;
};

/**
 * 数组删除项
 */
Array.prototype.remove = function(item) {
  let index = this.indexOf(item);
  if (index >= 0) {
    this.splice(index, 1);
  } else {
    console.error(`数组中未找到此项，${JSON.stringify(item)}`);
  }
};

Array.prototype.removes = function(array) {
  if (!array || array.length == 0) {
    console.error('数组为空');
    return;
  }
  for (let i = 0; i < array.length; i++) {
    let item = array[i];
    let index = this.indexOf(item);
    if (index >= 0) {
      this.splice(index, 1);
    } else {
      console.error(`数组中未找到此项，${JSON.stringify(item)}`);
    }
  }
};

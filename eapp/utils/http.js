var baseUrl = 'http://192.168.0.8:5050/';
var baseRequest = (url, config = {}) => {
	return new Promise(function(resolve, reject) {
		config.url = baseUrl + url;
		config.success = res => {
			if (res.status >= 200 && res.status < 300) {
				resolve(res.data);
			} else {
				reject(res);
			}
		};
		config.fail = err => {
			reject(err);
		};
		config.complete = () => {
			dd.hideLoading();
		};
		// 显示loading
		dd.showLoading();
		dd.httpRequest(config);
	});
}
var get = (url, config = {}) => {
	config.method = 'GET';
	return baseRequest(url, config);
};
var post = (url, config = {}) => {
	config.method = 'POST';
	return baseRequest(url, config);
};

module.exports = {
	baseUrl: baseUrl,
	get: get,
	post: post
}
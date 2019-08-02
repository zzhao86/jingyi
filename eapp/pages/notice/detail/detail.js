import http from '../../../utils/http';
const app = getApp();

Page({
	data: {
		id: '',
		detail: {}
	},
	/**
	 * 加载详情数据
	 */
	loadDetailData() {
		http.get('client/notice/detail', {
			data: {
				id: this.data.id
			}
		}).then(res => {
			console.log(res);
			if (res.isSuccess) {
				this.setData({
					detail: res.data
				});
			}
		});
	},
	/**
	 * 页面加载
	 */
	onLoad(query) {
		if (query.id) {
			this.setData({
				id: query.id
			});
			this.loadDetailData();
		}
	},
	/**
	 * 页面加载完成
	 */
	onReady() {

	},
	/**
	 * 页面显示
	 */
	onShow() {

	},
	/**
	 * 页面隐藏
	 */
	onHide() {

	},
	/**
	 * 页面被关闭
	 */
	onUnload() {
	},
	/**
	 * 标题被点击
	 */
	onTitleClick() {

	},
	/**
	 * 页面顶部下拉
	 */
	onPullDownRefresh() {
		this.loadListData();
	},
	/**
	 * 页面底部上滑
	 */
	onReachBottom() {

	},
	/**
	 * 返回自定义分享信息
	 */
	onShareAppMessage() {
		return {
			title: '公告',
			desc: '公告列表',
			imageUrl: app.globalData.baseApiUrl + '/static/images/login-bg.jpg',
			path: '/pages/notice/index/index',
		};
	},
});

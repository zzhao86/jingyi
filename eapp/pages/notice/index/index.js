import http from '../../../utils/http';

const app = getApp();

Page({
	data: {
		params: {
			index: 1,
			size: 20,
			query: {
				userId: app.globalData.user.id
			}
		},
		total: 0,
		baseApiUrl: app.globalData.baseApiUrl,
		list: []
	},
	/**
	 * 加载
	 */
	loadListData() {
		http.get('client/notice/list', {
			params: {
				params: this.data.params
			}
		}).then(res => {
			if (res.isSuccess) {
				this.setData({
					list: res.data
				});
			}
			dd.stopPullDownRefresh()
		})
	},
	/**
	 * 点击查看事件
	 */
	onViewClick(e) {
		let id = e.target.dataset.id;
		dd.navigateTo({
			url: `/pages/notice/detail/detail?id=${id}`
		});
	},
	/**
	 * 封面图片加载错误处理
	 */
	onCoverImageError(e) {
		let item = e.target.dataset.item;
		let list = new Array();
		for (let i = 0; i < this.data.list.length; i++) {
			let o = this.data.list[i];
			if (item.id == o.id) {
				o.coverUrl = '/static/images/image.png';
			}
			list.push(o);
		}
		this.setData({
			list: list
		});
	},
	/**
	 * 页面加载
	 */
	onLoad(query) {
		this.loadListData();
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

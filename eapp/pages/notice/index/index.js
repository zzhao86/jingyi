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
		baseApiUrl: app.globalData.baseApiUrl,
		list: []
	},
	onLoad(query) {
		// 页面加载
		http.get('client/notice/list', {
			params: {
				params: this.data.params
			}
		}).then(res => {
			this.setData({
				list: res.data
			});
		})
	},
	onReady() {
		// 页面加载完成
	},
	onShow() {
		// 页面显示
	},
	onHide() {
		// 页面隐藏
	},
	onUnload() {
		// 页面被关闭
	},
	onTitleClick() {
		// 标题被点击
	},
	onPullDownRefresh() {
		// 页面被下拉
	},
	onReachBottom() {
		// 页面被拉到底部
	},
	onShareAppMessage() {
		// 返回自定义分享信息
		return {
			title: '公告',
			desc: '公告列表',
			path: '/pages/notice/index/index',
		};
	},
});

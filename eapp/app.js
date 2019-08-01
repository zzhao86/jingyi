import http from './utils/http'

App({
	onLaunch(options) {
		
	},
	onShow(options) {
		// 从后台被 scheme 重新打开
		// options.query == {number:1}
	},
	globalData: {
		baseApiUrl: http.baseUrl,
		user: dd.getStorageSync({ key: 'JINGYI_DINGTALK_EAPP_USER' }).data || null,
	},
	setUser: user => {
		dd.setStorageSync({
			key: 'JINGYI_DINGTALK_EAPP_USER',
			data: user
		})
	}
});

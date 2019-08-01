import http from '../../utils/http'

const app = getApp();

Page({
	onLoad() {
		let user = app.globalData.user;
		if (user) {
			dd.redirectTo({
				url: '/pages/notice/index/index'
			});
		} else {
			dd.getAuthCode({
				success: res => {
					let code = res.authCode;
					http.get('account/login/dingtalk_corp', {
						data: {
							code: code
						}
					}).then(res => {
						app.setUser(res.data);
					}, err => {
						console.log('fail', err.errorMessage);
					})
				}
			})
		}
	},
});

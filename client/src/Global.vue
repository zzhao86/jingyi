<script>
  // 接口基础地址
  const baseUrl = 'http://123.56.179.69:5050/';
  // const baseUrl = 'http://192.168.0.8:5050/';
  // 钉钉CorpId
  const corpId = 'ding4be6acd37500e84335c2f4657eb6378f';//生产
  // const corpId = 'ding88b58049aa34141835c2f4657eb6378f'; //开发

  // 应用标题localStorage的key
  const titleSessionKey = 'JINGYI_CLIENT_TITLE';
  // 保存用户账号信息的localStorage的key
  const userSessionKey = 'JINGYI_CLIENT_USER';
  // 保存钉钉企业ID的localStorage的key
  const corpIdSessionKey = 'JINGYI_CLIENT_CROPID';
  // 保存钉钉是否已鉴权的localStorage的key
  const isPermissionSessionKey = 'JINGYI_CLIENT_IS_PERMISSION';

  Object.defineProperties(this, {
    baseUrl: {
      get: () => {
        return baseUrl;
      }
    },
    title: {
      get: () => {
        let sesstion = sessionStorage.getItem(titleSessionKey);
        if (session) {
          return JSON.parse(session);
        } else {
          return '物业管理';
        }
      },
      set: obj => {
        sessionStorage.setItem(titleSessionKey, JSON.stringify(obj));
      }
    },
    // 用户账号信息
    user: {
      get: () => {
        let session = sessionStorage.getItem(userSessionKey);
        if (session) {
          return JSON.parse(session);
        } else {
          return null;
        }
      },
      set: obj => {
        sessionStorage.setItem(userSessionKey, JSON.stringify(obj));
      }
    },
    // 钉钉企业ID
    corpId: {
      get: () => {
        return corpId;
      }
    },
    // 钉钉是否已鉴权
    isPermission: {
      get: () => {
        let session = sessionStorage.getItem(isPermissionSessionKey);
        if (session) {
          return session;
        } else {
          return null;
        }
      },
      set: val => {
        sessionStorage.setItem(isPermissionSessionKey, val);
      }
    }
  });
  export default this;
</script>

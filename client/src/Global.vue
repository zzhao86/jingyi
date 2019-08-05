<script>
  // 接口基础地址
  const baseUrl = 'http://192.168.0.8:5050/';
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
    // 用户账号信息
    user: {
      get: () => {
        let session = localStorage.getItem(userSessionKey);
        if (session) {
          return JSON.parse(session);
        } else {
          return null;
        }
      },
      set: obj => {
        localStorage.setItem(userSessionKey, JSON.stringify(obj));
      }
    },
    // 钉钉企业ID
    corpId: {
      get: () => {
        let session = localStorage.getItem(corpIdSessionKey);
        if (session) {
          return session;
        } else {
          return null;
        }
      },
      set: val => {
        localStorage.setItem(corpIdSessionKey, val);
      }
    },
    // 钉钉是否已鉴权
    isPermission: {
      get: () => {
        let session = localStorage.getItem(isPermissionSessionKey);
        if (session) {
          return session;
        } else {
          return null;
        }
      },
      set: val => {
        localStorage.setItem(isPermissionSessionKey, val);
      }
    }
  });
  export default this;
</script>

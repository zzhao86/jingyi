<template>
  <div>
    <div class="search-bar">
      <div class="search-wrapper">
        <i class="fa fa-search"></i>
        <input class="search-text" type="text" v-model="params.query.tenantCompany" placeholder="请输入承租单位名称" />
      </div>
      <a class="search-button" href="javascript:void(0);" @click="onKeywordsSearchClick">搜索</a>
    </div>
    <div class="condition-bar">
      <div class="condition-item" @click="onPositionConditionClick">
        <div class="item" v-if="!positionName">位置</div>
        <div class="item" v-else>位置：{{ positionName }}</div>
        <icon class="down fa fa-angle-down"></icon>
      </div>
      <div class="condition-item" @click="onFloorConditionClick">
        <div class="item" v-if="!floorName">楼号</div>
        <div class="item" v-else>楼号：{{ floorName }}</div>
        <icon class="down fa fa-angle-down"></icon>
      </div>
    </div>
    <div class="list-container" v-infinite-scroll="loadMoreData" infinite-scroll-distance="100" infinite-scroll-disabled="loading">
      <div class="no-data" v-if="listData.length == 0">
        <span>暂无资产</span>
      </div>
      <div class="list-item" v-for="(item, index) in listData" :key="index" @click="onViewClick(item.id)" v-else>
        <div class="assets-info">
          <div class="assets-name">{{ item.name }}</div>
          <div class="item-line">
            <div class="line-label">位置</div>
            <div class="line-value">{{ item.positionName }}</div>
          </div>
          <div class="item-line">
            <div class="line-label">楼号</div>
            <div class="line-value">{{ item.floorName }}</div>
          </div>
          <div class="item-line">
            <div class="line-label">房间号</div>
            <div class="line-value">{{ item.roomNo }}</div>
          </div>
          <div class="item-line">
            <div class="line-label">租赁面积</div>
            <div class="line-value">{{ item.leaseArea }}㎡</div>
          </div>
          <div class="item-line">
            <div class="line-label">承租单位</div>
            <div class="line-value">{{ item.tenantCompany }}</div>
          </div>
          <div class="item-line">
            <div class="line-label">租赁年限</div>
            <div class="line-value">{{ item.leaseTerm }}年</div>
          </div>
          <div class="item-line">
            <div class="line-label">合同起止时间</div>
            <div class="line-value">{{ `${item.startDate.substring(0, 10)} 至 ${item.endDate.substring(0, 10)}` }}</div>
          </div>
        </div>
      </div>
      <list-loading v-show="loading && !isLoadAll"></list-loading>
      <divider v-show="isLoadAll"></divider>
    </div>

    <!-- 位置条件 -->
    <mt-popup v-model="popupPositionVisible" class="popup-select" position="bottom">
      <mt-picker :slots="positionSlots" valueKey="name" :itemHeight="50" showToolbar @change="onPositionSelectChange">
        <div slot class="header">
          <mt-button size="small" type="default" @click="popupPositionVisible = false">取消</mt-button>
          <div class="title">请选择位置</div>
          <mt-button size="small" type="primary" @click="onPositionPopupOkClick">确定</mt-button>
        </div>
      </mt-picker>
    </mt-popup>

    <!-- 楼号条件 -->
    <mt-popup v-model="popupFloorVisible" class="popup-select" position="bottom">
      <mt-picker :slots="floorSlots" valueKey="name" :itemHeight="50" showToolbar @change="onFloorSelectChange">
        <div slot class="header">
          <mt-button size="small" type="default" @click="popupPositionVisible = false">取消</mt-button>
          <div class="title">请选择楼号</div>
          <mt-button size="small" type="primary" @click="onFloorPopupOkClick">确定</mt-button>
        </div>
      </mt-picker>
    </mt-popup>
  </div>
</template>
<script>
  import Divider from '../utils/components/Divider';
  import ListLoading from '../utils/components/ListLoading';
  export default {
    name: 'AssetsIndex',
    components: {
      Divider,
      ListLoading
    },
    created() {
      this.$global.title = '资产管理';
      this.loadListData();
      this.loadPositionSelectData();
      this.loadFloorSelectData();
    },
    data() {
      return {
        params: {
          index: 1,
          size: 10,
          query: {
            tenantCompany: '',
            position: '',
            floor: ''
          }
        },
        positionName: '',
        floorName: '',
        popupPositionVisible: false,
        popupFloorVisible: false,
        positionSlots: [
          {
            values: []
          }
        ],
        floorSlots: [
          {
            values: []
          }
        ],
        selectedPosition: {},
        selectedFloor: {},
        pageCount: 0,
        total: 0,
        loading: true,
        isLoadAll: false,
        listData: []
      };
    },
    methods: {
      loadListData() {
        this.$get('client/assets/list', {
          params: this.params
        }).then(res => {
          this.loading = false;
          if (res.isSuccess) {
            for (let i = 0; i < res.data.length; i++) {
              this.listData.push(res.data[i]);
            }
            this.pageCount = res.pageCount;
            this.total = res.total;
          }
        });
      },
      loadPositionSelectData() {
        this.$get('client/assets/select/position').then(res => {
          if (res.isSuccess) {
            res.data.unshift({
              id: '0',
              name: '全部位置'
            });
            this.positionSlots[0].values = res.data;
          }
        });
      },
      loadFloorSelectData() {
        this.$get('client/assets/select/floor').then(res => {
          if (res.isSuccess) {
            res.data.unshift({
              id: '0',
              name: '全部楼号'
            });
            this.floorSlots[0].values = res.data;
          }
        });
      },
      // 上滑加载更多
      loadMoreData() {
        if (this.params.index >= this.pageCount) {
          this.isLoadAll = true;
          return;
        }
        this.loading = true;
        this.params.index++;
        setTimeout(() => {
          this.loadListData();
        }, 1000);
      },
      onKeywordsSearchClick() {
        this.listData = new Array();
        this.params.index = 1;
        this.loadListData();
      },
      onPositionConditionClick() {
        this.popupPositionVisible = true;
      },
      onFloorConditionClick() {
        this.popupFloorVisible = true;
      },
      onPositionSelectChange(picker) {
        this.selectedPosition = picker.getSlotValue(0);
      },
      onFloorSelectChange(picker) {
        this.selectedFloor = picker.getSlotValue(0);
      },
      onPositionPopupOkClick() {
        this.popupPositionVisible = false;
        if (this.selectedPosition.id == '0') {
          this.params.query.position = '';
          this.positionName = '';
        } else {
          this.params.query.position = this.selectedPosition.id;
          this.positionName = this.selectedPosition.name;
        }
        this.params.index = 1;
        this.listData = new Array();
        this.loadListData();
      },
      onFloorPopupOkClick() {
        this.popupFloorVisible = false;
        if (this.selectedFloor.id == '0') {
          this.params.query.floor = '';
          this.floorName = '';
        } else {
          this.params.query.floor = this.selectedFloor.id;
          this.floorName = this.selectedFloor.name;
        }
        this.params.index = 1;
        this.listData = new Array();
        this.loadListData();
      }
    }
  };
</script>
<style scoped>
  .condition-bar {
    margin-top: 0;
    height: 40px;
    line-height: 40px;
    border-bottom: solid 1px #eee;
    display: flex;
    background-color: #fcfcfc;
  }
  .condition-item {
    flex: 1;
    text-align: center;
    position: relative;
  }
  .condition-item:nth-last-of-type(n + 2)::after {
    content: '';
    width: 1px;
    padding: 10px 0;
    position: absolute;
    right: 0;
    top: 50%;
    transform: translateY(-50%);
    background-color: #ddd;
  }
  .condition-item .item {
    color: #333;
    display: inline-block;
    max-width: calc((100vw / 2) - 50px);
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  .condition-item .down {
    font-size: 20px;
    color: #999;
  }

  .list-container {
    height: calc(100vh - 130px);
    padding-top: 0;
  }

  .assets-info {
    margin: 0 15px;
    height: 100%;
  }

  .list-item:first-of-type {
    margin-top: 0;
  }

  .assets-info .assets-name {
    color: #666;
    font-size: 18px;
    font-weight: bold;
    height: 40px;
    line-height: 40px;
    margin-bottom: 10px;
    border-bottom: solid 1px #efefef;
  }

  .assets-info .item-line {
    height: 40px;
    line-height: 40px;
    display: flex;
    font-size: 14px;
  }
  .assets-info .item-line .line-label {
    position: relative;
    color: #999;
    font-weight: bold;
    width: 120px;
  }
  .assets-info .item-line .line-label::after {
    content: '：';
  }
  .assets-info .item-line .line-value {
    color: #666;
    flex: 1;
    text-align: right;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .popup-select {
    width: 100vw;
  }
  .popup-select .header {
    padding: 10px;
    height: 40px;
    line-height: 40px;
    display: flex;
    background-color: #fafafa;
    position: relative;
    z-index: 1;
  }
  .popup-select .header .title {
    flex: 1;
    text-align: center;
    font-size: 18px;
    color: #666;
  }
  .search-bar {
    margin-top: 50px;
    padding: 0 5px;
    padding-bottom: 10px;
    display: flex;
  }
  .search-bar .search-wrapper {
    color: #999;
    height: 40px;
    line-height: 40px;
    border: solid 1px #ccc;
    border-radius: 5px;
    display: flex;
    flex: 1;
  }

  .search-bar .search-wrapper > .fa {
    line-height: 40px;
    width: 50px;
    text-align: center;
  }
  .search-bar .search-wrapper .search-text {
    outline: none;
    background: none;
    border: none;
    width: 100%;
    font-size: 14px;
    color: #333;
  }
  .search-bar .search-button {
    display: inline-block;
    text-align: center;
    width: 60px;
    height: 40px;
    line-height: 40px;
  }
  .search-bar .search-button:link,
  .search-bar .search-button:visited{
    color: #4b2937;
    text-decoration: none;
  }
</style>

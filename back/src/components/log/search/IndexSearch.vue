<template>
  <search-bar v-model="query" :tags="tags" show-advance placeholder="请输入资产名称或编号关键字" @search="onSearchClick" @advance-search="onAdvanceSearchClick" @reset="onResetClick">
    <template slot="content">
      <el-form :model="query" ref="advanceSearch" label-position="top" label-width="100px" size="mini">
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="日志类型">
              <el-select v-model="query.type" @change="onTypeTextChange">
                <el-option v-for="(item, index) in types" :key="index" :value="item.value" :label="item.label"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作模块">
              <el-select v-model="query.module" @change="onModuleTextChange">
                <el-option v-for="(item, index) in modules" :key="index" :value="item" :label="item"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- <el-row :gutter="10">
          <el-col :span="24">
            <el-form-item label="日期">
              <el-date-picker
                v-model="query.date"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                value-format="yyyy-MM-dd"
                :picker-options="pickerOptions"
                @change="onDateChange"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row> -->
      </el-form>
    </template>
  </search-bar>
</template>
<script>
  import SearchBar from '@/components/utils/components/SearchBar';

  export default {
    name: 'LogIndexSearch',
    components: {
      SearchBar
    },
    created() {
      this.loadModuleList();
      this.loadTypeList();
    },
    data() {
      return {
        query: {
          keywords: '',
          module: '',
          type: '',
          date: null
        },
        tagLabel: {
          keywords: '关键字',
          module: '操作模块',
          type: '日志类型',
          date: '日期'
        },
        tagText: {},
        tags: [],
        modules: [],
        types: [],
        pickerOptions: {
          disabledDate(date) {
            return date.getTime() > Date.now();
          },
          shortcuts: [
            {
              text: '最近一周',
              onClick(picker) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                picker.$emit('pick', [start, end]);
              }
            },
            {
              text: '最近一个月',
              onClick(picker) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                picker.$emit('pick', [start, end]);
              }
            },
            {
              text: '最近三个月',
              onClick(picker) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                picker.$emit('pick', [start, end]);
              }
            }
          ]
        }
      };
    },
    props: {},
    methods: {
      // 加载日志模块下拉列表数据
      loadModuleList() {
        this.$get('back/syslog/module/list').then(res => {
          if (res.isSuccess) {
            this.modules = res.data;
          }
        });
      },
      // 加载日志类型下拉列表数据
      loadTypeList() {
        this.$get('back/syslog/type/list').then(res => {
          if (res.isSuccess) {
            this.types = res.data;
          }
        });
      },
      // 重置
      onResetClick() {
        this.$refs['advanceSearch'].resetFields();
      },
      // 普通查询
      onSearchClick() {
        this.$emit('search', this.query);
      },
      // 高级查询
      onAdvanceSearchClick() {
        this.$children[0].composeSearchTags(this.tagLabel, this.tagText);
        this.$emit('search', this.query);
      },
      // 日志类型下拉框本文改变事件
      onTypeTextChange(text) {
        var temp = this.types.filter(o => {
          return o.value == text;
        });
        this.tagText['type'] = temp[0].label;
      },
      // 日志模块下拉框本文改变事件
      onModuleTextChange(text) {
        this.tagText['module'] = text;
      },
      // 日期选择框本文改变事件
      onDateChange(value) {
        if (value && value.length == 2) {
          this.tagText['date'] = `${value[0].substring(0, 10)}至${value[1].substring(0, 10)}`;
        } else {
          this.tagText['date'] = '';
        }
      }
    }
  };
</script>
<style></style>

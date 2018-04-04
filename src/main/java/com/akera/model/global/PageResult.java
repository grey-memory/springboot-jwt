package com.akera.model.global;

/**
 * Created by zwg.BlueOcean on 2018/1/18.
 */

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

/**
 * 分页对象
 */
public class PageResult {
    /**
     * 分页条数对象
      */
    public class PageSize {
        /**
         * 分页总条数
         */
        private Long total;
        public PageSize(){}
        public PageSize(Long total){
            this.total=total;
        }

        public Long getTotal() {
            return total;
        }

        public void setTotal(Long total) {
            this.total = total;
        }
    }

    /**
     * 分页数据
     */
    private Object list;
    private PageSize pageInfo;

    public PageResult(){}

    public PageResult(PageInfo<Object> data){
        this.list=data.getList();
        this.pageInfo=new PageSize(data.getTotal());
    }

    public Object getList() {
        return list;
    }

    public void setList(Object list) {
        this.list = list;
    }

    public PageSize getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageSize pageInfo) {
        this.pageInfo = pageInfo;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this, SerializerFeature.WRITE_MAP_NULL_FEATURES);
    }
}

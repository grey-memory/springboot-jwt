package com.akera.model.global;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by zwg.BlueOcean on 2018/1/18.
 */
public class SysResult{
    private static String SUCCESS="SUCCESS";
    private static String FAILED="FAILED";
    private Integer code;
    private String msg;
    private Object data;

    public SysResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static String getSUCCESS() {
		return SUCCESS;
	}

	public static void setSUCCESS(String sUCCESS) {
		SUCCESS = sUCCESS;
	}

	public static String getFAILED() {
		return FAILED;
	}

	public static void setFAILED(String fAILED) {
		FAILED = fAILED;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
     * 操作成功
     * 默认值，无需返回操作结果
     * @return
     */
    public static SysResult OK(){
        return new SysResult(0,SUCCESS,null);
    }


    /**
     * 操作成功
     * 返回操作结果
     * @param data
     * @return
     */
    public static SysResult OK(Object data){
        return new SysResult(0,SUCCESS,data);
    }

    /**
     * 操作成功
     * 自定义返回信息和返回数据，用于扩展
     * @param msg
     * @param data
     * @return
     */
    public static SysResult OK(String msg,Object data){
        return new SysResult(0,msg,data);
    }


    /**
     * 操作失败
     * 无提示,无返回数据
     * @return
     */
    public static SysResult ERROR(){
        return new SysResult(-1,FAILED,null);
    }

    /**
     *操作失败
     * 自定义失败提示信息，无返回数据
     * @param msg
     * @return
     */
    public static SysResult ERROR(String msg){
        return new SysResult(-1,msg,null);
    }

    /**
     * 操作失败
     * 自定义code，自定义msg，无返回数据
     * 用于扩展系统错误返回提示
     * @param code
     * @param msg
     * @return
     */
    public static SysResult ERROR(Integer code,String msg){
        return new SysResult(code,msg,null);
    }

    /**
     * 操作失败
     * 自定义msg，自定义data
     * 用于扩展系统错误返回提示
     * @param msg
     * @param data
     * @return
     */
    public static SysResult ERROR(String msg,Object data){
        return  new SysResult(-1,msg,data);
    }

    /**
     * 操作失败
     * 自定义code，msg，data
     * 用于扩展系统错误返回提示
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static SysResult ERROR(Integer code,String msg,Object data){
        return new SysResult(code,msg,data);
    }


    @Override
    public String toString() {
        return JSONObject.toJSONString(this, SerializerFeature.WRITE_MAP_NULL_FEATURES);
    }
}

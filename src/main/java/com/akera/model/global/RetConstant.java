package com.akera.model.global;

/**
 * 
 * @author 王欣宇
 * @time 2017年10月11日 下午3:12:05
 * @todo 响应参数1
 * @remark
 */
public class RetConstant {

	public interface CommonInfo {

		Integer RET_SUCCESS = 0;
		String RET_SUCCESS_MSG = "操作成功!";

		Integer RET_FAILED = -1;
		String RET_FAILED_MSG = "操作失败!";

		String RETCODE_UNKOWN_ERROR = "999";
		String RETMSG_UNKOWN_ERROR = "未知错误!";
		/*
		 * 用户自定义错误提示，返回并显示自定义信息
		 */
		Integer USER_DEFINED_CODE = 1009;
	}

	/**
	 * 
	 * @author 王欣宇
	 * @time 2017年10月11日 下午3:12:21
	 * @todo 用户权限
	 * @remark
	 */
	public interface UserInfo {

		String PERMISSION_DENIED_CODE = "1001";
		String PERMISSION_DENIED_MSG = "权限不足!";

		Integer INVALID_LOGIN_CODE = 999;
		String INVALID_LOGIN_MSG = "登录失效!";

		String USER_ALREADY_EXISTS_CODE = "1002";
		String USER_ALREADY_EXISTS_MSG = "用户名已存在!";

		String USER_NOT_EXISTS_CODE = "1003";
		String USER_NOT_EXISTS_MSG = "该用户不存在!";

		String RETMSG_PARAMS_NOVALID_CODE = "1004";
		String RETMSG_PARAMS_NOVALID_MSG = "入参错误!";

		String PASSWORD_NOVALID_CODE = "1005";
		String PASSWORD_NOVALID_MSG = "账号或密码错误!";

	}

	public static interface YesOrNo {
		public final static String YES = "1";
		public final static String NO = "2";

		public final static Integer YES_INT = 1;
		public final static Integer YES_NO = 2;
	}

	public static void main(String[] args) {
		System.out.println("User already exists".toUpperCase());
	}
}

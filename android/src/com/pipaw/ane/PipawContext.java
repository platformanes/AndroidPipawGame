package com.pipaw.ane;

import java.util.HashMap;
import java.util.Map;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.pipaw.func.PipawExit;
import com.pipaw.func.PipawInit;
import com.pipaw.func.PipawLogin;
import com.pipaw.func.PipawPay;

/**
 * @author Rect
 * @version  Time：2013-5-8 
 */
public class PipawContext extends FREContext {
	/**
	 * INIT sdk
	 */
	public static final String PIPAW_FUNCTION_INIT = "pipaw_function_init";
	/**
	 * 登录Key
	 */
	public static final String PIPAW_FUNCTION_LOGIN = "pipaw_function_login";
	/**
	 * 付费Key
	 */
	public static final String PIPAW_FUNCTION_PAY = "pipaw_function_pay";
	/**
	 * 退出Key
	 */
	public static final String PIPAW_FUNCTION_EXIT = "pipaw_function_exit";
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, FREFunction> getFunctions() {
		// TODO Auto-generated method stub
		Map<String, FREFunction> map = new HashMap<String, FREFunction>();
//	       //映射
		   map.put(PIPAW_FUNCTION_INIT, new PipawInit());
	       map.put(PIPAW_FUNCTION_LOGIN, new PipawLogin());
	       map.put(PIPAW_FUNCTION_PAY, new PipawPay());
	       map.put(PIPAW_FUNCTION_EXIT, new PipawExit());
	       return map;
	}

}

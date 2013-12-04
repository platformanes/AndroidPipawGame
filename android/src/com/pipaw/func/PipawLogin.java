package com.pipaw.func;

import android.content.Intent;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

/**
 * 执行登录
 * @author Rect
 * @version  Time：2013-5-8 
 */
public class PipawLogin implements FREFunction {

	private String TAG = "PipawLogin";
	private FREContext _context;
	@Override
	public FREObject call(final FREContext context, FREObject[] arg1) {
		// TODO Auto-generated method stub
		_context = context;
		FREObject result = null;  
		// TODO Auto-generated method stub
		//--------------------------------
		String merchantId = PipawConfig.merchantId;
		String merchantAppId = PipawConfig.merchantAppId;
		String appId = PipawConfig.appId;

		if(null == merchantId)
		{
			callBack("请先执行PipawInit函数初始化参数*2*error");
			return null;
		}
		BridgeActivity.isLogin = true;
		Intent intent = new Intent(BridgeActivity.MYACTIVITY_ACTION);
		intent.putExtra("merchantId", merchantId);
		intent.putExtra("merchantAppId", merchantAppId);
		intent.putExtra("appId", appId);
		BridgeActivity._context = _context;
		Log.d(TAG, "---------startActivityForResult处理-------");
		_context.getActivity().startActivityForResult(intent, 0); 
		
		
		//--------------------------------
		
		return result;
	}

	/**
	 * 结果传给AS端
	 */
	public void callBack(String status){
		Log.d(TAG, "-----status----"+status);
		_context.dispatchStatusEventAsync(TAG,status);
	}
}

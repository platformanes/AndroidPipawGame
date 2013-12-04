package com.pipaw.func;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

/**
 * 初始化SDK
 * @author Rect
 * @version  Time：2013-5-8 
 */
public class PipawInit implements FREFunction {

	private String TAG = "PipawInit";
	private FREContext _context;
	@Override
	public FREObject call(final FREContext context, FREObject[] arg1) {
		// TODO Auto-generated method stub
		_context = context;
		FREObject result = null; 
		// TODO Auto-generated method stub
		//--------------------------------
		String merchantId = null;
		String merchantAppId = null;
		String appId = null;
		String privateKey = null;
		try
		{
			merchantId = arg1[0].getAsString();
			merchantAppId = arg1[1].getAsString();
			appId = arg1[2].getAsString();
			privateKey = arg1[3].getAsString();
			
			PipawConfig.merchantId = merchantId;
			PipawConfig.merchantAppId = merchantAppId;
			PipawConfig.appId = appId;
			PipawConfig.privateKey = privateKey;
			
			BridgeActivity._context = _context;
			
			callBack("merchantId:"+merchantId);
			callBack("merchantAppId:"+merchantAppId);
			callBack("appId:"+appId);
			callBack("privateKey:"+privateKey);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		callBack("success");
		//--------------------------------
		
		return result;
	}

	/**
	 * 结果传给AS端
	 */
	public void callBack(String status){
		Log.d(TAG, "-----status----"+status);
		_context.dispatchStatusEventAsync(TAG, "status:"+status);
	}

}

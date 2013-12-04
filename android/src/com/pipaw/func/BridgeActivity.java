package com.pipaw.func;


import org.json.JSONObject;

import com.adobe.fre.FREContext;
import com.pipaw.pipawpay.PayRequest;
import com.pipaw.pipawpay.PipawSDK;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BridgeActivity extends Activity implements
OnClickListener {
	//声明开启Activity的Action
	public static final String MYACTIVITY_ACTION = "com.pipaw.func.BridgeActivity";
	private String TAG = "BridgeActivity";
	public static FREContext _context;
	public static PayRequest payRequest;
	private LinearLayout layout;
	public static Boolean isLogin = false;
	protected static final int UPDATE_TEXT = 0;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case 1:
			finish();
			break;
		case 3:
			
			break;
		}
	}
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//构建界面

		super.onCreate(savedInstanceState);
		Log.d(TAG, "---------onCreate-------");
		
		// 隐藏标题栏  
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
		// 隐藏状态栏  
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundResource(_context.getResourceId("drawable.bg"));
		this.setContentView(layout);
		

		TextView textv = new TextView(this);
		String str_2 = "点击任意返回游戏....";
		textv.setText(str_2);
		layout.addView(textv);
		
		layout.setId(1);
		layout.setOnClickListener(this);

		Intent localIntent = getIntent();
		if(isLogin)//登录
		{
			String merchantId = localIntent.getStringExtra("merchantId");
			String merchantAppId = localIntent.getStringExtra("merchantAppId");
			String appId = localIntent.getStringExtra("appId");
			PipawSDK.getInstance().login(this, merchantId, merchantAppId, appId);
		}
		else//付费
		{
			if(null != payRequest)
				PipawSDK.getInstance().pay(this, payRequest);
			else
				callBack("PipawPay","pay Error");
		}
		
	}

	public void callBack(String _TAG,String status){
		Log.d(_TAG, "-----status----"+status);
		_context.dispatchStatusEventAsync(_TAG,status);
	}
	
	/**
	 * 客户端同步通知 可选操作，请以服务端异步通知为准。
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		Boolean isSDKBack = false;
		if (requestCode == PipawSDK.REQUEST_PAY) {
			isSDKBack = true;
			if (resultCode == PipawSDK.PAY_CANCEL) {
				/**
				 * 用户取消支付
				 */
				callBack("PipawPay","取消支付");
			} else if (resultCode == PipawSDK.PAY_SUCCESS) {
				/**
				 * 支付成功
				 */
				callBack("PipawPay","支付成功");
			} else if (resultCode == PipawSDK.PAY_FAIL) {
				/**
				 * 支付失败
				 */
				callBack("PipawPay","支付失败");
				if (data != null) {
					Toast.makeText(this, data.getAction(), Toast.LENGTH_SHORT)
							.show();
				}
			} else if (resultCode == PipawSDK.PAY_CHECK_SIGN_FAIL) {
				/**
				 * 验签失败，可能支付成功，请以服务端异步通知为准。
				 */
				Toast.makeText(this, "验签失败，可能支付成功", Toast.LENGTH_SHORT).show();
				callBack("PipawPay","验签失败，可能支付成功");
				if (data != null) {
					Toast.makeText(this, data.getAction(), Toast.LENGTH_SHORT)
							.show();
				}
			}
		}

		if (requestCode == PipawSDK.REQUEST_LOGIN) {
			String TAG = "PipawLogin";
			isSDKBack = true;
			if (resultCode == PipawSDK.LOGIN_EXIT) {
				/**
				 * 退出登录
				 */
				callBack(TAG,"退出登录*2*exit");
			} else if (resultCode == PipawSDK.LOGIN_SUCCESS) {
				/**
				 * 登录成功
				 */
				/**
				 * 返回包含username，sid，time的json对象。
				 * 游戏服务端可通过merchantId，merchantAppId，appId，username，sid，time向
				 * 支付SDK服务端请求验证sid。 注：sid的有效时间为1小时，游戏服务端须在1小时内完成sid验证。
				 */
				Log.d("main", data.getAction());
				String jsonstring = data.getAction();
				try { 
					//{"sid":"74e0be4177f0da988cd4f50113d2dfc3","time":"1386059098","username":"qwertyuit"}
					JSONObject resultJson = new JSONObject(jsonstring);//转换为JSONObject  
					String sid = resultJson.getString("sid");
					String time = resultJson.getString("time");
					String username = resultJson.getString("username");
					//sid*time*username
					String str = sid;
					str += "*"+time;
					str += "*"+username;
					callBack(TAG,str);
				}
				catch (Exception e) {
					// TODO: handle exception
					callBack(TAG,"解析数据失败*2*error");
				}
				
			} else if (resultCode == PipawSDK.LOGIN_FAIL) {
				/**
				 * 登录失败
				 */
				callBack(TAG,"登录失败*2*exit");
				if (data != null) {
					Toast.makeText(this, data.getAction(), Toast.LENGTH_SHORT)
							.show();
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
		
		if (isSDKBack) {
			finish();
		}
		
	}
	@Override  
	public boolean onKeyDown(int keyCode, KeyEvent event)  
	{  
		if (keyCode == KeyEvent.KEYCODE_BACK )  
		{  
			
		}  
		return super.onKeyDown(keyCode, event);

	}  

}

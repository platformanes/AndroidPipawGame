package com.pipaw.func;

import android.content.Intent;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.pipaw.pipawpay.PayRequest;

/**
 * 执行付费
 * @author Rect
 * @version  Time：2013-5-8 
 */
public class PipawPay implements FREFunction {

	private String TAG = "PipawPay";
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
		String privateKey = PipawConfig.privateKey;
		String payerId = PipawConfig.uid;
		String subject = null;
		String price = null;
		String exOrderNo = null;
		String extraParam = null;
		
		try
		{
			//用户UID 商品名称 价格 订单号 附加参数
			payerId = arg1[0].getAsString();
			subject = arg1[1].getAsString();
			price = arg1[2].getAsString();
			exOrderNo = arg1[3].getAsString();
			extraParam = arg1[4].getAsString();
			
			callBack("uid-payerId:"+payerId);
			callBack("subject:"+subject);
			callBack("price:"+price);
			callBack("exOrderNo:"+exOrderNo);
			callBack("extraParam:"+extraParam);
			
			/**
			 * 生成支付请求
			 */
			PayRequest payRequest = new PayRequest();
			/**
			 * merchantId 商户系统Id
			 */
			payRequest.setMerchantId(merchantId);
			/**
			 * merchantAppId 商户应用Id
			 */
			payRequest.setMerchantAppId(merchantAppId);
			/**
			 * appId 应用Id
			 */
			payRequest.setAppId(appId);
			/**
			 * payerId 唯一的支付用户Id
			 */
			payRequest.setPayerId(payerId);
			/**
			 * isPipawId payerId是否是琵琶网用户 是琵琶网用户为true 反之为false
			 */
			payRequest.setIsPipawId("true");
			/**
			 * exOrderNo 交易的订单号
			 */
			payRequest.setExOrderNo(exOrderNo);
			/**
			 * subject 交易的商品名称
			 */
			payRequest.setSubject(subject);
			/**
			 * price 交易的金额
			 */
			payRequest.setPrice(price);
			/**
			 * extraParam 商户私有信息
			 */
			payRequest.setExtraParam(extraParam);
			/**
			 * 将merchantId，merchantAppId，appId，payerId，exOrderNo，subject，price，
			 * extraParam，privateKey直接连接起来获取md5签名值。
			 * 建议:签名在商户服务器端进行，同时商户私钥也应存储在服务器端，避免可能的安全隐患。
			 */
			StringBuilder content = new StringBuilder();
			content.append(merchantId).append(merchantAppId).append(appId)
					.append(payerId).append(exOrderNo).append(subject)
					.append(price).append(extraParam).append(privateKey);
			Log.d(TAG, "content " + content);
			String merchantSign = Md5Util.getMd5(content.toString());
			Log.d(TAG, "merchantSign " + merchantSign);
			/**
			 * merchantSign 交易签名
			 */
			payRequest.setMerchantSign(merchantSign);
			
			BridgeActivity.payRequest = payRequest;
			BridgeActivity.isLogin = false;
			Intent intent = new Intent(BridgeActivity.MYACTIVITY_ACTION);
			BridgeActivity._context = _context;
			Log.d(TAG, "---------startActivityForResult处理-------");
			_context.getActivity().startActivityForResult(intent, 0);
			
		}
		catch (Exception e) {
			// TODO: handle exception
			callBack("argv is error");
			return null;
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

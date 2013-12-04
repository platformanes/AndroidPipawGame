package com.pipaw.ane 
{ 
	/**
	 * 
	 * @author Rect  2013-5-6 
	 * 
	 */
	public class PipawEvents 
	{ 
		public function PipawEvents()
		{
		} 
		/**************************平台通知************************************/
		/**
		 *init 
		 */		
		public static const PIPAW_SDK_STATUS:String = "PipawInit";
		/**
		 * 用户登录
		 */
		public static const PIPAW_LOGIN_STATUS : String = "PipawLogin";
		
		/**
		 * 用户注销
		 */
		public static const PIPAW_LOGOUT_STATUS : String = "PipawExit";
		
		/**
		 * 充值
		 */
		public static const PIPAW_PAY_STATUS : String = "PipawPay";
	} 
}
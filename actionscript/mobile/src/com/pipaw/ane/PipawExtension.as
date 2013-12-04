package com.pipaw.ane 
{ 
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;
	import flash.events.StatusEvent;
	import flash.external.ExtensionContext;
	
	/**
	 * 
	 * @author Rect  2013-5-6 
	 * 
	 */
	public class PipawExtension extends EventDispatcher 
	{ 
		
		
		private static const PIPAW_FUNCTION_INIT:String = "pipaw_function_init";//与java端中Map里的key一致
		private static const PIPAW_FUNCTION_LOGIN:String = "pipaw_function_login";//与java端中Map里的key一致
		private static const PIPAW_FUNCTION_PAY:String = "pipaw_function_pay";//与java端中Map里的key一致
		private static const PIPAW_FUNCTION_EXIT:String = "pipaw_function_exit";//与java端中Map里的key一致
		
		private static const EXTENSION_ID:String = "com.pipaw.ane";//与extension.xml中的id标签一致
		private var extContext:ExtensionContext;
		
		/**单例的实例*/
		private static var _instance:PipawExtension; 
		public function PipawExtension(target:IEventDispatcher=null)
		{
			super(target);
			if(extContext == null) {
				extContext = ExtensionContext.createExtensionContext(EXTENSION_ID, "");
				extContext.addEventListener(StatusEvent.STATUS, statusHandler);
			}
			
		} 
		
		//第二个为参数，会传入java代码中的FREExtension的createContext方法
		/**
		 * 获取实例
		 * @return DLExtension 单例
		 */
		public static function getInstance():PipawExtension
		{
			if(_instance == null) 
				_instance = new PipawExtension();
			return _instance;
		}
		
		/**
		 * 转抛事件
		 * @param event 事件
		 */
		private function statusHandler(event:StatusEvent):void
		{
			dispatchEvent(event);
		}
		
		/**
		 * 
		 * @param merchantId
		 * @param merchantAppId
		 * @param appId
		 * @param privateKey
		 * @return 
		 * 
		 */			
		public function PipawInit(merchantId:String,merchantAppId:String,appId:String,privateKey:String = "privateKey"):String{
			if(extContext ){
				return extContext.call(PIPAW_FUNCTION_INIT,merchantId,merchantAppId,appId,privateKey) as String;
			}
			return "call login failed";
		} 
		/**
		 * 
		 * @param id
		 * @return 
		 * 
		 */			 
		public function PipawLogIn(id:String):String{
			if(extContext ){ 
				return extContext.call(PIPAW_FUNCTION_LOGIN,id)as String;
			}
			return "call PipawLogIn failed";
		}
		
		/**
		 * 
		 * @param payerId
		 * @param subject
		 * @param price
		 * @param exOrderNo
		 * @param extraParam
		 * @return 
		 * 
		 */			
		public function PipawPay(payerId:String,subject:String,price:String,exOrderNo:String,extraParam:String):String{
			if(extContext ){
				return extContext.call(PIPAW_FUNCTION_PAY,payerId,subject,price,exOrderNo,extraParam) as String;
			}
			return "call PipawPay failed";
		} 
		
		/**
		 *退出SDK时候调用   这个函数只在退出游戏的时候调用  
		 * @param key
		 * @return 
		 * 
		 */		
		public function PipawExit(key:int):String{
			if(extContext){ 
				return extContext.call(PIPAW_FUNCTION_EXIT,key) as String;
			}
			return "call exit failed";
		}
	} 
}
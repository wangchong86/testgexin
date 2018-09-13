package TestDemo.test;

import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.APNTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;

public class GetuiPushUtil {
	//三医院工单
	private static String appId = "8Egx0o9jb76cERD61gPvi3";
	private static String appKey = "92xjoulkjx7d13PDh8Vi26";
	private static String masterSecret = "J62sOMBxziAs6oxNRI0k03";
//	private static String appSecret="oh4fUF1h8e6jUZd7yZX2T";
	
	public static void main(String[] args) throws Exception{
		String clientId="";
		pushAndroidMessage(clientId,"测试","抢单运送药品，1楼103到2楼201","{\"orderNo\":1,\"orderType\":6}");
	}
	
	//push android，并返回任务id列表
	public static String pushAndroidMessage(String clientId,String title,String tipText,String content) throws Exception{
		List<String> clientIdList=new ArrayList<String>();
		clientIdList.add(clientId);
		return pushAndroidMessage(clientIdList, title, tipText, content);
	}
	
	//push ios，并返回任务id列表
	public static String pushIosMessage(String clientToken,String title,String tipText,String content) throws Exception{
		List<String> clientTokenList=new ArrayList<String>();
		clientTokenList.add(clientToken);
		return pushIosMessage(clientTokenList, title, tipText, content);
	}
	
	//push android
	public static String pushAndroidMessage(List<String> clientIdList,String title,String tipText,String content) throws Exception{
		// 配置返回每个用户返回用户状态，可选
		System.setProperty("gexin_pushList_needDetails", "false");
		// 配置返回每个别名及其对应cid的用户状态，可选
		System.setProperty("gexin_pushList_needAliasDetails", "false");
		IGtPush push = new IGtPush(appKey, masterSecret);
		// 通知透传模板
		NotificationTemplate template = createNotificationTemplate(title,tipText,content);
		ListMessage message = new ListMessage();
		message.setData(template);
		// 设置消息离线，并设置离线时间
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(48 * 1000 * 3600);
		// 配置推送目标
		List<Target> targets = new ArrayList<Target>();
		for(String clientId:clientIdList){
			Target target = new Target();
			target.setAppId(appId);
			target.setClientId(clientId);
			targets.add(target);
		}
		String taskId = push.getContentId(message);
		push.pushMessageToList(taskId, targets);
		return taskId;
	}
	
	//创建Android通知模板
	public static NotificationTemplate createNotificationTemplate(String title,String tipText,String content) {
		NotificationTemplate template = new NotificationTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appKey);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(1);
		template.setTransmissionContent(content);
		// 设置定时展示时间
		Style0 style = new Style0();
		// 设置通知栏标题与内容
		style.setTitle(title);
		style.setText(tipText);
		// 配置通知栏图标
		//style.setLogo("icon.png");
		// 配置通知栏网络图标
		//style.setLogoUrl("");
		style.setRing(true);
		style.setVibrate(true);
		style.setClearable(true);
		template.setStyle(style);
		return template;
	}
	
	//push IOS
	protected static String pushIosMessage(List<String> clientTokenList,String title,String tipText,String content) throws Exception{
		IGtPush push = new IGtPush(appKey, masterSecret);
		APNTemplate t = new APNTemplate();
		APNPayload apnpayload = new APNPayload();
		//apnpayload.setSound("");
		APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
		//通知文本消息标题
		alertMsg.setTitle(title);
		//通知文本消息字符串
		alertMsg.setBody(tipText);
		//对于标题指定执行按钮所使用的Localizable.strings,仅支持IOS8.2以上版本
		//alertMsg.setTitleLocKey("ccccc");
		//指定执行按钮所使用的Localizable.strings
		//alertMsg.setActionLocKey("ddddd");
		apnpayload.setAlertMsg(alertMsg);
		t.setAPNInfo(apnpayload);
		ListMessage message = new ListMessage();
		message.setData(t);
		String contentId = push.getAPNContentId(appId, message);
		//System.out.println(contentId);
		System.setProperty("gexin.rp.sdk.pushlist.needDetails", "false");
		List<String> dtl = new ArrayList<String>();
		for(String token:clientTokenList){
			dtl.add(token);
		}
		push.pushAPNMessageToList(appId, contentId, dtl);
		return contentId;
	}
}

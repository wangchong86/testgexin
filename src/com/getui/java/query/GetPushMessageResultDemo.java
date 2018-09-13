package com.getui.java.query;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.http.IGtPush;

public class GetPushMessageResultDemo {
	//您应用的appkey
	private static String appId = "FS6YO5T0nI6oSs5MAuo2E6";
	private static String appKey = "x7BoiAh4ZQ7IG1Lwr9TPv7";
	private static String masterSecret = "CPrWhEmSpp78u3hEt1V281";
    //您要查询的taskid
    private static final String TASKID = "OSL-0324_UEcfOhSSYl9sa25OKwQYK7";

    static String host ="http://sdk.open.api.igexin.com/apiex.htm";
    public static void main(String[] args) {

        IGtPush push = new IGtPush(host,appKey, masterSecret);
        System.out.println(push.getPushResult(TASKID).getResponse());
        IPushResult result=push.getPushResult(TASKID);
        String msgTotal =result.getResponse().get("msgTotal").toString();
        String clickNum=result.getResponse().get("clickNum").toString();
        String msgProcess =result.getResponse().get("msgProcess").toString();
        System.out.println("总下发数:"+msgTotal+"|点击数:"+clickNum+"|下发的消息总数:"+msgProcess);

    }

}
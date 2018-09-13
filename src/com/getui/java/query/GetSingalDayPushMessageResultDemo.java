package com.getui.java.query;
import java.util.Map;

import com.gexin.rp.sdk.base.IQueryResult;
import com.gexin.rp.sdk.http.IGtPush;

public class GetSingalDayPushMessageResultDemo {
	private static String APPID = "FS6YO5T0nI6oSs5MAuo2E6";
	private static String APPKEY = "x7BoiAh4ZQ7IG1Lwr9TPv7";
	private static String MASTERSECRET = "CPrWhEmSpp78u3hEt1V281";

    static String host ="http://sdk.open.api.igexin.com/apiex.htm";

    public static void main(String[] args) {

        IGtPush push = new IGtPush(host,APPKEY, MASTERSECRET);

        IQueryResult result = push.queryAppPushDataByDate(APPID, "20170323");
        Map<String, Object> data = (Map<String, Object>)result.getResponse().get("data");
        System.out.println(data);
        System.out.println("发送总数:"+data.get("sendCount"));
        System.out.println("在线下发数:"+data.get("sendOnlineCount"));
        System.out.println("接收总数:"+data.get("receiveCount"));
        System.out.println("展示总数:"+data.get("showCount"));
        System.out.println("点击总数:"+data.get("clickCount"));     
    }

}
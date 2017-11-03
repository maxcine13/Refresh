package com.jierong.share.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.jierong.share.BaseApp;
import com.jierong.share.Constants;
import com.jierong.share.R;
import com.jierong.share.mvp.model.info.PushInfo;
import com.jierong.share.mvp.view.act.WelcomeAct;
import com.jierong.share.util.AppPreferences;
import com.jierong.share.util.LogUtil;

public class MgtIntentService extends GTIntentService {

    public MgtIntentService() { }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
        LogUtil.d("GT-onReceiveServicePid-> " + pid);
    }

    /**
     * cid 离线上线通知
     * @param context
     * @param online
     */
    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
        LogUtil.d("GT-onReceiveOnlineState-> " + (online ? "online" : "offline"));
    }

    /**
     * 接收 cid
     * @param context
     * @param clientid
     */
    @Override
    public void onReceiveClientId(Context context, final String clientid) {
        LogUtil.d("GT-clientId-> " + clientid);
        Intent intent = new Intent();
        intent.putExtra("Pid", clientid);
        intent.setAction(Constants.Push_Action_GetId);
        sendBroadcast(intent);
    }

    /**
     * 处理个推透传消息
     * @param context
     * @param msg
     */
    @Override
    public void onReceiveMessageData(final Context context, GTTransmitMessage msg) {
        LogUtil.d("GT-onReceiveMessageData->");
        byte[] payload = msg.getPayload();
        if (payload == null) {
            LogUtil.d("GT-透传数据为空");
        } else {
            String data = new String(payload);
            PushInfo info = PushInfo.fromJSON(data);
            LogUtil.d("透传数据code: " + info.getCode());
            switch (info.getCode()) {
                case PushInfo.Push_Logout:
                    // 处理强退的逻辑
                    Intent logout = new Intent();
                    logout.setAction(Constants.Push_Action_Logout);
                    //logout.putExtra("Data", info.getData());
                    sendBroadcast(logout);
                    break;
                case PushInfo.Push_Gg_Me: {
                    // 处理发布公告
                    Intent ggMe = new Intent();
                    ggMe.setAction(Constants.Push_Gg_Me);
                    ggMe.putExtra("Data", info.getData());
                    sendBroadcast(ggMe);
                    break;
                }
                case PushInfo.Push_Gg_Home: {
                    // 处理发布公告
                    Intent ggHome = new Intent();
                    ggHome.setAction(Constants.Push_Gg_Home);
                    ggHome.putExtra("Data", info.getData());
                    sendBroadcast(ggHome);
                    break;
                }
                case PushInfo.Push_Red_Packet: {
                    // 处理发送红包的逻辑
                    AppPreferences.putBoolean(context, "isShowRedPacket", true);
                    AppPreferences.putBoolean(context, "needUpdateRead", true);

                    if(BaseApp.getInstance().isBackground()) {
                        // 如果app不在前台，则发送重启的通知
                        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        Notification notice;
                        Notification.Builder builder = new Notification.Builder(context)
                                .setTicker("您收到一条新的通知，请注意查收！")
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setWhen(System.currentTimeMillis())
                                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
                        Intent appIntent = new Intent(context, WelcomeAct.class);
                        appIntent.setAction(Intent.ACTION_MAIN);
                        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                        appIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
                                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                        );  // 关键的一步，设置启动模式
                        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            notice = builder.setContentIntent(contentIntent)
                                    .setContentTitle("红包通知")
                                    .setContentText("恭喜您获得" + info.getData() + "元红包，" +
                                            "赶紧去个人中心的余额里边查看吧！").build();
                            notice.flags = Notification.FLAG_AUTO_CANCEL;
                            nm.notify(10, notice);
                        }
                        LogUtil.d("app is in background");
                    } else {
                        LogUtil.d("app is in front");
                        Intent redPacket = new Intent();
                        redPacket.setAction(Constants.Push_Red_Packet);
                        redPacket.putExtra("Data", info.getData());
                        sendBroadcast(redPacket);
                    }
                    break;
                }
                default:break;
            }
        }
    }

    /**
     * 各种事件处理回执
     * @param context
     * @param gtCmdMessage
     */
    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {
    }
}

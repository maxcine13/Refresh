package com.jierong.share;

/**
 * 常量类
 *
 * @author qingf
 */
public class Constants {
    public static final String Share_Sdk_Key = "1bf01d9eab8cf";
    public static final String Share_Sdk_Secret = "7f8bf2d647d30c87909e6ee1393e7804";

    public static final String APP_ID_WX = "wxf683ffdd2e2d206c";
    public static final String APP_ID_QQ = "1106010770";
    public static final String APP_Key_WB = "3778070942";
    public static final String APP_Url_WB = "https://api.weibo.com/oauth2/default.html";
    public static final String APP_Scope_WB = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";

    /* APP在手机SD卡根目录下的路径名称 */
    public static final String App_File_Path = "share";
    public static final String New_Down_Path = App_File_Path + "/down";
    public static final String New_User_Ic = App_File_Path + "/uic/";
    public static final String New_Apk_Name = "fxzk.apk";

    /* 通用页面跳转标示*/
    public static final String WhileLineDetaile = "Detaile";
    public static final String Activation = "ActivationAct";
    public static final String RegisterActivity = "Register";
    public static final String IncomeRule = "Rule";
    public static final String GetMoney = "Money";
    public static final String Customer = "Customer";
    public static final String Income = "Income";
    public static final String HelperCenter = "Helper";
    public static final String Authen = "Authen";


    /* APP轻存储文件名 */
    public static final String Preference_Name = "shareConfig";
    public static final String PNK_UId = "uId";
    public static final String PNK_UIc = "uIc";
    public static final String PNK_NEW = "new_state";
    public static final String PNK_UName = "uName";
    public static final String PNK_UTel = "uTel";
    public static final String PNK_UToken = "uToken";
    public static final String PNK_UWay = "uWay";
    public static final String PNK_Umoney = "money";
    //public static final String PNK_UType = "uType";
    public static final String PNK_UThree = "uThree";
    public static String PNK_Location = "Locaton";//

    /* 内部推送广播的动作标识 */
    public static final String Push_Action_GetId = "push.action.getId";
    public static final String Push_Action_Logout = "push.action.logout";
    public static final String Push_Gg_Me = "push.action.gg.me";
    public static final String Push_Gg_Home = "push.action.gg.home";
    public static final String Push_Red_Packet = "push.action.red.packet";
    public static final String Refresh_Red_Packet_Unread = "refresh.red.packet.unread";
    public static final String Refresh_User_Center = "refresh.user.center";
    public static final String Refresh_User_Money = "refresh.user.money";
    public static final String Refresh_Phb_time = "refresh.phb.time";

    /* 权限审核问题 */
    public static final int Permission_Camera = 310;   // 获取照相机的权限
    public static final int Permission_Phone = 311;   // 获取手机状态的权限
    public static final int Permission_Storage = 312;   // 手机存储读写的权限
    public static final int Permission_Location = 313;   // 获取定位位置的权限
    public static final int Permission_Accounts = 314;   // 读取联系人的权限

    /* http基本访问域名 */
    public static final String Http_Base = "http://toshare.91jierong.com/index.php/API/";
    /* 获取欢迎界面广告的接口 */
    public static final String Http_Api_Wel_Adv = "http://skb.91jierong.com/index.php/Ports/Data/getWelcomeImg";
    /* 获取验证码以注册的接口 */
    public static final String Http_Api_GetKey = Http_Base + "Regist/registerCode";
    /* 注册的接口 */
    public static final String Http_Api_Register = Http_Base + "Regist/register";
    /* 注册时获取识别码的接口 */
    public static final String Http_Api_Register_Verify = Http_Base + "regist/verify";
    /* 同步用户数据的接口(自动登录) */
    public static final String Http_Api_InitUser = Http_Base + "Login/Free_login";
    /* 用户名密码登录的接口 */
    public static final String Http_Api_UserLogin = Http_Base + "Login/phoneLogin";
    /* 使用QQ登录的接口 */
    public static final String Http_Api_QQLogin = Http_Base + "Login/qqLogin";
    /* 使用新浪微博登录的接口 */
    public static final String Http_Api_WBLogin = Http_Base + "Login/weiboLogin";
    /* 使用微信登录的接口 */
    public static final String Http_Api_WXLogin = Http_Base + "Login/weixinLogin";
    /* 绑定时获取的验证码接口 */
    public static final String Http_Api_BindKey = Http_Base + "Regist/bindCode";
    /* 绑定手机号码的接口 */
    public static final String Http_Api_BindTel = Http_Base + "Regist/bindPhoneNum";
    /* 修改手机登录密码的接口 */
    public static final String Http_Api_ChangePw = Http_Base + "user/changpwd";
    /* 告知服务端当天状态的接口 */
    public static final String Http_Api_TelServer = Http_Base + "Index/login_log";
    /* 上传定位城市信息的接口 */
    public static final String Http_Api_UploadCity = Http_Base + "Index/addLoginLog";
    /* 获取首页数据的接口 */
    public static final String Http_Api_GetHomeData = Http_Base + "Index/index";
    /* 获取首页爆款返利数据的接口 */
    public static final String Http_Api_GetBkData = Http_Base + "Index/index_new";
    /* 获取分享赚钱 广告分类列表数据 */
    public static final String Http_Api_GetAdvData = Http_Base + "Index/cate_query";
    /* 获取用户中心数据的接口 */
    public static final String Http_Api_GetMe = Http_Base + "User/personal";
    /* 获取用户余额 */
    public static final String Http_Api_GetUMoney = Http_Base + "User/userover";
    /* 绑定支付宝 */
    public static final String Http_Api_BindCard = Http_Base + "user/zfb_bang";
    /* 向支付宝提钱 */
    public static final String Http_Api_MoneyOut = Http_Base + "user/zfb_tx";
    /* 检查用户绑定支付宝状态 */
    public static final String Http_Api_CheckBindCard = Http_Base + "user/is_zfb";
    /* 获取“我的”公告数据的接口 */
    public static final String Http_Api_GetGg_Me = Http_Base + "new/notice";
    /* 获取“首页”公告数据的接口 */
    public static final String Http_Api_GetGg_Home = Http_Base + "new/index_notice";
    /* 上传邀请码的接口 */
    public static final String Http_Api_UpYqm = Http_Base + "user/uploadyqm";
    /* 验证是否达人认证 */
    public static final String Http_Api_Master_Check = Http_Base + "user/is_proof";
    /* 达人认证接口 */
    public static final String Http_Api_Master = Http_Base + "User/proof";
    /* 完善用户头像数据的接口 */
    public static final String Http_Api_UpdateUic = Http_Base + "User/uploadAvatar";
    /* 完善用户标签列表的接口 */
    public static final String Http_Api_GetTagList = Http_Base + "User/user_label";
    /* 完善用户名称数据的接口(带标签) */
    public static final String Http_Api_Update_UName_City_Tag = Http_Base + "User/uploadInformation";
    /* 完善用户名称数据的接口 */
    public static final String Http_Api_Update_UName_City = Http_Base + "User/uploadInformation";
    /* 获取广告列表数据的接口 */
    public static final String Http_Api_GetAdvList = Http_Base + "Ad/adList";
    /* 给广告点赞的接口 */
    public static final String Http_Api_DoZan = Http_Base + "ad/like";
    /* 获取广告详情数据的接口 */
    public static final String Http_Api_GetAdvDesc = Http_Base + "Ad/adDetail";
    /* 获取广告分享收益的接口 */
    public static final String Http_Api_GetShareMoney = Http_Base + "Ad/shareGetMoney";
    /* 获取广告分发时候的可选分类的接口 */
    public static final String Http_Api_GetSendType = Http_Base + "request/request_list";
    /* 投放广告需求的接口 */
    public static final String Http_Api_SendAdv = Http_Base + "request/request_handel";
    /* 检查更新版本的接口 */
    public static final String Http_Api_GetVersion = Http_Base + "Server/updateInfo";
    public static final String Http_Api_GetVersion_Test = Http_Base + "Server/updateInfotest";
    /* 忘记密码短信验证码接口*/
    public static final String Http_Api_Get_Mobile_Code = Http_Base + "Regist/forgetcode";
    /* 忘记密码接口*/
    public static final String Http_Api_Forget = Http_Base + "Regist/forgetpwd";
    /*签到接口*/
    public static final String Http_Api_Sign = Http_Base + "user/check";
    /*收益分析获取链接接口*/
    public static final String Http_Api_IncomeGetUrl = Http_Base + "user/income";
    /*用户协议*/
    public static final String Http_Api_UserArgreement = Http_Base + "agreement/agreement";
    /*提现教程*/
    public static final String Http_Api_GetMoney = "http://toshare.91jierong.com/index.php/home/Realize/teach";
    /*帮助中心*/
    public static final String Http_Api_HelperCenter = Http_Base + "help/help";
    /*InComeRule*/
    public static final String Http_Api_InComeRule = Http_Base + "help/rule";
    /*新人领取大礼包接口*/
    public static final String Http_Api_GetGigt = Http_Base + "new/newpeople";
    /*推荐奖*/
    public static final String Http_Api_Recommend = Http_Base + "User/recommend";
    /*白条额度*/
    public static final String Http_Api_While_line = Http_Base + "user/baitiao";
    /*领取白条额度*/
    public static final String Http_Api_getWhileBars = Http_Base + "user/getbaitiao";
    /*白条使用说明*/
    public static final String Http_Api_While_line_information = Http_Base + "help/bar_rule";
    /*白条使用说明*/
    public static final String Http_Api_While_line_Bar_Agreement = Http_Base + "help/bar_agreement";
    /* 品质家电 */
    public static final String Http_Api_Pzjd = Http_Base + "taobk/homejd";
    /*获取分享达人的列表接口*/
    public static final String Http_Api_GetMasterData = Http_Base + "talent/talent";
    /*达人点赞和收藏接口：*/
    public static final String Http_Api_Myclick = Http_Base + "talent/myclick";
    /*我的收藏：*/
    public static final String Http_Api_MyCollect = Http_Base + "user/mycollect";
    /*评论列表：*/
    public static final String Http_Api_CommentList = Http_Base + "talent/comment_list";
    /*取消收藏*/
    public static final String Http_Api_Cancel = Http_Base + "user/canclec";
    /*评论列表：*/
    public static final String Http_Api_Comment = Http_Base + "talent/comment";
    /*收藏和点赞状态的接口：*/
    public static final String Http_Api_State = Http_Base + "talent/cl_state";
    /*达人细则*/
    public static final String Http_Api_Drxz = "http://toshare.91jierong.com/index.php/home/talent/drxz";
    /*退出登录*/
    public static final String Http_Api_LoginOut = Http_Base + "login/userLoginOut";
    /*激活额度状态*/
    public static final String Http_Api_Jiwhite = Http_Base + "user/jiwhite";
    /*激活额度接口*/
    public static final String Http_Api_WhiteHandel = Http_Base + "user/whitehandel";
    /*淘宝客查询预计返利接口*/
    public static final String Http_Api_TaoRebate = Http_Base + "Taobk/rebate";
    /*淘宝客超值返利接口*/
    public static final String Http_Api_TaoValure = Http_Base + "taobk/valuer";
    /*淘宝客今日特惠接口*/
    public static final String Http_Api_TaoPreferential = Http_Base + "taobk/todayso";
    /*淘宝客今日特惠接口*/
    public static final String Http_Api_Allgoods = Http_Base + "taobk/allgood";

    /*淘宝客提交接口*/
    public static final String Http_Api_Submit = Http_Base + "taobk/apply";
    /*淘宝客结算佣金接口*/
    public static final String Http_Api_TaoUpdatem = Http_Base + "Taobk/updatem";
    /*淘宝客商品列表接口*/
    public static final String Http_Api_GoodsLists = Http_Base + "taobk/apply_lst";
    /* 商品UIL接口*/
    public static final String Http_Api_GoodsUrl = Http_Base + "taobk/catelist";
    /* 商品搜索UIL接口*/
    public static final String Http_Api_GoodsSS = Http_Base + "taobk/listsearch";
    /* 推荐分享接口*/
    public static final String Http_Api_WXShare = Http_Base + "user/tj_share";
    /* 客服服务接口*/
    public static final String Http_Api_customer = Http_Base + "help/customerservice";

    /* 获取购物记录接口*/
    public static final String Http_Api_BuyList = Http_Base + "user/shoping";
    /* 获取购物返利接口*/
    public static final String Http_Api_FanList = Http_Base + "user/backmoney";
    /* 获取白赚分析接口*/
    public static final String Http_Api_BzList = Http_Base + "user/easytoearn";
    /* 周排行榜数据接口*/
    public static final String Http_Api_Zphb = Http_Base + "talent/week_ranking";
    /* 月排行榜数据接口*/
    public static final String Http_Api_Yphb = Http_Base + "talent/month_ranking";
    /* 达人等级明细网页路径*/
    public static final String Http_Api_DJMX = Http_Base + "talent/talent_rule";
    /* 我的红包消息列表接口*/
    public static final String Http_Api_RedPacket_List = Http_Base + "user/my_news";
    /* 获取“品质家电” 淘宝商品列表接口*/
    public static final String Http_Api_Pzjd_List = Http_Base + "taobk/homejd_upgrade";
    /* 获取“今日特惠” 淘宝商品列表接口*/
    public static final String Http_Api_Jrth_List = Http_Base + "taobk/todayso_upgrade";
    /* 获取“超值返利” 淘宝商品列表接口*/
    public static final String Http_Api_Czfl_List = Http_Base + "taobk/valuer_upgrade";
    /* 获取“爆款返利” 淘宝商品列表接口*/
    public static final String Http_Api_Bkfl_List = Http_Base + "taobk/catelist_upgrade";
    /* 设置密码的接口*/
    public static final String Http_Api_MakePw = Http_Base + "regist/band_pw";

}

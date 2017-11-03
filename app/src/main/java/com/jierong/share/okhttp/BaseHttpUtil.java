package com.jierong.share.okhttp;

import android.text.TextUtils;
import com.jierong.share.util.LogUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @author: qingf
 * @date: 2016/6/3.
 * @desc: 通用HTTP数据请求代理工具
 *
 * -1       服务端返回的错误信息
 * -101     接口封装异常
 * -102     接口调用失败
 * -103     JSON转换异常
 * -104     服务端返回数据为空
 * -105     返回结果不规范
 */
public class BaseHttpUtil {

	/**
	 * 执行普通的get请求
	 * @param url	接口请求地址
	 * @param mCallBack	回调结果
	 */
//	public void doGet(String url, final HttpStringCallBack mCallBack) {
//		try {
//			LogUtil.e("接口路径: " + url);
//			OkGo.get(url).tag(this).execute(new StringCallback() {
//				/****** onBefore、onAfter暂时不需要监听，暂不考虑 ******/
//
//				@Override
//				public void onSuccess(String t, Call call, Response response) {
//					try {
//						if (TextUtils.isEmpty(t))
//							mCallBack.onFailure(-104, "服务端返回数据为空");
//
//						LogUtil.e("结果串: " + t);
//                        JSONObject jsObj = new JSONObject(t);
//						// 有个问题，如何判断正规的json字符串是我们接口的返回串呢？
//						int resultCode = jsObj.optInt("code");
//                        if(resultCode == 1) {
//                            String data = jsObj.optString("data");
//                            mCallBack.onSuccess(data);
//                        } else {
//                            String errMsg = jsObj.optString("msg");
//                            mCallBack.onFailure(resultCode, errMsg);
//                        }
//                    } catch (JSONException e) {
//                        mCallBack.onFailure(-103, "JSON转换异常");
//                        e.printStackTrace();
//                    } catch (Exception e) {
//                        mCallBack.onFailure(-105, "返回结果不规范");
//                        e.printStackTrace();
//                    }
//				}
//
//				@Override
//				public void onError(Call call, Response response, Exception e) {
//					super.onError(call, response, e);
//					mCallBack.onFailure(-102, "接口调用失败");
//                    e.printStackTrace();
//				}
//			});
//		} catch (Exception e) {
//			mCallBack.onFailure(-101, "接口封装异常");
//			e.printStackTrace();
//		}
//	}

	/**
	 * 执行普通的post请求
	 * @param url	接口请求地址
	 * @param mCallBack	回调结果
	 */
	public void doPost(String url, HttpParams httpParams, final HttpStringCallBack mCallBack) {
		try {
			LogUtil.e("接口路径: " + url);
			LogUtil.e("接口参数: " + httpParams.toString());
			OkGo.post(url).tag(this)
					.params(httpParams)
					.execute(new StringCallback() {
				/****** onBefore、onAfter暂时不需要监听，暂不考虑 ******/

				@Override
				public void onSuccess(String t, Call call, Response response) {
					try {
						if (TextUtils.isEmpty(t))
							mCallBack.onFailure(-104, "服务端返回数据为空");

						LogUtil.e("结果串: " + t);
						JSONObject jsObj = new JSONObject(t);
						// 有个问题，如何判断正规的json字符串是我们接口的返回串呢？
						int resultCode = jsObj.optInt("code");
						if(resultCode == 1) {
							String data = jsObj.optString("data");
							mCallBack.onSuccess(data);
						} else {
							String errMsg = jsObj.optString("msg");
							mCallBack.onFailure(resultCode, errMsg);
						}
					} catch (JSONException e) {
						mCallBack.onFailure(-103, "JSON转换异常");
						e.printStackTrace();
					} catch (Exception e) {
						mCallBack.onFailure(-105, "返回结果不规范");
						e.printStackTrace();
					}
				}

				@Override
				public void onError(Call call, Response response, Exception e) {
					super.onError(call, response, e);
					mCallBack.onFailure(-102, "接口调用失败");
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			mCallBack.onFailure(-101, "接口封装异常");
			e.printStackTrace();
		}
	}



	/**
	 * 执行普通的get请求
	 * @param time	接口请求过期时间
	 * @param url	接口请求地址
	 * @param mCallBack	回调结果
	 */
	public void doGet(int time, String url, final HttpStringCallBack mCallBack) {
		try {
			LogUtil.e("接口路径: " + url);
			OkGo.get(url).tag(this).connTimeOut(time).execute(new StringCallback() {
				/****** onBefore、onAfter暂时不需要监听，暂不考虑 ******/

				@Override
				public void onSuccess(String t, Call call, Response response) {
					try {
						if (TextUtils.isEmpty(t))
							mCallBack.onFailure(-104, "服务端返回数据为空");

						LogUtil.e("结果串: " + t);
						JSONObject jsObj = new JSONObject(t);
						int resultCode = jsObj.optInt("code");
						if(resultCode == 1) {
							String data = jsObj.optString("data");
							mCallBack.onSuccess(data);
						} else {
							String errMsg = jsObj.optString("msg");
							mCallBack.onFailure(resultCode, errMsg);
						}
					} catch (JSONException e) {
						mCallBack.onFailure(-103, "JSON转换异常");
						e.printStackTrace();
					} catch (Exception e) {
						mCallBack.onFailure(-105, "返回结果不规范");
						e.printStackTrace();
					}
				}

				@Override
				public void onError(Call call, Response response, Exception e) {
					super.onError(call, response, e);
					mCallBack.onFailure(-102, "接口调用失败");
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			mCallBack.onFailure(-101, "接口封装异常");
			e.printStackTrace();
		}
	}

	/**
	 * 执行普通的文件下载请求
	 * @param path	文件保存路径
	 * @param name	文件名称
	 * @param url	文件下载地址
	 * @param mCallBack	回调结果
	 */
	public void doDownloadFile(final String path, final String name,
			String url, final HttpFileCallBack mCallBack) {
		try {
			// 已经在HttpFileTranslate中封装过了
			// File dir = new File(path);
			// if (!dir.exists()) dir.mkdirs();
			// File file = new File(dir, name);
			// if (file.exists()) file.delete();
			
			LogUtil.e("接口路径: " + url + "下载文件保存地址: " + path);
			OkGo.get(url).tag(this).execute(new HttpFileTranslate(path, name) {

				@Override
				public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
					mCallBack.inProgress(currentSize, totalSize, progress, networkSpeed);
				}
				
				@Override
				public void onSuccess(File file, Call call, Response response) {
					mCallBack.onSuccess(file);
				}
				
				@Override
				public void onError(Call call, Response response, Exception e) {
					super.onError(call, response, e);
					// 默认内定的路径和文件名都是已知的确定值
					File dir = new File(path);
					if (!dir.exists()) dir.mkdirs();
					File file = new File(dir, name);
					if (file.exists()) file.delete();
					dir.deleteOnExit();		// 貌似没用，文件夹不会删除
					mCallBack.onError(e);
				}
			});
		} catch (Exception e) {
			mCallBack.onError(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 支持断点下载的功能
	 * DownloadManager、DownloadInfo、DownloadListener的参与
	 * 1.DownloadService.getDownloadManager() 获取DownloadManager
	 * 2.downloadManager.getDownloadInfo("Tag"); 获取指定的DownloadInfo
	 * 3.downloadInfo.setListener(downListener); 如果任务存在，把任务的监听换成当前页面需要的监听
	 * 4.refreshUi(downloadInfo) 需要第一次手动刷一次
	 * 5.downloadManager.getDownloadInfo("Tag") 每次点击的时候，需要更新当前对象
	 * 6.downloadManager.addTask("Tag", OkHttpUtils.get(mUrl), downListener); 添加下载任务
	 * 7.downloadManager.pauseTask("Tag") 暂停下载任务
	 * 8.downloadManager.removeTask("Tag") 删除下载任务
	 * 9.downloadManager.restartTask("Tag") 重启下载任务
	 * 10.onResume()生命周期中执行“4”操作refreshUi(downloadInfo)
	 * 11.onDestroy()生命周期中执行downloadInfo.removeListener()
	 * 12.	downloadInfo.getState()、 downloadInfo.getDownloadLength()
	 * 		downloadInfo.getTotalLength()、downloadInfo.getNetworkSpeed()
	 * 		(Math.round(downloadInfo.getProgress() * 10000) * 1.0f / 100) + "%"
	 */
	
	/**
	 * 上传某个用户的头像信息
	 * @param url	上传接口地址
	 * @param uid	用户id
	 * @param token	用户token
	 * @param uic	用户头像
	 * @param mCallBack	回调结果
	 */
	public void doPostUic(String url, String uid, String token, File uic, final HttpUlCallBack mCallBack) {
		try {
			LogUtil.e("接口路径: " + url);
			OkGo.post(url).tag(this)
					.params("Uid", uid)
					.params("Token", token)
					.params("Uic", uic)
					.execute(new StringCallback() {

						@Override
						public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
							mCallBack.upProgress(currentSize, totalSize, progress, networkSpeed);
						}

						@Override
						public void onSuccess(String t, Call call, Response response) {
							mCallBack.onSuccess();
						}

						@Override
						public void onError(Call call, Response response, Exception e) {
							super.onError(call, response, e);
							mCallBack.onFailure();
							e.printStackTrace();
						}
					});
		} catch (Exception e) {
			mCallBack.onFailure();
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行文件上传请求
	 * @param url	上传接口地址
	 * @param params	上传的参数键值对
	 * @param key	上传文件的标识
	 * @param files	上传的文件集合
	 * @param mCallBack	回调结果
	 */
	public void doPostMore(String url, Map<String, String> params, 
			String key, ArrayList<File> files, final HttpUlCallBack mCallBack) {
		try {
			LogUtil.e("接口路径: " + url);
			OkGo.post(url).tag(this)
			.params(params)
			.addFileParams(key, files)
			.execute(new StringCallback() {
				
				@Override
				public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
					mCallBack.upProgress(currentSize, totalSize, progress, networkSpeed);
				}
				
				@Override
				public void onSuccess(String t, Call call, Response response) {
					mCallBack.onSuccess();
				}
				
				@Override
				public void onError(Call call, Response response, Exception e) {
					super.onError(call, response, e);
					mCallBack.onFailure();
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			mCallBack.onFailure();
			e.printStackTrace();
		}
	}
	
	/**
     * 关闭网络请求
     */
    public void closeHttp() {
        try {
			OkGo.getInstance().cancelTag(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
}

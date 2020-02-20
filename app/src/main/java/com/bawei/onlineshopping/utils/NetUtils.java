package com.bawei.onlineshopping.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Time: 2020/2/20
 * Author: 王冠华
 * Description:
 * 网络工具类,编写网络判断方法,获取网络数据
 */
public class NetUtils {
    //饿汉单例模式,方便调用
    private static NetUtils netUtils=new NetUtils();
    private NetUtils(){}
    public static NetUtils getInstance(){
        return netUtils;
    }
    //网络判断方法
    public boolean isNetWork(Context context){
        //获取网络状态
       ConnectivityManager cm= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        //判断网络状态
        if(info!=null){
            return true;
        }else {
            return false;
        }
    }
    //判断是否是wifi
    public boolean isWifi(Context context){
        //获取网络状态
        ConnectivityManager cm= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        //判断是否是WiFi
        if(info!=null&&ConnectivityManager.TYPE_WIFI==info.getType()){
            return true;
        }else {
            return false;
        }

    }
    //获取Json数据方法
    public void getUserReg(final String path, final Map<String,String>map, final ICallBack iCallBack){
        //开启线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    //连接
                    HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                    //GET请求
                    conn.setRequestMethod("POST");
                    //设置连接超时为5秒钟，
                    conn.setConnectTimeout(5000);
                    // 读取超时为5秒钟
                    conn.setReadTimeout(5000);
                    //允许输出
                    conn.setDoOutput(true);
                    //允许输入
                    conn.setDoInput(true);
                    StringBuilder builder = new StringBuilder();
                    //遍历集合
                    for (Map.Entry<String,String>entry:map.entrySet()){
                        String key = entry.getKey();
                        String value = entry.getValue();
                        builder.append(key+"="+value+"&");
                    }
                    String user = builder.toString();
                    user=user.substring(0,user.length()-1);
                    Log.i("test",""+user);
                    //获取输出流
                    OutputStream ops = conn.getOutputStream();
                    //转换成字节
                    ops.write(user.getBytes());
                    ops.flush();
                    conn.connect();
                    //获取响应吗
                    int responseCode = conn.getResponseCode();
                    //判断
                    if(responseCode==200){
                        //获取流
                        final InputStream inputStream = conn.getInputStream();
                        //读取流
                        int len=0;
                        StringBuilder sb = new StringBuilder();
                        byte[] bt=new byte[1024];
                        while ((len=inputStream.read(bt))!=-1){
                            //读取字节
                            String s = new String(bt, 0, len);
                            //存入StringBuilder
                            sb.append(s);
                        }
                        //将字节转换成字符
                        final String json = sb.toString();
                        Log.i("xxx",json+"");
                        //关闭流
                        inputStream.close();
                        //利用handler更新
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(iCallBack!=null) {
                                    iCallBack.onSuccess(json);
                                }
                            }
                        });
                    }else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(iCallBack!=null)
                                iCallBack.onError("网络请求失败");

                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("xxx",e.toString()+"");
                }


            }
        }).start();
    }
    //创建handler
    private Handler handler=new Handler();
    //创建接口
    public interface ICallBack{
        //成功方法
        void onSuccess(String json);
        //失败方法
        void onError(String mgs);
    }
}

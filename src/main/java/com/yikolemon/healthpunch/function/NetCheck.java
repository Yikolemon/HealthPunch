package com.yikolemon.healthpunch.function;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * 网络阻塞检测方法
 */
public class NetCheck {

    public static void NetCheck(Label label){
        try {
            boolean netState=false;
            //等待网络通畅
            while (!netState){
                Process p1 = Runtime.getRuntime().exec("ping -n 1 14.215.177.39");
                int returnVal = p1.waitFor();
                netState = (returnVal==0);
                if (netState){
                    break;
                }else {
                    Thread.sleep(5000);
                }
            }
            NetCheck.connectSuccess(label);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void connectSuccess(Label label){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                label.setText("账户登录中");
            }
        });
    }
}

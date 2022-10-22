package com.yikolemon.healthpunch;

import com.yikolemon.healthpunch.function.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import org.openqa.selenium.chrome.ChromeDriver;
import javafx.application.Platform;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class PunchThread implements Runnable{

    private AllController controller;

    private ChromeDriver chromeDriver;

    public PunchThread(AllController controller,ChromeDriver driver) {
        this.controller = controller;
        this.chromeDriver=driver;
    }

    @Override
    public void run() {
        Label netState = controller.NetState;


        //打卡检测

        UserUtil user = UserUtil.getUser();
        //PunchCheck.PunchCheck();
        //网络检测
        NetCheck.NetCheck(controller.NetState);


        boolean login = UserLogin.login(chromeDriver);

        if (!login){
            //失败了，程序在此结束
            UserLogin.loginFail(controller.NetState,controller.imageView);
            Exit.chromeExit(chromeDriver);
        }else {
            //程序继续执行
            UserLogin.loginSuccess(controller.NetState);
            Punch.punch(chromeDriver,controller.NetState,controller.imageView);
        }

    }



}

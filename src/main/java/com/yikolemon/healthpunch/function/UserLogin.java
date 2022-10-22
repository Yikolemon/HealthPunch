package com.yikolemon.healthpunch.function;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.Semaphore;


public class UserLogin {

    public static boolean login(ChromeDriver driver){
        UserUtil user = UserUtil.getUser();
        driver.get("http://authserver.nufe.edu.cn/authserver/login");
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.equals("http://authserver.nufe.edu.cn/authserver/login")){
            //未登录
            //driver.manage().timeouts().pageLoadTimeout(5,TimeUnit.SECONDS);
            //测试中只用sleep有效
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            WebDriverWait webDriverWait = new WebDriverWait(driver,30);
            //webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
            WebElement username = driver.findElementById("username");
            username.click();
            username.clear();
            username.sendKeys(user.getUsername());
            //webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("password")));
            WebElement password = driver.findElementById("password");
            //password.click();
            password.sendKeys(user.getPassword());
            WebElement login_submit = driver.findElementById("login_submit");
            login_submit.click();
            currentUrl=driver.getCurrentUrl();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (currentUrl.equals("http://authserver.nufe.edu.cn/authserver/login")){
                //登录失败了
                return false;
            }else {
                return true;
            }
        }
        //登录失败
        return true;

    }

    public static void loginSuccess(Label label){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                label.setText("健康打卡中");
            }
        });

    }

    public static void loginFail(Label label, ImageView imageView){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                label.setText("登录失败！");
                imageView.setImage(new Image("error.png"));
            }
        });
    }


}

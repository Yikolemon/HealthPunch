package com.yikolemon.healthpunch.function;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Punch {

    public static void punch(ChromeDriver driver,Label label,ImageView imageView){
        driver.get("http://authserver.nufe.edu.cn/authserver/login?service=" +
                "https%3A%2F%2Fzntb.nufe.edu.cn%3A8600%2F%23%2Fdform%2FgenericForm%2FsvESFsbo");
        //显示等待
        WebDriverWait wait = new WebDriverWait(driver, 5);
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//span[contains(.,'去填报')]")));
        } catch (Exception e) {
            havePunched(label,imageView);
            Exit.chromeExit(driver);
            return;
        }
        /*try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        WebElement btn = driver.findElementByXPath("//span[contains(.,'去填报')]");
        btn.click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//span[contains(.,'已了解并确认')]")));
        WebElement btn2 = driver.findElementByXPath("//span[contains(.,'已了解并确认')]");
        btn2.click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//span[contains(.,'提 交')]")));
        WebElement btn3 = driver.findElementByXPath("//span[contains(.,'提 交')]");
        btn3.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            driver.findElementByXPath("//span[contains(.,'表单提交成功')]");
        } catch (Exception e) {
            //表单提交失败了
            punchFail(label,imageView);
            Exit.chromeExit(driver);
            return;
        }
        punchSuc(label,imageView);
        Exit.chromeExit(driver);
        return;
    }

    public static void punchFail(Label label,ImageView imageView){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                label.setText("打卡失败！");
                imageView.setImage(new Image("error.png"));
            }
        });
    }

    public static void punchSuc(Label label,ImageView imageView){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                label.setText("打卡成功！");
                imageView.setImage(new Image("ok.png"));
            }
        });
    }

    public static void havePunched(Label label, ImageView image){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                label.setText("无需重复打卡");
                image.setImage(new Image("ok.png"));
            }
        });
    }
}

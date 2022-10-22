package com.yikolemon.healthpunch;

import com.yikolemon.healthpunch.function.*;
//import com.yikolemon.healthpunch.function.PunchCheck;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainApplication extends Application {

    private AllController controller;

    public static void setDriver(ChromeDriver driver) {
        MainApplication.driver = driver;
    }

    private static ChromeDriver driver;

    private Thread thread;

    @FXML
    private Label NetState;

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 200, 200);
        stage.setTitle("自动健康打卡");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.getIcons().add(new Image("img.png"));
        stage.show();
        stage.setOnCloseRequest(e->{
            if (driver!=null){
                Exit.chromeExit(driver);
            }
            if (thread!=null){
                try {
                    thread.stop();
                } catch (Exception ex) {
                    System.exit(0);
                }
            }
        });

        //时间检测
        boolean time = TimeCheck.TimeCheck();
        if (!time){
            System.exit(0);
        }
        controller=fxmlLoader.getController();

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        //账户登录
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.managed_default_content_settings.geolocation", 2);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("disable-geolocation");
        //options.addArguments("start-maximized");
        options.addArguments("window-size=800,400");
        driver = new ChromeDriver(options);

        thread = new Thread(new PunchThread(controller,driver));
        thread.start();


    }

    public static void main(String[] args) {
        launch();
    }


}

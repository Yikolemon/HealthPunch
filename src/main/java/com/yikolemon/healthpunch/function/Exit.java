package com.yikolemon.healthpunch.function;

import com.yikolemon.healthpunch.MainApplication;
import org.openqa.selenium.chrome.ChromeDriver;

public class Exit {
    public static void chromeExit(ChromeDriver driver){
        if (driver!=null){
            driver.close();
            driver.quit();
            MainApplication.setDriver(null);
        }
    }
}

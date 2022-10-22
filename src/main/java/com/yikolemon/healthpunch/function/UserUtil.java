package com.yikolemon.healthpunch.function;

import java.io.*;
import java.util.PropertyResourceBundle;

/**
 * User的单例模式，用于登录
 */
public class UserUtil {

    private UserUtil() {
    }
    private UserUtil(String username, String password){
        this.username=username;
        this.password=password;
    }
    private String username;
    private String password;


    private static UserUtil user;

    public static UserUtil getUser(){
        if (UserUtil.user==null){
            synchronized (UserUtil.class){
                if (UserUtil.user==null){
                    File file = new File("editMe.properties");
                    System.out.println(file.getAbsolutePath());
                    try( FileInputStream fileInputStream = new FileInputStream(file);
                         BufferedInputStream inputStream = new BufferedInputStream(fileInputStream);
                    ){
                        PropertyResourceBundle propertyResourceBundle = new PropertyResourceBundle(inputStream);
                        String username = propertyResourceBundle.getString("username");
                        String password = propertyResourceBundle.getString("password");
                        return new UserUtil(username,password);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }
        return user;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}

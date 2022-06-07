package com.Karchat.util;

import com.Karchat.util.BeansUtil.KarConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 程序用到的常量
 */
public class Constant {
    //Spring上下文
    public static ApplicationContext context = new AnnotationConfigApplicationContext(KarConfiguration.class);


}

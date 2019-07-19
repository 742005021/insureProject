package org.java.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/toindex").setViewName("index");
        registry.addViewController("/tologin").setViewName("login");
        registry.addViewController("/toregister").setViewName("register");
        registry.addViewController("/tomypolicy").setViewName("custop/mypolicy");
       // registry.addViewController("/toscore").setViewName("custop/score");
        registry.addViewController("/tomessage").setViewName("custop/message");
        registry.addViewController("/tosafety").setViewName("custop/safety");
        registry.addViewController("/tofamily").setViewName("custop/family");
        registry.addRedirectViewController("/", "/index");
        //registry.addViewController("/tocoupon").setViewName("custop/coupon");
    }
}

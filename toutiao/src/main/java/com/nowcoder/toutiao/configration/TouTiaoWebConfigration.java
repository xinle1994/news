package com.nowcoder.toutiao.configration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.nowcoder.toutiao.inteceptor.LoggingRequiredInterceptor;
import com.nowcoder.toutiao.inteceptor.PassportInterceptor;


/**
 * 用于自定义的拦截器
 * @author Administrator
 *
 */

@Component
public class TouTiaoWebConfigration extends WebMvcConfigurerAdapter {
	
	
	@Autowired
	PassportInterceptor passportInterceptor;
	
	@Autowired
	LoggingRequiredInterceptor loggingRequiredInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		// 访问全局页面都会进行访问
		registry.addInterceptor(passportInterceptor);
		
		//只处理相关的页面，，，，（需要权限的 页面）
		registry.addInterceptor(loggingRequiredInterceptor).addPathPatterns("/addNews*").addPathPatterns("/msg/*");
		super.addInterceptors(registry);
	}

}

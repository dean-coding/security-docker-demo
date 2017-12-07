package com.example.demo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author fuhw
 * @date 2017年11月29日 
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{

	/** 
	 *(non-Javadoc)
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addViewControllers(org.springframework.web.servlet.config.annotation.ViewControllerRegistry)
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		super.addViewControllers(registry);
		registry.addViewController("/managerlogin").setViewName("managerlogin");
		registry.addViewController("/consumerlogin").setViewName("consumerlogin");
	}
}

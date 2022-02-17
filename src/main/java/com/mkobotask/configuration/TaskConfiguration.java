package com.mkobotask.configuration;

import com.mkobotask.MkoboTaskApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackageClasses = MkoboTaskApplication.class)
@PropertySource("classpath:/application.properties")
public class TaskConfiguration {

}

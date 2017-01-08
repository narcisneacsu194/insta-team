package com.teamtreehouse.instateam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

@Configuration
public class TemplateConfig {
    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        final SpringResourceTemplateResolver springResourceTemplateResolver = new SpringResourceTemplateResolver();
        springResourceTemplateResolver.setCacheable(false);
        springResourceTemplateResolver.setPrefix("classpath:/templates/");
        springResourceTemplateResolver.setSuffix(".html");
        return springResourceTemplateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(){
        final SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setTemplateResolver(templateResolver());
        return springTemplateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver(){
        final ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(templateEngine());
        thymeleafViewResolver.setOrder(1);
        return thymeleafViewResolver;
    }
}

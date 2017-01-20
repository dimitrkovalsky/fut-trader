package com.liberty.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.liberty.common.JsonHelper;
import com.liberty.common.StringConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

@Configuration
@EnableWebMvc
@Slf4j
public class AppConfig extends WebMvcConfigurerAdapter {

    public static final String ALL_URL_MASK = "/*";
    public static final String FORWARD_INDEX_HTML = "forward:/index.html";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(ALL_URL_MASK).addResourceLocations(StringConstants.ROOT_WEB_PATH);
        registry.addResourceHandler("/swagger/**").addResourceLocations("/swagger/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(StringConstants.ROOT_WEB_PATH).setViewName(FORWARD_INDEX_HTML);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setOneIndexedParameters(true);
        argumentResolvers.add(resolver);
        super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(mappingJackson2HttpMessageConverter());
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper =
                new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD'T'hh:mm:ss");
        objectMapper.setDateFormat(dateFormat);
        objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, true);
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        converter.setObjectMapper(objectMapper);
        JsonHelper.setObjectMapper(objectMapper);
        return converter;
    }


}

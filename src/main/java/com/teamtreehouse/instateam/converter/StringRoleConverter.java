package com.teamtreehouse.instateam.converter;

import com.teamtreehouse.instateam.dao.RoleDao;
import com.teamtreehouse.instateam.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
// This spring component converts Role objects from the String form that it has in Thymeleaf view to
// it's actual counterpart from Java code.
@Component
public class StringRoleConverter implements Converter<String, Role>{

    @Autowired
    private RoleDao roleDao;

    // This method takes the role identifier as a string, converts it to a Long variable, and it uses it
    // to retrieve that specific object from the database.
    @Override
    public Role convert(String source) {
        return roleDao.findById(new Long(source));
    }

    // This Bean method autowires a ConversionService object. The conversion operation would not be possible without this Java bean.
    @Bean
    public ConversionService getConversionService(){
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        Set<Converter> converters = new HashSet<>();
        converters.add(new StringRoleConverter());
        bean.setConverters(converters);
        return bean.getObject();
    }
}

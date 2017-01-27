package com.teamtreehouse.instateam.converter;

import com.teamtreehouse.instateam.dao.CollaboratorDao;
import com.teamtreehouse.instateam.model.Collaborator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
// This spring component converts Collaborator objects from the String form that it has in Thymeleaf view to
// it's actual counterpart from Java code.
@Component
public class StringCollaboratorConverter implements Converter<String, Collaborator>{
    @Autowired
    private CollaboratorDao collaboratorDao;

    // This method takes the collaborator identifier as a string, converts it to a Long variable, and it uses it
    // to retrieve that specific object from the database.
    @Override
    public Collaborator convert(String source) {
        return collaboratorDao.findById(new Long(source));
    }

    // This Bean method autowires a ConversionService object. The conversion operation would not be possible without this Java bean.
    @Bean
    public ConversionService getConversionService(){
        ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();
        Set<Converter> converters = new HashSet<>();
        converters.add(new StringCollaboratorConverter());
        conversionServiceFactoryBean.setConverters(converters);
        return conversionServiceFactoryBean.getObject();
    }
}

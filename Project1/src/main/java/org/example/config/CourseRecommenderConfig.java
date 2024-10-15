package org.example.config;


import org.example.CourseRecommender;
import org.example.CourseRecommenderImpl2;

import org.example.mapper.AuthorMapper;
import org.example.mapper.CourseMapper;
import org.example.services.impl.CourseRecommenderImpl1;
import org.example.services.impl.CourseRecommenderImpl3;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CourseRecommenderConfig {


    @Qualifier("CourseRecommenderImpl1")
    private CourseRecommender courseRecommender;


    @Primary
    public CourseRecommender primaryRecommender() {
        return new CourseRecommenderImpl1();
    }

    @Bean(name = "secondaryRecommender")

    public CourseRecommenderImpl2 secondaryRecommender() {
        return new CourseRecommenderImpl3();
    }


    @Bean
    public CourseMapper courseMapper(){
        return Mappers.getMapper(CourseMapper.class);
    }

    @Bean
    public AuthorMapper authorMapper(){
        return Mappers.getMapper(AuthorMapper.class);
    }

}

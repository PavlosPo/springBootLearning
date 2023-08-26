package com.chapter3.learning.SERVICES;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("dev")
@Service
public class FauxServiceDEV implements DatasourceService{

    @Override
    public String getDatasource(){
        return "dev datasource";
    }
}

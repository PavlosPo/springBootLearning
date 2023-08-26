package com.chapter3.learning.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("uat")
@Service("fauxService")
public class FauxServiceUAT implements DatasourceService{

    @Override
    public String getDatasource(){
        return "uat datasource";
    }
}

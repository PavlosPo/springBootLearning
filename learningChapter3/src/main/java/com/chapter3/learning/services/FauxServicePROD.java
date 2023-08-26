package com.chapter3.learning.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("prod")
@Service("fauxService")
public class FauxServicePROD implements DatasourceService{

    @Override
    public String getDatasource(){
        return "prod datasource";
    }
}

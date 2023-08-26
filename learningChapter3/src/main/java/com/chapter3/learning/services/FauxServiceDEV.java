package com.chapter3.learning.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile({"dev", "default"})
@Service("fauxService")
public class FauxServiceDEV implements DatasourceService{

    @Override
    public String getDatasource(){
        return "dev datasource";
    }
}

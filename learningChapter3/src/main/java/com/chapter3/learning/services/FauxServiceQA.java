package com.chapter3.learning.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("qa")
@Service("fauxService")
public class FauxServiceQA implements DatasourceService{

    @Override
    public String getDatasource(){
        return "qa datasource";
    }
}

package com.chapter3.learning.controllers;

import com.chapter3.learning.services.DatasourceService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class FauxController {

    private DatasourceService datasourceService;

    public FauxController(@Qualifier("fauxService") DatasourceService datasourceService) {
        this.datasourceService = datasourceService;
    }

    public String getDatasource() {
        return datasourceService.getDatasource();
    }
}

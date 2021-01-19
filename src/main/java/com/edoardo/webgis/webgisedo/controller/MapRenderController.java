package com.edoardo.webgis.webgisedo.controller;
import com.edoardo.webgis.webgisedo.entity.Province;
import com.edoardo.webgis.webgisedo.repo.ProviceRepo;
import com.edoardo.webgis.webgisedo.service.GeoJsonFactoryService;
import org.geojson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Logger;

@Controller

public class MapRenderController
{
    private final static Logger log = Logger.getLogger(MapRenderController.class.getName());
    @Autowired
    ProviceRepo proviceRepo;
    @GetMapping(value = {"/main","/"})
    public String maprender(@RequestParam(name="name", required=false, defaultValue="prova") String name, Model model)
    {
        model.addAttribute("name",name);
        return "main";
    }
    @GetMapping(value = "/getprov",produces="application/json")
    public @ResponseBody  FeatureCollection getAllprov() {

        GeoJsonFactoryService geoJsonFactory = new GeoJsonFactoryService();
        return geoJsonFactory.makeGeoJson(proviceRepo.findAll());


    }


}

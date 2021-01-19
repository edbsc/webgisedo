package com.edoardo.webgis.webgisedo.controller;
import com.edoardo.webgis.webgisedo.entity.ProvinceEntity;
import com.edoardo.webgis.webgisedo.repo.ProviceRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.geojson.*;
import org.geojson.Polygon;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller

public class MapRenderController
{
    @Autowired
    ProviceRepo proviceRepo;
    @GetMapping(value = {"/main","/"})
    public String maprender(@RequestParam(name="name", required=false, defaultValue="prova") String name, Model model)
    {
        model.addAttribute("name",name);
        return "main";
    }
    @GetMapping(value = "/getprov",produces="application/json")
//    public @ResponseBody  ArrayList<HashMap<String,String>> getAllprov() {
//        List<ProvinceEntity> prov = proviceRepo.findAll();
//        GeometryFactory ringmaker = new GeometryFactory();
//        ArrayList<Polygon> confini = new ArrayList<>();
//        ArrayList<HashMap<String,String>> json = new ArrayList<>();
//        for(ProvinceEntity p : prov.subList(0,prov.size()))
//        {
//            HashMap<String,String> map = new HashMap<>();
//            map.put("ID",""+p.getGid());
//            map.put("Codice",p.getSigla());
//            map.put("Nome",p.getDen_prov());
//            Geometry geom = p.getGeom().getGeometryN(0);
//            if(geom.isSimple())
//            {
//                map.put("type","FeatureCollection");
//                Coordinate start = geom.getCoordinates()[0];
//               Coordinate finish = geom.getCoordinates()[geom.getCoordinates().length-1];
//               if(start.equals(finish))
//               {
//                   confini.add(ringmaker.createPolygon(geom.getCoordinates()));
//
//
//               }
//            }
//            json.add(map);
//        }
//
//        return json;
//
//    }
    public @ResponseBody  FeatureCollection getAllprov() {
        List<ProvinceEntity> prov = proviceRepo.findAll();
        FeatureCollection geojson = new FeatureCollection();
        HashMap<String,String> map =new HashMap<>();
        for(ProvinceEntity p : prov.subList(0,prov.size()))
        {
            map.put("ID",""+p.getGid());
            map.put("sigla",p.getSigla());
            map.put("nome",p.getDen_prov());
            Feature f = new Feature();
            f.setProperty("info",map);
            Polygon pol = getgeomfield(p.getGeom());
            f.setGeometry(pol);
            geojson.add(f);







        }
        return geojson;

    }
    public Polygon getgeomfield(Geometry g)
    {
        Polygon p = new Polygon();
        ArrayList<LngLatAlt> coordinates = new ArrayList<>();
        Coordinate start = g.getCoordinates()[0];
        Coordinate finish = g.getCoordinates()[g.getCoordinates().length-1];
       // if(!start.equals(finish)) return p;
        for(Coordinate c : g.getCoordinates())
        {
            coordinates.add(new LngLatAlt(c.x,c.y));
        }
        p.setExteriorRing(coordinates);
        return p;
    }


}

package com.edoardo.webgis.webgisedo.service;

import com.edoardo.webgis.webgisedo.controller.MapRenderController;
import com.edoardo.webgis.webgisedo.entity.Province;
import com.edoardo.webgis.webgisedo.repo.ProviceRepo;
import org.geojson.*;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@Service

public class GeoJsonFactoryService
{
    private final Logger log = Logger.getLogger(MapRenderController.class.getName());
    public FeatureCollection makeGeoJson(List<Province> prov)
    {
        FeatureCollection geoJson = new FeatureCollection();
        for(Province p: prov)
        {
            Feature provincia = new Feature();
            provincia.setProperty("info",createInfo(p));
            Polygon confini = new Polygon();
            org.locationtech.jts.geom.Geometry provinciaGeom = p.getGeom();
            List<LngLatAlt> points = new ArrayList<>();
            log.info(String.valueOf(provinciaGeom.getNumGeometries()));
//            if(provinciaGeom.getNumGeometries()>1)
//                for(Coordinate c : provinciaGeom.getGeometryN(1).getCoordinates())
//                    points.add(new LngLatAlt(c.x,c.y));
//            else
                for(Coordinate c : provinciaGeom.getGeometryN(0).getCoordinates())
                    points.add(new LngLatAlt(c.x,c.y));

            confini.setExteriorRing(points);
            provincia.setGeometry(confini);
            geoJson.add(provincia);

        }
        return geoJson;
    }
    private HashMap<String,String> createInfo (Province provincia)
    {
        HashMap<String,String> mappaInfo = new HashMap<>();
        mappaInfo.put("ID",String.valueOf(provincia.getGid()));
        mappaInfo.put("Sigla",provincia.getSigla());
        mappaInfo.put("Nome",provincia.getDen_prov());
        return mappaInfo;
    }
}

package com.edoardo.webgis.webgisedo.service;

import com.edoardo.webgis.webgisedo.controller.MapRenderController;
import com.edoardo.webgis.webgisedo.entity.Province;
import com.edoardo.webgis.webgisedo.repo.ProviceRepo;
import org.geojson.*;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPolygon;
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
            Geometry provinciaGeom = p.getGeom();
            MultiPolygon provinciaPol = (MultiPolygon)provinciaGeom;
            org.geojson.MultiPolygon geoObjectMultiPolygon = new org.geojson.MultiPolygon();
            for(int i=0; i<provinciaPol.getNumGeometries();i++)
            {
                List<LngLatAlt> points = new ArrayList<>();
                for(Coordinate c : provinciaPol.getGeometryN(i).getCoordinates())
                    points.add(new LngLatAlt(c.x,c.y));
                Polygon tempGeoObject = new Polygon();
                if(i==0)
                    tempGeoObject.setExteriorRing(points);
                else
                    tempGeoObject.addInteriorRing(points);
                provincia.setGeometry(tempGeoObject);
               // geoObjectMultiPolygon.add(tempGeoObject);

            }
            provincia.setGeometry(geoObjectMultiPolygon);


//





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

package com.edoardo.webgis.webgisedo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.MultiPolygon;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import javax.persistence.*;

@Entity
@Table(name ="province")
public class Province {

    @Id
    @Column(name = "gid")
    private int gid;
    @Column(name = "sigla")
    private String sigla;
    @Column(name = "den_prov")
    private String den_prov;
    @Column(name = "geom")
    @JsonIgnore
    private Geometry geom;

    public int getGid() {
        return gid;
    }
    public String getSigla()
    {
        return sigla;
    }
    @JsonIgnore
    public Geometry getGeom() {
        return geom;
    }

    public String getDen_prov() {
        return den_prov;
    }

    public void setDen_prov(String den_prov) {
        this.den_prov = den_prov;
    }

    public void setGeom(Geometry geom) {
        this.geom = geom;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
}

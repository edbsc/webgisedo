package com.edoardo.webgis.webgisedo.repo;

import com.edoardo.webgis.webgisedo.entity.ProvinceEntity;
import org.hibernate.cfg.JPAIndexHolder;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProviceRepo extends JpaRepository<ProvinceEntity, Integer> {
}

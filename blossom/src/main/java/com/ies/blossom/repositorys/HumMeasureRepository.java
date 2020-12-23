package com.ies.blossom.repositorys;

import com.ies.blossom.entitys.HumMeasure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface HumMeasureRepository extends JpaRepository<HumMeasure, Long> {

    @Query(value="SELECT * FROM hum_measures m WHERE m.sensor_id = :sensorId", nativeQuery = true)
    List<HumMeasure> findBySensorId(Long sensorId);

}

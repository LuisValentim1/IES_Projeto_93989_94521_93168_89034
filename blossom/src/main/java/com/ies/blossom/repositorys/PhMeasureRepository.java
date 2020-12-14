package com.ies.blossom.repositorys;

import com.ies.blossom.entitys.PhMeasure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PhMeasureRepository extends JpaRepository<PhMeasure, Long> {

    @Query(value = "SELECT * FROM ph_measures m WHERE m.sensor_id = :sensorId", nativeQuery = true)
    List<PhMeasure> findBySensorId(Long sensorId);
}

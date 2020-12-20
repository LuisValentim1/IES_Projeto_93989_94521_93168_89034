package com.ies.blossom.repositorys;

import com.ies.blossom.entitys.HumSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HumSensorRepository extends JpaRepository<HumSensor, Long> {
}

package com.ies.blossom.repositorys;

import com.ies.blossom.entitys.PhSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhSensorRepository extends JpaRepository<PhSensor, Long> {
}

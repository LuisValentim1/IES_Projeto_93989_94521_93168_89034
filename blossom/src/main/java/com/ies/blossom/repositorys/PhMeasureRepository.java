package com.ies.blossom.repositorys;

import com.ies.blossom.entitys.PhMeasure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhMeasureRepository extends JpaRepository<PhMeasure, Long> {
}

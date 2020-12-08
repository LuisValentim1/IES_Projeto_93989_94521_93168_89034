package com.ies.blossom.repositorys;

import com.ies.blossom.entitys.HumMeasure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HumMeasureRepository extends JpaRepository<HumMeasure, Long> {
}

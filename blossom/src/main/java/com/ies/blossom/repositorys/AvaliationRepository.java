package com.ies.blossom.repositorys;

import com.ies.blossom.entitys.Avaliation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliationRepository extends JpaRepository<Avaliation, Long>{
    
}

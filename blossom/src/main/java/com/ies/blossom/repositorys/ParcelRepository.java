package com.ies.blossom.repositorys;

import com.ies.blossom.entitys.Parcel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long> {

    //@Query("select p from parcels p where p.owner_id = ?1")	
    //List<Parcel> findByUser(long user_id);
	
}

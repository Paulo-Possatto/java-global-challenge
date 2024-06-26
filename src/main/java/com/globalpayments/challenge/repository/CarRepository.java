package com.globalpayments.challenge.repository;

import com.globalpayments.challenge.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarRepository extends JpaRepository<CarEntity, Long>{

    @Query("SELECT COUNT(c)>0 FROM CarEntity c WHERE c.carName = :carName")
    public boolean isCarExisting(
            @Param("carName") String carName
    );
}

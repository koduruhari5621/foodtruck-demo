package com.example.foodtruck.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.foodtruck.entity.FoodTruckEntity;

@Repository
public interface FoodTruckrepository extends MongoRepository<FoodTruckEntity, String> {

	List<FoodTruckEntity> findByApplicant(String applicant);

	List<FoodTruckEntity> findByStatus(String status);

	List<FoodTruckEntity> findByFacilityType(String facilityType);
	
	FoodTruckEntity findByLocationId(String locationId);
	

}

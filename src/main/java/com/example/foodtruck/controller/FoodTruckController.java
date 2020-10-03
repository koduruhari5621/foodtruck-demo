package com.example.foodtruck.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.foodtruck.entity.FoodTruckEntity;
import com.example.foodtruck.exception.FoodTruckInfoException;
import com.example.foodtruck.repository.FoodTruckrepository;

@RestController
@RequestMapping("/api/v1")
public class FoodTruckController {

	/*
	 * Since No businessLogic is Involved i ignored Service Layer which is
	 * standard otherwise i would have written Service layer as well
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FoodTruckController.class);

	@Autowired
	FoodTruckrepository foodTruckrepository;

	/*
	 * This Method will Insert the Truck Details
	 */
	@PostMapping("/save/truck")
	public FoodTruckEntity saveTruckData(@RequestBody FoodTruckEntity request) {

		return foodTruckrepository.save(request);

	}

	/*
	 * This Method will Update the Truck Details as locationId is unique key
	 */
	@PutMapping("/update/truck")
	public ResponseEntity<?> updateTruckData(@RequestBody FoodTruckEntity request) {

		FoodTruckEntity foodtruckResponseById = new FoodTruckEntity();
		try {
			foodtruckResponseById = foodTruckrepository.findByLocationId(request.getLocationId());
		} catch (Exception e) {
			throw new FoodTruckInfoException("Exception occured due to :" + e.getLocalizedMessage());
		}
		if (foodtruckResponseById != null) {
			FoodTruckEntity response = foodTruckrepository.save(request);
			return new ResponseEntity<FoodTruckEntity>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<FoodTruckEntity>(request, HttpStatus.BAD_REQUEST);

		}
	}
	/*
	 * Generic Method which will accept the filterType as
	 * applicant/facilityType/status and returns the List of Data.. This Method
	 * Reduces the code duplicacy which is repeated in next 3 methods
	 */

	@GetMapping("/getTruckDataByType")
	public ResponseEntity<?> getTruckDataByType(@RequestHeader Map<String, String> headers) {
		List<FoodTruckEntity> truckDataByType = new ArrayList<FoodTruckEntity>();
		try {
			if (headers.get("filtertype").equalsIgnoreCase("applicant")) {
				truckDataByType = foodTruckrepository.findByApplicant(headers.get("filtertypevalue"));
			} else if (headers.get("filtertype").equalsIgnoreCase("facilityType")) {

				truckDataByType = foodTruckrepository.findByFacilityType(headers.get("filtertypevalue"));
			} else if (headers.get("filtertype").equalsIgnoreCase("status")) {

				truckDataByType = foodTruckrepository.findByStatus(headers.get("filtertypevalue"));
			}

		} catch (Exception e) {
			throw new FoodTruckInfoException("Exception occured due to :" + e.getLocalizedMessage());
		}
		if (!(truckDataByType.isEmpty())) {
			return new ResponseEntity<List<FoodTruckEntity>>(truckDataByType, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<FoodTruckEntity>>(truckDataByType, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/getAllTruckDetails")
	public ResponseEntity<?> getAllTrucksData() {
		List<FoodTruckEntity> allTruckDetails = new ArrayList<FoodTruckEntity>();
		try {
			allTruckDetails = foodTruckrepository.findAll();
		} catch (Exception e) {
			throw new FoodTruckInfoException("Exception occured due to :" + e.getLocalizedMessage());
		}
		return new ResponseEntity<List<FoodTruckEntity>>(allTruckDetails, HttpStatus.OK);
	}
	/*
	 * This Method takes applicant as input and returns the List of TruckDetails
	 * Matched for that applicant
	 */

	@GetMapping("/getTruckDataByApplicant/{applicant}")
	public ResponseEntity<?> getTruckDataByApplicant(@PathVariable String applicant) {
		List<FoodTruckEntity> truckDataByApplicantType = new ArrayList<FoodTruckEntity>();
		try {
			truckDataByApplicantType = foodTruckrepository.findByApplicant(applicant);
		} catch (Exception e) {
			throw new FoodTruckInfoException("Exception occured due to :" + e.getLocalizedMessage());
		}
		if (!(truckDataByApplicantType.isEmpty())) {
			return new ResponseEntity<List<FoodTruckEntity>>(truckDataByApplicantType, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<FoodTruckEntity>>(truckDataByApplicantType, HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * This Method takes status as input and returns the List of TruckDetails
	 * Matched for that status
	 */
	@GetMapping("/getTruckDataByStatus/{status}")
	public ResponseEntity<?> getTruckDataByStatus(@PathVariable String status) {
		List<FoodTruckEntity> truckDataByStatusType = new ArrayList<FoodTruckEntity>();
		try {
			truckDataByStatusType = foodTruckrepository.findByStatus(status);
		} catch (Exception e) {
			throw new FoodTruckInfoException("Exception occured due to :" + e.getLocalizedMessage());
		}
		if (!(truckDataByStatusType.isEmpty())) {
			return new ResponseEntity<List<FoodTruckEntity>>(truckDataByStatusType, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<FoodTruckEntity>>(truckDataByStatusType, HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * This Method takes facilityType as input and returns the List of TruckDetails
	 * Matched for that facilityType
	 */
	@GetMapping("/getTruckDataByFacilityType/{facilityType}")
	public ResponseEntity<?> getTruckDataByFacilityType(@RequestHeader String facilityType) {
		List<FoodTruckEntity> truckDataByFacilityType = new ArrayList<FoodTruckEntity>();
		try {
			truckDataByFacilityType = foodTruckrepository.findByFacilityType(facilityType);
		} catch (Exception e) {
			throw new FoodTruckInfoException("Exception occured due to :" + e.getLocalizedMessage());
		}
		if (!(truckDataByFacilityType.isEmpty())) {
			return new ResponseEntity<List<FoodTruckEntity>>(truckDataByFacilityType, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<FoodTruckEntity>>(truckDataByFacilityType, HttpStatus.NOT_FOUND);
		}

	}

	/*
	 * This Method Deletes the entity by locationId
	 */
	@DeleteMapping("/deleteTruckDataById/{locationId}")
	public String deleteTruckDataById(@PathVariable String locationId) {
		Optional<FoodTruckEntity> foodTruckEntity = null;
		try {
			foodTruckEntity = foodTruckrepository.findById(locationId);
			if (foodTruckEntity.isPresent()) {
				foodTruckrepository.deleteById(locationId);
			}

		} catch (Exception e) {
			throw new FoodTruckInfoException("Exception occured due to :" + e.getLocalizedMessage());
		}
		return "Deleted Truck Entity Successfully";
	}

	/*
	 * This Method Deletes the entity
	 */
	@DeleteMapping("/deleteFoodtruck")
	public String deleteFoodTruck(@RequestBody FoodTruckEntity foodTruckEntity) {
		try {

			foodTruckrepository.delete(foodTruckEntity);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "Deleted Truck Entity Successfully";
	}

	@GetMapping("/getAllExpiredLicenseTrucks")
	public ResponseEntity<?> getAllTrucksWithExpiredLicenses() {
		List<FoodTruckEntity> foodTruckEntity = null;
		try {
			foodTruckEntity = foodTruckrepository.findByStatus("EXPIRED");
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (foodTruckEntity != null && !(foodTruckEntity.isEmpty())) {
			return new ResponseEntity<List<FoodTruckEntity>>(foodTruckEntity, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<FoodTruckEntity>>(foodTruckEntity, HttpStatus.NOT_FOUND);
		}

	}

}

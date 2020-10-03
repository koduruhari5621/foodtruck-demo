package com.example.foodtruck.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "foodtruck")
@Getter
@Setter
//@Entity
public class FoodTruckEntity {

	@Id
	private String locationId;
	private String applicant;
	private String facilityType;
	private String cnn;
	private String locationDescription;
	private String address;
	private String blockLot;
	private String block;
	private String lot;
	private String permit;
	private String status;
	private String foodItems;

}

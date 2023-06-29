package com.devnatao.parking.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devnatao.parking.dto.ParkingSpotDTO;
import com.devnatao.parking.model.ParkingSpotModel;
import com.devnatao.parking.services.ParkingSpotService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/parking-spot")
@CrossOrigin(origins = "", maxAge = 3600)
public class ParkingSpotController {

	@Autowired
	private ParkingSpotService service;
	
	@PostMapping
	public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDTO data) {
		if (service.existsByLicensePlateCar(data.getLicensePlateCar())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("License plate car already in use!");
		}
		
		if (service.existsByApartmentAndBlock(data.getApartment(), data.getBlock())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Parking spot already registered for this apartment or block");
		}
		
		if (service.existsByParkingSpotNumber(data.getParkingSpotNumber())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Parking spot number already in use!");
		}
		
		var entity = new ParkingSpotModel();
		BeanUtils.copyProperties(data, entity);
		entity.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(entity));
	}
	
	@GetMapping
	public ResponseEntity<Page<ParkingSpotModel>> getAllParkingSpots(@PageableDefault(page = 0, size = 10, sort = "id", 
					direction = Sort.Direction.ASC) Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(service.findAll(pageable));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ParkingSpotModel> getParkingSpotById(@PathVariable UUID id) {
		Optional<ParkingSpotModel> optionalParkingSpot = service.findById(id);
		
		if (!optionalParkingSpot.isPresent()) 
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		
		return ResponseEntity.status(HttpStatus.OK).body(optionalParkingSpot.get());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteParkingSpot(@PathVariable UUID id) {
		service.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateParkingSpot(@PathVariable UUID id, @RequestBody ParkingSpotDTO data) {
		Optional<ParkingSpotModel> optionalParkingSpotModel = service.findById(id);
		
		if (!optionalParkingSpotModel.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found!");
		}
		
		var parkingSpotModel = new ParkingSpotModel();
		BeanUtils.copyProperties(data, parkingSpotModel);
		parkingSpotModel.setId(optionalParkingSpotModel.get().getId());
		parkingSpotModel.setRegistrationDate(optionalParkingSpotModel.get().getRegistrationDate());
		
		return ResponseEntity.status(HttpStatus.OK).body(service.save(parkingSpotModel));
	}
	
	
}

package com.devnatao.parking.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devnatao.parking.model.ParkingSpotModel;
import com.devnatao.parking.repository.ParkingSpotRepository;

@Service
public class ParkingSpotService {

	@Autowired
	private ParkingSpotRepository repository;
	
	@Transactional
	public ParkingSpotModel save(ParkingSpotModel data) {
		return repository.save(data);
	}
	
	@Transactional(readOnly = true)
	public Page<ParkingSpotModel> findAll(Pageable page) {
		return repository.findAll(page);
	}
	
	@Transactional(readOnly = true)
	public Optional<ParkingSpotModel> findById(UUID id) {
		return repository.findById(id);
	}
	
	@Transactional
	public void delete(UUID id) {	
		repository.deleteById(id);
	}
	
	public ParkingSpotModel update(ParkingSpotModel data) {
		return repository.save(data);
	}
	
	public boolean existsByLicensePlateCar(String licensePlateCar) {
		return repository.existsByLicensePlateCar(licensePlateCar);
	}
	
	public boolean existsByParkingSpotNumber(String spotNumber) { 
		return repository.existsByParkingSpotNumber(spotNumber);
	}
	
	public boolean existsByApartmentAndBlock(String apartment, String block) {
		return repository.existsByApartmentAndBlock(apartment, block);
	}
 }

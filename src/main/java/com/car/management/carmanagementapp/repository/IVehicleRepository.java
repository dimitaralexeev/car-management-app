package com.car.management.carmanagementapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.car.management.carmanagementapp.bean.VehicleBean;

@Repository
public interface IVehicleRepository extends JpaRepository<VehicleBean, Integer>{
	
	
}

package com.example.patient_records.repository;

import com.example.patient_records.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  PatientRepository extends JpaRepository<Patient,Long>
{

}





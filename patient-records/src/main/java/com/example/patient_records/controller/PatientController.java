package com.example.patient_records.controller;


import com.example.patient_records.entity.Patient;
import com.example.patient_records.repository.PatientRepository;
import com.example.patient_records.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*") // Allow CORS for frontend
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/search")
    public List<Patient> searchPatients(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone) {
        return patientService.searchPatients(name, email, phone);
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.createPatient(patient);
    }

    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable Long id, @RequestBody Patient updatedPatient) {
        return patientService.updatePatient(id, updatedPatient);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }
}
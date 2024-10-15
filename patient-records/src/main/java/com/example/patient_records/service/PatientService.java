package com.example.patient_records.service;

import com.example.patient_records.entity.Patient;
import com.example.patient_records.repository.PatientRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PatientRepository patientRepository;

    // Method to perform global search
    public List<Patient> searchPatients(String name, String email, String phoneNumber) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Patient> query = cb.createQuery(Patient.class);
        Root<Patient> patient = query.from(Patient.class);

        List<Predicate> predicates = new ArrayList<>();

        // Add predicates based on provided search criteria
        if (name != null && !name.trim().isEmpty()) {
            Predicate namePredicate = cb.like(cb.lower(patient.get("name")), "%" + name.toLowerCase() + "%");
            predicates.add(namePredicate);
        }

        if (email != null && !email.trim().isEmpty()) {
            Predicate emailPredicate = cb.like(cb.lower(patient.get("email")), "%" + email.toLowerCase() + "%");
            predicates.add(emailPredicate);
        }

        if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
            Predicate phonePredicate = cb.like(patient.get("phoneNumber"), "%" + phoneNumber + "%");
            predicates.add(phonePredicate);
        }

        query.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(query).getResultList();
    }

    // Other CRUD methods (create, update, delete)
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Long id, Patient updatedPatient) {
        return patientRepository.findById(id)
                .map(existingPatient -> {
                    existingPatient.setName(updatedPatient.getName());
                    existingPatient.setEmail(updatedPatient.getEmail());
                    existingPatient.setPhoneNumber(updatedPatient.getPhoneNumber());
                    return patientRepository.save(existingPatient);
                })
                .orElseThrow(() -> new RuntimeException("Patient not found with id " + id));
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient not found with id " + id));
    }
}
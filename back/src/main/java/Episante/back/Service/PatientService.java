package Episante.back.Service;

import Episante.back.Repository.IPatientDao;
import Episante.back.Models.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private IPatientDao patientDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Inject the password encoder

    public String registerPatient(Patient patient) {
        if (patientDao.findByIdentifier(patient.getIdentifier()).isPresent()) {
            return "Patient with this identifier already exists.";
        }

        // Hash the password before saving
        String hashedPassword = passwordEncoder.encode(patient.getPassword());
        patient.setPassword(hashedPassword);

        patient.setCreatedAt(Timestamp.from(Instant.now())); // Set creation timestamp
        patient.setUpdatedAt(Timestamp.from(Instant.now())); // Set update timestamp

        patientDao.save(patient);
        return "Patient registered successfully!";
    }

    public String loginPatient(String identifier, String password) {
        Optional<Patient> patientOptional = patientDao.findByIdentifier(identifier);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            if (passwordEncoder.matches(password, patient.getPassword())) {
                return "Login successful: " + patient.getNom() + " " + patient.getPrenom();
            } else {
                return "Invalid password.";
            }
        } else {
            return "Patient with this identifier not found.";
        }
    }
}

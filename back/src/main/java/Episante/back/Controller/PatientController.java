package Episante.back.Controller;

import Episante.back.Repository.IPatientDao;
import Episante.back.Models.Patient;
import Episante.back.Service.PatientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService patientService;


    @Autowired
    private IPatientDao patientDao;


    // let's start with crud
    @GetMapping
    public List<Patient> getAllPatients() {
        logger.info("Get all patients");
        return patientDao.findAll();
    }


    @PostMapping
    public ResponseEntity<Patient> createPatient(@Valid @RequestBody Patient patient) {
        Patient savedPatient = patientDao.save(patient);
        logger.info("Patient created successfully: {}", savedPatient.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPatient);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id , @Valid @RequestBody Patient patientDetails) {
        Patient patient = patientDao.findById(id).orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
        BeanUtils.copyProperties(patientDetails, patient); // with copyProperties method you just copy the propreties of your Patient to avoid setters
        Patient updatedPatient = patientDao.save(patient);
        logger.info("Patient updated successfully: {}", updatedPatient.getId());
        return ResponseEntity.status(HttpStatus.OK).body(updatedPatient);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Patient> deletePatient(@PathVariable Long id) {
        if(!patientDao.existsById(id)) {
            logger.error("Patient not found with id: " + id);
        }
        patientDao.deleteById(id);
        logger.info("Patient deleted successfully: {}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Register and LogIn
    @PostMapping("/register")
    public ResponseEntity<?> registerPatient(@RequestBody Patient patient) {
        try {
            String message = patientService.registerPatient(patient);
            if (message.startsWith("Patient registered successfully")) {
                return ResponseEntity.ok(Map.of("message", message, "redirectToLogin", true)); // Indicate redirection
            } else {
                return ResponseEntity.badRequest().body(Map.of("message", message));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginPatient(@RequestBody Map<String, String> credentials) {
        try {
            String identifier = credentials.get("identifier");
            String password = credentials.get("password");
            String loginMessage = patientService.loginPatient(identifier, password);
            if (loginMessage.startsWith("Login successful")) {
                // Extract patient name (you might need to adjust this based on your login message)
                String patientName = loginMessage.substring(loginMessage.indexOf(":") + 1).trim();
                return ResponseEntity.ok(Map.of("message", "Login successful", "patientName", patientName));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", loginMessage));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "An error occurred during login."));
        }
    }

}

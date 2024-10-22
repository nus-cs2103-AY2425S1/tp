package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Patient in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient extends Person {
    /**
     * Static list to store all Patient instances.
     */
    private static ArrayList<Patient> patients = new ArrayList<>();

    /**
     * Constructs a Patient object with the specified details.
     * Every field must be present and not null.
     *
     * @param name The name of the patient.
     * @param role The role of the patient.
     * @param phone The phone number of the patient.
     * @param email The email address of the patient.
     * @param address The residential address of the patient.
     * @param remark Additional remarks about the patient.
     * @param tags Tags associated with the patient (e.g., chronic conditions).
     */
    public Patient(Name name, String role, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags) {
        super(name, role, phone, email, address, remark, tags);
        Patient.patients.add(this); // Add this patient to the static list of all patients
    }

    /**
     * Retrieves the list of all patients.
     *
     * @return An ArrayList containing all registered patients.
     */
    public static ArrayList<Patient> getPatients() {
        return patients;
    }

    /**
     * Retrieves a patient by their Id object.
     *
     * @param patientId The Id object of the patient to search for.
     * @return The Patient object if found, or null if no matching patient is found.
     */

    public static Patient getPatientWithId(Id patientId) {
        ArrayList<Patient> allPatients = getPatients();
        for (Patient patient : allPatients) {
            if (patient.getId().equals(patientId)) {
                return patient;
            }
        }
        return null;
    }

    /**
     * Retrieves a patient by their ID as a String.
     *
     * @param id The ID of the patient as a String.
     * @return The Patient object if found, or null if no matching patient is found.
     */
    public static Patient getPatientWithId(String id) {
        ArrayList<Patient> allPatients = getPatients();
        System.out.println(allPatients);
        for (Patient patient : allPatients) {
            if (String.valueOf(patient.getId().getIdValue()).equals(id)) {
                return patient;
            }
        }
        return null; // Return null if no matching patient is found
    }

    /**
     * adds a patient to the collection of patients
     *
     * @param patient The patient to be added
     */
    public static void addPatient(Patient patient) {
        patients.add(patient);
    }
}

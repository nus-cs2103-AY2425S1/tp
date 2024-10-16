package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Patient in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient extends Person {
    private static ArrayList<Patient> patients = new ArrayList<>();

    /**
     * Every field must be present and not null.
     *
     * @param name
     * @param phone
     * @param email
     * @param address
     * @param remark
     * @param tags
     */
    public Patient(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags) {
        super(name, phone, email, address, remark, tags);
        Patient.patients.add(this);
    }

    public static ArrayList<Patient> getPatients() {
        return patients;
    }

    /**
     * Retrieves a patient from the list of all patients with a matching ID in String format.
     *
     * @param id The ID of the patient as a String.
     * @return The Patient object if a patient with the given ID exists, or null if no such patient is found.
     */
    public static Patient getPatientWithId(String id) {
        ArrayList<Patient> allPatients = getPatients();
        for (Patient patient : allPatients) {
            if (String.valueOf(patient.getId().getIdValue()).equals(id)) {
                return patient;
            }
        }
        return null;
    }

    /**
     * Retrieves a patient from the list of all patients with a matching ID.
     *
     * @param id The ID of the patient as an Id object.
     * @return The Patient object if a patient with the given ID exists, or null if no such patient is found.
     */
    public static Patient getPatientWithId(Id id) {
        ArrayList<Patient> allPatients = getPatients();
        for (Patient patient : allPatients) {
            if (patient.getId().equals(id)) {
                return patient;
            }
        }
        return null;
    }

}

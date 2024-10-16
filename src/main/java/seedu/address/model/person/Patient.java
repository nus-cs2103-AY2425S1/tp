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

    public static Patient getPatientWithId(Id patientId) {
        ArrayList<Patient> allPatients = getPatients();
        for (Patient patient : allPatients) {
            if (patient.getId().equals(patientId)) {
                return patient;
            }
        }
        return null;
    }

}

package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Encapsulates the code required to represent a target user (doctor) of the application.
 */
public class Doctor extends Person {
    private static ArrayList<Doctor> doctors = new ArrayList<>();

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
    public Doctor(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags) {
        super(name, phone, email, address, remark, tags);
        Doctor.doctors.add(this);
    }

    public static ArrayList<Doctor> getDoctors() {
        return Doctor.doctors;
    }

    /**
     * Retrieves a doctor by their Id object.
     *
     * @param id The Id object of the doctor to search for.
     * @return The Doctor object if found, or null if no matching doctor is found.
     */
    public static Doctor getDoctorWithId(Id id) {
        ArrayList<Doctor> allDoctors = getDoctors();
        for (Doctor doctor : allDoctors) {
            if (doctor.getId().equals(id)) {
                return doctor;
            }
        }
        return null;
    }

    /**
     * Retrieves a doctor by their Id value.
     *
     * @param id The String idValue of the doctor to search for.
     * @return The Doctor object if found, or null if no matching doctor is found.
     */
    public static Doctor getDoctorWithId(String id) {
        ArrayList<Doctor> allDoctors = getDoctors();
        for (Doctor doctor : allDoctors) {
            if (String.valueOf(doctor.getId().getIdValue()).equals(id)) {
                return doctor;
            }
        }
        return null;
    }
}

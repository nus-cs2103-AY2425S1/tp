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

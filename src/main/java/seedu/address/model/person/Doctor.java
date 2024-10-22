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
     * @param name name of the doctor
     * @param role The role of the doctor
     * @param phone The phone number of the doctor
     * @param email The email address of the doctor
     * @param address name of the doctor
     * @param remark name of the doctor
     * @param tags name of the doctor
     */
    public Doctor(Name name, String role, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags) {
        super(name, role, phone, email, address, remark, tags);
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

    /**
     * adds a doctor to the collection of doctors
     *
     * @param doctor The doctor to be added
     */
    public static void addDoctors(Doctor doctor) {
        doctors.add(doctor);
    }
}

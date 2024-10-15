package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Id class that auto-increments and generates an automated unique Id number for Doctors and Patients separately.
 */
public class Id {
    private static int patientIdCounter = 0;
    private static int doctorIdCounter = 0;
    private int id;
    private Class<? extends Person> role;

    /**
     * Creates an Id that is associated with a specific class.
     *
     * @param personClass Either Doctor or Patient class.
     */
    public Id(Class<? extends Person> personClass) {
        requireNonNull(personClass);
        this.role = personClass;

        // Check if the class is Patient or Doctor and assign the appropriate ID
        if (personClass.equals(Patient.class)) {
            id = ++patientIdCounter;
        } else if (personClass.equals(Doctor.class)) {
            id = ++doctorIdCounter;
        } else {
            throw new IllegalArgumentException("Invalid class type. Expected Patient or Doctor.");
        }
    }

    public int getId() {
        return id;
    }

    public Class<? extends Person> getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "Id{" + "id=" + id + ", role=" + role.getSimpleName() + '}';
    }
}

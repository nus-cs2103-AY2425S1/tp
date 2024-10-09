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

    // Constructor that takes in a class type (Doctor or Patient)
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

    // Getter for the ID
    public int getId() {
        return id;
    }

    // Getter for the role (class type)
    public Class<? extends Person> getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "Id{" +
                "id=" + id +
                ", role=" + role.getSimpleName() +
                '}';
    }
}

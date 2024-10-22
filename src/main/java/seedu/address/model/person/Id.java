package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Id class that auto-increments and generates an automated unique Id number for Doctors and Patients separately.
 */
public class Id {
    private static int patientIdCounter = 0;
    private static int doctorIdCounter = 1000;
    private static int personIdCounter = 0; // TODO REMOVE AFTER V1.3
    protected int idValue;

    /**
     * Creates an Id that is associated with a specific class.
     *
     * @param personClass Either Doctor or Patient class.
     */
    public Id(Class<? extends Person> personClass) {
        requireNonNull(personClass);

        // Check if the class is Patient or Doctor and assign the appropriate ID
        if (personClass.equals(Patient.class)) {
            idValue = ++patientIdCounter;
        } else if (personClass.equals(Doctor.class)) {
            idValue = ++doctorIdCounter;
        } else if (personClass.equals(Person.class)) { // TODO AFTER INTEGRATION
            idValue = ++personIdCounter;
        } else {
            throw new IllegalArgumentException("Invalid class type. Expected Patient or Doctor.");
        }
    }

    public int getIdValue() {
        return idValue;
    }

    @Override
    public String toString() {
        return "Id{" + "id=" + idValue + '}';
    }
}

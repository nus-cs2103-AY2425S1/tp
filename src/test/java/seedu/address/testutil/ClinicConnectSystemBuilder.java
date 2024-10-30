package seedu.address.testutil;

import seedu.address.model.ClinicConnectSystem;
import seedu.address.model.patient.Patient;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code ClinicConnectSystem ab = new ClinicConnectSystemBuilder().withPatient("John", "Doe").build();}
 */
public class ClinicConnectSystemBuilder {

    private ClinicConnectSystem clinicConnectSystem;

    public ClinicConnectSystemBuilder() {
        clinicConnectSystem = new ClinicConnectSystem();
    }

    public ClinicConnectSystemBuilder(ClinicConnectSystem clinicConnectSystem) {
        this.clinicConnectSystem = clinicConnectSystem;
    }

    /**
     * Adds a new {@code Patient} to the {@code ClinicConnectSystem} that we are building.
     */
    public ClinicConnectSystemBuilder withPatient(Patient patient) {
        clinicConnectSystem.addPatient(patient);
        return this;
    }

    public ClinicConnectSystem build() {
        return clinicConnectSystem;
    }
}

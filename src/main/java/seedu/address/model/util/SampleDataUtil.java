package seedu.address.model.util;

import seedu.address.model.ClinicConnectSystem;
import seedu.address.model.ReadOnlyClinicConnectSystem;
import seedu.address.model.patient.Birthdate;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.Sex;
/**
 * Contains utility methods for populating {@code ClinicConnectSystem} with sample data.
 */
public class SampleDataUtil {
    public static Patient[] getSamplePatients() {
        return new Patient[] {
            new Patient(new Name("Alex Yeoh"), new Nric("T0123456A"), new Birthdate("2001-05-30"),
                    new Sex("M"), new Phone("91234567")),
            new Patient(new Name("Bernice Yu"), new Nric("S0123456B"), new Birthdate("1965-08-09"),
                new Sex("F"), new Phone("91234568")),
            new Patient(new Name("Charlotte Oliveiro"), new Nric("M1234457A"), new Birthdate("1990-12-20"),
                new Sex("F"), new Phone("91234569"))
        };
    }

    public static ReadOnlyClinicConnectSystem getSampleClinicConnectSystem() {
        ClinicConnectSystem sampleAb = new ClinicConnectSystem();
        for (Patient samplePatient : getSamplePatients()) {
            sampleAb.addPatient(samplePatient);
        }
        return sampleAb;
    }

}

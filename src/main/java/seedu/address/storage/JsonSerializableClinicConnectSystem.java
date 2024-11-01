package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ClinicConnectSystem;
import seedu.address.model.ReadOnlyClinicConnectSystem;
import seedu.address.model.patient.Patient;

/**
 * An Immutable ClinicConnectSystem that is serializable to JSON format.
 */
@JsonRootName(value = "clinicconnectsystem")
class JsonSerializableClinicConnectSystem {

    public static final String MESSAGE_DUPLICATE_PATIENT = "Patients list contains duplicate patient(s).";

    private final List<JsonAdaptedPatient> patients = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableClinicConnectSystem} with the given patients.
     */
    @JsonCreator
    public JsonSerializableClinicConnectSystem(@JsonProperty("patients") List<JsonAdaptedPatient> patients) {
        this.patients.addAll(patients);
    }

    /**
     * Converts a given {@code ReadOnlyClinicConnectSystem} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableClinicConnectSystem}.
     */
    public JsonSerializableClinicConnectSystem(ReadOnlyClinicConnectSystem source) {
        patients.addAll(source.getPatientList().stream().map(JsonAdaptedPatient::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code ClinicConnectSystem} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ClinicConnectSystem toModelType() throws IllegalValueException {
        ClinicConnectSystem clinicConnectSystem = new ClinicConnectSystem();
        for (JsonAdaptedPatient jsonAdaptedPatient : patients) {
            Patient patient = jsonAdaptedPatient.toModelType();
            if (clinicConnectSystem.hasPatient(patient)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PATIENT);
            }
            clinicConnectSystem.addPatient(patient);
        }
        return clinicConnectSystem;
    }

}

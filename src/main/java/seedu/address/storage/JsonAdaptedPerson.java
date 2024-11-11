package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Diagnosis;
import seedu.address.model.person.Id;
import seedu.address.model.person.Medication;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.person.Ward;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";
    private final String name;
    private final String id;
    private final String ward;
    private final String diagnosis;
    private final String medication;
    private final String notes;
    private final JsonAdaptedAppointment appointment;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("id") String id,
                             @JsonProperty("ward") String ward, @JsonProperty("diagnosis") String diagnosis,
                             @JsonProperty("medication") String medication, @JsonProperty("notes") String notes,
                             @JsonProperty("appointment") JsonAdaptedAppointment appointment) {
        this.name = name;
        this.id = id;
        this.ward = ward;
        this.diagnosis = diagnosis;
        this.medication = medication;
        this.notes = notes;
        this.appointment = appointment;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().value;
        id = source.getId().value;
        ward = source.getWard().value;
        diagnosis = source.getDiagnosis().value;
        medication = source.getMedication().value;
        notes = source.getNotes().value;
        appointment = source.getAppointment() != null ? new JsonAdaptedAppointment(source.getAppointment()) : null;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Id.class.getSimpleName()));
        }
        if (!Id.isValidId(id)) {
            throw new IllegalValueException(Id.MESSAGE_CONSTRAINTS);
        }
        final Id modelId = new Id(id);

        if (ward == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Ward.class.getSimpleName()));
        }
        if (!Ward.isValidWard(ward)) {
            throw new IllegalValueException(Ward.MESSAGE_CONSTRAINTS);
        }
        final Ward modelWard = new Ward(ward);

        if (diagnosis == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Diagnosis.class.getSimpleName()));
        }
        if (!Diagnosis.isValidDiagnosis(diagnosis)) {
            throw new IllegalValueException(Diagnosis.MESSAGE_CONSTRAINTS);
        }
        final Diagnosis modelDiagnosis = new Diagnosis(diagnosis);

        if (medication == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Medication.class.getSimpleName()));
        }
        if (!Medication.isValidMedication(medication)) {
            throw new IllegalValueException(Medication.MESSAGE_CONSTRAINTS);
        }
        final Medication modelMedication = new Medication(medication);

        if (notes == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Notes.class.getSimpleName()));
        }
        if (!Notes.isValidNote(notes)) {
            throw new IllegalValueException(Notes.MESSAGE_CONSTRAINTS);
        }
        final Notes modelNotes = new Notes(notes);

        final Appointment modelAppointment = (appointment != null) ? appointment.toModelType() : null;

        return new Person(modelName, modelId, modelWard, modelDiagnosis, modelMedication, modelNotes, modelAppointment);
    }

}

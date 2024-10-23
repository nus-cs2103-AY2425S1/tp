package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

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
import seedu.address.model.tag.Tag;

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
    /*
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

     */

    /*
    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    /*
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }
     */

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
        /*
        this.diagnosis = diagnosis;
        this.medication = medication;
        if (tags != null) {
            this.tags.addAll(tags);
        }

         */
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
        /*
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));

         */
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        /*
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }
         */

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
        final Notes modelNotes = new Notes(notes);

        final Appointment modelAppointment = (appointment != null) ? appointment.toModelType() : null;



        /*

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

         */

        /*
        final Medication modelMedication = new Medication(medication);
        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTags);
         */
        return new Person(modelName, modelId, modelWard, modelDiagnosis, modelMedication, modelNotes, modelAppointment);
    }

}

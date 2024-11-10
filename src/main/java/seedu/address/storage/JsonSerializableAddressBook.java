package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_APPOINTMENTS = "Appointments list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    private final List<JsonAdaptedAppointment> appointments = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons, patients and doctors
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("appointments") List<JsonAdaptedAppointment> appointments) {
        this.persons.addAll(persons);
        this.appointments.addAll(appointments);
    }


    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        appointments.addAll(source.getAppointmentList().stream().map(JsonAdaptedAppointment::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        List<Doctor> doctorList = new ArrayList<>();
        List<Patient> patientList = new ArrayList<>();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();

            if (person instanceof Doctor) {
                Doctor doctor = (Doctor) person;
                if (addressBook.hasDoctor(doctor)) {
                    throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
                }
                doctorList.add(doctor);
                addressBook.addDoctor(doctor);
            }

            if (person instanceof Patient) {
                Patient patient = (Patient) person;
                if (addressBook.hasPatient(patient)) {
                    throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
                }
                patientList.add(patient);
                addressBook.addPatient(patient);
            }
        }

        for (JsonAdaptedAppointment jsonAdaptedAppointment : appointments) {
            Appointment appointment = jsonAdaptedAppointment.toModelType(doctorList, patientList);
            if (addressBook.hasAppointment(appointment)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_APPOINTMENTS);
            }
            addressBook.addAppointment(appointment);
        }
        return addressBook;
    }
}

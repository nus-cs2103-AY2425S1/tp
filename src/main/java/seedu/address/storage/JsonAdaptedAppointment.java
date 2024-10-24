package seedu.address.storage;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Time;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.shared.Date;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
class JsonAdaptedAppointment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";

    private final Integer id;
    private final String doctorName;
    private final String patientName;
    private final String date;
    private final String time;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedAppointment(@JsonProperty("id") Integer id, @JsonProperty("doctor") String doctor,
                                   @JsonProperty("patient") String patient, @JsonProperty("date") String date,
                                   @JsonProperty("time") String time) {
        this.id = id;
        this.doctorName = doctor;
        this.patientName = patient;
        this.date = date;
        this.time = time;
    }

    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        id = source.getId();
        doctorName = source.getDoctorName();
        patientName = source.getPatientName();
        date = source.getDate().toString();
        time = source.getTime().toString();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Appointment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Appointment.
     */
    public Appointment toModelType(List<Doctor> allDoctors, List<Patient> allPatients) throws IllegalValueException {

        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Integer.class.getSimpleName()));
        }

        Integer modelId = id;

        if (doctorName == null) {
            throw new IllegalValueException("Doctor name is missing");
        }

        Doctor modelDoctor = allDoctors.stream()
                .filter(d -> d.getName().fullName.equals(doctorName))
                .findFirst()
                .orElseThrow(() -> new IllegalValueException("Doctor not found"));

        if (patientName == null) {
            throw new IllegalValueException("Patient name is missing");
        }

        Patient modelPatient = allPatients.stream()
                .filter(p -> p.getName().fullName.equals(patientName))
                .findFirst()
                .orElseThrow(() -> new IllegalValueException("Patient not found"));

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(time)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        final Time modelTime = new Time(time);

        return new Appointment(modelId, modelDoctor, modelPatient, modelDate, modelTime);
    }

}

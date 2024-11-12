package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppointments.APPOINTMENT_1;
import static seedu.address.testutil.TypicalDoctors.ALICE;
import static seedu.address.testutil.TypicalPatients.AMY;
import static seedu.address.testutil.TypicalPatients.CARL;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Time;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.shared.Date;

public class JsonAdaptedAppointmentTest {
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_SPECIALITY = "Pediatrics";
    private static final String VALID_DATEOFBIRTH = "01-01-2000";
    private static final String VALID_GENDER = "F";
    private static final String VALID_DATE_STRING = "15-02-2024";
    private static final Date VALID_DATE = new Date("15-02-2024");
    private static final String VALID_TIME_STRING = "1130";
    private static final Time VALID_TIME = new Time("1130");
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());


    @Test
    public void toModelType_validAppointmentDetails_returnsAppointment() throws Exception {
        List<Doctor> doctorList = new ArrayList<>();
        List<Patient> patientList = new ArrayList<>();
        doctorList.add(ALICE);
        patientList.add(CARL);
        JsonAdaptedAppointment jsonAdaptedAppointment = new JsonAdaptedAppointment(1, "Alice Pauline",
                "Carl Kurz", VALID_DATE_STRING, VALID_TIME_STRING);
        Appointment appointment = new Appointment(1, ALICE, CARL, VALID_DATE, VALID_TIME);
        assertEquals(appointment, jsonAdaptedAppointment.toModelType(doctorList, patientList));
    }

    @Test
    public void constructor_validSource_returnsJsonAdaptedAppointment() throws Exception {
        List<Doctor> doctorList = new ArrayList<>();
        List<Patient> patientList = new ArrayList<>();
        doctorList.add(ALICE);
        patientList.add(AMY);
        JsonAdaptedAppointment jsonAdaptedAppointment = new JsonAdaptedAppointment(APPOINTMENT_1);
        assertEquals(APPOINTMENT_1, jsonAdaptedAppointment.toModelType(doctorList, patientList));
    }

    @Test
    public void toModelType_invalidDoctorName_throwsIllegalValueException() {
        List<Doctor> doctorList = new ArrayList<>();
        List<Patient> patientList = new ArrayList<>();
        doctorList.add(ALICE);
        patientList.add(CARL);
        JsonAdaptedAppointment jsonAdaptedAppointment = new JsonAdaptedAppointment(1, "Alice",
                "Carl Kurz", VALID_DATE_STRING, VALID_TIME_STRING);
        assertThrows(IllegalValueException.class, () -> jsonAdaptedAppointment.toModelType(doctorList, patientList));
    }

    @Test
    public void toModelType_invalidId_throwsIllegalValueException() {
        List<Doctor> doctorList = new ArrayList<>();
        List<Patient> patientList = new ArrayList<>();
        doctorList.add(ALICE);
        patientList.add(CARL);
        JsonAdaptedAppointment jsonAdaptedAppointment = new JsonAdaptedAppointment(null, "Alice Pauline",
                "Carl Kurz", VALID_DATE_STRING, VALID_TIME_STRING);
        assertThrows(IllegalValueException.class, () -> jsonAdaptedAppointment.toModelType(doctorList, patientList));
    }

    @Test
    public void toModelType_invalidPatientName_throwsIllegalValueException() {
        List<Doctor> doctorList = new ArrayList<>();
        List<Patient> patientList = new ArrayList<>();
        doctorList.add(ALICE);
        patientList.add(CARL);
        JsonAdaptedAppointment jsonAdaptedAppointment = new JsonAdaptedAppointment(1, "Alice Pauline",
                "Carl", VALID_DATE_STRING, VALID_TIME_STRING);
        assertThrows(IllegalValueException.class, () -> jsonAdaptedAppointment.toModelType(doctorList, patientList));
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        List<Doctor> doctorList = new ArrayList<>();
        List<Patient> patientList = new ArrayList<>();
        doctorList.add(ALICE);
        patientList.add(CARL);
        String invalidDate = "2024-15-02";
        JsonAdaptedAppointment jsonAdaptedAppointment = new JsonAdaptedAppointment(1, "Alice Pauline",
                "Carl Kurz", invalidDate, VALID_TIME_STRING);
        assertThrows(IllegalValueException.class, () -> jsonAdaptedAppointment.toModelType(doctorList, patientList));
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        List<Doctor> doctorList = new ArrayList<>();
        List<Patient> patientList = new ArrayList<>();
        doctorList.add(ALICE);
        patientList.add(CARL);
        String invalidTime = "25:00"; // Invalid time format
        JsonAdaptedAppointment jsonAdaptedAppointment = new JsonAdaptedAppointment(1, "Alice Pauline",
                "Carl Kurz", VALID_DATE_STRING, invalidTime);
        assertThrows(IllegalValueException.class, () -> jsonAdaptedAppointment.toModelType(doctorList, patientList));
    }

    @Test
    public void toModelType_nullTime_throwsIllegalValueException() {
        List<Doctor> doctorList = new ArrayList<>();
        List<Patient> patientList = new ArrayList<>();
        doctorList.add(ALICE);
        patientList.add(CARL);
        JsonAdaptedAppointment jsonAdaptedAppointment = new JsonAdaptedAppointment(1, "Alice Pauline",
                "Carl Kurz", VALID_DATE_STRING, null);
        assertThrows(IllegalValueException.class, () -> jsonAdaptedAppointment.toModelType(doctorList, patientList));
    }


    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        List<Doctor> doctorList = new ArrayList<>();
        List<Patient> patientList = new ArrayList<>();
        doctorList.add(ALICE);
        patientList.add(CARL);
        JsonAdaptedAppointment jsonAdaptedAppointment = new JsonAdaptedAppointment(1, "Alice Pauline",
                "Carl Kurz", null, VALID_TIME_STRING);
        assertThrows(IllegalValueException.class, () -> jsonAdaptedAppointment.toModelType(doctorList, patientList));
    }



    @Test
    public void toModelType_nullDoctor_throwsIllegalValueException() {
        List<Doctor> doctorList = new ArrayList<>();
        List<Patient> patientList = new ArrayList<>();
        doctorList.add(ALICE);
        patientList.add(CARL);
        JsonAdaptedAppointment jsonAdaptedAppointment = new JsonAdaptedAppointment(1, null,
                "Carl Kurz", VALID_DATE_STRING, VALID_TIME_STRING);
        assertThrows(IllegalValueException.class, () -> jsonAdaptedAppointment.toModelType(doctorList, patientList));
    }

    @Test
    public void toModelType_nullPatient_throwsIllegalValueException() {
        List<Doctor> doctorList = new ArrayList<>();
        List<Patient> patientList = new ArrayList<>();
        doctorList.add(ALICE);
        patientList.add(CARL);
        JsonAdaptedAppointment jsonAdaptedAppointment = new JsonAdaptedAppointment(1, "Alice Pauline",
                null, VALID_DATE_STRING, VALID_TIME_STRING);
        assertThrows(IllegalValueException.class, () -> jsonAdaptedAppointment.toModelType(doctorList, patientList));
    }


    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_SPECIALITY, VALID_DATEOFBIRTH, VALID_GENDER, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}

package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_TYPE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_TYPE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICINE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICINE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SICKNESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SICKNESS_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AppointmentBook;
import seedu.address.model.appointment.Appointment;

/**
 * A utility class containing a list of {@code Appointment} objects to be used in tests.
 */
public class TypicalAppointments {

    public static final Appointment APPOINTMENT_1 = new AppointmentBuilder().withAppointmentType("Health Checkup")
            .withPersonId(1).withSickness("Flu").withMedicine("Panadol").build();
    public static final Appointment APPOINTMENT_2 = new AppointmentBuilder().withAppointmentType("Dental Checkup")
            .withPersonId(2).withSickness("Toothache").withMedicine("Ibuprofen").build();
    public static final Appointment APPOINTMENT_3 = new AppointmentBuilder().withAppointmentType("Eye Exam")
            .withPersonId(3).withSickness("Eye Strain").withMedicine("Eye Drops").build();
    public static final Appointment APPOINTMENT_4 = new AppointmentBuilder().withAppointmentType("Physical Therapy")
            .withPersonId(4).withSickness("Back Pain").withMedicine("Paracetamol").build();
    public static final Appointment APPOINTMENT_5 = new AppointmentBuilder().withAppointmentType("Vaccination")
            .withPersonId(5).withSickness("None").withMedicine("Vaccine").build();
    public static final Appointment APPOINTMENT_6 = new AppointmentBuilder().withAppointmentType("Routine Checkup")
            .withPersonId(6).withSickness("None").withMedicine("None").build();
    public static final Appointment APPOINTMENT_7 = new AppointmentBuilder().withAppointmentType("Consultation")
            .withPersonId(7).withSickness("Headache").withMedicine("Aspirin").build();

    // Manually added
    public static final Appointment APPOINTMENT_8 = new AppointmentBuilder().withAppointmentType("Emergency Room")
            .withPersonId(8).withSickness("Injury").withMedicine("Morphine").build();
    public static final Appointment APPOINTMENT_9 = new AppointmentBuilder().withAppointmentType("Surgery Consultation")
            .withPersonId(9).withSickness("Pre-Surgery").withMedicine("Pre-Meds").build();

    // Manually added - Appointment's details found in {@code CommandTestUtil}
    public static final Appointment APPOINTMENT_AMY = new AppointmentBuilder()
            .withAppointmentType(VALID_APPOINTMENT_TYPE_AMY)
            .withSickness(VALID_SICKNESS_AMY).withMedicine(VALID_MEDICINE_AMY).build();
    public static final Appointment APPOINTMENT_BOB = new AppointmentBuilder()
            .withAppointmentType(VALID_APPOINTMENT_TYPE_BOB)
            .withSickness(VALID_SICKNESS_BOB).withMedicine(VALID_MEDICINE_BOB).build();

    public static final String KEYWORD_MATCHING_CHECKUP = "Checkup"; // A keyword that matches Checkup

    private TypicalAppointments() {} // prevents instantiation

    /**
     * Returns an {@code AppointmentBook} with all the typical appointments.
     */
    public static AppointmentBook getTypicalAppointmentBook() {
        AppointmentBook ab = new AppointmentBook();
        for (Appointment appointment : getTypicalAppointments()) {
            ab.addAppointment(appointment);
        }
        return ab;
    }

    public static List<Appointment> getTypicalAppointments() {
        return new ArrayList<>(Arrays.asList(
                APPOINTMENT_1,
                APPOINTMENT_2,
                APPOINTMENT_3,
                APPOINTMENT_4,
                APPOINTMENT_5,
                APPOINTMENT_6,
                APPOINTMENT_7));
    }
}

package seedu.address.model.appointment;

import static seedu.address.testutil.Assert.assertThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AppointmentTest {

    private final String validAppointmentName = "Some bone appointment";
    private final String validAppointmentDate = "2024-10-25";
    private final String validAppointmentTime = "1000-1235";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Appointment(null, "", ""));
        assertThrows(NullPointerException.class, () -> new Appointment("", null, ""));
        assertThrows(NullPointerException.class, () -> new Appointment("", "", null));
    }

    @Test
    public void constructor_invalidAppointmentName_throwsIllegalArgumentException() {
        String invalidAppointmentName = "";
        assertThrows(IllegalArgumentException.class, () -> new Appointment(invalidAppointmentName,
                                                                           validAppointmentDate,
                                                                           validAppointmentTime));
    }

    @Test
    public void constructor_invalidAppointmentDate_throwsIllegalArgumentException() {
        String invalidAppointmentDate = "";
        assertThrows(IllegalArgumentException.class, () -> new Appointment(validAppointmentName,
                                                                           invalidAppointmentDate,
                                                                           validAppointmentTime));
        String invalidAppointmentDate2 = "2024-23-11";
        assertThrows(IllegalArgumentException.class, () -> new Appointment(validAppointmentName,
                                                                           invalidAppointmentDate2,
                                                                           validAppointmentTime));
    }

    @Test
    public void constructor_invalidAppointmentTimePeriod_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Appointment(validAppointmentName,
                                                                           validAppointmentDate,
                                                                           ""));
        assertThrows(IllegalArgumentException.class, () -> new Appointment(validAppointmentName,
                                                                           validAppointmentDate,
                                                                           "5512-1345"));
        assertThrows(IllegalArgumentException.class, () -> new Appointment(validAppointmentName,
                                                                           validAppointmentDate,
                                                                           "5512"));
        assertThrows(IllegalArgumentException.class, () -> new Appointment(validAppointmentName,
                                                                           validAppointmentDate,
                                                                           "asd1200-1223asd"));
        assertThrows(IllegalArgumentException.class, () -> new Appointment(validAppointmentName,
                                                                           validAppointmentDate,
                                                                           "2300-1200"));
    }

    @Test
    public void isValidAppointmentName() {
        // null appointment name
        assertThrows(NullPointerException.class, () -> Appointment.isValidAppointmentName(null));
    }

    @Test
    public void checkIsTimePeriodValid() {
        // null appointment name
        assertThrows(NullPointerException.class, () -> Appointment.checkIsTimePeriodValid(null));
    }

    @Test
    public void compareTo() {
        Appointment a = new Appointment("Some bone appointment", "2024-10-25","1000-1235");
        Appointment same_date_before = new Appointment("Some bone appointment", "2024-10-25","0900-1235");
        Appointment same_date_time = new Appointment("Some bone appointment", "2024-10-25","1000-1235");
        Appointment same_date_after = new Appointment("Some bone appointment", "2024-10-25","1100-1235");

        Appointment early_date_before = new Appointment("Some bone appointment", "2024-10-20","0900-1235");
        Appointment early_date_same = new Appointment("Some bone appointment", "2024-10-20","1000-1235");
        Appointment early_date_after = new Appointment("Some bone appointment", "2024-10-20","1100-1235");

        Appointment later_date_before = new Appointment("Some bone appointment", "2024-11-25","0900-1235");
        Appointment later_date_same = new Appointment("Some bone appointment", "2024-11-25","1000-1235");
        Appointment later_date_after = new Appointment("Some bone appointment", "2024-11-25","1100-1235");

        Assertions.assertTrue(0 < a.compareTo(same_date_before));
        Assertions.assertEquals(0, a.compareTo(same_date_time));
        Assertions.assertTrue(0 > a.compareTo(same_date_after));
        Assertions.assertTrue(0 < a.compareTo(early_date_before));
        Assertions.assertTrue(0 < a.compareTo(early_date_same));
        Assertions.assertTrue(0 < a.compareTo(early_date_after));
        Assertions.assertTrue(0 > a.compareTo(later_date_before));
        Assertions.assertTrue(0 > a.compareTo(later_date_same));
        Assertions.assertTrue(0 > a.compareTo(later_date_after));
    }

}
package seedu.address.model.appointment;

import static seedu.address.testutil.Assert.assertThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AppointmentTest {

    private final String validApptName = "Some bone appointment";
    private final String validApptDate = "2024-10-25";
    private final String validApptTime = "1000-1235";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Appointment(null, validApptDate, validApptTime));
        assertThrows(NullPointerException.class, () -> new Appointment(validApptName, null, validApptTime));
        assertThrows(NullPointerException.class, () -> new Appointment(validApptName, validApptDate, null));
    }

    @Test
    public void constructor_invalidApptName_throwsIllegalArgumentException() {
        String invalidApptName = "";
        assertThrows(IllegalArgumentException.class, () -> new Appointment(invalidApptName,
                                                                           validApptDate,
                                                                           validApptTime));
    }

    @Test
    public void constructor_invalidApptDate_throwsIllegalArgumentException() {
        String invalidApptDate = "";
        assertThrows(IllegalArgumentException.class, () -> new Appointment(validApptName,
                                                                           invalidApptDate,
                                                                           validApptTime));
        String invalidApptDate2 = "2024-23-11";
        assertThrows(IllegalArgumentException.class, () -> new Appointment(validApptName,
                                                                           invalidApptDate2,
                                                                           validApptTime));
    }

    @Test
    public void constructor_invalidApptTimePeriod_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Appointment(validApptName,
                                                                           validApptDate,
                                                                           ""));
        assertThrows(IllegalArgumentException.class, () -> new Appointment(validApptName,
                                                                           validApptDate,
                                                                           "5512-1345"));
        assertThrows(IllegalArgumentException.class, () -> new Appointment(validApptName,
                                                                           validApptDate,
                                                                           "5512"));
        assertThrows(IllegalArgumentException.class, () -> new Appointment(validApptName,
                                                                           validApptDate,
                                                                           "asd1200-1223asd"));
        assertThrows(IllegalArgumentException.class, () -> new Appointment(validApptName,
                                                                           validApptDate,
                                                                           "2300-1200"));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
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
    public void testCompareTo() {
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

    @Test
    public void testToString() {
        Appointment a = new Appointment(validApptName, validApptDate, validApptTime);
        String expected = validApptName + " [ " + validApptDate + " @ " + validApptTime + " ]";
        Assertions.assertEquals(expected, a.toString());
    }

    @SuppressWarnings(
            {
                    "ConstantValue",
                    "EqualsWithItself",
                    "SimplifiableAssertion"
            }
    )
    @Test
    public void testEquals() {
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

        Assertions.assertFalse(a.equals(null));
        Assertions.assertTrue(a.equals(a));
        Assertions.assertFalse(a.equals(same_date_before));
        Assertions.assertTrue(a.equals(same_date_time));
        Assertions.assertFalse(a.equals(same_date_after));
        Assertions.assertFalse(a.equals(early_date_before));
        Assertions.assertFalse(a.equals(early_date_same));
        Assertions.assertFalse(a.equals(early_date_after));
        Assertions.assertFalse(a.equals(later_date_before));
        Assertions.assertFalse(a.equals(later_date_same));
        Assertions.assertFalse(a.equals(later_date_after));
    }

    @Test
    public void testIsClashing() {
        Appointment a = new Appointment("Some bone appointment", "2024-10-25","1000-1235");
        Assertions.assertFalse(a.isClashing("2024-10-25","0900-0935")); // "same_date_before"
        Assertions.assertTrue(a.isClashing("2024-10-25","0900-1235")); // "same_date_before"
        Assertions.assertTrue(a.isClashing("2024-10-25","0845-1300")); // "same_date_time_enclose"
        Assertions.assertTrue(a.isClashing("2024-10-25","1000-1235")); // "same_date_time_inside"
        Assertions.assertTrue(a.isClashing("2024-10-25","1045-1200")); // "same_date_time"
        Assertions.assertTrue(a.isClashing("2024-10-25","1100-1235")); // "same_date_after"
        Assertions.assertFalse(a.isClashing("2024-10-25","1235-1345")); // "same_date_after"

        Assertions.assertFalse(a.isClashing("2024-09-25","0900-0935")); // "earlier_date_before"
        Assertions.assertFalse(a.isClashing("2024-09-25","0900-1235")); // "earlier_date_before"
        Assertions.assertFalse(a.isClashing("2024-09-25","0845-1300")); // "earlier_date_time_enclose"
        Assertions.assertFalse(a.isClashing("2024-09-25","1000-1235")); // "earlier_date_time_inside"
        Assertions.assertFalse(a.isClashing("2024-09-25","1045-1200")); // "earlier_date_time"
        Assertions.assertFalse(a.isClashing("2024-09-25","1100-1235")); // "earlier_date_after"
        Assertions.assertFalse(a.isClashing("2024-09-25","1235-1345")); // "earlier_date_after"

        Assertions.assertFalse(a.isClashing("2024-11-25","0900-0935")); // "later_date_before"
        Assertions.assertFalse(a.isClashing("2024-11-25","0900-1235")); // "later_date_before"
        Assertions.assertFalse(a.isClashing("2024-11-25","0845-1300")); // "later_date_time_enclose"
        Assertions.assertFalse(a.isClashing("2024-11-25","1000-1235")); // "later_date_time_inside"
        Assertions.assertFalse(a.isClashing("2024-11-25","1045-1200")); // "later_date_time"
        Assertions.assertFalse(a.isClashing("2024-11-25","1100-1235")); // "later_date_after"
        Assertions.assertFalse(a.isClashing("2024-11-25","1235-1345")); // "later_date_after"
    }
}
package seedu.address.model.appointment;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AppointmentTest {

    private final String validApptName = "Some bone appointment";
    private final String validApptDate = "2024-10-25";
    private final String validApptTime = "1000-1235";

    @Test
    public void constructorNullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Appointment(null, validApptDate, validApptTime));
        assertThrows(NullPointerException.class, () -> new Appointment(validApptName, null, validApptTime));
        assertThrows(NullPointerException.class, () -> new Appointment(validApptName, validApptDate, null));
    }

    @Test
    public void constructorInvalidApptNameThrowsIllegalArgumentException() {
        String invalidApptName = "";
        assertThrows(IllegalArgumentException.class, () -> new Appointment(invalidApptName,
                                                                           validApptDate,
                                                                           validApptTime));
    }

    @Test
    public void constructorInvalidApptDateThrowsIllegalArgumentException() {
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
    public void constructorInvalidApptTimePeriodThrowsIllegalArgumentException() {
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
        Appointment a = new Appointment("Some bone appointment", "2024-10-25", "1000-1235");
        Appointment sameDateBefore = new Appointment("Some bone appointment", "2024-10-25", "0900-1235");
        Appointment sameDateTime = new Appointment("Some bone appointment", "2024-10-25", "1000-1235");
        Appointment sameDateAfter = new Appointment("Some bone appointment", "2024-10-25", "1100-1235");

        Appointment earlyDateBefore = new Appointment("Some bone appointment", "2024-10-20", "0900-1235");
        Appointment earlyDateSame = new Appointment("Some bone appointment", "2024-10-20", "1000-1235");
        Appointment earlyDateAfter = new Appointment("Some bone appointment", "2024-10-20", "1100-1235");

        Appointment laterDateBefore = new Appointment("Some bone appointment", "2024-11-25", "0900-1235");
        Appointment laterDateSame = new Appointment("Some bone appointment", "2024-11-25", "1000-1235");
        Appointment laterDateAfter = new Appointment("Some bone appointment", "2024-11-25", "1100-1235");

        Assertions.assertTrue(0 < a.compareTo(sameDateBefore));
        Assertions.assertEquals(0, a.compareTo(sameDateTime));
        Assertions.assertTrue(0 > a.compareTo(sameDateAfter));
        Assertions.assertTrue(0 < a.compareTo(earlyDateBefore));
        Assertions.assertTrue(0 < a.compareTo(earlyDateSame));
        Assertions.assertTrue(0 < a.compareTo(earlyDateAfter));
        Assertions.assertTrue(0 > a.compareTo(laterDateBefore));
        Assertions.assertTrue(0 > a.compareTo(laterDateSame));
        Assertions.assertTrue(0 > a.compareTo(laterDateAfter));
    }

    @Test
    public void testToString() {
        Appointment a = new Appointment(validApptName, validApptDate, validApptTime);
        String expected = validApptName + " [ " + validApptDate + " @ " + validApptTime + " ]";
        Assertions.assertEquals(expected, a.toString());
    }

    @SuppressWarnings({
        "ConstantValue",
        "EqualsWithItself",
        "SimplifiableAssertion"})
    @Test
    public void testEquals() {
        Appointment a = new Appointment("Some bone appointment", "2024-10-25", "1000-1235");
        Appointment sameDateBefore = new Appointment("Some bone appointment", "2024-10-25", "0900-1235");
        Appointment sameDateTime = new Appointment("Some bone appointment", "2024-10-25", "1000-1235");
        Appointment sameDateAfter = new Appointment("Some bone appointment", "2024-10-25", "1100-1235");

        Appointment earlyDateBefore = new Appointment("Some bone appointment", "2024-10-20", "0900-1235");
        Appointment earlyDateSame = new Appointment("Some bone appointment", "2024-10-20", "1000-1235");
        Appointment earlyDateAfter = new Appointment("Some bone appointment", "2024-10-20", "1100-1235");

        Appointment laterDateBefore = new Appointment("Some bone appointment", "2024-11-25", "0900-1235");
        Appointment laterDateSame = new Appointment("Some bone appointment", "2024-11-25", "1000-1235");
        Appointment laterDateAfter = new Appointment("Some bone appointment", "2024-11-25", "1100-1235");

        Assertions.assertFalse(a.equals(null));
        Assertions.assertTrue(a.equals(a));
        Assertions.assertFalse(a.equals(sameDateBefore));
        Assertions.assertTrue(a.equals(sameDateTime));
        Assertions.assertFalse(a.equals(sameDateAfter));
        Assertions.assertFalse(a.equals(earlyDateBefore));
        Assertions.assertFalse(a.equals(earlyDateSame));
        Assertions.assertFalse(a.equals(earlyDateAfter));
        Assertions.assertFalse(a.equals(laterDateBefore));
        Assertions.assertFalse(a.equals(laterDateSame));
        Assertions.assertFalse(a.equals(laterDateAfter));
    }

    @Test
    public void testIsClashing() {
        Appointment a = new Appointment("Some bone appointment", "2024-10-25", "1000-1235");
        Assertions.assertFalse(a.isClashing("2024-10-25", "0900-0935")); // "sameDateBefore"
        Assertions.assertTrue(a.isClashing("2024-10-25", "0900-1235")); // "sameDateBefore"
        Assertions.assertTrue(a.isClashing("2024-10-25", "0845-1300")); // "sameDateTimeEnclose"
        Assertions.assertTrue(a.isClashing("2024-10-25", "1000-1235")); // "sameDateTimeInside"
        Assertions.assertTrue(a.isClashing("2024-10-25", "1045-1200")); // "sameDateTime"
        Assertions.assertTrue(a.isClashing("2024-10-25", "1100-1235")); // "sameDateAfter"
        Assertions.assertFalse(a.isClashing("2024-10-25", "1235-1345")); // "sameDateAfter"

        Assertions.assertFalse(a.isClashing("2024-09-25", "0900-0935")); // "earlierDateBefore"
        Assertions.assertFalse(a.isClashing("2024-09-25", "0900-1235")); // "earlierDateBefore"
        Assertions.assertFalse(a.isClashing("2024-09-25", "0845-1300")); // "earlierDateTimeEnclose"
        Assertions.assertFalse(a.isClashing("2024-09-25", "1000-1235")); // "earlierDateTimeInside"
        Assertions.assertFalse(a.isClashing("2024-09-25", "1045-1200")); // "earlierDateTime"
        Assertions.assertFalse(a.isClashing("2024-09-25", "1100-1235")); // "earlierDateAfter"
        Assertions.assertFalse(a.isClashing("2024-09-25", "1235-1345")); // "earlierDateAfter"

        Assertions.assertFalse(a.isClashing("2024-11-25", "0900-0935")); // "laterDateBefore"
        Assertions.assertFalse(a.isClashing("2024-11-25", "0900-1235")); // "laterDateBefore"
        Assertions.assertFalse(a.isClashing("2024-11-25", "0845-1300")); // "laterDateTimeEnclose"
        Assertions.assertFalse(a.isClashing("2024-11-25", "1000-1235")); // "laterDateTimeInside"
        Assertions.assertFalse(a.isClashing("2024-11-25", "1045-1200")); // "laterDateTime"
        Assertions.assertFalse(a.isClashing("2024-11-25", "1100-1235")); // "laterDateAfter"
        Assertions.assertFalse(a.isClashing("2024-11-25", "1235-1345")); // "laterDateAfter"
    }
}

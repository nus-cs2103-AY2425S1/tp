package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class BirthdayTest {

    @Test
    public void equals_differentBirthday() {
        Birthday birthday = new Birthday("1990-05-20");

        // same object -> returns true
        assertTrue(birthday.equals(birthday));

        // same values -> returns true
        Birthday birthdayCopy = new Birthday(birthday.value.toString());
        assertTrue(birthday.equals(birthdayCopy));

        // different types -> returns false
        assertFalse(birthday.equals(1));

        // null -> returns false
        assertFalse(birthday.equals(null));

        // different birthday -> returns false
        Birthday differentBirthday = new Birthday("1991-06-21");
        assertFalse(birthday.equals(differentBirthday));
    }

    @Test
    public void equals_emptyBirthday() {
        Birthday emptyBirthday = new Birthday("");
        Birthday anotherEmptyBirthday = new Birthday("");

        // Same empty birthday -> returns true
        assertTrue(emptyBirthday.equals(anotherEmptyBirthday));
    }

    @Test
    void getValue() {
        Birthday birthday = new Birthday("1990-05-20");
        LocalDate date = LocalDate.parse("1990-05-20");
        String dateString = date.toString();

        //Same date -> returns true
        assertTrue(dateString.equals(birthday.getValue()));
        assertTrue(date.equals(birthday.value));
    }

    @Test
    void isBirthdayWithinNextWeek() {
        LocalDate todaySomeYearsBack = LocalDate.now().minusYears(20); //Valid date for birthday
        LocalDate sixDaysFromTodaySomeYearsBack = todaySomeYearsBack.plusDays(6); //Valid date within a week
        LocalDate nextWeekSomeYearsBack = todaySomeYearsBack.plusWeeks(1); //Valid date but a week after
        LocalDate dayBeforeTodaySomeYearsBack = todaySomeYearsBack.minusDays(1);
        Birthday birthdayToday = new Birthday(todaySomeYearsBack.toString());
        Birthday birthdayInSixDays = new Birthday(sixDaysFromTodaySomeYearsBack.toString());
        Birthday birthdayNextWeek = new Birthday(nextWeekSomeYearsBack.toString());
        Birthday birthdayBeforeToday = new Birthday(dayBeforeTodaySomeYearsBack.toString());

        assertTrue(birthdayToday.isBirthdayWithinNextWeek());
        assertTrue(birthdayInSixDays.isBirthdayWithinNextWeek());
        assertFalse(birthdayNextWeek.isBirthdayWithinNextWeek());
        assertFalse(birthdayBeforeToday.isBirthdayWithinNextWeek());
    }

    @Test
    void getDateOfUpcomingBirthday() {
        LocalDate todaySomeYearsBack = LocalDate.now().minusYears(20);
        LocalDate dayBeforeTodaySomeYearsBack = todaySomeYearsBack.minusDays(1);
        Birthday birthdayToday = new Birthday(todaySomeYearsBack.toString());
        Birthday birthdayBeforeToday = new Birthday(dayBeforeTodaySomeYearsBack.toString());
        Birthday nonBoundaryBirthday = new Birthday("2001-12-31");

        assertEquals(birthdayToday.getDateOfUpcomingBirthday(), LocalDate.now());
        assertEquals(birthdayBeforeToday.getDateOfUpcomingBirthday(),
                LocalDate.now().minusDays(1).plusYears(1));
        assertEquals(nonBoundaryBirthday.getDateOfUpcomingBirthday(),
                LocalDate.parse("2001-12-31").withYear(LocalDate.now().getYear()));
    }

    @Test
    void testHashCode() {
        Birthday typicalBirthday = new Birthday("2001-01-01");
        assertEquals(typicalBirthday.hashCode(), LocalDate.parse("2001-01-01").hashCode());
    }
}

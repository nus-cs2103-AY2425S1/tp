package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.person.Volunteer;

/**
 * Contains unit tests for {@code SortOption}.
 */
public class SortOptionTest {

    @Test
    public void fromString_null_throwsNullPointerException() {
        // Null input should throw NullPointerException
        assertThrows(NullPointerException.class, () -> SortOption.fromString(null));
    }

    @Test
    public void fromString_validOption_returnsSortOption() {
        // Valid sort option: "name"
        SortOption sortOptionName = SortOption.fromString("name");
        assertEquals(SortOption.NAME, sortOptionName);
        assertEquals("name", sortOptionName.toString());
        assertEquals("person", sortOptionName.getRoleAsString());
        assertEquals(Person.class, sortOptionName.getRelatedClass());

        // Valid sort option: "hours"
        SortOption sortOptionHours = SortOption.fromString("hours");
        assertEquals(SortOption.HOURS, sortOptionHours);
        assertEquals("hours", sortOptionHours.toString());
        assertEquals("volunteer", sortOptionHours.getRoleAsString());
        assertEquals(Volunteer.class, sortOptionHours.getRelatedClass());

        // Valid sort option with different casing: "NaMe"
        SortOption sortOptionNameCase = SortOption.fromString("NaMe");
        assertEquals(SortOption.NAME, sortOptionNameCase);
        assertEquals("name", sortOptionNameCase.toString());

        // Valid sort option with different casing: "HoUrS"
        SortOption sortOptionHoursCase = SortOption.fromString("HoUrS");
        assertEquals(SortOption.HOURS, sortOptionHoursCase);
        assertEquals("hours", sortOptionHoursCase.toString());
    }

    @Test
    public void fromString_invalidOption_throwsIllegalArgumentException() {
        String invalidOption = "NotSortOption";
        String emptyOption = "";
        String whitespaceOption = "   ";
        String partiallyInvalidOption = "namee";

        // Invalid sort option: "NotSortOption"
        assertThrows(IllegalArgumentException.class, () -> SortOption.fromString(invalidOption));

        // Invalid sort option: empty string
        assertThrows(IllegalArgumentException.class, () -> SortOption.fromString(emptyOption));

        // Invalid sort option: whitespace string
        assertThrows(IllegalArgumentException.class, () -> SortOption.fromString(whitespaceOption));

        // Invalid sort option: partially invalid
        assertThrows(IllegalArgumentException.class, () -> SortOption.fromString(partiallyInvalidOption));
    }

    @Test
    public void testRoleAndRelatedClass() {
        // Testing role and related class for "name"
        SortOption sortOptionName = SortOption.NAME;
        assertEquals("person", sortOptionName.getRoleAsString());
        assertEquals(Person.class, sortOptionName.getRelatedClass());

        // Testing role and related class for "hours"
        SortOption sortOptionHours = SortOption.HOURS;
        assertEquals("volunteer", sortOptionHours.getRoleAsString());
        assertEquals(Volunteer.class, sortOptionHours.getRelatedClass());
    }
}

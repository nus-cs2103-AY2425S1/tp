package tutorease.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorease.address.logic.commands.CommandTestUtil.INVALID_LOCATION_INDEX_ZERO;
import static tutorease.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.person.Address;

public class LocationIndexTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LocationIndex(null));
    }
    @Test
    public void constructor_invalidLocationIndex_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Address(INVALID_LOCATION_INDEX_ZERO));
        assertThrows(IllegalArgumentException.class, () -> new Address(""));
    }
    @Test
    public void isValidLocationIndex() throws ParseException {
        // null location index
        assertThrows(NullPointerException.class, () -> LocationIndex.isValidLocationIndex(null));

        // invalid location index
        assertFalse(LocationIndex.isValidLocationIndex(""));
        assertFalse(LocationIndex.isValidLocationIndex(" "));
        assertFalse(LocationIndex.isValidLocationIndex("0"));
        assertFalse(LocationIndex.isValidLocationIndex("-1"));
        assertFalse(LocationIndex.isValidLocationIndex("1.1"));
    }
    @Test
    public void getValue() throws ParseException {
        LocationIndex locationIndex = new LocationIndex("1");
        assertNotEquals(2, locationIndex.getValue());
        assertNotEquals(1.0, locationIndex.getValue());
        assertEquals(0, locationIndex.getValue());
    }
    @Test
    public void equals() throws ParseException {
        LocationIndex locationIndex = new LocationIndex("1");

        assertTrue(locationIndex.equals(new LocationIndex("1")));
        assertTrue(locationIndex.equals(locationIndex));
        assertFalse(locationIndex.equals(null));
        assertFalse(locationIndex.equals(5.0f));
        assertFalse(locationIndex.equals(new LocationIndex("2")));
    }
}

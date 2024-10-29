package seedu.address.model.listing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RegionTest {

    @Test
    public void fromString_validRegion_returnsRegion() {
        assertEquals(Region.NORTH, Region.fromString("NORTH"));
        assertEquals(Region.SOUTH, Region.fromString("SOUTH"));
        assertEquals(Region.EAST, Region.fromString("EAST"));
        assertEquals(Region.WEST, Region.fromString("WEST"));
        assertEquals(Region.NORTHEAST, Region.fromString("NORTHEAST"));
        assertEquals(Region.NORTHWEST, Region.fromString("NORTHWEST"));
        assertEquals(Region.SOUTHEAST, Region.fromString("SOUTHEAST"));
        assertEquals(Region.SOUTHWEST, Region.fromString("SOUTHWEST"));
        assertEquals(Region.CENTRAL, Region.fromString("CENTRAL"));
    }

    @Test
    public void fromString_caseInsensitive_returnsRegion() {
        assertEquals(Region.SOUTHWEST, Region.fromString("SouTHWest"));
    }

    @Test
    public void fromString_validRegionWithWhitespace_returnsRegion() {
        assertEquals(Region.NORTH, Region.fromString(" North "));
    }

    @Test
    void fromString_invalidRegion_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Region.fromString("invalid"));
    }

    @Test
    void fromString_nullOrEmptyString_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Region.fromString(null));
        assertThrows(IllegalArgumentException.class, () -> Region.fromString(""));
        assertThrows(IllegalArgumentException.class, () -> Region.fromString("   "));
    }

    @Test
    public void isValidRegion_validRegions_returnTrue() {
        // Test with exact match
        assertTrue(Region.isValidRegion("NORTH"));

        // Test with different cases
        assertTrue(Region.isValidRegion("south"));
        assertTrue(Region.isValidRegion("East"));
        assertTrue(Region.isValidRegion("WEST"));

        // Test with whitespace trimming
        assertTrue(Region.isValidRegion(" northeast "));
    }

    @Test
    public void isValidRegion_invalidRegions_returnFalse() {
        // Test with invalid region names
        assertFalse(Region.isValidRegion("NORTHWESTERN"));
        assertFalse(Region.isValidRegion("Southern Singapore"));
        assertFalse(Region.isValidRegion(""));
        assertFalse(Region.isValidRegion("middle"));
    }

    @Test
    public void isValidRegion_nullRegion_returnFalse() {
        // Test with null input
        assertFalse(Region.isValidRegion(null));
    }
}

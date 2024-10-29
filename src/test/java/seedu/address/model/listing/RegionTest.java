package seedu.address.model.listing;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}

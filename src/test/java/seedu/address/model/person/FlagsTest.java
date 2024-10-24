package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;


public class FlagsTest {

    @Test
    public void testFlagIsPresent() {
        Flags flags = new Flags(PresetFlags.FAVOURITE, PresetFlags.BUYER);

        assertEquals(1, flags.flagIsPresent(PresetFlags.FAVOURITE));
        assertEquals(0, flags.flagIsPresent(PresetFlags.ARCHIVED));
        assertEquals(1, flags.flagIsPresent(PresetFlags.BUYER));
        assertEquals(0, flags.flagIsPresent(PresetFlags.SELLER));
    }

    @Test
    public void testGetNumberOfMatchingFlags() {
        Flags flags = new Flags(PresetFlags.FAVOURITE, PresetFlags.ARCHIVED);

        Set<PresetFlags> testFlags = new HashSet<>();
        testFlags.add(PresetFlags.FAVOURITE);
        testFlags.add(PresetFlags.BUYER);

        // FAVOURITE is present, BUYER is not.
        assertEquals(1, flags.getNumberOfMatchingFlags(testFlags));

        // Test with a different set containing ARCHIVED and SELLER.
        testFlags.clear();
        testFlags.add(PresetFlags.ARCHIVED);
        testFlags.add(PresetFlags.SELLER);

        // ARCHIVED is present, SELLER is not.
        assertEquals(1, flags.getNumberOfMatchingFlags(testFlags));

        // Test with a set containing all present flags.
        testFlags.clear();
        testFlags.add(PresetFlags.FAVOURITE);
        testFlags.add(PresetFlags.ARCHIVED);

        assertEquals(2, flags.getNumberOfMatchingFlags(testFlags));

        // Test with a set containing no matching flags.
        testFlags.clear();
        testFlags.add(PresetFlags.BUYER);
        testFlags.add(PresetFlags.SELLER);

        assertEquals(0, flags.getNumberOfMatchingFlags(testFlags));
    }

    @Test
    public void testGetWeightedFlags() {
        Flags flags = new Flags(PresetFlags.FAVOURITE, PresetFlags.BUYER, PresetFlags.SELLER);

        // Test weight calculation for matching flags.
        // Assuming the weight is calculated as 2^index in reverse order:
        // FAVOURITE: index 0 -> 2^0 = 1
        // ARCHIVED: index 1 -> 2^1 = 2
        // BUYER: index 2 -> 2^2 = 4
        // SELLER: index 3 -> 2^3 = 8
        PresetFlags[] allFlags = {PresetFlags.FAVOURITE, PresetFlags.ARCHIVED, PresetFlags.BUYER, PresetFlags.SELLER};
        assertEquals(7, flags.getWeightedFlags(allFlags)); // FAVOURITE + BUYER + SELLER = 1 + 2 + 4 = 7

        // Test with a subset of flags.
        PresetFlags[] someFlags = {PresetFlags.FAVOURITE, PresetFlags.BUYER};
        assertEquals(3, flags.getWeightedFlags(someFlags)); // FAVOURITE + BUYER = 1 + 2 = 3

        // Test with no matching flags.
        PresetFlags[] noMatchingFlags = {PresetFlags.ARCHIVED};
        assertEquals(0, flags.getWeightedFlags(noMatchingFlags)); // No matching flags = 0
    }

    @Test
    public void testEmptyFlags() {
        // Test with no flags in the set.
        Flags emptyFlags = new Flags();

        assertEquals(0, emptyFlags.getWeightedFlags(PresetFlags.FAVOURITE,
                PresetFlags.ARCHIVED, PresetFlags.BUYER, PresetFlags.SELLER));
        assertEquals(0, emptyFlags.getNumberOfMatchingFlags(new HashSet<>()));

        // Ensure that no flags are present.
        assertEquals(0, emptyFlags.flagIsPresent(PresetFlags.FAVOURITE));
        assertEquals(0, emptyFlags.flagIsPresent(PresetFlags.ARCHIVED));
    }
}

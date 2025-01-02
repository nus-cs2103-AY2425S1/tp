package seedu.address.model.tier;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TierTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tier(null));
    }

    @Test
    public void constructor_invalidTierName_throwsIllegalArgumentException() {
        String invalidTierName = "yellow";
        assertThrows(IllegalArgumentException.class, () -> new Tier(invalidTierName));
    }

    @Test
    public void isValidTierName() {
        // null tier name
        assertThrows(NullPointerException.class, () -> Tier.isValidTierName(null));

        // valid tiers -> return true
        assertTrue(Tier.isValidTierName("gold")); // All lowercase
        assertTrue(Tier.isValidTierName("GOLD")); // All uppercase
        assertTrue(Tier.isValidTierName("gOLd")); // Mix of both cases

        // invalid tier -> returns false
        assertFalse(Tier.isValidTierName(" ")); // Only contains spaces
        assertFalse(Tier.isValidTierName("invalid")); // Not present in the enum
    }

    @Test
    public void equals() {
        Tier tier = new Tier("Gold");

        // same values -> returns true
        assertTrue(tier.equals(new Tier("Gold")));

        // same object -> returns true
        assertTrue(tier.equals(tier));

        // null -> returns false
        assertFalse(tier.equals(null));

        // different types -> returns false
        assertFalse(tier.equals(5.0f));

        // different values -> returns false
        assertFalse(tier.equals(new Tier("Silver")));

        // case-insensitive: all uppercase -> returns true
        assertTrue(tier.equals(new Tier("GOLD")));

        // case-insensitive: all lowercase -> returns true
        assertTrue(tier.equals(new Tier("gold")));
    }
    @Test
    public void toStringMethod() {
        Tier tier = new Tier("Gold");
        assertTrue(tier.toString().equals("[GOLD]"));
    }
}

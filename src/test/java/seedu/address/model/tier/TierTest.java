package seedu.address.model.tier;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TierTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tier(null));
    }

    @Test
    public void constructor_invalidTierName_throwsIllegalArgumentException() {
        String invalidTagName = "yellow";
        assertThrows(IllegalArgumentException.class, () -> new Tier(invalidTagName));
    }

    @Test
    public void isValidTierName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tier.isValidTierName(null));
    }

}

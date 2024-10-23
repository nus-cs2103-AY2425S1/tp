package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SocialMediaTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SocialMedia(null, null));
    }

    @Test
    public void constructor_invalidHandle_throwsIllegalArgumentException() {
        String invalidHandle = "";
        assertThrows(IllegalArgumentException.class, () -> new SocialMedia(invalidHandle,
                SocialMedia.Platform.INSTAGRAM));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> SocialMedia.isValidHandleName(null));

        // invalid addresses
        assertFalse(SocialMedia.isValidHandleName("")); // empty string
        assertFalse(SocialMedia.isValidHandleName(" ")); // spaces only

        // valid addresses
        assertTrue(SocialMedia.isValidHandleName("username"));
        assertTrue(SocialMedia.isValidHandleName("4567")); // one character
        assertTrue(SocialMedia.isValidHandleName("USERNAME123")); // long address
    }

    @Test
    public void equals() {
        SocialMedia socialMedia = new SocialMedia("username", SocialMedia.Platform.FACEBOOK);

        // same values -> returns true
        assertTrue(socialMedia.equals(new SocialMedia("username", SocialMedia.Platform.FACEBOOK)));

        // same object -> returns true
        assertTrue(socialMedia.equals(socialMedia));

        // null -> returns false
        assertFalse(socialMedia.equals(null));

        // different types -> returns false
        assertFalse(socialMedia.equals(5.0f));

        // different values -> returns false
        assertFalse(socialMedia.equals(new SocialMedia("user", SocialMedia.Platform.FACEBOOK)));
        assertFalse(socialMedia.equals(new SocialMedia("username", SocialMedia.Platform.CAROUSELL)));
    }

    @Test
    public void toString_success() {
        assertEquals("[ig-username]",
                new SocialMedia("username", SocialMedia.Platform.INSTAGRAM).toString());
        assertEquals("[fb-username]",
                new SocialMedia("username", SocialMedia.Platform.FACEBOOK).toString());
        assertEquals("[cs-username]",
                new SocialMedia("username", SocialMedia.Platform.CAROUSELL).toString());
        assertEquals(" ",
                new SocialMedia(" ", SocialMedia.Platform.UNNAMED).toString());
    }
}

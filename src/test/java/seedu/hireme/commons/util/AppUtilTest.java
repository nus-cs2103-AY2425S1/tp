package seedu.hireme.commons.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.hireme.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Contains tests for the {@code AppUtil} class.
 */
public class AppUtilTest {

    /**
     * Tests that {@code AppUtil.getImage} returns a non-null object when given a valid image path.
     */
    @Test
    public void getImage_existingImage() {
        assertNotNull(AppUtil.getImage("/images/address_book_32.png"));
    }

    /**
     * Tests that {@code AppUtil.getImage} throws a {@code NullPointerException} when null is passed as the image path.
     */
    @Test
    public void getImage_nullGiven_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> AppUtil.getImage(null));
    }

    /**
     * Tests that {@code AppUtil.checkArgument} does nothing when the condition is true.
     */
    @Test
    public void checkArgument_true_nothingHappens() {
        AppUtil.checkArgument(true);
        AppUtil.checkArgument(true, "");
    }

    /**
     * Tests that {@code AppUtil.checkArgument} throws an {@code IllegalArgumentException}
     * when the condition is false, without a custom error message.
     */
    @Test
    public void checkArgument_falseWithoutErrorMessage_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> AppUtil.checkArgument(false));
    }

    /**
     * Tests that {@code AppUtil.checkArgument} throws an {@code IllegalArgumentException}
     * with the provided custom error message when the condition is false.
     */
    @Test
    public void checkArgument_falseWithErrorMessage_throwsIllegalArgumentException() {
        String errorMessage = "error message";
        assertThrows(IllegalArgumentException.class,
                     errorMessage, () -> AppUtil.checkArgument(false, errorMessage));
    }
}

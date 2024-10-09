package keycontacts.model.pianopiece;

import static keycontacts.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PianoPieceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PianoPiece(null));
    }

    @Test
    public void constructor_invalidPianoPieceName_throwsIllegalArgumentException() {
        String invalidPianoPieceName = "";
        assertThrows(IllegalArgumentException.class, () -> new PianoPiece(invalidPianoPieceName));
    }

    @Test
    public void isValidPianoPieceName() {
        // null piano piece name
        assertThrows(NullPointerException.class, () -> PianoPiece.isValidPianoPieceName(null));

        // blank
        assertFalse(PianoPiece.isValidPianoPieceName("")); // empty string
        assertFalse(PianoPiece.isValidPianoPieceName(" ")); // spaces only

        // valid name
        assertTrue(PianoPiece.isValidPianoPieceName("Für Elise")); // unicode character ü
        assertTrue(PianoPiece.isValidPianoPieceName("Prelude Op. 28 No. 4")); // numbers and dots
        assertTrue(PianoPiece.isValidPianoPieceName("#Special Edition Piece*")); // asterisks
        assertTrue(PianoPiece.isValidPianoPieceName("Sonata (Moonlight)")); // parentheses
    }

}

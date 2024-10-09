package keycontacts.model.pianopiece;

import static keycontacts.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

        // valid name
        assertTrue(PianoPiece.isValidPianoPieceName("Für Elise")); // unicode character ü
        assertTrue(PianoPiece.isValidPianoPieceName("Prelude Op. 28 No. 4")); // numbers and dots
        assertTrue(PianoPiece.isValidPianoPieceName("#Special Edition Piece*")); // asterisks
        assertTrue(PianoPiece.isValidPianoPieceName("Sonata (Moonlight)")); // parentheses
    }

    @Test
    public void equals() {
        PianoPiece piece1 = new PianoPiece("1");
        PianoPiece piece1duplicate = new PianoPiece("1");
        PianoPiece piece2 = new PianoPiece("2");

        assertFalse(piece1.equals(piece2));
        assertFalse(piece2.equals(piece1));

        assertTrue(piece1.equals(piece1));
        assertTrue(piece1.equals(piece1duplicate));
        assertTrue(piece1duplicate.equals(piece1));
    }

    @Test
    public void toStringMethod() {
        String pianoPieceName = "Fugue";
        PianoPiece piece = new PianoPiece(pianoPieceName);

        assertEquals("[" + pianoPieceName + "]", piece.toString());
    }

    @Test
    public void hashCodeMethod() {
        String pianoPieceName = "La Campanella";
        PianoPiece pianoPiece = new PianoPiece(pianoPieceName);

        assertEquals(pianoPieceName.hashCode(), pianoPiece.hashCode());
    }
}

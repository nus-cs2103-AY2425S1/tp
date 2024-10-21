package keycontacts.storage;

import static keycontacts.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import keycontacts.commons.exceptions.IllegalValueException;
import keycontacts.model.pianopiece.PianoPiece;


public class JsonAdaptedPianoPieceTest {
    private static final String VALID_PIANO_PIECE = "Johann Sebastian Bach – Prelude No. 1 in C";
    private static final String INVALID_PIANO_PIECE = " ";
    @Test
    public void toModelType_validPianoPiece_returnsPianoPiece() throws Exception {
        JsonAdaptedPianoPiece jsonAdaptedPianoPiece = new JsonAdaptedPianoPiece(VALID_PIANO_PIECE);
        PianoPiece pianoPiece = new PianoPiece(VALID_PIANO_PIECE);
        assertEquals(pianoPiece, jsonAdaptedPianoPiece.toModelType());
    }
    @Test
    public void toModelType_invalidPianoPiece_throwsIllegalValueException() {
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedPianoPiece(INVALID_PIANO_PIECE).toModelType());
    }
}

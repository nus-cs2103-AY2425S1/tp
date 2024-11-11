package keycontacts.logic.parser;

import static keycontacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static keycontacts.logic.commands.CommandTestUtil.INVALID_PIANO_PIECE_DESC;
import static keycontacts.logic.commands.CommandTestUtil.PIANO_PIECE_DESC_BEETHOVEN;
import static keycontacts.logic.commands.CommandTestUtil.PIANO_PIECE_DESC_PACHELBEL;
import static keycontacts.logic.commands.CommandTestUtil.VALID_PIANO_PIECE_BEETHOVEN;
import static keycontacts.logic.commands.CommandTestUtil.VALID_PIANO_PIECE_PACHELBEL;
import static keycontacts.logic.parser.CommandParserTestUtil.assertParseFailure;
import static keycontacts.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static keycontacts.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import java.util.Set;

import org.junit.jupiter.api.Test;

import keycontacts.logic.commands.UnassignPiecesCommand;
import keycontacts.model.pianopiece.PianoPiece;

public class UnassignPiecesCommandParserTest {
    private final String invalidFormatMessage =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignPiecesCommand.MESSAGE_USAGE);

    private final UnassignPiecesCommandParser parser = new UnassignPiecesCommandParser();
    @Test
    public void parse_noMissingParts_success() {
        UnassignPiecesCommand expectedCommand = new UnassignPiecesCommand(
                INDEX_FIRST_STUDENT,
                Set.of(new PianoPiece(VALID_PIANO_PIECE_BEETHOVEN))
        );

        String userInput = "1" + PIANO_PIECE_DESC_BEETHOVEN;

        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_pianoPieceNotPresent_success() {
        UnassignPiecesCommand expectedCommand = new UnassignPiecesCommand(INDEX_FIRST_STUDENT, Set.of());
        String userInput = "1";
        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_indexNotPresent_failure() {
        String userInput = PIANO_PIECE_DESC_BEETHOVEN;
        assertParseFailure(parser, userInput, invalidFormatMessage);
    }
    @Test
    public void parse_invalidPreamble_failure() {
        assertParseFailure(parser, "-1" + PIANO_PIECE_DESC_BEETHOVEN, invalidFormatMessage);
        assertParseFailure(parser, "0" + PIANO_PIECE_DESC_BEETHOVEN, invalidFormatMessage);
        assertParseFailure(parser, "1 r" + PIANO_PIECE_DESC_BEETHOVEN, invalidFormatMessage);
        assertParseFailure(parser, "1 i/s" + PIANO_PIECE_DESC_BEETHOVEN, invalidFormatMessage);
    }
    @Test
    public void parse_invalidPianoPiece_failure() {
        String userInput = "1" + INVALID_PIANO_PIECE_DESC;
        assertParseFailure(parser, userInput, PianoPiece.MESSAGE_CONSTRAINTS);
    }
    @Test
    public void parse_multiplePianoPieces_success() {
        String userInput = "1" + PIANO_PIECE_DESC_BEETHOVEN + PIANO_PIECE_DESC_PACHELBEL;
        Set<PianoPiece> pianoPieceSet = PianoPiece.getPianoPieceSet(VALID_PIANO_PIECE_BEETHOVEN,
                VALID_PIANO_PIECE_PACHELBEL);
        UnassignPiecesCommand expectedCommand = new UnassignPiecesCommand(INDEX_FIRST_STUDENT, pianoPieceSet);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

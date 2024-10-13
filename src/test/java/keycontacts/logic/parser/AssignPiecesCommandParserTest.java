package keycontacts.logic.parser;

import static keycontacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static keycontacts.logic.parser.CliSyntax.PREFIX_PIECE_NAME;
import static keycontacts.logic.parser.CommandParserTestUtil.assertParseFailure;
import static keycontacts.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Set;

import org.junit.jupiter.api.Test;

import keycontacts.commons.core.index.Index;
import keycontacts.logic.commands.AssignPiecesCommand;
import keycontacts.model.pianopiece.PianoPiece;
import keycontacts.model.util.SampleDataUtil;

public class AssignPiecesCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignPiecesCommand.MESSAGE_USAGE);
    private static final Index VALID_INDEX = Index.fromOneBased(1);
    private static final String VALID_PIANO_PIECE_ONE = "Piece 1";
    private static final String VALID_PIANO_PIECE_TWO = "Piece 2";
    private final AssignPiecesCommandParser parser = new AssignPiecesCommandParser();
    @Test
    public void parse_noMissingParts_success() {
        AssignPiecesCommand expectedCommand = new AssignPiecesCommand(
                VALID_INDEX,
                Set.of(new PianoPiece(VALID_PIANO_PIECE_ONE))
        );

        String userInput = VALID_INDEX.getOneBased()
                + " " + PREFIX_PIECE_NAME + VALID_PIANO_PIECE_ONE;

        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_pianoPieceNotPresent_failure() {
        String userInput = VALID_INDEX.getOneBased() + "";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }
    @Test
    public void parse_indexNotPresent_failure() {
        String userInput = PREFIX_PIECE_NAME + VALID_PIANO_PIECE_ONE;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }
    @Test
    public void parse_invalidPreamble_failure() {
        assertParseFailure(parser, "-1" + PREFIX_PIECE_NAME + VALID_PIANO_PIECE_ONE, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "0" + PREFIX_PIECE_NAME + VALID_PIANO_PIECE_ONE, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 r" + PREFIX_PIECE_NAME + VALID_PIANO_PIECE_ONE, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 i/s" + PREFIX_PIECE_NAME + VALID_PIANO_PIECE_ONE,
                MESSAGE_INVALID_FORMAT);
    }
    @Test
    public void parse_invalidPianoPiece_failure() {
        String userInput = VALID_INDEX.getOneBased() + " " + PREFIX_PIECE_NAME + " ";
        assertParseFailure(parser, userInput, PianoPiece.MESSAGE_CONSTRAINTS);
    }
    @Test
    public void parse_multiplePianoPieces_success() {
        String userInput = VALID_INDEX.getOneBased() + " " + PREFIX_PIECE_NAME + VALID_PIANO_PIECE_ONE + " "
                + PREFIX_PIECE_NAME + VALID_PIANO_PIECE_TWO;
        Set<PianoPiece> pianoPieceSet = SampleDataUtil.getPianoPieceSet(VALID_PIANO_PIECE_ONE, VALID_PIANO_PIECE_TWO);
        AssignPiecesCommand expectedCommand = new AssignPiecesCommand(VALID_INDEX, pianoPieceSet);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

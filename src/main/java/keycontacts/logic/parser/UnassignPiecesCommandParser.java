package keycontacts.logic.parser;

import static keycontacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static keycontacts.logic.parser.CliSyntax.PREFIX_PIECE_NAME;

import java.util.Set;

import keycontacts.commons.core.index.Index;
import keycontacts.logic.commands.UnassignPiecesCommand;
import keycontacts.logic.parser.exceptions.ParseException;
import keycontacts.model.pianopiece.PianoPiece;


/**
 * Parses input arguments and creates a new UnassignPieceCommand object
 */
public class UnassignPiecesCommandParser implements Parser<UnassignPiecesCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UnassignPieceCommand
     * and returns a UnassignPieceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnassignPiecesCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PIECE_NAME);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UnassignPiecesCommand.MESSAGE_USAGE), pe);
        }

        Set<PianoPiece> pianoPieces = ParserUtil.parsePianoPieces(argMultimap.getAllValues(PREFIX_PIECE_NAME));

        return new UnassignPiecesCommand(index, pianoPieces);
    }

}

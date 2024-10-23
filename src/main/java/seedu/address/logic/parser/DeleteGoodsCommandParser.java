package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOODS_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteGoodsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteGoodsCommand object with the given goodsName.
 */
public class DeleteGoodsCommandParser implements Parser<DeleteGoodsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteGoodsCommand
     * and returns a DeleteGoodsCommand for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public DeleteGoodsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_GOODS_NAME);

        // Show error message if no goodsName prefix
        if (!arePrefixesPresent(argMultimap, PREFIX_GOODS_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGoodsCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_GOODS_NAME);
        String goodsName = argMultimap.getValue(PREFIX_GOODS_NAME).get();

        return new DeleteGoodsCommand(goodsName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

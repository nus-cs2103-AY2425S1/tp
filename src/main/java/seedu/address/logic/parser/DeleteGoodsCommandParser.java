package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOODS_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteGoodsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.goods.GoodsName;
import seedu.address.model.person.Name;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GOODS_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_GOODS_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGoodsCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_GOODS_NAME);

        Name supplierName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        GoodsName goodsName = ParserUtil.parseGoodsName(argMultimap.getValue(PREFIX_GOODS_NAME).get());

        return new DeleteGoodsCommand(supplierName, goodsName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

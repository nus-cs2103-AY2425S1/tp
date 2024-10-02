package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ARRIVAL_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOODS_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROCUREMENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddGoodsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.goods.Date;
import seedu.address.model.goods.Goods;
import seedu.address.model.goods.GoodsCategories;
import seedu.address.model.goods.GoodsName;
import seedu.address.model.person.Name;

/**
 * Parses and creates a new AddGoodsCommand object
 */
public class AddGoodsCommandParser implements Parser<AddGoodsCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddGoodsCommand
     * and returns an AddGoodsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddGoodsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GOODS_NAME, PREFIX_QUANTITY, PREFIX_PRICE,
                        PREFIX_CATEGORY, PREFIX_PROCUREMENT_DATE, PREFIX_ARRIVAL_DATE, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_GOODS_NAME, PREFIX_QUANTITY, PREFIX_PRICE, PREFIX_CATEGORY,
                PREFIX_PROCUREMENT_DATE, PREFIX_ARRIVAL_DATE, PREFIX_NAME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGoodsCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_GOODS_NAME, PREFIX_QUANTITY, PREFIX_PRICE,
                PREFIX_CATEGORY, PREFIX_PROCUREMENT_DATE, PREFIX_ARRIVAL_DATE, PREFIX_NAME);
        Name supplierName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        // TODO: ENSURE CHECKS FOR SAFETY
        GoodsName goodsName = ParserUtil.parseGoodsName(argMultimap.getValue(PREFIX_GOODS_NAME).get());
        int quantity = Integer.valueOf(argMultimap.getValue(PREFIX_QUANTITY).get());
        double price = Double.valueOf(argMultimap.getValue(PREFIX_PRICE).get());
        GoodsCategories category = ParserUtil.parseGoodsCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
        Date procurementDate = ParserUtil.parseDateTimeValues(argMultimap.getValue(PREFIX_PROCUREMENT_DATE).get());
        Date arrivalDate = ParserUtil.parseDateTimeValues(argMultimap.getValue(PREFIX_ARRIVAL_DATE).get());

        Goods goods = new Goods(goodsName, quantity, price, category, procurementDate, arrivalDate, true, supplierName);

        return new AddGoodsCommand(goods);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

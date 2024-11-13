package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOODS_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.commons.util.Caster;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.ListGoodsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.goods.GoodsCategories;
import seedu.address.model.goodsreceipt.GoodsCategoryPredicate;
import seedu.address.model.goodsreceipt.GoodsNamePredicate;
import seedu.address.model.goodsreceipt.GoodsReceipt;
import seedu.address.model.goodsreceipt.SupplierNamePredicate;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new ListGoodsCommand object with the given predicate.
 */
public class ListGoodsCommandParser implements Parser<ListGoodsCommand> {

    public static final Predicate<GoodsReceipt> TRUE_PREDICATE = r -> true;

    /**
     * Parses the given {@code String} of arguments in the context of the ListGoodsCommand
     * and returns a ListGoodsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListGoodsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY, PREFIX_GOODS_NAME, PREFIX_NAME);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CATEGORY, PREFIX_GOODS_NAME, PREFIX_NAME);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListGoodsCommand.MESSAGE_USAGE));
        }

        Predicate<GoodsReceipt> predicate = parsePredicate(argMultimap);

        return new ListGoodsCommand(predicate);
    }

    private static Predicate<GoodsReceipt> parsePredicate(ArgumentMultimap argMultimap) throws ParseException {
        List<Optional<? extends Predicate<GoodsReceipt>>> predicates = List.of(
                parseGoodsNamePredicate(argMultimap),
                parseGoodsCategoryPredicate(argMultimap),
                parseSupplierNamePredicate(argMultimap));
        return predicates
                .stream()
                .flatMap(Optional::stream)
                .map(new Caster<Predicate<GoodsReceipt>>())
                .reduce(Predicate::and)
                .orElse(TRUE_PREDICATE);
    }

    private static Optional<SupplierNamePredicate> parseSupplierNamePredicate(
            ArgumentMultimap argMultimap) throws ParseException {
        Optional<Name> name = arePrefixesPresent(argMultimap, PREFIX_NAME)
                ? Optional.of(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()))
                : Optional.empty();
        return name.map(SupplierNamePredicate::new);
    }

    private static Optional<GoodsCategoryPredicate> parseGoodsCategoryPredicate(
            ArgumentMultimap argMultimap) throws ParseException {
        Optional<GoodsCategories> goodsCategory = arePrefixesPresent(argMultimap, PREFIX_CATEGORY)
                ? Optional.of(ParserUtil.parseGoodsCategory(argMultimap.getValue(PREFIX_CATEGORY).get()))
                : Optional.empty();
        return goodsCategory.map(GoodsCategoryPredicate::new);
    }

    private static Optional<GoodsNamePredicate> parseGoodsNamePredicate(ArgumentMultimap argMultimap) {
        Optional<String> goodsName = argMultimap.getValue(PREFIX_GOODS_NAME);
        return goodsName.map(GoodsNamePredicate::new);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.goods.GoodsCategories;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY);

        List<String> keywords = parseKeywords(argMultimap);

        Set<GoodsCategories> categorySet = parseCategorySet(argMultimap);

        if (keywords.isEmpty() && categorySet.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(keywords, categorySet);
    }

    private static Set<GoodsCategories> parseCategorySet(ArgumentMultimap argMultimap) throws ParseException {
        Set<GoodsCategories> categorySet;
        try {
            categorySet = ParserUtil.parseGoodsCategories(
                    argMultimap.getAllValues(PREFIX_CATEGORY));
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE), pe);
        }
        return categorySet;
    }

    private static List<String> parseKeywords(ArgumentMultimap argMultimap) {
        String keywordsArg = argMultimap.getPreamble().trim();

        return Arrays.stream(keywordsArg.split("\\s+"))
                .filter(x -> !x.isEmpty())
                .toList();
    }
}

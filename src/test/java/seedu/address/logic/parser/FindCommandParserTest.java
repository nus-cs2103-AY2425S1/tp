package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.goods.GoodsCategories;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(List.of("Alice", "Bob"));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_noNameNoArg_throwsParseException() {
        assertParseFailure(
                parser,
                " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_badCategoryArg_throwsParseException() {
        assertParseFailure(
                parser,
                String.format("ALICE %s%s", PREFIX_CATEGORY, "Edible"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsWithCategory_returnsFindCommand() {
        List<String> keywords = List.of("Alice", "Bob");
        List<String> categoryArgs = List.of("CONSUMABLES", "LIFESTYLE", "SPECIALTY");
        for (String categoryArg : categoryArgs) {
            GoodsCategories category;
            try {
                category = ParserUtil.parseGoodsCategory(categoryArg);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            FindCommand expectedFindCommand = new FindCommand(keywords, Set.of(category));
            String userInput = " \n Alice \n \t Bob  \t " + PREFIX_CATEGORY + categoryArg;
            assertParseSuccess(parser, userInput, expectedFindCommand);
        }
    }
}

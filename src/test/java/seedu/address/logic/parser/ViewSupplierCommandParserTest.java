package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewSupplierCommand;
import seedu.address.model.supplier.NameContainsKeywordsPredicate;

public class ViewSupplierCommandParserTest {

    private ViewSupplierCommandParser parser = new ViewSupplierCommandParser();

    @Test
    public void parse_validArgs_returnsViewSupplierCommand() {
        // no leading and trailing whitespaces
        ViewSupplierCommand expectedViewSupplierCommand =
                new ViewSupplierCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedViewSupplierCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedViewSupplierCommand);
    }

}

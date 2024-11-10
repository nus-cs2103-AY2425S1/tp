package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SUPPLIERS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewSupplierCommand;

public class ViewSupplierCommandParserTest {

    private ViewSupplierCommandParser parser = new ViewSupplierCommandParser();

    @Test
    public void parse_validArgs_returnsViewSupplierCommand() {
        ViewSupplierCommand expectedViewSupplierCommand =
                new ViewSupplierCommand(PREDICATE_SHOW_ALL_SUPPLIERS);
        assertParseSuccess(parser, "\t", expectedViewSupplierCommand);
    }

}

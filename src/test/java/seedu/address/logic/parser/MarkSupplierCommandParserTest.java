package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkSupplierCommand;
import seedu.address.model.supplier.SupplierStatus;


public class MarkSupplierCommandParserTest {
    private MarkSupplierCommandParser parser = new MarkSupplierCommandParser();
    private final String nonEmptyStatus = "active";
    @Test
    public void parse_indexSpecified_success() {
        // have remark
        Index targetIndex = INDEX_FIRST_SUPPLIER;
        String userInput = "-s " + targetIndex.getOneBased() + " " + nonEmptyStatus;
        MarkSupplierCommand expectedCommand = new MarkSupplierCommand(INDEX_FIRST_SUPPLIER,
                new SupplierStatus(nonEmptyStatus));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkSupplierCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, MarkSupplierCommand.COMMAND_WORD, expectedMessage);
        // no index
        assertParseFailure(parser, MarkSupplierCommand.COMMAND_WORD + " " + nonEmptyStatus, expectedMessage);
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROPERTY;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeletePropertyToBuyCommand;
import seedu.address.logic.commands.DeletePropertyToBuyCommand.EditPersonPropertyDescriptor;
import seedu.address.testutil.EditPersonPropertyDescriptorBuilder;


public class DeletePropertyToBuyCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePropertyToBuyCommand.MESSAGE_USAGE);
    private DeletePropertyToBuyCommandParser parser = new DeletePropertyToBuyCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no personIndex and no propertyIndex specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // no propertyIndex specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidParts_failure() {
        // wrong input specified, should be int
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // wrong input specified, should be int
        assertParseFailure(parser, "a b", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_tooManyParts_failure() {
        // too many index as inputs
        assertParseFailure(parser, "1 1 1 1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_bigGapBetweenArguments_success() {
        Index targetPersonIndex = INDEX_SECOND_PERSON;
        Index targetPropertyIndex = INDEX_FIRST_PROPERTY;
        String userInput = targetPersonIndex.getOneBased() + "       " + targetPropertyIndex.getOneBased();

        EditPersonPropertyDescriptor descriptor = new EditPersonPropertyDescriptorBuilder().build();
        DeletePropertyToBuyCommand expectedCommand = new DeletePropertyToBuyCommand(targetPersonIndex,
                targetPropertyIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetPersonIndex = INDEX_SECOND_PERSON;
        Index targetPropertyIndex = INDEX_FIRST_PROPERTY;
        String userInput = targetPersonIndex.getOneBased() + " " + targetPropertyIndex.getOneBased();

        EditPersonPropertyDescriptor descriptor = new EditPersonPropertyDescriptorBuilder().build();
        DeletePropertyToBuyCommand expectedCommand = new DeletePropertyToBuyCommand(targetPersonIndex,
                targetPropertyIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

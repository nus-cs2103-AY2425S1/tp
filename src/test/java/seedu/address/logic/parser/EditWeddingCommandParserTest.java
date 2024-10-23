package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.EditWeddingCommand.MESSAGE_NOT_EDITED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditWeddingCommand;
import seedu.address.logic.commands.EditWeddingCommand.EditWeddingDescriptor;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingName;
import seedu.address.testutil.EditWeddingDescriptorBuilder;


public class EditWeddingCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditWeddingCommand.MESSAGE_USAGE);

    private EditWeddingCommandParser parser = new EditWeddingCommandParser();

    @Test
    public void parse_missingParts_failure() {

        // no index specified, only valid Wedding name
        String userInput = " " + PREFIX_NAME + "Phoebe and Jade";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + " " + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, WeddingName.MESSAGE_CONSTRAINTS); // invalid name

        assertParseFailure(parser, "1" + " " + PREFIX_DATE + "10 December 2009",
                WeddingDate.MESSAGE_CONSTRAINTS); // invalid date

        // invalid name followed by valid date
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + " " + PREFIX_DATE + "10/10/2010",
                WeddingName.MESSAGE_CONSTRAINTS);

        // valid name followed by invalid date
        assertParseFailure(parser, "1" + NAME_DESC_AMY + " " + PREFIX_DATE + "10 December 2009",
                WeddingDate.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + " " + PREFIX_DATE + "10 December 2009",
                WeddingName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY + " " + PREFIX_DATE + "10/10/2027";

        EditWeddingDescriptor descriptor =
                new EditWeddingDescriptorBuilder().withWeddingName(VALID_NAME_AMY)
                .withWeddingDate("10/10/2027").build();
        EditWeddingCommand expectedCommand = new EditWeddingCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditWeddingDescriptor descriptor =
                new EditWeddingDescriptorBuilder().withWeddingName(VALID_NAME_AMY).build();
        EditWeddingCommand expectedCommand = new EditWeddingCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = targetIndex.getOneBased() + " " + PREFIX_DATE + "10/10/2025";
        descriptor = new EditWeddingDescriptorBuilder().withWeddingDate("10/10/2025").build();
        expectedCommand = new EditWeddingCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_multipleRepeatedFields_failure() {

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY + INVALID_NAME_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + " " + PREFIX_DATE + "10/389g93/2010" + " " + PREFIX_DATE
                  + "10/10/2010";
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // multiple valid fields repeated
        userInput = targetIndex.getOneBased() + NAME_DESC_AMY + " " + PREFIX_DATE + "10/10/2010" + NAME_DESC_BOB + " "
                + PREFIX_DATE + "12/08/2000";
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_DATE));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_NAME_DESC + " " + PREFIX_DATE + "10/389g93/2010" + " "
                + PREFIX_DATE + "10/389g93/2010" + INVALID_NAME_DESC;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_DATE));
    }

}

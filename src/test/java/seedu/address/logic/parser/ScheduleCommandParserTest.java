package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_TIME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.testutil.ScheduleDescriptorBuilder;


public class ScheduleCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE);

    private ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    public void parse_missingIndex_failure() {
        // no index specified
        assertParseFailure(parser, VALID_SCHEDULE_DATE_AMY, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }


    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased()
                + " " + PREFIX_SCHEDULE_NAME + VALID_SCHEDULE_NAME_AMY
                + " " + PREFIX_SCHEDULE_DATE + VALID_SCHEDULE_DATE_AMY
                + " " + PREFIX_SCHEDULE_TIME + VALID_SCHEDULE_TIME_AMY;

        ScheduleCommand.ScheduleDescriptor descriptor = new ScheduleDescriptorBuilder()
                .withScheduleName(VALID_SCHEDULE_NAME_AMY)
                .withScheduleDate(VALID_SCHEDULE_DATE_AMY)
                .withScheduleTime(VALID_SCHEDULE_TIME_AMY).build();
        ScheduleCommand expectedCommand = new ScheduleCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;

        // only date provided
        String userInput = targetIndex.getOneBased()
                + " " + PREFIX_SCHEDULE_DATE + VALID_SCHEDULE_DATE_AMY;

        ScheduleCommand.ScheduleDescriptor descriptor = new ScheduleDescriptorBuilder()
                .withScheduleDate(VALID_SCHEDULE_DATE_AMY).build();
        ScheduleCommand expectedCommand = new ScheduleCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // date + name provided
        userInput = targetIndex.getOneBased()
                + " " + PREFIX_SCHEDULE_NAME + VALID_SCHEDULE_NAME_AMY
                + " " + PREFIX_SCHEDULE_DATE + VALID_SCHEDULE_DATE_AMY;

        descriptor = new ScheduleDescriptorBuilder()
                .withScheduleName(VALID_SCHEDULE_NAME_AMY)
                .withScheduleDate(VALID_SCHEDULE_DATE_AMY).build();
        expectedCommand = new ScheduleCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // date + time provided
        userInput = targetIndex.getOneBased()
                + " " + PREFIX_SCHEDULE_DATE + VALID_SCHEDULE_DATE_AMY
                + " " + PREFIX_SCHEDULE_TIME + VALID_SCHEDULE_TIME_AMY;

        descriptor = new ScheduleDescriptorBuilder()
                .withScheduleDate(VALID_SCHEDULE_DATE_AMY)
                .withScheduleTime(VALID_SCHEDULE_TIME_AMY).build();
        expectedCommand = new ScheduleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecifiedWithExistingDate_success() {
        Index targetIndex = INDEX_FIRST_PERSON;

        // date only
        String userInput = targetIndex.getOneBased()
                + " " + PREFIX_SCHEDULE_DATE + VALID_SCHEDULE_DATE_AMY;

        ScheduleCommand.ScheduleDescriptor descriptor = new ScheduleDescriptorBuilder()
                .withScheduleDate(VALID_SCHEDULE_DATE_AMY).build();
        ScheduleCommand expectedCommand = new ScheduleCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // time only
        userInput = targetIndex.getOneBased()
                + " " + PREFIX_SCHEDULE_TIME + VALID_SCHEDULE_TIME_AMY;

        descriptor = new ScheduleDescriptorBuilder()
                .withScheduleTime(VALID_SCHEDULE_TIME_AMY).build();
        expectedCommand = new ScheduleCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        // name only
        userInput = targetIndex.getOneBased()
                + " " + PREFIX_SCHEDULE_NAME + VALID_SCHEDULE_NAME_BOB;

        descriptor = new ScheduleDescriptorBuilder()
                .withScheduleName(VALID_SCHEDULE_NAME_BOB).build();
        expectedCommand = new ScheduleCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allEmpty_success() {
        Index targetIndex = INDEX_FIRST_PERSON;

        // date only
        String userInput = Integer.toString(targetIndex.getOneBased());

        ScheduleCommand.ScheduleDescriptor descriptor = new ScheduleDescriptorBuilder().build();
        ScheduleCommand expectedCommand = new ScheduleCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

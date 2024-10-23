package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LEVEL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_DESC_S1_EXPRESS;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_S1_EXPRESS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_MATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.UpdateCommand.UpdatePersonDescriptor;
import seedu.address.model.person.Level;
import seedu.address.model.person.Name;
import seedu.address.model.person.Subject;
import seedu.address.testutil.UpdatePersonDescriptorBuilder;

public class TagCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE);

    private final TagCommandParser parser = new TagCommandParser();
    @Test
    public void parse_allFieldsPresent_success() {

        String userInput = NAME_DESC_BOB + SUBJECT_DESC_MATH + LEVEL_DESC_S1_EXPRESS;

        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder()
                .withSubjects(VALID_SUBJECT_MATH)
                .withLevel(VALID_LEVEL_S1_EXPRESS)
                .build();

        TagCommand expectedCommand = new TagCommand(new Name(VALID_NAME_BOB), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_onlySubjectPresent_success() {

        String userInput = NAME_DESC_BOB + SUBJECT_DESC_MATH;
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder()
                .withSubjects(VALID_SUBJECT_MATH)
                .build();

        TagCommand expectedCommand = new TagCommand(new Name(VALID_NAME_BOB), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_onlyLevelPresent_success() {

        String userInput = NAME_DESC_BOB + LEVEL_DESC_S1_EXPRESS;
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder()
                .withLevel(VALID_LEVEL_S1_EXPRESS)
                .build();

        TagCommand expectedCommand = new TagCommand(new Name(VALID_NAME_BOB), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleSubjects_success() {

        String userInput = NAME_DESC_BOB + SUBJECT_DESC_MATH + SUBJECT_DESC_ENGLISH;
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder()
                .withSubjects(VALID_SUBJECT_MATH, VALID_SUBJECT_ENGLISH)
                .build();

        TagCommand expectedCommand = new TagCommand(new Name(VALID_NAME_BOB), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingParts_failure() {
        //No name specified
        assertParseFailure(parser, SUBJECT_DESC_MATH + LEVEL_DESC_S1_EXPRESS, MESSAGE_INVALID_FORMAT);

        //No Subject and no Level specified
        assertParseFailure(parser, NAME_DESC_BOB, MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_invalidPreamble_failure() {
        // Invalid name being parsed as preamble
        assertParseFailure(parser, "1ohoh" + NAME_DESC_BOB, MESSAGE_INVALID_FORMAT);

        //Invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/somestring", MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid Name
        assertParseFailure(parser, INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        //Invalid Subject
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_SUBJECT_DESC, Subject.MESSAGE_CONSTRAINTS);

        //Invalid Level
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_LEVEL_DESC, Level.MESSAGE_CONSTRAINTS);
    }

}

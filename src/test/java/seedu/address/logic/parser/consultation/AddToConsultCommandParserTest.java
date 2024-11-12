package seedu.address.logic.parser.consultation;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.SPACED_PREFIX_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.SPACED_PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.consultation.AddToConsultCommand;
import seedu.address.model.student.Name;

public class AddToConsultCommandParserTest {

    private static final List<Index> EMPTY_INDEX_LIST = new ArrayList<>();
    private static final List<Name> EMPTY_NAME_LIST = new ArrayList<>();
    private static final String JOHN_STRING = "John Doe";
    private static final Name JOHN_NAME = new Name(JOHN_STRING);
    private static final String HARRY_STRING = "Harry Ng";
    private static final Name HARRY_NAME = new Name(HARRY_STRING);
    private static final String FIRST_INDEX_STRING = "1";
    private static final Index FIRST_INDEX = Index.fromOneBased(Integer.parseInt(FIRST_INDEX_STRING));
    private static final String SECOND_INDEX_STRING = "2";
    private static final Index SECOND_INDEX = Index.fromOneBased(Integer.parseInt(SECOND_INDEX_STRING));
    private static final String INVALID_INDEX_NEGATIVE = "-1";
    private static final String INVALID_INDEX_LETTERS = "ABC";

    private AddToConsultCommandParser parser = new AddToConsultCommandParser();


    @Test
    public void parse_namesPresentEmptyIndex_success() {
        Index index = Index.fromOneBased(1);
        List<Name> expectedNames = List.of(JOHN_NAME, HARRY_NAME);

        // no whitespaces
        assertParseSuccess(parser, " 1" + SPACED_PREFIX_NAME + JOHN_STRING + SPACED_PREFIX_NAME + HARRY_STRING,
                new AddToConsultCommand(index, expectedNames, EMPTY_INDEX_LIST));

        // random whitespaces
        assertParseSuccess(parser, "  \n \t 1 \n" + SPACED_PREFIX_NAME + JOHN_STRING
                        + "  \t" + SPACED_PREFIX_NAME + HARRY_STRING + "  ",
                new AddToConsultCommand(index, expectedNames, EMPTY_INDEX_LIST));
    }

    @Test
    public void parse_indicesPresentEmptyName_success() {
        List<Index> expectedIndices = List.of(FIRST_INDEX, SECOND_INDEX);

        // no whitespaces
        assertParseSuccess(parser, FIRST_INDEX_STRING + SPACED_PREFIX_INDEX + FIRST_INDEX_STRING + SPACED_PREFIX_INDEX
                        + SECOND_INDEX_STRING,
                new AddToConsultCommand(FIRST_INDEX, EMPTY_NAME_LIST, expectedIndices));

        // random whitespaces
        assertParseSuccess(parser, "  \n \t" + FIRST_INDEX_STRING + " \n" + SPACED_PREFIX_INDEX
                        + FIRST_INDEX_STRING + "  \t" + SPACED_PREFIX_INDEX + SECOND_INDEX_STRING + "  ",
                new AddToConsultCommand(FIRST_INDEX, EMPTY_NAME_LIST, expectedIndices));
    }

    @Test
    public void parse_allFieldsPresent_success() {
        List<Index> expectedIndices = List.of(FIRST_INDEX, SECOND_INDEX);
        List<Name> expectedNames = List.of(JOHN_NAME, HARRY_NAME);
        // no whitespaces
        assertParseSuccess(parser, FIRST_INDEX_STRING + SPACED_PREFIX_INDEX + FIRST_INDEX_STRING
                        + SPACED_PREFIX_INDEX + SECOND_INDEX_STRING + SPACED_PREFIX_NAME + JOHN_STRING
                        + SPACED_PREFIX_NAME + HARRY_STRING,
                new AddToConsultCommand(FIRST_INDEX, expectedNames, expectedIndices));

        // random whitespaces
        assertParseSuccess(parser, "  \n \t" + FIRST_INDEX_STRING + " \n" + SPACED_PREFIX_INDEX
                        + "  \t" + FIRST_INDEX_STRING
                        + SPACED_PREFIX_INDEX + "    " + SECOND_INDEX_STRING + SPACED_PREFIX_NAME + JOHN_STRING
                        + SPACED_PREFIX_NAME + HARRY_STRING,
                new AddToConsultCommand(FIRST_INDEX, expectedNames, expectedIndices));
    }


    @Test
    public void parse_missingNamesAndIndices_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToConsultCommand.MESSAGE_USAGE);

        // missing student names
        assertParseFailure(parser, " " + FIRST_INDEX_STRING + " ", expectedMessage);

        // missing index and student names
        assertParseFailure(parser, " ", expectedMessage);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToConsultCommand.MESSAGE_USAGE);

        // invalid index
        assertParseFailure(parser, " " + INVALID_INDEX_NEGATIVE + SPACED_PREFIX_NAME + JOHN_STRING
                + SPACED_PREFIX_INDEX + FIRST_INDEX_STRING, expectedMessage);

        // non-numeric index
        assertParseFailure(parser, " " + INVALID_INDEX_LETTERS
                + SPACED_PREFIX_INDEX + SECOND_INDEX_STRING, expectedMessage);
    }

    @Test
    public void parse_invalidName_failure() {
        // invalid name (empty name)
        assertParseFailure(parser, FIRST_INDEX_STRING + SPACED_PREFIX_NAME, Name.MESSAGE_CONSTRAINTS);

        // invalid name (numbers in name)
        assertParseFailure(parser, FIRST_INDEX_STRING + SPACED_PREFIX_NAME
                + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_EMPTY_PREFIX_FIELD;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_NO_PARAMETER_FOUND;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_PRESIDENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NICKNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM_HANDLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.ContainsKeywordsPredicate;

public class FindCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);

    private static final List<String> NAME_KEYWORD_LIST = Arrays.asList("amy", "Bob");
    private static final List<String> TELEGRAM_HANDLE_KEYWORD_LIST = Arrays.asList("amy", "123");
    private static final List<String> EMAIL_KEYWORD_LIST = Arrays.asList("gmail", "amy");
    private static final List<String> STUDENT_STATUS_KEYWORD_LIST = Arrays.asList("Undergrad", "phd");
    private static final List<String> ROLE_KEYWORD_LIST = Arrays.asList("pres", "Admin");
    private static final List<String> NICKNAME_KEYWORD_LIST = Arrays.asList("amy", "bob");
    private static final String NAME_QUERY = " " + PREFIX_NAME + "amy Bob";
    private static final String TELEGRAM_HANDLE_QUERY = " " + PREFIX_TELEGRAM_HANDLE + "amy 123";
    private static final String EMAIL_QUERY = " " + PREFIX_EMAIL + "gmail amy";
    private static final String STUDENT_STATUS_QUERY = " " + PREFIX_STUDENT_STATUS + "Undergrad phd";
    private static final String ROLE_QUERY = " " + PREFIX_ROLE + "pres Admin";
    private static final String NICKNAME_QUERY = " " + PREFIX_NICKNAME + "amy bob";

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_missingParts_throwsParseException() {
        // no prefix and no arguments
        assertParseFailure(parser, "     ", String.format(MESSAGE_NO_PARAMETER_FOUND, FindCommand.MESSAGE_USAGE));

        // no prefix
        assertParseFailure(parser, " Alice Bob", MESSAGE_INVALID_FORMAT);

        // all empty prefix field
        assertParseFailure(parser, " " + PREFIX_NAME, String.format(MESSAGE_EMPTY_PREFIX_FIELD));

        // only one empty prefix field
        assertParseFailure(parser, " " + PREFIX_NAME + ROLE_DESC_PRESIDENT, String.format(MESSAGE_EMPTY_PREFIX_FIELD));
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = NAME_QUERY + ROLE_QUERY + EMAIL_QUERY
                + STUDENT_STATUS_QUERY + NICKNAME_QUERY + TELEGRAM_HANDLE_QUERY;

        ContainsKeywordsPredicate predicate =
                new ContainsKeywordsPredicate(NAME_KEYWORD_LIST, TELEGRAM_HANDLE_KEYWORD_LIST, EMAIL_KEYWORD_LIST,
                        STUDENT_STATUS_KEYWORD_LIST, ROLE_KEYWORD_LIST, NICKNAME_KEYWORD_LIST);
        FindCommand expectedCommand = new FindCommand(predicate);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = NAME_QUERY + ROLE_QUERY + EMAIL_QUERY;

        ContainsKeywordsPredicate predicate =
                new ContainsKeywordsPredicate(NAME_KEYWORD_LIST, Collections.emptyList(), EMAIL_KEYWORD_LIST,
                        Collections.emptyList(), ROLE_KEYWORD_LIST, Collections.emptyList());
        FindCommand expectedCommand = new FindCommand(predicate);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        String userInput = NAME_QUERY;
        ContainsKeywordsPredicate predicate =
                new ContainsKeywordsPredicate(NAME_KEYWORD_LIST, Collections.emptyList(), Collections.emptyList(),
                        Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        FindCommand expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, userInput, expectedCommand);

        // telegramHandle
        userInput = TELEGRAM_HANDLE_QUERY;
        predicate = new ContainsKeywordsPredicate(Collections.emptyList(), TELEGRAM_HANDLE_KEYWORD_LIST,
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = EMAIL_QUERY;
        predicate = new ContainsKeywordsPredicate(Collections.emptyList(), Collections.emptyList(), EMAIL_KEYWORD_LIST,
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, userInput, expectedCommand);

        // student status
        userInput = STUDENT_STATUS_QUERY;
        predicate = new ContainsKeywordsPredicate(Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), STUDENT_STATUS_KEYWORD_LIST, Collections.emptyList(), Collections.emptyList());
        expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, userInput, expectedCommand);

        // role
        userInput = ROLE_QUERY;
        predicate = new ContainsKeywordsPredicate(Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(), ROLE_KEYWORD_LIST, Collections.emptyList());
        expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, userInput, expectedCommand);

        // nickname
        userInput = NICKNAME_QUERY;
        predicate = new ContainsKeywordsPredicate(Collections.emptyList(), Collections.emptyList(),
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), NICKNAME_KEYWORD_LIST);
        expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // valid followed by empty
        String userInput = NAME_QUERY + " " + PREFIX_NAME;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // empty followed by valid
        userInput = " " + PREFIX_NAME + NAME_QUERY;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // mulltiple valid fields repeated
        userInput = NAME_QUERY + ROLE_QUERY + STUDENT_STATUS_QUERY + NAME_QUERY + ROLE_QUERY + STUDENT_STATUS_QUERY;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ROLE, PREFIX_STUDENT_STATUS));
    }
}

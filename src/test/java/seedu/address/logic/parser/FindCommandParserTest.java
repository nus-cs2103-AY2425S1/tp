package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_BLANK_FIELD;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_ADMIN;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_PRESIDENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NICKNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM_HANDLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.contact.ContainsKeywordsPredicate;
import seedu.address.testutil.ContainsKeywordsPredicateBuilder;

public class FindCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_FUNCTION);

    private static final List<String> NAME_KEYWORD_LIST = Arrays.asList("amy", "Bob");
    private static final List<String> TELEGRAM_HANDLE_KEYWORD_LIST = Arrays.asList("amy", "123");
    private static final List<String> EMAIL_KEYWORD_LIST = Arrays.asList("gmail", "amy");
    private static final List<String> STUDENT_STATUS_KEYWORD_LIST = Arrays.asList("Undergrad", "phd");
    private static final List<String> ROLE_KEYWORD_LIST = Arrays.asList("President", "Admin");
    private static final List<String> NICKNAME_KEYWORD_LIST = Arrays.asList("amy", "bob");
    private static final String NAME_QUERY = " " + PREFIX_NAME + "amy Bob";
    private static final String TELEGRAM_HANDLE_QUERY = " " + PREFIX_TELEGRAM_HANDLE + "amy 123";
    private static final String EMAIL_QUERY = " " + PREFIX_EMAIL + "gmail amy"; // sucessful one?
    private static final String STUDENT_STATUS_QUERY = " " + PREFIX_STUDENT_STATUS + "Undergrad phd";
    private static final String PRESIDENT_ROLE_QUERY = ROLE_DESC_PRESIDENT;
    private static final String INVALID_ROLE_QUERY = " " + PREFIX_ROLE + "invalid role";
    private static final String ADMIN_ROLE_QUERY = ROLE_DESC_ADMIN;
    private static final String NICKNAME_QUERY = " " + PREFIX_NICKNAME + "amy bob";

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_missingParts_throwsParseException() {
        // no arguments
        assertParseFailure(parser, "     ", FindCommand.MESSAGE_MISSING_DESCRIPTION);

        // no prefix
        assertParseFailure(parser, " Alice Bob", FindCommand.MESSAGE_MISSING_PREFIX);

        // all empty prefix field
        assertParseFailure(parser, " " + PREFIX_NAME, MESSAGE_BLANK_FIELD);

        // only one empty prefix field
        assertParseFailure(parser, " " + PREFIX_NAME + ROLE_DESC_PRESIDENT, MESSAGE_BLANK_FIELD);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = NAME_QUERY + PRESIDENT_ROLE_QUERY + ADMIN_ROLE_QUERY + EMAIL_QUERY
                + STUDENT_STATUS_QUERY + NICKNAME_QUERY + TELEGRAM_HANDLE_QUERY;

        ContainsKeywordsPredicate predicate =
                new ContainsKeywordsPredicateBuilder().withNameKeywords(NAME_KEYWORD_LIST)
                        .withTelegramHandleKeywords(TELEGRAM_HANDLE_KEYWORD_LIST).withEmailKeywords(EMAIL_KEYWORD_LIST)
                        .withStudentStatusKeywords(STUDENT_STATUS_KEYWORD_LIST).withRoleKeywords(ROLE_KEYWORD_LIST)
                        .withNicknameKeywords(NICKNAME_KEYWORD_LIST).build();
        FindCommand expectedCommand = new FindCommand(predicate);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = NAME_QUERY + PRESIDENT_ROLE_QUERY + ADMIN_ROLE_QUERY + EMAIL_QUERY;

        ContainsKeywordsPredicate predicate =
                new ContainsKeywordsPredicateBuilder().withNameKeywords(NAME_KEYWORD_LIST)
                        .withEmailKeywords(EMAIL_KEYWORD_LIST).withRoleKeywords(ROLE_KEYWORD_LIST).build();
        FindCommand expectedCommand = new FindCommand(predicate);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        String userInput = NAME_QUERY;
        ContainsKeywordsPredicate predicate =
                new ContainsKeywordsPredicateBuilder().withNameKeywords(NAME_KEYWORD_LIST).build();
        FindCommand expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, userInput, expectedCommand);

        // telegramHandle
        userInput = TELEGRAM_HANDLE_QUERY;
        predicate =
                new ContainsKeywordsPredicateBuilder().withTelegramHandleKeywords(TELEGRAM_HANDLE_KEYWORD_LIST).build();
        expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = EMAIL_QUERY;
        predicate = new ContainsKeywordsPredicateBuilder().withEmailKeywords(EMAIL_KEYWORD_LIST).build();
        expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, userInput, expectedCommand);

        // student status
        userInput = STUDENT_STATUS_QUERY;
        predicate =
                new ContainsKeywordsPredicateBuilder().withStudentStatusKeywords(STUDENT_STATUS_KEYWORD_LIST).build();
        expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, userInput, expectedCommand);

        // role
        userInput = PRESIDENT_ROLE_QUERY + ADMIN_ROLE_QUERY;
        predicate = new ContainsKeywordsPredicateBuilder().withRoleKeywords(ROLE_KEYWORD_LIST).build();
        expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, userInput, expectedCommand);

        // nickname
        userInput = NICKNAME_QUERY;
        predicate = new ContainsKeywordsPredicateBuilder().withNicknameKeywords(NICKNAME_KEYWORD_LIST).build();
        expectedCommand = new FindCommand(predicate);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseName_emptyValue_returnsEmpty() throws Exception {
        assertThrows(seedu.address.logic.parser.exceptions.ParseException.class, () -> ParserUtil.parseName(""));
    }

    @Test
    public void parse_invalidRoleValue_failure() {
        String userInput = INVALID_ROLE_QUERY;
        assertParseFailure(parser, userInput, ParserUtil.MESSAGE_INVALID_ROLE_FIELD);
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
        userInput = NAME_QUERY + PRESIDENT_ROLE_QUERY + ADMIN_ROLE_QUERY + STUDENT_STATUS_QUERY
                + NAME_QUERY + STUDENT_STATUS_QUERY;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_STUDENT_STATUS));
    }
}

package tuteez.logic.parser;

import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tuteez.logic.parser.CliSyntax.PREFIX_EMAIL;
import static tuteez.logic.parser.CliSyntax.PREFIX_LESSON;
import static tuteez.logic.parser.CliSyntax.PREFIX_NAME;
import static tuteez.logic.parser.CliSyntax.PREFIX_PHONE;
import static tuteez.logic.parser.CliSyntax.PREFIX_TAG;
import static tuteez.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static tuteez.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tuteez.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import tuteez.logic.commands.FindCommand;
import tuteez.model.person.predicates.AddressContainsKeywordsPredicate;
import tuteez.model.person.predicates.CombinedPredicate;
import tuteez.model.person.predicates.LessonContainsKeywordsPredicate;
import tuteez.model.person.predicates.NameContainsKeywordsPredicate;
import tuteez.model.person.predicates.TagContainsKeywordsPredicate;

public class FindCommandParserTest {

    private static final NameContainsKeywordsPredicate NAME_PREDICATE =
            new NameContainsKeywordsPredicate(Arrays.asList("alice", "bob"));
    private static final TagContainsKeywordsPredicate TAG_PREDICATE =
            new TagContainsKeywordsPredicate(Arrays.asList("math", "secondary4"));
    private static final LessonContainsKeywordsPredicate LESSON_PREDICATE =
            new LessonContainsKeywordsPredicate(Arrays.asList("monday", "1900-2200"));

    private static final String VALID_NAME_DESC = " " + PREFIX_NAME + "alice bob";
    private static final String VALID_PHONE_DESC = " " + PREFIX_PHONE + "91234567 81234567";
    private static final String VALID_EMAIL_DESC = " " + PREFIX_EMAIL + "alice@example.com bob@example.com";
    private static final String VALID_ADDRESS_DESC = " " + PREFIX_ADDRESS + "choa jurong";
    private static final String VALID_TELEGRAM_DESC = " " + PREFIX_TELEGRAM + "alice123 bob321";
    private static final String VALID_TAG_DESC = " " + PREFIX_TAG + "math secondary4";
    private static final String VALID_LESSON_DESC = " " + PREFIX_LESSON + "monday 1900-2200";

    private static final String VALID_NAME_DESC_WITH_SPACE = " " + PREFIX_NAME + "\n alice \n \t bob  \t";
    private static final String VALID_PHONE_DESC_WITH_SPACE = " " + PREFIX_PHONE + "\n 91234567 \n \t 81234567  \t";
    private static final String VALID_EMAIL_DESC_WITH_SPACE =
            " " + PREFIX_EMAIL + "\n alice@example.com  \n \t bob@example.com  \t";
    private static final String VALID_ADDRESS_DESC_WITH_SPACE = " " + PREFIX_ADDRESS + "\n choa \n \t jurong  \t";
    private static final String VALID_TELEGRAM_DESC_WITH_SPACE = " " + PREFIX_TELEGRAM + "\n alice123 \n \t bob321  \t";
    private static final String VALID_TAG_DESC_WITH_SPACE = " " + PREFIX_TAG + "\n math \n \t secondary4  \t";
    private static final String VALID_LESSON_DESC_WITH_SPACE = " " + PREFIX_LESSON + "\n monday \n \t 1900-2200  \t";

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNamePrefixArg_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(new CombinedPredicate(List.of(NAME_PREDICATE)));
        assertParseSuccess(parser, VALID_NAME_DESC, expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, VALID_NAME_DESC_WITH_SPACE, expectedFindCommand);
    }

    @Test
    public void parse_validTagPrefixArg_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(new CombinedPredicate(List.of(TAG_PREDICATE)));
        assertParseSuccess(parser, VALID_TAG_DESC, expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, VALID_TAG_DESC_WITH_SPACE, expectedFindCommand);
    }

    @Test
    public void parse_validLessonPrefixArg_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(new CombinedPredicate(List.of(LESSON_PREDICATE)));
        assertParseSuccess(parser, VALID_LESSON_DESC, expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, VALID_LESSON_DESC_WITH_SPACE, expectedFindCommand);
    }

    @Test
    public void parse_validAllPrefixArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(new CombinedPredicate(List.of(NAME_PREDICATE,
                TAG_PREDICATE, LESSON_PREDICATE)));
        assertParseSuccess(parser, VALID_NAME_DESC + VALID_TAG_DESC + VALID_LESSON_DESC, expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, VALID_NAME_DESC_WITH_SPACE + VALID_TAG_DESC_WITH_SPACE
                + VALID_LESSON_DESC_WITH_SPACE, expectedFindCommand);
    }

}

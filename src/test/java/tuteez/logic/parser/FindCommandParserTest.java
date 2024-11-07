package tuteez.logic.parser;

import static tuteez.logic.Messages.MESSAGE_EMPTY_KEYWORD;
import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.Messages.MESSAGE_MISSING_PREFIX_FOR_FIND;
import static tuteez.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static tuteez.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tuteez.logic.parser.CliSyntax.PREFIX_LESSON_DAY;
import static tuteez.logic.parser.CliSyntax.PREFIX_LESSON_TIME;
import static tuteez.logic.parser.CliSyntax.PREFIX_NAME;
import static tuteez.logic.parser.CliSyntax.PREFIX_TAG;
import static tuteez.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tuteez.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import tuteez.logic.commands.FindCommand;
import tuteez.model.person.lesson.Lesson;
import tuteez.model.person.predicates.AddressContainsKeywordsPredicate;
import tuteez.model.person.predicates.CombinedPredicate;
import tuteez.model.person.predicates.LessonDayContainsKeywordsPredicate;
import tuteez.model.person.predicates.LessonTimeContainsKeywordsPredicate;
import tuteez.model.person.predicates.NameContainsKeywordsPredicate;
import tuteez.model.person.predicates.TagContainsKeywordsPredicate;

public class FindCommandParserTest {

    private static final NameContainsKeywordsPredicate NAME_PREDICATE =
            new NameContainsKeywordsPredicate(Arrays.asList("alice", "bob"));
    private static final AddressContainsKeywordsPredicate ADDRESS_PREDICATE =
            new AddressContainsKeywordsPredicate(Arrays.asList("choa", "jurong"));
    private static final TagContainsKeywordsPredicate TAG_PREDICATE =
            new TagContainsKeywordsPredicate(Arrays.asList("math", "secondary4"));
    private static final LessonDayContainsKeywordsPredicate LESSON_DAY_PREDICATE =
            new LessonDayContainsKeywordsPredicate(Arrays.asList("monday", "wed"));
    private static final LessonTimeContainsKeywordsPredicate LESSON_TIME_PREDICATE =
            new LessonTimeContainsKeywordsPredicate(Arrays.asList("1200-1330", "1900-2200"));

    private static final String VALID_NAME_DESC = " " + PREFIX_NAME + "alice bob";
    private static final String VALID_ADDRESS_DESC = " " + PREFIX_ADDRESS + "choa jurong";
    private static final String VALID_TAG_DESC = " " + PREFIX_TAG + "math secondary4";
    private static final String VALID_LESSON_DAY_DESC = " " + PREFIX_LESSON_DAY + "monday wed";
    private static final String VALID_LESSON_TIME_DESC = " " + PREFIX_LESSON_TIME + "1200-1330 1900-2200";

    private static final String VALID_NAME_DESC_WITH_SPACE = " " + PREFIX_NAME + "\n alice \n \t bob  \t";
    private static final String VALID_ADDRESS_DESC_WITH_SPACE = " " + PREFIX_ADDRESS + "\n choa \n \t jurong  \t";
    private static final String VALID_TAG_DESC_WITH_SPACE = " " + PREFIX_TAG + "\n math \n \t secondary4  \t";
    private static final String VALID_LESSON_DAY_DESC_WITH_SPACE = " " + PREFIX_LESSON_DAY
            + "\n monday \n \t wed  \t";
    private static final String VALID_LESSON_TIME_DESC_WITH_SPACE = " " + PREFIX_LESSON_TIME
            + "\n 1200-1330 \n \t 1900-2200  \t";

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
    public void parse_validAddressPrefixArg_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(new CombinedPredicate(List.of(ADDRESS_PREDICATE)));
        assertParseSuccess(parser, VALID_ADDRESS_DESC, expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, VALID_ADDRESS_DESC_WITH_SPACE, expectedFindCommand);
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
    public void parse_validLessonDayPrefixArg_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(new CombinedPredicate(List.of(LESSON_DAY_PREDICATE)));
        assertParseSuccess(parser, VALID_LESSON_DAY_DESC, expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, VALID_LESSON_DAY_DESC_WITH_SPACE, expectedFindCommand);
    }

    @Test
    public void parse_validLessonTimePrefixArg_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(new CombinedPredicate(List.of(LESSON_TIME_PREDICATE)));
        assertParseSuccess(parser, VALID_LESSON_TIME_DESC, expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, VALID_LESSON_TIME_DESC_WITH_SPACE, expectedFindCommand);
    }

    @Test
    public void parse_validAllPrefixArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(new CombinedPredicate(List.of(NAME_PREDICATE,
                ADDRESS_PREDICATE, TAG_PREDICATE, LESSON_DAY_PREDICATE, LESSON_TIME_PREDICATE)));
        assertParseSuccess(parser, VALID_NAME_DESC + VALID_ADDRESS_DESC + VALID_TAG_DESC
                + VALID_LESSON_DAY_DESC + VALID_LESSON_TIME_DESC, expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, VALID_NAME_DESC_WITH_SPACE + VALID_ADDRESS_DESC_WITH_SPACE
                + VALID_TAG_DESC_WITH_SPACE + VALID_LESSON_DAY_DESC_WITH_SPACE + VALID_LESSON_TIME_DESC_WITH_SPACE,
                expectedFindCommand);
    }

    @Test
    public void parse_noPrefixes_throwsParseException() {
        String userInput = PREAMBLE_WHITESPACE + "some random words";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_PREFIX_FOR_FIND));
    }

    @Test
    public void parse_emptyKeywordAfterPrefix_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_NAME, String.format(MESSAGE_EMPTY_KEYWORD, PREFIX_NAME));
        assertParseFailure(parser, " " + PREFIX_ADDRESS, String.format(MESSAGE_EMPTY_KEYWORD, PREFIX_ADDRESS));
        assertParseFailure(parser, " " + PREFIX_TAG, String.format(MESSAGE_EMPTY_KEYWORD, PREFIX_TAG));
        assertParseFailure(parser, " " + PREFIX_LESSON_DAY, String.format(MESSAGE_EMPTY_KEYWORD,
                PREFIX_LESSON_DAY));
        assertParseFailure(parser, " " + PREFIX_LESSON_TIME, String.format(MESSAGE_EMPTY_KEYWORD,
                PREFIX_LESSON_TIME));

        assertParseFailure(parser, " " + PREFIX_NAME + " " + PREFIX_ADDRESS,
                String.format(MESSAGE_EMPTY_KEYWORD, PREFIX_NAME));
    }

    @Test
    public void parse_invalidLessonDay_throwsParseException() {
        // One invalid keyword
        assertParseFailure(parser, " " + PREFIX_LESSON_DAY + "INVALID",
                "Invalid day keyword inputted after ld/: INVALID\n"
                        + Lesson.MESSAGE_INVALID_LESSON_DAY);

        // One valid keyword and one invalid keyword
        assertParseFailure(parser, " " + PREFIX_LESSON_DAY + "MON INVALID TUE",
                "Invalid day keyword inputted after ld/: INVALID\n"
                        + Lesson.MESSAGE_INVALID_LESSON_DAY);
    }

    @Test
    public void parse_invalidLessonTimeRange_throwsParseException() {
        // One invalid keyword
        assertParseFailure(parser, " lt/2500-2600",
                "Invalid time keyword inputted after lt/: 2500-2600\n"
                        + Lesson.MESSAGE_INVALID_LESSON_TIME);

        // One valid keyword and one invalid keyword
        assertParseFailure(parser, " lt/1000-1100 2500-2600",
                "Invalid time keyword inputted after lt/: 2500-2600\n"
                        + Lesson.MESSAGE_INVALID_LESSON_TIME);
    }
}

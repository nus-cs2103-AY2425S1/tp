package seedu.academyassist.logic.parser;

import static seedu.academyassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.academyassist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.academyassist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.academyassist.model.person.Subject.MESSAGE_CONSTRAINTS;

import org.junit.jupiter.api.Test;

import seedu.academyassist.logic.commands.FilterCommand;
import seedu.academyassist.model.filter.FilterParam;


/**
 * Contains tests for {@code FilterCommandParser}
 */
public class FilterCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            FilterCommand.MESSAGE_USAGE);
    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_validSubjectFilter_success() {
        FilterParam filterParam = new FilterParam("subject");
        // Valid subjects
        assertParseSuccess(parser, "filter s/english", new FilterCommand(filterParam, "english"));
        assertParseSuccess(parser, "filter s/math", new FilterCommand(filterParam, "math"));
        assertParseSuccess(parser, "filter s/chinese", new FilterCommand(filterParam, "chinese"));
        assertParseSuccess(parser, "filter s/science", new FilterCommand(filterParam, "science"));
    }

    @Test
    public void parse_validYearGroupFilter_success() {
        FilterParam filterParam = new FilterParam("yearGroup");
        // Valid year group numbers
        assertParseSuccess(parser, "filter yg/1", new FilterCommand(filterParam, "1"));
        assertParseSuccess(parser, "filter yg/2", new FilterCommand(filterParam, "2"));
        assertParseSuccess(parser, "filter yg/10", new FilterCommand(filterParam, "10"));
    }

    @Test
    public void parse_invalidFilterType_failure() {
        // Unsupported filter types
        assertParseFailure(parser, "filter x/english", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "filter sg/english", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "filter subject/english", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidSubject_failure() {
        // Invalid subjects
        assertParseFailure(parser, "filter s/art", MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "filter s/music", MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "filter s/eng", MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_missingFilterType_failure() {
        // No filter type specified
        assertParseFailure(parser, "filter english", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "filter", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingFilterValue_failure() {
        // No filter value provided
        assertParseFailure(parser, "filter s/", MESSAGE_CONSTRAINTS);
    }
}

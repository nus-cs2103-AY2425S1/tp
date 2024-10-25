package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.FilterCommandParser.MESSAGE_SUPPORT;
import static seedu.address.testutil.TypicalTags.COLLEAGUES;
import static seedu.address.testutil.TypicalTags.FRIENDS;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.RsvpStatus;
import seedu.address.model.tag.Tag;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();
    private Set<Tag> tags = new HashSet<>();
    private Set<RsvpStatus> statuses = new HashSet<>();

    @Test
    public void parse_noInput_failure() {
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleTags_success() {
        tags.add(FRIENDS);
        tags.add(COLLEAGUES);
        assertParseSuccess(parser, " t/friends t/colleagues", new FilterCommand(tags, statuses));
    }

    @Test
    public void parse_mixTagsAndRsvpStatus_success() {
        tags.add(FRIENDS);
        statuses.add(RsvpStatus.COMING);
        assertParseSuccess(parser, " t/friends s/1", new FilterCommand(tags, statuses));
    }

    @Test
    public void parse_filterName_failure() {
        assertParseFailure(parser, " n/alex",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_SUPPORT));
        assertParseFailure(parser, " t/friends n/alex",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_SUPPORT));
    }

    @Test
    public void parse_rsvpStatus_success() {
        statuses.add(RsvpStatus.COMING);
        assertParseSuccess(parser, " s/1", new FilterCommand(tags, statuses));
    }

    @Test
    public void parse_multipleRsvpStatus_failure() {
        String expectedMessage = "Multiple values specified for the following single-valued field(s): s/";
        assertParseFailure(parser, " s/1 s/2", expectedMessage);
    }
}

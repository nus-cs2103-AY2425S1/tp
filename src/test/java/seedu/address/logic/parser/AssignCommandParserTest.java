package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNUSED_STUDY_GROUP_TAG_3A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNUSED_STUDY_GROUP_TAG_3B;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.commands.AssignCommand.AssignStudyGroupTagDescriptor;
import seedu.address.model.tag.StudyGroupTag;

public class AssignCommandParserTest {
    private AssignCommandParser parser = new AssignCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        AssignStudyGroupTagDescriptor descriptor3A = new AssignStudyGroupTagDescriptor();
        descriptor3A.setStudyGroupTag(new StudyGroupTag(VALID_UNUSED_STUDY_GROUP_TAG_3A));
        AssignStudyGroupTagDescriptor descriptor3B = new AssignStudyGroupTagDescriptor();
        descriptor3B.setStudyGroupTag(new StudyGroupTag(VALID_UNUSED_STUDY_GROUP_TAG_3B));

        List<AssignStudyGroupTagDescriptor> expectedDescriptors = Arrays.asList(descriptor3A, descriptor3B);
        AssignCommand expectedAssignCommand = new AssignCommand(expectedDescriptors);

        assertParseSuccess(parser, VALID_UNUSED_STUDY_GROUP_TAG_3A + " " + VALID_UNUSED_STUDY_GROUP_TAG_3B,
                expectedAssignCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, VALID_UNUSED_STUDY_GROUP_TAG_3A + " \n \t  " + VALID_UNUSED_STUDY_GROUP_TAG_3B,
                expectedAssignCommand);
    }

    @Test
    public void parse_duplicateStudyGroups_failure() {
        assertParseFailure(parser,
                VALID_UNUSED_STUDY_GROUP_TAG_3A + " " + VALID_UNUSED_STUDY_GROUP_TAG_3A,
                AssignCommand.MESSAGE_DUPLICATE_STUDYGROUP);
    }

    @Test
    public void parse_nonemptyInvalidArg_throwsParseException() {
        assertParseFailure(parser, "@", StudyGroupTag.MESSAGE_CONSTRAINTS);
    }
}

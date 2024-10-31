package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.commands.AssignCommand.AssignStudyGroupTagDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.StudyGroupTag;

/**
 * Parses input arguments and creates a new AssignCommand object
 */
public class AssignCommandParser implements Parser<AssignCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignCommand and returns an AssignCommand
     * object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
        }

        String[] studyGroups = args.trim().split("\\s+");

        if (Arrays.stream(studyGroups).distinct().count() < studyGroups.length) {
            throw new ParseException(AssignCommand.MESSAGE_DUPLICATE_STUDYGROUP);
        }

        List<AssignStudyGroupTagDescriptor> assignStudyGroupTagDescriptors;
        try {
            assignStudyGroupTagDescriptors = Arrays.stream(studyGroups)
                    .map(name -> {
                        try {
                            AssignStudyGroupTagDescriptor assignStudyGroupTagDescriptor =
                                  new AssignStudyGroupTagDescriptor();
                            assignStudyGroupTagDescriptor.setStudyGroupTag(ParserUtil.parseStudyGroup(name));
                            return assignStudyGroupTagDescriptor;
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new ParseException(StudyGroupTag.MESSAGE_CONSTRAINTS, e);
        }

        return new AssignCommand(assignStudyGroupTagDescriptors);
    }
}

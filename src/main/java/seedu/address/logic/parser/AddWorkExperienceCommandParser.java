package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddWorkExperienceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.WorkExp;

/**
 * Parses input arguments and creates a new AddWorkExperience command object.
 */
public class AddWorkExperienceCommandParser implements Parser<AddWorkExperienceCommand> {

    @Override
    public AddWorkExperienceCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        // Check if the input is valid
        if (trimmedArgs.isEmpty() || !trimmedArgs.contains("in/") || !trimmedArgs.contains("w/")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddWorkExperienceCommand.MESSAGE_USAGE));
        }

        // Split the input to extract the index and work experience
        String[] splitArgs = trimmedArgs.split("\\s+");
        String indexString = null;
        String workExperienceString = null;

        for (String arg : splitArgs) {
            if (arg.startsWith("in/")) {
                indexString = arg.substring(3); // Remove "in/" prefix
            } else if (arg.startsWith("w/")) {
                workExperienceString = arg.substring(2); // Remove "w/" prefix
            }
        }

        if (indexString == null || workExperienceString == null || workExperienceString.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddWorkExperienceCommand.MESSAGE_USAGE));
        }

        try {
            // Parse the index
            Index index = ParserUtil.parseIndex(indexString);

            // Parse the WorkExp object
            WorkExp workExp = ParserUtil.parseWorkExp(workExperienceString);

            // Return the new AddWorkExperience command
            return new AddWorkExperienceCommand(index, workExp);

        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddWorkExperienceCommand.MESSAGE_USAGE), pe);
        }
    }
}

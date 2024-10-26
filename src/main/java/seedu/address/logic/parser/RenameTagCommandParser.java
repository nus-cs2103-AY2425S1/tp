package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.commands.RenameTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

public class RenameTagCommandParser implements Parser<RenameTagCommand> {
    public static final int EXPECTED_ARGUMENT_LENGTH = 2;

    public RenameTagCommand parse(String args) throws ParseException {
        String lowerCaseArguments = args.toLowerCase();
        ArgumentMultimap tokenisedArguments = ArgumentTokenizer.tokenize(lowerCaseArguments, PREFIX_TAG);
        List<String> arguments = tokenisedArguments.getAllValues(PREFIX_TAG);

        if (arguments.size() != EXPECTED_ARGUMENT_LENGTH) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        Tag existingTag = new Tag(arguments.get(0));
        String newTagName = arguments.get(1);
        return new RenameTagCommand(existingTag, newTagName);
    }
}

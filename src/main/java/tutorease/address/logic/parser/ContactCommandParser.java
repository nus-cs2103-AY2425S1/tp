package tutorease.address.logic.parser;

import static tutorease.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tutorease.address.logic.commands.AddContactCommand;
import tutorease.address.logic.commands.Command;
import tutorease.address.logic.commands.DeleteContactCommand;
import tutorease.address.logic.commands.EditContactCommand;
import tutorease.address.logic.commands.FindContactCommand;
import tutorease.address.logic.commands.ListContactCommand;
import tutorease.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates the appropriate ContactCommand objects.
 */
public class ContactCommandParser implements Parser<Command> {
    private static final Pattern CONTACT_COMMAND_FORMAT = Pattern.compile("(?<subCommand>\\S+)(?<subArguments>.*)");

    @Override
    public Command parse(String args) throws ParseException {
        final Matcher matcher = CONTACT_COMMAND_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        final String subCommand = matcher.group("subCommand").toLowerCase();
        final String subArguments = matcher.group("subArguments");

        switch (subCommand) {
        case AddContactCommand.COMMAND_WORD:
            return new AddContactCommandParser().parse(subArguments);
        case DeleteContactCommand.COMMAND_WORD:
            return new DeleteContactCommandParser().parse(subArguments);
        case ListContactCommand.COMMAND_WORD:
            return new ListContactCommand();
        case FindContactCommand.COMMAND_WORD:
            return new FindContactCommandParser().parse(subArguments);
        case EditContactCommand.COMMAND_WORD:
            return new EditContactCommandParser().parse(subArguments);
        // Future sub-commands like add, edit can be handled here
        default:
            throw new ParseException("Unknown contact sub-command: " + subCommand);
        }
    }
}

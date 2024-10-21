package tutorease.address.logic.parser;

import static tutorease.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tutorease.address.logic.commands.AddContactCommand;
import tutorease.address.logic.commands.Command;
import tutorease.address.logic.commands.DeleteContactCommand;
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
        case AddContactCommand.SUB_COMMAND_WORD:
            return new AddContactCommandParser().parse(subArguments);
        case DeleteContactCommand.SUB_COMMAND_WORD:
            return new DeleteContactCommandParser().parse(subArguments);
        case ListContactCommand.SUB_COMMAND_WORD:
            return new ListContactCommand();
        // Future sub-commands like add, edit can be handled here
        case FindContactCommand.SUB_COMMAND_WORD:
            return new FindContactCommandParser().parse(subArguments);
        default:
            throw new ParseException("Unknown contact sub-command: " + subCommand);
        }
    }
}

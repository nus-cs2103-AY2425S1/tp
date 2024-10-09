package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.LessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class LessonCommandParser implements Parser<Command> {
    public static final String COMMAND_WORD = "lesson";
    private static final Pattern CONTACT_COMMAND_FORMAT = Pattern.compile("(?<subCommand>\\S+)(?<subArguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    @Override
    public Command parse(String args) throws ParseException {
        final Matcher matcher = CONTACT_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        final String subCommand = matcher.group("subCommand").toLowerCase();
        final String subArguments = matcher.group("subArguments");

        switch (subCommand) {
        case AddLessonCommand.COMMAND_WORD:
            return new AddLessonParser().parse(subArguments);
        default:
            logger.finer("This user input caused a ParseException: " +
                    LessonCommand.COMMAND_WORD + " " + args);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}

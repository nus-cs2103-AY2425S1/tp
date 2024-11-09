package tutorease.address.logic.parser;

import static tutorease.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorease.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tutorease.address.commons.core.LogsCenter;
import tutorease.address.logic.commands.AddLessonCommand;
import tutorease.address.logic.commands.Command;
import tutorease.address.logic.commands.DeleteLessonCommand;
import tutorease.address.logic.commands.FindLessonCommand;
import tutorease.address.logic.commands.HelpCommand;
import tutorease.address.logic.commands.LessonCommand;
import tutorease.address.logic.commands.ListLessonCommand;
import tutorease.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input into command for execution.
 */
public class LessonCommandParser implements Parser<Command> {
    public static final String COMMAND_WORD = "lesson";
    private static final Pattern CONTACT_COMMAND_FORMAT = Pattern.compile("(?<subCommand>\\S+)(?<subArguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(LessonCommandParser.class);

    @Override
    public Command parse(String args) throws ParseException {
        logger.log(Level.INFO, "Parsing LessonCommand with args: " + args);

        final Matcher matcher = getMatcher(args);
        final String subCommand = matcher.group("subCommand").toLowerCase();
        final String subArguments = matcher.group("subArguments");

        return getCommand(args, subCommand, subArguments);
    }

    private static Command getCommand(String args, String subCommand, String subArguments) throws ParseException {
        logger.log(Level.INFO, "Getting command for LessonCommand with args: " + args);

        switch (subCommand) {
        case AddLessonCommand.COMMAND_WORD:
            return new AddLessonCommandParser().parse(subArguments);
        case DeleteLessonCommand.COMMAND_WORD:
            return new DeleteLessonCommandParser().parse(subArguments);
        case ListLessonCommand.COMMAND_WORD:
            return new ListLessonCommand();
        case FindLessonCommand.COMMAND_WORD:
            return new FindLessonCommandParser().parse(subArguments);
        default:
            logger.log(Level.WARNING, "This user input caused a ParseException: "
                    + LessonCommand.COMMAND_WORD + " " + args);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private static Matcher getMatcher(String args) throws ParseException {
        logger.log(Level.INFO, "Getting matcher for LessonCommand with args: " + args);

        final Matcher matcher = CONTACT_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            logger.log(Level.WARNING, "Invalid arguments found for LessonCommand");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        logger.log(Level.INFO, "Got matcher for LessonCommand with args: " + args);
        return matcher;
    }
}

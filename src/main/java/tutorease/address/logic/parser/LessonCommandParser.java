package tutorease.address.logic.parser;

import static tutorease.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorease.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tutorease.address.commons.core.LogsCenter;
import tutorease.address.logic.commands.AddLessonCommand;
import tutorease.address.logic.commands.Command;
import tutorease.address.logic.commands.DeleteLessonCommand;
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
    private static final Logger logger = LogsCenter.getLogger(TutorEaseParser.class);

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
            return new AddLessonCommandParser().parse(subArguments);
        case DeleteLessonCommand.COMMAND_WORD:
            return new DeleteLessonCommandParser().parse(subArguments);
        case ListLessonCommand.COMMAND_WORD:
            return new ListLessonCommand();
        default:
            logger.finer("This user input caused a ParseException: "
                    + LessonCommand.COMMAND_WORD + " " + args);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}

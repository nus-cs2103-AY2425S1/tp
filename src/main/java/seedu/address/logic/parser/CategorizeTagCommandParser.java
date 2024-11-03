package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;

import seedu.address.logic.commands.CategorizeTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagCategory;

/**
 * Parse input arguments and returns a new CategorizeTagCommand object.
 */
public class CategorizeTagCommandParser implements Parser<CategorizeTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CategorizeTagCommand
     * and returns an CategorizeTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CategorizeTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        System.out.println(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        Optional<String> parsedArgs = argMultimap.getValue(PREFIX_TAG);
        if (parsedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CategorizeTagCommand.MESSAGE_USAGE));
        }
        String[] split = parsedArgs.get().split("\\s+", 2);
        if (split.length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CategorizeTagCommand.MESSAGE_USAGE));
        }
        Tag tag = ParserUtil.parseTag(split[0]);
        TagCategory cat = parseCategory(split[1]);
        return new CategorizeTagCommand(tag, cat);
    }

    private TagCategory parseCategory(String cat) throws ParseException {
        switch (cat) {
        case "general":
            return TagCategory.GENERAL;
        case "acads":
            return TagCategory.ACADEMICS;
        case "activity":
            return TagCategory.ACTIVITIES;
        case "networking":
            return TagCategory.NETWORKING;
        case "mentor":
            return TagCategory.MENTORSHIP;
        default:
            throw new ParseException(String.format(CategorizeTagCommand.MESSAGE_INVALID_CATEGORY, cat));
        }
    }
}

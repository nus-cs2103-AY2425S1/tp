package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.CategorizeTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagCategory;

/**
 * Parses input arguments and returns a new CategorizeTagCommand object.
 */
public class CategorizeTagCommandParser implements Parser<CategorizeTagCommand> {

    private static final String CATEGORY_GENERAL = "general";
    private static final String CATEGORY_ACADEMICS = "acads";
    private static final String CATEGORY_ACTIVITIES = "activity";
    private static final String CATEGORY_NETWORKING = "network";
    private static final String CATEGORY_MENTORSHIP = "mentor";

    /**
     * Parses the given {@code String} of arguments in the context of the CategorizeTagCommand
     * and returns a CategorizeTagCommand object for execution.
     *
     * @param args The input arguments as a string.
     * @return A CategorizeTagCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public CategorizeTagCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        List<String> parsedArgs = argMultimap.getAllValues(PREFIX_TAG);

        if (parsedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CategorizeTagCommand.MESSAGE_USAGE));
        }

        String[] parsedArgsArray = getStringsArray(parsedArgs);

        List<Tag> tags = new ArrayList<>();

        for (int i = 0; i < parsedArgsArray.length - 1; i++) {
            tags.add(ParserUtil.parseTag(parsedArgsArray[i]));
        }

        TagCategory category = parseCategory(parsedArgsArray[parsedArgsArray.length - 1]);

        return new CategorizeTagCommand(tags, category);
    }

    private String[] getStringsArray(List<String> parsedArgs) throws ParseException {
        String[] argsArray = parsedArgs.toArray(String[]::new);

        String[] lastTagAndCategory = argsArray[argsArray.length - 1].split("\\s+");
        if (lastTagAndCategory.length < 2) { // ensure at least one tag is provided
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CategorizeTagCommand.MESSAGE_USAGE));
        }
        String[] allTagsAndCategory = new String[argsArray.length + 1];

        System.arraycopy(argsArray, 0, allTagsAndCategory, 0, argsArray.length - 1);
        allTagsAndCategory[allTagsAndCategory.length - 2] = lastTagAndCategory[0];
        allTagsAndCategory[allTagsAndCategory.length - 1] = lastTagAndCategory[1];
        return allTagsAndCategory;
    }

    /**
     * Parses the given category string and returns the corresponding TagCategory.
     *
     * @param category The category string to parse.
     * @return The corresponding TagCategory.
     * @throws ParseException if the category is invalid.
     */
    private TagCategory parseCategory(String category) throws ParseException {
        switch (category) {
        case CATEGORY_GENERAL:
            return TagCategory.GENERAL;
        case CATEGORY_ACADEMICS:
            return TagCategory.ACADEMICS;
        case CATEGORY_ACTIVITIES:
            return TagCategory.ACTIVITIES;
        case CATEGORY_NETWORKING:
            return TagCategory.NETWORKING;
        case CATEGORY_MENTORSHIP:
            return TagCategory.MENTORSHIP;
        default:
            throw new ParseException(String.format(CategorizeTagCommand.MESSAGE_INVALID_CATEGORY, category));
        }
    }
}

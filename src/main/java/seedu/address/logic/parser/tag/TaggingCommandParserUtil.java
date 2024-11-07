package seedu.address.logic.parser.tag;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * A utility class that provides methods for parsing input arguments related to
 * tagging commands, such as extracting the index and tags from user input.
 */
public class TaggingCommandParserUtil {

    /**
     * Parses the index and tags from the argument map.
     *
     * @param argMultimap the argument multimap containing the preamble and tags
     * @return a pair consisting of the parsed index and tag list
     * @throws ParseException if the index or tags are invalid
     */
    public static Pair<Index, Set<Tag>> parseIndexAndTags(ArgumentMultimap argMultimap, String messageUsage)
            throws ParseException {
        Index index;
        if (!arePrefixesPresent(argMultimap, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, messageUsage));
        }

        try {
            index = Index.oneBasedNoConstraints(Integer.parseInt(argMultimap.getPreamble().trim()));
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, messageUsage));
        }

        Set<Tag> tagValues = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        return new Pair<>(index, new HashSet<>(tagValues));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

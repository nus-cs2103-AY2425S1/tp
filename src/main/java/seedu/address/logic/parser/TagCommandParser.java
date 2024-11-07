package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FORCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.wedding.CreateWeddingCommandParser.arePrefixesPresent;

import java.util.HashSet;
import java.util.Set;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new TagCommand object.
 */
public class TagCommandParser implements Parser<TagCommand> {

    /**
     * Parses the given String of arguments in the context of the TagCommand
     * and returns a TagCommand object for execution.
     *
     * @param args the user input string containing the index and tags to be added
     * @return a new {@code TagCommand} object that contains the parsed index and list of tags
     * @throws ParseException if the input does not conform to the expected format (i.e., invalid index or missing tags)
     */
    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_FORCE);
        Pair<Index, Set<Tag>> indexAndTags = TaggingCommandParserUtil.parseIndexAndTags(argMultimap,
                TagCommand.MESSAGE_USAGE);

        if (arePrefixesPresent(argMultimap, PREFIX_FORCE)) {
            return new TagCommand(indexAndTags.getKey(), new HashSet<>(indexAndTags.getValue()), true);
        } else {
            return new TagCommand(indexAndTags.getKey(), new HashSet<>(indexAndTags.getValue()));
        }
    }
}

package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;


/**
 * Parses the given {@code String} of arguments in the context of the TagCommand
 * and returns a TagCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class TagCommandParser implements Parser<TagCommand> {
    @Override
    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap multimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        try {
            Index index = ParserUtil.parseIndex(multimap.getPreamble());
            String tags = multimap.getValue(PREFIX_TAG).orElse("");
            Set<Tag> tagSet = stringToTagSet(tags);

            if (tagSet.isEmpty()) {
                throw new ParseException("No tags specified");
            }
            return new TagCommand(index, tagSet);

        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Utility method to parse tags delimited by spaces into a Set of Tags
     * @param tagString Input string to parse
     * @return
     */
    private static Set<Tag> stringToTagSet(String tagString) {
        String[] strArr = tagString.split("\\s+"); // regex to catch multiple spaces
        System.out.println(Arrays.toString(strArr));
        Set<Tag> tagSet = Arrays.stream(strArr).map(s -> new Tag(s)).collect(Collectors.toSet());
        return tagSet;
    }
}

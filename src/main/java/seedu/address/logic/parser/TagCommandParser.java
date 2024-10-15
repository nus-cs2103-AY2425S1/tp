package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Handles parsing user input into a TagCommand class
 */
public class TagCommandParser implements Parser<TagCommand> {
    @Override
    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap multimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        //Reformatting error message for duplicate tags
        try {
            multimap.verifyNoDuplicatePrefixesFor(PREFIX_TAG);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    "Please only include one prefix t/ !"));
        }


        // If t/ prefix is missing
        if (multimap.getValue(PREFIX_TAG).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(multimap.getPreamble());
        String tags = multimap.getValue(PREFIX_TAG).orElse("");

        Set<Tag> tagSet;
        try {
            tagSet = Tag.stringToTagSet(tags);
        } catch (IllegalArgumentException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, e.getMessage()));
        }

        // If t/ prefix is followed by empty string
        if (tagSet.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, "No tags specified!"));
        }
        return new TagCommand(index, tagSet);

    }
}

package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.UpdateCommand.UpdatePersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new TagCommand object
 */
public class TagCommandParser implements Parser<TagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns an TagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SUBJECT, PREFIX_LEVEL);
        argMultiMap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_LEVEL);

        Name personToTag;
        if (argMultiMap.getValue(PREFIX_NAME).isPresent()) {
            personToTag = ParserUtil.parseName(argMultiMap.getValue(PREFIX_NAME).get());
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        UpdatePersonDescriptor editPersonTags = new UpdatePersonDescriptor();

        if (argMultiMap.getValue(PREFIX_LEVEL).isPresent()) {
            editPersonTags.setLevel(
                    ParserUtil.parseLevel(
                            argMultiMap.getValue(PREFIX_LEVEL).get()));

        }

        if (argMultiMap.getValue(PREFIX_SUBJECT).isPresent()) {
            editPersonTags.setSubjects(
                    ParserUtil.parseSubjects(
                            argMultiMap.getAllValues(PREFIX_SUBJECT)));
        }

        if (argMultiMap.getValue(PREFIX_SUBJECT).isEmpty()) {
            if (argMultiMap.getValue(PREFIX_LEVEL).isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
            }
        }

        return new TagCommand(personToTag, editPersonTags);
    }
}

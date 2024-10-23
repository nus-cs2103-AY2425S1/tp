package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS, PREFIX_TAG);

        Name targetName;

        try {
            targetName = ParserUtil.parseName(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_ADDRESS);

        Optional<String> nameArg = argMultimap.getValue(PREFIX_NAME);
        Optional<String> phoneArg = argMultimap.getValue(PREFIX_PHONE);
        Optional<String> addressArg = argMultimap.getValue(PREFIX_ADDRESS);
        Optional<List<String>> tagArgs = argMultimap.getAllValues(PREFIX_TAG).isEmpty()
                ? Optional.empty()
                : Optional.of(argMultimap.getAllValues(PREFIX_TAG));

        Optional<Name> name = nameArg.isPresent()
                ? Optional.of(ParserUtil.parseName(nameArg.get()))
                : Optional.empty();
        Optional<Phone> phone = phoneArg.isPresent()
                ? Optional.of(ParserUtil.parsePhone(phoneArg.get()))
                : Optional.empty();
        Optional<Address> address = addressArg.isPresent()
                ? Optional.of(ParserUtil.parseAddress(addressArg.get()))
                : Optional.empty();
        Optional<Set<Tag>> tags = tagArgs.isPresent()
                ? Optional.of(ParserUtil.parseTags(
                        tagArgs.get().stream().filter(s -> !s.isEmpty()).toList()))
                : Optional.empty();

        if (name.isEmpty() && phone.isEmpty() && address.isEmpty() && tags.isEmpty()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor(name, phone, address, tags);
        return new EditCommand(targetName, editPersonDescriptor);
    }
}

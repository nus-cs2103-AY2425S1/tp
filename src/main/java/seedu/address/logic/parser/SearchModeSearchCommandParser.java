package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.contact.commands.EditCommand;
import seedu.address.logic.commands.contact.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.role.Role;
import seedu.address.logic.commands.searchmode.SearchModeSearchCommand;

public class SearchModeSearchCommandParser implements Parser<SearchModeSearchCommand> {
    @Override
    public SearchModeSearchCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TELEGRAM, PREFIX_ROLE);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_TELEGRAM, PREFIX_ROLE);
        //for each prefix, if present, parse the value and create a predicate...
        // combine the predicates using AND
        // return the search command with the predicate
        // original predicate just show all
        Predicate<Person> predicate = Model.PREDICATE_SHOW_ALL_PERSONS;
        // if a field is present, AND with the predicate for that field
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {

        }
        return new SearchModeSearchCommand(predicate);
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.statistics.AddressBookStatistics;

/**
 * Provides the user with statistics from the existing Address Book.
 */
public class StatisticsCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_SUCCESS = "=== Statistics === \n";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        AddressBook currentAddressBook = (AddressBook) model.getAddressBook();
        AddressBookStatistics addressBookStatistics = currentAddressBook.getStatistics();
        String formattedStatistics = ParserUtil.parseAddressBookStatistics(addressBookStatistics);

        return new CommandResult(MESSAGE_SUCCESS + formattedStatistics,
                currentAddressBook.getStatistics());
    }
}

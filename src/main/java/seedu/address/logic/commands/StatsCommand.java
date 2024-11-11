package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

/**
 * Displays statistics including total counts for contacts, jobs, and companies,
 * as well as matched and unmatched contacts.
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays total counts for contacts, jobs, "
            + "companies, and match status.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Total Contacts: %d\nTotal Jobs: %d\nTotal Companies: %d\n"
            + "Matched Contacts: %d\nUnmatched Contacts: %d";
    @Override
    public CommandResult execute(Model model) {

        ReadOnlyAddressBook fullAddressBook = model.getAddressBook();

        int totalContacts = fullAddressBook.getPersonList().size();
        int totalJobs = fullAddressBook.getJobList().size();
        int totalCompanies = fullAddressBook.getCompanyList().size();

        int matchedContacts = (int) fullAddressBook.getPersonList().stream()
                .filter(Person::isMatchPresent)
                .count();
        int unmatchedContacts = totalContacts - matchedContacts;

        String resultMessage = String.format(MESSAGE_SUCCESS,
                totalContacts, totalJobs, totalCompanies, matchedContacts, unmatchedContacts);

        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof StatsCommand);
    }
}

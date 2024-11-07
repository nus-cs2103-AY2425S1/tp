package seedu.address.logic.commands;

import seedu.address.model.Model;
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
        int totalContacts = model.getFilteredPersonList().size();
        int totalJobs = model.getFilteredJobList().size();
        int totalCompanies = model.getFilteredCompanyList().size();

        int matchedContacts = (int) model.getFilteredPersonList().stream()
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

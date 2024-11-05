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

    @Override
    public CommandResult execute(Model model) {
        int totalContacts = model.getFilteredPersonList().size();
        int totalJobs = model.getFilteredJobList().size();
        int totalCompanies = model.getFilteredCompanyList().size();

        long matchedContacts = model.getFilteredPersonList().stream()
                .filter(Person::isMatchPresent)
                .count();
        long unmatchedContacts = totalContacts - matchedContacts;

        String resultMessage = String.format("Total Contacts: %d\nTotal Jobs: %d\nTotal Companies: "
                        + "%d\nMatched Contacts: %d\nUnmatched Contacts: %d",
                totalContacts, totalJobs, totalCompanies, matchedContacts, unmatchedContacts);

        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof StatsCommand);
    }
}

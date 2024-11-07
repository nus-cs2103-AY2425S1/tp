package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.company.Company;
import seedu.address.model.company.NameContainsKeywordsPredicate;

/**
 * Finds an existing company in the address book.
 */

public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds companies by company name, tag,"
            + " or contact number."
            + "\nParameters: find KEYWORD"
            + "\nExample: " + COMMAND_WORD + " WLB:HIGH"
            + "\nExample: " + COMMAND_WORD + " Google"
            + "\nExample: " + COMMAND_WORD + " Google WLB:HIGH";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCompanyList(predicate);
        List<Company> filteredCompanies = model.getFilteredCompanyList();

        if (filteredCompanies.isEmpty()) {
            return new CommandResult("There is no company that suits your keyword!");
        }

        return new CommandResult(
                String.format("Found %d companies!", filteredCompanies.size())
                        + "\n" + filteredCompanies.stream().map(Messages::format).toList());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return String.format("FindCommand[predicate=%s]", predicate);
    }
}

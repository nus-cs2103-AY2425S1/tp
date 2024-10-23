package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.company.NameContainsKeywordsPredicate;

/**
 * Finds an existing company in the address book.
 */

public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds companies by company name, tag,"
            + " or contact number."
            + "\nParameters: find [/c <Company name>] or [/t <Tag name>] or [/num <Contact number>]"
            + "\nExample: " + COMMAND_WORD + " /c Google"
            + "\nExample: " + COMMAND_WORD + " /t Tech"
            + "\nExample: " + COMMAND_WORD + " /num 12345678";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCompanyList(predicate);
        List<?> filteredCompanies = model.getFilteredCompanyList();

        if (filteredCompanies.isEmpty()) {
            return new CommandResult("There is no company that suits your keyword!");
        }

        return new CommandResult(
                String.format("Found %d companies!", filteredCompanies.size())
                        + "\n" + filteredCompanies.toString());
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

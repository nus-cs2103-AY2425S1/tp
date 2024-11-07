package seedu.internbuddy.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.internbuddy.commons.core.index.Index;
import seedu.internbuddy.commons.util.ToStringBuilder;
import seedu.internbuddy.logic.Messages;
import seedu.internbuddy.logic.commands.exceptions.CommandException;
import seedu.internbuddy.model.Model;
import seedu.internbuddy.model.company.Company;
import seedu.internbuddy.model.company.CompanyToViewPredicate;

/**
 * Finds and lists all companies in address book whose name, tags or application details
 * contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the application details of the company identified by the index number "
            + "used in the displayed company list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "%1$s application details shown";

    private final Index index;

    public ViewCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Company> lastShownList = model.getFilteredCompanyList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_COMPANY_LIST_EMPTY);
        }
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(
                    String.format(Messages.MESSAGE_INDEX_EXCEEDS_SIZE, lastShownList.size()));
        }

        Company companyToView = lastShownList.get(index.getZeroBased());
        CompanyToViewPredicate predicate = new CompanyToViewPredicate(companyToView);

        model.viewAppDetails(companyToView);
        model.updateFilteredCompanyList(predicate);

        return new CommandResult(String.format(MESSAGE_SUCCESS, companyToView.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }

        ViewCommand otherViewCommand = (ViewCommand) other;
        return index.equals(otherViewCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}

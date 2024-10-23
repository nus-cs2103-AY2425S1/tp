package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.company.Company;


/**
 * Displays all jobs and persons linked to a specific company in the address book.
 * <p>
 * The {@code ViewCompanyCommand} identifies the company by its index in the currently displayed
 * company list. It then filters and displays the jobs and persons associated with the selected company.
 */
public class ViewCompanyCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String ENTITY_WORD = "company";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays all jobs and persons linked to the company identified by the index number "
            + "used in the displayed company list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + ENTITY_WORD + " 1";



    private final Index targetIndex;

    public ViewCompanyCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the ViewCompanyCommand to display jobs and persons linked to the specified company.
     * Retrieves the company by index, filters jobs and persons linked to it, and shows the results.
     * Throws {@code CommandException} if the index is out of bounds.
     *
     * @param model The {@code Model} containing the company list and job-person links.
     * @return A {@code CommandResult} with feedback on the displayed jobs and persons.
     * @throws CommandException If the index is invalid.
     */
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Company> lastShownList = model.getFilteredCompanyList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        Company companyToView = lastShownList.get(targetIndex.getZeroBased());

        model.showLinkedJobsAndPersonsByCompany(companyToView);

        return new CommandResult(String.format("Showing jobs and persons linked to %s",
                companyToView.getName()));
    }

}

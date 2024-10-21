package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.company.Company;

/**
 * Shows all the bookmarked companies in the address book.
 */
public class BookmarkListCommand extends Command {

    public static final String COMMAND_WORD = "bmlist";

    public static final String MESSAGE_SUCCESS = "Listed all bookmarked companies";

    public static final String MESSAGE_NO_BOOKMARKED_COMPANIES = "There is no bookmarked companies";

    public static final Predicate<Company> PREDICATE_BOOKMARKED_COMPANIES = c -> c.getIsBookmark().getIsBookmarkValue();

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        model.updateFilteredCompanyList(PREDICATE_BOOKMARKED_COMPANIES);
        List<Company> filteredCompanies = model.getFilteredCompanyList();

        if (filteredCompanies.isEmpty()) {
            return new CommandResult(MESSAGE_NO_BOOKMARKED_COMPANIES);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

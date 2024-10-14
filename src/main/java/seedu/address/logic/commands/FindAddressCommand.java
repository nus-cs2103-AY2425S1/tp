package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.AddressContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose address contains any of the argument keywords.
 * Keyword matching is case-insensitive and allows partial matching.
 */
public class FindAddressCommand extends FindCommand {

    public static final String MESSAGE_FIND_ADDRESS_PERSON_SUCCESS = "Search for address containing \"%s\" "
            + " was successful. Showing results:";

    private final AddressContainsKeywordsPredicate predicate;

    /**
     * Command to filter contacts in WedLinker based on phone numbers.
     * The search matches any parts of the phone numbers.
     *
     * @param predicate Keywords used to filter contacts by their phone number.
     */
    public FindAddressCommand(AddressContainsKeywordsPredicate predicate) {
        super();
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);

        if (!model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(String.format(MESSAGE_FIND_ADDRESS_PERSON_SUCCESS, predicate.getDisplayString()));
        } else {
            return new CommandResult(MESSAGE_FIND_PERSON_UNSUCCESSFUL);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindAddressCommand)) {
            return false;
        }

        FindAddressCommand otherFindCommand = (FindAddressCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }

}

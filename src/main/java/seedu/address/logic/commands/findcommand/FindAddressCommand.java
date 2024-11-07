package seedu.address.logic.commands.findcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.person.keywordspredicate.AddressContainsKeywordsPredicate;
/**
 * Finds and lists all persons in address book whose address contains any of the argument keywords.
 * Keyword matching is case-insensitive and allows partial matching.
 */
public class FindAddressCommand extends FindCommand {

    /**
     * Command to filter contacts in WedLinker based on phone numbers.
     * The search matches any parts of the phone numbers.
     *
     * @param predicate Keywords used to filter contacts by their phone number.
     */
    public FindAddressCommand(AddressContainsKeywordsPredicate predicate) {
        super(predicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList((AddressContainsKeywordsPredicate) predicate);

        if (!model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(String.format(
                    Messages.MESSAGE_FIND_ADDRESS_PERSON_SUCCESS, predicate.getDisplayString()
            ));
        } else {
            return new CommandResult(Messages.MESSAGE_FIND_PERSON_UNSUCCESSFUL);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindAddressCommand otherFindCommand)) {
            return false;
        }

        return predicate.equals(otherFindCommand.predicate);
    }
}

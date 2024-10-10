package seedu.address.logic.commands;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds the person from the list of patients
 */
public class FindPersonCommand extends FindCommand{
    public static final String MESSAGE_SUCCESS = "Person has been found";

    public FindPersonCommand(NameContainsKeywordsPredicate predicate) {
        super(predicate);
    }

    /**
     * Finds all persons with names matching the predicate in the address book to the user.
     * @param model {@code Model} which the FindPersonCommand should operate on.
     * */
    @Override
    protected void findEntity(Model model) {
        model.updateFilteredPersonList(this.predicate);
    }

    /**
     * Returns the message to be displayed after finding the person.
     *
     * @return the message to be displayed after finding all persons.
     */
    @Override
    public String getSuccessMessage(Model model) {
        return String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size());
    }
}

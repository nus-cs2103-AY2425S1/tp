package seedu.address.logic.confirmations;

import seedu.address.model.person.Person;

public interface ConfirmationDialog {
    boolean confirmDeletion(Person personToDelete);
}

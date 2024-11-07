package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a contact identified using it's displayed index from the address book.
 */
public class DeleteContactCommand extends DeleteCommand<Person> {
    public static final String ENTITY_WORD = "contact";
    public static final String MESSAGE_DELETE_CONTACT_SUCCESS = "Contact deleted: Name: %1$s; "
            + "Email: %2$s; Phone: %3$s.";

    public DeleteContactCommand(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    protected List<Person> getEntityList(Model model) {
        return model.getFilteredPersonList();
    }

    @Override
    protected void deleteEntity(Model model, Person personToDelete) {
        model.deletePerson(personToDelete);
    }

    @Override
    protected String getSuccessMessage(Person personToDelete) {
        String name = personToDelete.getName().toString();
        String phone = personToDelete.getPhone().toString();
        String email = personToDelete.getEmail().toString();

        return String.format(MESSAGE_DELETE_CONTACT_SUCCESS, name, phone, email);
    }
}

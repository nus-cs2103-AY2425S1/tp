package tutorease.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorease.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

<<<<<<<< HEAD:src/main/java/tutorease/address/logic/commands/ListContactCommand.java
import seedu.address.model.Model;
========
import tutorease.address.model.Model;

>>>>>>>> d2853dfb5fc21fd78be9689eb5964a1ed3ad37fb:src/main/java/tutorease/address/logic/commands/ListCommand.java
/**
 * Lists all contacts in the address book.
 */
public class ListContactCommand extends Command {

    public static final String SUB_COMMAND_WORD = "list";
    public static final String COMMAND_WORD = "contact";
    public static final String MESSAGE_SUCCESS = "Listed all contacts";
    public static final String MESSAGE_NO_CONTACTS_FOUND = "No contacts found.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}


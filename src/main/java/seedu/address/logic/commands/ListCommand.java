package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

/**
 * Lists either all persons or events in the address book to the user.
 * Keyword matching is case-insensitive.
 */
public abstract class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
}

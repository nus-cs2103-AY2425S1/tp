package seedu.address.logic.commands;

/**
 * Represents an abstract command that adds an entity to the address book.
 *
 * <p>This class is intended to be extended by specific commands that handle
 * the deletion of different types of entities, such as persons or events.
 * The actual logic for checking duplicates and deleting the entity to the
 * model must be implemented by the subclasses.</p>
 */
public abstract class DeleteCommand extends Command {
    public static final String MESSAGE_USAGE = "Use \"delete p\" or \"delete e\" to"
            + " specify person or event to be deleted";
}

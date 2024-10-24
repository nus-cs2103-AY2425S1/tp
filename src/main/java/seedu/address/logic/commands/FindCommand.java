package seedu.address.logic.commands;

/**
 * Represents an abstract command to search for an entity by name in the address book.
 *
 * <p>This class is intended to be extended by specific commands that handle
 * the addition of different types of entities, such as persons or events.
 * The actual logic for checking duplicates and adding the entity to the
 * model must be implemented by the subclasses.</p>
 */
public abstract class FindCommand extends Command {
    public static final String MESSAGE_USAGE = "Use \"find p\" or \"find e\" to"
            + "specify person or event name to be searched";
}

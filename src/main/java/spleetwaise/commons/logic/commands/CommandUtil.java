package spleetwaise.commons.logic.commands;

import static spleetwaise.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import spleetwaise.address.logic.Messages;
import spleetwaise.address.model.person.Person;
import spleetwaise.commons.core.index.Index;
import spleetwaise.commons.logic.commands.exceptions.CommandException;
import spleetwaise.commons.model.CommonModelManager;

/**
 * Utility class for common command-related operations. Contains helper methods to reduce code duplication in command
 * classes.
 */
public class CommandUtil {

    /**
     * Retrieves a {@code Person} from the filtered person list at the specified {@code Index}.
     *
     * @param model The {@code CommonModelManager} instance used to retrieve the filtered person list.
     * @param index The {@code Index} of the person to retrieve from the filtered address book list.
     * @return The {@code Person} at the specified index in the filtered person list.
     * @throws CommandException If the specified index is out of bounds for the filtered person list.
     */
    public static Person getPersonByFilteredPersonListIndex(CommonModelManager model, Index index)
            throws CommandException {
        requireAllNonNull(model, index);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(
                    String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, index.getOneBased()));
        }

        return lastShownList.get(index.getZeroBased());
    }
}

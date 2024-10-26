package seedu.address.logic.commands.event.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;
import seedu.address.model.person.Person;



/**
 * Removes a person from an event
 */
public class RemovePersonFromEventCommand extends Command {

    public static final String COMMAND_WORD = "remove";
    public static final String MESSAGE_SUCCESS = "Person %1$s removed from event %2$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Person not found in event";
    public static final String MESSAGE_EVENT_NOT_FOUND = "Event not found";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " ei/ [INDEX] [pi/ [PERSON INDEX]] : Removes a person from "
            + "an event";

    private static final Logger logger = LogsCenter.getLogger(RemovePersonFromEventCommand.class);


    private final Index eventIndex;
    private final Index personIndex;

    /**
     * Creates a RemovePersonFromEventCommand to remove the specified {@code Person} from the specified {@code Event}
     */
    public RemovePersonFromEventCommand(Index eventIndex, Index personIndex) {
        requireAllNonNull(eventIndex, personIndex);
        this.eventIndex = eventIndex;
        this.personIndex = personIndex;
    }

    @Override
    public CommandResult execute(Model model, EventManager eventManager) throws CommandException {
        requireAllNonNull(model, eventManager);

        if (eventIndex.getZeroBased() >= eventManager.getEventList().size()) {
            throw new CommandException(MESSAGE_EVENT_NOT_FOUND);
        }

        //this is a copy of event list -> copied event, have to set it back again
        Event originalEvent = eventManager.getEventList().get(eventIndex.getZeroBased());
        Event event = new Event(originalEvent);

        if (personIndex.getZeroBased() >= event.getAllPersons().size()) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }
        // get the person from address book, then remove from event
        List<Person> lastShownList = model.getFilteredPersonList();
        Person person = lastShownList.get(personIndex.getZeroBased());
        String personRole = event.getRole(person);
        if (personRole == null) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }
        // at this point, person should have a role in event
        assert(event.hasPerson(person));
        logger.info("Removing person " + person.getName() + " from event " + event.getName());
        logger.info(event.getName() + " now has " + event.getAllPersons().size() + " people");

        event.removePerson(person, personRole);
        eventManager.setEvent(originalEvent, event);
        return new CommandResult(String.format(MESSAGE_SUCCESS, person.getName(), event.getName()));




    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemovePersonFromEventCommand // instanceof handles nulls
                && eventIndex.equals(((RemovePersonFromEventCommand) other).eventIndex)
                && personIndex.equals(((RemovePersonFromEventCommand) other).personIndex)); // state check
    }


}

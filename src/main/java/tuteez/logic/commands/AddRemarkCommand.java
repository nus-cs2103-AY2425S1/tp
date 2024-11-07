package tuteez.logic.commands;

import static tuteez.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import tuteez.commons.core.LogsCenter;
import tuteez.commons.core.index.Index;
import tuteez.logic.Messages;
import tuteez.logic.commands.exceptions.CommandException;
import tuteez.model.Model;
import tuteez.model.person.Person;
import tuteez.model.remark.Remark;
import tuteez.model.remark.RemarkList;

/**
 * Adds a remark to the specified person.
 */
public class AddRemarkCommand extends Command {
    public static final String COMMAND_WORD = "addremark";
    public static final String COMMAND_WORD_ALT = "addrmk";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " (short form: " + COMMAND_WORD_ALT + ")"
            + ": Adds a remark for the student identified by the index number in the displayed student list."
            + "\nParameters: INDEX (must be a positive integer)" + PREFIX_REMARK + "REMARK\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_REMARK + "This is a new remark\n";

    private static final Logger logger = LogsCenter.getLogger(AddRemarkCommand.class);

    private final Index personIndex;
    private final Remark remarkToAdd;

    /**
     * Adds the specified Remark to the person {@code personIndex} of the displayed list.
     *
     * @param personIndex The personIndex of the person in the displayed list to add the remark to.
     * @param remarkToAdd Remark to be added.
     */
    public AddRemarkCommand(Index personIndex, Remark remarkToAdd) {
        this.personIndex = personIndex;
        this.remarkToAdd = remarkToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Person personToUpdate = getPersonFromModel(model);
        String logMessage = String.format("Fetched person from model: %s", personToUpdate);
        logger.info(logMessage);
        Person updatedPerson = addRemarkToPerson(personToUpdate);

        model.setPerson(personToUpdate, updatedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.updateLastViewedPerson(updatedPerson);

        return new CommandResult(String.format("Added remark to %1$s: %2$s",
                updatedPerson.getName(), remarkToAdd.toString()));
    }

    private Person getPersonFromModel(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        return lastShownList.get(personIndex.getZeroBased());
    }

    private Person addRemarkToPerson(Person person) {
        RemarkList updatedRemarkList = new RemarkList(new ArrayList<>(person.getRemarkList().getRemarks()));
        updatedRemarkList.addRemark(remarkToAdd);

        String logMessage = String.format("Remark '%s' added to person %s ", remarkToAdd, person);
        logger.info(logMessage);

        return new Person(person.getName(), person.getPhone(), person.getEmail(),
                person.getAddress(), person.getTelegramUsername(),
                person.getTags(), person.getLessons(), updatedRemarkList);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }


        // instanceof handles nulls
        if (!(other instanceof AddRemarkCommand)) {
            return false;
        }

        AddRemarkCommand otherAddRemarkCommand = (AddRemarkCommand) other;
        return remarkToAdd.equals(otherAddRemarkCommand.remarkToAdd)
                && personIndex.equals(otherAddRemarkCommand.personIndex);
    }
}

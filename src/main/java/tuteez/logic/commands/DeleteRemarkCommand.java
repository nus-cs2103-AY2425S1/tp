package tuteez.logic.commands;

import static tuteez.logic.parser.CliSyntax.PREFIX_REMARK_INDEX;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import tuteez.commons.core.LogsCenter;
import tuteez.commons.core.index.Index;
import tuteez.logic.Messages;
import tuteez.logic.commands.exceptions.CommandException;
import tuteez.model.Model;
import tuteez.model.person.Person;
import tuteez.model.remark.RemarkList;

/**
 * Deletes a remark from the specified person.
 */
public class DeleteRemarkCommand extends Command {
    public static final String COMMAND_WORD = "deleteremark";
    public static final String COMMAND_WORD_ALT = "delrmk";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " (short form: " + COMMAND_WORD_ALT + ")"
            + ": Deletes a remark for the student identified by the index number in the displayed student list."
            + "\nParameters: INDEX (must be a positive integer) " + PREFIX_REMARK_INDEX
            + "REMARK_INDEX (must be a positive integer)\n"
            + "Example to add remark: " + COMMAND_WORD + " 2 " + PREFIX_REMARK_INDEX + "1\n";
    public static final String MESSAGE_SUCCESS = "Deleted remark at index %1$s from %2$s";

    private static final Logger logger = LogsCenter.getLogger(DeleteRemarkCommand.class);

    private final Index personIndex;
    private final Index remarkIndex;

    /**
     * Deletes the specified Remark {@code remarkIndex} to the person {@code personIndex} of the displayed list.
     *
     * @param personIndex The personIndex of the person in the displayed list to delete the remark from.
     * @param remarkIndex Remark to be deleted.
     */
    public DeleteRemarkCommand(Index personIndex, Index remarkIndex) {
        this.personIndex = personIndex;
        this.remarkIndex = remarkIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Person personToUpdate = getPersonFromModel(model);
        String logMessage = String.format("Fetched person from model: %s", personToUpdate);
        logger.info(logMessage);
        Person updatedPerson = deleteRemarkFromPerson(personToUpdate);

        model.setPerson(personToUpdate, updatedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.updateLastViewedPerson(updatedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, remarkIndex.getOneBased(),
                updatedPerson.getName()));
    }

    private Person getPersonFromModel(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        return lastShownList.get(personIndex.getZeroBased());
    }

    private Person deleteRemarkFromPerson(Person person) throws CommandException {
        RemarkList updatedRemarkList = new RemarkList(new ArrayList<>(person.getRemarkList().getRemarks()));

        String logSizeMessage = String.format("Size of remark list: %d ", updatedRemarkList.getSize());
        logger.info(logSizeMessage);

        if (remarkIndex.getZeroBased() < 0 || remarkIndex.getZeroBased() >= updatedRemarkList.getSize()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REMARK_INDEX);
        }

        updatedRemarkList.deleteRemark(remarkIndex.getZeroBased());

        String logDeleteMessage = String.format("Remark of index %d deleted from person %s ",
                remarkIndex.getOneBased(), person);
        logger.info(logDeleteMessage);

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
        if (!(other instanceof DeleteRemarkCommand)) {
            return false;
        }

        DeleteRemarkCommand otherDeleteRemarkCommand = (DeleteRemarkCommand) other;
        return remarkIndex.equals(otherDeleteRemarkCommand.remarkIndex)
                && personIndex.equals(otherDeleteRemarkCommand.personIndex);
    }
}

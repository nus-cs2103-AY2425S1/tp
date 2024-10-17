package tuteez.logic.commands;

import java.util.ArrayList;

import tuteez.commons.core.index.Index;
import tuteez.logic.commands.exceptions.CommandException;
import tuteez.model.Model;
import tuteez.model.person.Person;
import tuteez.model.remark.RemarkList;

/**
 * Deletes a remark from the specified person.
 */
public class DeleteRemarkCommand extends RemarkCommand {
    public static final String DELETE_REMARK_PARAM = "-d";

    private final Index remarkIndex;

    /**
     * Deletes the specified Remark {@code remarkIndex} to the person {@code personIndex} of the displayed list.
     *
     * @param personIndex The personIndex of the person in the displayed list to delete the remark from.
     * @param remarkIndex Remark to be deleted.
     */
    public DeleteRemarkCommand(Index personIndex, Index remarkIndex) {
        super(personIndex);
        this.remarkIndex = remarkIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Person personToUpdate = getPersonFromModel(model);
        Person updatedPerson = deleteRemarkFromPerson(personToUpdate);

        model.setPerson(personToUpdate, updatedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format("Deleted remark at index %1$s from Person %2$s",
                remarkIndex.getOneBased(), personIndex.getOneBased()));
    }

    private Person deleteRemarkFromPerson(Person person) {
        RemarkList updatedRemarkList = new RemarkList(new ArrayList<>(person.getRemarkList().getRemarks()));
        updatedRemarkList.deleteRemark(remarkIndex.getZeroBased());

        return new Person(person.getName(), person.getPhone(), person.getEmail(),
                person.getAddress(), person.getTags(), updatedRemarkList);
    }
}

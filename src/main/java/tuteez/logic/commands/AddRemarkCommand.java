package tuteez.logic.commands;

import java.util.ArrayList;

import tuteez.commons.core.index.Index;
import tuteez.logic.commands.exceptions.CommandException;
import tuteez.model.Model;
import tuteez.model.person.Person;
import tuteez.model.remark.Remark;
import tuteez.model.remark.RemarkList;

/**
 * Adds a remark to the specified person.
 */
public class AddRemarkCommand extends RemarkCommand {
    public static final String ADD_REMARK_PARAM = "-a";

    private final Remark remarkToAdd;

    /**
     * Adds the specified Remark to the person {@code personIndex} of the displayed list.
     *
     * @param personIndex The personIndex of the person in the displayed list to add the remark to.
     * @param remarkToAdd Remark to be added.
     */
    public AddRemarkCommand(Index personIndex, Remark remarkToAdd) {
        super(personIndex);
        this.remarkToAdd = remarkToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Person personToUpdate = getPersonFromModel(model);
        Person updatedPerson = addRemarkToPerson(personToUpdate);

        model.setPerson(personToUpdate, updatedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format("Added remark to Person %1$s: %2$s",
                personIndex.getOneBased(), remarkToAdd.toString()));
    }

    private Person addRemarkToPerson(Person person) {
        RemarkList updatedRemarkList = new RemarkList(new ArrayList<>(person.getRemarkList().getRemarks()));
        updatedRemarkList.addRemark(remarkToAdd);
        return new Person(person.getName(), person.getPhone(), person.getEmail(),
                person.getAddress(), person.getTags(), updatedRemarkList);
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

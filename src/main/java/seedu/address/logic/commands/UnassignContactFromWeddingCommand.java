package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.ParserUtil.parsePersonListToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.PersonInWeddingPredicate;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

/**
 * This UnassignContactFromWeddingCommand class unassigns contacts in the addressbook
 * from the wedding at the specified index.
 */
public class UnassignContactFromWeddingCommand extends Command {

    public static final String COMMAND_WORD = "unassign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unassigns contacts from a specific wedding "
            + "where the contacts are identified by their index number.\n"
            + "Parameters: unassign "
            + PREFIX_CONTACT + "(specify at least 1 person index to assign)... \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CONTACT + "1 2 3";

    public static final String MESSAGE_UNASSIGN_FROM_WEDDING_SUCCESS =
            "Unassigned the following people from %1$s: %2$s";


    //private final Index specificWeddingIndex;

    private final Set<Index> unassignedPersonIndexList;


    /**
     * This constructor initialises the specific wedding index and the
     * index's of the contacts to unassign from that wedding
     * @param unassignedPersonIndexList
     */
    public UnassignContactFromWeddingCommand(Set<Index> unassignedPersonIndexList) {
        this.unassignedPersonIndexList = unassignedPersonIndexList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Wedding> lastShownWeddingList = model.getFilteredWeddingList();

        ObjectProperty<WeddingName> specificWeddingName = model.getCurrentWeddingName();

        if (specificWeddingName.get() == null) {
            throw new CommandException("You need to be viewing a wedding to unassign contacts.");
        }

        Wedding weddingToModify = new Wedding(null, null);
        List<PersonId> existingPersonsInWedding = new ArrayList<>();
        for (Wedding w : lastShownWeddingList) {
            if (w.getWeddingName().equals(specificWeddingName.get())) {
                weddingToModify = w;
                existingPersonsInWedding = w.getAssignees();
            }
        }

        // get a list of all the Persons that the user is trying to unassign from the wedding
        ArrayList<Person> unassignedContacts = new ArrayList<>();

        List<Person> lastShownList = model.getFilteredPersonList();

        for (Index i : unassignedPersonIndexList) {
            if (i.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            } else {
                Person personToRemove = lastShownList.get(i.getZeroBased());
                unassignedContacts.add(personToRemove);
            }
        }

        for (Person person : unassignedContacts) {
            if (!existingPersonsInWedding.contains(person.getId())) {
                throw new CommandException(person.getName().toString() + " has not been assigned to this wedding.");
            } else {
                existingPersonsInWedding.remove(person.getId());
            }
        }

        Wedding newWedding = new Wedding(weddingToModify.getWeddingName(),
                weddingToModify.getWeddingDate(), existingPersonsInWedding);

        model.setWedding(weddingToModify, newWedding);

        String unassignedPersonNames = parsePersonListToString(unassignedContacts);

        PersonInWeddingPredicate predicate = new PersonInWeddingPredicate(newWedding);

        model.updateFilteredPersonList(predicate);

        return new CommandResult(String.format(MESSAGE_UNASSIGN_FROM_WEDDING_SUCCESS,
                weddingToModify.getWeddingName().toString(), unassignedPersonNames));


    }


}

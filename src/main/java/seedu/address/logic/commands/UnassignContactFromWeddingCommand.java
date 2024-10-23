package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.ParserUtil.parsePersonListToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.wedding.Wedding;

/**
 * This UnassignContactFromWeddingCommand class unassigns contacts in the addressbook
 * from the wedding at the specified index.
 */
public class UnassignContactFromWeddingCommand extends Command {

    public static final String COMMAND_WORD = "unassign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unassigns contacts from a specific wedding "
            + "where the wedding and contacts are identified by their index number. \n"
            + "Parameters: unassign WeddingIndex (must be a positive integer) "
            + PREFIX_CONTACT + "(specify at least 1 person index to assign)... \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_CONTACT + "1 2 3";

    public static final String MESSAGE_UNASSIGN_FROM_WEDDING_SUCCESS =
            "Unssigned the following people from %1$s's wedding: %2$s";


    private final Index specificWeddingIndex;

    private final Set<Index> unassignedPersonIndexList;


    /**
     * This constructor initialises the specific wedding index and the
     * index's of the contacts to unassign from that wedding
     * @param specificWeddingIndex
     * @param unassignedPersonIndexList
     */
    public UnassignContactFromWeddingCommand(Index specificWeddingIndex, Set<Index> unassignedPersonIndexList) {
        this.specificWeddingIndex = specificWeddingIndex;
        this.unassignedPersonIndexList = unassignedPersonIndexList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Wedding> lastShownWeddingList = model.getFilteredWeddingList();

        if (specificWeddingIndex.getZeroBased() >= lastShownWeddingList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX);
        }


        Wedding weddingToModify = lastShownWeddingList.get(specificWeddingIndex.getZeroBased());

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

        List<PersonId> existingPersonsInWedding = weddingToModify.getAssignees();

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

        return new CommandResult(String.format(MESSAGE_UNASSIGN_FROM_WEDDING_SUCCESS,
                weddingToModify.getWeddingName().toString(), unassignedPersonNames));


    }


}

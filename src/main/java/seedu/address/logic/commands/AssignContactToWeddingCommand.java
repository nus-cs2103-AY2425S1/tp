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
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.wedding.Wedding;


/**
 * This AssignContactToWeddingCommand class assigns contacts in the addressbook into the wedding at the specified index.
 */

public class AssignContactToWeddingCommand extends Command {

    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns contacts to a specific wedding "
            + "where the wedding and contacts are identified by their index number. \n"
            + "Parameters: assign WeddingIndex (must be a positive integer) "
            + PREFIX_CONTACT + "(specify at least 1 person index to assign)... \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_CONTACT + "1 2 3";

    public static final String MESSAGE_ASSIGN_TO_WEDDING_SUCCESS =
            "Assigned the following people to %1$s's wedding: %2$s";

    private final Index targetWeddingIndex;
    private final Set<Index> assignedPersonIndexList;

    /**
     * This constructor initialises the target wedding index and the assigned person index list based on the parser.
     * @param targetWeddingIndex
     * @param assignedPersonIndexList
     */
    public AssignContactToWeddingCommand(Index targetWeddingIndex, Set<Index> assignedPersonIndexList) {
        this.targetWeddingIndex = targetWeddingIndex;
        this.assignedPersonIndexList = assignedPersonIndexList;
    }


    /**
     * This method assigns new contacts to specified weddings, throwing errors whenever necessary.
     * @param model {@code Model} which the command should operate on.
     * @return CommandResult
     * @throws CommandException
     */

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Wedding> lastShownWeddingList = model.getFilteredWeddingList();

        if (targetWeddingIndex.getZeroBased() >= lastShownWeddingList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX);
        }


        Wedding weddingToModify = lastShownWeddingList.get(targetWeddingIndex.getZeroBased());

        // get a list of all the Persons that the user is trying to assign to the wedding
        ArrayList<Person> newContactsAssignedToWedding = new ArrayList<>();

        List<Person> lastShownList = model.getFilteredPersonList();

        for (Index i : assignedPersonIndexList) {
            if (i.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX +
                        " " + Messages.MESSAGE_TRY_PERSON_LIST_MODE);
            } else {
                Person personToAdd = lastShownList.get(i.getZeroBased());
                newContactsAssignedToWedding.add(personToAdd);
            }
        }

        List<PersonId> existingPersonsInWedding = weddingToModify.getAssignees();

        for (Person person : newContactsAssignedToWedding) {
            if (existingPersonsInWedding.contains(person.getId())) {
                throw new CommandException(person.getName().toString() + " has already been assigned to this wedding.");
            } else {
                existingPersonsInWedding.add(person.getId());
            }
        }

        Wedding newWedding = new Wedding(weddingToModify.getWeddingName(),
                weddingToModify.getWeddingDate(), existingPersonsInWedding);

        model.setWedding(weddingToModify, newWedding);

        String assignedPersonNames = parsePersonListToString(newContactsAssignedToWedding);

        return new CommandResult(String.format(MESSAGE_ASSIGN_TO_WEDDING_SUCCESS,
                weddingToModify.getWeddingName().toString(), assignedPersonNames));

    }
}

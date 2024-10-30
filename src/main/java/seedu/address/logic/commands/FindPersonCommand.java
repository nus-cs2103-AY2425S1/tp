package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.function.Predicate;

import seedu.address.logic.Messages;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds the person from the list of patients
 */
public class FindPersonCommand extends FindCommand<Person> {

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " person : Finds all persons matching the following parameters.\n"
            + "To specify name, use the parameter n/[NAME] where [NAME] is a list of person names, non-case-sensitive,"
            + "separated by spaces.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME\n"
            + "Example: " + COMMAND_WORD + " " + ParserUtil.PERSON_ENTITY_STRING + " n/alice bob";

    public FindPersonCommand(Predicate<Person> predicate) {
        super(predicate);
    }

    /**
     * Finds all persons with names matching the predicate in the address book to the user.
     *
     * @param model {@code Model} which the FindPersonCommand should operate on.
     * */
    @Override
    protected void findEntity(Model model) {
        model.updateFilteredPersonList(this.predicate);
    }

    /**
     * Returns the predicate to be used for finding the person.
     *
     * @return the predicate to be used for finding the person.
     */
    public Predicate<Person> getPredicate() {
        return predicate;
    }

    /**
     * Returns the message to be displayed after finding the person.
     *
     * @return the message to be displayed after finding all persons.
     */
    @Override
    public String getSuccessMessage(Model model) {
        return String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof FindPersonCommand otherFindPersonCommand)) {
            return false;
        }
        return predicate.equals(otherFindPersonCommand.predicate);
    }
}

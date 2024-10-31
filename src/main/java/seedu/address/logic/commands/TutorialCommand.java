package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.AttendanceStatus;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tutorial;

/**
 * Abstract command to manage commands that interact with the attendance of
 * tutorials of existing persns in the address book.
 */
public abstract class TutorialCommand extends Command {
    protected final Index index;
    protected final List<Tutorial> tutorials;

    /**
     * @param index of the person in the display list
     * @param tutorial single tutorial to change
     */
    public TutorialCommand(Index index, Tutorial tutorial) {
        this.index = index;
        this.tutorials = List.of(tutorial);
    }

    /**
     * Constructor for marking multiple tutorials.
     * @param index of the person in the display list
     * @param tutorials list of tutorials to change
     */
    public TutorialCommand(Index index, List<Tutorial> tutorials) {
        this.index = index;
        this.tutorials = tutorials.stream().distinct().collect(Collectors.toList());
    }

    protected abstract AttendanceStatus getStatus();
    protected abstract String getUnnecessaryMessage();
    protected abstract String generateSuccessMessage(Person person, String tutorials);

    /**
     * Takes a person and resets the specified tutorial(s) and returns that person with the new tutorials.
     * @param personToEdit The person whose attendance will be changed.
     */
    private Pair<Person, String> generatePerson(Person personToEdit)
            throws CommandException {
        Map<Tutorial, AttendanceStatus> newTutorials = new LinkedHashMap<>(personToEdit.getTutorials());
        List<String> affectedTutorials = new ArrayList<>();
        List<String> unnecessaryTutorials = new ArrayList<>();

        for (Tutorial tutorial : tutorials) {
            if (newTutorials.get(tutorial) == getStatus()) {
                unnecessaryTutorials.add(tutorial.tutorial);
                continue;
            }
            newTutorials.put(tutorial, getStatus());
            affectedTutorials.add(tutorial.tutorial);
        }

        String message = affectedTutorials.isEmpty()
                ? String.format(
                        getUnnecessaryMessage(),
                    personToEdit.getName(),
                    String.join(", ", unnecessaryTutorials)
                )
                : generateSuccessMessage(personToEdit, String.join(", ", affectedTutorials));

        return new Pair<>(
                new Person(
                        personToEdit.getName(),
                        personToEdit.getStudentId(),
                        personToEdit.getPhone(),
                        personToEdit.getEmail(),
                        personToEdit.getTags(),
                        newTutorials
                ), message);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> currDisplayedList = model.getFilteredPersonList();
        List<Person> personToEditList = CommandUtil.filterPersonsByIndex(currDisplayedList, index);
        List<Person> editedPersonList = new ArrayList<>();
        String finalSuccessMessage = "";
        for (Person personToEdit : personToEditList) {
            Pair<Person, String> editedPair = this.generatePerson(personToEdit);
            Person editedPerson = editedPair.getKey();
            String formattedTutorials = editedPair.getValue();
            editedPersonList.add(editedPerson);
            model.setPerson(personToEdit, editedPerson);
            String successMessage = formattedTutorials + "\n";
            finalSuccessMessage += successMessage;
        }
        finalSuccessMessage = finalSuccessMessage.trim();
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(finalSuccessMessage);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TutorialCommand)) {
            return false;
        }

        // state check
        TutorialCommand e = (TutorialCommand) other;
        return index.equals(e.index)
                && tutorials.equals(e.tutorials);
    }
}

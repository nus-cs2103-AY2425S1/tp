package tuteez.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import tuteez.commons.core.index.Index;
import tuteez.logic.commands.exceptions.CommandException;
import tuteez.model.Model;
import tuteez.model.person.Person;
import tuteez.model.person.lesson.Lesson;

/**
 * Adds a lesson to a specified student.
 */
public class AddLessonCommand extends LessonCommand {
    public static final String ADD_LESSON_PARAM = "-a";

    private final Lesson lessonToAdd;

    /**
     * Adds a Lesson to the student with the specified {@code personIndex} of the displayed list.
     * @param personIndex The Index of the student in the displayed list to add the lesson to
     * @param lessonToAdd Lesson to be added
     */
    public AddLessonCommand(Index personIndex, Lesson lessonToAdd) {
        super(personIndex);
        this.lessonToAdd = lessonToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToUpdate = getPersonFromModel(model);
        Map<Person, ArrayList<Lesson>> clashingLessons = model.getClashingLessons(lessonToAdd);
        if (!clashingLessons.isEmpty()) {
            StringBuilder sb = new StringBuilder(AddCommand.MESSAGE_CLASHING_LESSON).append("\n");
            clashingLessons.forEach((student, lessons) -> {
                sb.append(student.getName()).append(": ");
                lessons.forEach(ls -> sb.append(ls.getDayAndTime()).append(" "));
                sb.append("\n");
            });
            throw new CommandException(sb.toString());
        }
        Person updatedPerson = addLessonToPerson(personToUpdate);
        model.setPerson(personToUpdate, updatedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format("Added lesson to %1$s: %2$s", personToUpdate.getName(),
                lessonToAdd.toString()));
    }

    private Person addLessonToPerson(Person person) {
        assert person != null;

        Set<Lesson> updatedLessons = new HashSet<>(person.getLessons());
        updatedLessons.add(lessonToAdd);

        return new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                person.getTelegramUsername(), person.getTags(), updatedLessons, person.getRemarkList());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddLessonCommand)) {
            return false;
        }

        AddLessonCommand otherAddLessonCommand = (AddLessonCommand) other;
        return personIndex.equals(otherAddLessonCommand.personIndex)
                && lessonToAdd.equals(otherAddLessonCommand.lessonToAdd);
    }

}

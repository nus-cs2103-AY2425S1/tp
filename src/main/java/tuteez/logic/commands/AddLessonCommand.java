package tuteez.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tuteez.commons.core.index.Index;
import tuteez.logic.commands.exceptions.CommandException;
import tuteez.model.Model;
import tuteez.model.person.Person;
import tuteez.model.person.lesson.Lesson;

/**
 * Adds a lesson to a specified student.
 */
public class AddLessonCommand extends LessonCommand {
    public static final String MESSAGE_SUCCESS = "Added lessons to %1$s:\n%2$s";
    public static final String MESSAGE_NEW_LESSONS_CLASH = "The following added lessons clash with one another:";
    public static final String MESSAGE_ALL_LESSONS_CLASH = "None of the lessons could be added due to clashes.";
    public static final String MESSAGE_PARTIAL_SUCCESS = "Some lessons were added successfully, but others had clashes."
            + "\n"
            + "Successfully added:\n%1$s"
            + "Failed to add:\n%2$s";

    public static final String MESSAGE_USAGE = "Add lessons by index in displayed student list: " + COMMAND_WORD_ADD
            + " (short form: " + COMMAND_WORD_ADD_ALT + ")"
            + " INDEX l/LESSON [l/LESSON]...\n"
            + "Example: " + COMMAND_WORD_ADD + " 1 l/monday 0900-1100 l/wednesday 1400-1600\n";

    private final List<Lesson> lessonsToAdd;

    /**
     * Adds a Lesson to the student with the specified {@code personIndex} of the displayed list.
     * @param personIndex The Index of the student in the displayed list to add the lesson to
     * @param lessonsToAdd Lessons to be added
     */
    public AddLessonCommand(Index personIndex, List<Lesson> lessonsToAdd) {
        super(personIndex);
        this.lessonsToAdd = lessonsToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToUpdate = getPersonFromModel(model);

        List<Lesson> internalClashes = findInternalClashes(lessonsToAdd);
        if (!internalClashes.isEmpty()) {
            throw new CommandException(MESSAGE_NEW_LESSONS_CLASH + "\n" + formatLessonList(internalClashes));
        }

        List<Lesson> successfulAdditions = new ArrayList<>();
        List<String> failureMessages = new ArrayList<>();

        for (Lesson lesson : lessonsToAdd) {
            Map<Person, ArrayList<Lesson>> clashingLessons = model.getClashingLessons(lesson);
            if (clashingLessons.isEmpty()) {
                successfulAdditions.add(lesson);
            } else {
                failureMessages.add(formatClashMessage(lesson, clashingLessons));
            }
        }

        if (successfulAdditions.isEmpty()) {
            throw new CommandException(MESSAGE_ALL_LESSONS_CLASH + "\n" + formatFailureMessages(failureMessages));
        }

        Person updatedPerson = addLessonsToPerson(personToUpdate, successfulAdditions);
        model.setPerson(personToUpdate, updatedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.updateLastViewedPerson(updatedPerson);

        String resultMessage = generateResultMessage(personToUpdate, successfulAdditions, failureMessages);
        return new CommandResult(resultMessage);
    }

    private String generateResultMessage(Person personToUpdate, List<Lesson> successfulAdditions,
                                         List<String> failureMessages) {
        if (failureMessages.isEmpty()) {
            return String.format(MESSAGE_SUCCESS,
                    personToUpdate.getName(),
                    formatLessonList(successfulAdditions));
        } else {
            return String.format(MESSAGE_PARTIAL_SUCCESS,
                    formatLessonList(successfulAdditions),
                    formatFailureMessages(failureMessages));
        }
    }

    private String formatFailureMessages(List<String> failureMessages) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < failureMessages.size(); i++) {
            sb.append(failureMessages.get(i));
            if (i < failureMessages.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private List<Lesson> findInternalClashes(List<Lesson> lessons) {
        List<Lesson> clashingLessons = new ArrayList<>();

        for (int i = 0; i < lessons.size() - 1; i++) {
            checkLessonAgainstRemaining(lessons, i, clashingLessons);
        }

        return clashingLessons;
    }

    private void checkLessonAgainstRemaining(List<Lesson> lessons, int currentIndex, List<Lesson> clashingLessons) {
        Lesson currentLesson = lessons.get(currentIndex);

        for (int j = currentIndex + 1; j < lessons.size(); j++) {
            if (!Lesson.isClashingWithOtherLesson(currentLesson, lessons.get(j))) {
                continue;
            }
            addToClashingLessonsIfAbsent(clashingLessons, currentLesson);
            addToClashingLessonsIfAbsent(clashingLessons, lessons.get(j));
        }
    }

    private void addToClashingLessonsIfAbsent(List<Lesson> clashingLessons, Lesson lesson) {
        if (!clashingLessons.contains(lesson)) {
            clashingLessons.add(lesson);
        }
    }

    private Person addLessonsToPerson(Person person, List<Lesson> lessonsToAdd) {
        assert person != null;

        List<Lesson> updatedLessons = new ArrayList<>(person.getLessons());
        updatedLessons.addAll(lessonsToAdd);

        return new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                person.getTelegramUsername(), person.getTags(), updatedLessons, person.getRemarkList());
    }

    private String formatLessonList(List<Lesson> lessons) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lessons.size(); i++) {
            sb.append(i + 1).append(". ").append(lessons.get(i).toString()).append("\n");
        }
        return sb.toString();
    }

    private String formatClashMessage(Lesson lesson, Map<Person, ArrayList<Lesson>> clashingLessons) {
        StringBuilder sb = new StringBuilder();
        sb.append(lesson.toString()).append(" - Clashes with:\n");
        clashingLessons.forEach((person, lessons) -> {
            sb.append("•").append(person.getName()).append(": ");
            lessons.forEach(ls -> sb.append(ls.toString()).append(" "));
            sb.append("\n");
        });
        return sb.toString();
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
                && lessonsToAdd.equals(otherAddLessonCommand.lessonsToAdd);
    }

}

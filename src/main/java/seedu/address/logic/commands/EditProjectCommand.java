package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROJECTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectId;
import seedu.address.model.project.ProjectName;
import seedu.address.model.skill.Skill;
import seedu.address.ui.DisplayType;

/**
 * Edits the details of an existing project in the address book.
 */
public class EditProjectCommand extends Command {

    public static final String COMMAND_WORD = "editproject";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the project identified "
            + "by the index number used in the displayed project list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_PROJECT_NAME + "NAME] "
            + "[" + PREFIX_SKILL + "SKILL]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PROJECT_NAME + "Project ALPHA "
            + PREFIX_SKILL + "Cybersecurity";

    public static final String MESSAGE_EDIT_PROJECT_SUCCESS = "Edited Project: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PROJECT = "This project already exists in the address book.";
    public static final String MESSAGE_EDIT_PROJECT_ID = "Project id cannot be edited.";

    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    private final Index index;
    private final EditProjectDescriptor editProjectDescriptor;

    /**
     * @param index                  of the project in the filtered project list
     *                               to edit
     * @param editProjectDescriptor details to edit the project with
     */
    public EditProjectCommand(Index index, EditProjectDescriptor editProjectDescriptor) {
        requireNonNull(index);
        requireNonNull(editProjectDescriptor);

        this.index = index;
        this.editProjectDescriptor = new EditProjectDescriptor(editProjectDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Project> lastShownList = model.getFilteredProjectList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        Project projectToEdit = lastShownList.get(index.getZeroBased());
        Project editedProject = createEditedProject(projectToEdit, editProjectDescriptor);

        // Cannot modify projectId via CLI; Project must have the same projectId
        // ie, projectToEdit and editedProject must still be same project
        assert projectToEdit.isSameProject(editedProject);

        model.setProject(projectToEdit, editedProject);

        model.getFilteredAssignmentList().stream()
                .filter(assignment -> assignment.getProject().isSameProject(projectToEdit))
                .forEach(assignment -> model.setAssignment(assignment, new Assignment(assignment.getAssignmentId(),
                         editedProject, assignment.getEmployee())));

        // Edited project successfully
        logger.fine(COMMAND_WORD + " project\n" + editedProject);

        model.updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(String.format(MESSAGE_EDIT_PROJECT_SUCCESS, Messages.format(editedProject)),
                DisplayType.PROJECT_LIST);
    }

    /**
     * Creates and returns a {@code Project} with the details of
     * {@code projectToEdit} edited with {@code editProjectDescriptor}.
     */
    private static Project createEditedProject(Project projectToEdit,
                                                 EditProjectDescriptor editProjectDescriptor) {
        assert projectToEdit != null;

        ProjectId projectId = projectToEdit.getId();
        ProjectName updatedName = editProjectDescriptor.getName().orElse(projectToEdit.getName());
        Set<Skill> updatedSkills = editProjectDescriptor.getSkills().orElse(projectToEdit.getSkills());

        return new Project(updatedName, projectId, updatedSkills);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditProjectCommand)) {
            return false;
        }

        EditProjectCommand otherEditProjectCommand = (EditProjectCommand) other;
        return index.equals(otherEditProjectCommand.index)
                && editProjectDescriptor.equals(otherEditProjectCommand.editProjectDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editProjectDescriptor", editProjectDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the project with. Each non-empty field value will
     * replace the corresponding field value of the project.
     */
    public static class EditProjectDescriptor {
        private ProjectName name;
        private Set<Skill> skills;

        public EditProjectDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditProjectDescriptor(EditProjectDescriptor toCopy) {
            setName(toCopy.name);
            setSkills(toCopy.skills);
        }

        /**
         * Returns true if at least one editable field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, skills);
        }

        public void setName(ProjectName name) {
            this.name = name;
        }

        public Optional<ProjectName> getName() {
            return Optional.ofNullable(name);
        }

        /**
         * Sets {@code skills} to this object's {@code skills}.
         * A defensive copy of {@code skills} is used internally.
         */
        public void setSkills(Set<Skill> skills) {
            this.skills = (skills != null) ? new HashSet<>(skills) : null;
        }

        /**
         * Returns an unmodifiable skill set, which throws
         * {@code UnsupportedOperationException} if modification is attempted.
         * Returns {@code Optional#empty()} if {@code skills} is null.
         */
        public Optional<Set<Skill>> getSkills() {
            return (skills != null) ? Optional.of(Collections.unmodifiableSet(skills)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditProjectDescriptor)) {
                return false;
            }

            EditProjectDescriptor otherEditProjectDescriptor = (EditProjectDescriptor) other;
            return Objects.equals(name, otherEditProjectDescriptor.name)
                    && Objects.equals(skills, otherEditProjectDescriptor.skills);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("skills", skills)
                    .toString();
        }
    }
}

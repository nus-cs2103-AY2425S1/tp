package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectId;
import seedu.address.model.project.ProjectName;
import seedu.address.model.skill.Skill;

/**
 * Jackson-friendly version of {@link Project}.
 */
class JsonAdaptedProject {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Project's %s field is missing!";

    private final String name;
    private final String id;
    private final List<JsonAdaptedSkill> skills = new ArrayList<>();
    /**
     * Constructs a {@code JsonAdaptedProject} with the given project details.
     */
    @JsonCreator
    public JsonAdaptedProject(@JsonProperty("name") String name,
                              @JsonProperty("id") String id,
                              @JsonProperty("skills") List<JsonAdaptedSkill> skills) {
        this.name = name;
        this.id = id;
        if (skills != null) {
            this.skills.addAll(skills);
        }
    }

    /**
     * Converts a given {@code Project} into this class for Jackson use.
     */
    public JsonAdaptedProject(Project source) {
        name = source.getName().fullName;
        id = source.getId().fullId;
        skills.addAll(source.getSkills().stream()
                .map(JsonAdaptedSkill::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Project toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ProjectName.class.getSimpleName()));
        }
        if (!ProjectName.isValidName(name)) {
            throw new IllegalValueException(ProjectName.MESSAGE_CONSTRAINTS);
        }
        final ProjectName modelProjectName = new ProjectName(name);

        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ProjectId.class.getSimpleName()));
        }
        if (!ProjectId.isValidId(id)) {
            throw new IllegalValueException(ProjectId.MESSAGE_CONSTRAINTS);
        }
        final ProjectId modelProjectId = new ProjectId(id);
        final List<Skill> projectSkills = new ArrayList<>();
        for (JsonAdaptedSkill skill : skills) {
            projectSkills.add(skill.toModelType());
        }
        final Set<Skill> modelSkills = new HashSet<>(projectSkills);

        return new Project(modelProjectName, modelProjectId, modelSkills);
    }

}

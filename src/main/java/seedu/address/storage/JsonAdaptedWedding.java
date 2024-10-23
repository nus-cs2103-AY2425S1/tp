package seedu.address.storage;

import static seedu.address.logic.parser.ParserUtil.parseWeddingDate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.PersonId;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingName;

/**
 * Jackson-friendly version of {@link Wedding}.
 */
public class JsonAdaptedWedding {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Wedding's %s field is missing!";
    public static final String NAME_FIELD = "Name";
    public static final String DATE_FIELD = "Date";

    private final String name;
    private final String dateString;

    private final List<JsonAdaptedPersonId> assignees = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedWedding} with the given wedding details.
     */
    @JsonCreator
    public JsonAdaptedWedding(@JsonProperty("name") String name,
                              @JsonProperty("dateString") String date,
                              @JsonProperty("assignees") List<JsonAdaptedPersonId> assignees) {
        this.name = name;
        this.dateString = date;
        if (assignees != null) {
            this.assignees.addAll(assignees);
        }
    }

    /**
     * Converts a given {@code Wedding} into this class for Jackson use.
     */
    public JsonAdaptedWedding(Wedding source) {
        name = source.getWeddingName().fullName;
        dateString = source.getWeddingDate().toString();

        assignees.addAll(source.getAssignees().stream()
                .map(JsonAdaptedPersonId::new)
                .collect(Collectors.toList()));
    }


    /**
     * Converts this Jackson-friendly adapted wedding object into the model's {@code Wedding} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Wedding toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, NAME_FIELD));
        }
        final WeddingName modelName = new WeddingName(name);

        if (dateString == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, DATE_FIELD));
        }
        final WeddingDate modelDate = parseWeddingDate(dateString);

        final List<PersonId> modelAssignees = new ArrayList<>();
        for (JsonAdaptedPersonId assignee : assignees) {
            modelAssignees.add(assignee.toModelType());
        }
        if (modelAssignees.isEmpty()) {
            return new Wedding(modelName, modelDate);
        } else {
            return new Wedding(modelName, modelDate, modelAssignees);
        }
    }

}

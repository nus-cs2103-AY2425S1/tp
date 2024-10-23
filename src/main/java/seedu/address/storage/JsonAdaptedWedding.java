package seedu.address.storage;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.PersonId;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingName;

/**
 * Jackson-friendly version of {@link Wedding}.
 */
public class JsonAdaptedWedding {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Wedding's %s field is missing!";

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
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        name = source.getWeddingName().fullName;
        dateString = source.getWeddingDate().fullDate.format(formatter);

        assignees.addAll(source.getAssignees().stream()
                .map(JsonAdaptedPersonId::new)
                .collect(Collectors.toList()));
    }


    /**
     * Converts this Jackson-friendly adapted wedding object into the model's {@code Wedding} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted wedding.
     */
    public Wedding toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    WeddingName.class.getSimpleName()));
        }
        if (!WeddingName.isValidWeddingName(name)) {
            throw new IllegalValueException(WeddingName.MESSAGE_CONSTRAINTS);
        }
        final WeddingName modelName = new WeddingName(name);

        if (dateString == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    WeddingDate.class.getSimpleName()));
        }
        if (!WeddingDate.isValidWeddingDate(name)) {
            throw new IllegalValueException(WeddingDate.MESSAGE_CONSTRAINTS);
        }
        final WeddingDate modelDate = ParserUtil.parseWeddingDate(dateString);

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

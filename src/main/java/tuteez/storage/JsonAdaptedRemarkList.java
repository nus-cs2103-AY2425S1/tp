package tuteez.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tuteez.commons.exceptions.IllegalValueException;
import tuteez.model.remark.Remark;
import tuteez.model.remark.RemarkList;

/**
 * Jackson-friendly version of {@link RemarkList}.
 */
public class JsonAdaptedRemarkList {
    private final List<String> remarks;

    /**
     * Constructs a {@code JsonAdaptedRemarkList} with the given {@code remarks}.
     */
    @JsonCreator
    public JsonAdaptedRemarkList(@JsonProperty("remarks") List<String> remarks) {
        this.remarks = remarks != null ? remarks : new ArrayList<>();
    }

    /**
     * Constructs a {@code JsonAdaptedRemarkList} with the given {@code remarkList}.
     */
    public JsonAdaptedRemarkList(RemarkList remarkList) {
        this.remarks = remarkList.getRemarks().stream()
                .map(Remark::toString)
                .collect(Collectors.toList());
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted remark list.
     */
    public RemarkList toModelType() throws IllegalValueException {
        List<Remark> modelRemarks = new ArrayList<>();
        for (String remarkStr : remarks) {
            modelRemarks.add(new Remark(remarkStr));
        }
        return new RemarkList(modelRemarks);
    }
}

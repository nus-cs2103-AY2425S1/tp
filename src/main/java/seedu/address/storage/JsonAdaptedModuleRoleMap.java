package seedu.address.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.ModuleCode;
import seedu.address.model.person.ModuleRoleMap;
import seedu.address.model.person.RoleType;

/**
 * Jackson-friendly version of {@link ModuleRoleMap}.
 */
public class JsonAdaptedModuleRoleMap {
    private final Map<String, String> moduleRoleMap;

    /**
     * Constructs a {@code JsonAdaptedModuleRoleMap} from the given {@code moduleRoleMap} in Json format.
     */
    @JsonCreator
    public JsonAdaptedModuleRoleMap(Map<String, String> moduleRoleMap) {
        this.moduleRoleMap = moduleRoleMap;
    }

    /**
     * Converts a given {@code ModuleRoleMap} into this class for Jackson use.
     */
    public JsonAdaptedModuleRoleMap(ModuleRoleMap moduleRoleMap) {
        this.moduleRoleMap =
                moduleRoleMap.getRoles().entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey().toString(), entry -> entry.getValue().toString()));
    }

    /**
     * Converts a given {@code ModuleRoleMap} into the Json format {@code String}.
     */
    private String toJsonString() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jacksonData = objectMapper.writeValueAsString(this.moduleRoleMap);

        return jacksonData;
    }

    /**
     * Serializes the JsonAdaptedModuleRoleMap to JSON format.
     */
    @JsonValue
    public Map<String, String> getModuleRoleMapJsonString() {
        return moduleRoleMap;
    }

    /**
     * Converts this Jackson-friendly adapted moduleRoleMap object into the model's {@code ModuleRoleMap} object.
     */
    public ModuleRoleMap toModelType() throws IllegalValueException {
        HashMap<ModuleCode, RoleType> map = new HashMap<>();
        for (String key : moduleRoleMap.keySet()) {
            map.put(new ModuleCode(key), ParserUtil.parseRoleType(moduleRoleMap.get(key)));
        }

        return new ModuleRoleMap(map);
    }
}

package seedu.address.ui;

import javafx.scene.control.Label;
import seedu.address.model.game.Game;
import seedu.address.model.game.Role;
import seedu.address.model.game.SkillLevel;
import seedu.address.model.game.Username;

public class GameLabelGenerator {
    public static Label gameLabel(Game game) {
        StringBuilder sb = new StringBuilder();
        Username username = game.getUsername();
        SkillLevel skillLevel = game.getSkillLevel();
        Role role = game.getRole();
        sb.append(game.getGameName()).append("\n");
        if (username != null) {
            sb.append("Username: ").append(game.getUsername()).append("\n");
        }
        if (skillLevel !=null) {
            sb.append("Skill Lvl: ").append(game.getSkillLevel()).append("\n");
        }
        if (role != null) {
            sb.append("Role: ").append(game.getRole());
        }
        return new Label(sb.toString());
    }
}

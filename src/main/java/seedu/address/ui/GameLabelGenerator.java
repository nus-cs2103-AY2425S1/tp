package seedu.address.ui;

import javafx.scene.control.Label;
import seedu.address.model.game.Game;

public class GameLabelGenerator {
    public static Label gameLabel(Game game) {
        StringBuilder sb = new StringBuilder();
        sb.append("").append(game.getGameName()).append("\n")
          .append("Username: ").append(game.getUsername()).append("\n")
                .append("Skill Lvl: ").append(game.getSkillLevel()).append("\n")
                .append("Role: ").append(game.getRole());
        return new Label(sb.toString());
    }
}

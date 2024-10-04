package seedu.address.ui;

import javafx.scene.layout.HBox;

public class MainWindowPanel extends HBox {
    public MainWindowPanel(PersonListPanel personListPanel, RightPanel rightPanel) {
        this.getChildren().addAll(personListPanel.getRoot(), rightPanel.getRoot());
        this.setSpacing(10);
    }
}

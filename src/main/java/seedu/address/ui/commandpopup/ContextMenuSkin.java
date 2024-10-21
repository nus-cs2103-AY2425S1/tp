package seedu.address.ui.commandpopup;

import com.sun.javafx.scene.control.ContextMenuContent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ContextMenuSkin extends ContextMenu {
    private ContextMenuContent.MenuItemContainer itemSelected=null;
    public void start(Stage primaryStage) {

        MenuItem cmItem1 = new MenuItem("Item 1");
        cmItem1.setOnAction(e->System.out.println("Item 1"));
        MenuItem cmItem2 = new MenuItem("Item 2");
        cmItem2.setOnAction(e->System.out.println("Item 2"));

        final ContextMenu cm = new ContextMenu(cmItem1,cmItem2);

        Scene scene = new Scene(new StackPane(), 300, 250);
        scene.setOnMouseClicked(t -> {
            if(t.getButton()== MouseButton.SECONDARY || t.isControlDown()){
                cm.show(scene.getWindow(),t.getScreenX(),t.getScreenY());

                ContextMenuContent cmc= (ContextMenuContent)cm.getSkin().getNode();

                cmc.setOnKeyPressed(ke->{
                    switch (ke.getCode()) {
                    case UP:    break;
                    case DOWN:  break;
                    case TAB:   ke.consume();
                        if(itemSelected!=null){
                            itemSelected.getItem().fire();
                        }
                        cm.hide();
                        break;
                    default: break;
                    }
                });
                VBox itemsContainer = cmc.getItemsContainer();
                itemsContainer.getChildren().forEach(n->{
                    ContextMenuContent.MenuItemContainer item=(ContextMenuContent.MenuItemContainer)n;
                    item.focusedProperty().addListener((obs,b,b1)->{
                        if(b1){
                            itemSelected=item;
                        }
                    });
                });
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

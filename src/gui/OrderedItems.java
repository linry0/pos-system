package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import menu.Item;

import java.util.ArrayList;
import java.util.Collection;

public class OrderedItems {
    private VBox vBox;
    private ToggleGroup toggleGroup;

    public OrderedItems() {
        vBox = new VBox();
        vBox.setSpacing(Constants.VBOX_SPACING);
        vBox.setPrefSize(Constants.VBOX_LIST_WIDTH, Constants.VBOX_LIST_HEIGHT);
        toggleGroup = new ToggleGroup();
    }

    public VBox getVBox() {
        return vBox;
    }

    public void add(Item item) {
                    Label label1 = new Label(item.getName());
                    Label label2 = new Label(Integer.toString(item.getPrice()));
                HBox hBox = new HBox();
                hBox.setSpacing(Constants.HBOX_SPACING);
                hBox.getChildren().addAll(label1, label2);
            RadioButton radioButton = new RadioButton();
            radioButton.setUserData(item);
            radioButton.setGraphic(hBox);
            radioButton.setToggleGroup(toggleGroup);
        vBox.getChildren().add(radioButton);
    }

    public void addAll(Collection<Item> items) {
        for (Item item : items) {
            add(item);
        }
    }

    public ArrayList<Item> getAll() {
        ArrayList<Item> arrayList = new ArrayList<>();

        for (Node node : vBox.getChildren()) {
            if (node instanceof RadioButton radioButton) {
                Item item = (Item) radioButton.getUserData();
                arrayList.add(item);
            }
        }

        return arrayList;
    }

    public void removeSelected() {
        vBox.getChildren().remove((RadioButton) toggleGroup.getSelectedToggle());
    }

    public Button buttonItemAdd(Item item) {
        Button button = new Button(item.getName());
        EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                add(item);
            }
        };
        button.setOnAction(eventHandler);

        return button;
    }

    public Button buttonItemRemove(String label) {
        Button button = new Button(label);
        EventHandler<ActionEvent> eventHandler = actionEvent -> {
            removeSelected();
        };
        button.setOnAction(eventHandler);

        return button;
    }
}

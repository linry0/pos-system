package gui;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import menu.Order;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public class SavedOrders {
    private final ArrayList<Order> orders;
    private Order selected;

    public SavedOrders() {
        this.orders = new ArrayList<>();
        this.selected = null;
    }

    public VBox getVBox() {
        VBox vBox = new VBox();
        vBox.setSpacing(Constants.VBOX_SPACING);
        vBox.setPrefSize(Constants.VBOX_LIST_WIDTH, Constants.VBOX_LIST_HEIGHT);

        selected = null;
        ToggleGroup toggleGroup = new ToggleGroup();

        for (Order order : orders) {
            RadioButton radioButton = new RadioButton();
            radioButton.setUserData(order);
                ZonedDateTime zonedDateTime = ((Order) radioButton.getUserData()).getZonedDateTime();
                String string = String.format("%02d:%02d:%02d",zonedDateTime.getHour(), zonedDateTime.getMinute(), zonedDateTime.getSecond());
            radioButton.setText(string);
            radioButton.setOnAction(actionEvent -> {
                selected = (Order) radioButton.getUserData();
            });

            vBox.getChildren().add(radioButton);
            radioButton.setToggleGroup(toggleGroup);
        }

        return vBox;
    }

    public Order getSelected() {
        return selected;
    }

    public void resetSelected() {
        selected = new Order();
    }

    public void removeSelected() {
        orders.remove(selected);
        resetSelected();
    }

    public void add(Order order) {
        orders.add(order);
    }
}

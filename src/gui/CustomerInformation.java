package gui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CustomerInformation {
    private final VBox vBox;

    public CustomerInformation() {
        vBox = new VBox();
        vBox.setSpacing(Constants.VBOX_SPACING);
        vBox.setPrefSize(Constants.VBOX_LIST_WIDTH * 1.5, Constants.VBOX_LIST_HEIGHT);
            String[] fields = {"Name", "Telephone", "Postcode", "House No.", "Address", "Notes"};
            for (String field : fields) {
                    HBox hBox = new HBox(Constants.HBOX_SPACING);
                        Label label = new Label(field);
                        TextField textField = new TextField();
                    hBox.getChildren().addAll(label, textField);
                vBox.getChildren().add(hBox);
            }
    }

    public VBox getVBox() {
        return vBox;
    }

    public String getName() {
        return ((TextInputControl) ((HBox) vBox.getChildren().get(0)).getChildren().getLast()).getText();
    }
    public String getTelephone() {
        return ((TextInputControl) ((HBox) vBox.getChildren().get(1)).getChildren().getLast()).getText();
    }
    public String getPostcode() {
        return ((TextInputControl) ((HBox) vBox.getChildren().get(2)).getChildren().getLast()).getText();
    }
    public String getAddress1() {
        return ((TextInputControl) ((HBox) vBox.getChildren().get(3)).getChildren().getLast()).getText();
    }
    public String getAddress2() {
        return ((TextInputControl) ((HBox) vBox.getChildren().get(4)).getChildren().getLast()).getText();
    }
    public String getNotes() {
        return ((TextInputControl) ((HBox) vBox.getChildren().get(5)).getChildren().getLast()).getText();
    }

    public void setName(String string) {
        ((TextInputControl) ((HBox) vBox.getChildren().get(0)).getChildren().getLast()).setText(string);
    }
    public void setTelephone(String string) {
        ((TextInputControl) ((HBox) vBox.getChildren().get(1)).getChildren().getLast()).setText(string);
    }
    public void setPostcode(String string) {
        ((TextInputControl) ((HBox) vBox.getChildren().get(2)).getChildren().getLast()).setText(string);
    }
    public void setAddress1(String string) {
        ((TextInputControl) ((HBox) vBox.getChildren().get(3)).getChildren().getLast()).setText(string);
    }
    public void setAddress2(String string) {
        ((TextInputControl) ((HBox) vBox.getChildren().get(4)).getChildren().getLast()).setText(string);
    }
    public void setNotes(String string) {
        ((TextInputControl) ((HBox) vBox.getChildren().get(5)).getChildren().getLast()).setText(string);
    }
}

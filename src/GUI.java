import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// add to run configurations vm options (its hidden by default)
// --module-path /Users/linry/Documents/fortune-garden/javafx-sdk-22.0.1/lib --add-modules javafx.controls,javafx.fxml

//TODO add functionality to load orders from orders.tsv (with load confirmation success popup)
//TODO move Printer from computer package to root directory and rename CLI
//TODO formulate plan to integrate classes from computer package into GUI (Menu, Category, Item, Reader)
public class GUI extends Application implements Constants {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("screen0: fullscreen");
        primaryStage.setScene(sceneMain(primaryStage));
        primaryStage.setFullScreen(DEFAULT_FULLSCREEN);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            stageClose(primaryStage).showAndWait();
        });
    }

    public Scene sceneMain(Stage stage) {
        VBox vBox = new VBox(VBOX_SPACING);
            Label label = new Label("Welcome to the fortune-garden computer!");
            HBox hBox = new HBox(HBOX_SPACING);
                    VBox vBoxOrders = new VBox(VBOX_SPACING);
                        vBoxOrders.setPrefSize(VBOX_WIDTH, VBOX_HEIGHT);
                ScrollPane scrollPaneItems = scrollPaneItems(vBoxOrders);
                ScrollPane scrollPaneOrders = new ScrollPane(vBoxOrders);
                hBox.getChildren().addAll(scrollPaneItems, scrollPaneOrders);
        vBox.getChildren().addAll(label, hBox, hBoxStageControls(stage, scrollPaneOrders));

        return new Scene(vBox, 600, 600);
    }

    public Button buttonSave(Stage stage, ScrollPane scrollPaneOrders) {
        Button button = new Button("SAVE");
            EventHandler<ActionEvent> eventHandler = actionEvent -> {
                List<String> orders = new ArrayList<>();

                Node nodeVBox = scrollPaneOrders.getContent();
                if (nodeVBox instanceof VBox vBox) {
                    for (Node nodeLabel : vBox.getChildren()) {
                        if (nodeLabel instanceof Label label) {
                            orders.add(label.getText());
                        }
                    }
                }

                //TODO is there a better way to save the file?
                try {
                    Files.write(Paths.get("src/orders.tsv"), orders);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                stageSave(stage).show();
            };
            button.setOnAction(eventHandler);

        return button;
    }

    public Button buttonRestart(Stage stage) {
        Button button = new Button("RESTART");
            EventHandler<ActionEvent> eventHandler = actionEvent -> {
                //stage.hide(); needs Platform.setImplicitExit(false) (in start() preferably) to not close entire programme when no more stages are visible (Platform.exit() to actually exit the programme in that case)
                stage.setScene(sceneMain(stage));
                stage.setFullScreen(DEFAULT_FULLSCREEN);
                //stage.show(); needs the entire programme not to be closed to show the stage
            };
            button.setOnAction(eventHandler);

        return button;
    }

    public Button buttonClose(Stage stage) {
        Button button = new Button("CLOSE");
            EventHandler<ActionEvent> eventHandler = actionEvent -> {
                WindowEvent windowEvent = new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST);
                stage.fireEvent(windowEvent);
            };
        button.setOnAction(eventHandler);

        return button;
    }

    public Button buttonAddItem(String name, Pane pane) {
        Button button = new Button(name);
            Label label = new Label(name); //deprecated in favour of adding a new label instance each button press since adding duplicate items to panes is not allowed
            EventHandler<ActionEvent> eventHandler = actionEvent -> {
                pane.getChildren().add(new Label(name)); //adding a new label instance
            };
        button.setOnAction(eventHandler);

        return button;
    }

    public ScrollPane scrollPaneItems(Pane itemDestination) {
        ScrollPane scrollPane = new ScrollPane();
            VBox vBox = new VBox(10);
            vBox.setPrefSize(VBOX_WIDTH, VBOX_HEIGHT);
                Button button0 = buttonAddItem("Starters", itemDestination);
                Button button1 = buttonAddItem("Beef Dishes", itemDestination);
                Button button2 = buttonAddItem("Chicken Dishes", itemDestination);
                Button button3 = buttonAddItem("Pork Dishes", itemDestination);
                Button button4 = buttonAddItem("Rice Dishes", itemDestination);
                Button button5 = buttonAddItem("Noodle Dishes", itemDestination);
                Button button6 = buttonAddItem("Drinks", itemDestination);
                Button button7 = buttonAddItem("Box Meals", itemDestination);
            vBox.getChildren().addAll(button0, button1, button2, button3, button4, button5, button6, button7);
        scrollPane.setContent(vBox);

        return scrollPane;
    }

    public HBox hBoxStageControls(Stage stage, ScrollPane scrollPaneOrders) {
        HBox hBox = new HBox(HBOX_SPACING);
            Button buttonSave = buttonSave(stage, scrollPaneOrders);
            Button buttonRestart = buttonRestart(stage);
            Button buttonClose = buttonClose(stage);
            hBox.getChildren().addAll(buttonSave, buttonRestart, buttonClose);

        return hBox;
    }

    public Stage stageSave(Stage stageOwner) {
        Stage stage = new Stage();
        stage.initOwner(stageOwner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.DECORATED);

        stage.setX(STAGE_X); stage.setY(STAGE_Y);
        stage.setWidth(STAGE_WIDTH); stage.setHeight(STAGE_HEIGHT);
        stage.setTitle("Save Confirmation");
            VBox vBox = new VBox(VBOX_SPACING);
                Label label = new Label("Orders have been successfully saved to `src/orders.tsv`.");
                Button buttonClose = buttonClose(stage);
                vBox.getChildren().addAll(label, buttonClose);
            Scene scene = new Scene(vBox);
        stage.setScene(scene);

        return stage;
    }

    public Stage stageClose(Stage stageOwner) {
        Stage stage = new Stage();
        stage.initOwner(stageOwner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.DECORATED);

        stage.setX(STAGE_X); stage.setY(STAGE_Y);
        stage.setWidth(STAGE_WIDTH); stage.setHeight(STAGE_HEIGHT);
        stage.setTitle("WARNING");
            VBox vBox = new VBox(VBOX_SPACING);
                Label label = new Label("YOUR COMPUTER HAS VIRUS");
                Button buttonMiscellaneous = new Button("DOWNLOAD FREE ANTIVIRUS");
                    buttonMiscellaneous.setOnAction(event -> stageClose(stage).showAndWait());
                Button buttonClose = buttonClose(stage);
                vBox.getChildren().addAll(label, buttonMiscellaneous, buttonClose);
            Scene scene = new Scene(vBox, STAGE_WIDTH, STAGE_HEIGHT);
        stage.setScene(scene);

        return stage;
    }
}

//stage.initModality(Modality.APPLICATION_MODAL);
//stage.initModality(Modality.WINDOW_MODAL);
//stage.initModality(Modality.NONE);
//stage.initStyle(StageStyle.DECORATED);
//stage.initStyle(StageStyle.UNDECORATED);
//stage.initStyle(StageStyle.TRANSPARENT);
//stage.initStyle(StageStyle.UNIFIED);
//stage.initStyle(StageStyle.UTILITY);
//stage.setOnCloseRequest(function);
//stage.setOnHiding(function);
//stage.setOnHidden(function);
//stage.setOnShown(function);
//stage.addEventHandler(KeyEvent.KEY_PRESSED, function); //read jenkov documentation for more
//scene.setCursor(Cursor.OPEN_HAND);
//scene.setCursor(Cursor.CLOSED_HAND);
//scene.setCursor(Cursor.CROSSHAIR);
//scene.setCursor(Cursor.DEFAULT);
//scene.setCursor(Cursor.HAND);
//scene.setCursor(Cursor.WAIT);
//scene.setCursor(Cursor.H_RESIZE);
//scene.setCursor(Cursor.V_RESIZE);
//scene.setCursor(Cursor.MOVE);
//scene.setCursor(Cursor.TEXT);

//node.setLayoutX(50); node.setLayoutY(50);
//node.setPrefWidth(400); node.setPrefHeight(200);
//node.setMinWidth(200); node.setMinHeight(100);
//node.setMaxWidth(600); node.setMaxHeight(300);
//node.setUserData(new MyObject("some data"));

//lock JavaFX Property like width to some ratio of height of pane

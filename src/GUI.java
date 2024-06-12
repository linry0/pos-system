import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

// add to run configurations vm options (its hidden by default)
// --module-path /Users/linry/Documents/fortune-garden/javafx-sdk-22.0.1/lib --add-modules javafx.controls,javafx.fxml

//TODO prevent crashing when duplicate items are added
//TODO implement save button in hBoxStageControls to save items to a txt
public class GUI extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("screen0: fullscreen");
        primaryStage.setScene(sceneMain(primaryStage));
        primaryStage.setFullScreen(true);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            stageExit(primaryStage).showAndWait();
        });
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

    public Button buttonRestart(Stage stage) {
        Button button = new Button("RESTART");
            EventHandler<ActionEvent> eventHandler = actionEvent -> {
                //stage.hide(); needs Platform.setImplicitExit(false) (in start() preferably) to not close entire programme when no more stages are visible (Platform.exit() to actually exit the programme in that case)
                stage.setScene(sceneMain(stage));
                stage.setFullScreen(true);
                //stage.show(); needs the entire programme not to be closed to show the stage
            };
            button.setOnAction(eventHandler);

        return button;
    }

    public Button buttonAddItem(String name, Pane pane) {
        Button button = new Button(name);
            Label label = new Label(name);
            EventHandler<ActionEvent> eventHandler = actionEvent -> {
                pane.getChildren().add(label);
            };
        button.setOnAction(eventHandler);

        return button;
    }

    public ScrollPane scrollPaneItems(Pane itemDestination) {
        ScrollPane scrollPane = new ScrollPane();
            VBox vBox = new VBox(10);
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

    public HBox hBoxStageControls(Stage stage) {
        HBox hBox = new HBox();
            Button buttonClose = buttonClose(stage);
            Button buttonRestart = buttonRestart(stage);
            hBox.getChildren().addAll(buttonClose, buttonRestart);

        return hBox;
    }

    public Scene sceneMain(Stage stage) {
        VBox vBox = new VBox(10);
            Label label0 = new Label("Welcome to the fortune-garden computer!");
            ScrollPane scrollPaneOrders = new ScrollPane();
                VBox vBoxOrders = new VBox();
                scrollPaneOrders.setContent(vBoxOrders);
            ScrollPane scrollPaneItems = scrollPaneItems(vBoxOrders);
        vBox.getChildren().addAll(label0, scrollPaneItems, scrollPaneOrders, hBoxStageControls(stage));

        return new Scene(vBox, 400, 1000);
    }

    public Stage stageExit(Stage stageOwner) {
        Stage stage = new Stage();
        stage.initOwner(stageOwner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setX(100); stage.setY(100);
        stage.setWidth(500); stage.setHeight(500);
        stage.setTitle("WARNING");
            VBox vBox = new VBox(10);
                Label label = new Label("YOUR COMPUTER HAS VIRUS");
                Button button1 = new Button("DOWNLOAD FREE ANTIVIRUS");
                    button1.setOnAction(event -> stageExit(stage).showAndWait());
                vBox.getChildren().addAll(label, button1, buttonClose(stage));
            Scene scene = new Scene(vBox, 400, 200);
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

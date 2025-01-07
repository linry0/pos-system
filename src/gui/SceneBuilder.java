package gui;

import javafx.scene.Scene;
import javafx.stage.Stage;

@FunctionalInterface
public interface SceneBuilder {
	Scene build(Stage stage);
}

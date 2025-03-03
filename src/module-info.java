module pos.system {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    exports gui to javafx.graphics;
    opens gui to javafx.graphics;
	exports element to javafx.graphics;
	opens element to javafx.graphics;
}

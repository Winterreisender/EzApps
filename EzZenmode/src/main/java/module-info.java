module io.github.winterreisender.ezzenmode {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires java.desktop;
    //requires javafx.web;
    opens io.github.winterreisender.ezzenmode to javafx.fxml;
    exports io.github.winterreisender.ezzenmode;
}
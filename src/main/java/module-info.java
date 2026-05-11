module com.pchess {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires javafx.media;
    opens com.pchess to javafx.fxml;
    opens com.pchess.controller to javafx.fxml;
    
    exports com.pchess;
}

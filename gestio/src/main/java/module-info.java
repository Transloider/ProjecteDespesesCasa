module com.projectedespeses {
    requires javafx.controls;
    requires transitive javafx.fxml;
    requires java.sql;
    requires javafx.graphics;

    opens com.projectedespeses to javafx.fxml;
    opens com.projectedespeses.controller to javafx.fxml;
    exports com.projectedespeses;
    exports com.projectedespeses.controller;
    exports com.projectedespeses.model;
    exports com.projectedespeses.enumerats;
}

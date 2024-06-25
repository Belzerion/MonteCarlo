module com.montecarlo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.montecarlo to javafx.fxml;
    exports com.montecarlo;
}

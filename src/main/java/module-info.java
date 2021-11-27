module com.ohnet.snakefx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.ohnet.snakefx to javafx.fxml;
    exports com.ohnet.snakefx;
}
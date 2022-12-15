module com.example.tp4_rpg_javafx_version {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires javafx.media;

    opens com.example.tp4_rpg_javafx_version to javafx.fxml;
    exports com.example.tp4_rpg_javafx_version;
}
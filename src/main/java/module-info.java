module com.example.intern_mnagement_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires java.mail;
    requires org.apache.pdfbox;
    requires java.desktop;
    requires com.github.librepdf.openpdf;
    requires javafx.graphics;
    opens com.example.intern_manegement_app to javafx.fxml;
    exports com.example.intern_manegement_app;
}
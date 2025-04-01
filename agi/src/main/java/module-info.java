module com.cripto.agi.agi {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.net.http;
    requires java.sql;
    requires org.jetbrains.annotations;
    requires org.json;
    requires io.github.cdimascio.dotenv.java;

    opens com.cripto.agi.agi to javafx.fxml;
    exports com.cripto.agi.agi;
    exports com.cripto.agi.agi.javafx.controllers;
    opens com.cripto.agi.agi.javafx.controllers to javafx.fxml;
}
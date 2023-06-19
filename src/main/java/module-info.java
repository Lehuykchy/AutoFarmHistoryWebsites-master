module com.example.demo3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.jsoup;


    opens com.example.demo3 to javafx.fxml;
    exports com.example.demo3;
    exports com.example.demo3.controller;
    opens com.example.demo3.controller to javafx.fxml;
    exports com.example.demo3.model;
    opens com.example.demo3.model to javafx.fxml;
}
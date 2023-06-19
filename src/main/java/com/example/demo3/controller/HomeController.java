package com.example.demo3.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
/**
 *     Main screen with Home Controller includes 2 parts:
 *         - Left  : Navbar
 *         - Right : Main content
 *
 *     UI functions:
 *         - Data tab  : View history data, search by name and category.
 *         - Config tab: Input url and related fields then you can get data and preview.
 *                       If this data is exactly what you want, you can save to databases.
 *
 *
 */
public class HomeController {
    @FXML
    private Button buttonData;
    @FXML
    private Button buttonConfig;
    @FXML
    private AnchorPane srcData, srcConfig;
    @FXML
    protected void onDataButtonClick() {
        srcData.setVisible(true);
        srcConfig.setVisible(false);

        buttonData.setStyle("-fx-background-color: yellow;");
        buttonConfig.setStyle("-fx-background-color: white;");
    }

    @FXML
    protected void onConfigButtonClick(){
        srcConfig.setVisible(true);
        srcData.setVisible(false);

        buttonData.setStyle("-fx-background-color: white;");
        buttonConfig.setStyle("-fx-background-color: yellow;");
    }
}

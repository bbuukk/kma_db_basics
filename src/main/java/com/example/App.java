package com.example;
import javafx.application.Application;
import javafx.stage.Stage;

    public class App extends Application {

        @Override
        public void start(Stage primaryStage) {
            primaryStage.setMinWidth(1200);
            primaryStage.setMinHeight(800);
            primaryStage.setResizable(true);
            primaryStage.show();
        }
    }


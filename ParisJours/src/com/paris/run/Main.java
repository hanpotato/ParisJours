package com.paris.run;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void init() throws Exception {
		super.init();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/com/paris/view/login.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("PARIS JOURS Ver0.1");
		primaryStage.setScene(scene);
		primaryStage.show();
		System.out.println("Welcome! PARIS JOURS ~ 프로그램 시작");
	}

	public static void main(String[] args) {
		launch(args);
	}
}
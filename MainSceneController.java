package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

import javafx.scene.control.TextArea;

public class MainSceneController {
	Client1 client;

	public MainSceneController() {
		client = new Client1();

	}

	@FXML
	private TextField clientTextField;
	@FXML
	private Button sendBtn;
	@FXML
	private TextArea conversationArea;

	@FXML
	public void handleClick(ActionEvent event) {
		String message = clientTextField.getText();

		client.sendMessage(message);
		// clientTextField.clear();
	}
}
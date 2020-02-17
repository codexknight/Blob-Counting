package org.example;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    final static int SQUARE_SIZE = 19;
    final static int width = 454;
    final static int height = 400;

    Canvas canvas;
    GraphicsContext g;

    Label message;

    ComboBox<String> percentFill;

    int rows;
    int columns;

    @Override
    public void start(Stage stage) {
        rows = (height - 120) / SQUARE_SIZE;
        columns = (width - 20) / SQUARE_SIZE;

        canvas = new Canvas(1 + columns * SQUARE_SIZE, 1 + rows * SQUARE_SIZE);
        g = canvas.getGraphicsContext2D();
        canvas.setOnMousePressed(e -> mousePressed(e));

        message = new Label("Click a square to get the blob size.");
        message.setTextFill(Color.BLUE);
        message.setFont(Font.font(null, FontWeight.BOLD, 14));



        percentFill = new ComboBox<String>();
        percentFill.getItems().add("10% fill");
        percentFill.getItems().add("20% fill");
        percentFill.getItems().add("30% fill");
        percentFill.getItems().add("40% fill");
        percentFill.getItems().add("50% fill");
        percentFill.getItems().add("60% fill");
        percentFill.getItems().add("70% fill");
        percentFill.getItems().add("80% fill");
        percentFill.getItems().add("90% fill");
        percentFill.setEditable(false);
        percentFill.setValue("40% fill");

        Button newButton = new Button("New Blobs");
        newButton.setOnAction(e -> fillGrid());

        Button countButton = new Button("Count the Blobs");
        countButton.setOnAction(e -> countBlobs());

        Pane root = new Pane(canvas, message, percentFill, newButton, countButton);
        root.setStyle("-fx-background-color:#BBF; -fx-border-color:#00A;-fx-border-width:2px");

        canvas.relocate(10, 10);
        message.setManaged(false);
        message.relocate(15, height - 100);
        message.resize(width - 30, 23);
        message.setAlignment(Pos.CENTER);
        
        countButton.setManaged(false);
        countButton.relocate(15, height - 72);
        countButton.resize(width - 30, 28);

        newButton.setManaged(false);
        newButton.relocate(15, height - 37);
        newButton.resize((width - 40) / 2, 28);

        percentFill.setManaged(false);
        percentFill.relocate(width / 2 + 5, height - 37);
        percentFill.resize((width - 40) / 2, 28);

        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("RANDOM BLOB COUNTER");
        fillGrid();

        stage.show();
    }

    private void countBlobs() {
    }

    private void fillGrid() {
    }

    private void mousePressed(MouseEvent e) {
    }

    public static void main(String[] args) {
        launch();
    }

}
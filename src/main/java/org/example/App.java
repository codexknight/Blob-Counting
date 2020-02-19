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

    final static int SQUARE_SIZE = 9;
    final static int width = 454;
    final static int height = 400;

    Canvas canvas;
    GraphicsContext g;

    Label message;

    ComboBox<String> percentFill;

    int rows;
    int columns;

    boolean[][] filled;
    boolean[][] visited;


    @Override
    public void start(Stage stage) {
        rows = (height - 120) / SQUARE_SIZE;
        columns = (width - 20) / SQUARE_SIZE;

        filled = new boolean[rows][columns];
        visited = new boolean[rows][columns];

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
        int count = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                visited[r][c] = false;
            }
        }
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (getBlobSize(r, c) > 0) {
                    count++;
                }
            }
        }
        draw();
        message.setText("The number of blobs is " + count);
    }

    private int getBlobSize(int r, int c) {
        if (r < 0 || r >= rows || c < 0 || c >= columns) {
            return 0;
        }
        if (filled[r][c] == false || visited[r][c] == true) {
            return 0;
        }
        visited[r][c] = true;
        int size = 1;
        size += getBlobSize(r - 1, c);
        size += getBlobSize(r + 1, c);
        size += getBlobSize(r, c - 1);
        size += getBlobSize(r, c + 1);
        return size;
    }

    private void fillGrid() {
        double probability = (percentFill.getSelectionModel().getSelectedIndex() + 1) / 10.0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                filled[r][c] = (Math.random() < probability);
                visited[r][c] = false;
            }
        }
        message.setText("Click a square to get the blob size.");
        draw();
    }

    private void mousePressed(MouseEvent e) {

        int row = (int) ((e.getY()-1 ) / SQUARE_SIZE);
        int col = (int) ((e.getX()-1) / SQUARE_SIZE);
        if (row < 0 || row >= rows || col < 0 || col >= columns) {
            message.setText("Please click on a square!");
            return;
        }
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                visited[r][c] = false;
            }

        }
        int size = getBlobSize(row, col);
        if (size == 0) {
            message.setText("There is no blob at (" + row + "," + col + ").");

        }
        else if (size == 1) {
            message.setText("Blob at (" + row + "," + col + ") contains 1 square.");
        }
        else {
            message.setText("Blob at (" + row + "," + col + ") contains " + size + " squares.");
        }
        draw();
    }

    public void draw() {

        g.setFill(Color.WHITE);
        g.fillRect(0, 0, columns * SQUARE_SIZE, rows * SQUARE_SIZE);

        g.setStroke(Color.BLACK);
        for (int i = 0; i <= rows; i++) {
            g.strokeLine(0.5, 0.5 + i*SQUARE_SIZE, columns*SQUARE_SIZE + 0.5, 0.5 + i*SQUARE_SIZE);
        }
        for (int i = 0; i <= columns; i++){
            g.strokeLine(0.5 + i*SQUARE_SIZE, 0.5, 0.5 + i*SQUARE_SIZE, rows*SQUARE_SIZE + 0.5);
        }

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (visited[r][c]) {
                    g.setFill(Color.RED);
                    g.fillRect(1 + c * SQUARE_SIZE, 1 + r * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);
                } else if (filled[r][c]) {
                    g.setFill(Color.GRAY);
                    g.fillRect(1 + c*SQUARE_SIZE, 1 + r*SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);

                }
            }
        }

    }

    public static void main(String[] args) {
        launch();
    }

}
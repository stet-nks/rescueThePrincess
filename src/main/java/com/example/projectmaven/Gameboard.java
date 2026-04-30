package com.example.projectmaven;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Gameboard extends Application {

    enum CellType {
        GRASS, PLAYER, PRINCESS, BOMB, WALL
    }

    // Use "matrix" instead of "map"
    private CellType[][] matrix = new CellType[Constants.ROWS][Constants.COLS];

    @Override
    public void start(Stage stage) {

        initMatrix();

        GridPane grid = new GridPane();
        grid.prefWidthProperty().bind(stage.widthProperty());
        grid.prefHeightProperty().bind(stage.heightProperty());
        drawBoard(grid);

        BorderPane root = new BorderPane();
        root.setCenter(grid);

        Scene scene = new Scene(root, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);

        stage.setTitle("Rescue the Princess");
        stage.setScene(scene);
        stage.show();
    }

    private void initMatrix() {
        for (int r = 0; r < Constants.ROWS; r++) {
            for (int c = 0; c < Constants.COLS; c++) {
                matrix[r][c] = CellType.GRASS;
            }
        }

        // Sample objects
        matrix[0][0] = CellType.PLAYER;
        matrix[9][9] = CellType.PRINCESS;
        matrix[4][5] = CellType.BOMB;
        matrix[1][1] = CellType.WALL;
        matrix[1][2] = CellType.WALL;
    }

    private void drawBoard(GridPane grid) {
        grid.getChildren().clear();

        for (int row = 0; row < Constants.ROWS; row++) {
            for (int col = 0; col < Constants.COLS; col++) {

                StackPane cell = new StackPane();
                cell.prefWidthProperty().bind(grid.widthProperty().divide(Constants.COLS));
                cell.prefHeightProperty().bind(grid.heightProperty().divide(Constants.ROWS));
                cell.setStyle("-fx-border-color: black; -fx-background-color: beige;");

                Label label = new Label();

                if(matrix[row][col] == CellType.PLAYER ) {
                    label.setText("🧍");
                }else if(matrix[row][col] == CellType.PRINCESS ) {
                    label.setText("👸");
                }else if(matrix[row][col] == CellType.BOMB){
                    label.setText("💣");
                }else if(matrix[row][col] == CellType.WALL){
                    label.setText("");
                    cell.setStyle("-fx-border-color: black; -fx-background-color: gray;");
                }else{
                    label.setText("");
                }

                cell.getChildren().add(label);
                grid.add(cell, col, row);
            }
        }
    }
}
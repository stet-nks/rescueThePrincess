package com.example.projectmaven;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Random;

public class Gameboard extends Application {

    enum CellType {
        GRASS, PLAYER, PRINCESS, BOMB, WALL
    }

    private static final int NUM_BOMBS = 4;

    private CellType[][] matrix = new CellType[Constants.ROWS][Constants.COLS];

    // Images — loaded once, reused for every cell
    private Image grassImage;
    private Image playerImage;
    private Image princessImage;
    private Image bombImage;
    private Image wallImage;

    @Override
    public void start(Stage stage) {

        loadImages();
        initMatrix();

        GridPane grid = new GridPane();
        drawBoard(grid);

        BorderPane root = new BorderPane();
        root.setCenter(grid);

        Scene scene = new Scene(root, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);

        stage.setTitle("Rescue the Princess");
        stage.setScene(scene);
        stage.show();
    }

    //  Image loading
    private void loadImages() {
        // Images live in src/main/resources/com/example/projectmaven
        grassImage   = new Image(getClass().getResourceAsStream("grass.png"));
        playerImage  = new Image(getClass().getResourceAsStream("player.png"));
        princessImage = new Image(getClass().getResourceAsStream("princess.png"));
        bombImage    = new Image(getClass().getResourceAsStream("bomb.png"));
        wallImage    = new Image(getClass().getResourceAsStream("wall.png"));
    }

    // Matrix initialisation
    private void initMatrix() {

        // 1. Fill everything with grass
        for (int r = 0; r < Constants.ROWS; r++) {
            for (int c = 0; c < Constants.COLS; c++) {
                matrix[r][c] = CellType.GRASS;
            }
        }

        // 2. Perimeter walls (top, bottom, left, right edges)
        for (int c = 0; c < Constants.COLS; c++) {
            matrix[0][c] = CellType.WALL;
            matrix[Constants.ROWS - 1][c] = CellType.WALL;
        }
        for (int r = 0; r < Constants.ROWS; r++) {
            matrix[r][0] = CellType.WALL;
            matrix[r][Constants.COLS - 1] = CellType.WALL;
        }

        // 3. Player always starts at [1][1]
        matrix[1][1] = CellType.PLAYER;

        // 4. Place princess and bombs randomly (not on walls, not on player)
        Random random = new Random();

        // Princess first
        placeRandom(random, CellType.PRINCESS);

        // Then bombs
        for (int i = 0; i < NUM_BOMBS; i++) {
            placeRandom(random, CellType.BOMB);
        }
    }

    /** Places one item of the given type on a random free (GRASS) inner cell. */
    private void placeRandom(Random random, CellType type) {
        int r, c;
        do {
            // Inner cells only: rows 1 (ROWS-2), cols 1 (COLS-2)
            r = 1 + random.nextInt(Constants.ROWS - 2);
            c = 1 + random.nextInt(Constants.COLS - 2);
        } while (matrix[r][c] != CellType.GRASS); // retry if cell already taken
        matrix[r][c] = type;
    }

    //  Board rendering
    private void drawBoard(GridPane grid) {
        grid.getChildren().clear();

        for (int row = 0; row < Constants.ROWS; row++) {
            for (int col = 0; col < Constants.COLS; col++) {

                StackPane cell = new StackPane();
                cell.setPrefWidth(Constants.CELL_SIZE);
                cell.setPrefHeight(Constants.CELL_SIZE);
                cell.setStyle("-fx-border-color: black;");

                //  Layer 1: grass background (always present)
                ImageView bg = makeImageView(grassImage, cell);
                cell.getChildren().add(bg);

                // Layer 2: foreground sprite (if not plain grass)
                Image fg = switch (matrix[row][col]) {
                    case PLAYER   -> playerImage;
                    case PRINCESS -> princessImage;
                    case BOMB     -> bombImage;
                    case WALL     -> wallImage;
                    default       -> null;
                };

                if (fg != null) {
                    ImageView fgView = makeImageView(fg, cell);
                    cell.getChildren().add(fgView);
                }

                grid.add(cell, col, row);
            }
        }
    }

    /** Creates an ImageView that fills its parent StackPane. */
    private ImageView makeImageView(Image image, StackPane parent) {
        ImageView iv = new ImageView(image);
        iv.setFitWidth(Constants.CELL_SIZE);
        iv.setFitHeight(Constants.CELL_SIZE);
        iv.setPreserveRatio(false);
        return iv;
    }
}
package com.example.projectmaven;

public class Constants {
    // Grid size
    public static final int ROWS = 10;
    public static final int COLS = 10;

    // Each cell is this many pixels wide and tall
    public static final int CELL_SIZE = 80; // 80 x 10 = 800 → fills the window perfectly

    // Size of window
    public static final int SCENE_WIDTH  = COLS * CELL_SIZE;
    public static final int SCENE_HEIGHT = ROWS * CELL_SIZE;
}

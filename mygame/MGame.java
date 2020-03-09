package mygame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MGame extends Application {
    static GridPane grid;
    public int x;
    public int y;

    @Override
    public void start(Stage primaryStage) throws Exception {
        int rows = 20;
        int columns = 20;

                primaryStage.setTitle("\n" + "Dots and Cells");
                grid = new GridPane();
                for (int i = 0; i < columns; i++) {
                    ColumnConstraints column = new ColumnConstraints(20);
                    grid.getColumnConstraints().add(column);
                }

                for (int i = 0; i < rows; i++) {
                    RowConstraints row = new RowConstraints(20);
                    grid.getRowConstraints().add(row);
                }

        Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run( ) {
                        Runnable updater = new Runnable() {
                                @Override
                                public void run( ) {
                                    try {
                                        for (x = 0; x < rows; x++) {
                                            int xrand = (int) (Math.random() * 10);
                                            for (y = 0; y < columns; y++) {
                                                int yrand = (int) (Math.random() * 10);
                                                    grid.add(Сells.getCell(1), x + xrand, y + yrand);
                                             }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                };
                        while (true) {
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException ex) {
                            }
                            Platform.runLater(updater);
                        }
                    }
        });

        thread.setDaemon(true);
        thread.start();

                    grid.setOnMouseReleased(new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent me) {
                            try {
                                    grid.add(Dots.getDot(1), (int) ((me.getSceneX() - (me.getSceneX() % 20)) / 20), (int) ((me.getSceneY() - (me.getSceneY() % 20)) / 20));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    grid.setStyle("-fx-background-color: white; -fx-grid-lines-visible: true");
                    Scene scene = new Scene(grid, (columns * 20), (rows * 20), Color.WHITE);
                    primaryStage.setScene(scene);
                    primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


package mygame;

import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Dots {

    public static SubScene getDot(final int number) throws Exception {
        Circle circle = new Circle(10, 10f, 5);
        circle.setFill(Color.RED);
        Group group = new Group();
        group.getChildren().add(circle);
        SubScene scene = new SubScene(group, 20, 20);
        scene.setFill(Color.WHITE);
        return scene;
    }
}


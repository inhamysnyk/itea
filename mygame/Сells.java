package mygame;

import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;

public class Сells {

    public static SubScene getCell(final int number) throws Exception {
        Group group = new Group();
        SubScene scene = new SubScene(group, 20, 20);
        scene.setFill(Color.GREEN);
        return scene;
    }
}

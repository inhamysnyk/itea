package fxdog;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;

public class MainFXD extends Application {
    Stage primaryStage;
    TextField dogNameField = new TextField("");
    TextField dogAgeField = new TextField("");
    TextField ownerNameField = new TextField("");

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage=primaryStage;

        try {
            Label dogName = new Label("Dog name");
            Label dogAge = new Label("Dog age");
            Label ownerName = new Label("Owner Name");
            Button createDog = new Button("Create Dog");
            createDog.setId("myId");

            GridPane root = new GridPane();
            root.setAlignment(Pos.CENTER);
            root.setHgap(10);
            root.setVgap(10);
            root.setPadding(new Insets(25, 25, 25, 25));

            root.add(dogName, 0, 0);
            root.add(dogNameField, 1, 0);
            root.add(dogAge, 0, 1);
            root.add(dogAgeField, 1, 1);
            root.add(ownerName, 0, 2);
            root.add(ownerNameField, 1, 2);
            root.add(createDog, 0, 3);

            HBox hBox = new HBox();
            hBox.getChildren().add(createDog);
            Scene scene = new Scene(root, 400, 400);
            root.add(hBox, 1, 5);
            primaryStage.setScene(scene);
            primaryStage.show();

            createDog.setOnAction((event) -> {
               if (!(dogNameField.getText().isEmpty()) && !(dogAgeField.getText().isEmpty())
                        && !(ownerNameField.getText().isEmpty())) {
                   Dog dog = new Dog(dogNameField.getText(), dogAgeField.getText());
                   Owner owner = new Owner(ownerNameField.getText());
                   dog.setOwner(owner);

                   try(DataOutputStream dos=new DataOutputStream(
                           new FileOutputStream("save.ser"));){
                       dos.writeUTF(dog.getName());
                       dos.writeUTF(dog.getAge());
                       dos.writeUTF(dog.getOwner());

                       try {
                   nextScene();
                       } catch (IOException e) {
                   e.printStackTrace();
                }

        }catch(Exception e) {
            e.printStackTrace();
        }
    }else{
                   Label result = new Label("Заполните все поля !");
                   root.add(result, 0, 6);
    }});
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    private void nextScene() throws IOException {

        Label result = new Label("Собака сохранена в файл save.ser");
        Button getDog = new Button("Get Dog");
        TextField getDogResultText = new TextField("");

                GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(25, 25, 25, 25));

        root.add(result, 0, 0);
        root.add(getDog, 0, 1);
        root.add(getDogResultText, 0, 2);

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        getDog.setOnAction((event) -> {
            try (DataInputStream dos = new DataInputStream(
                    new FileInputStream("save.ser"));) {
                String text = "name=" + dos.readUTF() +
                        ", age=" + dos.readUTF() +
                        ", owner=" + dos.readUTF();
                getDogResultText.setText(text);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

        public static void main (String[]args){
            launch(args);
        }

    }




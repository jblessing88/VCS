package versioncontrol;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewController{
    private static final Stage PRIMARY_STAGE = new Stage();
    private static final String[] HEADER_INFO = new String[2];
    
    public static String[] start(){
        HEADER_INFO[0] = "";
        HEADER_INFO[1] = "";
        VBox content = new VBox();
        Label author = new Label("Author: ");
        TextField name = new TextField();
        Label comment = new Label("Comment: ");
        TextArea message = new TextArea();
        HBox controls = new HBox();
        Button cancel = new Button("Cancel");
        Button done = new Button("Done");
        BorderPane border = new BorderPane();
        double width = 450;
        double height = 300;
        Scene scene = new Scene(border, width, height);
        
        cancel.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
            PRIMARY_STAGE.close();
        });
        done.addEventFilter(ActionEvent.ACTION, (ActionEvent event) -> {
            HEADER_INFO[0] = name.getText();
            HEADER_INFO[1] = message.getText();
            
            PRIMARY_STAGE.close();
        });
        done.setStyle("-fx-base: dodgerblue;");
        
        controls.setMargin(cancel, new Insets(20,10,20,10));
        controls.setMargin(done, new Insets(20,10,20,10));
        controls.getChildren().addAll(cancel,done);
        controls.setAlignment(Pos.CENTER_RIGHT);
        message.setWrapText(true);
        
        content.setPadding(new Insets(20, 20, 0, 20));
        content.getChildren().addAll(author, name, comment, message, controls);
        content.setVgrow(comment, Priority.ALWAYS);
        
        border.setStyle("-fx-background-color: #E8E8E8;");
        border.setCenter(content);
        
        PRIMARY_STAGE.setScene(scene);
        PRIMARY_STAGE.centerOnScreen();
        PRIMARY_STAGE.setMinWidth(width);
        PRIMARY_STAGE.setMinHeight(height);
        PRIMARY_STAGE.setTitle("Version Control System");
        PRIMARY_STAGE.showAndWait();
        
        return HEADER_INFO;
    }
}
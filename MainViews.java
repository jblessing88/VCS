package versioncontrol;

import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainViews extends Application{
    private static final double WIDTH = 650;
    private static final double HEIGHT = 750;
    private static Stage primaryStage = new Stage();
    public static final Stage COMPARE_STAGE = new Stage();
    private static final HBox BUTTONS = new HBox();
    public static final Button CHECK = new Button("Check-In");
    public static final Button PRINT_DIFF = new Button("Print Diff");
    public static final Button PRINT_LF = new Button("Print LogFile");
    public static final Button VIEW = new Button("View Old");
    private static final VBox CONTENT = new VBox();
    private static final TitledPane PREVIEW_BOX = new TitledPane();
    private static final ScrollPane SCROLL = new ScrollPane();
    private static final VBox PREVIEW = new VBox();
    private static final HBox CONTROLS = new HBox();
    public static final Button CANCEL = new Button("Cancel");
    public static final Button DYNAMIC = new Button(Controller.dynamic);
    private static final BorderPane BORDER = new BorderPane();
    private static final Scene SCENE = new Scene(BORDER, WIDTH, HEIGHT);
    private static final Controller INSTANCE = Controller.getInstance();
    public static final Button COMPARE_DONE = new Button("Done");

    @Override
    public void init(){
        CHECK.addEventHandler(ActionEvent.ACTION, INSTANCE);
        PRINT_DIFF.addEventHandler(ActionEvent.ACTION, INSTANCE);
        PRINT_LF.addEventHandler(ActionEvent.ACTION, INSTANCE);
        VIEW.addEventHandler(ActionEvent.ACTION, INSTANCE);
        CANCEL.addEventHandler(ActionEvent.ACTION, INSTANCE);
        DYNAMIC.addEventHandler(ActionEvent.ACTION, INSTANCE);
        COMPARE_DONE.addEventHandler(ActionEvent.ACTION, INSTANCE);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        show();
    }
    
    public static void show(){
        SCROLL.setHbarPolicy(ScrollBarPolicy.NEVER);
        SCROLL.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        SCROLL.setFitToWidth(true);
        SCROLL.setPrefSize(200, 200);
        SCROLL.setStyle("-fx-background: #FFFFFF;");
        SCROLL.setContent(PREVIEW);
        DYNAMIC.setStyle("-fx-base: dodgerblue;");
        BUTTONS.setStyle("-fx-background-color: #D0D0D0;");
        BUTTONS.setPadding(new Insets(20, 20, 20, 20));
        BUTTONS.getChildren().addAll(CHECK, PRINT_DIFF, PRINT_LF, VIEW);
        BUTTONS.setMargin(CHECK,new Insets(0,20,0,20));
        BUTTONS.setMargin(PRINT_DIFF,new Insets(0,20,0,20));
        BUTTONS.setMargin(PRINT_LF,new Insets(0,20,0,20));
        BUTTONS.setMargin(VIEW,new Insets(0,20,0,20));
        BUTTONS.setAlignment(Pos.CENTER);
        CONTROLS.setMargin(CANCEL, new Insets(20,10,20,10));
        CONTROLS.setMargin(DYNAMIC, new Insets(20,10,20,10));
        CONTROLS.getChildren().addAll(CANCEL,DYNAMIC);
        CONTROLS.setAlignment(Pos.CENTER_RIGHT);
        PREVIEW_BOX.setText("No Preview Available");
        PREVIEW_BOX.setCollapsible(false);
        PREVIEW_BOX.setPrefHeight(HEIGHT);
        PREVIEW_BOX.setContent(SCROLL);
        CONTENT.setPadding(new Insets(20, 20, 0, 20));
        CONTENT.getChildren().addAll(PREVIEW_BOX, CONTROLS);
        CONTENT.setVgrow(PREVIEW_BOX, Priority.ALWAYS);
        BORDER.setStyle("-fx-background-color: #E8E8E8;");
        BORDER.setTop(BUTTONS);
        BORDER.setCenter(CONTENT);
        primaryStage.setScene(SCENE);
        primaryStage.centerOnScreen();
        primaryStage.setMinWidth(WIDTH);
        primaryStage.setMinHeight(HEIGHT);
        primaryStage.setTitle("Version Control System");
        primaryStage.show();
        primaryStage.setOnCloseRequest((WindowEvent arg0) -> {
            Platform.exit();
        });
    }
    
    public static String chooseFile(String direc){
        FileChooser filec = new FileChooser();
        filec.setTitle("Choose A File");
        if(direc != null)
            filec.setInitialDirectory(new File(direc));
        File file = filec.showOpenDialog(primaryStage);
        try{
            return (file.exists()) ? file.getAbsolutePath():null;
        }catch(NullPointerException npe){}
        return null;
    }
    
    public static String chooseProject(String direc){
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose a Folder");
        chooser.setInitialDirectory(new File(direc));
        File file = chooser.showDialog(primaryStage);
        try{
            return (file.exists()) ? file.getAbsolutePath():null;
        }catch(NullPointerException npe){}
        return null;
    }
    
    public static void displayFile(TextFlow text, String path){
        PREVIEW.getChildren().clear();
        PREVIEW.getChildren().add(text);
        PREVIEW_BOX.setText(path);
    }
    
    public static void displayComparison(TextFlow text, TextFlow text2, String path, String path2){
        final HBox compareContent = new HBox();
        final VBox compare = new VBox();
        final Scene compareScene = new Scene(compare, WIDTH*2, HEIGHT);
        final TitledPane oldTitledPane = new TitledPane();
        final TitledPane newTitledPane = new TitledPane();
        final ScrollPane oldScrollPane = new ScrollPane();
        final ScrollPane newScrollPane = new ScrollPane();
        final VBox oldPreview = new VBox();
        final VBox newPreview = new VBox();
        final HBox compareControls = new HBox();
        oldPreview.getChildren().add(text);
        newPreview.getChildren().add(text2);
        oldScrollPane.setContent(oldPreview);
        newScrollPane.setContent(newPreview);
        oldScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        oldScrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        newScrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        newScrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        oldScrollPane.setFitToWidth(true);
        newScrollPane.setFitToWidth(true);
        oldTitledPane.setCollapsible(false);
        oldTitledPane.setPrefSize(WIDTH, HEIGHT);
        oldTitledPane.setStyle("-fx-background: #FFFFFF;");
        oldTitledPane.setText("Old File: " + path);
        oldTitledPane.setContent(oldScrollPane);
        newTitledPane.setCollapsible(false);
        newTitledPane.setPrefSize(WIDTH, HEIGHT);
        newTitledPane.setStyle("-fx-background: #FFFFFF;");
        newTitledPane.setText("New File: " + path2);
        newTitledPane.setContent(newScrollPane);
        compareContent.getChildren().addAll(oldTitledPane, newTitledPane);
        compareContent.setHgrow(oldTitledPane, Priority.ALWAYS);
        compareContent.setHgrow(newTitledPane, Priority.ALWAYS);
        COMPARE_DONE.setStyle("-fx-base: dodgerblue;");
        compareControls.getChildren().add(COMPARE_DONE);
        compare.setVgrow(compareContent, Priority.ALWAYS);
        compare.getChildren().addAll(compareContent, compareControls);
        compareContent.setPadding(new Insets(10, 10, 0, 10));
        compareControls.setMargin(COMPARE_DONE, new Insets(20,10,20,10));
        compareControls.setAlignment(Pos.CENTER_RIGHT);
        COMPARE_STAGE.setScene(compareScene);
        COMPARE_STAGE.setTitle("Comparison");
        COMPARE_STAGE.centerOnScreen();
        COMPARE_STAGE.show();
    }
    
    public static void displayPrint(TextFlow text, String path){
        TextFlow newText = new TextFlow();
        newText.getChildren().addAll(new Text(path), text);
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            job.showPageSetupDialog(primaryStage);
            job.showPrintDialog(primaryStage);
            Printer printer = job.getPrinter();
            PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
            job.getJobSettings().setPageLayout(pageLayout);
            boolean success = job.printPage(newText);
            if (success) { 
                job.endJob();
            }
        }
    }
    
    public static void displayNoPreview(){
        PREVIEW.getChildren().clear();
        PREVIEW_BOX.setText("No Preview Available");
    }
}
package versioncontrol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Controller implements EventHandler<ActionEvent>{
    private static final String SEP = System.getProperty("file.separator");
    private static final String DIREC = System.getProperty("user.home") + SEP + "VCS";
    private static Controller instance = null;
    public static String dynamic = "Ok";
    private static String fileName = "";
    private static String ext = "";
    private static String fullPath = "";
    
    protected Controller(){}
    
    public static Controller getInstance(){
        if(instance == null)
            instance = new Controller();
        return instance;
    }
    
    private static void setup(){
        File directory = new File(DIREC);
        if(!directory.exists()){
            directory.mkdirs();
        }
    }
    
    private static void determinePathAndExt(String path){
        fullPath = path;
        int s = path.lastIndexOf(SEP);
        int d = path.lastIndexOf(".");
        fileName = path.substring((s+1), d);
        ext = path.substring(d, path.length());
    }
    
    @Override
    public void handle(ActionEvent event) {
        String path;
        if(event.getSource() == MainViews.CHECK){
            path = MainViews.chooseFile(null);
            if(path == null)
                return;
            dynamic = "Compare";
            MainViews.DYNAMIC.setText(dynamic);
            determinePathAndExt(path);
            MainViews.displayFile(displayFile(path), path);
        }
        else if(event.getSource() == MainViews.PRINT_DIFF){
            path = MainViews.chooseProject(DIREC);
            if(path == null)
                return;
            dynamic = "Print Diff";
            MainViews.DYNAMIC.setText(dynamic);
            MainViews.displayFile(displayFile(path + SEP + getCurrDiffFromManifest(path + SEP + "Manifest.txt")), path + SEP + getCurrDiffFromManifest(path + SEP + "Manifest.txt"));
        }
        else if(event.getSource() == MainViews.PRINT_LF){
            path = MainViews.chooseProject(DIREC);
            if(path == null)
                return;
            dynamic = "Print Log";
            MainViews.DYNAMIC.setText(dynamic);         
            MainViews.displayFile(displayFile(path + SEP + "log.txt"), path + SEP + "log.txt");
        }
        else if(event.getSource() == MainViews.VIEW){
            path = MainViews.chooseFile(DIREC);
            if(path == null)
                return;
            dynamic = "Ok";
            MainViews.DYNAMIC.setText(dynamic);           
            determinePathAndExt(path);           
            MainViews.displayFile(displayFile(path), path);
        }
        else if(event.getSource() == MainViews.CANCEL){
            MainViews.displayNoPreview();
        }
        else if(event.getSource() == MainViews.DYNAMIC){
            switch(dynamic){
                case "Compare":
                    File directory = new File(DIREC + SEP + fileName);
                    if(!directory.exists()){
                        directory.mkdirs();
                    }
                    
                    if(new File(DIREC+SEP+fileName+SEP+fileName+ext).exists()){
                        Path src = Paths.get(fullPath);
                        Path dest = Paths.get(DIREC, fileName, "temp"+ext);
                        try{
                            Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
                        } catch(IOException e){}
                        
                        String diffFile = getCurrDiffFromManifest(DIREC+SEP+fileName+SEP+"Manifest.txt");
                        Matcher matcher = Pattern.compile("(\\d+)").matcher(diffFile);
                        String a = "";
                        while(matcher.find()) {
                            a = matcher.group(1);
                        }
                        
                        int version = Integer.parseInt(a) + 1;
                        diffFile = "Diff_" + (version-1) + ".txt";
                        String newDiffFile = "Diff_" + version + ".txt";
                        
                        Comparator.compare(DIREC + SEP + fileName + SEP, fileName, ext, version);
                        
                        MainViews.displayComparison(displayFile(DIREC + SEP + fileName + SEP + diffFile), displayFile(DIREC + SEP + fileName + SEP + newDiffFile), DIREC + SEP + fileName + SEP + diffFile, DIREC + SEP + fileName + SEP + newDiffFile);
                        
                        src = Paths.get(DIREC, fileName, "temp"+ext);
                        dest = Paths.get(DIREC, fileName, fileName+ext);
                        try{
                            Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);
                        } catch(IOException e){}   
                    }
                    else{
                        Path src = Paths.get(fullPath);
                        Path dest = Paths.get(DIREC, fileName, fileName+ext);
                        try{
                            Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
                        } catch(IOException e){}
                        Comparator.compare(DIREC + SEP + fileName + SEP, fileName, ext, 1);
                        
                        String diffFile = getCurrDiffFromManifest(DIREC+SEP+fileName+SEP+"Manifest.txt");
                        MainViews.displayComparison(new TextFlow(), displayFile(DIREC + SEP + fileName + SEP + diffFile), "No Previous File", DIREC + SEP + fileName + SEP + diffFile);
                    }
                    break;
                case "Print Diff":
                    MainViews.displayPrint(displayFile(DIREC + SEP + fileName + SEP + getCurrDiffFromManifest(DIREC+SEP+fileName+SEP+"Manifest.txt")), DIREC + SEP + fileName + SEP + getCurrDiffFromManifest(DIREC+SEP+fileName+SEP+"Manifest.txt"));
                    break;
                case "Print Log":
                    MainViews.displayPrint(displayFile(DIREC + SEP + fileName + SEP + "log.txt"), DIREC + SEP + fileName + SEP + "log.txt");
                    break;
                case "Ok":
                    MainViews.displayNoPreview();
                    break;
            }
        }
        else if(event.getSource() == MainViews.COMPARE_DONE){
            MainViews.COMPARE_STAGE.hide();
        }
    }
    
    private TextFlow displayFile(String path){
        TextFlow text = new TextFlow();
        BufferedReader br = null;
        if(path.contains("Diff_")){
            try{
                String string = "";
                String ver = path.substring((path.lastIndexOf("_")+1), path.lastIndexOf("."));
                BufferedReader bre = new BufferedReader(new FileReader(new File(DIREC + SEP + fileName + SEP + "log.txt")));
                while(bre.ready()){
                    string = bre.readLine();
                    if(string.equals("Version=" + ver)){
                        string = string + "\n" + bre.readLine();
                        string = string + "\n" + bre.readLine();
                        string = string + "\n" + bre.readLine();
                        string = string + "\n" + bre.readLine();
                        string = string + "\n" + bre.readLine();
                        string = string + "\n" + bre.readLine();
                        Text temp2 = new Text(string + "\n\n");
                        text.getChildren().add(temp2);
                    }
                }
                bre.close();
            } catch (FileNotFoundException ex) {} catch (IOException ex) {}
        }
        try{
            br = new BufferedReader(new FileReader(new File(path)));
            while(br.ready()){
                String temp = br.readLine();
                Text temp2 = new Text();
                if(temp.length() > 0 && path.contains("Diff_")){
                    Matcher matcher = Pattern.compile("(\\d*)(\\s*)").matcher(temp);
                    int count = 0;
                    int end = 0;
                    while(matcher.find()) {
                        int tempStart = matcher.start();
                        int tempEnd = matcher.end();
                        
                        if(tempStart == end){
                            end = tempEnd;
                        }
                        else{
                            count = end;
                        } 
                    }
                    switch(temp.charAt(count)){
                        case '+':
                            temp2 = new Text(temp + "\n");
                            temp2.setFont(Font.font("Bitstream Vera Sans Mono", FontWeight.NORMAL, 14));
                            temp2.setFill(Color.GREEN);
                            break;
                        case '-':
                            temp2 = new Text(temp + "\n");
                            temp2.setFont(Font.font("Bitstream Vera Sans Mono", FontWeight.NORMAL, 14));
                            temp2.setFill(Color.RED);
                            break;
                        default:
                            temp2 = new Text(temp + "\n");
                            temp2.setFont(Font.font("Bitstream Vera Sans Mono", FontWeight.NORMAL, 14));
                            temp2.setFill(Color.BLACK);
                            break;
                    }
                }
                else{
                    temp2.setFont(Font.font("Bitstream Vera Sans Mono", FontWeight.NORMAL, 14));
                    temp2.setFill(Color.BLACK);
                    temp2.setText(temp + "\n");
                }
                text.getChildren().add(temp2);
            }
        } catch(IOException e){}
        finally{
            try {
                if(br != null)
                    br.close();
            } catch (IOException ex) {}
        }
        return text;
    }
    
    public static String getCurrDiffFromManifest(String filename){
        BufferedReader br = null;
        String text = "";
        try{
            br = new BufferedReader(new FileReader(new File(filename)));
            text = br.readLine();
        } catch(IOException e){}
        finally{
            try {
                if(br != null)
                    br.close();
            } catch (IOException ex) {}
        }
        return text;
    }
    
    public static void main(String[] args) {
        setup();
        launch(args);   
    }
}
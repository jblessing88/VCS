package versioncontrol;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Comparator {
    private static String[] headerInfo = new String[7];
    private static ArrayList<Change> changes = new ArrayList<>();
    private static int totLOC = 0;
    private static int addLOC = 0;
    private static int delLOC = 0;
    
    private Comparator(){}
    
    public static void compare(String direc, String fileName, String ext, int version){
        int line = 1;
        boolean done = false;
        boolean none = false;
        Scanner scan = null;
        Scanner scan2 = null;
        Path path = Paths.get(direc, "temp" + ext);
        if(!Files.exists(path)){
            none = true;
            try {
                Files.createFile(path);
            } catch (IOException ex) {}
        }
        try {
            if(!none){
                scan2 = new Scanner(new File(direc + fileName + ext));
                scan = new Scanner(new File(direc + "temp" + ext));
            }
            else{
                scan2 = new Scanner(new File(direc + "temp" + ext));
                scan = new Scanner(new File(direc + fileName + ext));
            }
            while(!done) {
                while (scan.hasNextLine() && scan2.hasNextLine()) {
                    String curr = scan.nextLine();
                    String prev = scan2.nextLine();
                    if (curr.equals("") && !curr.equals(prev)) {
                        delLOC++;
                        totLOC++;
                        changes.add(new Change(line, Change.CONDITION.DELETED, prev));
                    } else if (prev.equals("") && !curr.equals(prev)) {
                        addLOC++;
                        totLOC++;
                        changes.add(new Change(line, Change.CONDITION.ADDED, curr));
                    } else if (!curr.equals(prev)) {
                        delLOC++;
                        addLOC++;
                        totLOC++;
                        totLOC++;
                        changes.add(new Change(line, Change.CONDITION.DELETED, prev));
                        changes.add(new Change(line, Change.CONDITION.ADDED, curr));
                    } else {
                        changes.add(new Change(line, Change.CONDITION.NONE, curr));
                        totLOC++;
                    }
                    line++;
                }
                
                if (scan.hasNextLine() && !scan2.hasNextLine()) {
                    addLOC++;
                    totLOC++;
                    changes.add(new Change(line, Change.CONDITION.ADDED, scan.nextLine()));
                } else if (scan2.hasNextLine() && !scan.hasNextLine()) {
                    delLOC++;
                    totLOC++;
                    changes.add(new Change(line, Change.CONDITION.DELETED, scan2.nextLine()));
                } else if(!scan.hasNextLine() && !scan2.hasNextLine()){
                    done = true;
                }
                line++;
            }
        } catch (FileNotFoundException e) {
            
        } finally{
            if(scan != null)
                scan.close();
            if(scan2 != null)
                scan2.close();
        }
        
        headerInfo[0] = version+"";
        headerInfo[1] = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
        headerInfo[2] = addLOC+"";
        headerInfo[3] = delLOC+"";
        headerInfo[4] = totLOC+"";
        String[] info = ViewController.start();
        headerInfo[5] = info[0];
        headerInfo[6] = info[1];

        Change[] temp = new Change[changes.size()];
        for(int i = 0; i < changes.size(); i++){
            temp[i] = changes.get(i);
        }
        Labeler.changes(direc, Controller.getCurrDiffFromManifest(direc + "Manifest.txt"), temp, headerInfo, version);
        if(none){
            try {
                Files.delete(path);
            } catch (IOException ex) {}
        }
        temp = null;
        changes = new ArrayList<>();
        headerInfo = new String[7];
        totLOC = 0;
        addLOC = 0;
        delLOC = 0;
    }
}
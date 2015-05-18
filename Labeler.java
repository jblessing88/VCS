package versioncontrol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Labeler {
    private static int version;
    
    private Labeler(){}
    
    public static void changes(String direc, String fileName, Change[] changes, String[] header, int ver){
        version = ver;
        inLineChanges(direc, fileName, changes);
        headerChanges(direc + "log.txt", header);
    }
    
    private static void inLineChanges(String direc, String fileName, Change[] changes){
        File diff = new File(direc + "Diff_" + version + ".txt");
        File manifest = new File(direc + "Manifest.txt");
        PrintWriter pw = null;
        PrintWriter pw2 = null;
        try{
            if(!diff.exists())
                diff.createNewFile();
            pw = new PrintWriter(new FileWriter(diff));
            for (int i = 0; i < changes.length; i++) {
                String empty = "";
                switch(String.valueOf(i).length()){
                    case 1:
                        empty = "    ";
                        break;
                    case 2:
                        empty = "   ";
                        break;
                    case 3:
                        empty = "  ";
                        break;
                    case 4:
                        empty = " ";
                        break;
                    default:
                        empty = "";
                        break;
                }
                if (changes[i].getCond() == Change.CONDITION.ADDED) {
                    pw.append(empty + i + " + | " + changes[i].getCode() + "\n");
                } else if (changes[i].getCond() == Change.CONDITION.DELETED) {
                    pw.append(empty + i + " - | " + changes[i].getCode() + "\n");
                } else if (changes[i].getCond() == Change.CONDITION.NONE) {
                    pw.append(empty + i + "    | " + changes[i].getCode() + "\n");
                }
            }
            pw2 = new PrintWriter(new FileWriter(manifest));
            pw2.print("Diff_" + version + ".txt");
        } catch (IOException ex) {
        
        } finally{
            if(pw != null)
                pw.close();
            if(pw2 != null)
                pw2.close();
        } 
    }
    
    private static void headerChanges(String direc, String[] headerInfo){
        String header = "Version="+version+"\nDate="+headerInfo[1]+"\nAdded="+headerInfo[2]+"\nDeleted="+headerInfo[3]+"\nTotal="+headerInfo[4]+"\nAuthor="+headerInfo[5]+"\nComment="+headerInfo[6];
        PrintWriter pw = null;
        PrintWriter pw2;
        BufferedReader br = null;
        BufferedReader br2;
        File file = new File(direc);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException ex) {}
        }
        try {
            br = new BufferedReader(new FileReader(file));
            String string = "";
            while(br.ready()){
                string = string + br.readLine() + "\n";
            }
            pw = new PrintWriter(new FileWriter(file));
            pw.append(header+"\n\n");
            pw.append(string);
        } catch (IOException e) {} finally{
            try {
                br.close();
            } catch (IOException e) {}
            pw.close();
        }
    }
}
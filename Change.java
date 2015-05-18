package versioncontrol;

public class Change {
    private final int LINE_NUM;
    private final CONDITION COND;
    private final String CODE;
    
    public Change(int lineNum, CONDITION cond, String code){
        this.LINE_NUM = lineNum;
        this.COND = cond;
        this.CODE = code;
    }
    
    public int getLineNum(){
        return LINE_NUM;
    }
    
    public CONDITION getCond(){
        return COND;
    }
    
    public String getCode(){
        return CODE;
    }
    
    public static enum CONDITION{
        ADDED, DELETED, NONE;
    }
    
    @Override
    public String toString(){
        return LINE_NUM+","+COND+","+CODE+"\n";
    }
}
package logic;

import java.io.*;
import java.util.*;

public class GlossaryBook implements Serializable {
    static Scanner input=new Scanner(System.in);
    static Properties publicBookLoader;
    private static int numberOfPBook;
    private static String publicBookPath;
    private Properties bookLoader;
    private HashMap<Integer,String[]> word;
    private User owner;
    private String name;
    private int number;
    private boolean isPublic;
    private boolean beingTest;

    static {
        publicBookPath=System.getProperty("user.dir")+ File.separator+"src"+File.separator+"resources";
        String info=System.getProperty("user.dir")+ File.separator+"src"+File.separator+"Info.properties";
        try(BufferedReader br=new BufferedReader(new FileReader(info))) {
            publicBookLoader=new Properties();
            publicBookLoader.load(br);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GlossaryBook(User owner,String name) {//Owner is not necessarily be owner, but accessor
        this.owner=owner;
        this.name=name;
        isPublic=certifyName(name);
        loadWord();
    }

    public static int getNumberOfPBook(){
        return numberOfPBook;
    }
    private void loadWord(){//Load words into properties
        String path;
        path=isPublic ? publicBookPath+File.separator+name+".txt" : owner.getMyBookPath()+File.separator+name+File.separator+name+".txt";
        try(BufferedReader br=new BufferedReader(new FileReader(path))) {
            bookLoader=new Properties();
            bookLoader.load(br);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveWord(){//Only owner's book can be save
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(owner.getMyBookPath()+File.separator+name+File.separator+name+".txt"))) {
            bookLoader.store(bw,"Last time refreshed at:");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getOwner() {
        return owner;
    }

    private void setOwner(User owner) {
        this.owner = owner;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getNumber() {
        return number;
    }

    public boolean isBeingTest() {
        return beingTest;
    }

    public void setBeingTest(boolean beingTest) {
        this.beingTest = beingTest;
    }

    public static boolean certifyName(String name){//Certify public book
        return publicBookLoader.contains(name);
    }


    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean addWord(String word,String meaning){
        if (isPublic){
            System.out.println("This book is a public book!");
            return false;
        }
        if (word.equals("") || meaning.equals("")){
            System.out.println("Word or meaning cannot be nothing!");
            return false;
        }
        if (bookLoader.containsKey(word)){
            System.out.println("This book is already has "+"'"+word+"'!");
            return false;
        }
        bookLoader.setProperty(word, meaning);
        saveWord();
        number++;
        System.out.println("Addition successful!");
        return true;
    }

    public boolean deleteWord(String word){
        if (isPublic){
            System.out.println("This book is a public book!");
            return false;
        }
        if (number==0){
            System.out.println("This book does not have any words!");
        }
        if (!bookLoader.containsKey(word)){
            System.out.println("This book is not include "+"'"+word+"'!");
            return false;
        }
        bookLoader.remove(word);
        saveWord();
        number--;
        System.out.println("Delete successful!");
        return true;
    }

    public HashMap<Integer, String[]> getWordMap() {
        HashMap<Integer, String[]> studyMap=new HashMap<>();
        Set<String> set=bookLoader.stringPropertyNames();
        int index=0;
        for (String s : set){
            index++;
            String[] content={s,bookLoader.getProperty(s)};
            studyMap.put(index,content);
        }
        return studyMap;
    }

    public static String pureName(String name){
        return name.substring(0,name.lastIndexOf("."));
    }
}

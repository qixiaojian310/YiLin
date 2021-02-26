package logic;

import java.io.*;
import java.util.*;

public class Examination implements java.io.Serializable{
    static Scanner input=new Scanner(System.in);
    private User user;
    private GlossaryBook book;
    private TreeMap<Integer,String[]> bookContent;//Get the book content
    private HashMap<Integer,Integer> wrongRecord;//For review and show the overall record!
    private int[] faultTracker;//record the order you learned
    private int frequency;
    private int number;//How many word a day/How many word for a test
    private int learned;
    private int incorrect;
    private double accuracy;

    public Examination(User user) {
        this.user = user;
        book=user.getBook();
        number=user.getPlan();
        bookContent=new TreeMap<>(book.getWordMap());
        faultTracker=new int[bookContent.size()];
        for (int i : faultTracker)
            i=0;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public double getAccuracy() {
        return accuracy;
    }

    private void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }
    public double test(){
        return accuracy;
    }

    public void studyHelper(){
        if (learned==bookContent.size()){
            System.out.println("You already learned this book! Let's study it again!");
            learned=0;
        }
//        int numberOfEachLoop;
//        if (number>=20)
//            numberOfEachLoop=4;
//        else if (number>10)
//            numberOfEachLoop=3;
//        else if (number>5)
//            numberOfEachLoop=2;
//        else numberOfEachLoop=1;
        System.out.println("Ready? Go!");
        Set<Integer> set=bookContent.keySet();
        int all=bookContent.size();
        int index=number;
        for (int i=learned+1;i<all;i++){
            System.out.print(Arrays.toString(bookContent.get(i))+"      ");
//            if (numberOfEachLoop>=3 && index%3==0)
//                System.out.println();
            index--;
            if (index%2==0){
                System.out.println("按Enter键继续学习！");
                input.nextLine();
            }
            if (index==0){
                System.out.println("Today's goal completed!");
                break;
            }

        }
        learned+=number;
    }

    public void reviewHelper(){
        if (!haveFault())
        System.out.println("You got these words perfect by now! Don't worry about it! Go for fun!");
        else {
            System.out.println("Those are the words you do not remember well:");
            System.out.println("(If you test and got it incorrect, the fault time will increase one! But if you got it right");
            System.out.print(", it will decrease one until 0, accordingly)\n");
            for (int i=0;i<faultTracker.length;i++) {
                if (faultTracker[i]!=0){
                    System.out.println(i+"."+Arrays.toString(bookContent.get(i))+"  "+faultTracker[i]+" times!");
                }
            }
        }
    }

    public void testHelper(){
        if (learned<20){
            System.out.println("You cannot attend a test if you do not learn more than 20 words!");
            return;
        }
        System.out.println("Ready? Go!");
        Set<Integer> word=bookContent.keySet();
        int index=learned;
        for (Integer i: word){//Start a test
            String[] content=bookContent.get(i);
            int[] temp=new int[3];//Generate 3 fault answers index
            System.out.println("--------------------------------------------");
            do {
                for (int j=0;j<3;j++)
                    temp[j]=(int)(Math.random()*learned)+1;
            }while (temp[0] != i && temp[1]!=i && temp[2]!=i && temp[0]!=temp[1] && temp[0]!=temp[2] && temp[1]!=temp[2]);
            String[] problem=new String[4];
            for (int j=0;j<3;j++){//Get fault answers Chinese meaning
                problem[j]=bookContent.get(temp[j])[1];//Other answers
                System.out.println(temp[j]+" "+problem[j]);
            }
            problem[3]=content[1];//The right answer
            System.out.println(i+" "+problem[3]);
            HashMap<String,String> answers=new HashMap<>();
            answers.put(problem[(i+1)%4],"a");
            answers.put(problem[(i+2)%4],"b");
            answers.put(problem[(i+3)%4],"c");
            answers.put(problem[(i+4)%4],"d");
            System.out.println(content[0]);
            System.out.print("A. "+problem[(i+1)%4]+"   ");
            System.out.print("B. "+problem[(i+2)%4]);
            System.out.println();
            System.out.print("C. "+problem[(i+3)%4]+"   ");
            System.out.print("D. "+problem[(i+4)%4]);
            System.out.println();
            System.out.println("Please choose your answer!");
            String answer=input.nextLine();
            if (answers.get(problem[3]).equals(answer.toLowerCase())){//get the represent of right answer and compare with enter
                System.out.println("You got it");
                if (faultTracker[i]>0)
                faultTracker[i]--;
            }
            else {
                System.out.println("Sorry! The right answer is "+answers.get(problem[3]));
                faultTracker[i]++;
                incorrect++;
            }
            index--;
            if (index==0)
                break;
        }
        accuracy=1-(double)incorrect/learned;
        System.out.println("End! Your got "+incorrect+" incorrect, the accuracy is "+accuracy);
    }

    public boolean haveFault(){
        for (int i : faultTracker) {
            if (i!=0)
                return false;
        }
        return true;
    }

    public void showMyRecord(){
        System.out.println("---------------------------------------------------------");
        System.out.println("By now, your accuracy is "+accuracy+", total incorrect record is "+incorrect);
        System.out.println("Keep up a good word!");
    }

    public void saveRecord(String path){
        try(ObjectOutputStream saver=new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(path)))) {
            saver.writeObject(this);
            user.setHasRecord(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Examination getRecord(String path){
        Examination exam = null;
        try(ObjectInputStream reader=new ObjectInputStream(new BufferedInputStream(new FileInputStream(path)))) {
            Object o=reader.readObject();
            exam=(Examination)o;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return exam;
    }
}

package logic;

import java.io.*;
import java.util.*;

public class User implements Serializable{
    static Scanner input=new Scanner(System.in);
    static String regex="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
    private static String userListPath;
    private static Properties userInfo=null;
    private static BufferedReader readUser=null;

    private static String passwordPath;
    private static Properties passwordInfo=null;
    private static BufferedReader readPassword=null;
    private String name;
    private String ID;
    private String password;
    private String gender="Unknown";
    private Calendar birthday=null;
    private Calendar testDate;
    private String aboutMe;
    private File myInfo;
    private String userPath;
    private String myBookPath;//folder
    private String myRecordPath;//folder
    private Properties bookLoader;
    private GlossaryBook book;
    private int numberOfBook;
    private int plan;
    private boolean hasRecord;

    static {
        userListPath =System.getProperty("user.dir")+File.separator+"src"+File.separator+"user"+File.separator+"userInfo.txt";
        passwordPath=System.getProperty("user.dir")+File.separator+"src"+File.separator+"user"+File.separator+"password.txt";
        try {// Remember to close when exit!
            readUser = new BufferedReader(new FileReader(userListPath));
            userInfo = new Properties();
            userInfo.load(readUser);

            readPassword = new BufferedReader(new FileReader(passwordPath));
            passwordInfo = new Properties();
            passwordInfo.load(readPassword);
            System.out.println("Loading....... successfully!");
       }
        catch (FileNotFoundException e) {
            System.out.println("File vanished!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Something went wrong at first!");
            e.printStackTrace();
        }
    }

    public User(String ID,String password){//login
        this.ID=ID;
        this.password=password;
        name=userInfo.getProperty(ID);
        if (setAndCheckPath()) {//See if user has its userPath and get myBook and myRecord!
            myInfo = new File(userPath + File.separator + ID + ".txt");
            myBookPath=userPath+File.separator+"My book";
            myRecordPath=userPath+File.separator+"My record";
        }
        loadMyBookInfo();
    }

    public User(){//register
        newID();
        setOrUpdatePassword();
        setMyInfo();
        if (setAndCheckPath()){
            myInfo=new File(userPath+File.separator+ID+".txt");
            myBookPath=userPath+File.separator+"My book";
            myRecordPath=userPath+File.separator+"My record";
            File myBook=new File(myBookPath);
            File myBookInfo=new File(myBookPath+File.separator+"bookInfo.txt");
            File myRecord=new File(myRecordPath);
            try(BufferedWriter writeUser = new BufferedWriter(new FileWriter(userListPath));
                BufferedWriter writePassword = new BufferedWriter(new FileWriter(passwordPath))) {
                if (!myInfo.createNewFile() || !myBook.mkdir() || !myBookInfo.createNewFile() || !myRecord.mkdir()){
                    System.out.println("Fail to create file! Other operations might fail!");
                    System.out.println("For the security of the data, system exit!");
                    System.exit(1);
                }
                userInfo.load(readUser);
                userInfo.setProperty(ID,name);
                passwordInfo.load(readPassword);
                passwordInfo.setProperty(ID,password);
                userInfo.store(writeUser,"Last time refreshed at:");
                passwordInfo.store(writePassword,"Last time refreshed at:");

                if (putMyInfo()){
                    System.out.println("Register Successfully!");//Succeed!
                    System.out.println("Please remind:");
                    System.out.println("ID: "+ID);
                    System.out.println("Password: "+password);
                    System.out.println("Write them down!!!");
                    loadMyBookInfo();
                }
                else
                    System.out.println("Error! Fail to register!");
            } catch (IOException e) {
                System.out.println("IOException is threw!!");
                e.printStackTrace();
            }
        }
        else
            System.out.println("Fail to create a folder! Other operations might fail!");
    }

    public void newID(){
        if (ID==null){
            String ID=String.valueOf((int)(Math.floor(Math.random()*(10000000-1000000))+1000000));
            while (!validateID(ID)){
                ID=String.valueOf((int)(Math.floor(Math.random()*(10000000-1000000))+1000000));
            }
            this.ID=ID;
            System.out.println("Your ID is "+ID);
        }
    }

    public String getID() {
        return ID;
    }

    public void setOrUpdateName() {
        boolean update=false;
        boolean pass = false;
        String name;
        if (this.name==null)
            System.out.println("What's your name?");
        else{
            update=true;
            System.out.println("Enter the new name!");
        }
        while (!pass){
            System.out.print("Enter:");
            name=input.nextLine();
            if(User.certifyName(name)){
                this.name=name;
                pass=true;
            }
            else {
                System.out.println(name+" is already existed! Please reset your name!");
                pass=false;
                choice1();
            }
        }
        if (update){
            try(BufferedWriter bw=new BufferedWriter(new FileWriter(userListPath))) {
                userInfo.setProperty(ID,this.name);
                userInfo.store(bw,"Last time refreshed at:");
                putMyInfo();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setOrUpdatePassword() {
        boolean pass=false;
        boolean update=false;
        String password;
        if (this.password!=null){//Non-null must mean it is a existed account, otherwise new account
            update=true;
            System.out.println("Please enter your current password:");
            while (true){
                System.out.print("Enter:");
                password=input.nextLine();
                if (!password.equals(this.password)){
                    System.out.println("Password incorrect!");
                    choice1();
                }
                else break;
            }
        }
        System.out.println("Set your password!");
        while (!pass){
            System.out.print("Enter:");
            password=input.nextLine();
            if (password.matches(User.regex)){
                System.out.println("Please enter it again!");
                System.out.print("Enter:");
                String temp=input.nextLine();
                if (temp.equals(password)){
                    System.out.println("Password setting process done!");
                    this.password=password;
                    pass=true;
                }
                else {
                    System.out.println("The password you enter is different with the first one! Please reset!");
                    choice1();
                }
            }
            else{
                System.out.println("Your password is not consistent with regulation! Please reset:");
                choice1();
            }
        }
        if (update){
            try(BufferedWriter bw=new BufferedWriter(new FileWriter(passwordPath))) {
                passwordInfo.setProperty(ID,this.password);
                passwordInfo.store(bw,"Last time refreshed at:");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getPassword(){
        return password;
    }

    public void setGender(){
        System.out.println("Male or Female?");
        String gender = this.gender;
        while (choice2()){
            gender=input.next();
            if (gender.toLowerCase().equals("male") || gender.toLowerCase().equals("female")){
                this.gender=gender;
                break;
            }
            else
                System.out.println("What you enter is illegal!");
        }
    }

    public String getGender(){
        return gender;
    }

    public void setBirthday(){
        System.out.println("When is your birthday:");
        Calendar birthday=null;
        while (choice2()){
            int year,month,day;
            try {
                System.out.print("Year:");
                year=input.nextInt();
                System.out.print("Month:");
                month=input.nextInt();
                System.out.print("Day:");
                day=input.nextInt();
                birthday=Calendar.getInstance();
                birthday.set(Calendar.YEAR,year);
                birthday.set(Calendar.MONTH,month);
                birthday.set(Calendar.DAY_OF_MONTH,day);
                this.birthday=birthday;
                break;
            }
            catch (InputMismatchException e){
                System.out.println("What you enter is illegal! Please reenter!");
            }
        }
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public void setAboutMe(){
        System.out.println("Say something about you so others may see!");
        String aboutMe;
        if (choice2()){
            aboutMe=input.nextLine();
            this.aboutMe=aboutMe;
        }
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public int getNumberOfBook() {
        return numberOfBook;
    }

    public GlossaryBook getBook() {
        return book;
    }

    public String getMyBookPath() {
        return myBookPath;
    }

    public String getMyRecordPath() {
        return myRecordPath;
    }

    public String getUserPath() {
        return userPath;
    }

    public boolean isHasRecord() {
        return hasRecord;
    }

    public void setHasRecord(boolean hasRecord) {
        this.hasRecord = hasRecord;
    }

    public void setMyInfo(){
        System.out.println("Now Let's complete your information!");
        setOrUpdateName();
        setGender();
        setBirthday();
        setAboutMe();
    }

    public boolean putMyInfo(){//Must invoke it after new user register!
        //All the info have been certified
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(myInfo))) {
            StringBuilder my=new StringBuilder(50);
            my.append("ID: ").append(ID).append("\n");
            my.append("Name: ").append(name).append("\n");
            my.append("Gender: ").append(gender).append("\n");
            if (birthday!=null){
                int year=birthday.get(Calendar.YEAR);
                int month=birthday.get(Calendar.MONTH);
                int day=birthday.get(Calendar.DAY_OF_MONTH);
                my.append("Birthday: ").append(year).append(".").append(month).append(".").append(day).append("\n");
            }
            else
                my.append("Birthday: ").append("Unknown").append("\n");
            if (aboutMe==null)
                my.append("About me: ").append("\n");
            else
                my.append("About me: ").append(aboutMe).append("\n");
            bw.write(my.toString());
            return true;
        } catch (IOException e) {
            System.out.println("Fail to put for IOException!");
            e.printStackTrace();
        }
        return false;
    }

    private static boolean validateID(String ID){//drop repeated ID
        if (ID==null)
            return false;
        return !userInfo.containsKey(ID);
    }

    public static boolean certifyID(String ID){//Certify entered ID
        if (ID==null)
            return false;
        if (ID.length()!=7)
            return false;
        for (int i=0;i<ID.length();i++){
            if (!Character.isDigit(ID.charAt(i)))
                return false;
        }
        return userInfo.containsKey(ID);
    }

    public static boolean certifyName(String name){//Certify new name
        if (name==null)
            return false;
        return !userInfo.contains(name);
    }

    public static boolean certifyPassword(String ID,String password){//ID must be certified first, Certify entered password
        if (password==null)
            return false;
        if (password.length()<6 || password.length()>16)
            return false;
        for (int i=0;i<password.length();i++){
            if (!Character.isLetterOrDigit(password.charAt(i)))
                return false;
        }
        return passwordInfo.getProperty(ID).equals(password);
    }

    private boolean setAndCheckPath() {
        this.userPath =System.getProperty("user.dir")+File.separator+"src"+File.separator+"user"+File.separator+ID;//Folder
        File info=new File(userPath);
        if (info.exists())
            return true;
        else
            return info.mkdir();
    }

    private void loadMyBookInfo(){//Make sure invoke from inside, first invoke when object is new
        try(BufferedReader bookReader=new BufferedReader(new FileReader(myBookPath+File.separator+"bookInfo.txt"))) {
            bookLoader=new Properties();
            bookLoader.load(bookReader);
            numberOfBook=bookLoader.size();
        } catch (IOException e) {
            System.out.println("IOException occurs! Book loading process fail!");
        }
    }

    private boolean refreshMyBookInfo(){
        if (numberOfBook==0)
            return false;
        try(BufferedWriter bookWriter=new BufferedWriter(new FileWriter(myBookPath+File.separator+"bookInfo.txt"))) {
            bookLoader.store(bookWriter,"Last time refreshed at:");
        } catch (IOException e) {
            System.out.println("IOException occurs!");
            return false;
        }
        return true;
    }


    public boolean createMyBook(String bookName){
        if (bookName.equals("")){
            System.out.println("You can name it nothing!");
            return false;
        }
        if (bookLoader.contains(bookName)){
            System.out.println("You already got a book name "+bookName+"!");
            return false;
        }
        if (GlossaryBook.certifyName(bookName)){
            System.out.println("There is a public book name "+bookName+"!");
            return false;
        }
        loadMyBookInfo();
        numberOfBook++;
        bookLoader.setProperty(String.valueOf(numberOfBook),bookName);
        File bookF=new File(myBookPath+File.separator+bookName);
        File book=new File(myBookPath+File.separator+bookName+File.separator+bookName+".txt");//The place where save it
        try {
            if (!bookF.mkdir() || !book.createNewFile()){
                System.out.println("Fail to create file!");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Create successfully!");
        return refreshMyBookInfo();
    }

    public boolean selectByName(String name){
        if (GlossaryBook.publicBookLoader.contains(name)){
            book=new GlossaryBook(this,name);
            return true;
        }
        if (bookLoader.contains(name)){
            book=new GlossaryBook(this,name);
            return true;
        }
        return false;
    }

    public boolean selectPublicBook(int key){
        String sKey=String.valueOf(key);
        if (GlossaryBook.publicBookLoader.containsKey(sKey)){
            book=new GlossaryBook(this,GlossaryBook.publicBookLoader.getProperty(sKey));
            return true;
        }
        else {
            System.out.println("Input illegal! Please enter again!");
            return false;
        }
    }

    public boolean selectByKey(int key){//Select my book
        String sKey=String.valueOf(key);
        if (bookLoader.containsKey(sKey)){
            book=new GlossaryBook(this,bookLoader.getProperty(sKey));
            return true;
        }
        else {
            System.out.println("Input illegal! Please enter again!");
            return false;
        }
    }

    public int getPlan() {
        return plan;
    }

    public void setMyPlan(int plan){
        this.plan=plan;
    }

    public void useBook(int usage){
        String path=getMyRecordPath()+ File.separator+book.getName()+"(Record).txt";
        Examination e;
        if (!hasRecord)
            e=new Examination(this);
        else
            e=Examination.getRecord(path);
        switch (usage){
            case 1:study(e,path);break;
            case 2:review(e);break;
            case 3:exam(e,path);break;
            case 4:viewMyRecord(e);break;
            default:System.out.println("Error!");
        }
    }
    public void study(Examination e,String path){
        e.studyHelper();
        e.saveRecord(path);
    }

    public void review(Examination e){
        e.reviewHelper();
    }

    public void exam(Examination e,String path){
        e.testHelper();
        e.saveRecord(path);
    }

    public void viewMyRecord(Examination e){
        e.showMyRecord();
    }

    public void viewMyBook(){//First time load my book info
        if (numberOfBook!=0){
            for (int i=1;i<=numberOfBook;i++){
                System.out.print(i+". "+bookLoader.getProperty(String.valueOf(i))+" ");
                if (i%3==0)
                    System.out.println();
            }
            System.out.println();
        }
        else System.out.println("You do not have any books!");
    }

    public void viewPublicBook(){
        Set<String> pBook=GlossaryBook.publicBookLoader.stringPropertyNames();
        int i=1;
        for (String book : pBook) {
            System.out.print(i+". "+GlossaryBook.publicBookLoader.getProperty(book)+" ");
            if (i%3==0)
                System.out.println();
            i++;
        }
        System.out.println();
    }

    public static void choice1(){
        int choice;
        System.out.println("1.Continue  2.Exit");
        choice=input.nextInt();
        input.nextLine();//take \n
        try{
            switch (choice){
                case 1:break;
                case 2:quit(0);break;
                default:throw new InputMismatchException();
            }
        }
        catch (InputMismatchException e){
            System.out.println("Illegal input!!");
            input.nextLine();
        }
    }

    public static boolean choice2(){
        int choice;
        System.out.println("1.Continue  2.Skip  3.Exit");
        choice=input.nextInt();
        input.nextLine();//take \n
        try{
            switch (choice){
                case 1:System.out.print("Enter:");return true;
                case 2:return false;
                case 3:quit(1);
                default:throw new InputMismatchException();
            }
        }
        catch (InputMismatchException e){
            System.out.println("Illegal input!! Skipping...... You can reset later!");
            input.nextLine();
            return false;
        }
    }

    public static void quit(int index){
        try {
            readUser.close();
            readPassword.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (index==0)
            System.out.println("See you next time! Bye!--------------------------------");
        System.exit(index);
    }
}

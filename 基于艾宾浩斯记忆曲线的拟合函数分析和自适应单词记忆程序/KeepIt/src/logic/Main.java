package logic;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    static Scanner input=new Scanner(System.in);
    public static void main(String[] args) {
        User user=new User("1000000","ad1000000");//Administrator
        interfaceI();

    }
    private static void interfaceI(){//Get user for the access
        User user = null;
        int choice;
        System.out.println("Welcome to keepIt! Let's go to get vocabularies done!");
        while (true){
            try {
                System.out.println("------------------------------------------------------------------------------");
                System.out.println("1.Login 2.Register 3.Exit");
                choice=input.nextInt();
                input.nextLine();//take \n
                switch (choice){
                    case 1: user=login();break;
                    case 2: user=register();break;
                    case 3: User.quit(0);break;
                    default: throw new InputMismatchException("Wrong");
                }
                break;
            }
            catch (InputMismatchException e){
                System.out.println("Illegal input!! Please enter again!");
                if (!"Wrong".equals(e.getMessage()))
                    input.nextLine();
            }
        }
        interfaceII(Objects.requireNonNull(user));
    }
    private static void interfaceII(User user){//All users have these options
        int choice;
        while (true){
            try {
                System.out.println("------------------------------------------------------------------------------");
                System.out.println("1.Study 2.Review 3.Exam 4.Show my record 5.Manage my book 6.Change name 7.Change password 8.Log out 9.Exit");
                choice=input.nextInt();
                input.nextLine();//take \n
                switch (choice){
                    case 1:
                    case 2:
                    case 3:
                    case 4: selectBookInterface(user,choice);break;
                    case 5: interfaceIII(user);return;
                    case 6: user.setOrUpdateName();break;
                    case 7: user.setOrUpdatePassword();break;
                    case 8: interfaceI();return;
                    case 9: User.quit(0);return;
                    default:throw new InputMismatchException("Wrong");
                }
            }
            catch (InputMismatchException e){
                System.out.println("Illegal input!! Please enter again!");
                if (!"Wrong".equals(e.getMessage()))
                    input.nextLine();
            }
        }
    }

    private static void selectBookInterface(User user,int usage){
        int choice,choice1;boolean result=false;
        System.out.println("Public book:-----------------------------------");
        user.viewPublicBook();
        System.out.println("Your book:-------------------------------------");
        user.viewMyBook();
        System.out.println("-----------------------------------------------");
        System.out.println("Please choose 1.Public book   2.My book");
        while (true){//Select a type of book
            try {
                System.out.print("Enter:");
                choice=input.nextInt();
                input.nextLine();
                switch (choice){
                    case 1:
                    case 2:System.out.println("Select a book!(Enter the index):");break;
                    default:throw new InputMismatchException("Wrong");
                }
                break;
            }
            catch (InputMismatchException e){
                System.out.print("Illegal input! please enter again!");
                if (!"Wrong".equals(e.getMessage()))
                input.nextLine();
            }
        }
        while (true){//Choose a book to study/review exam! If choose succeed, this user's book is refresh
            try {
                do {
                    System.out.print("Enter:");
                    choice1=input.nextInt();
                    input.nextLine();
                    switch (choice){
                        case 1:result=user.selectPublicBook(choice1);break;
                        case 2:result=user.selectByKey(choice1);break;
                        default:throw new InputMismatchException("Wrong");
                    }
                }while (!result);
                //Book is already picked!!
                break;
            }
            catch (InputMismatchException e){
                System.out.println("Input illegal! Please enter again!");
                if (!"Wrong".equals(e.getMessage()))
                    input.nextLine();
            }
        }
        checkPlanInterface(user);
        user.useBook(usage);
    }

    private static void checkPlanInterface(User user){//Check if or not user got a plan
        //When this function finish, user already got a plan(forcibly)
        if (user.getPlan()==0){
            int plan;
            while (true){
                try{
                    System.out.println("Please set a plan, how many words you should remember one day!");
                    plan=input.nextInt();
                    input.nextLine();
                    if (plan>0){
                        user.setMyPlan(plan);
                        System.out.println("You plan is already! Let's start!");
                        break;
                    }
                    else {
                        System.out.println("Plan cannot be negative!");
                    }
                }
                catch (InputMismatchException e){
                    System.out.println("Illegal input! Please enter again!");
                }
            }

        }
    }

    private static void interfaceIII(User user){//Certify and return an order
        int choice;
        System.out.println("------------------------------------------------------------------------------");
        while (true){
            try {
                System.out.println("1.View my book 2.Update my book 3.Make a new book 4.Return");
                choice=input.nextInt();
                input.nextLine();//take \n
                switch (choice){
                    case 1: user.viewMyBook();break;
                    case 2: if (user.getNumberOfBook()==0){
                                System.out.println("You do not have any book!");
                            }
                            else{
                                System.out.println("Select a book(Enter the index):");
                                user.viewMyBook();
                                int choice1;
                                do {
                                    System.out.print("Enter:");
                                    choice1=input.nextInt();
                                    input.nextLine();
                                }while (!user.selectByKey(choice1));
                                interfaceIV(user,user.getBook());
                                return;
                            }
                            break;

                    case 3: System.out.println("Please make this book a name!");
                            String name;
                            do {
                                System.out.print("Enter:");
                                name=input.nextLine();
                            }while (!user.createMyBook(name));
                            break;

                    case 4: interfaceII(user);
                    default:throw new InputMismatchException("Wrong");
                }
            }
            catch (InputMismatchException e){
                System.out.println("Illegal input!! Please enter again!");
                if (!"Wrong".equals(e.getMessage()))
                    input.nextLine();
            }
        }
    }

    private static void interfaceIV(User user,GlossaryBook book){//Modify book interface
        int choice;
        System.out.println("------------------------------------------------------------------------------");
        while (true){
            try {
                System.out.println("1.Add word 2.Delete word 3.return");
                choice=input.nextInt();
                input.nextLine();//take \n
                switch (choice){
                    case 1: String word="",meaning="";
                            do {
                                System.out.print("Please enter this word:");
                                word=input.nextLine();
                                System.out.print("Please enter it's meaning:");
                                meaning=input.nextLine();
                            }while (!book.addWord(word,meaning));
                            break;

                    case 2:
                            do {
                                System.out.print("Please enter this word:");
                                word = input.nextLine();
                            } while (!book.deleteWord(word));
                            break;

                    case 3: interfaceIII(user);return;

                    default:throw new InputMismatchException("Wrong");
                }
                break;
            }
            catch (InputMismatchException e){
                System.out.println("Illegal input!! Please enter again!");
                if (!"Wrong".equals(e.getMessage()))
                    input.nextLine();
            }
        }
        interfaceIII(user);
    }
    private static User login(){//User is already existed, so new it later
        String ID = null;
        String password = null;
        boolean pass=false;
        while (!pass){
            while (!pass){
                System.out.println("Please enter your ID:");
                System.out.print("Enter:");
                ID=input.next();
                if (User.certifyID(ID))
                    pass=true;
                else{
                    System.out.println("The ID you enter is wrong or non-existed! Try again:");
                    User.choice1();
                }
            }
            pass=false;
            while (!pass){
                System.out.println("Please enter your password");
                System.out.print("Enter:");
                password=input.next();
                if (User.certifyPassword(ID,password))
                    pass=true;
                else {
                    System.out.println("Password incorrect! Try again!");
                    User.choice1();
                }
            }
        }
        User user = new User(ID,password);
        System.out.println("Login Successfully! Welcome, "+user.getName());
        return user;
    }

    private static User register(){//User is not existed, so new it first
        return new User();
    }


}

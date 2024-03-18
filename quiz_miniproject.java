import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Question {
    String question;
    String answer;
}

public class Quiz {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to use the default question set or create a new one? (1: Default / 2: New)"); //asks user if they want an existing set/create new one
        int choice = scanner.nextInt();
        if (choice == 1) {
            // Uses existing questions
            Question[] questionList = questionList();
            playQuiz(questionList);
        } else if (choice == 2) {
            Question[] questionList = createNewQSet(); 
            System.out.println("Enter the file name to save the new set: ");
            String fileName = scanner.next();
            saveQsToFile(fileName, questionList); //creates new set and saves it to file
            playQuiz(questionList);
        }
    }
    
    public static void saveQsToFile(String fileName, Question[] questionList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Question q : questionList) {
                writer.println("Q: "+ q.question);
                writer.println("A: "+ q.answer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Question newQuestion(String Q, String a) {
        Question q = new Question();
        q.question = Q;
        q.answer = a;
        return q;
    }

    public static Question[] createNewQSet() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of questions in the new set: ");
        int num = scanner.nextInt();
        Question[] questionList = new Question[num];
        for (int i = 0; i < num; i++) {
            System.out.println("Enter question #" + (i + 1) + ": ");
            String q = scanner.next();
            System.out.println("Enter answer for question #" + (i + 1) + ": ");
            String a = scanner.next();
            questionList[i] = newQuestion(q, a);
        }
        return questionList;
    }

    public static String getQuestion(Question q) {
        return q.question;
    }

    public static String getAnswer(Question q) {
        return q.answer;
    }

    public static int Introduction() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the football quiz!");
        System.out.println("How many people are playing?");
        String users = scanner.nextLine();
        final int Users = Integer.parseInt(users); //stores how many users for loop length
        System.out.println();
        return Users;
    }

    public static int[] GMS() {
        int[] gmS = { 1, 2, 3 }; // increasing points for each question
        return gmS;
    }
    public static Question[] questionList(){
        Question[] questionlist = new Question[3];
        questionlist[0] = newQuestion(" Who won the Ballon D'or in 2022?", "Karim Benzema");
        questionlist[1] = newQuestion(" Which club won the UCL in 2004?", "Porto");
        questionlist[2] = newQuestion(" Which country has won the most World Cups?", "Brazil");
        return questionlist;
    }

    public static int[] Questions(int Users, int[] gmS, Question[] questionList) {
        int[] scores = new int[Users];
        for (int i = 0; i < Users; i++) { // repeats Q for every user
            for (int j = 0; j < questionList.length; j++) {
                String userAnswer = Input("Player #" + (i + 1) + ": " + getQuestion(questionList[j]));
                while (userAnswer.length() >= 15) {
                    System.out.println("Incorrect input! Please use less than 15 characters! ");
                    System.out.println();
                    userAnswer = Input("Player #" + (i + 1) + ": " + getQuestion(questionList[j]));
                }
                if (userAnswer.equals(getAnswer(questionList[j]))) {
                    scores[i] += gmS[j];
                    System.out.println("Correct! Score: " + scores[i]);
                    System.out.println();
                } else {
                    System.out.println("Incorrect! Score: " + scores[i]);
                    System.out.println();
                }
            }
        }
        return scores; // returns the scores in an array
    }

    public static void End(int[] scores, int Users) {
        System.out.println("Thank you for playing! The final scores are:");
        for (int i = 0; i < Users; i++) {
            System.out.println("Player #" + (i + 1) + ": " + scores[i]); // prints out all scores in a loop
        }
    }

    public static String Input(String question) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(question); // asks the user
        return scanner.nextLine();
    }

    public static void playQuiz(Question[] questionList) {
        int Users = Introduction();
        int[] gmS = GMS();
        int[] scores = Questions(Users, gmS, questionList);
        End(scores, Users);
    }
}

Quiz.main(null);
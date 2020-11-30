import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.text.*;
/**
@author Margaret Nixon, Jordan Garnett, Xuchu Ma, Myles St. Pierre
This class is the driver to a working prototype for an online quiz system.
*/
public class quizProgramDriver extends Application {
  public static void main(String[] args) {
      launch(args);
  }

/*
This method runs the main screen where users can select to take a quiz, or
login as an admin to edit a quiz.
*/
  public void start(Stage primaryStage) {
      primaryStage.setTitle("Quiz Program");
      
      //create button to lead to admin login screen
      Button btnAdmin = new Button();
      btnAdmin.setLayoutY(200);
      btnAdmin.setLayoutX(250);
      btnAdmin.setPrefSize(120, 50);
      btnAdmin.setText("Go to Admin Login");
      btnAdmin.setOnAction(e -> {
               primaryStage.close();
               openLoginOn_Click();
      });
      
      //create button to lead to user/player screen
      Button btnGoQuiz = new Button();
      btnGoQuiz.setLayoutY(100);
      btnGoQuiz.setLayoutX(250);
      btnGoQuiz.setPrefSize(120, 50);
      btnGoQuiz.setText("Do a quiz");
      btnGoQuiz.setOnAction(e -> goToQuizSelect_Click());
      
      //put buttons on pane then in scene
      Pane root = new Pane();
      root.getChildren().addAll(btnAdmin, btnGoQuiz);
      
      Scene scene = new Scene(root, 600, 400);
      primaryStage.setScene(scene);
      primaryStage.show();
  }
  /*
  This method opens the admin login.
  */
  public void openLoginOn_Click() {
    
    Stage loginStage = new Stage();
    loginStage.setTitle("Admin Login");
    
    //sets up textfields for username and password as well as a submit button
    Text txtUser = new Text("Username:");
    txtUser.setLayoutY(40);
    txtUser.setLayoutX(40);
    Text txtPswrd = new Text("Password:");
    txtPswrd.setLayoutY(90);
    txtPswrd.setLayoutX(40);
    TextField txtfUser = new TextField();
    txtfUser.setLayoutY(50);
    txtfUser.setLayoutX(40);
    PasswordField txtfPswrd = new PasswordField();
    txtfPswrd.setLayoutY(100);
    txtfPswrd.setLayoutX(40);
    Text correct = new Text("");
    correct.setLayoutY(190);
    correct.setLayoutX(40);
    
    Button btnLogin = new Button();
    btnLogin.setText("Login");
    btnLogin.setLayoutY(140);
    btnLogin.setLayoutX(40);
    btnLogin.setOnAction(e -> {
            String user = txtfUser.getText();
            String pass = txtfPswrd.getText();
            txtfUser.clear();
            txtfPswrd.clear();
            if(user.equals("admin") && pass.equals("admin")) //will replace if statement with a checkPass() method
            {
               loginStage.close();
               adminSettings();
            }
            else
            {
               correct.setText("Username or password incorrect!\nPlease try again.");
            }
    }); 

    Pane root = new Pane();
    root.getChildren().addAll(txtUser, txtPswrd, txtfUser, txtfPswrd, btnLogin, correct);
   
    Scene loginScene = new Scene(root, 300, 275);
    loginStage.setScene(loginScene);
    loginStage.show();
  }
    
   /**
   This will open up the admin page to edit quizzes.
   */
  public void adminSettings() {
    Stage adminStage = new Stage();
    adminStage.setTitle("Admin Settings");
    Text settingsDesc = new Text("Admin Settings:");
    settingsDesc.setLayoutY(20);
    settingsDesc.setLayoutX(110);
    
    //Make a quiz button
    Button btnMakeQuiz = new Button();
    btnMakeQuiz.setLayoutY(40);
    btnMakeQuiz.setLayoutX(100);
    btnMakeQuiz.setPrefSize(120, 50);
    btnMakeQuiz.setText("Create a quiz!");
    btnMakeQuiz.setOnAction(e -> {
            adminStage.close();
            makeQuiz_OnClick();
    });
      
    Pane root = new Pane();
    root.getChildren().addAll(btnMakeQuiz, settingsDesc);

    Scene adminScene = new Scene(root, 300, 275);
    adminStage.setScene(adminScene);
    adminStage.show();
  }

  public void makeQuiz_OnClick() {
      Stage makeQuizStage = new Stage();
      makeQuizStage.setTitle("Quiz Creation");
      Text quizName = new Text("Insert name of quiz:");
      quizName.setLayoutY(20);
      quizName.setLayoutX(85);
      Text numOfQ = new Text("Insert number of questions:");
      numOfQ.setLayoutY(100);
      numOfQ.setLayoutX(85);
      
      TextField quizfName = new TextField();
      quizfName.setLayoutY(35);
      quizfName.setLayoutX(85);
      
      Spinner<Integer> SnumOfQ = new Spinner<Integer>(1, 20, 1, 1);
      SnumOfQ.setLayoutY(120);
      SnumOfQ.setLayoutX(85);
      
      Button btnContinueQuiz = new Button();
      btnContinueQuiz.setLayoutY(150);
      btnContinueQuiz.setLayoutX(85);
      btnContinueQuiz.setPrefSize(150, 30);
      btnContinueQuiz.setText("Begin making questions");
      btnContinueQuiz.setOnAction(e -> {
            String title = quizfName.getText();
            int rounds = SnumOfQ.getValue();
            Quiz newQuiz = new Quiz(title, rounds);
            makeQuizStage.close();
            
            for(int i = 1; i <= rounds; i++)
               newQuiz.addQ(makeQuestion(i));
              
            //we will probably add newQuiz to a list of all quizzes here
            adminSettings();
    });
      
      Pane root = new Pane();
      root.getChildren().addAll(quizName, numOfQ, quizfName, SnumOfQ, btnContinueQuiz);
      
      Scene makeQuizScene = new Scene(root, 300, 250);
      makeQuizStage.setScene(makeQuizScene);
      makeQuizStage.show();
   }
   
   public Question makeQuestion(int round)  {
      Stage makeQuestionStage = new Stage();
      makeQuestionStage.setTitle("Question Creation");
      Question newQuestion = new Question();
      
      Text questionQtitle = new Text("Insert Question #" + round + ":");
      questionQtitle.setLayoutY(20);
      questionQtitle.setLayoutX(85);
      Text answerAtitle = new Text("Answer A:");
      answerAtitle.setLayoutY(80);
      answerAtitle.setLayoutX(85);
      Text answerBtitle = new Text("Answer B:");
      answerBtitle.setLayoutY(140);
      answerBtitle.setLayoutX(85);
      Text answerCtitle = new Text("Answer C:");
      answerCtitle.setLayoutY(200);
      answerCtitle.setLayoutX(85);
      Text answerDtitle = new Text("Answer D:");
      answerDtitle.setLayoutY(260);
      answerDtitle.setLayoutX(85);
      Text correctTitle = new Text("Select Correct Answer:");
      correctTitle.setLayoutY(310);
      correctTitle.setLayoutX(85);
      
      TextField questionQ = new TextField();
      questionQ.setLayoutY(35);
      questionQ.setLayoutX(10);
      questionQ.setPrefWidth(280);
      TextField answerA = new TextField();
      answerA.setLayoutY(90);
      answerA.setLayoutX(85);
      TextField answerB = new TextField();
      answerB.setLayoutY(150);
      answerB.setLayoutX(85);
      TextField answerC = new TextField();
      answerC.setLayoutY(210);
      answerC.setLayoutX(85);
      TextField answerD = new TextField();
      answerD.setLayoutY(270);
      answerD.setLayoutX(85);
      
      ComboBox<Character> correctAns = new ComboBox<Character>();
      correctAns.getItems().addAll('A', 'B', 'C', 'D');
      correctAns.setLayoutY(320);
      correctAns.setLayoutX(85);
      
      
      Button btnContinueQuestion = new Button();
      btnContinueQuestion.setLayoutY(360);
      btnContinueQuestion.setLayoutX(85);
      btnContinueQuestion.setPrefSize(150, 30);
      btnContinueQuestion.setText("Continue");
      btnContinueQuestion.setOnAction(e -> {
               String title = questionQ.getText();
               String A = answerA.getText();
               String B = answerB.getText();
               String C = answerC.getText();
               String D = answerD.getText();
               int answer;
               switch(correctAns.getValue())  {
                  case 'A':
                     answer = 1;
                     break;
                  case 'B':
                     answer = 2;
                     break;
                  case 'C':
                     answer = 3;
                     break;
                  case 'D':
                     answer = 4;
                     break;
                  default:
                     answer = 1;
                     break; 
               }
               
               newQuestion.makeQuestion(title, A, B, C, D, answer);
               makeQuestionStage.close();
      });
      
      Pane root = new Pane();
      root.getChildren().addAll(questionQtitle, answerAtitle, answerBtitle, answerCtitle, answerDtitle,
                             questionQ, answerA, answerB, answerC, answerD,
                             btnContinueQuestion, correctAns, correctTitle);
      
      Scene makeQuestionScene = new Scene(root, 300, 500);
      makeQuestionStage.setScene(makeQuestionScene);
      makeQuestionStage.showAndWait();
      return newQuestion;
  }
  
  
  //Unfinished regular user screens are below this point
  
  
  
  /*
  This goes to waiting screen for quiz.
  */
  public void goToQuizSelect_Click() {
    Stage waitStage = new Stage();
    waitStage.setTitle("Waiting for Admin");
    Text txtWait = new Text("Please Wait for Quiz to Start");
    StackPane root = new StackPane();
    root.getChildren().add(txtWait);
    Scene waitScene = new Scene(root, 300, 275);
    waitStage.setScene(waitScene);
    waitStage.show();
  }
/**
This starts the quiz.
*/
  public void goToQuizOn_Click() { //this needs to only start if admin clicks start once server up
    //these variables call functions to get the next question and answers
    String question = "This will be calling a function"; ////////////////////////////
    String ans1 = "function";
    String ans2 = "function";
    String ans3 = "function";
    String ans4 = "function";
    StackPane root = new StackPane();
    Scene quizScene = new Scene(root, 300, 275);

    Stage quizStage = new Stage();
    quizStage.setTitle("Quiz");
    Text question1 = new Text(question); //question
    Button btnOption1 = new Button();
    btnOption1.setText(ans1); //answer 1
    //(^-^)btnOption1.setOnAction(e -> checkScoreOn_Click( 1, quizStage, quizScene));
    Button btnOption2 = new Button();
    btnOption2.setText(ans2); //answer 2
    //(^-^)btnOption2.setOnAction(e -> checkScoreOn_Click( 2, quizStage, quizScene));
    Button btnOption3 = new Button();
    btnOption3.setText(ans3); //answer 3
    //(^-^)btnOption3.setOnAction(e -> checkScoreOn_Click( 3, quizStage, quizScene));
    Button btnOption4 = new Button();
    btnOption4.setText(ans4); //answer 4
    //(^-^)btnOption4.setOnAction(e -> checkScoreOn_Click( 4, quizStage, quizScene));

    //tackPane root = new StackPane();
    root.getChildren().add(question1);
    root.getChildren().add(btnOption1);
    root.getChildren().add(btnOption2);
    root.getChildren().add(btnOption3);
    root.getChildren().add(btnOption4);

    //Scene quizScene = new Scene(root, 300, 275); i change to the front
    quizStage.setScene(quizScene);
    quizStage.show();
  }
  /*
  This method will show scores then move to the next question.
  */
  public void checkScoreOn_Click(int ansNumb) {
    //get right answer from server, temporarily hard-coded /////////////////////////////////
    int realAns = 2;
    boolean ansRight = false;
    if (realAns == ansNumb) {
      ansRight = true;
    }
    //send score somehow, then receive scores for everyone as a string/////////////////////
    String allScores = "This will be everyones score"; /////////////while scores are displayed, next question should open back on quizStage
    Stage scoreStage = new Stage();
    scoreStage.setTitle("Scoreboard");
    Text scores = new Text(allScores);
    Text closeWhen = new Text("When done viewing current scores,"
        + " please close this window and return to next question.");

    StackPane root = new StackPane();
    root.getChildren().add(scores);
    root.getChildren().add(closeWhen);

    Scene scoreScene = new Scene(root, 300, 275);
    scoreStage.setScene(scoreScene);
    scoreStage.show();

    //then they close this window and go to quizStage which has a new question ///////////

  }
  
}
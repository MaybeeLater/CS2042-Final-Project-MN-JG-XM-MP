import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.text.*;
import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter; //Not sure why this wouldn't import with java.time.*;

/**
 * @author Margaret Nixon, Jordan Garnett, Xuchu Ma, Myles St. Pierre This class
 *         is the driver to a working prototype for an online quiz system.
 */

public class quizProgramDriver extends Application {

   ArrayList<Quiz> quizList = new ArrayList<Quiz>();
   public boolean ansCorrect;
   public static Stage pStage;

   public static void main(String[] args) {
      launch(args);
   }

   /*
    * This method runs the main screen where users can select to take a quiz, or
    * login as an admin to edit a quiz.
    */
   public void start(Stage primaryStage) {
      primaryStage.setTitle("Quiz Program");
      pStage = primaryStage;

      // load quizzes
      try {
         FileInputStream fi = new FileInputStream(new File("QuizStorage.txt"));
         ObjectInputStream oi = new ObjectInputStream(fi);

         quizList = (ArrayList<Quiz>) oi.readObject();
         oi.close();
         fi.close();
      } catch (FileNotFoundException ie) {
      } catch (IOException ie) {
      } catch (ClassNotFoundException ie) {
      }
      // create button to lead to admin login screen
      Button btnAdmin = new Button();
      btnAdmin.setLayoutY(200);
      btnAdmin.setLayoutX(235);
      btnAdmin.setPrefSize(120, 50);
      btnAdmin.setText("Go to Admin Login");
      btnAdmin.setOnAction(e -> {
         primaryStage.close();
         openLoginOn_Click();
      });

      // create button to lead to user/player screen
      Button btnGoQuiz = new Button();
      btnGoQuiz.setLayoutY(100);
      btnGoQuiz.setLayoutX(235);
      btnGoQuiz.setPrefSize(120, 50);
      btnGoQuiz.setText("Do a quiz");
      btnGoQuiz.setOnAction(e -> {
         primaryStage.close();
         goToQuizSelect_Click();
      });

      // put buttons on pane then in scene
      Pane root = new Pane();
      root.getChildren().addAll(btnAdmin, btnGoQuiz);

      Scene scene = new Scene(root, 600, 400);
      primaryStage.setScene(scene);
      primaryStage.show();
   }

   // This method opens the admin login.
   public void openLoginOn_Click() {

      Stage loginStage = new Stage();
      loginStage.setTitle("Admin Login");

      // sets up textfields for username and password as well as a submit button
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
         if (user.equals("admin") && pass.equals("admin")) // will replace if statement with a checkPass() method
         {
            loginStage.close();
            adminSettings();
         } else {
            correct.setText("Username or password incorrect!\nPlease try again.");
         }
      });

      Button btnReturn = new Button();
      btnReturn.setText("Return");
      btnReturn.setOnAction(e -> {
         loginStage.close();
         start(pStage);
      });

      Pane root = new Pane();
      root.getChildren().addAll(txtUser, txtPswrd, txtfUser, txtfPswrd, btnLogin, correct, btnReturn);

      Scene loginScene = new Scene(root, 300, 275);
      loginStage.setScene(loginScene);
      loginStage.show();
   }

   // This will open up the admin page to manage quizzes and results.
   public void adminSettings() {
      Stage adminStage = new Stage();
      adminStage.setTitle("Admin Settings");
      Text settingsDesc = new Text("Admin Settings:");
      settingsDesc.setLayoutY(20);
      settingsDesc.setLayoutX(100);
      Text emptyCheck = new Text("");
      emptyCheck.setLayoutY(110);
      emptyCheck.setLayoutX(93);

      // Make a quiz button
      Button btnMakeQuiz = new Button();
      btnMakeQuiz.setLayoutY(40);
      btnMakeQuiz.setLayoutX(85);
      btnMakeQuiz.setPrefSize(120, 50);
      btnMakeQuiz.setText("Create a quiz!");
      btnMakeQuiz.setOnAction(e -> {
         adminStage.close();
         makeQuiz_OnClick();
      });

      // Make a view quiz button
      ComboBox<String> boxList = new ComboBox<String>();
      if (!quizList.isEmpty()) {
         for (int i = 0; i < quizList.size(); i++) {
            boxList.getItems().add(quizList.get(i).QuizTitle);
         }
      }
      boxList.getSelectionModel().selectFirst();

      Button btnReturn = new Button();
      btnReturn.setText("Log out");
      btnReturn.setOnAction(e -> {
         adminStage.close();
         start(pStage);
      });

      boxList.setLayoutY(100);
      boxList.setLayoutX(40);
      boxList.setPrefSize(170, 20);

      Button btnViewQuiz = new Button();
      btnViewQuiz.setLayoutY(100);
      btnViewQuiz.setLayoutX(220);
      btnViewQuiz.setPrefSize(60, 20);
      btnViewQuiz.setText("View");
      btnViewQuiz.setOnAction(e -> {
         adminStage.close();
         viewQuiz_OnClick(boxList.getValue());
      });

      // This button will save the quiz questions and return to main screen
      Button btnSaveChanges = new Button();
      btnSaveChanges.setLayoutY(220);
      btnSaveChanges.setLayoutX(85);
      btnSaveChanges.setPrefSize(120, 50);
      btnSaveChanges.setText("Save Changes");
      btnSaveChanges.setOnAction(e -> {
         try {
            FileOutputStream f = new FileOutputStream(new File("QuizStorage.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(quizList);
            o.close();
         } catch (FileNotFoundException ie) {
         } catch (IOException ie) {
         }
         adminStage.close();
         start(pStage);
      });

      Pane root = new Pane();
      root.getChildren().addAll(btnReturn, btnMakeQuiz, settingsDesc, emptyCheck, btnSaveChanges);
      if (!quizList.isEmpty()) {
         root.getChildren().addAll(btnViewQuiz, boxList);
      } else
         emptyCheck.setText("No quizzes loaded");

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

      Text checkName = new Text("");

      Text numOfQ = new Text("Insert number of questions:");
      numOfQ.setLayoutY(100);
      numOfQ.setLayoutX(85);
      Text passOfQ = new Text("Set number of questions to pass:");
      passOfQ.setLayoutY(170);
      passOfQ.setLayoutX(85);
      Text tNumTeam = new Text("Set the number of teams:");
      tNumTeam.setLayoutY(220);
      tNumTeam.setLayoutX(85);

      TextField quizfName = new TextField();
      quizfName.setLayoutY(35);
      quizfName.setLayoutX(85);

      Spinner<Integer> SnumOfQ = new Spinner<Integer>(1, 30, 1, 1);
      SnumOfQ.setLayoutY(120);
      SnumOfQ.setLayoutX(85);

      Spinner<Integer> SnumPass = new Spinner<Integer>(1, 20, 1, 1);
      SnumPass.setLayoutY(180);
      SnumPass.setLayoutX(85);

      Spinner<Integer> SnumTeams = new Spinner<Integer>(1, 3, 2, 1);
      SnumTeams.setLayoutY(235);
      SnumTeams.setLayoutX(85);

      Button btnContinueQuiz = new Button();
      btnContinueQuiz.setLayoutY(300);
      btnContinueQuiz.setLayoutX(85);
      btnContinueQuiz.setPrefSize(150, 30);
      btnContinueQuiz.setText("Begin making questions");
      btnContinueQuiz.setOnAction(e -> {
         if (quizfName.getText().equals("")) {
            checkName.setLayoutY(290);
            checkName.setLayoutX(85);
            checkName.setText("Please give a name for the quiz!");
         } else if (SnumOfQ.getValue() % SnumTeams.getValue() != 0) {
            checkName.setLayoutY(290);
            checkName.setLayoutX(25);
            checkName.setText("Teams will have an uneven amount of questions!");
         } else {
            String title = quizfName.getText();
            int rounds = SnumOfQ.getValue();
            int passNum = SnumPass.getValue();
            int numOfTeams = SnumTeams.getValue();
            Quiz newQuiz = new Quiz(title, rounds, passNum, numOfTeams);
            makeQuizStage.close();

            for (int i = 1; i <= rounds; i++)
               newQuiz.addQ(makeQuestion(i));

            quizList.add(newQuiz);
            adminSettings();
         }
      });

      Button btnReturn = new Button();
      btnReturn.setText("Return");
      btnReturn.setOnAction(e -> {
         makeQuizStage.close();
         adminSettings();
      });

      Pane root = new Pane();
      root.getChildren().addAll(btnReturn, quizName, numOfQ, quizfName, SnumOfQ, SnumPass, passOfQ, btnContinueQuiz,
            checkName, SnumTeams, tNumTeam);

      Scene makeQuizScene = new Scene(root, 300, 350);
      makeQuizStage.setScene(makeQuizScene);
      makeQuizStage.show();
   }

   public Question makeQuestion(int round) {
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
      Text setQandA = new Text("");
      setQandA.setLayoutY(410);
      setQandA.setLayoutX(85);

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
      correctAns.setValue('A');
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
         switch (correctAns.getValue()) {
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
         if (title.equals("")) {
            setQandA.setText("Please enter a question!");
         } else {
            newQuestion.makeQuestion(title, A, B, C, D, answer);
            makeQuestionStage.close();
         }
      });

      Pane root = new Pane();
      root.getChildren().addAll(questionQtitle, answerAtitle, answerBtitle, answerCtitle, answerDtitle, questionQ,
            answerA, answerB, answerC, answerD, btnContinueQuestion, correctAns, correctTitle, setQandA);

      Scene makeQuestionScene = new Scene(root, 300, 500);
      makeQuestionStage.setScene(makeQuestionScene);
      makeQuestionStage.showAndWait();
      return newQuestion;
   }

   public void viewQuiz_OnClick(String q) {
      Stage viewQuizStage = new Stage();
      viewQuizStage.setTitle("Quiz Viewer");

      int index = 0;
      // finds which quiz was selected
      // this may cause issues with identical named quizzes, should implement fix
      for (int j = 0; j < quizList.size(); j++)
         if (q.equals(quizList.get(j).QuizTitle))
            index = j;

      final int finalIndex = index;

      Text quizQuestion = new Text("Quiz Name = " + quizList.get(index).QuizTitle);
      quizQuestion.setLayoutX(280);
      quizQuestion.setLayoutY(20);

      Text passNumber = new Text("# of points needed to pass = " + quizList.get(index).passNumber);
      passNumber.setLayoutX(530);
      passNumber.setLayoutY(20);

      Text teamNumber = new Text("# of teams in this quiz = " + quizList.get(index).numTeams);
      teamNumber.setLayoutX(530);
      teamNumber.setLayoutY(40);

      Text[] temp = new Text[quizList.get(index).list.size()];
      int x = 20;
      int y = 60;
      for (int k = 0; k < quizList.get(index).list.size(); k++) {
         temp[k] = new Text(k + 1 + ". " + quizList.get(index).list.get(k).questionTitle);
         temp[k].setLayoutX(x);
         temp[k].setLayoutY(y);
         if (k == 9) {
            x = 350;
            y = 40;
         }
         if (k == 19) {
            x = 670;
            y = 40;
         }
         y += 20;
      }

      Button btnDeleteButton = new Button();
      btnDeleteButton.setLayoutX(20);
      btnDeleteButton.setLayoutY(260);
      btnDeleteButton.setPrefSize(150, 30);
      btnDeleteButton.setText("Delete Quiz?");
      btnDeleteButton.setOnAction(e -> {
         quizList.remove(finalIndex);
         viewQuizStage.close();
         adminSettings();
      });

      Button btnReturn = new Button();
      btnReturn.setText("Return");
      btnReturn.setOnAction(e -> {
         viewQuizStage.close();
         adminSettings();
      });

      Pane root = new Pane();
      for (int i = 0; i < temp.length; i++)
         root.getChildren().add(temp[i]);

      root.getChildren().addAll(btnDeleteButton, btnReturn, quizQuestion, passNumber, teamNumber);

      Scene viewQuizScene = new Scene(root, 950, 300);
      viewQuizStage.setScene(viewQuizScene);
      viewQuizStage.show();
   }

   /*************************************************************************
    * 
    * 
    * User Screens are below this section
    * 
    * 
    **************************************************************************/

   public void goToQuizSelect_Click() {
      Stage quizSele = new Stage();
      Pane root = new Pane();
      quizSele.setTitle("Quiz Selection");
      Text txtWait = new Text("");
      txtWait.setLayoutX(40);
      txtWait.setLayoutY(50);

      Button btnReturn = new Button();
      btnReturn.setText("Return");
      btnReturn.setOnAction(e -> {
         quizSele.close();
         start(pStage);
      });
      root.getChildren().add(btnReturn);

      if (quizList.isEmpty()) {
         txtWait.setText("There are no quizzes currently made!");
         root.getChildren().add(txtWait);
      } else {
         ComboBox<String> boxQuiz = new ComboBox<String>();
         for (int j = 0; j < quizList.size(); j++)
            boxQuiz.getItems().add(quizList.get(j).QuizTitle);

         boxQuiz.getSelectionModel().selectFirst();
         boxQuiz.setLayoutY(50);
         boxQuiz.setLayoutX(40);
         boxQuiz.setPrefSize(170, 20);

         Button btnGoQuiz = new Button();
         btnGoQuiz.setLayoutY(50);
         btnGoQuiz.setLayoutX(220);
         btnGoQuiz.setPrefSize(60, 20);
         btnGoQuiz.setText("Go");
         btnGoQuiz.setOnAction(e -> {
            int index = 0;
            // finds which quiz was selected
            // this may cause issues with identical named quizzes should implement fix
            for (int j = 0; j < quizList.size(); j++)
               if (boxQuiz.getValue().equals(quizList.get(j).QuizTitle))
                  index = j;

            quizSele.close();
            Teamdisplay_Click(index);
         });
         root.getChildren().addAll(boxQuiz, btnGoQuiz);
      }
      Scene waitScene = new Scene(root, 300, 120);
      quizSele.setScene(waitScene);
      quizSele.show();
   }

   public void Teamdisplay_Click(int index) {

      Stage teamStage = new Stage();
      teamStage.setTitle("Team Selection");
      Text qName = new Text("Quiz Name = " + quizList.get(index).QuizTitle);
      qName.setLayoutY(30);
      qName.setLayoutX(80);

      Text txtTeam1 = new Text("Team 1");
      txtTeam1.setLayoutY(60);
      txtTeam1.setLayoutX(25);
      TextField member1a = new TextField();
      member1a.setLayoutY(80);
      member1a.setLayoutX(10);
      member1a.setPrefWidth(80);
      TextField member1b = new TextField();
      member1b.setLayoutY(110);
      member1b.setLayoutX(10);
      member1b.setPrefWidth(80);
      TextField member1c = new TextField();
      member1c.setLayoutY(140);
      member1c.setLayoutX(10);
      member1c.setPrefWidth(80);

      Text txtTeam2 = new Text("Team 2");
      txtTeam2.setLayoutY(60);
      txtTeam2.setLayoutX(125);
      TextField member2a = new TextField();
      member2a.setLayoutY(80);
      member2a.setLayoutX(110);
      member2a.setPrefWidth(80);
      TextField member2b = new TextField();
      member2b.setLayoutY(110);
      member2b.setLayoutX(110);
      member2b.setPrefWidth(80);
      TextField member2c = new TextField();
      member2c.setLayoutY(140);
      member2c.setLayoutX(110);
      member2c.setPrefWidth(80);

      Text txtTeam3 = new Text("Team 3");
      txtTeam3.setLayoutY(60);
      txtTeam3.setLayoutX(225);
      TextField member3a = new TextField();
      member3a.setLayoutY(80);
      member3a.setLayoutX(210);
      member3a.setPrefWidth(80);
      TextField member3b = new TextField();
      member3b.setLayoutY(110);
      member3b.setLayoutX(210);
      member3b.setPrefWidth(80);
      TextField member3c = new TextField();
      member3c.setLayoutY(140);
      member3c.setLayoutX(210);
      member3c.setPrefWidth(80);

      Text seta = new Text("");
      seta.setLayoutY(260);
      seta.setLayoutX(30);

      Button startQuiz = new Button();
      startQuiz.setLayoutY(180);
      startQuiz.setLayoutX(110);
      startQuiz.setPrefSize(80, 40);
      startQuiz.setText("Start Quiz");

      startQuiz.setOnAction(e -> {
         int[] scores = new int[3];
      
         String[] team1 = new String[3];
         String[] team2 = new String[3];
         String[] team3 = new String[3];

         team1[0] = member1a.getText(); team1[1] = member1b.getText(); team1[2] = member1c.getText();
         team2[0] = member2a.getText(); team2[1] = member2b.getText(); team2[2] = member2c.getText();
         team3[0] = member3a.getText(); team3[1] = member3b.getText(); team3[2] = member3c.getText();

         teamStage.close();
         scores = quizMaster(index);
         resultsScreen(scores, quizList.get(index), team1, team2, team3);
      });

      Pane root = new Pane();
      root.getChildren().addAll(qName, seta, startQuiz, txtTeam1, txtTeam2, txtTeam3, member1a, member1b, member1c);
      if (quizList.get(index).numTeams >= 2)
         root.getChildren().addAll(member2a, member2b, member2c);

      if (quizList.get(index).numTeams == 3)
         root.getChildren().addAll(member3a, member3b, member3c);

      Button btnReturn = new Button();
      btnReturn.setText("Return");
      btnReturn.setOnAction(e -> {
         teamStage.close();
         goToQuizSelect_Click();
      });
      root.getChildren().add(btnReturn);

      Scene teamScene = new Scene(root, 300, 260);
      teamStage.setScene(teamScene);
      teamStage.show();
   }

   public int[] quizMaster(int index)
   {
      boolean correct = false;
      int[] scores = new int[3];
      scores[0] = 0; scores[1] = 0; scores[2] = 0;
      Quiz curQuiz = quizList.get(index);

      int counter = 1;
      for(int i = 0; i < curQuiz.list.size(); i++)
      {
         correct = questionScreen(curQuiz.list.get(i), scores, counter, i+1, curQuiz.numTeams);
         if(correct)
            scores[counter-1]++;

         if(scores[0] == curQuiz.passNumber || scores[1] == curQuiz.passNumber || scores[2] == curQuiz.passNumber)
            break;

         counter++;
         if(curQuiz.numTeams == 2 && counter == 3)
            counter = 1;
         else if(curQuiz.numTeams == 3 && counter == 4)
            counter = 1;
         else if(curQuiz.numTeams == 1)
            counter = 1;
      }

      return scores;
   }

   public boolean questionScreen(Question curQuest, int[] scores, int team, int qNum, int numTeams)
   {
      Stage questStage = new Stage();
      questStage.setTitle("Quiz Master");

      Text txtTeam = new Text("Team " + team + ", it's your turn!");
      txtTeam.setLayoutY(20);
      txtTeam.setLayoutX(20);
      Text questionTitle = new Text("Round " + qNum + ": " + curQuest.questionTitle);
      questionTitle.setLayoutY(50);
      questionTitle.setLayoutX(20);
      Text team1Score = new Text("Team 1 Score: " + scores[0]);
      team1Score.setLayoutY(240);
      team1Score.setLayoutX(20);
      Text team2Score = new Text("Team 2 Score: " + scores[1]);
      team2Score.setLayoutY(255);
      team2Score.setLayoutX(20);
      Text team3Score = new Text("Team 3 Score: " + scores[2]);
      team3Score.setLayoutY(270);
      team3Score.setLayoutX(20);

      RadioButton ansA = new RadioButton(curQuest.A);
      ansA.setLayoutX(20);
      ansA.setLayoutY(80);
      RadioButton ansB = new RadioButton(curQuest.B);
      ansB.setLayoutX(20);
      ansB.setLayoutY(100);
      RadioButton ansC = new RadioButton(curQuest.C);
      ansC.setLayoutX(20);
      ansC.setLayoutY(120);
      RadioButton ansD = new RadioButton(curQuest.D);
      ansD.setLayoutX(20);
      ansD.setLayoutY(140);
   
      ToggleGroup questGroup = new ToggleGroup();

      ansA.setToggleGroup(questGroup);
      ansB.setToggleGroup(questGroup);
      ansC.setToggleGroup(questGroup);
      ansD.setToggleGroup(questGroup);

      ansA.fire();

      Button btnSubmit = new Button();
      btnSubmit.setLayoutY(170);
      btnSubmit.setLayoutX(20);
      btnSubmit.setPrefSize(120, 50);
      btnSubmit.setText("Submit Answer");
      btnSubmit.setOnAction(e -> {
         int chosenAns = 0;
         if(ansA.isSelected())
            chosenAns = 1;
         else if(ansB.isSelected())
            chosenAns = 2;
         else if(ansC.isSelected())
            chosenAns = 3;
         else if(ansD.isSelected())
            chosenAns = 4;
         
         if(chosenAns == curQuest.correct)
            ansCorrect = true;
         else 
            ansCorrect = false;
            
         questStage.close();
      });
      
      Pane root = new Pane();
      root.getChildren().addAll(txtTeam, questionTitle, ansA, ansB, ansC, ansD, btnSubmit, team1Score);
      if(numTeams >= 2)
         root.getChildren().add(team2Score);
      if(numTeams == 3)
         root.getChildren().add(team3Score);

      Scene questScene = new Scene(root, 225, 285);
      questStage.setScene(questScene);
      questStage.showAndWait();

      return ansCorrect;
   }
   
   public void resultsScreen(int[] scores, Quiz curQuiz, String[] team1, String[] team2, String[] team3)
   {
      Stage resultsStage = new Stage();
      resultsStage.setTitle("Results Screen");

      Text congratulations = new Text("The Game Is Over!");
      congratulations.setLayoutX(90);
      congratulations.setLayoutY(20);
      Text winnerTeam = new Text();
      winnerTeam.setLayoutX(90);
      winnerTeam.setLayoutY(40);
      Text winner1 = new Text();
      winner1.setLayoutX(10);
      winner1.setLayoutY(60);
      Text winner2 = new Text();
      winner2.setLayoutX(110);
      winner2.setLayoutY(60);
      Text winner3 = new Text();
      winner3.setLayoutX(210);
      winner3.setLayoutY(60);
      Text save = new Text("Results will be saved to \"Results.txt\"");
      save.setLayoutX(40);
      save.setLayoutY(240);

      if(scores[0] == curQuiz.passNumber)
      {
         winnerTeam.setText("Team 1 has won!");
         winner1.setText(team1[0]);
         winner2.setText(team1[1]);
         winner3.setText(team1[2]);
      }
      else if(scores[1] == curQuiz.passNumber)
      {
         winnerTeam.setText("Team 2 has won!");
         winner1.setText(team2[0]);
         winner2.setText(team2[1]);
         winner3.setText(team2[2]);
      }
      else if(scores[2] == curQuiz.passNumber)
      {
         winnerTeam.setText("Team 3 has won!");
         winner1.setText(team3[0]);
         winner2.setText(team3[1]);
         winner3.setText(team3[2]);
      }
      else
      {
         winnerTeam.setText("No team had reached the goal!");
         winnerTeam.setLayoutX(60);
      }

      Text score1Display = new Text("Team 1 Score:");
      score1Display.setLayoutX(10);
      score1Display.setLayoutY(120);
      Text score1 = new Text(Integer.toString(scores[0]));
      score1.setLayoutX(50);
      score1.setLayoutY(140);
      Text score2Display = new Text("Team 2 Score:");
      score2Display.setLayoutX(110);
      score2Display.setLayoutY(120);
      Text score2 = new Text(Integer.toString(scores[1]));
      score2.setLayoutX(150);
      score2.setLayoutY(140);
      Text score3Display = new Text("Team 3 Score:");
      score3Display.setLayoutX(210);
      score3Display.setLayoutY(120);
      Text score3 = new Text(Integer.toString(scores[2]));
      score3.setLayoutX(250);
      score3.setLayoutY(140);

      Button btnReturn = new Button();
      btnReturn.setText("Return To Quiz Selection");
      btnReturn.setLayoutX(60);
      btnReturn.setLayoutY(160);
      btnReturn.setPrefSize(180, 50);
      btnReturn.setOnAction(e -> {
         try
         {
            File res = new File("Results.txt");
            res.createNewFile();

            FileWriter w = new FileWriter(res, true);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
            LocalDateTime now = LocalDateTime.now(); 
            w.write(dtf.format(now) + " " + curQuiz.QuizTitle + " Results\nTeam 1 Members: " + team1[0] + " + " + team1[1] + " + " + team1[2] + 
                     "\nTeam 2 Members: " + team2[0] + " + " + team2[1] + " + " + team2[2] + "\nTeam 3 Members: " +
                     team3[0] + " + " + team3[1] + " + " + team3[2] + "\nTeam 1 Score: " + Integer.toString(scores[0]) + 
                     "\nTeam 2 Score: " + Integer.toString(scores[1]) + "\nTeam 3 Score: " + Integer.toString(scores[2]) + "\n\n\n\n");
            w.close();
         }catch (IOException ie){
         }
         resultsStage.close();
         goToQuizSelect_Click();
      });

      if(curQuiz.numTeams < 3)
         score3.setText("N/A");
      if(curQuiz.numTeams < 2)
         score2.setText("N/A");

      Pane root = new Pane();
      root.getChildren().addAll(congratulations, winnerTeam, winner1, winner2, winner3, score1Display, score1,
                                 score2Display, score2, score3Display, score3, btnReturn, save);

      Scene resultsScene = new Scene(root, 300, 260);
      resultsStage.setScene(resultsScene);
      resultsStage.show();
   }

}

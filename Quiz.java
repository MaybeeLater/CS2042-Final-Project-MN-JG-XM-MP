import java.io.Serializable;
import java.util.*;

public class Quiz implements Serializable
{
   String QuizTitle;
   int rounds, passNumber, numTeams;
   ArrayList<Question> list = new ArrayList<Question>();
   
   public Quiz(String title, int rounds, int passNumber, int numTeams)
   {
      this.QuizTitle = title;
      this.rounds = rounds;
      this.passNumber = passNumber;
      this.numTeams = numTeams;
   }
   
   public void addQ(Question question)
   {
      list.add(question);
   }
}
   
import java.io.Serializable;
import java.util.*;

public class Quiz implements Serializable
{
   String QuizTitle;
   int rounds, passPercent, index;
   ArrayList<Question> list = new ArrayList<Question>();
   
   public Quiz(String title, int rounds, int passPercent)
   {
      this.QuizTitle = title;
      this.rounds = rounds;
      this.passPercent = passPercent;
   }
   
   public void addQ(Question question)
   {
      list.add(question);
   }
}
   
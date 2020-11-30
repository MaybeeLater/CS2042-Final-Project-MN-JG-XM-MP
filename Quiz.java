import java.util.*;

public class Quiz
{
   String QuizTitle;
   int rounds;
   ArrayList<Question> list = new ArrayList<Question>();
   
   public Quiz(String title, int rounds)
   {
      this.QuizTitle = title;
      this.rounds = rounds;
      ArrayList<Question> list = new ArrayList<Question>();
   }
   
   public void addQ(Question question)
   {
      list.add(question);
   }
}
   
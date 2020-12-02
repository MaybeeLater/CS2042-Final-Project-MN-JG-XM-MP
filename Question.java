import java.io.Serializable;
public class Question implements Serializable
{
   String questionTitle, A, B, C, D;
   int correct;
   
   public Question(){}
   
   public Question makeQuestion(String title, String A, String B, String C, String D, int correct)
   {
      this.questionTitle = title;
      this.A = A;
      this.B = B;
      this.C = C;
      this.D = D;
      this.correct = correct; //A = 1, B = 2, etc...
      
      return this;
   }
}
      
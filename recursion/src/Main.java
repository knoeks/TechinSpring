public class Main {
  public static void main(String[] args) {
    factorial(7);
  }


  public static long factorial(long number) {
    int res = 1;
    for (int  i = 2; i <= number; i++) {
      res *= i;
    }

    return res;
  }

  
}
import java.util.*;

public class Main {
 public static void main(String[] args) {
     Scanner scan = new Scanner(System.in);
       int a = scan.nextInt();
       int b = scan.nextInt();
       int max = 0;
   		for(int i = 0;i < 5648612; i++) {
        	for(int j = 0;j < 5648612; j++) {
        		for(int k = 0;k < 5648612; k++) {
        			int m = i + j;
        		}
        	}
        }
       if(a > b) {
           max = a;
        } else
           max = b;
       System.out.println("max=" + max);
       scan.close();
    }
}
import java.lang.Math;

public class Recursion{
    public int cutRod(int[] p, int n){
        if(n == 0){
            return 0;
        }
        int q = Integer.MIN_VALUE;
        for(int i = 1; i <= n; i++){
            q = Math.max(q, p[i] + cutRod(p,n-i));
        }
        return q;
    }
    public static void main(String[] args){
        // int[] p = {0,1,5,8,9,10,17,17,20,24,30,32,35,36};
        int[] p = new int[32];
        for(int i = 1; i < 32; i++){
            p[i] = i+(i%3);
        }
        int n = p.length-1;
        Recursion r = new Recursion();
        System.out.println(r.cutRod(p,n));

    }
}
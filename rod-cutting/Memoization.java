import java.lang.Math;

public class Memoization {
    public int cutRodAux(int[] p, int n, int[] r){
        int q;
        if(r[n] >= 0){
            return r[n];
        }
        if(n==0){
            q = 0;
        }
        else{
            q = Integer.MIN_VALUE;
            for(int i = 1; i <= n; i++){
                q = Math.max(q, p[i] + cutRodAux(p,n-i,r));
            }
        }
        r[n] = q;
        return q;
    }
    public int cutRod(int[] p, int n){
        int[] r = new int[p.length];
        for(int i = 0; i < r.length; i++){
            r[i] = Integer.MIN_VALUE;
        }
        return cutRodAux(p,n,r);
    }
    public static void main(String[] args){
        //int[] p = {0,1,5,8,9,10,17,17,20,24,30,32,35,36};
        int[] p = new int[32];
        for(int i = 1; i < 32; i++){
            p[i] = i+(i%3);
        }
        int n = p.length-1;
        Memoization m = new Memoization();
        System.out.println(m.cutRod(p,n));
    }
}

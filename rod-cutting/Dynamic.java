import java.lang.Math;

public class Dynamic {
    public int cutRod(int[] p,int n){
        int[]r = new int[p.length];
        r[0] = 0;
        for(int i = 0; i <= n; i++){
            int q = Integer.MIN_VALUE;
            for(int j = 0; j <= i; j++){
                q = Math.max(q, p[j] + r[i-j]);
            }
            r[i] = q;
        }
        return r[n];
    }
    public static void main(String[] args){
        //int[] p = {0,1,5,8,9,10,17,17,20,24,30,32,35,36};
        int[] p = new int[32];
        for(int i = 1; i < 32; i++){
            p[i] = i+(i%3);
        }
        int n = p.length-1;
        Dynamic d = new Dynamic();
        System.out.println(d.cutRod(p,n));
    }
}

package quality;

public class BadQualityExample {
    public static void main(String[] args) {
        System.out.println(doIt(Integer.parseInt(args[0]), 0));
    }

    static int doIt(int ipt, int ftr)
{
    // initialize the base cases
    var a = 0;
    var othr = 0;
    // for each x make sure it is less than ipt otherwise break
    for (var x = 0; x < ipt; x++)
    {
        if (x == 1) othr = 1;
        else { var t = a + othr;
            a = othr; ftr = ftr++; othr = t;
        }
    }
    return a + othr;
}
}

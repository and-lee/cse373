public class stringMatch {

    public static int min(int a, int b, int c) {
        int ans = -1;
        if (a <= b && a <= c) {
            ans = a;
        } else if (b <= a && b <= c) {
            ans = b;
        } else {
            ans = c;
        }
        return ans;
    }

    public static int appStringMatch(String a, String b) {
        int[][] m = new int[a.length() + 1][b.length() + 1]; // matrix

        // row init, when b is empty
        // col init, when a is empty
        // base cases
        for (int x = 0; x <= a.length(); x++) {
            m[x][0] = x;
        }

        for (int x = 0; x <= b.length(); x++) {
            m[0][x] = x;
        }

        int i = 0;
        int j = 0;
        for (i = 1; i <= a.length(); i++) {
            for (j = 1; j <= b.length(); j++) {
                // match
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    m[i][j] = m[i - 1][j - 1];
                } else {
                    // substitution m[i-1][j-1]
                    // delete [i-1][j]
                    // insert [i][j-1]
                    m[i][j] = 1 + min(m[i-1][j-1], m[i-1][j], m[i][j-1]);
                }
            }
        }

        return m[i - 1][j - 1];
    }

    public static void main(String args[]) {
        String str1 = "watch the movie raising arizona?";
        String str2 = "watch da mets raze arizona?";
        // 12
        System.out.println(appStringMatch(str1, str2));

        str1 = "this is what happens when I type slow";
        str2 = "htishisth whaty havpens when ui type fasht";
        // 14
        System.out.println(appStringMatch(str1, str2));

        str1 = "leonard skiena";
        str2 = "lynard skynard";
        // 6
        System.out.println(appStringMatch(str1, str2));
    }
}
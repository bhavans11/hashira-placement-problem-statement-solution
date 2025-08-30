import java.math.BigInteger;

public class HashiraProblemstatementsolution {
    static String[] jsonInputs = {
        // Test Case 1
        "{ \"keys\": { \"n\": 4, \"k\": 3 },"
      + " \"1\": { \"base\": \"10\", \"value\": \"4\" },"
      + " \"2\": { \"base\": \"2\", \"value\": \"111\" },"
      + " \"3\": { \"base\": \"10\", \"value\": \"12\" },"
      + " \"6\": { \"base\": \"4\", \"value\": \"213\" } }",
        // Test Case 2
        "{ \"keys\": { \"n\": 10, \"k\": 7 },"
      + " \"1\": { \"base\": \"6\", \"value\": \"13444211440455345511\" },"
      + " \"2\": { \"base\": \"15\", \"value\": \"aed7015a346d635\" },"
      + " \"3\": { \"base\": \"15\", \"value\": \"6aeeb69631c227c\" },"
      + " \"4\": { \"base\": \"16\", \"value\": \"e1b5e05623d881f\" },"
      + " \"5\": { \"base\": \"8\", \"value\": \"316034514573652620673\" },"
      + " \"6\": { \"base\": \"3\", \"value\": \"2122212201122002221120200210011020220200\" },"
      + " \"7\": { \"base\": \"3\", \"value\": \"20120221122211000100210021102001201112121\" },"
      + " \"8\": { \"base\": \"6\", \"value\": \"20220554335330240002224253\" },"
      + " \"9\": { \"base\": \"12\", \"value\": \"45153788322a1255483\" },"
      + " \"10\": { \"base\": \"7\", \"value\": \"1101613130313526312514143\" } }"
    };

    public static void main(String[] args) {
        for (int tcase = 0; tcase < jsonInputs.length; tcase++) {
            String jsonInput = jsonInputs[tcase];
            int n = getIntValue(jsonInput, "\"n\":", ",");
            int k = getIntValue(jsonInput, "\"k\":", "}");

            String[] bases = new String[n];
            String[] values = new String[n];
            BigInteger[] decimals = new BigInteger[n];

            int rootNum = 0;
            for (int i = 1; rootNum < n; i++) {
                String key = "\"" + i + "\": {";
                int idx = jsonInput.indexOf(key);
                if (idx == -1) continue;
                bases[rootNum] = getStringValue(jsonInput, "\"base\": \"", "\"", idx);
                values[rootNum] = getStringValue(jsonInput, "\"value\": \"", "\"", idx);
                decimals[rootNum] = new BigInteger(values[rootNum], Integer.parseInt(bases[rootNum]));
                rootNum++;
            }

            // Get correct set (first k roots)
            BigInteger secret = BigInteger.ONE;
            for (int i = 0; i < k; i++) secret = secret.multiply(decimals[i]);
            // Corrupt share (first not in correct set, so index k)
            BigInteger corrupt = decimals[k];

            System.out.println("Testcase " + (tcase + 1) + ":");
            System.out.println("Secret (product of first k roots):\n" + secret);
            System.out.println("Corrupt share (root " + (k + 1) + "):\n" + corrupt);
            System.out.println();
        }
    }

    static int getIntValue(String str, String start, String end) {
        int idx1 = str.indexOf(start);
        int idx2 = str.indexOf(end, idx1);
        String sub = str.substring(idx1 + start.length(), idx2).replaceAll("[^0-9]", "");
        return Integer.parseInt(sub);
    }
    static String getStringValue(String str, String start, String end, int fromIdx) {
        int idx1 = str.indexOf(start, fromIdx);
        int idx2 = str.indexOf(end, idx1 + start.length());
        return str.substring(idx1 + start.length(), idx2);
    }
}


import java.math.BigInteger;

public class HashiraProblemstatementsolution {
    // Example input: replace with your full JSON string as needed
    static String jsonInput =
      "{ \"keys\": { \"n\": 10, \"k\": 7 },"
    + "\"1\": { \"base\": \"6\", \"value\": \"13444211440455345511\" },"
    + "\"2\": { \"base\": \"15\", \"value\": \"aed7015a346d635\" },"
    + "\"3\": { \"base\": \"15\", \"value\": \"6aeeb69631c227c\" },"
    + "\"4\": { \"base\": \"16\", \"value\": \"e1b5e05623d881f\" },"
    + "\"5\": { \"base\": \"8\", \"value\": \"316034514573652620673\" },"
    + "\"6\": { \"base\": \"3\", \"value\": \"2122212201122002221120200210011020220200\" },"
    + "\"7\": { \"base\": \"3\", \"value\": \"20120221122211000100210021102001201112121\" },"
    + "\"8\": { \"base\": \"6\", \"value\": \"20220554335330240002224253\" },"
    + "\"9\": { \"base\": \"12\", \"value\": \"45153788322a1255483\" },"
    + "\"10\": { \"base\": \"7\", \"value\": \"1101613130313526312514143\" } }";

    public static void main(String[] args) {
        // Find n and k
        int n = getIntValue(jsonInput, "\"n\":", ",");
        int k = getIntValue(jsonInput, "\"k\":", "}");

        String[] bases = new String[n];
        String[] values = new String[n];
        BigInteger[] decimals = new BigInteger[n];

        // Parse each share from string
        for (int i = 1; i <= n; i++) {
            String key = "\"" + i + "\": {";
            int idx = jsonInput.indexOf(key);
            bases[i-1] = getStringValue(jsonInput, "\"base\": \"", "\"", idx);
            values[i-1] = getStringValue(jsonInput, "\"value\": \"", "\"", idx);
            decimals[i-1] = new BigInteger(values[i-1], Integer.parseInt(bases[i-1]));
        }

        // Output roots as decimals
        System.out.println("Roots (decimal):");
        for (int i = 0; i < n; i++)
            System.out.println((i+1) + ": " + decimals[i]);

        // Get correct set (first k roots)
        System.out.println("\nCorrect Set:");
        for (int i = 0; i < k; i++)
            System.out.println("Root " + (i+1) + ": " + decimals[i]);

        // Choose a corrupt share (just next root not in correct set)
        System.out.println("\nCorrupt Share:");
        System.out.println("Root " + (k+1) + ": " + decimals[k]);

        // Simulated secret: product of correct set decimals
        BigInteger secret = BigInteger.ONE;
        for (int i = 0; i < k; i++)
            secret = secret.multiply(decimals[i]);

        System.out.println("\nSecret (product of correct set decimals):");
        System.out.println(secret);
    }

    // Extract integer after key
    static int getIntValue(String str, String start, String end) {
        int idx1 = str.indexOf(start);
        int idx2 = str.indexOf(end, idx1);
        String sub = str.substring(idx1 + start.length(), idx2).replaceAll("[^0-9]", "");
        return Integer.parseInt(sub);
    }

    // Extract string between start and end after a certain index
    static String getStringValue(String str, String start, String end, int fromIdx) {
        int idx1 = str.indexOf(start, fromIdx);
        int idx2 = str.indexOf(end, idx1 + start.length());
        return str.substring(idx1 + start.length(), idx2);
    }
}

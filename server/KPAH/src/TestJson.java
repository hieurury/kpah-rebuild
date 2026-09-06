import org.json.JSONArray;

public class TestJson {
    public static void main(String[] args) {
        try {
            JSONArray arr = new JSONArray();
            arr.put("{\"key\":0}");
            System.out.println("Output: " + arr.toString());
            JSONArray parsed = new JSONArray(arr.toString());
            System.out.println("Parsed: " + parsed.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

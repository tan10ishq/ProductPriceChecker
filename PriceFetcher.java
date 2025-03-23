import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.net.URLEncoder;
import org.json.JSONObject;
import org.json.JSONArray;

public class PriceFetcher {
    private static final String SERP_API_KEY = "3cdc9b49f72371f59dd6b30a1925e955184b9e5562e37fd10920ec37d5c49093";  

    public static String fetchProductPrice(String productName) {
        try {
            // Encode product name to handle spaces and special characters
            String encodedProductName = URLEncoder.encode(productName, "UTF-8");

            // Construct the URI first to avoid deprecated URL constructor
            URI uri = new URI("https", "serpapi.com", "/search.json",
                    "engine=google_shopping&q=" + encodedProductName + "&api_key=" + SERP_API_KEY, null);

            // Convert URI to URL
            URL url = uri.toURL();

            // Open HTTP connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            
            // Log the response code
            System.out.println("Response Code: " + responseCode);

            if (responseCode != 200) {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorResponse.append(line);
                }
                errorReader.close();
                System.out.println("Error Response: " + errorResponse.toString());
                return null;
            }

            // Read the API response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(response.toString());

            // Extract the product price
            JSONArray shoppingResults = jsonResponse.getJSONArray("shopping_results");

            if (shoppingResults.length() > 0) {
                JSONObject firstProduct = shoppingResults.getJSONObject(0);
                String price = firstProduct.optString("price", "N/A");
                return price;
            } else {
                System.out.println("No product found.");
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to fetch product data.");
            return null;
        }
    }
}

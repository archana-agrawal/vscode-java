import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class GoogleAnalyticsService {
    public static void sendDataToGoogleAnalytics(String trackingId, String accountID, String accountName, LocalDate dateOfBirth) {
        try {
            // Construct the payload data
            String payload = "v=1&tid=" + trackingId + "&cid=" + accountID + "&an=" + accountName + "&dob=" + dateOfBirth.toString();

            // Set up the connection to the Google Analytics Measurement Protocol endpoint
            URL url = new URL("https://www.google-analytics.com/collect");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);

            // Send the payload data to the Google Analytics endpoint
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(payload.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();

            // Check the response code to ensure the request was successful
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Data sent successfully to Google Analytics.");
            } else {
                System.out.println("Failed to send data to Google Analytics. Response code: " + responseCode);
            }

            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            System.out.println("An error occurred while sending data to Google Analytics: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String trackingId = "UA-XXXXXXXXX-X";
        String accountID = "123456";
        String accountName = "Example Account";
        LocalDate dateOfBirth = LocalDate.of(1990, 5, 15);

        sendDataToGoogleAnalytics(trackingId, accountID, accountName, dateOfBirth);
    }
}

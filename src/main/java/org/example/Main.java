package org.example;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://www.cbr-xml-daily.ru/daily_json.js");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String jsonResponse = response.toString();
            ObjectMapper mapper = new ObjectMapper();

            JsonNode nodes = mapper.readTree(jsonResponse);
            String valutes = nodes.get("Valute").toString();

            nodes = mapper.readTree(valutes);
            for (var node : nodes)
            {
                System.out.print(node.get("CharCode") + " ");
                System.out.println(node.get("Value"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
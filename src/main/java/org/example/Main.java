package org.example;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        fiatParse();
        cryptoParse();
    }

    public static void fiatParse() {
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
            for (var node : nodes) {
                System.out.print(node.get("CharCode").toString() + " ");
                System.out.println(node.get("Value").asDouble());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cryptoParse() {
        try {
            URL url = new URL("https://api.binance.com/api/v3/ticker/price");
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

            JsonNode json = mapper.readTree(jsonResponse);

            var pairs = json.findValues("symbol");
            var prices = json.findValues("price");

            for (int i = 0; i < pairs.size(); i++) {
                if (pairs.get(i).toString().contains("USDT")) {
                    System.out.println(pairs.get(i).toString() + " : " + prices.get(i).asDouble());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
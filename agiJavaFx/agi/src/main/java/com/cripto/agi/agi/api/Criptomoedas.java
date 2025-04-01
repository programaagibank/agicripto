package com.cripto.agi.agi.api;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Criptomoedas {
    private double precoBtc;
    private double precoEth;
    private double precoSol;

    public double getPrecoBtc() {
        return precoBtc;
    }

    public void setPrecoBtc(double precoBtc) {
        this.precoBtc = precoBtc;
    }

    public double getPrecoEth() {
        return precoEth;
    }

    public void setPrecoEth(double precoEth) {
        this.precoEth = precoEth;
    }

    public double getPrecoSol() {
        return precoSol;
    }

    public void setPrecoSol(double precoSol) {
        this.precoSol = precoSol;
    }

    public void consultarPrecoBitcoin() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.coinbase.com/v2/prices/BTC-BRL/spot"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject jsonResponse = new JSONObject(response.body());
                this.precoBtc = jsonResponse.getJSONObject("data").getDouble("amount");
            } else {
                System.out.println("Erro ao acessar a API. Código: " + response.statusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void consultarPrecoEthereum() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.coinbase.com/v2/prices/ETH-BRL/spot"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject jsonResponse = new JSONObject(response.body());
                this.precoEth = jsonResponse.getJSONObject("data").getDouble("amount");
            } else {
                System.out.println("Erro ao acessar a API. Código: " + response.statusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void consultarPrecoSolana() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.coinbase.com/v2/prices/SOL-BRL/spot"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject jsonResponse = new JSONObject(response.body());
                this.precoSol = jsonResponse.getJSONObject("data").getDouble("amount");
            } else {
                System.out.println("Erro ao acessar a API. Código: " + response.statusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

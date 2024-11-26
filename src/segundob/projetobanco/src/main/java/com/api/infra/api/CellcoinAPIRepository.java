package com.api.infra.api;

import com.api.data.protocols.paymentslip.*;
import com.api.data.protocols.pix.*;
import com.api.domain.entities.PaymentBill;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CellcoinAPIRepository implements ILoadPaymentBillRepository, IPayPaymentBillRepository, IGeneratePixQRCodeRepository {

    private static final String BASE_URL = "https://sandbox.openfinance.celcoin.dev";
    private static final String BILLPAYMENTS_AUTHORIZE_ENDPOINT = "/v5/transactions/billpayments/authorize";
    private static final String TOKEN_ENDPOINT = "/v5/token";
    private static final String CLIENT_ID = "41b44ab9a56440.teste.celcoinapi.v5";
    private static final String CLIENT_SECRET = "e9d15cde33024c1494de7480e69b7a18c09d7cd25a8446839b3be82a56a044a3";

    @Override
    public PaymentBill loadPaymentBill(String digitableLine) {
        try {
            String token = generateToken();

            JSONObject payload = new JSONObject();
            JSONObject barCode = new JSONObject();
            barCode.put("digitable", digitableLine);
            barCode.put("type", 0);
            payload.put("barCode", barCode);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + BILLPAYMENTS_AUTHORIZE_ENDPOINT))
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JSONObject jsonResponse = new JSONObject(response.body());
                return mapToPaymentBill(jsonResponse);
            } else {
                throw new RuntimeException("Erro ao consultar boleto: " + response.body());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar PaymentBill: " + e.getMessage(), e);
        }
    }

    private String generateToken() {
        try {
            String formData = String.format(
                    "client_id=%s&grant_type=client_credentials&client_secret=%s",
                    CLIENT_ID, CLIENT_SECRET);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + TOKEN_ENDPOINT))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(formData))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JSONObject jsonResponse = new JSONObject(response.body());
                return jsonResponse.getString("access_token");
            } else {
                throw new RuntimeException("Erro ao gerar token: " + response.body());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar token: " + e.getMessage(), e);
        }
    }

    private PaymentBill mapToPaymentBill(JSONObject jsonResponse) {
        PaymentBill paymentBill = new PaymentBill();

        paymentBill.setAssignor(jsonResponse.optString("assignor"));
        paymentBill.setRegisterData(jsonResponse.optString("registerData"));
        paymentBill.setSettleDate(jsonResponse.optString("settleDate"));
        paymentBill.setDueDate(jsonResponse.optString("dueDate"));
        paymentBill.setEndHour(jsonResponse.optString("endHour"));
        paymentBill.setIniteHour(jsonResponse.optString("initeHour"));
        paymentBill.setNextSettle(jsonResponse.optString("nextSettle"));
        paymentBill.setDigitable(jsonResponse.optString("digitable"));
        paymentBill.setTransactionId(jsonResponse.optLong("transactionId"));
        paymentBill.setType(jsonResponse.optInt("type"));
        paymentBill.setValue(jsonResponse.optBigDecimal("value", null));
        paymentBill.setMaxValue(jsonResponse.optBigDecimal("maxValue", null));
        paymentBill.setMinValue(jsonResponse.optBigDecimal("minValue", null));
        paymentBill.setErrorCode(jsonResponse.optString("errorCode"));
        paymentBill.setMessage(jsonResponse.optString("message"));
        paymentBill.setStatus(jsonResponse.optInt("status"));

        return paymentBill;
    }

    @Override
    public void payPaymentBill(Long transactionId) {
        try {
            String token = generateToken();

            JSONObject payload = new JSONObject();
            payload.put("externalNSU", "1234");
            payload.put("externalTerminal", "teste123");

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/v5/transactions/billpayments/" + transactionId + "/capture"))
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(payload.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject jsonResponse = new JSONObject(response.body());
                if (!"000".equals(jsonResponse.optString("errorCode"))) {
                    throw new RuntimeException("Erro ao processar pagamento: " + jsonResponse.optString("message"));
                }
            } else if (response.statusCode() == 400) {
                JSONObject jsonResponse = new JSONObject(response.body());
                throw new RuntimeException("Erro ao processar pagamento: " + jsonResponse.optString("message"));
            } else {
                throw new RuntimeException("Erro inesperado ao processar pagamento. Status: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao capturar pagamento: " + e.getMessage(), e);
        }
    }

    @Override
    public String generatePixQRCode(double amount) {
        try {
            String token = generateToken();

            JSONObject merchant = new JSONObject();
            merchant.put("postalCode", "01201005");
            merchant.put("city", "Barueri");
            merchant.put("merchantCategoryCode", "5651");
            merchant.put("name", "Celcoin");

            JSONObject payload = new JSONObject();
            payload.put("key", "testepix@celcoin.com.br");
            payload.put("amount", amount);
            payload.put("merchant", merchant);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/pix/v1/brcode/static"))
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject jsonResponse = new JSONObject(response.body());
                return jsonResponse.optString("emvqrcps", "QR Code não gerado");
            } else {
                throw new RuntimeException("Erro ao gerar QR Code PIX. Status: " + response.statusCode() + " - " + response.body());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar QR Code PIX: " + e.getMessage(), e);
        }
    }
}
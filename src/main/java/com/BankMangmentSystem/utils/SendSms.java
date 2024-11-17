package com.BankMangmentSystem.utils;

import okhttp3.*;

import java.io.IOException;

public class SendSms {

    private static final String API_URL = "https://textbelt.com/text";
    private static final String API_KEY = "textbelt";  // Replace with your actual API key if using a paid or self-hosted service

    private final OkHttpClient client;

    public SendSms() {
        this.client = new OkHttpClient();
    }

    public boolean sendOtpSms(String phoneNumber, String otp) {
        String message = "Your OTP is: " + otp;

        // Prepare the request data
        String postData = "phone=" + phoneNumber + "&message=" + message + "&key=" + API_KEY;

        RequestBody body = RequestBody.create(postData, MediaType.get("application/x-www-form-urlencoded"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            // Check if the request was successful
            if (response.isSuccessful()) {
                System.out.println("OTP sent successfully to " + phoneNumber);
                return true;
            } else {
                // Log the error response from the API
                String errorResponse = response.body() != null ? response.body().string() : "No response body";
                System.err.println("Failed to send OTP: " + errorResponse);
                return false;
            }
        } catch (IOException e) {
            // Catch potential IOExceptions (network issues, etc.)
            System.err.println("Error occurred while sending OTP: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}





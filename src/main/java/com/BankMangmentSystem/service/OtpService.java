package com.BankMangmentSystem.service;

import com.BankMangmentSystem.utils.SendSms;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OtpService {

    private static final int OTP_EXPIRATION_TIME = 5 * 60 * 1000; // 5 minutes
    private final SendSms smsSender = new SendSms();

    // Store OTPs and their expiration times
    private final Map<String, OtpData> otpStorage = new HashMap<>();

    public String generateAndSendOtp(String phoneNumber) {
        String otp = generateOtp();
        boolean smsSent = smsSender.sendOtpSms(phoneNumber, otp);
        if (smsSent) {
            // Store OTP and its expiration time
            otpStorage.put(phoneNumber, new OtpData(otp, System.currentTimeMillis() + OTP_EXPIRATION_TIME));
            return "OTP sent successfully.";
        }
        return "Failed to send OTP.";
    }

    public boolean validateOtp(String phoneNumber, String enteredOtp) {
        // Check if OTP exists and is not expired
        OtpData storedOtpData = otpStorage.get(phoneNumber);

        if (storedOtpData == null) {
            return false; // No OTP found for this phone number
        }

        // Check if the OTP has expired
        if (System.currentTimeMillis() > storedOtpData.getExpirationTime()) {
            otpStorage.remove(phoneNumber); // OTP has expired, remove it
            return false;
        }

        // Validate the entered OTP
        return storedOtpData.getOtp().equals(enteredOtp);
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = random.nextInt(900000) + 100000; // Generate a 6-digit OTP
        return String.valueOf(otp);
    }

    // Inner class to store OTP and expiration time
    private static class OtpData {
        private final String otp;
        private final long expirationTime;

        public OtpData(String otp, long expirationTime) {
            this.otp = otp;
            this.expirationTime = expirationTime;
        }

        public String getOtp() {
            return otp;
        }

        public long getExpirationTime() {
            return expirationTime;
        }
    }
}



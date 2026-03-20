package com.yaritza.payments_service_demo;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/currency")
@CrossOrigin(origins = "http://localhost:3000")
public class CurrencyController {

    @GetMapping("/format")
    public ResponseEntity<?> formatCurrency(
        @RequestParam String amount,
        @RequestParam(defaultValue = "USD") String currency,
        @RequestParam(defaultValue = "en-US") String locale
    ) {
        try {
            double parsedAmount = Double.parseDouble(amount);
            Locale localeObj = Locale.forLanguageTag(locale);
            NumberFormat formatter = NumberFormat.getCurrencyInstance(localeObj);
            formatter.setCurrency(Currency.getInstance(currency));
            String formatted = formatter.format(parsedAmount);

            return ResponseEntity.ok(Map.of(
                "formatted", formatted,
                "amount", amount,
                "currency", currency,
                "locale", locale
            ));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid or missing amount"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Internal server error"));
        }
    }
}
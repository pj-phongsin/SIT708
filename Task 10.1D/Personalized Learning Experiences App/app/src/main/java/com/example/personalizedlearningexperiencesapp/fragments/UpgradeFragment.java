package com.example.personalizedlearningexperiencesapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.example.personalizedlearningexperiencesapp.R;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.Nullable;

import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UpgradeFragment extends Fragment {

    private PaymentsClient paymentsClient;
    private Button starterPurchase, intermediatePurchase, advancedPurchase;
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;

    private void launchGooglePaySheet(String planName, int amountCents) {
        try {
            JSONObject paymentDataRequestJson = new JSONObject()
                .put("apiVersion", 2)
                .put("apiVersionMinor", 0)
                .put("allowedPaymentMethods", new JSONArray()
                    .put(new JSONObject()
                        .put("type", "CARD")
                        .put("parameters", new JSONObject()
                            .put("allowedAuthMethods", new JSONArray()
                                .put("PAN_ONLY")
                                .put("CRYPTOGRAM_3DS"))
                            .put("allowedCardNetworks", new JSONArray()
                                .put("VISA")
                                .put("MASTERCARD"))
                        )
                        .put("tokenizationSpecification", new JSONObject()
                            .put("type", "PAYMENT_GATEWAY")
                            .put("parameters", new JSONObject()
                                .put("gateway", "example")
                                .put("gatewayMerchantId", "exampleMerchantId"))
                        )
                    )
                )
                .put("transactionInfo", new JSONObject()
                    .put("totalPrice", String.format("%.2f", amountCents / 100.0))
                    .put("totalPriceStatus", "FINAL")
                    .put("currencyCode", "USD"))
                .put("merchantInfo", new JSONObject()
                    .put("merchantName", "Demo App"));

            PaymentDataRequest request =
                PaymentDataRequest.fromJson(paymentDataRequestJson.toString());

            AutoResolveHelper.resolveTask(
                paymentsClient.loadPaymentData(request),
                requireActivity(),
                LOAD_PAYMENT_DATA_REQUEST_CODE
            );

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Payment request error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upgrade, container, false);

        paymentsClient = Wallet.getPaymentsClient(
                requireActivity(),
                new Wallet.WalletOptions.Builder()
                        .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                        .build()
        );

        starterPurchase = view.findViewById(R.id.btnStarter);
        intermediatePurchase = view.findViewById(R.id.btnIntermediate);
        advancedPurchase = view.findViewById(R.id.btnAdvanced);

        starterPurchase.setOnClickListener(v -> launchGooglePaySheet("Starter", 299));
        intermediatePurchase.setOnClickListener(v -> launchGooglePaySheet("Intermediate", 499));
        advancedPurchase.setOnClickListener(v -> launchGooglePaySheet("Advanced", 999));

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOAD_PAYMENT_DATA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                PaymentData paymentData = PaymentData.getFromIntent(data);
                Toast.makeText(getContext(), "Payment successful!", Toast.LENGTH_LONG).show();
                requireActivity().getSharedPreferences("AppPrefs", Activity.MODE_PRIVATE)
                        .edit()
                        .putString("upgradePlan", "Premium") // Replace with actual planName if tracked
                        .apply();
            } else {
                Toast.makeText(getContext(), "Payment canceled or failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
package com.startbutton.sbpayment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.startbutton.sdk.StartButtonPlugin;
import com.startbutton.sdk.models.CurrencyType;
import com.startbutton.sdk.models.Payment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private Button startPaymentBtn;
    private static final String TAG = "MAIN_ACTIVITY";

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("Created State");
        initPayment();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startPaymentBtn = findViewById(R.id.startPaymentBtn);

        startPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });
    }
    public void startPayment() {
        // Get the current date and time
        LocalDateTime now = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
        }

        // Define the format for ISO 8601
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        }

        // Format the date and time in ISO 8601 format
        String isoDateTime = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            isoDateTime = now.format(formatter);
        }
        Payment payment = new Payment(300000.00, "festy691@gmail.com", CurrencyType.NGN, "SBC-native-"+isoDateTime);
        StartButtonPlugin.makePayment(this, payment, (resultCode, data) -> {
            // Handle the result here
            Log.d("MainActivity", data.getMessage());
            if (resultCode == RESULT_OK && data != null) {
                // Use the result value here
                Log.d("MainActivity", data.getMessage());
            }
        });
    }

    void initPayment(){
        try {
            Log.i(TAG, "Initializing");
            /*String liveKey = "";
            String devKey = "";*/
            String liveKey = "sb_482b6de023fa3d45c7c5be84e207927eaeaa2cbbe849a2f01075ea44697a1232";
            String devKey = "sb_e73bc345e432e8dd5310577c2bc084429579d1c36f48328ddb719f474b8112c0";
            boolean isLive = false;
            StartButtonPlugin.initialize(isLive ? liveKey : devKey, isLive);
            Log.i(TAG, "Initialized");
        } catch (ExecutionException e) {
            Log.e(TAG, "Initialization Error: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Log.e(TAG, "Initialization Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
package com.sanarip.rad.quotes;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sanarip.rad.quotes.model.Quote;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Quote quote;
    ProgressBar pb;

    private static final  String TAG = "QUOTES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        getQuote();
    }

    public void getQuote(){
        Retrofit rf = new Retrofit.Builder()
                .baseUrl("http://api.forismatic.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = rf.create(ApiService.class);

        api.getQuote().enqueue(new Callback<Quote>() {
            @Override
            public void onResponse(Call<Quote> call, Response<Quote> response) {
                quote = response.body();
                pb.setVisibility(View.INVISIBLE);
                displayQuote();
            }

            @Override
            public void onFailure(Call<Quote> call, Throwable t) {

            }
        });
    }

    public void displayQuote(){
        TextView view = (TextView) findViewById(R.id.quote);
        view.setText(quote.getQuoteText());

        TextView au = (TextView) findViewById(R.id.quoteAuthor);
        au.setText(quote.getQuoteAuthor());
    }

    public void onClick(View view) {
        pb.setVisibility(View.VISIBLE);
        getQuote();
    }

    public void copyQuote(View view){
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        TextView quote = (TextView) findViewById(R.id.quote);
        TextView author = (TextView) findViewById(R.id.quoteAuthor);
        ClipData clip = ClipData.newPlainText("quote", quote.getText() + " " + author.getText());
        clipboardManager.setPrimaryClip(clip);
        Toast.makeText(this, "Cкопировано", Toast.LENGTH_LONG).show();
    }
}
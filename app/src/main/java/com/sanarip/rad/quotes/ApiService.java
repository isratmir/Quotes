package com.sanarip.rad.quotes;


import com.sanarip.rad.quotes.model.Quote;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("api/1.0/?method=getQuote&format=json&lang=ru")
    Call<Quote> getQuote();
}

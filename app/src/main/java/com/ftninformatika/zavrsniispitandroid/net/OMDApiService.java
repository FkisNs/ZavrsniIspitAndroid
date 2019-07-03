package com.ftninformatika.zavrsniispitandroid.net;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OMDApiService {

    /**
     * Prvo je potrebno da definisemo retrofit instancu preko koje ce komunikacija ici
     * */

    static OkHttpClient okHttpClient = new OkHttpClient();

    public static Retrofit getRetrofitInstance(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Contract.BASE_URL)
                .client(okHttpClient) //stavio sam kako bih dobio neku povratnu informaciju ako ima greska
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    /**
     * Definisemo konkretnu instancu servisa na intnerntu sa kojim
     * vrsimo komunikaciju
     * */
    public static OMDBApiEndpoint apiInterface(){
        OMDBApiEndpoint apiService = getRetrofitInstance().create(OMDBApiEndpoint.class);
        return apiService;
    }
}

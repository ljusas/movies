package ljusas.com.movies.web;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APICall {

    public static OMDBApiService createRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIContract.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OMDBApiService service = retrofit.create(OMDBApiService.class);
        return service;


        /**
         * Ili ovako :)
        * return retrofit.create(OMDBApiService.class);
        * */
    }
}

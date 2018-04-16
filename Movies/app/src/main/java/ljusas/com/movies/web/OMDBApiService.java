package ljusas.com.movies.web;

import java.util.Map;


import ljusas.com.movies.model.Movie;
import ljusas.com.movies.model.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;


public interface OMDBApiService {

    @GET("/")
    Call<Response> searchODMApi(@QueryMap Map<String, String> options);

    @GET("/")
    Call<Movie> getInfoDataByID(@QueryMap Map<String, String> options);
}

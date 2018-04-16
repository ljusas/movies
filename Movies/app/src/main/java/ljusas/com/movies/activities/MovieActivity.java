package ljusas.com.movies.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import ljusas.com.movies.R;
import ljusas.com.movies.model.Movie;
import ljusas.com.movies.web.APICall;
import ljusas.com.movies.web.APIContract;
import ljusas.com.movies.web.OMDBApiService;
import retrofit2.Call;
import retrofit2.Callback;

public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        final String dataID = getIntent().getStringExtra("data");
        movieSearch(dataID);
    }

    private void movieSearch (String dataID){

        OMDBApiService service = APICall.createRetrofit();

        Map<String, String> params = new HashMap<>();
        params.put(APIContract.IMDB_KEY, dataID);
        params.put(APIContract.API_KEY,getString(R.string.password));

        Call<Movie> call = service.getInfoDataByID(params);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, retrofit2.Response<Movie> response) {
                if (response.code() == 200){
                    Movie m = response.body();

                    ImageView imageView = MovieActivity.this.findViewById(R.id.movie_image);
                    Picasso.with(MovieActivity.this).load(m.getPoster()).into(imageView);

                    TextView tv_title = MovieActivity.this.findViewById(R.id.movie_title);
                    tv_title.setText(String.format("Title: %s", m.getTitle()));

                    TextView tvYear = (TextView) findViewById(R.id.movie_year);
                    tvYear.setText(String.format("Movie year: %s", m.getYear()));

                    TextView tvActors = (TextView) findViewById(R.id.movie_actors);
                    tvActors.setText(String.format("Actors: %s", m.getActors()));

                    TextView tvPlot = (TextView) findViewById(R.id.movie_plot);
                    tvPlot.setText(String.format("Movie year: %s", m.getPlot()));

                }else {
                    Toast.makeText(MovieActivity.this, "Nema detalja za uneti film", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(MovieActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

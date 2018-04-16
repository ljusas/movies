package ljusas.com.movies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ljusas.com.movies.R;
import ljusas.com.movies.model.Movie;
import ljusas.com.movies.model.Response;
import ljusas.com.movies.model.Search;
import ljusas.com.movies.web.APICall;
import ljusas.com.movies.web.APIContract;
import ljusas.com.movies.web.OMDBApiService;
import retrofit2.Call;
import retrofit2.Callback;


public class MainActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button serachButton = findViewById(R.id.searchButton);
        final EditText userInput = findViewById(R.id.userInput);

        serachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSerach(userInput.getText().toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.action_about:
                dialog = new AboutDialog(MainActivity.this).prepareDialog();
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doSerach(String userInput){

        OMDBApiService service = APICall.createRetrofit();
        Map<String, String> params = new HashMap<>();
        params.put(APIContract.SEARCH, userInput);
        params.put(APIContract.PLOT, "full");
        params.put(APIContract.API_KEY,getString(R.string.password));

        Call<Response> call = service.searchODMApi(params);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, final retrofit2.Response<Response> response) {

                if (response.isSuccessful()){

                    List<String> list = new ArrayList<>();
                    final Response responses = response.body();
                    String check = responses.getTotalResults();

                    if (check!=null){

                        for (Search s :responses.getSearch()){
                            String movieName = s.getTitle() + " (" + s.getYear() + ")";
                            list.add(movieName);
                        }

                        listView = (ListView) findViewById(R.id.movie_list);

                        ListAdapter adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, list);
                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String imdbKey = responses.getSearch().get(position).getImdbID();
                                Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                                intent.putExtra("data", imdbKey);
                                startActivity(intent);
                                Toast.makeText(MainActivity.this, "Prikaz podataka o unetom filmu", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else {
                        Toast.makeText(MainActivity.this, "Nema unetog filma", Toast.LENGTH_SHORT).show();
                        ListAdapter adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item, list);
                        listView.setAdapter(adapter);
                    }

                }else {
                    Toast.makeText(MainActivity.this, "Nema unetog filma", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            };
        });
    }
}

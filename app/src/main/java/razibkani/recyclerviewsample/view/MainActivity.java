package razibkani.recyclerviewsample.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import razibkani.recyclerviewsample.R;
import razibkani.recyclerviewsample.listener.RecyclerTouchListener;
import razibkani.recyclerviewsample.model.adapter.Adapter;
import razibkani.recyclerviewsample.model.pojo.News;

public class MainActivity extends AppCompatActivity {

    List<View> headers; // list headers
    List<News> news; // list news
    List<View> footers; // list footer

    private View header; // single header view
    private View footer; // single footer view

    private Adapter adapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        settings();
    }

    // init component and set data
    private void settings() {
        // initialize variable and views
        headers = new ArrayList<>();
        news = new ArrayList<>();
        footers = new ArrayList<>();

        header = LayoutInflater.from(this).inflate(R.layout.item_header, null); // inflate header view
        footer = LayoutInflater.from(this).inflate(R.layout.item_footer, null); // inflate footer view

        loadNews(); // load news data

        adapter = new Adapter(news);
        adapter.addHeader(header);
        adapter.addFooter(footer);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerTouchListener(this, new RecyclerTouchListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // ignoring clicked header / footer
                        if (!(position <= 0 || position >= news.size())) {
                            // position - 1 because we have header, so items on adapter is +1
                            int pos = position - 1;
                            Toast.makeText(MainActivity.this, news.get(pos).title, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
        );
    }

    // dummy news data
    private void loadNews() {
        news.add(new News(1, "http://cdn-media-1.lifehack.org/wp-content/files/2016/04/13001540/TM8WQQY4GS-1024x683.jpg",
                "What Not To Do Anymore When You Turn 24", 1));
        news.add(new News(1, "http://cdn-media-2.lifehack.org/wp-content/files/2015/07/extroverted-introvert-0-1024x727.jpg",
                "19 Real Life Examples of An Extroverted Introvert So You Don’t Get Confused", 2));
        news.add(new News(1, "http://cdn-media-2.lifehack.org/wp-content/files/2015/10/07231442/Lifehack_EQ.jpg",
                "7 Things Emotionally Intelligent People Don’t Do", 2));
        news.add(new News(1, "http://cdn-media-2.lifehack.org/wp-content/files/2016/04/07085604/24497899011_3fbdff6e16_o-1024x576.jpg",
                "5 Good Reasons Why You Should Embrace A Healthy Lifestyle Today", 2));
        news.add(new News(1, "http://cdn-media-1.lifehack.org/wp-content/files/2016/04/11131407/23759430044_3fb2b7dd4a_o.png",
                "3 Tips for Building and Maintaining a Strong Online Reputation", 2));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

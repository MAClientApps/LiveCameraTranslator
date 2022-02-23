package com.lctrans.livecamtranslator;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lctrans.livecamtranslator.model.HistoryItem;
import com.lctrans.livecamtranslator.adapter.RecentSearchAdapter;
import com.lctrans.livecamtranslator.dbhelper.DatabaseHelper;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    ImageView back1;
    DatabaseHelper dbHelper;
    RecyclerView history;
    public List<HistoryItem> historylist;
    public RecentSearchAdapter mAdapter;
    ProgressBar progress;
    TextView tvempty;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_history);
        AdsUtil.showVideoAds(this,true);
        AdsUtil.showBannerAds(this, R.id.appodealBannerView);

        ImageView imageView = (ImageView) findViewById(R.id.back1);
        this.back1 = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                HistoryActivity.this.onBackPressed();
            }
        });
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        this.dbHelper = databaseHelper;
        databaseHelper.openDataBase();
        this.history = (RecyclerView) findViewById(R.id.recycler_view);
        this.progress = (ProgressBar) findViewById(R.id.progress);
        this.tvempty = (TextView) findViewById(R.id.tvempty);
        this.history.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.history.setItemAnimator(new DefaultItemAnimator());
        new getSearchHistory().execute(new Void[0]);
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    private class getSearchHistory extends AsyncTask<Void, Void, Void> {


        public void onPreExecute() {
            List unused = HistoryActivity.this.historylist = new ArrayList();
            HistoryActivity.this.historylist.clear();
            HistoryActivity.this.progress.setVisibility(View.VISIBLE);
        }


        public Void doInBackground(Void... voidArr) {
            try {
                HistoryActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        List unused = HistoryActivity.this.historylist = HistoryActivity.this.dbHelper.getSearchHistory();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                HistoryActivity.this.progress.setVisibility(View.GONE);
            }
            try {
                Thread.sleep(1000);
                return null;
            } catch (InterruptedException e2) {
                e2.printStackTrace();
                return null;
            }
        }


        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            HistoryActivity.this.progress.setVisibility(View.GONE);
            if (HistoryActivity.this.historylist.size() == 0) {
                HistoryActivity.this.tvempty.setVisibility(View.VISIBLE);
                return;
            }
            HistoryActivity.this.tvempty.setVisibility(View.GONE);
            HistoryActivity historyActivity = HistoryActivity.this;
            HistoryActivity historyActivity2 = HistoryActivity.this;
            RecentSearchAdapter unused = historyActivity.mAdapter = new RecentSearchAdapter(historyActivity2, historyActivity2.historylist);
            HistoryActivity.this.history.setAdapter(HistoryActivity.this.mAdapter);
        }
    }
}

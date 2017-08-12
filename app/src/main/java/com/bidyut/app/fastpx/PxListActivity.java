package com.bidyut.app.fastpx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bidyut.app.fastpx.di.ApplicationComponent;
import com.bidyut.app.fastpx.di.DaggerApplicationComponent;
import com.bidyut.app.fastpx.di.PxServiceModule;
import com.bidyut.app.fastpx.service.SearchResults;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class PxListActivity extends AppCompatActivity {
    private static final int NUM_COLUMNS = 2;

    @BindView(R.id.px_list)
    RecyclerView mPxListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_px_list);
        ButterKnife.bind(this);

        mPxListView.setClickable(true);
        mPxListView.setHasFixedSize(true);
        mPxListView.setLayoutManager(new GridLayoutManager(this, NUM_COLUMNS,
                GridLayoutManager.VERTICAL, false));
//        mPxListView.setLayoutManager(new StaggeredGridLayoutManager(2,
//                StaggeredGridLayoutManager.VERTICAL));

        getComponent().repository().getItems("car")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SearchResults>() {
                    @Override
                    public void call(SearchResults searchResults) {
                        success(searchResults);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        failure(throwable.getMessage());
                    }
                });
    }

    public void success(SearchResults results) {
        mPxListView.setAdapter(new PxListAdapter(this, results.photos));
    }

    public void failure(String error) {
        Toast.makeText(this, "Failed to load photo list: " + error, Toast.LENGTH_LONG).show();
    }

    private ApplicationComponent getComponent() {
        return DaggerApplicationComponent.builder()
                .pxServiceModule(new PxServiceModule(this))
                .build();
    }
}

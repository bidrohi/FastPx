package com.bidyut.app.fastpx;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bidyut.app.fastpx.service.PxPhoto;
import com.bidyut.app.fastpx.service.PxService;
import com.bidyut.app.fastpx.service.SearchResults;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class PxListActivity extends AppCompatActivity {
    private static final int NUM_COLUMNS = 2;

    @Bind(R.id.px_list)
    RecyclerView mPxListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_px_list);
        ButterKnife.bind(this);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PxService.API_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final PxService service = retrofit.create(PxService.class);

        mPxListView.setClickable(true);
        mPxListView.setHasFixedSize(true);
        mPxListView.setLayoutManager(new GridLayoutManager(this, NUM_COLUMNS,
                GridLayoutManager.VERTICAL, false));
//        mPxListView.setLayoutManager(new StaggeredGridLayoutManager(2,
//                StaggeredGridLayoutManager.VERTICAL));

        service.searchPhotos("car")
                .subscribeOn(Schedulers.io())
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

    private static class PxListAdapter extends RecyclerView.Adapter<PxViewHolder> {
        private final LayoutInflater mInflater;
        private final Picasso mPicasso;
        private final List<PxPhoto> mPhotos;

        public PxListAdapter(Context context, List<PxPhoto> photos) {
            mInflater = LayoutInflater.from(context);
            mPicasso = Picasso.with(context);
            mPhotos = photos;
        }

        @Override
        public int getItemCount() {
            return mPhotos.size();
        }

        @Override
        public PxViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            final View view = mInflater.inflate(R.layout.px_frame, parent, false);
            return new PxViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PxViewHolder holder, int position) {
            final PxPhoto pxPhoto = mPhotos.get(position);
            mPicasso.load(pxPhoto.image_url)
//                    .placeholder(R.drawable.placeholder)
                    .into(holder.image);
            holder.image.setContentDescription(pxPhoto.description);
        }
    }

    public static class PxViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image)
        public ImageView image;

        public PxViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

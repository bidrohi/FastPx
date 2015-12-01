package com.bidyut.app.fastpx;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class PxListActivity extends AppCompatActivity {
    private static final int NUM_COLUMNS = 2;

    private RecyclerView mPxListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_px_list);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PxService.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final PxService service = retrofit.create(PxService.class);

        mPxListView = (RecyclerView) findViewById(R.id.px_list);
        mPxListView.setClickable(true);
        mPxListView.setHasFixedSize(true);
//        mPxListView.setLayoutManager(new GridLayoutManager(this, NUM_COLUMNS,
//                GridLayoutManager.VERTICAL, false));
        mPxListView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));

        final Call<SearchResults> results = service.searchPhotos("car");
        results.enqueue(new Callback<SearchResults>() {
            @Override
            public void onResponse(Response<SearchResults> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    success(response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                failure(t.getMessage());
            }
        });
    }

    public void success(SearchResults results) {
        mPxListView.setAdapter(new PxListAdapter(this, results.photos));
    }

    public void failure(String error) {
        Toast.makeText(this, "Failed to load photo list: " + error, Toast.LENGTH_LONG).show();
    }

    private static class PxListAdapter extends RecyclerView.Adapter<AppViewHolder> {
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
        public AppViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            final View view = mInflater.inflate(R.layout.px_frame, parent, false);
            return new AppViewHolder(view);
        }

        @Override
        public void onBindViewHolder(AppViewHolder holder, int position) {
            final PxPhoto pxPhoto = mPhotos.get(position);
            mPicasso.load(pxPhoto.image_url)
//                    .placeholder(R.drawable.placeholder)
                    .into(holder.image);
            holder.image.setContentDescription(pxPhoto.description);
        }
    }

    private static class AppViewHolder extends RecyclerView.ViewHolder {
        public final ImageView image;

        public AppViewHolder(View view) {
            super(view);
            this.image = (ImageView) view.findViewById(R.id.image);
        }
    }
}

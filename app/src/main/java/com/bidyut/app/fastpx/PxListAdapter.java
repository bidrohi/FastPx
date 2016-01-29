package com.bidyut.app.fastpx;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bidyut.app.fastpx.data.PxPhoto;
import com.squareup.picasso.Picasso;

import java.util.List;

class PxListAdapter extends RecyclerView.Adapter<PxViewHolder> {
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
        mPicasso.load(pxPhoto.imageUrl)
//                    .placeholder(R.drawable.placeholder)
                .into(holder.image);
        holder.image.setContentDescription(pxPhoto.description);
    }
}

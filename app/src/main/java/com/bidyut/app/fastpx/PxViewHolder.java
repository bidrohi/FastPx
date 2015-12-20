package com.bidyut.app.fastpx;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PxViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.image)
    public ImageView image;

    public PxViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}

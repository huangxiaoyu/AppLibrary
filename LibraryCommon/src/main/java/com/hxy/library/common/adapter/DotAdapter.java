package com.hxy.library.common.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.hxy.app.librarycore.R;

/**
 * Created by Administrator on 2015/11/4.
 */
public class DotAdapter extends RecyclerView.Adapter<DotAdapter.ViewHolder> {
    Context context;
    private int count, position = 0;


    public DotAdapter(Context context, int count) {
        this.context = context;
        this.count = count;
    }

    public void setSelectPosition(int position) {
        this.position = position;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView iv_dot = new ImageView(context);
        ViewHolder viewHolder = new ViewHolder(iv_dot);
        iv_dot.setPadding(3,0,3,0);
        viewHolder.iv_top = iv_dot;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (this.position != position) {
            holder.iv_top.setImageResource(R.mipmap.icon_dot_normal);
        } else {
            holder.iv_top.setImageResource(R.mipmap.icon_dot);
        }
    }

    @Override
    public int getItemCount() {
        return count;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);

        }

        ImageView iv_top;
    }
}

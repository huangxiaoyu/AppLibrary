package com.hxy.library.common.view;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hxy.app.librarycore.R;
import com.hxy.library.common.listener.OnItemChildClickListener;
import com.hxy.library.common.utils.recycleviewdivider.HorizontalDividerItemDecoration;


/**
 * Created by huangxiaoyu on 2016/11/14.
 */

public class SheetDialog extends BottomSheetDialog implements View.OnClickListener {
    RecyclerView mRecyclerView;
    Context mContext;

    public SheetDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        init();
    }

    public SheetDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
        mContext = context;
        init();
    }

    protected SheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener
            cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int screenHeight = getScreenHeight(getContext());
        int statusBarHeight = getStatusBarHeight(getContext());
        int dialogHeight = screenHeight - statusBarHeight;
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ? ViewGroup
                .LayoutParams.MATCH_PARENT : dialogHeight);

    }

    private static int getScreenHeight(Context context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE)
        ).getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_sheetdialog, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager
                .VERTICAL, false));
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .build());
        view.findViewById(R.id.btnCancel).setOnClickListener(this);
        setContentView(view);
        setCancelable(false);
        setCanceledOnTouchOutside(true);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCancel) {
            dismiss();

        }
    }

    public class SheetAdapter extends RecyclerView.Adapter<Viewholder> {
        JSONArray mArray;
        String key;
        OnItemChildClickListener mOnItemChildClickListener;

        public void setOnItemChildViewClickListener(OnItemChildClickListener
                                                            onItemChildViewClickListener) {
            mOnItemChildClickListener = onItemChildViewClickListener;
        }

        public SheetAdapter(JSONArray array, String key) {
            mArray = array;
            this.key = key;
        }

        @Override
        public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout
                    .simple_center_text, parent, false);
            return new Viewholder(item);
        }

        @Override
        public void onBindViewHolder(Viewholder holder, final int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemChildClickListener != null) {
                        mOnItemChildClickListener.onItemChildClickListener(v, position, -1);
                    }
                }
            });
            JSONObject object = mArray.getJSONObject(position);
            holder.tv_text.setText(object.getString(key));

        }

        @Override
        public int getItemCount() {
            return mArray.size();
        }

        public JSONObject getItem(int position) {
            return mArray.getJSONObject(position);
        }


    }

    class Viewholder extends RecyclerView.ViewHolder {

        public Viewholder(View itemView) {
            super(itemView);

            tv_text = (TextView) itemView.findViewById(R.id.tvText);
        }

        TextView tv_text;
    }
}

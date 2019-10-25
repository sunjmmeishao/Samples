package com.example.android.recyclerplayground.adapters;

//import android.annotation.Nullable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.recyclerplayground.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.VerticalItemHolder> {

    private ArrayList<GameItem> mItems;

    private AdapterView.OnItemClickListener mOnItemClickListener;
    private  View.OnTouchListener OnTouchListener;
    public SimpleAdapter() {
        mItems = new ArrayList<GameItem>();
    }

    /*
     * A common adapter modification or reset mechanism. As with ListAdapter,
     * calling notifyDataSetChanged() will trigger the RecyclerView to update
     * the view. However, this method will not trigger any of the RecyclerView
     * animation features.
     */

    public int findItem(String addr) {
       for (int n =0; n< mItems.size();n++)
       {
           GameItem i = mItems.get(n);
           if (i.addr.equals(addr) )
           {
               return n;
           }
       }
       return -1;

       // notifyDataSetChanged();
    }

    public GameItem getItem(int position) {
        if (position <  mItems.size() && position >= 0)
          return mItems.get(position);
        return  null;

    }

    public void updateItemCount(int n,GameItem item) {

        mItems.set(n,item);

        notifyDataSetChanged();
    }
    public void setItemCount(int count) {
        mItems.clear();
        mItems.addAll(generateDummyData(count));

        notifyDataSetChanged();
    }

    /*
     * Inserting a new item at the head of the list. This uses a specialized
     * RecyclerView method, notifyItemInserted(), to trigger any enabled item
     * animations in addition to updating the view.
     */
    public void addItem(int position) {
        if (position > mItems.size()) return;
        
        mItems.add(position, generateDummyItem());
        notifyItemInserted(position);
    }
    public void addItem(GameItem item) {
        int position = mItems.size();

        mItems.add(position, item);
        notifyDataSetChanged();
       // notifyItemInserted(position);
    }


    /*
     * Inserting a new item at the head of the list. This uses a specialized
     * RecyclerView method, notifyItemRemoved(), to trigger any enabled item
     * animations in addition to updating the view.
     */
    public void removeItem(int position) {
        if (position >= mItems.size()) return;

        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public VerticalItemHolder onCreateViewHolder(ViewGroup container, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View root = inflater.inflate(R.layout.view_match_item, container, false);


        return new VerticalItemHolder(root, this);
    }

    @Override
    public void onBindViewHolder(VerticalItemHolder itemHolder, int position) {
        GameItem item = mItems.get(position);

        itemHolder.setAwayScore(String.valueOf(item.awayScore));
        itemHolder.setConnectState(item.connectState);
       // itemHolder.setAwayName(item.awayTeam);
        itemHolder.setAwayName(item.awayTeam);
        itemHolder.setHomeName(item.addr);

        Button ib_image = itemHolder.itemView.findViewById(R.id.logo_connect);

        ib_image.setOnTouchListener(OnTouchListener);
        ib_image.setTag(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setOnConnectClickListener(View.OnTouchListener onItemClickListener) {
        OnTouchListener =  onItemClickListener;
    }
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private void onItemHolderClick(VerticalItemHolder itemHolder) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(null, itemHolder.itemView,
                    itemHolder.getAdapterPosition(), itemHolder.getItemId());
        }
    }

    public static class GameItem {
        public String addr;
        public String awayTeam;
        public int connectState;
        public int awayScore;

        public GameItem(String addr, String awayTeam, int connectState, int awayScore) {
            this.addr = addr;
            this.awayTeam = awayTeam;
            this.connectState = connectState;
            this.awayScore = awayScore;
        }
    }

    public static class VerticalItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mHomeScore, mAwayScore;
        private TextView mHomeName, mAwayName;
        Button bt;
        private SimpleAdapter mAdapter;

        public VerticalItemHolder(View itemView, SimpleAdapter adapter) {
            super(itemView);
            itemView.setOnClickListener(this);

            mAdapter = adapter;

            mHomeScore = (TextView) itemView.findViewById(R.id.text_score_home);
            mAwayScore = (TextView) itemView.findViewById(R.id.text_score_away);
            mHomeName = (TextView) itemView.findViewById(R.id.text_team_home);
            mAwayName = (TextView) itemView.findViewById(R.id.text_team_away);
              bt = (Button)itemView.findViewById(R.id.logo_connect);


        }

        @Override
        public void onClick(View v) {
            mAdapter.onItemHolderClick(this);
        }

        public void setConnectState(int state) {
           if (state == 0)
           {
               bt.setText("已连接");
           }
           else
           {
               bt.setText("未连接");
           }
        }

        public void setAwayScore(CharSequence awayScore) {
            mAwayScore.setText(awayScore);
        }

        public void setHomeName(CharSequence homeName) {
            mHomeName.setText(homeName);
        }

        public void setAwayName(CharSequence awayName) {
            mAwayName.setText(awayName);
        }
    }

    public static GameItem generateDummyItem() {
        Random random = new Random();
        return new GameItem("Upset Home", "Upset Away",
                random.nextInt(100),
                random.nextInt(100) );
    }

    public static List<SimpleAdapter.GameItem> generateDummyData(int count) {
        ArrayList<SimpleAdapter.GameItem> items = new ArrayList<SimpleAdapter.GameItem>();

        for (int i=0; i < count; i++) {
            items.add(new SimpleAdapter.GameItem("Losers", "Winners", i, i+5));
        }

        return items;
    }
}

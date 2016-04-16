package razibkani.recyclerviewsample.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import razibkani.recyclerviewsample.R;
import razibkani.recyclerviewsample.model.pojo.News;

/**
 * Created by razibkani on 4/16/16.
 */
public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // constant for item type
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_BIG_NEWS = 1;
    public static final int TYPE_SMALL_NEWS = 2;
    public static final int TYPE_FOOTER = 3;

    List<View> headers = new ArrayList<>(); // lists header
    List<News> news = new ArrayList<>(); // lists news
    List<View> footers = new ArrayList<>(); // lists footer

    public Adapter(List<News> news) {
        this.news = news;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // if type == big, inflate big layout
        if (viewType == TYPE_BIG_NEWS) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_big_news, parent, false);
            return new NewsViewHolder(view);

        }
        // if type == big, inflate big layout
        else if (viewType == TYPE_SMALL_NEWS) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_small_news, parent, false);
            return new NewsViewHolder(view);
        }
        // else header/footer
        else {
            //create a new framelayout, or inflate from a resource
            FrameLayout frameLayout = new FrameLayout(parent.getContext());
            frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new HeaderFooterViewHolder(frameLayout);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position < headers.size()){
            View v = headers.get(position);
            // add header view
            setHeaderFooterView((HeaderFooterViewHolder) holder, v);
        }else if(position >= headers.size() + news.size()){
            View v = footers.get(position - news.size() - headers.size());
            // add footer view
            setHeaderFooterView((HeaderFooterViewHolder) holder, v);
        }else {
            // news item
            setNewsView((NewsViewHolder) holder, position - headers.size());
        }
    }

    @Override
    public int getItemCount() {
        //total size of header + news + footer
        return headers.size() + news.size() + footers.size();
    }

    @Override
    public int getItemViewType(int position) {
        //check item type
        if (position < headers.size()) {
            return TYPE_HEADER;
        } else if (position >= headers.size() + news.size()) {
            return TYPE_FOOTER;
        } else {
            if (news.get(position - 1).type == TYPE_BIG_NEWS) {
                return TYPE_BIG_NEWS;
            } else {
                return TYPE_SMALL_NEWS;
            }
        }
    }

    private void setHeaderFooterView(HeaderFooterViewHolder holder, View view){
        // remove view and add new view
        holder.base.removeAllViews();
        holder.base.addView(view);
    }

    private void setNewsView(NewsViewHolder holder, int position){
        // set image into imageview (using Picasso)
        Picasso.with(holder.image.getContext()).load(news.get(position).urlImage)
                .into(holder.image);
        // set title
        holder.title.setText(news.get(position).title);
    }

    // add header view to adapter
    public void addHeader(View header){
        if(!headers.contains(header)){
            headers.add(header);
            //animate
            notifyItemInserted(headers.size() - 1);
        }
    }

    // remove header view from adapter
    public void removeHeader(View header){
        if(headers.contains(header)){
            //animate
            notifyItemRemoved(headers.indexOf(header));
            headers.remove(header);
            if(header.getParent() != null) {
                ((ViewGroup) header.getParent()).removeView(header);
            }
        }
    }

    // add footer view to adapter
    public void addFooter(View footer){
        if(!footers.contains(footer)){
            footers.add(footer);
            //animate
            notifyItemInserted(headers.size()+news.size()+footers.size()-1);
        }
    }

    // remove footer view from adapter
    public void removeFooter(View footer){
        if(footers.contains(footer)) {
            //animate
            notifyItemRemoved(headers.size()+news.size()+footers.indexOf(footer));
            footers.remove(footer);
            if(footer.getParent() != null) {
                ((ViewGroup) footer.getParent()).removeView(footer);
            }
        }
    }

    // news ViewHolder
    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        public NewsViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    // header/footer ViewHolder
    public static class HeaderFooterViewHolder extends RecyclerView.ViewHolder {
        FrameLayout base;
        public HeaderFooterViewHolder(View itemView) {
            super(itemView);
            this.base = (FrameLayout) itemView;
        }
    }
}
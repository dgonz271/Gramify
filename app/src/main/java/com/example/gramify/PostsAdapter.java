package com.example.gramify;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder>{

    private Context context;
    private List<Post> posts;

    //Constructor was generated
    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    //After we extend PostsAdapter, we implement this method
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, viewGroup, false);
        return new ViewHolder(view);
    }

    //After we extend PostsAdapter, we implement this method
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Post post = posts.get(i);
        viewHolder.bind(post);
    }

    //After we extend PostsAdapter, we implement this method
    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textHandle;
        private ImageView imViewImage;
        private TextView textDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textHandle = itemView.findViewById(R.id.textView_handle);
            imViewImage = itemView.findViewById(R.id.imageView_Image);
            textDescription = itemView.findViewById(R.id.textView_description);
        }

        public void bind(Post post)
        {
            //TODO: Bind elements to post
            textHandle.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if(image != null)
                Glide.with(context).load(image.getUrl()).into(imViewImage);
            textDescription.setText(post.getDescription());
        }

    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }
}

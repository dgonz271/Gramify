package com.example.gramify.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gramify.Post;
import com.example.gramify.PostsAdapter;
import com.example.gramify.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {
    private static final String TAG = "PostsFragment";

    private RecyclerView recViewPosts;
    protected PostsAdapter postsAdapter;
    protected List<Post> displayedPosts;
    private SwipeRefreshLayout swipeContainer;

    //onCreateView to inflate the view
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recViewPosts = view.findViewById(R.id.recView_Posts);
        swipeContainer = view.findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //clearing out current posts
                postsAdapter.clear();
                //adding new items to display
                postsAdapter.addAll(displayedPosts);
                queryPosts();
                //Once the refresh is finished, we set setRefreshing to false
                swipeContainer.setRefreshing(false);
                Toast.makeText(getContext(), "Timeline Refreshed", Toast.LENGTH_SHORT).show();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_purple,
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light);





        //create data source
        displayedPosts= new ArrayList<>();
        //create adapter
        postsAdapter = new PostsAdapter(getContext(), displayedPosts);
        //set adapter on recyclerView
        recViewPosts.setAdapter(postsAdapter);
        //set layout manager on recyclerView
        recViewPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        queryPosts();


    }


    protected void queryPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);

        postQuery.setLimit(20);
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);

        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {

                if (e != null) {
                    Log.e(TAG, "Error with post query");
                    e.printStackTrace();
                    return;
                }
                displayedPosts.addAll(posts);
                postsAdapter.notifyDataSetChanged();

                for (int i = 0; i < posts.size(); i++) {
                    Log.d(TAG, "Post by @" + posts.get(i).getUser().getUsername() + ": " + posts.get(i).getDescription());
                }


            }
        });
    }


}

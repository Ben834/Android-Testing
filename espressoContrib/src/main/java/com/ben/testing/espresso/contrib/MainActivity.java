package com.ben.testing.espresso.contrib;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ben.espresso_contrib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link AppCompatActivity} displaying a {@link RecyclerView} and a navigation
 * drawer to be tested.
 */
public class MainActivity extends AppCompatActivity {

    @VisibleForTesting
    protected final static int NB_COMMENTS = 100;

    @VisibleForTesting
    protected final static String COMMENT_BEGINNING = "Item: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Drawer
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);

        //Navigation view
        final NavigationView navigationView = (NavigationView) findViewById(R.id.activity_main_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if(item.getItemId() == R.id.drawer_profile){
                    drawerLayout.closeDrawers();
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });

        //Content
        final TextView textView = (TextView) findViewById(R.id.activity_main_row_clicked);
        CommentAdapter commentAdapter = new CommentAdapter(new CommentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                textView.setText(String.valueOf(position));
            }
        }, getCommentList());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_main_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(commentAdapter);
        commentAdapter.notifyDataSetChanged();
    }

    private List<Comment> getCommentList() {
        List<Comment> comments = new ArrayList<>();
        for (int i = 0; i < NB_COMMENTS; i++) {
            String position = String.valueOf(i);
            comments.add(new Comment(position, COMMENT_BEGINNING + position));
        }
        return comments;
    }
}

package com.example.mylibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class WishlistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // setup the recycler view
        RecyclerView recyclerView = findViewById(R.id.wishlistRecyclerView);
        BookRecyclerViewAdapter adapter = new BookRecyclerViewAdapter(this, "wishlist");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setBooks(Utils.getInstance(this).getWishlistBooks());
    }

    // switch back to MainActivity when back button is pressed, delete this activity
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        // new task, don't keep any history of previous tasks, clear backstack
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    // go back to home screen when back arrow menu item selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
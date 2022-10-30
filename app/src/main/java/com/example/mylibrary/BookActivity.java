package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    public static final String BOOK_ID_KEY = "bookId";

    private TextView txtBookName, txtAuthor, txtPages, txtDescription;
    private Button btnAddToWishlist, btnAddToAlreadyRead, btnAddToFavourites, btnAddToCurrentlyReading;
    private ImageView bookImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initViews();

        // getting the intent that started the activity (can be null)
        Intent intent = getIntent();
        if(intent != null) {
            // retrieve id of the book selected in previous activity
            int bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
            if(bookId != -1) {
                Book incomingBook = Utils.getInstance(this).getBookById(bookId);
                if(incomingBook != null) {
                    setData(incomingBook);

                    handleAlreadyRead(incomingBook);
                    handleWishlistBooks(incomingBook);
                    handleCurrentlyReadingBooks(incomingBook);
                    handleFavouriteBooks(incomingBook);
                }
            }
        }
    }

    // disables button if book already exists in favourites list, sets onClickListener otherwise
    private void handleFavouriteBooks(final Book book) {
        ArrayList<Book> favouriteBooks = Utils.getInstance(this).getFavouriteBooks();

        // check if book already exists in list
        boolean existingInFavouriteBooks = false;
        for (Book b : favouriteBooks) {
            if(b.getId() == book.getId()) {
                existingInFavouriteBooks = true;
            }
        }

        if(existingInFavouriteBooks) {
            btnAddToFavourites.setEnabled(false);
        } else {
            //TODO: instead of disabling the button change it to 'remove book from list' button
            btnAddToFavourites.setOnClickListener(new View.OnClickListener() {
                // add the book to favourites list when button clicked, navigate user to FavouritesActivity
                @Override
                public void onClick(View view) {
                    if(Utils.getInstance(BookActivity.this).addToFavourites(book)) {
                        Toast.makeText(BookActivity.this, "Book Added To Favourites", Toast.LENGTH_SHORT).show();
                        btnAddToFavourites.setEnabled(false);
                        // navigate the user to already read list activity
                        Intent intent = new Intent(BookActivity.this, FavouritesActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    // disables button if book already exists in currently reading list, sets onClickListener otherwise
    private void handleCurrentlyReadingBooks(final Book book) {
        ArrayList<Book> currentlyReadingBooks = Utils.getInstance(this).getCurrentlyReadingBooks();

        // check if book already exists in list
        boolean existingInCurrentlyReadingBook = false;
        for (Book b : currentlyReadingBooks) {
            if(b.getId() == book.getId()) {
                existingInCurrentlyReadingBook = true;
            }
        }

        if(existingInCurrentlyReadingBook) {
            btnAddToCurrentlyReading.setEnabled(false);
        } else {
            //TODO: instead of disabling the button change it to 'remove book from list' button
            btnAddToCurrentlyReading.setOnClickListener(new View.OnClickListener() {
                // add the book to currently reading list when button clicked, navigate user to CurrentlyReadingActivity
                @Override
                public void onClick(View view) {
                    if(Utils.getInstance(BookActivity.this).addToCurrentlyReading(book)) {
                        Toast.makeText(BookActivity.this, "Book Added To Currently Reading List", Toast.LENGTH_SHORT).show();
                        btnAddToCurrentlyReading.setEnabled(false);
                        // navigate the user to already read list activity
                        Intent intent = new Intent(BookActivity.this, CurrentlyReadingActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    // disables button if book already exists in wishlist, sets onClickListener otherwise
    private void handleWishlistBooks(final Book book) {
        ArrayList<Book> wishlistBooks = Utils.getInstance(this).getWishlistBooks();

        // check if book already exists in list
        boolean existingInWishlistBook = false;
        for (Book b : wishlistBooks) {
            if(b.getId() == book.getId()) {
                existingInWishlistBook = true;
            }
        }

        if(existingInWishlistBook) {
            btnAddToWishlist.setEnabled(false);
        } else {
            //TODO: instead of disabling the button change it to 'remove book from list' button
            btnAddToWishlist.setOnClickListener(new View.OnClickListener() {
                // add the book to wishlist when button clicked, navigate user to WishlistActivity
                @Override
                public void onClick(View view) {
                    if(Utils.getInstance(BookActivity.this).addToWishlist(book)) {
                        Toast.makeText(BookActivity.this, "Book Added To Wishlist", Toast.LENGTH_SHORT).show();
                        btnAddToWishlist.setEnabled(false);
                        // navigate the user to already read list activity
                        Intent intent = new Intent(BookActivity.this, WishlistActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    // disables button if book already exists in already read list, sets onClickListener otherwise
    private void handleAlreadyRead(final Book book) {
        ArrayList<Book> alreadyReadBooks = Utils.getInstance(this).getAlreadyReadBooks();

        // check if book already exists in list
        boolean existingInAlreadyReadBooks = false;
        for (Book b : alreadyReadBooks) {
            if(b.getId() == book.getId()) {
                existingInAlreadyReadBooks = true;
            }
        }

        if(existingInAlreadyReadBooks) {
            btnAddToAlreadyRead.setEnabled(false);
        } else {
            //TODO: instead of disabling the button change it to 'remove book from list' button
            btnAddToAlreadyRead.setOnClickListener(new View.OnClickListener() {
                // add the book to already read books list when button clicked, navigate user to AlreadyReadBookActivity
                @Override
                public void onClick(View view) {
                    if(Utils.getInstance(BookActivity.this).addToAlreadyRead(book)) {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        btnAddToAlreadyRead.setEnabled(false);
                        // navigate the user to already read list activity
                        Intent intent = new Intent(BookActivity.this, AlreadyReadBookActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    // sets components to show the data of the selected book
    private void setData(Book book) {
        txtBookName.setText(book.getName());
        txtAuthor.setText(book.getAuthor());
        txtPages.setText(String.valueOf(book.getPages()));
        txtDescription.setText(book.getLongDesc());
        Glide.with(this)
                .asBitmap().load(book.getImageUrl())
                .into(bookImage);
    }

    // initializing components
    private void initViews() {
        txtBookName = findViewById(R.id.txtNameBookActivity);
        txtAuthor = findViewById(R.id.txtAuthorBookActivity);
        txtPages = findViewById(R.id.txtPagesBookActivity);
        txtDescription = findViewById(R.id.txtDescriptionBookActivity);

        btnAddToAlreadyRead = findViewById(R.id.btnAddToReadList);
        btnAddToFavourites = findViewById(R.id.btnAddToFavourites);
        btnAddToWishlist = findViewById(R.id.btnAddToWishlist);
        btnAddToCurrentlyReading = findViewById(R.id.btnAddToCurrentlyReading);

        bookImage = findViewById(R.id.imgBookImage);
    }
}
package com.example.mylibrary;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

// implement singleton pattern
public final class Utils {

    private static final String ALL_BOOKS_KEY = "all_books";
    private static final String ALREADY_READ_BOOKS_KEY = "already_read_books";
    private static final String WISHLIST_BOOKS_KEY = "wishlist_books";
    private static final String CURRENTLY_READING_BOOKS_KEY = "currently_reading_books";
    private static final String FAVOURITE_BOOKS_KEY = "favourite_books";

    private SharedPreferences sharedPreferences;

    private static Utils instance;
//    private static ArrayList<Book> allBooks;
//    private static ArrayList<Book> alreadyReadBooks;
//    private static ArrayList<Book> wishlistBooks;
//    private static ArrayList<Book> currentlyReadingBooks;
//    private static ArrayList<Book> favouriteBooks;

    // private constructor
    private Utils(Context context) {

        sharedPreferences = context.getSharedPreferences("alternateDB", Context.MODE_PRIVATE);

        if(getAllBooks() == null) {
            initData();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        // check if any of the lists are null, initialize them if so
        if(getAlreadyReadBooks() == null) {
            editor.putString(ALREADY_READ_BOOKS_KEY, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
        if(getWishlistBooks() == null) {
            editor.putString(WISHLIST_BOOKS_KEY, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
        if(getCurrentlyReadingBooks() == null) {
            editor.putString(CURRENTLY_READING_BOOKS_KEY, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
        if(getFavouriteBooks() == null) {
            editor.putString(FAVOURITE_BOOKS_KEY, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
    }

    // initialize some basic data
    private void initData() {
        String longDesc = "Natasha: I’m a girl who believes in science and facts. Not fate. Not destiny. Or dreams that will never come true. I’m definitely not the kind of girl who meets a cute boy on a crowded New York City street and falls in love with him. Not when my family is twelve hours away from being deported to Jamaica. Falling in love with him won’t be my story.\n" +
                "\n" +
                "Daniel: I’ve always been the good son, the good student, living up to my parents’ high expectations. Never the poet. Or the dreamer. But when I see her, I forget about all that. Something about Natasha makes me think that fate has something much more extraordinary in store—for both of us.\n" +
                "\n" +
                "The Universe: Every moment in our lives has brought us to this single moment. A million futures lie before us. Which one will come true?";

        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(1, "The sun is also a star", "Nicola Yoon", 400, "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1459793538i/28763485.jpg",
                "A short love story", longDesc, 7, "Review", false));
        books.add(new Book(2, "The picture of Dorian Gray", "Oscar Wilde", 280,"https://kbimages1-a.akamaihd.net/f437d8b9-fff8-4359-8ba5-9b5b20645c6b/353/569/90/False/the-picture-of-dorian-gray-322.jpg",
                "The story revolves around a portrait of Dorian Gray painted by Basil Hallward, a friend of Dorian's and an artist infatuated with Dorian's beauty.",
                "Long Desc", 8, "Review", false));

        // serialize variables and save in shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(ALL_BOOKS_KEY, gson.toJson(books));
        editor.commit();
    }

    /**
     * get the single instance of the class
     * @param context the context of the activity
     * @return returns the instance of the class
     */
    public static Utils getInstance(Context context) {
        if (null == instance) {
            instance = new Utils(context);
        }
        return instance;
    }

    // returns all books from shared preferences
    public ArrayList<Book> getAllBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALL_BOOKS_KEY, null), type);
        return books;
    }

    // returns already read books from shared preferences
    public ArrayList<Book> getAlreadyReadBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALREADY_READ_BOOKS_KEY, null), type);
        return books;
    }

    // returns wishlist books from shared preferences
    public ArrayList<Book> getWishlistBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(WISHLIST_BOOKS_KEY, null), type);
        return books;
    }

    // returns books currently being read from shared preferences
    public ArrayList<Book> getCurrentlyReadingBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(CURRENTLY_READING_BOOKS_KEY, null), type);
        return books;
    }

    // returns favourite books from shared preferences
    public ArrayList<Book> getFavouriteBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(FAVOURITE_BOOKS_KEY, null), type);
        return books;
    }

    // returns a book with given id, if it exists
    public Book getBookById(int id) {
        ArrayList<Book> books = getAllBooks();
        if(books != null) {
            for (Book b : books) {
                if(b.getId() == id) {
                    return b;
                }
            }
        }
        return null;
    }

    // add a book to already read books
    public boolean addToAlreadyRead(Book book) {
        ArrayList<Book> books = getAlreadyReadBooks();
        if(books != null) {
            if(books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(ALREADY_READ_BOOKS_KEY);
                editor.putString(ALREADY_READ_BOOKS_KEY, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    // add a book to the wishlist
    public boolean addToWishlist(Book book) {
        ArrayList<Book> books = getWishlistBooks();
        if(books != null) {
            if(books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(WISHLIST_BOOKS_KEY);
                editor.putString(WISHLIST_BOOKS_KEY, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    // add a book to currently reading list
    public boolean addToCurrentlyReading(Book book) {
        ArrayList<Book> books = getCurrentlyReadingBooks();
        if(books != null) {
            if(books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(CURRENTLY_READING_BOOKS_KEY);
                editor.putString(CURRENTLY_READING_BOOKS_KEY, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    // add a book to favourite books
    public boolean addToFavourites(Book book) {
        ArrayList<Book> books = getFavouriteBooks();
        if(books != null) {
            if(books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(FAVOURITE_BOOKS_KEY);
                editor.putString(FAVOURITE_BOOKS_KEY, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    // remove a book from already read books
    public boolean removeFromAlreadyRead(Book book) {
        ArrayList<Book> books = getAlreadyReadBooks();
        if(books != null) {
            for (Book b : books) {
                if(b.getId() == book.getId()) {
                    if(books.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(ALREADY_READ_BOOKS_KEY);
                        editor.putString(ALREADY_READ_BOOKS_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // remove a book from currently reading list
    public boolean removeFromCurrentlyReading(Book book) {
        ArrayList<Book> books = getCurrentlyReadingBooks();
        if(books != null) {
            for (Book b : books) {
                if(b.getId() == book.getId()) {
                    if(books.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(CURRENTLY_READING_BOOKS_KEY);
                        editor.putString(CURRENTLY_READING_BOOKS_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // remove a book from the wishlist
    public boolean removeFromWishlist(Book book) {
        ArrayList<Book> books = getWishlistBooks();
        if(books != null) {
            for (Book b : books) {
                if(b.getId() == book.getId()) {
                    if(books.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(WISHLIST_BOOKS_KEY);
                        editor.putString(WISHLIST_BOOKS_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // remove a book from favourite books
    public boolean removeFromFavourites(Book book) {
        ArrayList<Book> books = getFavouriteBooks();
        if(books != null) {
            for (Book b : books) {
                if(b.getId() == book.getId()) {
                    if(books.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(FAVOURITE_BOOKS_KEY);
                        editor.putString(FAVOURITE_BOOKS_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

package com.example.mylibrary;

import static com.example.mylibrary.BookActivity.BOOK_ID_KEY;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "BookRecViewAdapter";
    private ArrayList<Book> books = new ArrayList<>();
    // the activity (itself) that calls the adapter
    private Context mContext;

    // the name activity that calls the adapter - String, not a Context variable, used for checking purposes
    private String parentActivity;

    // constructor
    public BookRecyclerViewAdapter(Context mContext, String parentActivity) {
        this.mContext = mContext;
        this.parentActivity = parentActivity;
    }

    // called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item
    // https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.Adapter#onCreateViewHolder(android.view.ViewGroup,%20int)
    // calls constructor of ViewHolder to create new object
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false);
        return new ViewHolder(view);
    }

    // called when recycler view items need to be updated (when they are scrolled)
    // https://stackoverflow.com/questions/37523308/when-onbindviewholder-is-called-and-how-it-works
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.txtName.setText(books.get(position).getName());

        // when a book is selected, navigate user to BookActivity
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BookActivity.class);
                // pass the id of the selected book to BookActivity
                intent.putExtra(BOOK_ID_KEY, books.get(holder.getAdapterPosition()).getId());
                mContext.startActivity(intent);
            }
        });

        // set the image view to display an image from the web
        Glide.with(mContext)
                .asBitmap()
                .load(books.get(position).getImageUrl())
                .into(holder.imgBook);

        holder.txtAuthor.setText(books.get(position).getAuthor());
        holder.txtDescription.setText(books.get(position).getShortDesc());

        // check if needed to expand
        if(books.get(position).isExpanded()) {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedRelLayout.setVisibility(View.VISIBLE);
            holder.downArrow.setVisibility(View.GONE);

            // make delete button visible and set onClickListener if parentActivity is FavouriteBooks
            if(parentActivity.equals("favourites")){
                holder.btnDelete.setVisibility(View.VISIBLE);
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    // set onClickListener for delete button - create AlertDialog when pressed
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Are you sure you want to delete this book?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            // delete the book from list when deletion confirmed
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(Utils.getInstance(mContext).removeFromFavourites(books.get(holder.getAdapterPosition()))) {
                                    Toast.makeText(mContext, "Book removed", Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();
                                }
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // don't do anything
                            }
                        });
                        builder.create().show();
                    }
                });
            }
            // make delete button visible and set onClickListener if parentActivity is AlreadyReadBooksActivity
            else if(parentActivity.equals("alreadyRead")) {
                holder.btnDelete.setVisibility(View.VISIBLE);
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    // set onClickListener for delete button - create AlertDialog when pressed
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Are you sure you want to delete this book?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            // delete the book from list when deletion confirmed
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(Utils.getInstance(mContext).removeFromAlreadyRead(books.get(holder.getAdapterPosition()))) {
                                    Toast.makeText(mContext, "Book removed", Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();
                                }
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // don't do anything
                            }
                        });
                        builder.create().show();
                    }
                });
            } // make delete button visible and set onClickListener if parentActivity is WishlistActivity
            else if(parentActivity.equals("wishlist")) {
                holder.btnDelete.setVisibility(View.VISIBLE);
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    // set onClickListener for delete button - create AlertDialog when pressed
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Are you sure you want to delete this book?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            // delete the book from list when deletion confirmed
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(Utils.getInstance(mContext).removeFromWishlist(books.get(holder.getAdapterPosition()))) {
                                    Toast.makeText(mContext, "Book removed", Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();
                                }
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // don't do anything
                            }
                        });
                        builder.create().show();
                    }
                });
            } // make delete button visible and set onClickListener if parentActivity is CurrentlyReadingActivity
            else if(parentActivity.equals("currentlyReading")) {
                holder.btnDelete.setVisibility(View.VISIBLE);
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    // set onClickListener for delete button - create AlertDialog when pressed
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Are you sure you want to delete this book?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            // delete the book from list when deletion confirmed
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(Utils.getInstance(mContext).removeFromCurrentlyReading(books.get(holder.getAdapterPosition()))) {
                                    Toast.makeText(mContext, "Book removed", Toast.LENGTH_SHORT).show();
                                    //TODO: replace this with callback interface to resolve recycler view not updating
                                    notifyDataSetChanged();
                                }
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // don't do anything
                            }
                        });
                        builder.create().show();
                    }
                });
            } // set delete button to be visible if parentActivity is AllBooksActivity
            else {
                holder.btnDelete.setVisibility(View.GONE);
            }
        } else {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedRelLayout.setVisibility(View.GONE);
            holder.downArrow.setVisibility(View.VISIBLE);
        }
    }

    // number of items in the recycler view
    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    // data structure for each item in the recycler view, called by onCreateViewHolder()
    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView parent;
        private ImageView imgBook, downArrow, upArrow;
        private TextView txtName, txtAuthor, txtDescription, btnDelete;
        private RelativeLayout expandedRelLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            imgBook = itemView.findViewById(R.id.imgBook);
            downArrow = itemView.findViewById(R.id.imgDownArrow);
            upArrow = itemView.findViewById(R.id.imgUpArrow);
            txtName = itemView.findViewById(R.id.txtBookName);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtDescription = itemView.findViewById(R.id.txtShortDesc);
            expandedRelLayout = itemView.findViewById(R.id.expandedRelLayout);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            // expand card view
            downArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Book book = books.get(getAdapterPosition());
                    book.setExpanded(!book.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            // collapse card view
            upArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Book book = books.get(getAdapterPosition());
                    book.setExpanded(!book.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}

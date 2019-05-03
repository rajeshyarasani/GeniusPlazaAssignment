package com.test.genplaza.users.ui.main;

import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.test.genplaza.users.R;
import com.test.genplaza.users.ui.database.ImageTableManager;
import com.test.genplaza.users.ui.database.Preference;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class ImageViewerActivity extends AppCompatActivity {
    private String imageUrl;
    private ImageView imageView;
    private Button sendButton;
    ImageTableManager imageTableManager;
    private String imageId;
    private CommentListAdapter commentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_image_viewer);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            imageId = intent.getStringExtra(ImageListAdapter.KEY_IMAGE_ID);
            imageUrl = intent.getStringExtra(ImageListAdapter.KEY_IMAGE_URL);
        }

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Image Viewer");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        imageTableManager = ImageTableManager.getInstance();
//        ImageTableManager.initRoom(this);


        initUI();

        loadImage();
    }

    private EditText editText;
    private ObservableField<List<String>> commentListObservableField = new ObservableField<List<String>>(new ArrayList<String>());

    private android.databinding.Observable.OnPropertyChangedCallback imageCommentListChangeListener = new android.databinding.Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(android.databinding.Observable observable, int i) {
            if (commentListAdapter != null) {
                commentListAdapter.refreshAdapter(commentListObservableField.get());
            }
        }
    };

    private void initUI() {
        imageView = findViewById(R.id.image_fullscreen);
        sendButton = findViewById(R.id.button_send);
        editText = findViewById(R.id.edittext_comment);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = editText.getText().toString();
                if (comment == null || comment.isEmpty()) {
                    Toast.makeText(ImageViewerActivity.this, "Enter any comment", Toast.LENGTH_LONG).show();
                }
                Set<String> comments = Preference.getComments(imageId, editText.getText().toString());
                commentListObservableField.set(new ArrayList<String>(comments));
            }
        });
        commentListObservableField.addOnPropertyChangedCallback(imageCommentListChangeListener);

        initAdapter();
    }

    private void initAdapter() {
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView = findViewById(R.id.rv_comments);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(layoutManager);
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
            commentListAdapter = new CommentListAdapter(this, new ArrayList<String>(Preference.getComments(imageId)));
        recyclerView.setAdapter(commentListAdapter);
    }

    private void loadImage() {
        Picasso.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.ic_default_image_thumbnail)
                .into(imageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utils.hideKeyboard(this);
    }
}
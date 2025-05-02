package my.edu.utar.travelapp.Post;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import my.edu.utar.travelapp.R;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private final List<Post> posts;

    public PostAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int vt) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        Post post = posts.get(pos);

        h.textViewUserName.setText(post.getUserName());
        h.imageViewProfile.setImageResource(R.drawable.ic_profile_test);
        h.textViewPost.setText(post.getText());

        // Tagged people
        List<String> tags = post.getTaggedPeople();
        TextView tvFeedTagged = h.itemView.findViewById(R.id.textViewFeedTagged);
        if (tags != null && !tags.isEmpty()) {
            tvFeedTagged.setText("Tagged " + tags.size() + " people");
            tvFeedTagged.setVisibility(View.VISIBLE);
            tvFeedTagged.setOnClickListener(view -> {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Tagged People")
                        .setItems(tags.toArray(new String[0]), null)
                        .show();
            });
        } else {
            tvFeedTagged.setVisibility(View.GONE);
        }

        // Media URIs
        Uri img = post.getImageUri();
        Uri vid = post.getVideoUri();

        Log.d("PostAdapter", "Binding post at " + pos + " → img=" + img + ", vid=" + vid);

        try {
            if (img != null) {
                h.imageViewPost.setVisibility(View.VISIBLE);
                h.videoViewPost.setVisibility(View.GONE);
                h.imageViewPost.setImageURI(null); // Reset
                h.imageViewPost.setImageURI(img);
            } else if (vid != null) {
                h.videoViewPost.setVisibility(View.VISIBLE);
                h.imageViewPost.setVisibility(View.GONE);
                h.videoViewPost.setVideoURI(null); // Reset
                h.videoViewPost.setVideoURI(vid);
                h.videoViewPost.start();
            } else {
                h.imageViewPost.setVisibility(View.GONE);
                h.videoViewPost.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.e("PostAdapter", "Media binding failed at pos " + pos, e);
            h.imageViewPost.setVisibility(View.GONE);
            h.videoViewPost.setVisibility(View.GONE);
        }

        // Location
        String loc = post.getLocation();
        TextView tvLoc = h.itemView.findViewById(R.id.textViewPostLocation);
        if (loc != null && !loc.isEmpty()) {
            tvLoc.setText(loc);
            tvLoc.setVisibility(View.VISIBLE);
        } else {
            tvLoc.setVisibility(View.GONE);
        }

        // Like & comment counts
        h.buttonLike.setText("Like (" + post.getLikeCount() + ")");
        h.buttonComment.setText("Comment (" + post.getCommentCount() + ")");

        h.buttonLike.setIconTintResource(
                post.isLiked()
                        ? R.color.colorPrimary
                        : R.color.colorOnSurfaceVariant
        );

        // Like
        h.buttonLike.setOnClickListener(v -> {
            post.toggleLike();
            notifyItemChanged(pos);
        });

        // Comment
        h.buttonComment.setOnClickListener(v -> {
            Context ctx = v.getContext();
            final EditText input = new EditText(ctx);
            new AlertDialog.Builder(ctx)
                    .setTitle("Add a comment")
                    .setView(input)
                    .setPositiveButton("Post", (dialog, which) -> {
                        post.comment();
                        notifyItemChanged(pos);
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // Share
        h.buttonShare.setOnClickListener(v -> {
            Context ctx = v.getContext();
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, post.getText());
            ctx.startActivity(Intent.createChooser(share, "Share post via"));
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewProfile, imageViewPost;
        VideoView videoViewPost;
        TextView textViewUserName, textViewPost;
        Button buttonComment, buttonShare;
        MaterialButton buttonLike;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProfile = itemView.findViewById(R.id.imageViewProfile);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            textViewPost = itemView.findViewById(R.id.textViewPost);
            imageViewPost = itemView.findViewById(R.id.imageViewPost);
            videoViewPost = itemView.findViewById(R.id.videoViewPost);
            buttonLike = itemView.findViewById(R.id.buttonLike);
            buttonComment = itemView.findViewById(R.id.buttonComment);
            buttonShare = itemView.findViewById(R.id.buttonShare);
        }
    }
}

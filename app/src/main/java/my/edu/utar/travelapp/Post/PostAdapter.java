package my.edu.utar.travelapp.Post;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        h.setIsRecyclable(false);
        Post post = posts.get(pos);

        Uri profileUri = post.getUserProfileUri();
        if (profileUri != null) {
            h.imageViewProfile.setImageURI(null);
            h.imageViewProfile.setImageURI(profileUri);
        } else {
            h.imageViewProfile.setImageResource(R.drawable.ic_profile_test);
        }

        h.textViewPost.setText(post.getText());
        h.editTextNewComment.setText(""); // ✨ Clear previous input

        Post shared = post.getSharedPost();
        if (shared != null) {
            h.textViewUserName.setText("Shared by " + post.getSharedBy());
            h.sharedContainer.setVisibility(View.VISIBLE);
            h.textViewShared.setText("Originally posted by " + shared.getUserName() + ": " + shared.getText());
        } else {
            h.textViewUserName.setText(post.getUserName());
            h.sharedContainer.setVisibility(View.GONE);
        }

        List<String> tags = post.getTaggedPeople();
        if (tags != null && !tags.isEmpty()) {
            h.textViewFeedTagged.setText("Tagged " + tags.size() + " people");
            h.textViewFeedTagged.setVisibility(View.VISIBLE);
        } else {
            h.textViewFeedTagged.setVisibility(View.GONE);
        }

        Uri img = post.getImageUri(), vid = post.getVideoUri();
        if (img != null) {
            h.imageViewPost.setVisibility(View.VISIBLE);
            h.videoViewPost.setVisibility(View.GONE);
            h.imageViewPost.setImageURI(null);
            h.imageViewPost.setImageURI(img);
        } else if (vid != null) {
            h.videoViewPost.setVisibility(View.VISIBLE);
            h.imageViewPost.setVisibility(View.GONE);
            h.videoViewPost.setVideoURI(vid);
            h.videoViewPost.setOnPreparedListener(mp -> h.videoViewPost.start());
        } else {
            h.imageViewPost.setVisibility(View.GONE);
            h.videoViewPost.setVisibility(View.GONE);
        }

        String loc = post.getLocation();
        if (loc != null && !loc.isEmpty()) {
            h.textViewPostLocation.setText(loc);
            h.textViewPostLocation.setVisibility(View.VISIBLE);
        } else {
            h.textViewPostLocation.setVisibility(View.GONE);
        }

        h.buttonLike.setText("Like (" + post.getLikeCount() + ")");
        h.buttonLike.setIconTintResource(
                post.isLiked() ? R.color.colorPrimary : R.color.colorOnSurfaceVariant);

        h.buttonLike.setOnClickListener(v -> {
            post.toggleLike();
            notifyItemChanged(pos);
        });

        h.buttonShare.setOnClickListener(v -> {
            Context ctx = v.getContext();
            Intent intent = new Intent(ctx, PostActivity.class);
            intent.putExtra("sharedText", post.getText());
            intent.putExtra("sharedUser", post.getUserName());
            if (post.getImageUri() != null)
                intent.putExtra("sharedImageUri", post.getImageUri().toString());
            if (post.getVideoUri() != null)
                intent.putExtra("sharedVideoUri", post.getVideoUri().toString());
            ctx.startActivity(intent);
        });

        h.buttonSubmitComment.setOnClickListener(v -> {
            String commentText = h.editTextNewComment.getText().toString().trim();
            if (!commentText.isEmpty()) {
                Context ctx = v.getContext();
                SharedPreferences prefs = ctx.getSharedPreferences("AppSettingsPrefs", Context.MODE_PRIVATE);
                String userName = prefs.getString("user_name", "Anonymous");
                String imageUriStr = prefs.getString("profile_image", "");
                Uri commentProfileUri = imageUriStr.isEmpty()
                        ? Uri.parse("android.resource://" + ctx.getPackageName() + "/" + R.drawable.ic_profile_test)
                        : Uri.parse(imageUriStr);

                Post.Comment newComment = new Post.Comment(userName, commentProfileUri, commentText);
                post.addComment(newComment);
                h.editTextNewComment.setText("");
                notifyItemChanged(pos);
            }
        });

        List<Post.Comment> comments = post.getComments();
        h.layoutCommentList.removeAllViews();
        if (!comments.isEmpty()) {
            h.layoutCommentList.setVisibility(View.VISIBLE);
            Context ctx = h.itemView.getContext();
            for (Post.Comment c : comments) {
                View cv = LayoutInflater.from(ctx).inflate(R.layout.comment_item, h.layoutCommentList, false);
                ((TextView) cv.findViewById(R.id.textViewCommentUser)).setText(c.getUserName());
                ((TextView) cv.findViewById(R.id.textViewCommentText)).setText(c.getText());
                ImageView image = cv.findViewById(R.id.imageViewCommentProfile);
                if (c.getUserProfileUri() != null) {
                    image.setImageURI(null);
                    image.setImageURI(c.getUserProfileUri());
                } else {
                    image.setImageResource(R.drawable.ic_profile_test);
                }
                h.layoutCommentList.addView(cv);
            }
        } else {
            h.layoutCommentList.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewProfile, imageViewPost;
        VideoView videoViewPost;
        TextView textViewUserName, textViewPost, textViewPostLocation, textViewFeedTagged, textViewShared;
        LinearLayout sharedContainer, layoutCommentList;
        MaterialButton buttonLike;
        Button buttonComment, buttonShare, buttonSubmitComment;
        EditText editTextNewComment;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProfile = itemView.findViewById(R.id.imageViewProfile);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            textViewPost = itemView.findViewById(R.id.textViewPost);
            sharedContainer = itemView.findViewById(R.id.sharedContainer);
            textViewShared = itemView.findViewById(R.id.textViewSharedContent);
            textViewPostLocation = itemView.findViewById(R.id.textViewPostLocation);
            textViewFeedTagged = itemView.findViewById(R.id.textViewFeedTagged);
            imageViewPost = itemView.findViewById(R.id.imageViewPost);
            videoViewPost = itemView.findViewById(R.id.videoViewPost);
            buttonLike = itemView.findViewById(R.id.buttonLike);
            buttonComment = itemView.findViewById(R.id.buttonComment);
            buttonShare = itemView.findViewById(R.id.buttonShare);
            editTextNewComment = itemView.findViewById(R.id.editTextNewComment);
            buttonSubmitComment = itemView.findViewById(R.id.buttonSubmitComment);
            layoutCommentList = itemView.findViewById(R.id.layoutCommentList);
        }
    }
}

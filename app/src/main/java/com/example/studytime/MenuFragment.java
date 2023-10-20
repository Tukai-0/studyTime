package com.example.studytime;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class MenuFragment extends Fragment {
    ExoPlayer player;
    public MenuFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        player = new ExoPlayer.Builder(requireContext()).build();
        StyledPlayerView styledPlayerView = view.findViewById(R.id.video2_view);
        styledPlayerView.setPlayer(player);
        Uri videoUri = Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + R.raw.sample4);
        MediaItem mediaItem = MediaItem.fromUri(videoUri);
        player.setMediaItem(mediaItem);
        styledPlayerView.setVisibility(View.GONE);

        ImageView overlayImage = view.findViewById(R.id.overlay_image);
        overlayImage.setOnClickListener(view1 -> {
            overlayImage.setVisibility(View.GONE);
            styledPlayerView.setVisibility(View.VISIBLE);
            player.prepare();
        });
        ImageView popularImage1 = view.findViewById(R.id.pop1);
        popularImage1.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=O_ML-TVNaHQ"));
            view1.getContext().startActivity(intent);
        });
        ImageView popularImage2 = view.findViewById(R.id.pop2);
        popularImage2.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=-YbPnkKjRwo"));
            view1.getContext().startActivity(intent);
        });
        ImageView popularImage3 = view.findViewById(R.id.pop3);
        popularImage3.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=TpPx8jBWveM"));
            view1.getContext().startActivity(intent);
        });
        ImageView popularImage4 = view.findViewById(R.id.pop4);
        popularImage4.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=L_ZWuLNgjLk"));
            view1.getContext().startActivity(intent);
        });
        ImageView popularImage5 = view.findViewById(R.id.pop5);
        popularImage5.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=fMC-o0L5apU"));
            view1.getContext().startActivity(intent);
        });
        ImageView youtube_icon = view.findViewById(R.id.youtube_icon);
        youtube_icon.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@studyTimeBangla/videos"));
            view1.getContext().startActivity(intent);
        });
        ImageView facebook_icon = view.findViewById(R.id.facebook_icon);
        facebook_icon.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/StudyTime3675"));
            view1.getContext().startActivity(intent);
        });
        ImageView telegram_icon = view.findViewById(R.id.telegram_icon);
        telegram_icon.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/StudyTimeBangla"));
            view1.getContext().startActivity(intent);
        });
        ImageView instagram_icon = view.findViewById(R.id.instagram_icon);
        instagram_icon.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/studytimebangla"));
            view1.getContext().startActivity(intent);
        });
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        player.release();
    }
    @Override
    public void onPause() {
        super.onPause();
        player.setPlayWhenReady(false);
    }
    @Override
    public void onResume() {
        super.onResume();
        player.setPlayWhenReady(true);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        player.release();
    }

}
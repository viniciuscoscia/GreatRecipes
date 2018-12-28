package com.example.viniciuscoscia.greatrecipes.ui.fragments.stepDetails;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.viniciuscoscia.greatrecipes.R;
import com.example.viniciuscoscia.greatrecipes.entity.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class StepDetailsFragment extends Fragment implements Player.EventListener {

    public static final String TAG = StepDetailsFragment.class.getSimpleName();
    private static final String VIDEO_POSITION = "video_position";
    private static final String CURRENT_WINDOW = "currentWindow";

    private SimpleExoPlayer mExoPlayer;
    private PlayerView mPlayerView;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private TextView stepDescription;
    private Step step;
    private View rootView;
    private ImageView noVideo;
    private Long videoLastPosition = 0L;
    private VideoConfiguration videoConfiguration;
    private int currentWindow;
    private boolean isTablet;
    private boolean isPortrait = false;

    public StepDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle bundle) {

        findViews(inflater, container);

        isTablet = getResources().getBoolean(R.bool.isTablet);
        isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        if(!isTablet){
            videoConfiguration = (VideoConfiguration) rootView.getContext();
        }

        if(!isPortrait && !isTablet){
            setVideoFullScreen();
        }

        if(bundle != null) {
            step = bundle.getParcelable(Step.STEP_KEY);
        }

        stepDescription.setText(step.getDescription());

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializeMediaSession();
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initializeMediaSession();
            initializePlayer();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            videoLastPosition = savedInstanceState.getLong(VIDEO_POSITION);
            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW);
        }
    }

    private void findViews(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        mPlayerView = rootView.findViewById(R.id.playerView);
        stepDescription = rootView.findViewById(R.id.tv_stepDescription);
        noVideo = rootView.findViewById(R.id.no_video);
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    public void setStep(Step step) {
        this.step = step;

        if (rootView != null) {
            stepDescription.setText(step.getDescription());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        savePlayerValues();
        outState.putParcelable(Step.STEP_KEY, step);
        outState.putInt(CURRENT_WINDOW, currentWindow);
        outState.putLong(VIDEO_POSITION, videoLastPosition);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null && !step.getVideoURL().equals("")) {
            checkOrientation();
        }
    }

    private void setVideoForPortrait() {
        videoConfiguration.exitFullScreen();
        stepDescription.setVisibility(View.VISIBLE);
    }

    private void setVideoFullScreen() {
        ViewGroup.LayoutParams videoParams = mPlayerView.getLayoutParams();
        videoParams.width = videoParams.MATCH_PARENT;
        videoParams.height = videoParams.MATCH_PARENT;
        mPlayerView.setLayoutParams(videoParams);
        noVideo.setLayoutParams(videoParams);
        if(!isTablet)
            videoConfiguration.enterFullScreen();
    }

    public interface VideoConfiguration {
        void enterFullScreen();
        void exitFullScreen();
    }

    private void initializePlayer() {
        String videoURL = step.getVideoURL();

        if(videoURL.equals("")){
            noVideo.setVisibility(View.VISIBLE);
            mPlayerView.setVisibility(View.INVISIBLE);
            return;
        }

        if (mExoPlayer == null) {
            // let the factory create a player instance with default components
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(rootView.getContext(),
                                                new DefaultRenderersFactory(rootView.getContext()),
                                                new DefaultTrackSelector(),
                                                new DefaultLoadControl());
            mExoPlayer.setPlayWhenReady(true);
            mExoPlayer.seekTo(currentWindow, videoLastPosition);
            mPlayerView.setPlayer(mExoPlayer);
            mPlayerView.setDefaultArtwork(getContext().getDrawable(R.drawable.no_video));
        }
        String userAgent = Util.getUserAgent(rootView.getContext(), "GreatRecipes");
        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoURL), new DefaultDataSourceFactory(
                rootView.getContext(), userAgent), new DefaultExtractorsFactory(), null, null);

        mExoPlayer.prepare(mediaSource, false, false);
    }

    private void checkOrientation() {
        if (isTablet) {
            return;
        }
        if (isPortrait) {
            setVideoForPortrait();
        } else {
            setVideoFullScreen();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            savePlayerValues();
            releasePlayer();
        }
    }

    private void savePlayerValues() {
        if(mExoPlayer != null) {
            videoLastPosition = mExoPlayer.getContentPosition();
            currentWindow = mExoPlayer.getCurrentWindowIndex();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            savePlayerValues();
            releasePlayer();
        }
    }

    private void initializeMediaSession() {
        if (mMediaSession == null) {
            mMediaSession = new MediaSessionCompat(rootView.getContext(), TAG);

            mMediaSession.setFlags(
                    MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                            MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

            mMediaSession.setMediaButtonReceiver(null);

            mStateBuilder = new PlaybackStateCompat.Builder()
                    .setActions(
                            PlaybackStateCompat.ACTION_PLAY |
                                    PlaybackStateCompat.ACTION_PAUSE |
                                    PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                    PlaybackStateCompat.ACTION_PLAY_PAUSE);

            mMediaSession.setPlaybackState(mStateBuilder.build());
            mMediaSession.setCallback(new MySessionCallback());
            mMediaSession.setActive(true);
        }
    }

    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }
}
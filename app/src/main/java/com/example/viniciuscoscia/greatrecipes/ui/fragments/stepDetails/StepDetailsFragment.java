package com.example.viniciuscoscia.greatrecipes.ui.fragments.stepDetails;

import android.net.Uri;
import android.os.Bundle;
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
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class StepDetailsFragment extends Fragment implements Player.EventListener  {

    private static final String TAG = StepDetailsFragment.class.getSimpleName();
    private SimpleExoPlayer mExoPlayer;
    private PlayerView mPlayerView;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private TextView stepDescription;
    private Step step;
    private View rootView;
    private ImageView noVideo;

    public StepDetailsFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle bundle) {

        rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        mPlayerView = rootView.findViewById(R.id.playerView);
        stepDescription = rootView.findViewById(R.id.tv_stepDescription);
        noVideo = rootView.findViewById(R.id.no_video);

        mPlayerView.setDefaultArtwork(rootView.getResources()
                .getDrawable(R.drawable.question_mark));

        initializeMediaSession(rootView);
        setupVideoPlayer();
        stepDescription.setText(step.getDescription());

        return rootView;
    }

    private void setupVideoPlayer() {
        String videoURL = step.getVideoURL();

        if(videoURL.equals("")){
            noVideo.setVisibility(View.VISIBLE);
            mPlayerView.setVisibility(View.GONE);
            return;
        }

        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        RenderersFactory renderersFactory = new DefaultRenderersFactory(rootView.getContext());
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(rootView.getContext(), renderersFactory, trackSelector, loadControl);
        mPlayerView.setPlayer(mExoPlayer);
        mPlayerView.setDefaultArtwork(getContext().getDrawable(R.drawable.no_video));
        mExoPlayer.addListener(this);

        String userAgent = Util.getUserAgent(rootView.getContext(), "GreatRecipes");
        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoURL), new DefaultDataSourceFactory(
                rootView.getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(true);
    }

    private void initializeMediaSession(View view) {

        if(mMediaSession != null) {
            return;
        }

        mMediaSession = new MediaSessionCompat(view.getContext(), TAG);

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

    @Override
    public void onDetach() {
        super.onDetach();
        releasePlayer();
    }

    private void releasePlayer() {
        if(mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
        mMediaSession.setActive(false);
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
        if(rootView != null) {
            setupVideoPlayer();
            stepDescription.setText(step.getDescription());
        }
    }
}

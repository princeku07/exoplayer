package com.xperiencelabs.exoplayer;

import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceView;

import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.DefaultRenderersFactory;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.Renderer;
import androidx.media3.exoplayer.RenderersFactory;
import androidx.media3.exoplayer.audio.MediaCodecAudioRenderer;
import androidx.media3.exoplayer.mediacodec.MediaCodecSelector;
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.ProgressiveMediaSource;
import androidx.media3.extractor.Extractor;
import androidx.media3.extractor.ExtractorsFactory;
import androidx.media3.extractor.mp4.Mp4Extractor;
import androidx.media3.extractor.text.DefaultSubtitleParserFactory;

public class MainActivity extends AppCompatActivity {

    private ExoPlayer player;
    private SurfaceView videoView;
    MediaSource mediaSource;

    private static final String VIDEO_URI = "https://storage.googleapis.com/zingcam/flam/prod/augment/7eed174b-1b0d-41ef-b1b3-ae61c30b4e42.mp4";

    @OptIn(markerClass = UnstableApi.class) @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Assuming your layout has a SurfaceView with ID "videoView"

        videoView = findViewById(R.id.videoView);
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory();

        RenderersFactory vieoOnlyRenderersFactory =
                (handler, videoListener, audioListener, textOutput, metadataOutput) ->
                        new Renderer[] {
                                new MediaCodecAudioRenderer(
                                        this, MediaCodecSelector.DEFAULT, handler, audioListener)

                        };

        ExtractorsFactory mp4ExtractorsFactory = () -> new Extractor[] {
                new Mp4Extractor(new DefaultSubtitleParserFactory())
        };

        // 1st approach
        player = new ExoPlayer.Builder(this,new DefaultMediaSourceFactory(this,mp4ExtractorsFactory)).build();

       // 2nd approach
//        player = new ExoPlayer.Builder(this,MediaSource.Factory.UNSUPPORTED).build();

        // 3rd approach
//        player = new ExoPlayer.Builder(this,new DefaultMediaSourceFactory(this,ExtractorsFactory.EMPTY)).build();


        if(videoView !=  null){
            player.setVideoSurface(videoView.getHolder().getSurface());
        }

        mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(Uri.parse(VIDEO_URI)));

        player.setMediaSource(mediaSource);
        player.prepare();
        // Remove explicit prepare call, automatic playback should start
        // player.prepare();
        player.setPlayWhenReady(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (player != null) {
            player.setPlayWhenReady(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null) {
            player.pause();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
        }
    }
}

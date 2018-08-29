package foadkhezri.github.com.commonenglish;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhrasesFragment extends Fragment {
    ListView listView;
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.
                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        // Create and setup the {@link AudioManager} to request audio focus
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("کچا داری میری؟", "Where are you going?", R.raw.phrase_where_are_you_going, R.color.category_phrases));
        words.add(new Word("اسمت چیه؟", "What is your name?", R.raw.phrase_what_is_your_name, R.color.category_phrases));
        words.add(new Word("اسم من...", "My name is...", R.raw.phrase_my_name_is, R.color.category_phrases));
        words.add(new Word("حالت چطوره", "How are you?", R.raw.phrase_how_are_you_feeling, R.color.category_phrases));
        words.add(new Word("خوبم.", "I’m fine.", R.raw.phrase_im_feeling_good, R.color.category_phrases));
        words.add(new Word("داری میای؟", "Are you coming?", R.raw.phrase_are_you_coming, R.color.category_phrases));
        words.add(new Word("آره, دارم میام.", "Yes, I’m coming.", R.raw.phrase_yes_im_coming, R.color.category_phrases));
        words.add(new Word("دارم میام.", "I’m coming.", R.raw.phrase_im_coming, R.color.category_phrases));
        words.add(new Word("بزن بریم", "Let’s go.", R.raw.phrase_lets_go, R.color.category_phrases));
        words.add(new Word("بیا اینجا", "Come here.", R.raw.phrase_come_here, R.color.category_phrases));


        WordAdapter arrayAdapter = new WordAdapter(getActivity(), words);
        listView = rootView.findViewById(R.id.list);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                // Get the {@link Word} object at the given position the user clicked on
                Word word = words.get(position);

                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                int result = audioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now.

                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    mediaPlayer = MediaPlayer.create(getActivity(), word.getmAudioResourceId());

                    // Start the audio file
                    mediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mediaPlayer.setOnCompletionListener(completionListener);
                }
            }
        });
        return rootView;
    }
    public void releaseMediaPlayer(){
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
    @Override
    public void onPause() {
        super.onPause();
        releaseMediaPlayer();
    }

}

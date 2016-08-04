package com.android_camp.doseit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Locale;
import android.util.Log;
import android.speech.tts.TextToSpeech;


public class VoiceActivity extends BaseActivity {

    private static final String TAG = "HandsFreeDoseIt";

    /* speech */
    private ConversationHandler ch;
    private SpeechRecognizer sr;
    private TextToSpeech tts;


    /* display */
    TextView state;
    TextView input;
    TextView gender;
    TextView age;
    TextView weight;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

        if(!sr.isRecognitionAvailable(this))
            return;

        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new Listener());

        tts = new TextToSpeech(VoiceActivity.this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                    ch = new ConversationHandler(sr, tts, VoiceActivity.this);
                    ch.start();
                }
            }
        });


        state = (TextView) findViewById(R.id.state);
        input = (TextView) findViewById(R.id.input);
        gender = (TextView) findViewById(R.id.gender_value);
        age = (TextView) findViewById(R.id.age_value);
        weight = (TextView) findViewById(R.id.weight_value);


    }

    class Listener implements RecognitionListener {

        private static final float MIN_SCORE = -1;

        public void onReadyForSpeech(Bundle params) {
            Log.d(TAG, "onReadyForSpeech");

        }

        public void onBeginningOfSpeech() {
            Log.d(TAG, "onBeginningOfSpeech");

        }

        public void onRmsChanged(float rmsdB) {
            Log.d(TAG, "onRmsChanged");

        }

        public void onBufferReceived(byte[] buffer) {
            Log.d(TAG, "onBufferReceived");

        }

        public void onEndOfSpeech() {
            Log.d(TAG, "onEndOfSpeech");

        }

        public void onError(int error) {
            Log.d(TAG,  "error " +  error);
        }

        /*
        * Getting the maximum value result
        * */
        public void onResults(Bundle results) {

            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            float[] scores = results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);

            String result = new String();
            float maxScore = MIN_SCORE;

            assert (data.size() == scores.length);

            for (int i = 0; i < data.size(); i++) {
                if(maxScore < scores[i]) {
                    maxScore = scores[i];
                    result = (String) data.get(i);
                }
            }
            ch.onResults(result.toUpperCase());
        }

        public void onPartialResults(Bundle partialResults) {
            Log.d(TAG, "onPartialResults");
        }

        public void onEvent(int eventType, Bundle params) {
            Log.d(TAG, "onEvent " + eventType);
        }
    }

    public void updateDisplay(Display parameters){
        state.setText(parameters.state.toString());
        input.setText(parameters.input.toString());
        gender.setText(parameters.gender.toString());
        age.setText(parameters.age.toString());
        weight.setText(String.valueOf(parameters.weight));

    }

}


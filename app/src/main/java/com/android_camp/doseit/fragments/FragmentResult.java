package com.android_camp.doseit.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.android_camp.doseit.R;
import com.android_camp.doseit.VoiceActivity;
import com.android_camp.doseit.fragments.FragmentParameters.ParameterListener;

import java.util.ArrayList;
import java.util.Locale;

public class FragmentResult extends Fragment {
    private double answer = 0;
    private String name = "";
    private String warn = "";
    private TextView mResult, mMedicine, mWarning;

    private TextToSpeech textToSpeech;
    private FragmentParameters.Swipe swiper;
    private SpeechRecognizer speechRecognizer;
    private static final String BACK = "back";
    private static final String BACK_SPEECH = "Say back for more medicines";

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(container.getWindowToken(), 0);

        mResult = (TextView) view.findViewById(R.id.result_value);
        mMedicine = (TextView) view.findViewById(R.id.medicin_name);
        mWarning = (TextView) view.findViewById(R.id.warning);

        mResult.setText(String.valueOf(answer));
        mMedicine.setText(name);
        mWarning.setText(warn);


        if(getActivity() instanceof VoiceActivity) {
            swiper = (FragmentParameters.Swipe) getActivity();

            textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {

                @Override
                public void onInit(int status) {
                    if (status != TextToSpeech.ERROR) {
                        textToSpeech.setLanguage(Locale.US);
                    }
                }
            });
            swiper = (FragmentParameters.Swipe) getActivity();
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());
            speechRecognizer.setRecognitionListener(new BackListener());

        }

        return view;

    }

    private void listenForInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);

        speechRecognizer.startListening(intent);

    }

    public void setWarning(String warning) {
        this.warn = warning;
        if(mWarning != null) {
            mWarning.setText(String.valueOf(warn));
            textToSpeech.speak("WARNING!: " + mWarning.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            textToSpeech.speak("", TextToSpeech.QUEUE_ADD, null);
            textToSpeech.speak(BACK_SPEECH, TextToSpeech.QUEUE_ADD, null);
            listenForInput();
        }
    }

    public void setMedName(String name) {
        this.name = name;
        if(mMedicine != null) {
            mMedicine.setText(String.valueOf(this.name));
            textToSpeech.speak(this.name, TextToSpeech.QUEUE_ADD, null);
            textToSpeech.speak("", TextToSpeech.QUEUE_ADD, null);

        }
    }

    public void setResult(double result) {
        answer = result;
        if(mResult != null) {
            mResult.setText(String.format("%.5g%n", result) + " mg");
            textToSpeech.speak(mResult.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            textToSpeech.speak("", TextToSpeech.QUEUE_ADD, null);

        }
    }

    public double getAnswer() {
        return answer;
    }

    public String getName() {
        return name;
    }

    public String getWarn() {
        return warn;
    }

    private class BackListener implements RecognitionListener {

        private static final float MIN_SCORE = -1;


        @Override
        public void onReadyForSpeech(Bundle bundle) {

        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float v) {

        }

        @Override
        public void onBufferReceived(byte[] bytes) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onError(int i) {

        }

        @Override
        public void onResults(Bundle results) {

            speechRecognizer.stopListening();

            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            float[] scores = results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);

            String result = new String();
            float maxScore = MIN_SCORE;

            for (int i = 0; i < data.size(); i++) {
                if (maxScore < scores[i]) {
                    maxScore = scores[i];
                    result = (String) data.get(i);
                }
            }
            result = result.toLowerCase();
            if((result.endsWith(BACK))){
                swiper.moveToNext(1);
            }

        }

        @Override
        public void onPartialResults(Bundle bundle) {

        }

        @Override
        public void onEvent(int i, Bundle bundle) {

        }
    }
}

package com.android_camp.doseit.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android_camp.doseit.Parameter;
import com.android_camp.doseit.R;
import com.android_camp.doseit.VoiceActivity;

import java.util.ArrayList;
import java.util.Locale;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import org.w3c.dom.Text;


public class FragmentParameters extends Fragment {

    public interface Swipe {
        public void moveToNext(int currentScreen);
    }

    private Swipe swiper;

    public interface ParametersCallBack {
        void setParameters(Parameter p);
    }
    private ParametersCallBack myListener;

    private static final String TAG = "VoiceParameter";

    public enum States  { PARAMETERS, MEDICINE, CANCEL}
    public enum Params { AGE, GENDER, WEIGHT, DONE }
    Parameter parameter = new Parameter();


    /* speech */
    private VoiceParameterHandler ch;
    private SpeechRecognizer speechRecognizer;
    private TextToSpeech textToSpeech;

    /* display */
    private EditText weight;
    private RadioButton child;
    private RadioButton adult;
    private RadioButton male;
    private RadioButton female;

    private RadioGroup gender;
    private RadioGroup age;
    private EditText height;

    public FragmentParameters(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        myListener = (ParametersCallBack) getActivity();


        if(getActivity() instanceof VoiceActivity){

            swiper = (Swipe) getActivity();
            final VoiceActivity activity = (VoiceActivity) getActivity();

//            if (!speechRecognizer.isRecognitionAvailable(activity))
//                return view;

            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(activity);
            speechRecognizer.setRecognitionListener(new ParameterListener());

            textToSpeech = new TextToSpeech(activity, new TextToSpeech.OnInitListener() {

                @Override
                public void onInit(int status) {
                    if (status != TextToSpeech.ERROR) {
                        textToSpeech.setLanguage(Locale.US);
                        ch = new VoiceParameterHandler();
                        ch.start();
                    }
                }
            });

        }
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_parameters, container, false);

        TextView height_value = (TextView) view.findViewById(R.id.height_value);
        EditText h = (EditText) view.findViewById(R.id.height);
        height_value.setVisibility(View.GONE);
        h.setVisibility(View.GONE);


        male = (RadioButton) view.findViewById(R.id.male);
        female = (RadioButton) view.findViewById(R.id.female);
        child = (RadioButton) view.findViewById(R.id.kid);
        adult = (RadioButton) view.findViewById(R.id.adult);
        weight = (EditText) view.findViewById(R.id.weight);

        return view;
    }


    @Override
    public void onStart(){
        super.onStart();

        if(getActivity() instanceof VoiceActivity)
            return;

        age = (RadioGroup) getActivity().findViewById(R.id.age_group);
        Log.d("MSC",age==null?"null":"ago");
        if (age != null) {
            age.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    String age = "adult";
                    switch (checkedId) {
                        case R.id.adult:
                            age = "adult";
                            break;
                        case R.id.kid:
                            age = "kid";
                            break;
                    }
                    parameter.setAge(age);
                    Log.d("MSC","SetAge");
                    myListener.setParameters(parameter);
                }
            });
        }

        gender = (RadioGroup) getActivity().findViewById(R.id.gender_group);
        if (gender != null) {
            gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    String gender = "female";
                    switch (checkedId) {
                        case R.id.male:
                            gender = "male";
                            break;
                        case R.id.female:
                            gender = "female";
                            break;
                    }
                    parameter.setGender(gender);
                    myListener.setParameters(parameter);
                }
            });
        }


        height = (EditText) getActivity().findViewById(R.id.height);
        if (height != null)
            height.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    double height = (s.toString().isEmpty()) ? 170.0 : Double.parseDouble(s.toString());
                    parameter.setHeight(height);
                    myListener.setParameters(parameter);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        weight = (EditText) getActivity().findViewById(R.id.weight);
        if (weight != null)
            weight.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    double weight = (s.toString().isEmpty()) ? 60.0 : Double.parseDouble(s.toString());
                    parameter.setWeight(weight);
                    myListener.setParameters(parameter);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
    }


    public class ParameterListener implements RecognitionListener {

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
            Log.d(TAG, "error " + error);
        }

        /*
        * Getting the maximum value result
        * */
        public void onResults(Bundle results) {

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
            ch.onResults(result.toUpperCase());
        }

        public void onPartialResults(Bundle partialResults) {
            Log.d(TAG, "onPartialResults");
        }

        public void onEvent(int eventType, Bundle params) {
            Log.d(TAG, "onEvent " + eventType);
        }
    }


    private class VoiceParameterHandler {
        Params currentParam;
        States currentState;


        public VoiceParameterHandler() {}

        public void onResults(String result) {
            speechRecognizer.stopListening();

            switch (currentState){
                case PARAMETERS:
                    if(cancel(result)) /* if user wants to insert input again */
                        break;
                    switch (currentParam){
                        case GENDER:
                            fillGender(result);
                            break;
                        case AGE:
                            fillAge(result);
                            break;
                        case WEIGHT:
                            fillWeight(result);
                            break;
                        case DONE:
                            moveToNextFragment(result);
                            break;
                        default:
                            break;
                    }
                case MEDICINE:
                    moveToNextFragment(result);
                    break;
                default:
                    break;

            }

        }

        private void moveToNextFragment(String result) {
            /* confirm results */
            if(result.startsWith("OK")) {
                if(male.isChecked())
                    parameter.setGender("male");
                else parameter.setGender("female");
                double w = 0;

                try{
                    w = Double.parseDouble(weight.getText().toString());
                } catch (NumberFormatException e){
                    Log.e("FragmentParameter", "Error while parsing weight");
                }

                parameter.setWeight(w);
                if(child.isChecked())
                    parameter.setAge("kid");
                else parameter.setAge("adult");
                myListener.setParameters(parameter);

                swiper.moveToNext(1);

            }
            else askParameter();
        }

        private void fillWeight(String result) {
            try{
                double w = Double.parseDouble(result);
                weight.setText(String.valueOf(w));
                textToSpeech.speak(weight.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                nextParam();
            }catch (NumberFormatException e){
                askParameter();
            }
        }

        private void nextParam() {

            switch (currentParam){
                case GENDER:
                    currentParam = Params.AGE;
                    break;
                case AGE:
                    currentParam = Params.WEIGHT;
                    break;
                case WEIGHT:
                    currentParam = Params.DONE;
                    currentState = States.MEDICINE;
                    break;
                default:
                    break;
            }

            askParameter();


        }

        private void fillGender(String result) {
            String[] res = result.split(" ");
            if(res.length > 1){
                result = res[1];
            }
            if(result.startsWith("F")) {
                female.setChecked(true);
                textToSpeech.speak("Female", TextToSpeech.QUEUE_FLUSH, null);
                nextParam();
            }
            else if(result.startsWith("M")){
                male.setChecked(true);
                textToSpeech.speak("Male", TextToSpeech.QUEUE_FLUSH, null);
                nextParam();
            }
            else {
                askParameter();
            }

        }

        private void fillAge(String result) {
            try{
                double age = Double.parseDouble(result);
                if(age <= 15) {
                    child.setChecked(true);
                    textToSpeech.speak("Child", TextToSpeech.QUEUE_ADD, null);
                }
                else{
                    adult.setChecked(true);
                    textToSpeech.speak("Adult", TextToSpeech.QUEUE_ADD, null);

                }
                nextParam();


            }catch (NumberFormatException e){
                askParameter();
            }
        }


        private boolean cancel(String result) {
            String cancel = States.CANCEL.toString();
            if(cancel.startsWith(result) || cancel.endsWith(result)) {
                beforeParam();
                return true;
            }
            return false;
        }

        private void beforeParam() {

            switch (currentParam){
                case AGE:
                    currentParam = Params.GENDER;
                    male.setChecked(false);
                    female.setChecked(false);
                    break;
                case WEIGHT:
                    currentParam = Params.AGE;
                    child.setChecked(false);
                    adult.setChecked(false);
                    break;
                case DONE:
                    currentParam = Params.WEIGHT;
                    currentState = States.PARAMETERS;
                    weight.setText("0");
                default:
                    break;
            }

            askParameter();

        }

        public void start() {
            currentParam = Params.GENDER;
            currentState = States.PARAMETERS;
            askParameter();
        }

        private void askParameter() {

            switch (currentParam){
                case GENDER: /* first parameter */
                    textToSpeech.speak(Params.GENDER.toString(), TextToSpeech.QUEUE_ADD, null);
                    break;
                case WEIGHT:
                    textToSpeech.speak(Params.WEIGHT.toString(), TextToSpeech.QUEUE_ADD, null);
                    break;
                case AGE:
                    textToSpeech.speak(Params.AGE.toString(), TextToSpeech.QUEUE_ADD, null);
                    break;
                case DONE:
                    textToSpeech.speak("Say OK to confirm", TextToSpeech.QUEUE_ADD, null);
                    break;
                default:
                    throw new IllegalStateException();

            }

            listenForInput();
        }

        private void listenForInput() {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);

            speechRecognizer.startListening(intent);

        }

    }

}

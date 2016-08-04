package com.android_camp.doseit;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;

public class ConversationHandler {

    private final VoiceActivity activity;

    private final SpeechRecognizer speechRecognition;
    private final TextToSpeech textToSpeech;

    public enum States  { PARAMETERS, MEDICINE, CANCEL}
    public enum Params { AGE, GENDER, WEIGHT, DONE }

    private Display params;

    public ConversationHandler(SpeechRecognizer sr, TextToSpeech tts, VoiceActivity a) {
        params = new Display();
        this.speechRecognition = sr;
        this.textToSpeech = tts;
        this.activity = a;
    }

    public void onResults(String result) {
        speechRecognition.stopListening();

        switch (params.state){
            case PARAMETERS:
                if(cancel(result)) /* if user wants to insert input again */
                    break;
                switch (params.input){
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
                        break;
                    default:
                        break;
                }
            case MEDICINE:
                fetchMedicine(result);
                break;
            default:
                break;

        }

    }

    //TODO
    private void fetchMedicine(String result) {
//        display();
    }

    private void fillWeight(String result) {
        try{
            double weight = Double.parseDouble(result);
            params.weight = weight;
            nextParam();
        }catch (NumberFormatException e){
            result = States.CANCEL.toString();
            cancel(result);
        }
    }

    private void nextParam() {

        Params currentParam = params.input;

        switch (currentParam){
            case GENDER:
                params.input = Params.AGE;
                break;
            case AGE:
                params.input = Params.WEIGHT;
                break;
            case WEIGHT:
                params.input = Params.DONE;
                params.state = States.MEDICINE;
                break;
            default:
                break;
        }

        activity.updateDisplay(params);

        if(!currentParam.equals(Params.DONE.toString()))
            askParameter();

    }

    private void fillGender(String result) {
        if(result.startsWith("F")) {
            params.gender = Display.Gender.FEMALE;
            nextParam();
        }
        else if(result.startsWith("M")){
            params.gender = Display.Gender.MALE;
            nextParam();
        }
        else {
            result = States.CANCEL.toString();
            cancel(result);
        }

    }

    private void fillAge(String result) {
        try{
            double age = Double.parseDouble(result);
            if(age <= 15)
                params.age = Display.Age.MINOR;
            else params.age = Display.Age.ADULT;
            nextParam();
        }catch (NumberFormatException e){
            result = States.CANCEL.toString();
            cancel(result);
        }
    }

    private boolean cancel(String result) {
        String cancel = States.CANCEL.toString();
        if(cancel.equals(result)) {
            beforeParam();
            return true;
        }
        return false;
    }

    private void beforeParam() {
        Params currentParam = params.input;

        switch (currentParam){
            case AGE:
                params.input = Params.GENDER;
                params.gender = Display.Gender.NONE;
                break;
            case WEIGHT:
                params.input = Params.AGE;
                params.age = Display.Age.NONE;
                break;
            case DONE:
                params.input = Params.WEIGHT;
                params.weight = 0;
            default:
                break;
        }

        activity.updateDisplay(params);

        if(!currentParam.equals(Params.DONE.toString()))
            askParameter();

    }

    public void start() {
        params.input = Params.GENDER;
        askParameter();
    }

    private void askParameter() {

        switch (params.input){
            case GENDER: /* first parameter */
                textToSpeech.speak(Params.GENDER.toString(), TextToSpeech.QUEUE_FLUSH, null);
                break;
            case WEIGHT:
                textToSpeech.speak(Params.WEIGHT.toString(), TextToSpeech.QUEUE_FLUSH, null);
                break;
            case AGE:
                textToSpeech.speak(Params.AGE.toString(), TextToSpeech.QUEUE_FLUSH, null);
                break;
            default:
                throw new IllegalStateException();
                //break;

        }

        listenForInput();
    }

    private void listenForInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);

        speechRecognition.startListening(intent);

    }

}

package com.example.studytime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import android.view.ViewGroup.LayoutParams;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {
    EditText search_bar;
    ImageView mic;
    HorizontalScrollView scrollView;
    LinearLayout scrollviewlayout;
    TextView edit_text;
    public static final Integer RecordAudioRequestCode = 1;
    private SpeechRecognizer speechRecognizer;
    private ArrayList<String> buttonText = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onPause() {
        super.onPause();
        storeButtonTexts();
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        retrieveButtonTexts();
        search_bar = findViewById(R.id.searchbar);
        mic = findViewById(R.id.mic_icon);
        scrollView = findViewById(R.id.search_scroll);
        scrollviewlayout = findViewById(R.id.scroll_view);
        edit_text = findViewById(R.id.ClearSearches);
        speechRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
        final Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
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
            public void onResults(Bundle bundle) {
                ArrayList<String> arrayList = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                search_bar.setText(arrayList.get(0));
                String text=arrayList.get(0);
                search_bar.setSelection(search_bar.getText().length());
                addButton(text);
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onPartialResults(Bundle bundle) {
            }
            @Override
            public void onEvent(int i, Bundle bundle) {
            }
        });
        findViewById(R.id.arrow_icon).setOnClickListener(view -> {
            finish();
            Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        mic.setOnTouchListener((view, motionEvent) -> {

            if(ContextCompat.checkSelfPermission(SearchActivity.this, Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED){
                checkPermission();
            }

            if(motionEvent.getAction()==MotionEvent.ACTION_UP) {
                speechRecognizer.stopListening();
            }
            if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                speechRecognizer.startListening(speechIntent);
            }

            return false;
        });
        search_bar.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String text = search_bar.getText().toString();
                if (text.matches("")){
                    Toast.makeText(getApplicationContext(), "Please provide something", Toast.LENGTH_SHORT).show();
                }
                else {
                    addButton(text);
                    Log.d("Button Texts", "Button Texts: " + Arrays.toString(buttonText.toArray()));
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });
        edit_text.setOnClickListener(view -> showClearAlert());
    }
    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    private void checkPermission() {
        ActivityCompat.requestPermissions(SearchActivity.this, new String[]{
                Manifest.permission.RECORD_AUDIO},RecordAudioRequestCode);
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private void addButton(String text) {
        Button btn = new Button(this);
        btn.setText(text);
        btn.setTransformationMethod(null);
        btn.setBackground(getResources().getDrawable(R.drawable.recentsearch_roundedges));
        btn.setTextColor(Color.BLACK);
        btn.setTextSize(12);
        btn.setId(View.generateViewId());
        Log.d("Button Text", "Button Text: " + btn.getText());
        buttonText.add(text);
        btn.setPadding(10,5,5,10);
        btn.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        ((LinearLayout.LayoutParams) btn.getLayoutParams()).setMargins(10, 10, 10, 10);

        btn.setOnClickListener(view -> {
            String searchText = btn.getText().toString();
            Toast.makeText(getApplicationContext(), searchText, Toast.LENGTH_SHORT).show();
        });
        scrollviewlayout.addView(btn);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        speechRecognizer.destroy();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==RecordAudioRequestCode && grantResults.length>0) {
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissions Granted", Toast.LENGTH_SHORT).show();
            }

        }
    }
    private void showClearAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_clear, null);
        builder.setView(dialogView);
        final AlertDialog clearDialog = builder.create();

        Button yesButton = dialogView.findViewById(R.id.yes_button);
        Button noButton = dialogView.findViewById(R.id.no_button);

        yesButton.setOnClickListener(view -> {
            scrollviewlayout.removeAllViews();
            buttonText.clear();
            clearDialog.dismiss();
        });
        noButton.setOnClickListener(view -> clearDialog.dismiss());
        clearDialog.show();
    }
    private void storeButtonTexts() {
        SharedPreferences sharedPreferences = getSharedPreferences("buttonTexts", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("buttonTexts_size", buttonText.size());
        for (int i = 0; i < buttonText.size(); i++) {
            editor.putString("buttonTexts_" + i, buttonText.get(i));
        }
        editor.apply();
    }

    private void retrieveButtonTexts() {
        scrollviewlayout = findViewById(R.id.scroll_view);
        SharedPreferences sharedPreferences = getSharedPreferences("buttonTexts", Context.MODE_PRIVATE);
        int size = sharedPreferences.getInt("buttonTexts_size", 0);
        for (int i = 0; i < size; i++) {
            String text = sharedPreferences.getString("buttonTexts_" + i, null);
            Log.d("Button Texts", "Button Texts new: " + text);
            buttonText.add(text);
            Button button_shared = new Button(this);
            button_shared.setText(text);
            button_shared.setTransformationMethod(null);
            button_shared.setId(View.generateViewId());
            button_shared.setBackground(getResources().getDrawable(R.drawable.recentsearch_roundedges));
            button_shared.setTextColor(Color.BLACK);
            button_shared.setTextSize(12);
            button_shared.setPadding(10,5,5,10);
            button_shared.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            ((LinearLayout.LayoutParams) button_shared.getLayoutParams()).setMargins(10, 10, 10, 10);
            button_shared.setOnClickListener(view -> {
                String searchText = button_shared.getText().toString();
                Toast.makeText(getApplicationContext(), searchText, Toast.LENGTH_SHORT).show();
            });

            scrollviewlayout.addView(button_shared);
        }
    }
}



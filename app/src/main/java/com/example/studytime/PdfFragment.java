package com.example.studytime;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class PdfFragment extends Fragment {

    TextView pdftextView1;
    TextView pdftextView2;
    public PdfFragment() {
        // Required empty public constructor
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pdf, container, false);
        Bundle args = getArguments();
        assert args != null;
        String selectedClass = args.getString("selectedClass");
        String selectedSubject = args.getString("selectedSubject");
        String selectedItem = args.getString("selectedItem");

        // get the text to set in the TextView based on the selected options
        String text1 = "";
        String text2 = "";
        switch (selectedClass) {
            case "Class 10":
                switch (selectedSubject) {
                    case "Math":
                        text1 = "Math 1";
                        text2 = "Math 2";
                        break;
                    case "Physical Science":
                        text1 = "Physical Science 1";
                        text2 = "Physical Science 2";
                        break;
                    case "History":
                        text1 = "History 1";
                        text2 = "History 2";
                        break;
                }
                break;
            case "Class 9":
                switch (selectedSubject) {
                    case "Math":
                        text1 = "Math 1";
                        text2 = "Math 2";
                        break;
                    case "Physical Science":
                        text1 = "Physical Science 1";
                        text2 = "Physical Science 2";
                        break;
                    case "Life Science":
                        text1 = "Life Science 1";
                        text2 = "Life Science 2";
                        break;
                }
                break;
            case "Class 8":
                switch (selectedSubject) {
                    case "Math":
                        text1 = "Math 1";
                        text2 = "Math 2";
                        break;
                    case "Physical Science":
                        text1 = "Physical Science 1";
                        text2 = "Physical Science 2";
                        break;
                    case "Geography":
                        text1 = "Geography 1";
                        text2 = "Geography 2";
                        break;
                }
                break;
        }
        pdftextView1 = view.findViewById(R.id.pdfTextView1);
        pdftextView2 = view.findViewById(R.id.pdfTextView2);
        pdftextView1.setText(text1);
        pdftextView2.setText(text2);
        String finalText = text1;
        String finalText1 = text2;
        pdftextView1.setOnClickListener(v -> {
            Bundle args_1 = new Bundle();
            args_1.putString("selectedClass", selectedClass);
            args_1.putString("selectedChapter", finalText);
            PdfViewerFragment pdfViewerFragment = new PdfViewerFragment();
            pdfViewerFragment.setArguments(args_1);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, pdfViewerFragment)
                    .addToBackStack(null)
                    .commit();
        });
        pdftextView2.setOnClickListener(v -> {
            Bundle args_1 = new Bundle();
            args_1.putString("selectedClass", selectedClass);
            args_1.putString("selectedChapter", finalText1);
            PdfViewerFragment pdfViewerFragment = new PdfViewerFragment();
            pdfViewerFragment.setArguments(args_1);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, pdfViewerFragment)
                    .addToBackStack(null)
                    .commit();
        });
        return view;
    }
}
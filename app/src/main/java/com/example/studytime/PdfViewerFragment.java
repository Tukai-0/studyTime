package com.example.studytime;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;


public class PdfViewerFragment extends Fragment implements OnPageChangeListener {
    int currentPage = 0;
    PDFView pdfView;
    public PdfViewerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pdf_viewer, container, false);
        Bundle args_1 = getArguments();
        assert args_1 != null;
        String selectedClass = args_1.getString("selectedClass");
        String selectedChapter = args_1.getString("selectedChapter");
        pdfView = view.findViewById(R.id.pdfView);
        if (selectedClass.equals("Class 10")) {
            if (selectedChapter.equals("Math 1")) {
                pdfView.fromAsset("sample1.pdf").defaultPage(currentPage).onPageChange(this).load();
            } else if (selectedChapter.equals("Math 2")) {
                pdfView.fromAsset("sample2.pdf").defaultPage(currentPage).onPageChange(this).load();
            }
        }
        return view;
    }
    @Override
    public void onPageChanged(int page, int pageCount) {
        currentPage = page;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        pdfView.recycle();
    }
}
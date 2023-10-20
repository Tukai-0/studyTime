package com.example.studytime;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class CategoryFragment extends Fragment {

    String selectedClass, selectedSubject, selectedItem;
    TextView tvClassSpinner, tvSubjectSpinner, tvItemSpinner;
    Button submitButton;
    Spinner class_spinner, subject_spinner, item_spinner;

    FrameLayout mContainer;
    ArrayAdapter<CharSequence> classAdapter, subjectAdapter, itemAdapter;
    public CategoryFragment() {
        // Required empty public constructor
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        class_spinner = view.findViewById(R.id.spinner_class);
        classAdapter = ArrayAdapter.createFromResource(getContext(), R.array.dropdown_items_class, R.layout.custom_spinner_dropdown_item);
        classAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        class_spinner.setAdapter(classAdapter);
        subject_spinner = view.findViewById(R.id.spinner_subject);
        item_spinner = view.findViewById(R.id.spinner_item);
        class_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

                selectedClass = class_spinner.getSelectedItem().toString();
                int parentId = parent.getId();
                if (parentId == R.id.spinner_class){
                    switch (selectedClass){
                        case "Select Your Class": subjectAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.default_items_subject, R.layout.custom_spinner_dropdown_item); itemAdapter = ArrayAdapter.createFromResource(getContext(), R.array.default_dropdown_items, R.layout.custom_spinner_dropdown_item);
                            break;
                        case "Class 10": subjectAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.class10_items_subject, R.layout.custom_spinner_dropdown_item); itemAdapter = ArrayAdapter.createFromResource(getContext(), R.array.dropdown_items, R.layout.custom_spinner_dropdown_item);
                            break;
                        case "Class 9": subjectAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.class9_items_subject, R.layout.custom_spinner_dropdown_item); itemAdapter = ArrayAdapter.createFromResource(getContext(), R.array.dropdown_items, R.layout.custom_spinner_dropdown_item);
                            break;
                        case "Class 8": subjectAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.class8_items_subject, R.layout.custom_spinner_dropdown_item); itemAdapter = ArrayAdapter.createFromResource(getContext(), R.array.dropdown_items, R.layout.custom_spinner_dropdown_item);
                            break;
                        default: break;
                    }
                    subjectAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
                    subject_spinner.setAdapter(subjectAdapter);
                    itemAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
                    item_spinner.setAdapter(itemAdapter);

                    subject_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedSubject = subject_spinner.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    item_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedItem = item_spinner.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        submitButton = view.findViewById(R.id.submit_button);
        tvClassSpinner = view.findViewById(R.id.textView_class);
        tvSubjectSpinner = view.findViewById(R.id.textView_subject);
        tvItemSpinner = view.findViewById(R.id.textView_item);
        mContainer = view.findViewById(R.id.frame_layout);
        submitButton.setOnClickListener(v -> {
            if (selectedClass.equals("Select Your Class")){
                tvClassSpinner.setError("Class is required");
                tvClassSpinner.requestFocus();
            } else if (selectedSubject.equals("Select Your Subject")) {
                tvSubjectSpinner.setError("Subject is required");
                tvSubjectSpinner.requestFocus();
                tvClassSpinner.setError(null);
            } else if (selectedItem.equals("Select Your Item")){
                tvItemSpinner.setError("Item is required");
                tvItemSpinner.requestFocus();
                tvClassSpinner.setError(null);
            } else {
                tvSubjectSpinner.setError(null);
                tvItemSpinner.setError(null);
                Bundle args = new Bundle();
                args.putString("selectedClass", selectedClass);
                args.putString("selectedSubject", selectedSubject);
                args.putString("selectedItem", selectedItem);
                PdfFragment pdfFragment = new PdfFragment();
                pdfFragment.setArguments(args);
                // Replace the container layout with the new fragment
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, pdfFragment)
                        .addToBackStack(null)
                        .commit();
                //Toast.makeText(getContext(), "Selected Class: "+selectedClass+", Selected Subject: "+selectedSubject+", Selected Item: "+selectedItem , Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
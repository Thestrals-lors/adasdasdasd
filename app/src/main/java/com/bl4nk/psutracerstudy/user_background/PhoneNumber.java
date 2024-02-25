package com.bl4nk.psutracerstudy.user_background;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bl4nk.psutracerstudy.R;
import com.bl4nk.psutracerstudy.admin_adapter.PhoneNumberAdapter;
import com.bl4nk.psutracerstudy.admin_model.AddressModel;
import com.bl4nk.psutracerstudy.admin_model.PhoneNumberModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class PhoneNumber extends Fragment {

    FirebaseFirestore fStore;
    CollectionReference reference;
    RecyclerView recyclerView;

    PhoneNumberAdapter phoneNumberAdapter;
    List<PhoneNumberModel> list;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public PhoneNumber() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_phone_number, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fStore = FirebaseFirestore.getInstance();
        reference = fStore.collection("Users");

        init(view);

        loadAllPhoneNumber();
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();
        phoneNumberAdapter = new PhoneNumberAdapter(getContext(), list);
        recyclerView.setAdapter(phoneNumberAdapter);
    }

    private void loadAllPhoneNumber() {
        reference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                if (value == null) {
                    return;
                }
                list.clear();
                for(QueryDocumentSnapshot snapshot: value){
                    if(snapshot.getString("userType").equals("Admin")){

                    }
                    else {
                        list.add(new PhoneNumberModel(
                                snapshot.getString("userMobileNumber")
                        ));

                    }
                }
                phoneNumberAdapter.notifyDataSetChanged();
            }
        });
    }
}
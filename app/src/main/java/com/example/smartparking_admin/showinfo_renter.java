package com.example.smartparking_admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class showinfo_renter extends Fragment {


    String address, email, fname, lname, phone;
    private FirebaseFirestore db;
    TextView addresstxt, emailtxt, fnametxt, lnametxt, phonetxt;
    Button delbtn;

    public showinfo_renter() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        email = getArguments().getString("email");

        return inflater.inflate(R.layout.fragment_showinfo_renter, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addresstxt = getActivity().findViewById(R.id.detailaddress);
        fnametxt = getActivity().findViewById(R.id.detailfname);
        lnametxt = getActivity().findViewById(R.id.detaillname);
        emailtxt = getActivity().findViewById(R.id.detailemail);
        phonetxt = getActivity().findViewById(R.id.detailphone);
        delbtn = getActivity().findViewById(R.id.rt_delbtn);


        db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("renter").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        fnametxt.setText(document.getData().get("fname").toString().trim());
                        lnametxt.setText(document.getData().get("lname").toString().trim());
                        addresstxt.setText(document.getData().get("address").toString().trim());
                        phonetxt.setText(document.getData().get("phone").toString().trim());
                        emailtxt.setText(document.getData().get("email").toString().trim());


                    } else {
                        Toast.makeText(getContext(), "No such document", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "No such document");

                    }
                } else {

                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.collection("renter").document(email)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "successfully deleted!");
                                Toast.makeText(getContext(), "successfully deleted!", Toast.LENGTH_SHORT).show();
                                Fragment fragment;
                                fragment = new renterfrag();
                                loadFragment(fragment);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });

            }
        });
    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_renter, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
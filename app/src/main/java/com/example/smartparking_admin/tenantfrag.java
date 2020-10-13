package com.example.smartparking_admin;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;


public class tenantfrag extends Fragment {


    private FirebaseFirestore db;
    public ListAdapter adapter = null;
    public ListView listView;
    ProgressDialog loading;

    String email;

    public tenantfrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tenantfrag, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = getActivity().findViewById(R.id.list_tenant);

        loading = ProgressDialog.show(getContext(), "Fetching Profile", "Please wait ...");
        loading.setCancelable(false);

        db = FirebaseFirestore.getInstance();
        final ArrayList<HashMap<String, String>> list = new ArrayList<>();
        db.collection("tenant")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d("TAGH", document.getId() + " => " + document.getData());

                                email = document.getData().get("email").toString().trim();

                                HashMap<String, String> item = new HashMap<>();

                                item.put("email", email);

                                list.add(item);



                            }
                        } else {
                            loading.dismiss();
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                        adapter = new SimpleAdapter(getContext(),list,R.layout.tenant_listbind,
                                new String[]{"email"},
                                new int[]{R.id.tt_email});


                        listView.setAdapter(adapter);
                        loading.dismiss();
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

                HashMap<String, String> item = (HashMap<String, String>) parent.getItemAtPosition(position);


                Fragment fragment;
                fragment = new showinfo_tenant();

                Bundle args = new Bundle();
                args.putString("email",item.get("email"));


                fragment.setArguments(args);

                loadFragment(fragment);


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
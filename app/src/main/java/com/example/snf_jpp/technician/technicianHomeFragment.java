package com.example.snf_jpp.technician;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.snf_jpp.MainActivity;
import com.example.snf_jpp.ModelTask;
import com.example.snf_jpp.R;
import com.example.snf_jpp.users.aboutUsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class technicianHomeFragment extends Fragment implements com.example.snf_jpp.technician.technicianAdapterWork.OnItemClickListener {
    //-----------------------------------------------------------------------------------
    public static final String EXTRA_ID = "complaintID";
    public static final String EXTRA_Time = "complaintTime";
    public static final String EXTRA_WorkType = "complaintWorkType";
    public static final String EXTRA_Status = "complaintStatus";
    public static final String EXTRA_Location = "complaintLocation";
    public static final String EXTRA_ImageBefore = "complaintImageBefore";
    public static final String EXRA_ImageAfter = "complaintImageAfter";
    public static final String EXTRA_Title = "complaintTitle";
    public static final String EXTRA_Dcomplainer = "complainerDescription";
    //-----------------------------------------------------------------------------------

    //firebase auth
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    List<ModelTask> taskList;
    technicianAdapterWork technicianAdapterWork;
    ProgressBar progressBar;

    public technicianHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_technician, container, false);

        //init
        firebaseAuth = FirebaseAuth.getInstance();

        //recycler view and its properties
        recyclerView = view.findViewById(R.id.ReportRecyclerview);
        progressBar = view.findViewById(R.id.progress_bar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        //show newest post first, for this load from last
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        //set layout to recyclerview
        recyclerView.setLayoutManager(layoutManager);

        //init task list
        taskList = new ArrayList<>();

        //loadTask
        loadTasks();

        return view;

    }

    private void loadTasks() {
        //path of all tasks
        //A Firebase reference represents a particular location in your Database and can be used for reading or writing data to that Database location.
        //The entry point for accessing a Firebase Database. You can get an instance by calling getInstance(). To access a location in the database and read or write data, use getReference().
        //the key word task
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tasks");

        //get all data from this ref
        //Classes implementing this interface can be used to receive events about data changes at a location.
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskList.clear();
                //A DataSnapshot instance contains data from a Firebase Database location. Any time you read Database data, you receive the data as a DataSnapshot.
                for (DataSnapshot ds:dataSnapshot.getChildren()){

                    //get value into modelTask from ds(data)
                    ModelTask modelTask = ds.getValue(ModelTask.class);

                    //add modeltask value/data into task list
                    taskList.add(modelTask);

                    technicianAdapterWork = new technicianAdapterWork(getActivity(),taskList);
                    // set adapter to recyclerview
                    recyclerView.setAdapter(technicianAdapterWork);

                    //------------
                    technicianAdapterWork.setOnItemClickListener(technicianHomeFragment.this);

                    technicianAdapterWork.notifyDataSetChanged();
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //in case of error
                //dont know why error
//                Toast.makeText(getActivity(),"error"+ databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

//    private void searchTasks(final String searchQuery){
//        //path of all tasks
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tasks");
//        //get all data from this ref
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                taskList.clear();
//                for (DataSnapshot ds:dataSnapshot.getChildren()){
//                    ModelTask modelTask = ds.getValue(ModelTask.class);
//
////                           || modelTask.getpDescr().toLowerCase().contains(searchQuery.toLowerCase())
//                    if(modelTask.getpTitle().toLowerCase().contains(searchQuery.toLowerCase()) ){
//                        taskList.add(modelTask);
//                    }
//
//
//                    technicianAdapterWork = new technicianAdapterWork(getActivity(),taskList);
//                    // set adapter to recyclerview
//                    recyclerView.setAdapter(technicianAdapterWork);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                //in case of error
//                Toast.makeText(getActivity(),""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void checkUserStatus(){
        //get current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            //user is signed in stay here
            //set email of logged in user
//            ProfileTv.setText(user.getEmail());
            Toast.makeText(getContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();
        }
        else {
            //user not signed in, go to main activity
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
    }

    //show options menu
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);// to show menu option in fragment

        super.onCreate(savedInstanceState);
    }

    // inflate options menu
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflating menu
        inflater.inflate(R.menu.menu_home,menu);

//        searchView to search posts by post title/description
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
//
//        //search listener
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                //called when user press search button
//                if (!TextUtils.isEmpty(s)){
//                    searchTasks(s);
//                }
//                else {
//                    loadTasks();
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                //called as and when user press any letter
//                if (!TextUtils.isEmpty(s)) {
//                    searchTasks(s);
//                }
//                else {
//                    loadTasks();
//                }
//                return false;
//            }
//        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    //handle menu item clicks
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //get item id
        int id = item.getItemId();

        if (id == R.id.action_aboutUs) {
            startActivity(new Intent(getActivity(), aboutUsActivity.class));
        }

        if (id == R.id.action_logout){

            firebaseAuth.signOut();
            Toast.makeText(getActivity(),"Logout success.",Toast.LENGTH_SHORT).show();
            checkUserStatus();
        }

        if (id == R.id.action_add_complaint){
            startActivity(new Intent(getActivity(), technicianWorkActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
//----------------------------------

    @Override
    public void onItemClick(int position) {
//        Intent reportIntent = new Intent(getActivity(),complaintReportPage.class);
//        ModelTask clickedItem = taskList.get(position);
//
//        reportIntent.putExtra(EXTRA_ID,clickedItem.getuName());
//        reportIntent.putExtra(EXTRA_Time,clickedItem.getpTime());
//
//        startActivity(reportIntent);
    }
}

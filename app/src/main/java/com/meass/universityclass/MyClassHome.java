package com.meass.universityclass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class MyClassHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Toolbar mainToolbar;
    private String current_user_id;
    private BottomNavigationView mainBottomNav;
    private DrawerLayout mainDrawer;
    private ActionBarDrawerToggle mainToggle;
    private NavigationView mainNav;

    FrameLayout frameLayout;
    private TextView drawerName;
    private CircleImageView drawerImage;
    FirebaseAuth firebaseAuth;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseFirestoreSettings settings;
    private DatabaseReference mUserRef;

    ChatFragment chatFragment;
    RequestFragment requestFragment;
    HomeFragment1 historyFragment;
    private UserSession session;
    private HashMap<String, String> user;
    private String name, section, roomid, class_,uuid;
    FloatingActionButton fab_plus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class_home);
        FirebaseApp.initializeApp(MyClassHome.this);
        try {
            name=getIntent().getStringExtra("name");
            section=getIntent().getStringExtra("section");
            roomid=getIntent().getStringExtra("roomid");
            class_=getIntent().getStringExtra("class_");
            uuid=getIntent().getStringExtra("uuid");



        }catch (Exception e) {
            name=getIntent().getStringExtra("name");
            section=getIntent().getStringExtra("section");
            roomid=getIntent().getStringExtra("roomid");
            class_=getIntent().getStringExtra("class_");
            uuid=getIntent().getStringExtra("uuid");
        }
        //check Internet Connection
        //new CheckInternetConnection(this).checkConnection();

        session = new UserSession(getApplicationContext());
        new CheckInternetConnection(this).checkConnection();
        Toolbar toolbar = findViewById(R.id.toolbar);
        mAuth=FirebaseAuth.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        mainDrawer=findViewById(R.id.main_activity);
        mainNav = findViewById(R.id.main_nav);
        mainNav.setNavigationItemSelectedListener(this);
        frameLayout=findViewById(R.id.main_container);
        mainToggle = new ActionBarDrawerToggle(this,mainDrawer,toolbar,R.string.open,R.string.close);
        mainDrawer.addDrawerListener(mainToggle);
        mainToggle.setDrawerIndicatorEnabled(true);
        mainToggle.syncState();
        mainBottomNav = findViewById(R.id.mainBottomNav);
        //BottomNavigationViewHelper.disableShiftMode(mainBottomNav);

        chatFragment=new ChatFragment();
        requestFragment=new RequestFragment();
        historyFragment=new HomeFragment1();


        //




        //


        fab_plus=findViewById(R.id.fab_plus);
        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String list[]={"Create Assignment","Create Class Notification","Upload Video","Upload Pdf","Create Exam"};
                AlertDialog.Builder builder=new AlertDialog.Builder(MyClassHome.this);
                builder.setTitle("Create")
                        .setItems(list, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which==0) {
                                    Intent intent=new Intent(getApplicationContext(),Assignment_Create.class);
                                    intent.putExtra("name",name);
                                    intent.putExtra("section",section);
                                    intent.putExtra("roomid",roomid);
                                    intent.putExtra("class_",class_);
                                    intent.putExtra("uuid",uuid);
                                    startActivity(intent);
                                }
                                else if(which==1) {
                                    Intent intent=new Intent(getApplicationContext(),Create_Notifications.class);
                                    intent.putExtra("name",name);
                                    intent.putExtra("section",section);
                                    intent.putExtra("roomid",roomid);
                                    intent.putExtra("class_",class_);
                                    intent.putExtra("uuid",uuid);
                                    startActivity(intent);
                                }
                                //3
                                else if(which==2) {
                                    Intent intent=new Intent(getApplicationContext(),Add_Video.class);
                                    intent.putExtra("name",name);
                                    intent.putExtra("section",section);
                                    intent.putExtra("roomid",roomid);
                                    intent.putExtra("class_",class_);
                                    intent.putExtra("uuid",uuid);
                                    startActivity(intent);
                                }
                                //4
                                else if(which==3) {
                                    Intent intent=new Intent(getApplicationContext(),Add_newPdf.class);
                                    intent.putExtra("name",name);
                                    intent.putExtra("section",section);
                                    intent.putExtra("roomid",roomid);
                                    intent.putExtra("class_",class_);
                                    intent.putExtra("uuid",uuid);
                                    startActivity(intent);
                                }
                                //5
                                else if(which==4) {
                                    Intent intent=new Intent(getApplicationContext(),Create_Exam.class);
                                    intent.putExtra("name",name);
                                    intent.putExtra("section",section);
                                    intent.putExtra("roomid",roomid);
                                    intent.putExtra("class_",class_);
                                    intent.putExtra("uuid",uuid);
                                    startActivity(intent);
                                }
                            }
                        }).create();
                builder.show();
            }
        });


      ///
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        getList = new ArrayList<>();
        getDataAdapter1 = new AssignmentAdapter(getList,"as");
        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference =      firebaseFirestore.collection(roomid)

                .document();
        recyclerView = findViewById(R.id.blog_list_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyClassHome.this));
        recyclerView.setAdapter(getDataAdapter1);
        reciveData();

    }
    private void reciveData() {

        firebaseFirestore.collection(roomid)  .orderBy("time", Query.Direction.DESCENDING)


                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (DocumentChange ds : queryDocumentSnapshots.getDocumentChanges()) {
                            if (ds.getType() == DocumentChange.Type.ADDED) {

                 /*String first;
                 first = ds.getDocument().getString("name");
                 Toast.makeText(MainActivity2.this, "" + first, Toast.LENGTH_SHORT).show();*/
                                Classs_Model get = ds.getDocument().toObject(Classs_Model.class);
                                getList.add(get);
                                getDataAdapter1.notifyDataSetChanged();
                            }

                        }
                    }
                });

    }

    ////////
    LottieAnimationView empty_cart;
    DocumentReference documentReference;
    RecyclerView recyclerView;
    AssignmentAdapter getDataAdapter1;
    List<Classs_Model> getList;
    String url;
    private BottomNavigationView.OnNavigationItemSelectedListener selectlistner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.stream:
                            //name, section, roomid, class_
                            Bundle b = new Bundle();
                            b.putString("name", name);
                            b.putString("section", section);
                            b.putString("roomid", roomid);
                            b.putString("class_", class_);
                            b.putString("uuid", uuid);
                            ChatFragment fragment2 = new ChatFragment();
                            fragment2.setArguments(b);
                            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                            ft2.replace(R.id.content, fragment2, "");
                            ft2.commit();
                            break;



                    }
                    return false;
                }
            };
    private void replaceFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (fragment == chatFragment){
            Bundle b = new Bundle();

            b.putString("name", name);
            b.putString("section", section);
            b.putString("roomid", roomid);
            b.putString("class_", class_);
            b.putString("uuid", uuid);
            fragment.setArguments(b);
            fragmentTransaction.hide(requestFragment);
            fragmentTransaction.hide(historyFragment);
            // fragmentTransaction.hide(historyFragment);

        } else if (fragment == requestFragment){
            Bundle b = new Bundle();

            b.putString("name", name);
            b.putString("section", section);
            b.putString("roomid", roomid);
            b.putString("class_", class_);
            b.putString("uuid", uuid);
            fragment.setArguments(b);
            fragmentTransaction.hide(chatFragment);
            fragmentTransaction.hide(historyFragment);
            Toast.makeText(this, ""+b, Toast.LENGTH_SHORT).show();
            //   fragmentTransaction.hide(historyFragment);

        }
        else if (fragment == historyFragment){
            Bundle b = new Bundle();

            b.putString("name", name);
            b.putString("section", section);
            b.putString("roomid", roomid);
            b.putString("class_", class_);
            b.putString("uuid", uuid);
            fragment.setArguments(b);
            fragmentTransaction.hide(chatFragment);
            fragmentTransaction.hide(requestFragment);
            //   fragmentTransaction.hide(historyFragment);

        }



        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }
    public void initializeFragment(){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container,requestFragment);
        fragmentTransaction.add(R.id.main_container,chatFragment);
         fragmentTransaction.add(R.id.main_container,historyFragment);


        fragmentTransaction.hide(chatFragment);
        fragmentTransaction.hide(historyFragment);



        fragmentTransaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id) {
            case R.id.stream :
                Toasty.success(getApplicationContext(),"You are in stream page",Toasty.LENGTH_SHORT,true).show();

                break;
            case R.id.notification :
                Intent notification=new Intent(getApplicationContext(),MyNotificationList.class);
                notification.putExtra("name",name);
                notification.putExtra("section",section);
                notification.putExtra("roomid",roomid);
                notification.putExtra("class_",class_);
                notification.putExtra("uuid",uuid);
                startActivity(notification);

                break;

            case R.id.classrowk :
                Intent intent=new Intent(getApplicationContext(),Class_Work.class);
                intent.putExtra("name",name);
                intent.putExtra("section",section);
                intent.putExtra("roomid",roomid);
                intent.putExtra("class_",class_);
                intent.putExtra("uuid",uuid);
                startActivity(intent);

                break;
            case R.id.examination :
                Intent intentclas=new Intent(getApplicationContext(),MyExamList.class);
                intentclas.putExtra("name",name);
                intentclas.putExtra("section",section);
                intentclas.putExtra("roomid",roomid);
                intentclas.putExtra("class_",class_);
                intentclas.putExtra("uuid",uuid);
                startActivity(intentclas);

                break;
            case R.id.teaching :
                Intent intent1=new Intent(getApplicationContext(),PeopleList.class);
                intent1.putExtra("name",name);
                intent1.putExtra("section",section);
                intent1.putExtra("roomid",roomid);
                intent1.putExtra("class_",class_);
                intent1.putExtra("uuid",uuid);
                startActivity(intent1);

                break;
                //
            case R.id.videos :
                Intent videos=new Intent(getApplicationContext(),MyVideoLIst.class);
                videos.putExtra("name",name);
                videos.putExtra("section",section);
                videos.putExtra("roomid",roomid);
                videos.putExtra("class_",class_);
                videos.putExtra("uuid",uuid);
                startActivity(videos);

                break;
                //
            case R.id.pdf :
                Intent pdf=new Intent(getApplicationContext(),PdfList.class);
                pdf.putExtra("name",name);
                pdf.putExtra("section",section);
                pdf.putExtra("roomid",roomid);
                pdf.putExtra("class_",class_);
                pdf.putExtra("uuid",uuid);
                startActivity(pdf);

                break;
            //
            case R.id.startclass:
                try {


                    JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                            .setServerURL(new URL("https://meet.jit.si"))
                            .setWelcomePageEnabled(false)
                            .setRoom("https://meet.jit.si/"+roomid)
                            .setAudioMuted(false)
                            .setVideoMuted(false)
                            .setAudioOnly(false)
                            .setFeatureFlag("invite.enabled", false)
                            .setFeatureFlag("pip.enabled",false) // <- this line you have to add
                            .setFeatureFlag("calendar.enabled",false)  // optional
                            .setFeatureFlag("call-integration.enabled",false)  // optional
                            .setFeatureFlag("pip.enabled",false)
                            .setFeatureFlag("calendar.enabled",false)
                            .setFeatureFlag("call-integration.enabled",false)
                            .setFeatureFlag("close-captions.enabled",false)
                            .setFeatureFlag("chat.enabled",false)
                            .setFeatureFlag("invite.enabled",false)
                            .setFeatureFlag("live-streaming.enabled",false)
                            .setFeatureFlag("meeting-name.enabled",false)
                            .setFeatureFlag("raise-hand.enabled",false)
                            .setFeatureFlag("video-share.enabled",false)
                            .setWelcomePageEnabled(false)
                            .build();

                    JitsiMeetActivity.launch(MyClassHome.this, options);


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.attend:
                Intent intent11=new Intent(getApplicationContext(),AttendenceList.class);
                intent11.putExtra("name",name);
                intent11.putExtra("section",section);
                intent11.putExtra("roomid",roomid);
                intent11.putExtra("class_",class_);
                intent11.putExtra("uuid",uuid);
                startActivity(intent11);

                break;

        }

        return false;
    }
    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(getApplicationContext(), My_Teaching_List.class));

        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), My_Teaching_List.class));
    }
}
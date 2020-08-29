package com.dev.hms;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class home extends AppCompatActivity {
    private fragmentfavoire fragmentfavoire;
    private fragmentmesreservation fragmentmesreservation;
    private fragmentprofile fragmentprofile;
    private fragmentacceuil fragmentacceuil;
    MeowBottomNavigation meowBottomNavigation;
    private final static int ID_favoire=2;
    private final static int ID_acceuil=1;
    private final static int ID_mesreservation=3;
    private final static int ID_profile=4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        meowBottomNavigation =findViewById(R.id.meo);
        meowBottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_hotel_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_baseline_favorite_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_transfer_within_a_station_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.ic_baseline_emoji_people_24));

        getSupportFragmentManager().beginTransaction().replace(R.id.fralelayout,new fragmentacceuil()).commit();


        meowBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
            }

        });

        meowBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment select=null;
                switch (item.getId()){
                    case ID_acceuil:
                        select=new fragmentacceuil();
                        break;
                    case ID_profile:
                        select=new fragmentprofile();
                        break;
                    case ID_mesreservation:
                        select=new fragmentmesreservation();
                        break;
                    case ID_favoire:
                        select=new fragmentfavoire();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fralelayout,select).commit();


            }
        });





    }
}
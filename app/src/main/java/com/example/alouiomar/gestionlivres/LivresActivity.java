package com.example.alouiomar.gestionlivres;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class LivresActivity extends AppCompatActivity implements TabHost.OnTabChangeListener {
    TabHost tabHost ;
    ListView lv_affich;
    AccessDB lBDD;
    Button b_ajouter;
    Button b_supp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livres);

        b_supp=(Button) findViewById(R.id.b_supp);
        b_supp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lBDD.suppTT();
            }
        });

        tabHost = (TabHost) findViewById(R.id.tab);
        tabHost.setup();
        TabHost.TabSpec s1 = tabHost.newTabSpec("onglet_insertion");
// Paramétrer le texte qui s'affichera dans l'onglet
// ainsi que l'image qui se positionnera
// au-dessus du texte.
        s1.setIndicator("Insertion", getResources().getDrawable(android.R.drawable.ic_input_add));
// Spécifier le Layout qui s'affichera lorsque l'onglet sera sélectionné
        s1.setContent(R.id.insertion);
// Ajouter l'onglet dans notre TabHost
        tabHost.addTab(s1);
// On peut ajouter les onglets comme ceci :
        tabHost.addTab(tabHost.newTabSpec("tab_affiche")
                .setIndicator("Affichage").setContent(R.id.affichage));
        tabHost.addTab(tabHost.newTabSpec("tab_recherche")
                .setIndicator("Recherche").setContent(R.id.recherche));
        tabHost.setOnTabChangedListener(this);
/****************************************************/
        lBDD = new AccessDB(this); lBDD.open();
// Récupération et ajout des écouteurs
        lv_affich = (ListView) findViewById(R.id.lv_affich);
        lv_affich.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                             @Override
                                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                             }
                                         }
        );
        //  TextView recherche_text = (TextView) findViewById(R.id.ed_rech);
        // recherche_text.addTextChangedListener(this);

    }
    @Override
    protected void onDestroy() { lBDD.close();
        super.onDestroy();
    }

    public void onClick(View source) {


        EditText titre = (EditText) findViewById(R.id.titre);
        EditText isbn = (EditText) findViewById(R.id.isbn);
        Toast.makeText(getBaseContext(), "Livre ajouté avec succés !",Toast.LENGTH_LONG).show();
        lBDD.ajouter(isbn.getText().toString(),titre.getText().toString());


    }




    public void onTabChanged(String param) {
        if
                (param.equals("tab_affiche")) {
            ArrayList<Livre> livr = new ArrayList<>();
            livr = lBDD.getall();
            String []  li = new String[livr.size()];

            for (int i = 0; i <livr.size() ; i++) {
                li[i] = livr.get(i).toString();
            }

            //ListView listView = (ListView) findViewById(R.id.lv_affich);
            ListAdapter listAdapter = new ArrayAdapter<String>(this , R.layout.support_simple_spinner_dropdown_item,li);
            lv_affich.setAdapter(listAdapter);

            final ArrayList<Livre> finalLivr = livr;
            lv_affich.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Livre lv=null;
               lv= finalLivr.get(position);
                lBDD.supp(lv);
                Toast.makeText(getBaseContext(), "Livre supprimé avec succés !",Toast.LENGTH_LONG).show();
                ArrayList<Livre> livr = new ArrayList<>();
                livr = lBDD.getall();
                String []  li = new String[livr.size()];

                for (int i = 0; i <livr.size() ; i++) {
                    li[i] = livr.get(i).toString();
                }

                //ListView listView = (ListView) findViewById(R.id.lv_affich);
                ListAdapter listAdapter = new ArrayAdapter<String>(getBaseContext() , R.layout.support_simple_spinner_dropdown_item,li);
                lv_affich.setAdapter(listAdapter);

    }
});
        } else if (param.equals("tab_recherche")) {

            EditText editText = (EditText) findViewById(R.id.search);
            ListView listView = (ListView) findViewById(R.id.list);
            ArrayList<Livre> livr = new ArrayList<>();
            livr = lBDD.getall();
            String []  li = new String[livr.size()];

            for (int i = 0; i <livr.size() ; i++) {
                li[i] = livr.get(i).toString();
            }
            ListAdapter listAdapter = new ArrayAdapter<String>(this , R.layout.support_simple_spinner_dropdown_item,li);

            listView.setAdapter(listAdapter);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }


                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if(s.toString().equals(""))
                    {
                        ListView listView = (ListView) findViewById(R.id.list);

                        ArrayList<Livre> livr = new ArrayList<>();
                        livr = lBDD.getall();
                        String []  li = new String[livr.size()];

                        for (int i = 0; i <livr.size() ; i++) {
                            li[i] = livr.get(i).toString();
                        }

                        ArrayList<String> listitem = new ArrayList<>(Arrays.asList(li));
                        ListAdapter listAdapter = new ArrayAdapter<String>(getBaseContext() , R.layout.support_simple_spinner_dropdown_item,li);

                        listView.setAdapter(listAdapter);


                    }
                    else
                    {
                        ListView listView = (ListView) findViewById(R.id.list);

                        ArrayList<Livre> livr = new ArrayList<>();
                        livr = lBDD.getall();
                        String []  li = new String[livr.size()];

                        for (int i = 0; i <livr.size() ; i++) {
                            li[i] = livr.get(i).toString();
                        }
                        ArrayList<String> listitem = new ArrayList<>(Arrays.asList(li));


                        for(String l:li){
                            if(!l.contains(s.toString()))
                            {
                                listitem.remove(l) ;
                            }
                        }
                        String [] m = new String[listitem.size()];
                        for (int i = 0; i <listitem.size() ; i++) {
                            m[i] = listitem.get(i);
                        }
                        ListAdapter listAdapter = new ArrayAdapter<String>(getBaseContext() , R.layout.support_simple_spinner_dropdown_item,m);

                        listView.setAdapter(listAdapter);

                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });



        }

    }





}
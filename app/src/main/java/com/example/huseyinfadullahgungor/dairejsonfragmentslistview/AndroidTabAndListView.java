package com.example.huseyinfadullahgungor.dairejsonfragmentslistview;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AndroidTabAndListView extends TabActivity {
    // TabSpec Names
    private static final String SAYAC_SPEC = "Sayaçlar Bilgi";
    private static final String DAİRE_FATURA_SPEC = "DAİRE FATURA";
    private static final String BİNA_FATURA_SPEC = "BİNA          FATURA";
    private static final String BİNA_LİSTE_SPEC = "BİNA  LİSTESİ";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        TabHost tabHost = getTabHost();

        // Inbox Tab
        TabSpec sayacSpec = tabHost.newTabSpec(SAYAC_SPEC);

        // Tab Icon
        sayacSpec.setIndicator(SAYAC_SPEC, getResources().getDrawable(R.drawable.icon_inbox));

        Intent inboxIntent = new Intent(this, Sayacs.class);
        // Tab Content
        sayacSpec.setContent(inboxIntent);



        // Profile Tab
        TabSpec daireFaturaSpec = tabHost.newTabSpec(DAİRE_FATURA_SPEC);
        daireFaturaSpec.setIndicator(DAİRE_FATURA_SPEC, getResources().getDrawable(R.drawable.icon_profile));
        Intent daireFaturaIntent = new Intent(this, DaireAylikFatura.class);
        daireFaturaSpec.setContent(daireFaturaIntent);


        TabSpec binaFaturaSpec = tabHost.newTabSpec(BİNA_FATURA_SPEC);
        binaFaturaSpec.setIndicator(BİNA_FATURA_SPEC, getResources().getDrawable(R.drawable.icon_profile));
        Intent binaFaturaIntent = new Intent(this, BinaAylikFatura.class);
        binaFaturaSpec.setContent(binaFaturaIntent);

        // Outbox Tab
        TabSpec binaListeSpec = tabHost.newTabSpec(BİNA_LİSTE_SPEC);
        binaListeSpec.setIndicator(BİNA_LİSTE_SPEC, getResources().getDrawable(R.drawable.icon_outbox));
        Intent binaListeIntent = new Intent(this, BinaDaireListesi.class);
        binaListeSpec.setContent(binaListeIntent);

        // Adding all TabSpec to TabHost
        tabHost.addTab(sayacSpec); // Adding Sayac tab
        tabHost.addTab(daireFaturaSpec); // Adding Daire Fatura tab
        tabHost.addTab(binaFaturaSpec); // Adding Bina Fatura tab
        tabHost.addTab(binaListeSpec); // Adding Bina Liste tab


    }



}


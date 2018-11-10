package com.example.huseyinfadullahgungor.dairejsonfragmentslistview;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by huseyinfadullahgungor on 9.07.2018.
 */

public class DigerSayaclar extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jsonParser = new JSONParser();

    ArrayList<HashMap<String, String>> outboxList;

    // products JSONArray
    JSONArray outbox = null;


    String fado;
    ArrayList<HashMap<String, String>> contactList;
    private String TAG = AndroidTabAndListView.class.getSimpleName();
    private ListView lv;
    TextView digerSayaclarUsttekiIsim;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.digersayaclar_list);

        contactList = new ArrayList<>();

        // Hashmap for ListView
        outboxList = new ArrayList<HashMap<String, String>>();

        digerSayaclarUsttekiIsim=findViewById(R.id.digersayaclar);
        Intent intent = getIntent();
        String easyPuzzle = intent.getExtras().getString("dialogIsim");
        digerSayaclarUsttekiIsim.setText(easyPuzzle);

        // Loading OUTBOX in Background Thread
        new DigerSayaclar.LoadOutbox().execute();
    }

    /**
     * Background Async Task to Load all OUTBOX messages by making HTTP Request
     * */
    class LoadOutbox extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DigerSayaclar.this);
            pDialog.setMessage("Sayaç Bilgileri Yükleniyor ...");
            pDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_button));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting Outbox JSON
         * */
        protected String doInBackground(String... args) {


            try {
                URL theURL = new URL("https://www.faturapaylasim.net/api/dairelogin?userName=114305&password=123456");
                HttpURLConnection myConnection= (HttpURLConnection) theURL.openConnection();
                myConnection.setRequestProperty("Authorization","Basic " +myConnection);
                if (myConnection.getResponseCode() == 200){
                    URL bina = new URL("https://www.faturapaylasim.net/api/apidairesayaclistesi");
                    HttpURLConnection binaConnection= (HttpURLConnection) bina.openConnection();
                    binaConnection.setRequestProperty("Authorization" , "Basic CgOs_4WJ9_6_FtikeDzTBvgb5g-Jj8LmzqPktNAN4mtYerYcdOFtbHVyvJO58ScbAAe7nDx4Y9DL2LPMA75aqNeBBitiaR0lIKQI0-qDwnH56XJCEEZNVtNV7baTceGaLjMuLc4XEjlAlDVc9yvd2N_SGSeScQ7z_aIGJLeg_AI|" );
                    if (binaConnection.getResponseCode() == 200){
                        Log.d("BAŞARILI","GENEL TOKEN DOĞRU, BİNA TOKENİ DOĞRU");

                        BufferedReader reader=new BufferedReader(new InputStreamReader(binaConnection.getInputStream()));
                        String jsonString= reader.readLine();
                        Log.d("OLDU",jsonString);
                        fado=jsonString;




                    }
                    else {
                        Log.d("BAŞARISIZ","GENEL TOKEN DOĞRU, BİNA TOKENI YANLIŞ");
                    }

                }else{
                    Log.d("BAŞARISIZ","GENEL TOKEN YANLIŞ");
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }



            if (fado != null) {
                try {
                    //   JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    //     JSONArray contacts = jsonObj.getJSONArray("contacts");

                    JSONArray array = new JSONArray(fado);


                    // looping through All Contacts
                    for (int i = BinaDaireListesi.itemPozisyonu; i < BinaDaireListesi.itemPozisyonu+1; i++) {
                        JSONObject c = array.getJSONObject(i);
                        JSONArray sayaclar = c.getJSONArray("Sayacs");

                        for (int j=0; j<sayaclar.length(); j++){
                            JSONObject d=sayaclar.getJSONObject(j);

                            String sayacTipi = d.getString("Aciklama");
                            String sayacNumarasi = d.getString("SayacNumarasi");
                            String ılkOkumaTarihi = d.getString("IlkOkumaTarihi");
                            String sonOkumaTarihi = d.getString("SonOkumaTarihi");
                            String ılkEndeks = d.getString("IlkEndeks");
                            String sonEndeks = d.getString("SonEndeks");
                            String tuketim = d.getString("Tuketim");


                            HashMap<String, String> contact = new HashMap<>();

                            // adding each child node to HashMap key => value
                            contact.put("Aciklama", "Sayaç Tipi: " +sayacTipi);
                            contact.put("SayacNumarasi", "Sayaç Numarasi: " +sayacNumarasi);
                            contact.put("IlkOkumaTarihi", "İlk Okuma Tarihi: " +ılkOkumaTarihi.substring(0,10));
                            contact.put("SonOkumaTarihi", "Son Okuma Tarihi: " +sonOkumaTarihi.substring(0,10));
                            contact.put("IlkEndeks", "İlk Endeks: " +ılkEndeks);
                            contact.put("SonEndeks", "Son Endeks: " +sonEndeks);
                            contact.put("Tuketim", "Tüketim: " +tuketim);

                            contactList.add(contact);

                        }



                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }




            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            DigerSayaclar.this, contactList,
                            R.layout.digersayaclar_list_item, new String[] { "Aciklama","SayacNumarasi","IlkOkumaTarihi","SonOkumaTarihi","IlkEndeks","SonEndeks","Tuketim" },
                            new int[] { R.id.digerSayacTipi, R.id.digerSayacNumarasi, R.id.digerIlkOkumaTarihi,  R.id.digerSonOkumaTarihi, R.id.digerIlkEndeks, R.id.digerSonEndeks,  R.id.digerTuketim });
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }

}

package com.example.huseyinfadullahgungor.dairejsonfragmentslistview;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.style.UpdateAppearance;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
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
 * Created by huseyinfadullahgungor on 21.06.2018.
 */

public class DaireAylikFatura extends ListActivity {
    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jsonParser = new JSONParser();

    ArrayList<HashMap<String, String>> inboxList;

    // products JSONArray
    JSONArray inbox = null;

    // Inbox JSON url
    private static final String INBOX_URL = "https://api.androidhive.info/mail/inbox.json";

    // ALL JSON node names
    private static final String TAG_MESSAGES = "messages";
    private static final String TAG_ID = "id";
    private static final String TAG_FROM = "from";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_SUBJECT = "subject";
    private static final String TAG_DATE = "date";

    String fado;
    ArrayList<HashMap<String, String>> contactList;
    private String TAG = AndroidTabAndListView.class.getSimpleName();
    private ListView lv;
    ProgressBar progressBar;
    String asama;
    SeekBar seekBar;
    Button çizgigrafikbuton;
    public static String [] faturaTutarıDaire=new String [13];
    public static String [] aylarDaire=new String[13];







    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daire_fatura_list);

        contactList = new ArrayList<>();
        //lv = (ListView) findViewById(R.id.list);

        // Hashmap for ListView
        inboxList = new ArrayList<HashMap<String, String>>();

       // progressBar= findViewById(R.id.Progress);
        // seekBar=findViewById(R.id.seekValue);

        // Loading INBOX in Background Thread

        new DaireAylikFatura.LoadInbox().execute();

        çizgigrafikbuton=findViewById(R.id.çizgigrafikbutonid);





        initçizgi();
    }


    public void initçizgi() {
        çizgigrafikbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gec2 = new Intent(DaireAylikFatura.this, GrafikDaire.class);
                startActivity(gec2);
            }
        });
    }

    /**
     * Background Async Task to Load all INBOX messages by making HTTP Request
     * */
    class LoadInbox extends AsyncTask<String, String, String> {



        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DaireAylikFatura.this);
            pDialog.setMessage("Daire Aylık Faturaları Yükleniyor ...");
            pDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_button));
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting Inbox JSON
         * */
        protected String doInBackground(String... args) {


            try {
                URL theURL = new URL("https://www.faturapaylasim.net/api/dairelogin?userName=114305&password=123456");
                HttpURLConnection myConnection= (HttpURLConnection) theURL.openConnection();
                myConnection.setRequestProperty("Authorization","Basic CgOs_4WJ9_6_FtikeDzTBvgb5g-Jj8LmzqPktNAN4mtYerYcdOFtbHVyvJO58ScbAAe7nDx4Y9DL2LPMA75aqNeBBitiaR0lIKQI0-qDwnH56XJCEEZNVtNV7baTceGaLjMuLc4XEjlAlDVc9yvd2N_SGSeScQ7z_aIGJLeg_AI|");
                if (myConnection.getResponseCode() == 200){
                    URL bina = new URL("https://www.faturapaylasim.net/api/apidairefaturalari");
                    HttpURLConnection binaConnection= (HttpURLConnection) bina.openConnection();
                    binaConnection.setRequestProperty("Authorization" , "Basic CgOs_4WJ9_6_FtikeDzTBvgb5g-Jj8LmzqPktNAN4mtYerYcdOFtbHVyvJO58ScbAAe7nDx4Y9DL2LPMA75aqNeBBitiaR0lIKQI0-qDwnH56XJCEEZNVtNV7baTceGaLjMuLc4XEjlAlDVc9yvd2N_SGSeScQ7z_aIGJLeg_AI|");
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
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject c = array.getJSONObject(i);
                        String faturaTutari = c.getString("FaturaTutari");
                        String sonOdemeTarihi1 = c.getString("SonOdemeTarihi");
                        String paylasimDOnemAdi = c.getString("PaylasimDOnemAdi");
                        String toplamIsinmaBedeli = c.getString("ToplamIsinmaBedeli");
                        double progressim= (double) (Double.valueOf(faturaTutari) * 100 / Double.valueOf(toplamIsinmaBedeli)  );
                        asama= String.valueOf((double) progressim);

                        faturaTutarıDaire[i]= faturaTutari;
                        aylarDaire[i]=paylasimDOnemAdi;

                        // String address = c.getString("address");
                        // String gender = c.getString("gender");

                        // Phone node is JSON Object
                        //  JSONObject phone = c.getJSONObject("phone");
                        // String mobile = phone.getString("mobile");
                        // String home = phone.getString("home");
                        //  String office = phone.getString("office");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("FaturaTutari", "Fatura Turarı: " +faturaTutari.substring(0,6));
                        contact.put("SonOdemeTarihi", "Son Ödeme Tarihi: " +sonOdemeTarihi1.substring(0,10));
                        contact.put("PaylasimDOnemAdi", paylasimDOnemAdi);
                        contact.put("ToplamIsinmaBedeli", "Toplam Isinma Bedeli: " +toplamIsinmaBedeli.substring(0,6));
                        contact.put("Progress" , "Bina tüketiminin % "  +asama.substring(0,4) +" sizin tarafınızdan yapılmıştır");

                        // contact.put("mobile", mobile);

                        // adding contact to contact list
                        contactList.add(contact);
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
                            DaireAylikFatura.this, contactList,
                            R.layout.daire_fatura_list_item, new String[] { "PaylasimDOnemAdi","FaturaTutari","SonOdemeTarihi","ToplamIsinmaBedeli", "Progress"  },
                            new int[] { R.id.PaylasimDOnemAdi, R.id.FaturaTutari, R.id.SonOdemeTarihi, R.id.ToplamIsinmaBedeli, R.id.yuzde});
                    // updating listview

                    setListAdapter(adapter);

                }
            });



        }

    }




}

package com.example.huseyinfadullahgungor.dairejsonfragmentslistview;

/**
 * Created by huseyinfadullahgungor on 9.06.2018.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.github.mikephil.charting.data.BarEntry;

public class BinaAylikFatura extends ListActivity {
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
    Button grafikbuton;



    public static String [] faturaTutarı=new String [13];
    public static String [] aylar=new String[13];





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bina_fatura_list);

        contactList = new ArrayList<>();
        //lv = (ListView) findViewById(R.id.list);

        // Hashmap for ListView
        inboxList = new ArrayList<HashMap<String, String>>();

        // Loading INBOX in Background Thread
        new LoadInbox().execute();


        grafikbuton=findViewById(R.id.grafikbutonid);

        init();






    }
    public void init() {
        grafikbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gec = new Intent(BinaAylikFatura.this, GrafikBina.class);
                startActivity(gec);
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
            pDialog = new ProgressDialog(BinaAylikFatura.this);
            pDialog.setMessage("Bina Aylık Faturaları Yükleniyor ...");
            pDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_button));
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting Inbox JSON
         * */
        protected String doInBackground(String... args) {
//            // Building Parameters
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//
//            // getting JSON string from URL
//            JSONObject json = jsonParser.makeHttpRequest(INBOX_URL, "GET",
//                    params);
//
//            // Check your log cat for JSON reponse
//            Log.d("Inbox JSON: ", json.toString());
//
//            try {
//                inbox = json.getJSONArray(TAG_MESSAGES);
//                // looping through All messages
//                for (int i = 0; i < inbox.length(); i++) {
//                    JSONObject c = inbox.getJSONObject(i);
//
//                    // Storing each json item in variable
//                    String id = c.getString(TAG_ID);
//                    String from = c.getString(TAG_FROM);
//                    String subject = c.getString(TAG_SUBJECT);
//                    String date = c.getString(TAG_DATE);
//
//                    // creating new HashMap
//                    HashMap<String, String> map = new HashMap<String, String>();
//
//                    // adding each child node to HashMap key => value
//                    map.put(TAG_ID, id);
//                    map.put(TAG_FROM, from);
//                    map.put(TAG_SUBJECT, subject);
//                    map.put(TAG_DATE, date);
//
//                    // adding HashList to ArrayList
//                    inboxList.add(map);
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }








            try {
                URL theURL = new URL("https://www.faturapaylasim.net/api/dairelogin?userName=114305&password=123456");
                HttpURLConnection myConnection= (HttpURLConnection) theURL.openConnection();
                myConnection.setRequestProperty("Authorization","Basic CgOs_4WJ9_6_FtikeDzTBvgb5g-Jj8LmzqPktNAN4mtYerYcdOFtbHVyvJO58ScbAAe7nDx4Y9DL2LPMA75aqNeBBitiaR0lIKQI0-qDwnH56XJCEEZNVtNV7baTceGaLjMuLc4XEjlAlDVc9yvd2N_SGSeScQ7z_aIGJLeg_AI|");
                if (myConnection.getResponseCode() == 200){
                    URL bina = new URL("https://www.faturapaylasim.net/api/apibinapaylasimdonemleri");
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
                        String paylasimDonemAdi = c.getString("PaylasimDonemAdi");
                        String faturaTarihi = c.getString("FaturaTarihi");
                        String sonOdemeTarihi = c.getString("SonOdemeTarihi");
                        String toplamIsinmaBedeli = c.getString("ToplamIsinmaBedeli");


                        faturaTutarı[i]= toplamIsinmaBedeli;
                        aylar[i]=paylasimDonemAdi;




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
                        contact.put("PaylasimDonemAdi", paylasimDonemAdi);
                        contact.put("FaturaTarihi", "Fatura Tarihi: " +faturaTarihi.substring(0,10));
                        contact.put("SonOdemeTarihi", "Son Ödeme Tarihi: " +sonOdemeTarihi.substring(0,10));
                        contact.put("ToplamIsinmaBedeli", "Toplam Isinma Bedeli: " +toplamIsinmaBedeli.substring(0,6));

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
                            BinaAylikFatura.this, contactList,
                            R.layout.bina_fatura_list_item, new String[] { "PaylasimDonemAdi","FaturaTarihi","ToplamIsinmaBedeli","SonOdemeTarihi" },
                            new int[] { R.id.PaylasimDonemAdi, R.id.FaturaTarihi, R.id.ToplamIsinmaBedeli, R.id.SonOdemeTarihi });
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }


}

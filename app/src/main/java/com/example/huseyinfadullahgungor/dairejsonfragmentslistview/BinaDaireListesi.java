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

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;


public class BinaDaireListesi extends ListActivity {
    // Progress Dialog
    //ProgressDialog
    private AlertDialog pDialog;

    // Creating JSON Parser object
    JSONParser jsonParser = new JSONParser();

    ArrayList<HashMap<String, String>> outboxList;

    // products JSONArray
    JSONArray outbox = null;

    // Outbox JSON url
    private static final String OUTBOX_URL = "https://api.androidhive.info/mail/outbox.json";

    // ALL JSON node names
    private static final String TAG_MESSAGES = "messages";
    private static final String TAG_ID = "id";
    private static final String TAG_TO = "to";
    private static final String TAG_SUBJECT = "subject";
    private static final String TAG_DATE = "date";


    String fado;
    ArrayList<HashMap<String, String>> contactList;
    private String TAG = AndroidTabAndListView.class.getSimpleName();
    public ListView lv;
    public static int itemPozisyonu;
    public  String isimDialog;
    public String adlarim []=new String[42];
    public static String aboneNolarim[]=new String[42];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bina_daireleri_list);





        contactList = new ArrayList<>();

        // Hashmap for ListView
        outboxList = new ArrayList<HashMap<String, String>>();


        // Loading OUTBOX in Background Thread
        new LoadOutbox().execute();




    }


    protected void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
         final int itemPosition     = position;
        itemPozisyonu=itemPosition;
        //String  itemValue    = (String) l.getItemAtPosition(position);
        //content.setText("Click : \n  Position :"+itemPosition+"  \n  ListItem : " +itemValue);
        Log.d("SIRASI", String.valueOf(itemPosition));
      //  Intent intent =new Intent(getApplicationContext(),DialogActivity.class);
       // startActivity(intent);

        AlertDialog.Builder mBuilder= new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        mView.setBackgroundResource(R.drawable.layout_bg);

        TextView text1=(TextView) mView.findViewById(R.id.dialogtext_id1);
        Button buton1= (Button) mView.findViewById(R.id.dialogbuton_id1);
        Button buton2= (Button) mView.findViewById(R.id.dialogbuton_id2);

        text1.setText(adlarim[itemPosition]);

        mBuilder.setView(mView);
        AlertDialog dialog=mBuilder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        buton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),DigerSayaclar.class);
                intent.putExtra("dialogIsim",adlarim[itemPosition]);
                startActivity(intent);
            }
        });

        buton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DigerFaturalar.class);
                intent.putExtra("dialogIsim",adlarim[itemPosition]);
                startActivity(intent);
            }
        });




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

            pDialog = new ProgressDialog(BinaDaireListesi.this);
            pDialog.setMessage("Sayaç Bilgileri Yükleniyor ...");
            pDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_button));
           // pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();


        }

        /**
         * getting Outbox JSON
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//
//            // getting JSON string from URL
//            JSONObject json = jsonParser.makeHttpRequest(OUTBOX_URL, "GET",
//                    params);
//
//            // Check your log cat for JSON reponse
//            Log.d("Outbox JSON: ", json.toString());
//
//            try {
//                outbox = json.getJSONArray(TAG_MESSAGES);
//                // looping through All messages
//                for (int i = 0; i < outbox.length(); i++) {
//                    JSONObject c = outbox.getJSONObject(i);
//
//                    // Storing each json item in variable
//                    String id = c.getString(TAG_ID);
//                    String to = c.getString(TAG_TO);
//                    String subject = c.getString(TAG_SUBJECT);
//                    String date = c.getString(TAG_DATE);
//
//                    // subject taking only first 23 chars
//                    // to fit into screen
//                    if(subject.length() > 23){
//                        subject = subject.substring(0, 22) + "..";
//                    }
//
//                    // creating new HashMap
//                    HashMap<String, String> map = new HashMap<String, String>();
//
//                    // adding each child node to HashMap key => value
//                    map.put(TAG_ID, id);
//                    map.put(TAG_TO, to);
//                    map.put(TAG_SUBJECT, subject);
//                    map.put(TAG_DATE, date);
//
//                    // adding HashList to ArrayList
//                    outboxList.add(map);
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
                    URL bina = new URL("https://www.faturapaylasim.net/api/apidairelistesi");
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
                        String daireNo = c.getString("DaireNo");
                        String adSoyad = c.getString("AdSoyad");
                        String aboneNo = c.getString("AboneNo");



                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("DaireNo","Daire No: " +daireNo);
                        contact.put("AdSoyad", adSoyad);
                        adlarim[i]=adSoyad;
                        contact.put("AboneNo", "Abone No: " +aboneNo);
                        aboneNolarim[i]=aboneNo;
                      //  contact.put("SonOdemeTarihi", "Son Ödeme Tarihi: " +sonOdemeTarihi.substring(0,10));

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
                    ListAdapter adapter1 = new SimpleAdapter(
                            BinaDaireListesi.this, contactList,
                            R.layout.bina_daireleri_list_item, new String[] { "AdSoyad","DaireNo","AboneNo" },
                            new int[] { R.id.AdSoyad, R.id.DaireNo, R.id.AboneNo });
                    // updating listview
                    setListAdapter(adapter1);







                }
            });






//             lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    Intent intent =new Intent(getApplicationContext(),DialogActivity.class);
//                    startActivity(intent);
//                }
//            });




        }



    }



}
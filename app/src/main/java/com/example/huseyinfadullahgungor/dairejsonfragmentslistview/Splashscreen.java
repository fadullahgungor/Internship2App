package com.example.huseyinfadullahgungor.dairejsonfragmentslistview;

/**
 * Created by huseyinfadullahgungor on 10.07.2018.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import com.facebook.shimmer.ShimmerFrameLayout;



public class Splashscreen extends Activity {

    Thread splashTread;
    ImageView imageView;
    TextView textdegis;
    int count=0;
    String faturaP[]=new String[]{"ADİL PAYLAŞIM","HIZLI","KOLAY","ADİL PAYLAŞIM","HIZLI","KOLAY","ADİL PAYLAŞIM","HIZLI","KOLAY","ADİL PAYLAŞIM","HIZLI","KOLAY","ADİL PAYLAŞIM","HIZLI","KOLAY","ADİL PAYLAŞIM"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        textdegis=findViewById(R.id.text_degis);

        Thread t=new Thread(){
            @Override
            public void run() {
                while (count<10){
                    try {
                        Thread.sleep(1000);


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                count++;
                                textdegis.setText(String.valueOf(faturaP[count]));
                                textdegis.setAnimation(AnimationUtils.loadAnimation(Splashscreen.this, android.R.anim.fade_in));





                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();






        imageView = (ImageView)findViewById(R.id.splash);
        this.imageView.setImageDrawable(getResources().getDrawable(R.drawable.fatura_paylasim));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        ShimmerFrameLayout container =
                (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        container.startShimmer();

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(Splashscreen.this,
                            AndroidTabAndListView.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    Splashscreen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    Splashscreen.this.finish();
                }

            }
        };
        splashTread.start();




    }

}
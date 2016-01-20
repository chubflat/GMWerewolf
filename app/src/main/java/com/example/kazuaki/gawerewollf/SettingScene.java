package com.example.kazuaki.gawerewollf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import static android.widget.LinearLayout.*;

/**
 * Created by Kazuaki on 2015/12/06.
 */
public class SettingScene extends Activity {
    public static void main(String[] args){}

    public static String scene;


    @Override
    public void onCreate(Bundle savedInstanceState){
        scene = "setting_scene";
        super.onCreate(savedInstanceState);
        FrameLayout layout = new FrameLayout(this);
        setContentView(layout);

        //custom add
        final CustomView customView = new CustomView(this);
        layout.addView(customView);

        Button button = new Button(this);
        button.setText("スタート");

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(customView.dp_width*8/10,customView.dp_height/10);
        lp.gravity = Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL;
        lp.bottomMargin = customView.dp_height*3/100;
        button.setLayoutParams(lp);
        button.setBackgroundResource(R.drawable.button);
        button.setTextSize(30);
        button.setBottom(3);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                scene = "game_scene";
                Intent intent = new Intent(SettingScene.this,GameScene.class);
                startActivity(intent);

            }
        });
        layout.addView(button);

    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event){
//        int actionId = event.getAction();
//
//        switch (actionId){
//            case MotionEvent.ACTION_DOWN:
//                if(scene.equals("game_scene")){
//                    Intent intent = new Intent(SettingScene.this,GameScene.class);
//                    startActivity(intent);
//                    Log.d("test", "test++=");
//
//                }else{
//
//                }
//                break;
//            default:
//                break;
//        }
//        return super.onTouchEvent(event);
//
//    }
}

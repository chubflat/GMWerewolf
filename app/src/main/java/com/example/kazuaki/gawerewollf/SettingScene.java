package com.example.kazuaki.gawerewollf;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import static android.widget.LinearLayout.*;

/**
 * Created by Kazuaki on 2015/12/06.
 */
public class SettingScene extends Activity {
    public static void main(String[] args){}

    public static boolean isSettingScene;
    public static boolean isGameScene;
    public static String settingPhase;
    public static boolean onDialog = false;
    public static String dialogPattern;
    public static CustomView customView;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {    //戻るボタンの反応なくす
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch (event.getKeyCode()){
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        initBackground();
        super.onCreate(savedInstanceState);
        FrameLayout layout = new FrameLayout(this);
        setContentView(layout);

        //custom add
        customView = new CustomView(this);
        layout.addView(customView);

    }

   @Override
   public boolean onTouchEvent(MotionEvent event){
       int actionId = event.getAction();

       switch (actionId){
           case MotionEvent.ACTION_DOWN:
               if(isGameScene){
                   Intent intent = new Intent(SettingScene.this,GameScene.class);
                   startActivity(intent);

               }else{

               }
               break;
           default:
               break;
       }

       String dialogText = "dialogText";

               if(event.getAction() == MotionEvent.ACTION_DOWN && onDialog == true ){
                   AlertDialog.Builder builder = new AlertDialog.Builder(this);

                   if(dialogPattern.equals("addPlayer")){
                       final EditText addPlayerView = new EditText(SettingScene.this);
                        builder.setTitle("追加するプレイヤー名を記入してください")
                               //setViewにてビューを設定
                               .setView(addPlayerView)
                               .setPositiveButton("追加", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {
                                       Toast.makeText(SettingScene.this,addPlayerView.getText().toString(),Toast.LENGTH_LONG).show();
                                   }
                               })
                               .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {

                                   }
                               })
                               .show();

                       dialogPattern = "";

                   }else{

                                   switch (dialogPattern){
                                       case "start":
                                           dialogText = "ゲームを開始します";
                                           break;
                                       default:
                                           break;
                                   }
                                   builder.setMessage(dialogText)
                                           .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                               public void onClick(DialogInterface dialog, int id) {
                       // ボタンをクリックしたときの動作
                                                   // dialog 表示しない
                                                   onDialog = false;
                                                   SettingScene.isSettingScene = false;
                                                   SettingScene.isGameScene = true;
                                                   // Activity遷移
                                                   Intent intent = new Intent(SettingScene.this,GameScene.class);
                                                   startActivity(intent);
                                                   customView.invalidate();

                                               }
                                           });
                                   builder.setMessage(dialogText)
                                           .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                                               public void onClick(DialogInterface dialog, int id) {
                       // ボタンをクリックしたときの動作
                                               }
                                           });
                                   builder.show();
                   }
               }
       return true;
   }



    public void initBackground(){
        isSettingScene = true;
        isGameScene = false;
        settingPhase ="setting_menu";
    }

//    public static boolean appInstalled(Context context, String uri)
//    {
//        PackageManager pm = context.getPackageManager();
//        boolean appInstalled = false;
//        try{
//            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
//            return true;
//        }catch (PackageManager.NameNotFoundException e){
//            return false;
//        }
//    }
//    public static void sendTextToLine(Context context, String lineComment){
//        try {
//            String lineString = "line://msg/text/" + lineComment;
//            Intent intent = Intent.parseUri(lineString, Intent.URI_INTENT_SCHEME);
//            context.startActivity(intent);
//        } catch (Exception e) {
//            //LINE投稿失敗
//        }
//    }
}

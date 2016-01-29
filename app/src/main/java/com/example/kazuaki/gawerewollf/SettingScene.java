package com.example.kazuaki.gawerewollf;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static ListView playerListView;
    public static SimpleAdapter adapter;
    public static List<Map<String,String>> listInfoDicArray;//リスト情報のMap
    public static ArrayList<String> playerNameArray;

    public static ListView roleListView;
    public static SimpleAdapter roleAdapter;
    public static List<Map<String,String>> roleSettingInfoArray;
    public static ArrayList<String> roleSettingArray;


    public static int selectedPlayerId;

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

        // 参加プレイヤー用リスト
        playerListView = new ListView(this);
        playerNameArray = new ArrayList<>();

        FrameLayout.LayoutParams centerListLp = new FrameLayout.LayoutParams(customView.width,customView.height*4/10);
        centerListLp.gravity = Gravity.TOP;
        centerListLp.topMargin = customView.height * 20 / 100;

        playerListView.setLayoutParams(centerListLp);
        listInfoDicArray = new ArrayList<Map<String,String>>();

        adapter = new SimpleAdapter(this,listInfoDicArray,android.R.layout.simple_list_item_2,new String[]{"name","listSecondInfo"},new int[]{android.R.id.text1,android.R.id.text2});

        playerListView.setAdapter(adapter);
        playerListView.setBackgroundColor(Color.WHITE);
        playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
//                if (phase.equals("player_setting")) {
//                    selectedPlayerId = -2;
//                } else {
//                    selectedPlayerId = listPlayerIdArray.get(position);
//                }
//
//                if (phase.equals("player_setting")) {
//
//                }
//                else {
//                    if (nowPlayer < playerArray.size() && playerArray.get(nowPlayer).get("roleId") == Utility.Role.Werewolf) {
//                        if (isFirstNight) {//人狼：初日の夜はタッチできない
//                            if (selectedPlayerId == -1) {
//                                goNextPhase();
//                                customView.invalidate();
//                            }
//
//                        } else {// 人狼：2日目以降タッチされたplayerIdを渡して再描画
//                            wolfkill(selectedPlayerId, 0);
//                            goNextPhase();
//                            customView.invalidate();
//                        }
//                    } else if (nowPlayer < playerArray.size() && playerArray.get(nowPlayer).get("roleId") == Utility.Role.Bodyguard) {
//                        bodyguardId = selectedPlayerId;
//                        goNextPhase();
//                        customView.invalidate();
//                    } else {
//                        goNextPhase();
//                        customView.invalidate();
//                    }
//                }
//            }

        });
        // TODO 長押しで削除
        playerListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,
                                           View view, int position, long id) {
                String dialogText = String.format("「%d」さんを削除します",listInfoDicArray.get(position).get("name"));
                selectedPlayerId = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingScene.this);
                builder.setMessage(dialogText)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // ボタンをクリックしたときの動作
                                // dialog 表示しない
                                onDialog = false;
                                listInfoDicArray.remove(selectedPlayerId);

                                playerNameArray.clear();
                                listInfoDicArray.clear();

                                for (int i = 0; i < playerNameArray.size(); i++) {

                                    Map<String,String> conMap = new HashMap<>();
                                    conMap.put("name",playerNameArray.get(i));
                                    conMap.put("listSecondInfo","");
                                    listInfoDicArray.add(conMap);
                                }

                                playerListView.invalidateViews();
                            }
                        });
                builder.setMessage(dialogText)
                        .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // ボタンをクリックしたときの動作
                            }
                        });
                builder.show();

                return true;
            }
        });

        layout.addView(playerListView);

//        // 配役設定用
//        roleListView = new ListView(this);
//        roleSettingArray = new ArrayList<>();
//
////        FrameLayout.LayoutParams centerListLp = new FrameLayout.LayoutParams(customView.width,customView.height*4/10);
//        centerListLp.gravity = Gravity.TOP;
//        centerListLp.topMargin = customView.height * 20 / 100;
//
//        roleListView.setLayoutParams(centerListLp);
//        roleSettingInfoArray = new ArrayList<Map<String,String>>();
//
//        roleAdapter = new SimpleAdapter(this,roleSettingInfoArray,android.R.layout.simple_list_item_2,new String[]{"role","volume"},new int[]{android.R.id.text1,android.R.id.text2});
//
//        playerListView.setAdapter(roleAdapter);
//        playerListView.setBackgroundColor(Color.WHITE);
//        playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            }
////                if (phase.equals("player_setting")) {
////                    selectedPlayerId = -2;
////                } else {
////                    selectedPlayerId = listPlayerIdArray.get(position);
////                }
////
////                if (phase.equals("player_setting")) {
////
////                }
////                else {
////                    if (nowPlayer < playerArray.size() && playerArray.get(nowPlayer).get("roleId") == Utility.Role.Werewolf) {
////                        if (isFirstNight) {//人狼：初日の夜はタッチできない
////                            if (selectedPlayerId == -1) {
////                                goNextPhase();
////                                customView.invalidate();
////                            }
////
////                        } else {// 人狼：2日目以降タッチされたplayerIdを渡して再描画
////                            wolfkill(selectedPlayerId, 0);
////                            goNextPhase();
////                            customView.invalidate();
////                        }
////                    } else if (nowPlayer < playerArray.size() && playerArray.get(nowPlayer).get("roleId") == Utility.Role.Bodyguard) {
////                        bodyguardId = selectedPlayerId;
////                        goNextPhase();
////                        customView.invalidate();
////                    } else {
////                        goNextPhase();
////                        customView.invalidate();
////                    }
////                }
////            }
//
//        });

    }

    public static void drawListView(ListView listView,boolean visible){
        if(visible == true) {
            listView.setVisibility(View.VISIBLE);
        }else if(visible == false){
            listView.setVisibility(View.INVISIBLE);
        }
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
//                                       Toast.makeText(SettingScene.this, addPlayerView.getText().toString(), Toast.LENGTH_LONG).show();

                                       String text = addPlayerView.getText().toString();
                                       if(!(text.equals(""))){
                                           playerNameArray.add(text);
                                       }

                                       listInfoDicArray.clear();

                                       for (int i = 0; i < playerNameArray.size(); i++) {

                                           Map<String,String> conMap = new HashMap<>();
                                           conMap.put("name",playerNameArray.get(i));
                                           conMap.put("listSecondInfo","");
                                           listInfoDicArray.add(conMap);
                                       }

                                       playerListView.invalidateViews();
//                                       // 中身クリア
//                                       GameScene.editText.getEditableText().clear();
                                   }
                               })
                               .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {

                                   }
                               })
                               .show();

                       dialogPattern = "";

                   }else if(dialogPattern.equals("start")){

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
//                                                   customView.invalidate();

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
//                   else if(dialogPattern.equals("roleVolume")){
//                       ArrayList<Integer> roleVolume = new ArrayList<>();
//                       for(int i = 0;i<playerNameArray.size();i++){
//                           roleVolume.add(i);
//                       }
//                       builder.setTitle("人数を選択してください")
//                               //setViewにてビューを設定
//                               .setSingleChoiceItems(roleVolume,0,mClickListerner)
//                               .setPositiveButton("追加", new DialogInterface.OnClickListener() {
//                                   @Override
//                                   public void onClick(DialogInterface dialog, int which) {
////                                       Toast.makeText(SettingScene.this, addPlayerView.getText().toString(), Toast.LENGTH_LONG).show();
//
//                                       String text = addPlayerView.getText().toString();
//                                       if (!(text.equals(""))) {
//                                           playerNameArray.add(text);
//                                       }
//
//                                       listInfoDicArray.clear();
//
//                                       for (int i = 0; i < playerNameArray.size(); i++) {
//
//                                           Map<String, String> conMap = new HashMap<>();
//                                           conMap.put("name", playerNameArray.get(i));
//                                           conMap.put("listSecondInfo", "");
//                                           listInfoDicArray.add(conMap);
//                                       }
//
//                                       playerListView.invalidateViews();
////                                       // 中身クリア
////                                       GameScene.editText.getEditableText().clear();
//                                   }
//                               })
//                               .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
//                                   @Override
//                                   public void onClick(DialogInterface dialog, int which) {
//
//                                   }
//                               })
//                               .show();
//
//                       dialogPattern = "";
//
//                   }
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

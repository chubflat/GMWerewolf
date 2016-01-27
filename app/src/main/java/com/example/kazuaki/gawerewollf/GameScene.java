package com.example.kazuaki.gawerewollf;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.FrameLayout.LayoutParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameScene extends Activity {
    public void main(String args[]) {
    }

    public static ArrayList<Integer> listPlayerIdArray;// listに入っているplayerのID
    public static int day;//日にち
    //    public static String victim;
    public static ArrayList<Integer> victimArray;//夜間犠牲者リスト
    public static List<Map<String,Object>> playerArray;

    public static int nowPlayer;//今端末を操作しているプレイヤー
    public static ListView listView;//リストビュー
    public static EditText editText;//名前入力用
    public static int selectedPlayerId;//リストで選択されたプレイヤーID
    public static int mediumId;//昼に処刑されたプレイヤーのID
    public static int bodyguardId;//狩人が守ったプレイヤーのID
    public static List<Map<String,String>> listInfoDicArray;//リスト情報のMap
    public static SimpleAdapter adapter;
    public static boolean isFirstNight;//初日フラグ
    public static ArrayList<ArrayList<Integer>> wolfkillArray;
    public static ArrayList<String> prePlayerList;//参加者リスト
    public static CustomView customView;

    public static boolean isGameScene = true;
    public static boolean isSettingScene = false;
    public static String gamePhase;
    public static String nightPhase;

    public static boolean onDialog;
    public static String dialogPattern;


    public static String lineText = "test";

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
        gamePhase = "set_role";
        isGameScene = true;
        initBackground();
        super.onCreate(savedInstanceState);

        FrameLayout layout = new FrameLayout(this);
        setContentView(layout);

        //custom add
        customView = new CustomView(this);
        layout.addView(customView);

        //EditText
//        editText = new EditText(this);
//        LayoutParams editLP = new LayoutParams(customView.width,customView.height/10);
//        editLP.gravity = Gravity.BOTTOM;
//        editLP.bottomMargin = customView.height*45/100;

//        prePlayerList = new ArrayList<>();
//
//        editText.setLayoutParams(editLP);
//        editText.setBackgroundColor(Color.WHITE);
//
//        layout.addView(editText);

        // ListView add
        listView = new ListView(this);
        LayoutParams lp = new LayoutParams(customView.width,customView.height*4/10);
        lp.gravity = Gravity.BOTTOM;
        lp.bottomMargin = 0;
        selectedPlayerId = -2;

        listPlayerIdArray = new ArrayList<>();

        listInfoDicArray = new ArrayList<Map<String,String>>();


        adapter = new SimpleAdapter(this,listInfoDicArray,android.R.layout.simple_list_item_2,new String[]{"name","listSecondInfo"},new int[]{android.R.id.text1,android.R.id.text2});

        listView.setAdapter(adapter);
        listView.setLayoutParams(lp);
        listView.setBackgroundColor(Color.WHITE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (gamePhase.equals("player_setting")) {
                    selectedPlayerId = -2;
                } else {
                    selectedPlayerId = listPlayerIdArray.get(position);
                }

                if (gamePhase.equals("player_setting")) {

                } else {
                    if (nowPlayer < playerArray.size() && playerArray.get(nowPlayer).get("roleId") == Utility.Role.Werewolf) {
                        if (isFirstNight) {//人狼：初日の夜はタッチできない
                            if (selectedPlayerId == -1) {
                                goNextPhase();
                                customView.invalidate();
                            }

                        } else {// 人狼：2日目以降タッチされたplayerIdを渡して再描画
                            wolfkill(selectedPlayerId, 0);
                            goNextPhase();
                            customView.invalidate();
                        }
                    } else if (nowPlayer < playerArray.size() && playerArray.get(nowPlayer).get("roleId") == Utility.Role.Bodyguard) {
                        bodyguardId = selectedPlayerId;
                        goNextPhase();
                        customView.invalidate();
                    } else {
                        goNextPhase();
                        customView.invalidate();
                    }
                }
            }

        });
        layout.addView(listView);
    }


        @Override
        public boolean onTouchEvent(MotionEvent event){


            String dialogText = "dialogText";

            if(event.getAction() == MotionEvent.ACTION_DOWN && onDialog == true ){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                switch (dialogPattern){
                    case "sendLine":
                        dialogText = "LINEに投稿しますか？";
                        break;
                    case "next":
                        dialogText = "次の役職に移ります";
                        break;
                    case "finish_night":
                        dialogText = "夜時間を終了します";
                        break;
                    default:
                        break;
                }
                builder.setMessage(dialogText)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
    // ボタンをクリックしたときの動作
                                switch (dialogPattern){
                                    case "sendLine":
                                        sendTextToLine(GameScene.this,lineText);
                                        break;

                                    case "next":
                                    case "finish_night":
                                        goNextNightPhase();
                                        break;
                                    default:
                                        break;

                                }

                                onDialog = false;
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

            return true;
        }

    public static void setListAdapter(int type){
//        names.clear();
//        listSecondInfo.clear();
        listInfoDicArray.clear();
        listPlayerIdArray.clear();
        if(type == -1) { //処刑用
            for (int i = 0; i < playerArray.size(); i++) {
                if ((boolean) playerArray.get(i).get("isLive") == true) {
                    listPlayerIdArray.add(i);

                    Map<String,String> conMap = new HashMap<>();
                    conMap.put("name",(String)playerArray.get(i).get("name"));
                    conMap.put("listSecondInfo","");
                    listInfoDicArray.add(conMap);
                }
            }
        }else if(type == 1){//人狼用
            if(isFirstNight){//仲間確認用
                for (int i = 0; i < playerArray.size(); i++) {
                    if (playerArray.get(i).get("roleId") == Utility.Role.Werewolf && nowPlayer != i) {
                        listPlayerIdArray.add(i);

                        Map<String,String> conMap = new HashMap<>();
                        conMap.put("name",(String)playerArray.get(i).get("name"));
                        conMap.put("listSecondInfo","");
                        listInfoDicArray.add(conMap);
                    }
                }
                Map<String,String> confirm = new HashMap<>();
                confirm.put("name","確認したらここをタップ");
                confirm.put("listSecondInfo", "");
                listInfoDicArray.add(confirm);
                listPlayerIdArray.add(-1);

            }else{ // 噛み用
                for (int i = 0; i < playerArray.size(); i++) {
                    if ((boolean) playerArray.get(i).get("isLive") == true && playerArray.get(i).get("roleId") != Utility.Role.Werewolf) {
                        listPlayerIdArray.add(i);

                        Map<String,String> conMap = new HashMap<>();
                        conMap.put("name", (String) playerArray.get(i).get("name"));

                        String listSecondInfo = String.format("feel: %d ,should: %d ,must: %d ",wolfkillArray.get(i).get(0),wolfkillArray.get(i).get(1),wolfkillArray.get(i).get(2));
                        if((wolfkillArray.get(i).get(0) + wolfkillArray.get(i).get(1) + wolfkillArray.get(i).get(2) >  0)){
                            conMap.put("listSecondInfo",listSecondInfo);
                        }else{
                            conMap.put("listSecondInfo","");
                        }
                        listInfoDicArray.add(conMap);
                    }
                }
            }
        }else if(type == 2){//予言者用
            for(int i=0;i < playerArray.size();i++){
                if((boolean) playerArray.get(i).get("isLive") == true && playerArray.get(i).get("roleId") != Utility.Role.Seer){
                    listPlayerIdArray.add(i);

                    Map<String,String> conMap = new HashMap<>();
                    conMap.put("name",(String)playerArray.get(i).get("name"));
                    conMap.put("listSecondInfo","");
                    listInfoDicArray.add(conMap);
                }
            }
        }else if(type == 3){
            for(int i=0;i < playerArray.size();i++){
                if((boolean) playerArray.get(i).get("isLive") == true && playerArray.get(i).get("roleId") != Utility.Role.Bodyguard){
                    listPlayerIdArray.add(i);

                    Map<String,String> conMap = new HashMap<>();
                    conMap.put("name",(String)playerArray.get(i).get("name"));
                    conMap.put("listSecondInfo","");
                    listInfoDicArray.add(conMap);
                }
            }
        }
        listView.invalidateViews();
    }
    public static void drawListView(boolean visible){
        if(visible == true) {
            listView.setVisibility(View.VISIBLE);
        }else if(visible == false){
            listView.setVisibility(View.INVISIBLE);
        }
    }

    // 初期化メソッド
    public static void initBackground() {
        day = 1;
        isFirstNight = true;
        gamePhase = "set_role";
        victimArray = new ArrayList<Integer>(); // 犠牲者リスト
        nightPhase = "werewolf";
        lineText = "";
//        drawListView(false);

    }

    public static ArrayList<Integer> roleArray;

    public static void setRole(){
        Map<String, Object> playerMap;
        //TODO EditText作成時
        ArrayList<String> playerName = new ArrayList<>();
        for(int i=0;i<prePlayerList.size();i++){
            playerName.add(prePlayerList.get(i));
        }

        ArrayList<Utility.Role> fixedRoleArray = new ArrayList<>();
        //TODO 役職追加時
        roleArray = new ArrayList<>();
//        = new ArrayList<>(Arrays.asList(2,2,1,1,1,1));// 村狼占霊狂狩

        // デフォルトの場合
        switch (playerName.size()){
            case 1:
                roleArray = new ArrayList<>(Arrays.asList(1,0,0,0,0,0));
                break;
            case 2:
                roleArray = new ArrayList<>(Arrays.asList(1,1,0,0,0,0));
                break;
            case 3:
                roleArray = new ArrayList<>(Arrays.asList(2,1,0,0,0,0));
                break;
            case 4:
                roleArray = new ArrayList<>(Arrays.asList(3,1,0,0,0,0));
                break;
            case 5:
                roleArray = new ArrayList<>(Arrays.asList(3,1,1,0,0,0));
                break;
            case 6:
                roleArray = new ArrayList<>(Arrays.asList(4,1,1,0,0,0));
                break;
            case 7:
                roleArray = new ArrayList<>(Arrays.asList(5,1,1,0,0,0));
                break;
            case 8:
                roleArray = new ArrayList<>(Arrays.asList(5,2,1,0,0,0));
                break;
            case 9:
                roleArray = new ArrayList<>(Arrays.asList(5,2,1,1,0,0));
                break;
            case 10:
                roleArray = new ArrayList<>(Arrays.asList(5,2,1,1,1,0));
                break;
            case 11:
                roleArray = new ArrayList<>(Arrays.asList(5,2,1,1,1,1));
                break;
            case 12:
                roleArray = new ArrayList<>(Arrays.asList(6,2,1,1,1,1));
                break;
            case 13:
                roleArray = new ArrayList<>(Arrays.asList(6,3,1,1,1,1));
                break;
            default:
                break;
        }
        for(int i=0;i<roleArray.size();i++){

            for(int j=0;j<roleArray.get(i);j++){
                fixedRoleArray.add((Utility.Role.values()[i]));
//                fixedRoleArray.add(Utility.Role.Villager);
            }
        }
        Collections.shuffle(fixedRoleArray);
//        Log.d("fixedRoleArray.size()","fixedRoleArray.size()="+fixedRoleArray.size());

        playerArray = new ArrayList<>();
        for(int i=0;i<playerName.size();i++) {
            playerMap = new HashMap<>();
            playerMap.put("playerId", i);
            playerMap.put("name", playerName.get(i));
            playerMap.put("isLive",true);
            playerMap.put("roleId",fixedRoleArray.get(i));

            playerArray.add(playerMap);
        }
        wolfkillArray = new ArrayList<>();
        for(int i =0;i<playerArray.size();i++){
            ArrayList<Integer> array = new ArrayList<>();
            for(int j = 0;j<3;j++){
                array.add(0);
            }
            wolfkillArray.add(array);
        }
//        victimArray = new ArrayList<Integer>();
    }

    public void setBackground(){
        //TODO 画面作成処理
        //TODO 終了判定
        // isFinishメソッドを呼び出す
        // winIdに応じてテキストをかえる

        //TODO 続行の場合
        // 最初の人を探す
        // ◯日目の夜になりました
        // 役職ごとに文章を表示
    }
    public static void wolfkill(int id,int strong){
        // 配列の適切な部分を+1する
        int point = wolfkillArray.get(id).get(strong);
        point++;
        wolfkillArray.get(id).set(strong,point);
    }
    public static void sumWolfkill(){
        ArrayList<Integer> maxkillArray = new ArrayList<>();
        int max = 1;
        for(int i = 0;i<playerArray.size();i++){
            int point = wolfkillArray.get(i).get(0) + wolfkillArray.get(i).get(1)*2 + wolfkillArray.get(i).get(2)*3;
            if(max < point){
                max = point;
                maxkillArray.clear();
                maxkillArray.add(i);
            }else if(max == point){
                maxkillArray.add(i);
            }
        }
        if(maxkillArray.size() > 0){
            Collections.shuffle(maxkillArray);
            int killIndex = maxkillArray.get(0);
            if(killIndex == bodyguardId){

            }else{
                victimArray.add(killIndex);
            }
        }
        for(int i = 0;i < victimArray.size();i++){
            playerArray.get(victimArray.get(i)).put("isLive", false);
        }
    }

    public static void setBackScene(){}
    public static void goNextPhase(){
        // TODO phaseの順番を定義する
        drawListView(false);
        switch (gamePhase){
            case "set_role":
                gamePhase = "night_opening";
                day = 1;
                break;
            case "night_opening":
                gamePhase = "night_action";
                break;
            case "night_action":

                gamePhase = "morning";
                break;
            case "morning":
                if(isFinish() == 0) {
                    gamePhase = "afternoon_meeting";
                }else{
                    gamePhase = "gameover";
                }
                break;
            case "afternoon_meeting":
                setListAdapter(-1);
                gamePhase = "evening_voting";
                break;
            case "evening_voting":
                gamePhase = "excution";
                break;
            case "excution":
                playerArray.get(selectedPlayerId).put("isLive",false);
                mediumId = selectedPlayerId;
                refresh();
                if(isFinish() == 0) {
                    gamePhase = "night_opening";
                }else{
                    gamePhase = "gameover";
                }
                break;
            case "gameover":
                gamePhase = "winner";
                break;
            case "winner":
                gamePhase = "ending";
                break;
//            case "ending":
//                initBackground();
//                GameScene.editText.setVisibility(View.VISIBLE);
//                drawListView(true);
//                gamePhase = "player_setting";
//                break;
            default:
                break;
        }
    }
    public static void goNextNightPhase(){
        switch (nightPhase){
            case "werewolf":
                nightPhase = "seer";
                break;
            case "seer":
                nightPhase = "medium";
                break;
            case "medium":
                nightPhase = "bodyguard";
                break;
            case "bodyguard":
                nightPhase = "minion";
                break;
            case "minion":
                nightPhase = "villager";
                break;
            case "villager":
                goNextPhase();
                // TODO refresh
                break;
        }
    }
    public static void refresh(){
        //TODO 保持している情報を消す
        for(int i = 0;i<playerArray.size();i++){
            if((boolean)playerArray.get(i).get("isLive") == true){
                nowPlayer = i;
                break;
            }
        }
        for(int i =0;i<playerArray.size();i++) {
            for (int j = 0; j < 3; j++) {
                wolfkillArray.get(i).set(j, 0);
            }
        }
        // 犠牲者クリア
        victimArray.clear();
        bodyguardId = -1;
    }

    public static int isFinish(){
        int point = 0;
        boolean wolfzero = true;
        for(int i = 0;i<playerArray.size();i++){
            Utility.Role roleId = (Utility.Role)playerArray.get(i).get("roleId");
            if((boolean)playerArray.get(i).get("isLive") == true){
                if(roleId == Utility.Role.Werewolf){
                    point--;
                    wolfzero = false;
                }else{
                    point++;
                }
            }
        }
        //TODO 勝利判定
        int result = 0;
        if(wolfzero){
            result = 1;//村人勝ち
        }
        if(point <= 0){
            result = -1;//人狼勝ち
        }
        return result;
    }

    public static void sendTextToLine(Context context, String lineComment){
        try {
            String lineString = "line://msg/text/" + lineComment;
            Intent intent = Intent.parseUri(lineString, Intent.URI_INTENT_SCHEME);
            context.startActivity(intent);
        } catch (Exception e) {
            //LINE投稿失敗
        }
    }
}

package com.example.kazuaki.gawerewollf;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kazuaki on 2015/12/23.
 */
public class CustomView extends View {

    //TODO Bitmap宣言
    private Bitmap backgroundImg = null;
    private Bitmap roleImg = null;
    private Bitmap frameImg = null;
    private Bitmap timerFrameImg = null;
    private Bitmap buttonImg = null;
    private Bitmap backCard = null;

    //TODO サイズ取得用
    public static int width;
    public static int height;

    public static int bitmapWidth;
    public static int bitmapHeight;

    public static float textSize;

    //SettingScene用
    public static Rect backgroundRect;
    public static Rect clientButtonRect;
    public static Rect userSettingButtonRect;
    // TODO ユーザー設定中身

    //gameScene用
    public static Rect confirmButtonRect;
    public static Rect actionButtonRect;
    public static Rect topTextRect;
    public static Rect roleCardRect;
    public static Rect timerRect;

    // TODO GameSceneと共通の変数
    public static int day = 0;
    public static int selectedPlayerId;
    public static int mediumId;
    public static boolean isFirstNight;
    //    public static String scene;
    public static String settingPhase;
    public static String gamePhase;
    public static Boolean isSettingScene;
    public static Boolean isGameScene;
    public static String nightPhase;


    //setting_scene用宣言
    public static Rect rectButton1;
    public static Rect rectButton2;
    public static Rect rectButton3;
    public static Rect rectButton4;
    public static Rect rectButton5;
    public static Rect rectButton6;
    public static String playerVolume;

    public static String lineText ="";



    //TODO Canvasに新要素追加時

    public CustomView(Context context) {
        super(context);
        setFocusable(true);

        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;
    }

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

    public void initBackground(){
        // Bitmap初期化
        bitmapWidth = width;
        bitmapHeight = height;
        backgroundImg = decodeSampledBitmapFromResource(getResources(),R.drawable.afternoon,bitmapWidth,bitmapHeight);
        frameImg = decodeSampledBitmapFromResource(getResources(),R.drawable.frame,bitmapWidth,bitmapHeight);
        //TODO 画像サイズ修正時になおす
        timerFrameImg = decodeSampledBitmapFromResource(getResources(),R.drawable.frame,bitmapWidth,bitmapHeight);
        buttonImg = decodeSampledBitmapFromResource(getResources(),R.drawable.button,bitmapWidth,bitmapHeight);
        roleImg = decodeSampledBitmapFromResource(getResources(),R.drawable.card0,bitmapWidth,bitmapHeight);
//        backCard = decodeSampledBitmapFromResource(getResources(),R.drawable.back_card,bitmapWidth,bitmapHeight);

        //SettingScene用Rect初期化
        backgroundRect = new Rect(0,0,bitmapWidth,bitmapHeight);
//        clientButtonRect = new Rect(width * 10 / 100 ,height * 50 / 100,width * 90 / 100 ,height * 60 / 100);
//        userSettingButtonRect = new Rect(width * 10 / 100 ,height * 65 / 100,width * 90 / 100 ,height * 75 / 100);

        //GameScene用Rect初期化
        confirmButtonRect = new Rect(width * 10 / 100 ,height * 80 / 100,width * 90 / 100 ,height * 90 / 100);
//        actionButtonRect = new Rect (width * 75 / 100 ,height * 5 / 100,width * 95 / 100 ,height * 20 / 100);
//        topTextRect = new Rect(width * 20 / 100 ,height * 5 / 100,width * 80 / 100 ,height * 15 / 100);
//        roleCardRect = new Rect(width * 5 / 100, height * 5/100 ,width * 20 / 100 ,height * 20 / 100);
//        timerRect = new Rect(width * 22 / 100, height * 5/100 ,width * 70 / 100 ,height * 20 / 100);

        SettingScene.drawListView(SettingScene.playerListView,false);

    }


    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        Paint paint = new Paint();
        textSize = (float)height * 4 / 100 ;

        paint.setTextSize(textSize);
        paint.setColor(Color.BLACK);

        int button1H = height * 82 / 100;//TODO ボタン位置変更時
        int button2H = button1H - height * 13/100;
        int button3H = button2H - height * 13/100;
        int button4H = button3H - height * 13/100;
        int button5H = button4H - height * 13/100;
        int button6H = button5H - height * 13/100;

        rectButton1 = new Rect(width*10/100,button1H,width*90/100,button1H + height*10/100);
        rectButton2 = new Rect(width*10/100,button2H,width*90/100,button2H + height*10/100);
        rectButton3 = new Rect(width*10/100,button3H,width*90/100,button3H + height*10/100);
        rectButton4 = new Rect(width*10/100,button4H,width*90/100,button4H + height*10/100);
        rectButton5 = new Rect(width*10/100,button5H,width*90/100,button5H + height*10/100);
        rectButton6 = new Rect(width*10/100,button6H,width*90/100,button6H + height*10/100);

        //SettingScene のボタン整理

        initBackground();
        //TODO GameSceneとの共有変数の初期化
        setSameVariable();

        backgroundImg = decodeSampledBitmapFromResource(getResources(),R.drawable.afternoon,bitmapWidth,bitmapHeight);
        canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);

        if(isSettingScene){
            switch (settingPhase){

                case "setting_menu":
                    // 背景
                    backgroundImg = decodeSampledBitmapFromResource(getResources(),R.drawable.afternoon,bitmapWidth,bitmapHeight);
                    canvas.drawBitmap(backgroundImg, null, backgroundRect, paint);
                    //スタート
                    canvas.drawBitmap(buttonImg,null,rectButton1,paint);
                    canvas.drawText("スタート", width / 4, button1H + height * 6/100,paint);
                    //ルール設定
                    canvas.drawBitmap(buttonImg,null,rectButton2,paint);
                    canvas.drawText("ルール設定", width / 4,button2H + height * 6/100, paint);
                    //配役設定
                    canvas.drawBitmap(buttonImg,null,rectButton3,paint);
                    canvas.drawText("配役設定", width / 4, button3H + height * 6/100, paint);
                    //プレイヤー設定
                    canvas.drawBitmap(buttonImg,null,rectButton4,paint);
                    canvas.drawText("プレイヤー設定", width / 4, button4H +height * 6/100, paint);
                    //役職一覧
                    canvas.drawBitmap(buttonImg,null,rectButton5,paint);
                    canvas.drawText("役職説明", width / 4, button5H + height * 6/100,paint);
                    //プレイヤー数表示
                    playerVolume = String.format("プレイヤー数：%d人",SettingScene.listArray.size());
                    paint.setColor(Color.WHITE);
                    textSize = (float)height * 6 / 100;
                    paint.setTextSize(textSize);
                    canvas.drawText(playerVolume, width * 5 / 100, height * 10 / 100 ,paint);
                    break;

                case "rule_setting":
                    // 背景
                    backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.night);
                    canvas.drawBitmap(backgroundImg, null, backgroundRect, paint);
                    //戻る
                    canvas.drawBitmap(buttonImg, null, rectButton1,paint);
                    canvas.drawText("戻る", width / 4, button1H + height * 6 / 100, paint);
                    //推奨設定
                    canvas.drawBitmap(buttonImg,null,rectButton2, paint);
                    canvas.drawText("推奨設定", width / 4, button2H + height * 6 / 100, paint);
                    //議論時間
                    canvas.drawBitmap(buttonImg, null, rectButton3, paint);
                    String textTime = String.format("議論時間：%d分", meetingTime);//TODO 変更可能
                    canvas.drawText(textTime, width / 4, button3H + height * 6 / 100, paint);
                    //初日占い
                    canvas.drawBitmap(buttonImg, null, rectButton4, paint);
                    String textSeer = String.format("初日占い：%s",setText("seerMode"));// TODO 変更可能に
                    canvas.drawText(textSeer, width / 4, button4H + height * 6/100, paint);
                    //役かけ
                    canvas.drawBitmap(buttonImg, null, rectButton5, paint);
                    String textLack = String.format("役かけ：%s",setText("isLacking"));//TODO 変更可能に
                    canvas.drawText(textLack, width / 4, button5H + height * 6/100,paint);
                    //連続ガード
                    canvas.drawBitmap(buttonImg,null,rectButton6,paint);
                    String textBodyguard = String.format("連続ガード：%s",setText("canContinuousGuard"));//TODO 変更可能に
                    canvas.drawText(textBodyguard, width / 4, button6H + height * 6/100,paint);

                    break;

                case "role_setting":
                    backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.afternoon);
                    canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);
                    //戻る
                    canvas.drawBitmap(buttonImg, null, rectButton1,paint);
                    canvas.drawText("戻る", width / 4, button1H + height * 6 / 100, paint);
                    //推奨設定
                    canvas.drawBitmap(buttonImg,null,rectButton2, paint);
                    canvas.drawText("推奨設定", width / 4, button2H + height * 6 / 100, paint);
                    //プレイヤー数表示
                    playerVolume = String.format("プレイヤー数：%d人",SettingScene.listArray.size());
                    paint.setColor(Color.WHITE);
                    textSize = (float)height * 6 / 100;
                    paint.setTextSize(textSize);
                    canvas.drawText(playerVolume, width * 5 / 100, height * 10 / 100 ,paint);

                    break;

                case "player_setting":
                    // 背景
                    backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.night);
                    canvas.drawBitmap(backgroundImg, null, backgroundRect, paint);
                    //戻る
                    canvas.drawBitmap(buttonImg, null, rectButton1,paint);
                    canvas.drawText("戻る", width / 4, button1H + height * 6 / 100, paint);
                    //プレイヤー追加
                    canvas.drawBitmap(buttonImg,null,rectButton2, paint);
                    canvas.drawText("プレイヤー追加", width / 4, button2H + height * 6 / 100, paint);
                    //プレイヤー数表示
                    playerVolume = String.format("プレイヤー数：%d人", SettingScene.listArray.size());// TODO 人数表示
                    paint.setColor(Color.WHITE);
                    textSize = (float)height * 6 / 100;
                    paint.setTextSize(textSize);
                    canvas.drawText(playerVolume, width * 5 / 100, height * 10 / 100, paint);
                    //playerListView表示
                    SettingScene.drawListView(SettingScene.playerListView, true);
                    break;

                case "explain":
                    backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.afternoon);
                    canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);
                    //戻る
                    canvas.drawBitmap(buttonImg, null, rectButton1,paint);
                    canvas.drawText("戻る", width / 4, button1H + height * 6 / 100, paint);
                    break;

                default:
                    break;
            }
        }else if(!isSettingScene && isGameScene){
            // background
            backgroundImg = decodeSampledBitmapFromResource(getResources(),R.drawable.night,bitmapWidth,bitmapHeight);
            canvas.drawBitmap(backgroundImg, null, backgroundRect, paint);

            GameScene.drawListView(false);

            switch (gamePhase){

                case "set_role":
                    backgroundImg = decodeSampledBitmapFromResource(getResources(),R.drawable.night,bitmapWidth,bitmapHeight);
                    canvas.drawBitmap(backgroundImg, null, backgroundRect, paint);
                    //配役：自動設定
                    canvas.drawBitmap(buttonImg, null, rectButton3, paint);
                    canvas.drawText("自動配役", width / 4, button3H + height * 6 / 100, paint);
                    //配役：手動設定
                    canvas.drawBitmap(buttonImg, null, rectButton5, paint);
                    canvas.drawText("手動配役", width / 4, button5H + height * 6/100,paint);

                    canvas.drawBitmap(buttonImg, null, confirmButtonRect, paint);
                    canvas.drawText("次へ", width * 25 / 100, height * 85 / 100, paint);

                    break;

                case "role_check":
                    backgroundImg = decodeSampledBitmapFromResource(getResources(),R.drawable.night,bitmapWidth,bitmapHeight);
                    canvas.drawBitmap(backgroundImg, null, backgroundRect, paint);

                    canvas.drawBitmap(frameImg, null, new Rect(width / 10, height * 2 / 10, width * 9 / 10, height * 5 / 10), paint);//width 1/10~9/10,height 2/10~5/10

                    String text;

                    text = String.format("プレイヤー数は%d人です。\n" + "村人：%s\n" + "人狼：%s\n" +"予言者：%s\n" + "霊媒師：%s\n" + "狂人：%s\n" +"狩人：%s\n",GameScene.playerNameArray.size(),GameScene.getRoleArray(0),GameScene.getRoleArray(1),GameScene.getRoleArray(2),GameScene.getRoleArray(3),GameScene.getRoleArray(4),GameScene.getRoleArray(5));

                    TextPaint mTextPaint = new TextPaint();
                    mTextPaint.setTextSize(textSize);
                    StaticLayout mTextLayout = new StaticLayout(text,mTextPaint,width*3/5, Layout.Alignment.ALIGN_NORMAL,1.0f,0.0f,false);
                    canvas.translate(width * 2 / 10, height * 25 / 100);//text の左上座標の指定

                    mTextLayout.draw(canvas);
                    canvas.restore();

                    canvas.drawBitmap(buttonImg, null, confirmButtonRect, paint);
                    canvas.drawText("次へ", width * 25 / 100, height * 85 / 100, paint);

                    break;

                case "night_opening":
                    backgroundImg = decodeSampledBitmapFromResource(getResources(),R.drawable.night,bitmapWidth,bitmapHeight);
                    canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);

                    canvas.drawBitmap(frameImg, null, new Rect(width / 10, height * 2 / 10, width * 9 / 10, height * 5 / 10), paint);//width 1/10~9/10,height 2/10~5/10

                    if(isFirstNight){
                        switch (nightPhase){
                            case "werewolf":
                                lineText = "＜はるき＞さん、＜はせべ＞さん、＜くろき＞さん、は人狼です。仲間を確認してください。";

                                break;
                            case "seer":
//                                lineText = String.format("予言者は「%s」さんです。%s","",(String) Utility.getRoleInfo((Utility.Role) GameScene.playerArray.get(nowPlayer).get("roleId")).get("explain"));
                                break;
                            case "medium":
                                lineText = "あなたは霊媒師です。";
                                break;
                            case "bodyguard":
                                lineText = "あなたは狩人です";
                                break;
                            case "minion":
                                lineText = "あなたは狂人です";
                                break;
                            case "villager":
                                lineText = "あなたは村人です。";
                                break;
                            default:
                                break;
                        }


                    }else{
                        lineText = String.format("%d日目の夜になりました。それぞれアクションを行ってください",day); // 2日目以降の夜

                    }
                    canvas.drawText(lineText,width * 10/100,height * 5/100,paint);
                    //LINEに投稿
                    canvas.drawBitmap(buttonImg, null, rectButton3, paint);
                    canvas.drawText("LINEに投稿する", width / 4, button3H + height * 6 / 100, paint);
                    //次へ
                    canvas.drawBitmap(buttonImg, null, confirmButtonRect, paint);
                    canvas.drawText("次へ", width * 25 / 100, height * 85 / 100, paint);

                    GameScene.lineText = lineText;
                    break;

                case "night_action":
                    //夜時間終了
                    canvas.drawBitmap(buttonImg, null, confirmButtonRect,paint);
                    canvas.drawText("夜時間終了", width / 4, button1H + height * 6 / 100, paint);
//                        //
//                        canvas.drawBitmap(buttonImg,null,rectButton2, paint);
//                        canvas.drawText("****", width / 4, button2H + height * 6 / 100, paint);
                    //狩人
                    canvas.drawBitmap(buttonImg, null, rectButton3, paint);
                    String textTime = String.format("狩人：%s", "未実装");
                    canvas.drawText(textTime, width / 4, button3H + height * 6 / 100, paint);
                    //霊媒師
                    canvas.drawBitmap(buttonImg, null, rectButton4, paint);
                    String textSeer = String.format("霊媒師：%s","未実装");
                    canvas.drawText(textSeer, width / 4, button4H + height * 6/100, paint);
                    //占い師
                    canvas.drawBitmap(buttonImg, null, rectButton5, paint);
                    String textLack = String.format("占い師：%s","未実装");
                    canvas.drawText(textLack, width / 4, button5H + height * 6 / 100, paint);
                    // 人狼
                    canvas.drawBitmap(buttonImg,null,rectButton6,paint);
                    String textBodyguard = String.format("人狼：%s","未実装");
                    canvas.drawText(textBodyguard, width / 4, button6H + height * 6 / 100, paint);


                    break;

                case "morning":
                    // background
                    backgroundImg = decodeSampledBitmapFromResource(getResources(),R.drawable.afternoon,bitmapWidth,bitmapHeight);
                    canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);

//                    if(GameScene.victimArray.size() == 0){
//                        text = String.format("%d日目の朝になりました。昨晩の犠牲者はいませんでした。",day);
//                    }else {
//                        text = String.format("%d日目の朝になりました。昨晩の犠牲者は、「%s」さんでした。", day, (String) GameScene.playerArray.get(GameScene.victimArray.get(0)).get("name"));
//                    }
//                    break;
                    // confirm button
                    canvas.drawBitmap(buttonImg, null, confirmButtonRect, paint);
                    canvas.drawText("次へ", width * 25 / 100, height * 85 / 100, paint);

                    break;
                case "afternoon_meeting":
                    // background
                    backgroundImg = decodeSampledBitmapFromResource(getResources(),R.drawable.afternoon,bitmapWidth,bitmapHeight);
                    canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);

//                    if(GameScene.victimArray.size() == 0){
//                        text = String.format("%d日目の朝になりました。昨晩の犠牲者はいませんでした。",day);
//                    }else {
//                        text = String.format("%d日目の朝になりました。昨晩の犠牲者は、「%s」さんでした。", day, (String) GameScene.playerArray.get(GameScene.victimArray.get(0)).get("name"));
//                    }
//                    break;
                    // confirm button
                    canvas.drawBitmap(buttonImg, null, confirmButtonRect, paint);
                    canvas.drawText("次へ", width * 25 / 100, height * 85 / 100, paint);


                    break;
                case "evening_voting":
                    // background
                    backgroundImg = decodeSampledBitmapFromResource(getResources(),R.drawable.evening,bitmapWidth,bitmapHeight);
                    canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);

                    // TODO List表示
                    backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.evening);
//                    text = "日が暮れて今日も1人処刑者を決めなければなりません。投票を行い、処刑者を決定してください。同票の場合は決選投票を行い、3回続けても変更がない場合は引き分けとなります。";
                    GameScene.drawListView(true);

//                    // confirm button
//                    canvas.drawBitmap(buttonImg, null, confirmButtonRect, paint);
//                    canvas.drawText("次へ", width * 25 / 100, height * 85 / 100, paint);

                    break;
                case "excution":
                    // background
                    backgroundImg = decodeSampledBitmapFromResource(getResources(),R.drawable.evening,bitmapWidth,bitmapHeight);
                    canvas.drawBitmap(roleImg,null,roleCardRect,paint);
                    canvas.drawBitmap(timerFrameImg,null,timerRect,paint);

                    Rect excutionFrameRect = new Rect(width * 15 / 100,height * 20 / 100,width * 85 / 100 ,height * 80 / 100);
                    canvas.drawBitmap(frameImg,null,excutionFrameRect,paint);

                    // confirm button
                    canvas.drawBitmap(buttonImg, null, confirmButtonRect, paint);
                    canvas.drawText("次へ", width * 25 / 100, height * 85 / 100, paint);

                    break;
                case "gameover":
                    // confirm button
                    canvas.drawBitmap(buttonImg, null, confirmButtonRect, paint);
                    canvas.drawText("次へ", width * 25 / 100, height * 85 / 100, paint);

                    break;
                default:
                    break;
            }
        }

    }

    // ルール設定の変数宣言

    public static boolean canContinuousGuard = true; //連続護衛有無
    public static boolean isLacking = false; //役欠け有無
    public static String seerMode = "free"; //初日占い
    public static int meetingTime = 3; //議論 時間

    public static String setText(String rule){
        String ruleText = "";
        switch (rule){
            case "seerMode":
                if(seerMode.equals("free")){
                    ruleText = "あり";
                }else if(seerMode.equals("none")){
                    ruleText = "なし";
                }else if(seerMode.equals("revelation")){
                    ruleText = "お告げ";
                }
                break;
            case "isLacking":
                if(isLacking){
                    ruleText = "あり";
                }else {
                    ruleText = "なし";
                }
                break;
            case "canContinuousGuard":
                if(canContinuousGuard){
                    ruleText = "あり";
                }else{
                    ruleText = "なし";
                }
                break;
            default:
                ruleText = "";
            break;
        }
        return ruleText;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        float pointX = event.getX();
        float pointY = event.getY();


        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(isSettingScene){
                    switch (settingPhase){
                        case "setting_menu":
                            if(rectButton1.contains((int)pointX,(int)pointY)){ //スタートボタン
                                setDialog("start");
//                                SettingScene.settingPhase = "client_menu";
                            }else if(rectButton2.contains((int)pointX,(int)pointY)){ // ルール設定
                                SettingScene.settingPhase = "rule_setting";

                            }else if(rectButton3.contains((int)pointX,(int)pointY)){ // 配役設定
                                SettingScene.settingPhase = "role_setting";

                            }else if(rectButton4.contains((int)pointX,(int)pointY)){ // プレイヤー設定
                                SettingScene.settingPhase = "player_setting";
                            } // TODO 役職説明追加
                            break;

                        case "rule_setting":
                            if(rectButton1.contains((int)pointX,(int)pointY)){ //戻る
                                SettingScene.settingPhase = "setting_menu";

                            }else if(rectButton2.contains((int)pointX,(int)pointY)){ // 推奨設定
                                meetingTime = 5;
                                seerMode = "free";
                                canContinuousGuard = true;
                                isLacking = false;

                            }else if(rectButton3.contains((int)pointX,(int)pointY)){ // 議論時間
                                if(meetingTime == 10){
                                    meetingTime = 3;
                                }else{
                                    meetingTime++;
                                }

                            }else if(rectButton4.contains((int)pointX,(int)pointY)){ // 初日占い
                                switch (seerMode){
                                    case "free":
                                        seerMode = "none";
                                        break;
                                    case "none":
                                        seerMode = "revelation";
                                        break;
                                    case "revelation":
                                        seerMode = "free";
                                        break;
                                    default:
                                        break;
                                }

                            }else if(rectButton5.contains((int)pointX,(int)pointY)){ // 役かけ
                                if(isLacking){
                                    isLacking = false;
                                }else{
                                    isLacking = true;
                                }

                            }else if(rectButton6.contains((int)pointX,(int)pointY)){ // 連続ガード
                                if(canContinuousGuard){
                                    canContinuousGuard = false;
                                }else{
                                    canContinuousGuard = true;
                                }
                            }
                            break;
                        case "role _setting":
                            if(rectButton1.contains((int)pointX,(int)pointY)){ //戻る
                                SettingScene.settingPhase = "setting_menu";
                            }else if(rectButton2.contains((int)pointX,(int)pointY)){ // 推奨設定
                                // TODO デフォルト配役セット
                            }
                            break;
                        case "player_setting":
                            if(rectButton1.contains((int)pointX,(int)pointY)) { //戻る
                                SettingScene.settingPhase = "setting_menu";
                            }else if(rectButton2.contains((int)pointX,(int)pointY)){ //プレイヤー追加
                                //TODO ダイアログ
                                setDialog("addPlayer");

                            }
                            break;
                        case "explain":
                            if(rectButton1.contains((int)pointX,(int)pointY)) { //戻る
                                SettingScene.settingPhase = "setting_menu";
                            }
                            break;
                        default:
                            break;
                    }

                }else if(!isSettingScene && isGameScene){
                    switch (gamePhase){
                        case "set_role":
                            if(rectButton3.contains((int) pointX, (int) pointY)){ //自動設定
                                GameScene.setRole();
                                GameScene.gamePhase = "role_check";

                            }
                            break;
                        case "role_check":
                            if(rectButton3.contains((int) pointX, (int) pointY)) {
                                GameScene.gamePhase = "role_check";
                                GameScene.gamePhase = "night_opening";
                            }
                            break;

                        case "night_opening":
                            if(rectButton3.contains((int) pointX, (int) pointY)){ //LINE投稿
                                setDialog("sendLine");

                            }else if(confirmButtonRect.contains((int) pointX, (int) pointY)){ //次へ
                                setDialog("next");

                        }
                            break;
                        case "night_action":
                            if(confirmButtonRect.contains((int) pointX, (int) pointY)){ //自動設定
                                setDialog("finish_night");

                            }
                            break;
                        case "morning":
                        case "afternoon_meeting":
                            if(confirmButtonRect.contains((int) pointX, (int) pointY)){ //自動設定
                                GameScene.goNextPhase();

                            }
                        case "evening_voting":
                        default:
                            break;
                    }

//                    if(confirmButtonRect.contains((int)pointX,(int)pointY)){
//                        GameScene.goNextPhase();
//                    }

                }
                break;

            default:
                return true;
        }
        invalidate();
        return false;

    }

    private void setDialog(String dialogPattern){
        if(!isGameScene){
            SettingScene.onDialog = true;
            SettingScene.dialogPattern = dialogPattern;
        }else{
            GameScene.onDialog = true;
            GameScene.dialogPattern = dialogPattern;
        }


    }

    public static void setSameVariable(){
        isSettingScene = SettingScene.isSettingScene;
        isGameScene = SettingScene.isGameScene;
        day = GameScene.day;
        selectedPlayerId = GameScene.selectedPlayerId;
        isFirstNight = GameScene.isFirstNight;
        settingPhase = SettingScene.settingPhase;
        gamePhase = GameScene.gamePhase;
        nightPhase = GameScene.nightPhase;
        lineText = GameScene.lineText;

    }



    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,int reqWidth, int reqHeight) {

// First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

// Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

// Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
// Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }


}

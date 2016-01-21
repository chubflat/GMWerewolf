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

    //setting_scene用宣言
    public static Rect rectButton1;
    public static Rect rectButton2;
    public static Rect rectButton3;
    public static Rect rectButton4;
    public static Rect rectButton5;
    public static Rect rectButton6;
    public static String playerVolume;

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
    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        Paint paint = new Paint();
        paint.setTextSize(50);
        paint.setColor(Color.BLACK);

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
        clientButtonRect = new Rect(width * 10 / 100 ,height * 50 / 100,width * 90 / 100 ,height * 60 / 100);
        userSettingButtonRect = new Rect(width * 10 / 100 ,height * 65 / 100,width * 90 / 100 ,height * 75 / 100);

        //GameScene用Rect初期化
        confirmButtonRect = new Rect(width * 10 / 100 ,height * 80 / 100,width * 90 / 100 ,height * 90 / 100);
        actionButtonRect = new Rect (width * 75 / 100 ,height * 5 / 100,width * 95 / 100 ,height * 20 / 100);
        topTextRect = new Rect(width * 20 / 100 ,height * 5 / 100,width * 80 / 100 ,height * 15 / 100);
        roleCardRect = new Rect(width * 5 / 100, height * 5/100 ,width * 20 / 100 ,height * 20 / 100);
        timerRect = new Rect(width * 22 / 100, height * 5/100 ,width * 70 / 100 ,height * 20 / 100);


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

        //TODO GameSceneとの共有変数の初期化
        setSameVariable();

        backgroundImg = decodeSampledBitmapFromResource(getResources(),R.drawable.afternoon,bitmapWidth,bitmapHeight);
        canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);

        // default List非表示
//        GameScene.drawListView(false);

        if(isSettingScene){
            switch (settingPhase){

                case "setting_menu":
                    // 背景
                    backgroundImg = decodeSampledBitmapFromResource(getResources(),R.drawable.afternoon,bitmapWidth,bitmapHeight);
                    canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);
//                //スタート
                    canvas.drawBitmap(buttonImg,null,rectButton1,paint);
                    canvas.drawText("スタート", canvas.getWidth() / 4, button1H + height * 6/100,paint);
                    //ルール設定
                    canvas.drawBitmap(buttonImg,null,rectButton2,paint);
                    canvas.drawText("ルール設定", canvas.getWidth() / 4,button2H + height * 6/100, paint);
                    //配役設定
                    canvas.drawBitmap(buttonImg,null,rectButton3,paint);
                    canvas.drawText("配役設定", canvas.getWidth() / 4, button3H + height * 6/100, paint);
                    //プレイヤー設定
                    canvas.drawBitmap(buttonImg,null,rectButton4,paint);
                    canvas.drawText("プレイヤー設定", canvas.getWidth() / 4, button4H +height * 6/100, paint);
                    //役職一覧
                    canvas.drawBitmap(buttonImg,null,rectButton5,paint);
                    canvas.drawText("役職説明", canvas.getWidth() / 4, button5H + height * 6/100,paint);
                    //プレイヤー数表示
                    playerVolume = String.format("プレイヤー数：%d人",8);
                    paint.setColor(Color.WHITE);
                    canvas.drawText(playerVolume, width * 20 / 100, height * 10 / 100 ,paint);
//
                    break;

                case "rule_setting":
                    // 背景
                    backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.night);
                    canvas.drawBitmap(backgroundImg, null, backgroundRect, paint);
                    //戻る
                    canvas.drawBitmap(buttonImg, null, rectButton1,paint);
                    canvas.drawText("戻る", canvas.getWidth() / 4, button1H + height * 6 / 100, paint);
                    //推奨設定
                    canvas.drawBitmap(buttonImg,null,rectButton2, paint);
                    canvas.drawText("推奨設定", canvas.getWidth() / 4, button2H + height * 6 / 100, paint);
                    //議論時間
                    canvas.drawBitmap(buttonImg, null, rectButton3, paint);
                    String textTime = String.format("議論時間：%d分", 5);
                    canvas.drawText(textTime, canvas.getWidth() / 4, button3H + height * 6 / 100, paint);
                    //初日占い
                    canvas.drawBitmap(buttonImg, null, rectButton4, paint);
                    String textSeer = String.format("初日占い：%s", "あり");
                    canvas.drawText(textSeer, canvas.getWidth() / 4, button4H + height * 6/100, paint);
                    //役かけ
                    canvas.drawBitmap(buttonImg, null, rectButton5, paint);
                    String textLack = String.format("役かけ：%s","なし");
                    canvas.drawText(textLack, canvas.getWidth() / 4, button5H + height * 6/100,paint);
                    //連続ガード
                    canvas.drawBitmap(buttonImg,null,rectButton6,paint);
                    String textBodyguard = String.format("連続ガード：%s","あり");
                    canvas.drawText(textBodyguard, canvas.getWidth() / 4, button6H + height * 6/100,paint);

                    break;

                case "role_setting":
                    backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.afternoon);
                    canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);
                    //戻る
                    canvas.drawBitmap(buttonImg, null, rectButton1,paint);
                    canvas.drawText("戻る", canvas.getWidth() / 4, button1H + height * 6 / 100, paint);
                    //推奨設定
                    canvas.drawBitmap(buttonImg,null,rectButton2, paint);
                    canvas.drawText("推奨設定", canvas.getWidth() / 4, button2H + height * 6 / 100, paint);
                    //プレイヤー数表示
                    playerVolume = String.format("プレイヤー数：%d人",8);
                    paint.setColor(Color.WHITE);
                    canvas.drawText(playerVolume, width * 20 / 100, height * 10 / 100 ,paint);

                    break;

                case "player_setting":
                    // 背景
                    backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.night);
                    canvas.drawBitmap(backgroundImg, null, backgroundRect, paint);
                    //戻る
                    canvas.drawBitmap(buttonImg, null, rectButton1,paint);
                    canvas.drawText("戻る", canvas.getWidth() / 4, button1H + height * 6 / 100, paint);
                    //シャッフル
                    canvas.drawBitmap(buttonImg,null,rectButton2, paint);
                    canvas.drawText("シャッフル", canvas.getWidth() / 4, button2H + height * 6 / 100, paint);
                    break;

                case "explain":
                    backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.afternoon);
                    canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);
                    //戻る
                    canvas.drawBitmap(buttonImg, null, rectButton1,paint);
                    canvas.drawText("戻る", canvas.getWidth() / 4, button1H + height * 6 / 100, paint);
                    break;

                case "client_menu":
//                    //TODO Client設定 部屋探索
//                        /*
//                        * bluetoothで部屋のIDを受信
//                        * リストに表示
//                        * リストの中身を選択したら待機画面に*/
//
//                    confirmButtonRect = new Rect(width * 10 / 100 ,height * 80 / 100,width * 90 / 100 ,height * 90 / 100);
////                    backgroundImg = BitmapFactory.decodeResource(getResources(),R.drawable.night);
//                    backgroundImg = decodeSampledBitmapFromResource(getResources(),R.drawable.night,bitmapWidth,bitmapHeight);
//                    canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);
//                    paint.setColor(Color.WHITE);
//                    canvas.drawText("ルール設定待ち",width * 30/100,height * 50 / 100,paint);
//
//                    canvas.drawBitmap(buttonImg, null, confirmButtonRect, paint);
//                    canvas.drawText("次へ",width * 25/100,height * 85/100,paint);

                    break;
                case "rule_confirm":
//                    // background
//                    backgroundImg = decodeSampledBitmapFromResource(getResources(),R.drawable.afternoon,bitmapWidth,bitmapHeight);
//                    canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);
//                    //topText
//                    canvas.drawBitmap(timerFrameImg,null,topTextRect,paint);
//                    canvas.drawText("ルール",width * 30/100,height * 10/100,paint);
//
//                    // TODO List表示
//                    // 2行リスト GameSceneに記述
//
//                    //confirmButton
//                    canvas.drawBitmap(buttonImg, null, confirmButtonRect, paint);
//                    canvas.drawText("確認",width * 25/100,height * 85/100,paint);
                    break;
                default:
                    break;
            }
        }else if(!isSettingScene && isGameScene){
            // background
            backgroundImg = decodeSampledBitmapFromResource(getResources(),R.drawable.night,bitmapWidth,bitmapHeight);
            canvas.drawBitmap(backgroundImg, null, backgroundRect, paint);

            switch (gamePhase){
                case "night_roleRotate":
                    // rotateImg 表示
                    Rect rotateCardRect = new Rect(width * 15 /100,height * 20 / 100 ,width * 85 / 100 ,height *20 /100 + width * 70 / 100  * 1125 /938 );
                    //TODO cardRotate
                    //TODO roleImgを取ってくる:デフォルトで村人

                    canvas.drawBitmap(backCard, null, rotateCardRect, paint);
                    //timer実装


                    canvas.drawBitmap(roleImg,null,rotateCardRect,paint);
                    // confirm button
                    canvas.drawBitmap(buttonImg, null, confirmButtonRect, paint);
                    canvas.drawText("詳細確認", width * 25 / 100, height * 85 / 100, paint);

                    break;
                case "night_roleCheck":

                    //Rect宣言
                    Rect topFrameRect = new Rect(width * 5 /100,height * 5 / 100 ,width * 95 / 100 ,height * 50 /100);
                    Rect belowFrameRect = new Rect(width * 20 /100,height * 60 / 100 ,width * 80 / 100 ,height * 75 /100);
                    Rect roleCheckCardRect = new Rect(width * 42 /100,height * 10 / 100 ,width * 58 / 100 ,height * 10 /100 + width * 16/100 * 1125/938);

                    // canvasDraw
                    // 画面上部のテキスト情報
                    canvas.drawBitmap(frameImg,null,topFrameRect,paint);
                    String roleText = String.format("あなたの役職は「%s」です。%s","村人","役職のテキスト");

                    TextPaint mTextPaint = new TextPaint();
                    mTextPaint.setTextSize(30);
                    StaticLayout mTextLayout = new StaticLayout(roleText,mTextPaint,width*3/5, Layout.Alignment.ALIGN_NORMAL,1.0f,0.0f,false);
                    canvas.translate(width * 2 / 10, height * 25 / 100);//text の左上座標の指定

                    mTextLayout.draw(canvas);
                    canvas.restore();





                    canvas.drawBitmap(frameImg,null,belowFrameRect,paint);
                    canvas.drawBitmap(roleImg, null, roleCheckCardRect, paint);
                    // confirm button
                    canvas.drawBitmap(buttonImg,null,confirmButtonRect,paint);
                    canvas.drawText("初日夜へ", width * 25 / 100, height * 85 / 100, paint);


                    break;

                case "night_chat":
                    canvas.drawBitmap(roleImg,null,roleCardRect,paint);
                    canvas.drawBitmap(timerFrameImg,null,timerRect,paint);
                    canvas.drawBitmap(buttonImg,null,actionButtonRect,paint);

                    String action = "占う";
                    // TODO 役職ごとに文字を変えるswitch文
                    canvas.drawText(action, width * 75 / 100, height * 10 / 100, paint);

                    // TODO Chat実装
                    // confirm button
                    canvas.drawBitmap(buttonImg, null, confirmButtonRect, paint);
                    canvas.drawText("次へ", width * 25 / 100, height * 85 / 100, paint);

                    break;

                case "morning":
                    // background
                    backgroundImg = decodeSampledBitmapFromResource(getResources(),R.drawable.morning,bitmapWidth,bitmapHeight);
                    canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);

                    canvas.drawBitmap(roleImg,null,roleCardRect,paint);
                    canvas.drawBitmap(timerFrameImg,null,timerRect,paint);
                    Rect morningFrameRect = new Rect(width * 15 / 100,height * 40 / 100,width * 85 / 100 ,height * 60 / 100);
                    canvas.drawBitmap(frameImg,null,morningFrameRect,paint);

                    //TODO Text表示

                    // confirm button
                    canvas.drawBitmap(buttonImg, null, confirmButtonRect, paint);
                    canvas.drawText("次へ", width * 25 / 100, height * 85 / 100, paint);


                    break;
                case "afternoon_meeting":
                    // background
                    backgroundImg = decodeSampledBitmapFromResource(getResources(), R.drawable.afternoon, width, height);
                    canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);

                    roleImg = decodeSampledBitmapFromResource(getResources(),R.drawable.back_card,bitmapWidth,bitmapHeight);
                    canvas.drawBitmap(roleImg,null,roleCardRect,paint);
                    canvas.drawBitmap(timerFrameImg,null,timerRect,paint);

                    // confirm button
                    canvas.drawBitmap(buttonImg, null, confirmButtonRect, paint);
                    canvas.drawText("次へ", width * 25 / 100, height * 85 / 100, paint);

                    break;
                case "evening_voting":
                    // background
                    backgroundImg = decodeSampledBitmapFromResource(getResources(),R.drawable.evening,bitmapWidth,bitmapHeight);
                    canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);

                    // TODO List表示

                    // confirm button
                    canvas.drawBitmap(buttonImg, null, confirmButtonRect, paint);
                    canvas.drawText("次へ", width * 25 / 100, height * 85 / 100, paint);

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

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float pointX = event.getX();
        float pointY = event.getY();



        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(isSettingScene){
                    switch (settingPhase){
                        case "setting_menu":
                            if(clientButtonRect.contains((int)pointX,(int)pointY)){
                                setDialog("Seer");
                                SettingScene.settingPhase = "client_menu";
                            }
                            break;
                        case "client_menu":
                            if(confirmButtonRect.contains((int)pointX,(int)pointY)){
                                setDialog("Werewolf");
                                SettingScene.settingPhase = "rule_confirm";
//                                GameScene.scene = "game_scene";
                                //コメントアウト
//                                GameScene.settingPhase = "setting_menu";
                            }
                            break;
                        case "rule_confirm":
                            if(confirmButtonRect.contains((int)pointX,(int)pointY)){
                                SettingScene.isSettingScene = true;
                                SettingScene.isGameScene = true;
                            }

                            break;
                        default:
                            break;
                    }

                }else if(!isSettingScene && isGameScene){
                    if(confirmButtonRect.contains((int)pointX,(int)pointY)){
                        GameScene.goNextPhase();
                    }

                }
                break;

            default:
                return true;
        }
        if(!settingPhase.equals("rule_confirm")){
            invalidate();
        }

        return false;

    }

    private void setDialog(String dialogPattern){
        GameScene.onDialog = true;
        GameScene.dialogPattern = dialogPattern;
    }

    public static void setSameVariable(){
        isSettingScene = SettingScene.isSettingScene;
        isGameScene = SettingScene.isGameScene;
        day = GameScene.day;
        selectedPlayerId = GameScene.selectedPlayerId;
        isFirstNight = GameScene.isFirstNight;
        settingPhase = SettingScene.settingPhase;
        gamePhase = GameScene.gamePhase;

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


//    private Bitmap backgroundImg =null;
//    private Bitmap frameImg = null;
//    private Bitmap buttonImg;
//    public String text = "text";
//    public static String phase = "";
//    public static int nowPlayer = 0;
//    public static int day;
//    public static int selectedPlayerId;
//    public static int mediumId;
//    public static int bodyguardId;
//    public static boolean isFirstNight = true;
//
//    //画面サイズ用宣言
//    public static int width;
//    public static int height;
//    public static int textSize;
//
//    public static Rect buttonrect;
//    public static Rect addButton;
//    public static Rect startButton;
//    public static int playerSize;
//
//    //背景サイズ
//    public static Rect backgroundRect;
//
//    //setting_scene用宣言
//    public static String scene;
//    public static Rect rectButton1;
//    public static Rect rectButton2;
//    public static Rect rectButton3;
//    public static Rect rectButton4;
//    public static Rect rectButton5;
//    public static Rect rectButton6;
//
//    //プレイヤー数表示
//    public static String playerVolume;
//
//    public CustomView(Context context){
//        super(context);
//        setFocusable(true);
//
//        // WindowsManager取得
//        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
//        // Displayインスタンス生成
//        Display dp = wm.getDefaultDisplay();
//        // ディスプレイサイズ取得
//        width = dp.getWidth();
//        height = dp.getHeight();
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        Paint paint = new Paint();
//        backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.night);
//        frameImg = BitmapFactory.decodeResource(getResources(), R.drawable.frame);
//        buttonImg = BitmapFactory.decodeResource(getResources(), R.drawable.button);
//        paint.setTextSize(50);
//
//        backgroundRect  = new Rect(0,0,width,height);
//
//        int button1H = height * 82 / 100;//TODO ボタン位置変更時
//        int button2H = button1H - height * 13/100;
//        int button3H = button2H - height * 13/100;
//        int button4H = button3H - height * 13/100;
//        int button5H = button4H - height * 13/100;
//        int button6H = button5H - height * 13/100;
//
//        rectButton1 = new Rect(width*10/100,button1H,width*90/100,button1H + height*10/100);
//        rectButton2 = new Rect(width*10/100,button2H,width*90/100,button2H + height*10/100);
//        rectButton3 = new Rect(width*10/100,button3H,width*90/100,button3H + height*10/100);
//        rectButton4 = new Rect(width*10/100,button4H,width*90/100,button4H + height*10/100);
//        rectButton5 = new Rect(width*10/100,button5H,width*90/100,button5H + height*10/100);
//        rectButton6 = new Rect(width*10/100,button6H,width*90/100,button6H + height*10/100);
//
//
//
//        phase = GameScene.phase;
//        nowPlayer = GameScene.nowPlayer;
//        day = GameScene.day;
//        selectedPlayerId = GameScene.selectedPlayerId;
//        isFirstNight = GameScene.isFirstNight;
//        mediumId = GameScene.mediumId;
//        bodyguardId = GameScene.bodyguardId;
//
//        scene = SettingScene.scene;
//
//
//        switch (scene){
//
//            case "setting_scene":
//
//                // 背景
//                backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.afternoon);
//                canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);
////                //スタート
////                canvas.drawBitmap(buttonImg,null,rectButton1,paint);
////                canvas.drawText("スタート", canvas.getWidth() / 4, button1H + height * 6/100,paint);
//                //ルール設定
//                canvas.drawBitmap(buttonImg,null,rectButton2,paint);
//                canvas.drawText("ルール設定", canvas.getWidth() / 4,button2H + height * 6/100, paint);
//                //配役設定
//                canvas.drawBitmap(buttonImg,null,rectButton3,paint);
//                canvas.drawText("配役設定", canvas.getWidth() / 4, button3H + height * 6/100, paint);
//                //プレイヤー設定
//                canvas.drawBitmap(buttonImg,null,rectButton4,paint);
//                canvas.drawText("プレイヤー設定", canvas.getWidth() / 4, button4H + height * 6/100, paint);
//                //役職一覧
//                canvas.drawBitmap(buttonImg,null,rectButton5,paint);
//                canvas.drawText("役職説明", canvas.getWidth() / 4, button5H + height * 6/100,paint);
//                //プレイヤー数表示
//                playerVolume = String.format("プレイヤー数：%d人",8);
//                paint.setColor(Color.WHITE);
//                canvas.drawText(playerVolume, width * 20 / 100, height * 10 / 100 ,paint);
//
//                break;
//
//            case "rule_setting":
//                // 背景
//                backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.night);
//                canvas.drawBitmap(backgroundImg, null, backgroundRect, paint);
//                //戻る
//                canvas.drawBitmap(buttonImg, null, rectButton1,paint);
//                canvas.drawText("戻る", canvas.getWidth() / 4, button1H + height * 6 / 100, paint);
//                //推奨設定
//                canvas.drawBitmap(buttonImg,null,rectButton2, paint);
//                canvas.drawText("推奨設定", canvas.getWidth() / 4, button2H + height * 6 / 100, paint);
//                //議論時間
//                canvas.drawBitmap(buttonImg, null, rectButton3, paint);
//                String textTime = String.format("議論時間：%d分", 5);
//                canvas.drawText(textTime, canvas.getWidth() / 4, button3H + height * 6 / 100, paint);
//                //初日占い
//                canvas.drawBitmap(buttonImg, null, rectButton4, paint);
//                String textSeer = String.format("初日占い：%s", "あり");
//                canvas.drawText(textSeer, canvas.getWidth() / 4, button4H + height * 6/100, paint);
//                //役かけ
//                canvas.drawBitmap(buttonImg, null, rectButton5, paint);
//                String textLack = String.format("役かけ：%s","なし");
//                canvas.drawText(textLack, canvas.getWidth() / 4, button5H + height * 6/100,paint);
//                //連続ガード
//                canvas.drawBitmap(buttonImg,null,rectButton6,paint);
//                String textBodyguard = String.format("連続ガード：%s","あり");
//                canvas.drawText(textBodyguard, canvas.getWidth() / 4, button6H + height * 6/100,paint);
//
//                break;
//
//            case "role_setting":
//                backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.afternoon);
//                canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);
//                //戻る
//                canvas.drawBitmap(buttonImg, null, rectButton1,paint);
//                canvas.drawText("戻る", canvas.getWidth() / 4, button1H + height * 6 / 100, paint);
//                //推奨設定
//                canvas.drawBitmap(buttonImg,null,rectButton2, paint);
//                canvas.drawText("推奨設定", canvas.getWidth() / 4, button2H + height * 6 / 100, paint);
//                //プレイヤー数表示
//                playerVolume = String.format("プレイヤー数：%d人",8);
//                paint.setColor(Color.WHITE);
//                canvas.drawText(playerVolume, width * 20 / 100, height * 10 / 100 ,paint);
//
//                break;
//
//            case "player_setting":
//                // 背景
//                backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.night);
//                canvas.drawBitmap(backgroundImg, null, backgroundRect, paint);
//                //戻る
//                canvas.drawBitmap(buttonImg, null, rectButton1,paint);
//                canvas.drawText("戻る", canvas.getWidth() / 4, button1H + height * 6 / 100, paint);
//                //シャッフル
//                canvas.drawBitmap(buttonImg,null,rectButton2, paint);
//                canvas.drawText("シャッフル", canvas.getWidth() / 4, button2H + height * 6 / 100, paint);
//                break;
//
//            case "explain":
//                backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.afternoon);
//                canvas.drawBitmap(backgroundImg,null,backgroundRect,paint);
//                //戻る
//                canvas.drawBitmap(buttonImg, null, rectButton1,paint);
//                canvas.drawText("戻る", canvas.getWidth() / 4, button1H + height * 6 / 100, paint);
//                break;
//
//            case "game_scene":
//                textSize = 30;
//                switch (phase){
//                    case "player_setting":
//                        playerSize = 0;
//
//                        break;
//                    case "role_setting":
//                        int villagerVolume = GameScene.roleArray.get(0);
//                        int werewolfVolume = GameScene.roleArray.get(1);
//                        int SeerVolume = GameScene.roleArray.get(2);
//                        int mediumVolume = GameScene.roleArray.get(3);
//                        int minionVolume = GameScene.roleArray.get(4);
//                        int bodyguardVolume = GameScene.roleArray.get(5);
//
//                        text = String.format("プレイヤー数は%d人です。\n" + "村人：%d人\n" + "人狼：%d人\n" +"予言者：%d人\n" + "霊媒師：%d人\n" + "狂人：%d人\n" +"狩人：%d人\n",playerSize,villagerVolume,werewolfVolume,SeerVolume,mediumVolume,minionVolume,bodyguardVolume);
//                        break;
//                    case "night_opening" :
//                        text = String.format("%d日目の夜になりました。「%s」さんから端末を回してそれぞれ行動を決定してください。それ以外のプレイヤーは目を閉じて顔を伏せてください。",day,GameScene.playerArray.get(nowPlayer).get("name"));
//                        GameScene.drawListView(false);
//                        break;
//                    case "night_playerCheck":
//                        text = String.format("あなたは「%s」さんですね？",GameScene.playerArray.get(nowPlayer).get("name"));
//                        break;
//                    case "night_roleRotate":
//                        text = String.format("あなたの役職は「%s」です。", (String) Utility.getRoleInfo((Utility.Role) GameScene.playerArray.get(nowPlayer).get("roleId")).get("name"));
//                        textSize = 50;
//                        break;
//                    case "night_roleCheck":
//                        text = String.format("あなたの役職は「%s」です。%s", (String) Utility.getRoleInfo((Utility.Role) GameScene.playerArray.get(nowPlayer).get("roleId")).get("name"),(String) Utility.getRoleInfo((Utility.Role) GameScene.playerArray.get(nowPlayer).get("roleId")).get("explain"));
//                        if(isFirstNight) {
//                            if((Boolean)Utility.getRoleInfo((Utility.Role) GameScene.playerArray.get(nowPlayer).get("roleId")).get("hasTableFirst")){
//                                if(GameScene.playerArray.get(nowPlayer).get("roleId") == Utility.Role.Werewolf){
//                                    GameScene.setListAdapter(1);
//                                }else if(GameScene.playerArray.get(nowPlayer).get("roleId") == Utility.Role.Seer){
//                                    GameScene.setListAdapter(2);
//                                }
//                                GameScene.drawListView(true);
//                            }else{
//                            }
//
//                        }else{
//                            if((Boolean)Utility.getRoleInfo((Utility.Role) GameScene.playerArray.get(nowPlayer).get("roleId")).get("hasTable")){
//                                if(GameScene.playerArray.get(nowPlayer).get("roleId") == Utility.Role.Werewolf){
//                                    GameScene.setListAdapter(1);
//                                }else if(GameScene.playerArray.get(nowPlayer).get("roleId") == Utility.Role.Seer){
//                                    GameScene.setListAdapter(2);
//                                }else if(GameScene.playerArray.get(nowPlayer).get("roleId") == Utility.Role.Bodyguard){
//                                    GameScene.setListAdapter(3);
//                                }
//                                GameScene.drawListView(true);
//                            }else if(GameScene.playerArray.get(nowPlayer).get("roleId") == Utility.Role.Medium){
//                                String result = "人間";
//                                if((int)Utility.getRoleInfo((Utility.Role) GameScene.playerArray.get(mediumId).get("roleId")).get("mediumResult") == -1){
//                                    result = "人狼";
//                                }
//                                text = String.format("あなたは霊媒師です。昼に処刑された「%s」さんは＜%s＞でした。",(String)GameScene.playerArray.get(mediumId).get("name"),result);
//                            }
//                        }
//                        break;
//                    case "Seer":
//                        String result = "人間";
//                        if((int)Utility.getRoleInfo((Utility.Role) GameScene.playerArray.get(selectedPlayerId).get("roleId")).get("seerResult") == -1){
//                            result = "人狼";
//                        }
//                        text = String.format("「%s」さんは＜%s＞です",(String)GameScene.playerArray.get(selectedPlayerId).get("name"),result);
//                        break;
//                    case "nextPlayer":
//                        text = "次のプレイヤーに端末を渡してください。";
//                        break;
//                    case "afternoon_opening":
//                        backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.afternoon);
//                        if(GameScene.victimArray.size() == 0){
//                            text = String.format("%d日目の朝になりました。昨晩の犠牲者はいませんでした。",day);
//                        }else {
//                            text = String.format("%d日目の朝になりました。昨晩の犠牲者は、「%s」さんでした。", day, (String) GameScene.playerArray.get(GameScene.victimArray.get(0)).get("name"));
//                        }
//                        break;
//                    case "afternoon_opening2":
//                        backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.afternoon);
//                        text= "人狼を見つけるための会議を始めてください。議論の時間は5分です。";
//                        break;
//                    case "afternoon_meeting":
//                        backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.afternoon);
//                        // TODO タイマー実装時変更
//                        text = "議論時間中";
//                        break;
//                    case "afternoon_voting":
//                        backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.evening);
//                        text = "日が暮れて今日も1人処刑者を決めなければなりません。投票を行い、処刑者を決定してください。同票の場合は決選投票を行い、3回続けても変更がない場合は引き分けとなります。";
//                        GameScene.drawListView(true);
//                        break;
//                    case "excution":
//                        backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.evening);
//                        text = String.format("「%s」さんは村の総意で処刑されました。",(String)GameScene.playerArray.get(selectedPlayerId).get("name"));
//                        GameScene.drawListView(false);
//                        break;
//                    case "gameover":
//                        backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.evening);
//                        text = "この時点でゲームが終了しました。勝利者は、、、";
//                        break;
//                    case "winner":
//                        int winId = GameScene.isFinish();
//                        String message = "";
//                        if(winId == 1){
//                            message = "村人チームの勝利です！";
//                            backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.afternoon);
//                        }else if(winId == -1){
//                            message = "人狼チームの勝利です！";
//                            backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.night);
//                        }
//                        text = message;
//                        textSize = 50;
//                        break;
//                    case "ending":
//                        text = "お疲れさまでした。役職を確認してください";
//                        backgroundImg = BitmapFactory.decodeResource(getResources(), R.drawable.night);
//                        break;
//                    default:
//                        text = "error";
//                        break;
//                }
//                if(phase.equals("player_setting")){
//                    canvas.drawBitmap(backgroundImg, null, new Rect(0, 0, width, height), paint);
//                    // addButton
//                    addButton = new Rect(width / 10,height*15/100,width*9/10,height*25/100);
//                    canvas.drawBitmap(buttonImg, null, addButton, paint);
//                    String addButton = "プレイヤー追加";
//                    paint.setTextSize(50);
//                    canvas.drawText(addButton,width*25/100,height*22/100,paint);
//
//
//                    // startButton
//                    startButton = new Rect(width / 10,height*3/10,width*9/10,height*4/10);
//                    canvas.drawBitmap(buttonImg, null, startButton, paint);
//                    String startButton = "スタート";
//                    canvas.drawText(startButton,width*35/100,height*37/100,paint);
//
//                    playerSize = GameScene.listInfoDicArray.size();
//                    String playersize = String.format("プレイヤー数：%d人",playerSize);
//                    paint.setColor(Color.WHITE);
//                    canvas.drawText(playersize,width*2/10,height*10/100,paint);
//
//
//                }else{
//                    canvas.drawBitmap(backgroundImg, null, new Rect(0, 0, width, height), paint);
//                    canvas.drawBitmap(frameImg, null, new Rect(width / 10, height * 2 / 10, width * 9 / 10, height * 5 / 10), paint);//width 1/10~9/10,height 2/10~5/10
//
//                    buttonrect = new Rect(width / 10, height * 8 / 10, width * 9 / 10, height * 9 / 10);
//                    canvas.drawBitmap(buttonImg, null,buttonrect, paint);
//
//                    String button = "次へ";
//                    paint.setTextSize(50);
//                    canvas.drawText(button,width*4/10,height*87/100,paint);
//
//                    TextPaint mTextPaint = new TextPaint();
//                    mTextPaint.setTextSize(textSize);
//                    StaticLayout mTextLayout = new StaticLayout(text,mTextPaint,width*3/5, Layout.Alignment.ALIGN_NORMAL,1.0f,0.0f,false);
//                    canvas.translate(width * 2 / 10, height * 25 / 100);//text の左上座標の指定
//
//                    mTextLayout.draw(canvas);
//                    canvas.restore();
//                }
//
//                break;
//
//            default:
//                break;
//        }
//
//
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        float pointX = event.getX();
//        float pointY = event.getY();
//
//        switch (event.getAction()) {
//
//            case MotionEvent.ACTION_DOWN:
//                switch (scene){
//                    case "setting_scene":
//                        if(rectButton1.contains((int)pointX,(int)pointY)){
//                            SettingScene.scene = "game_scene";
//                            phase = "player_setting";
//                            // GameScene.javaをよびだす
//                            Log.d("start","start+=");
//                        }else if(rectButton2.contains((int)pointX,(int)pointY)){
//                            SettingScene.scene = "rule_setting";
//                        }else if(rectButton3.contains((int)pointX,(int)pointY)){
//                            SettingScene.scene = "role_setting";
//                        }else if(rectButton4.contains((int)pointX,(int)pointY)){
//                            SettingScene.scene = "player_setting";
//                        }else if(rectButton5.contains((int)pointX,(int)pointY)){
//                            SettingScene.scene = "explain";
//                        }
//                        break;
//
//                    case "rule_setting":
//                        if(rectButton1.contains((int)pointX,(int)pointY)){
//                            //戻る
//                            scene = "setting_scene";
//                        }else if(rectButton2.contains((int)pointX,(int)pointY)){
//                            //推奨設定
//                            //TODO 役職変数作ったあとにデフォルト設定決める
//                            /*
//                            * 議論時間5分
//                            * 初日占いあり
//                            * 役かけなし
//                            * 連続ガードあり*/
//                        }else if(rectButton3.contains((int)pointX,(int)pointY)){
//                            //議論時間
////                            scene = "role_setting";
//                        }else if(rectButton4.contains((int)pointX,(int)pointY)){
//                            //初日占い
////                            scene = "player_setting";
//                        }else if(rectButton5.contains((int)pointX,(int)pointY)){
//                            //役かけ
////                            scene = "explain";
//                        }else if(rectButton6.contains((int)pointX,(int)pointY)){
//                            //連続ガード
////                            scene = "";
//                        }
//                        break;
//
//                    case "role_setting":
//                        break;
//
//                    case "player_setting":
//                        break;
//
//                    case "explain":
//                        break;
//
//                    case "game_scene":
//                        if(!(phase.equals("player_setting")) && buttonrect.contains((int) pointX, (int) pointY)) {//TODO or文で書き直す
//                            GameScene.goNextPhase();
//                        }else if(addButton.contains((int) pointX, (int) pointY)){
//                            //TODO 参加者を消す処理
//                            String text = GameScene.editText.getText().toString();
//                            if(!(text.equals(""))){
//                                GameScene.prePlayerList.add(text);
//                            }
//
//                            GameScene.listInfoDicArray.clear();
//
//                            for (int i = 0; i < GameScene.prePlayerList.size(); i++) {
////                        if ((boolean) playerArray.get(i).get("isLive") == true) {
//////                    String name = (String) playerArray.get(i).get("name");
//////                    names.add(name);
////                            GameScene.listPlayerArray.add(i);
//
//                                Map<String,String> conMap = new HashMap<>();
//                                conMap.put("name",GameScene.prePlayerList.get(i));
//                                conMap.put("listSecondInfo","");
//                                GameScene.listInfoDicArray.add(conMap);
////                        }
//                            }
//
////                    Map<String ,String> map = new HashMap<>();
////                    map.put("name",text);
////                    map.put("listSecondInfo","");
////                    GameScene.listInfoDicArray.add(map);
//
//                            GameScene.listView.invalidateViews();
//                            // 中身クリア
//                            GameScene.editText.getEditableText().clear();
//                        }else if(startButton.contains((int) pointX, (int) pointY)){
//                            Log.d("start", "start=");
//                            GameScene.editText.setVisibility(View.INVISIBLE);
//                            GameScene.goNextPhase();
//                            Log.d("startfin", "startfin=");
//                        }
//                        break;
//
//                    default:
//                        break;
//                }
//
//
//                sendTextToLine(SettingScene.this, "こちらのコメントはLINEへ投稿しました！");
//                scene = "game_scene";
//                Intent intent = new Intent(SettingScene.this,GameScene.class);
//                startActivity(intent);
//                Log.d("break","break=");
//                break;
//
//
//            default:
//                return true;
//        }
//        invalidate();
//        return true;
//    }
}

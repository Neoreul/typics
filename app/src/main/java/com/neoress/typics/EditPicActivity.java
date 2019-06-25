package com.neoress.typics;

import android.accounts.Account;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import static com.neoress.typics.MainActivity.mLastPhotoUri;

public class EditPicActivity extends AppCompatActivity {

    static final int REQUEST_SEND_TO = 3;
    static final int REQUEST_IMAGE_HIDDEN = 5;
    public static final String USERUID = "UUID";

    Vector<Uri> tracer = new Vector<>();
    Vector<String> history = new Vector<>();
    ArrayAdapter<String> adapter;

    //event when click screen
    boolean paintClick = false;
    boolean screenClick = false;
    public static boolean onMerge = false;
    public static boolean onSave = false;

    //add text
    private int id = -1; // color
    public static int edt_id = 0;
    public static int cur_edt_id = 0;
    public static TextView[] txt_addText;
    static int keyDrag = 0;

    int rotate = 0, size = 20, magic = 10;
    Map<Uri, Integer> curMagic = new HashMap<>();
    int curPos = 0;

    //navigation drawer
    private String[] mNavTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_edit_pic);

        adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, history);

        //save history(new)
        useForHistory(getString(R.string.new_history));
        showImgView(mLastPhotoUri);

        //navigation drawer
        mNavTitles = getResources().getStringArray(R.array.navigation_drawer);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerList = findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mNavTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        switch (position)
        {
            //home
            case 0:
                Intent intent = new Intent(EditPicActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            //market
            case 1:

                break;
            //account
            case 2:

                break;
            //About
            case 3:
                AboutUs fm = new AboutUs();

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fl_AboutUs, fm)
                        .commit();
                break;
        }

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mNavTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    private void Exit(){
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if(onSave) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Confirm");
            builder.setMessage("Do you want to save changes?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    saveMedia();
                    dialog.dismiss();
                    Exit();
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing
                    dialog.dismiss();
                    Exit();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }
        else{

            Exit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_HIDDEN && resultCode == RESULT_OK) {
            mLastPhotoUri = data.getData();
            useForHistory(getString(R.string.openHide));
            showImgView(mLastPhotoUri);
        }
    }

    Spinner spinner;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        //MenuItem item = menu.findItem(R.id.spinner_history);
        //spinner = (Spinner) MenuItemCompat.getActionView(item);
        // Specify the layout to use when the list of choices appears
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(adapter); // set the adapter to provide layout of rows and content
        //history.add("example.hehehhe");
        Log.i("spinner", "data: " + history.toString());
        //adapter.notifyDataSetChanged();
        //spinner.setOnItemSelectedListener(this);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        MenuItem item = menu.findItem(R.id.upload_setting);
        if (mLastPhotoUri == null)
        {
            item.setEnabled(false);
        }
        else
            item.setEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.save_setting:
                saveMedia();
                onSave = false;
                break;

            case R.id.share_setting:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, mLastPhotoUri);
                shareIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(shareIntent,
                        getResources().getText(R.string.send_to)), REQUEST_SEND_TO);
                break;

            case R.id.search_setting:

                break;
            case R.id.hide_setting:
                if (mLastPhotoUri!=null){
                    File dir = new File(MainActivity.file,".Typics");
                    if (!dir.exists())
                        dir.mkdir();
                    saveDirectory(MainActivity.file);
                }
                break;
            case R.id.openHidden:  // not working
                /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                        + "/.NotePic/");
                intent.setDataAndType(uri, "resource/folder");
                startActivity(Intent.createChooser(intent, "Open folder"));*/

                Uri selectedUri = Uri.parse(MainActivity.file + "/.Typics"); // truoc kia /.Typics
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(selectedUri, "resource/folder");

                if (intent.resolveActivityInfo(getPackageManager(), 0) != null)
                {
                    startActivityForResult(intent, REQUEST_IMAGE_HIDDEN);
                }
                else
                {
                    // if you reach this place, it means there is no any file
                    // explorer app installed on your device
                    Toast.makeText(this, "Please install file explorer to use this function", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.upload_setting:
                break;
            case R.id.market:
                break;

            case R.id.logout:
                break;


            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private static String uuidString() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }

        return result;


        /*Cursor returnCursor =
                getContentResolver().query(uri, null, null, null, null);
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        return returnCursor.getString(nameIndex);*/
    }

    private void saveMedia(){
        mergeImages();
        ImageView img = findViewById(R.id.img_View);
        Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Typics");
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "Typics");
        values.put(MediaStore.Images.Media.DESCRIPTION, "make by Typics");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        // Add the date meta data to ensure the image is added at the front of the gallery
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());

        Uri url = null;
        ContentResolver cr = this.getContentResolver();

        try {
            url = cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (bitmap != null) {
                OutputStream imageOut = cr.openOutputStream(url);
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, imageOut);
                } finally {
                    imageOut.close();
                }
            }
            else {
                cr.delete(url, null, null);
                url = null;
            }
        } catch (Exception e) {
            if (url != null) {
                cr.delete(url, null, null);
                url = null;
            }
        }
    }

    private String getNewImageName(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TP_" + timeStamp + "_";
        return imageFileName;
    }

    protected void saveDirectory(File dir)
    {
        Bitmap bm;

        try {
            bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mLastPhotoUri);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Can't get bitmap from uri", Toast.LENGTH_SHORT).show();
            return;
        }
        //Lay ten image
        String imageName = getNewImageName()+".png";
        File currImageDir;
        currImageDir = new File(dir, imageName);
        FileOutputStream fout;

        try {
            fout=new FileOutputStream(currImageDir);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Can't save Image", Toast.LENGTH_SHORT).show();
            return;
        }
        bm.compress(Bitmap.CompressFormat.PNG, 0, fout);

        try{
            fout.flush();
            fout.close();
        }
        catch(IOException e){
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dir == MainActivity.tempFile){
            mLastPhotoUri = Uri.fromFile(currImageDir);
        }
        //Toast.makeText(this, "Saved " + currImageDir.getAbsolutePath(), Toast.LENGTH_LONG).show();
    }

    public void useForHistory(String str){
        history.add(0, str);
        adapter.notifyDataSetChanged();
        if (history.size() >= 10)
        {
            history.remove(history.size() - 1);
            tracer.remove(tracer.size() - 1);
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);

        parcelFileDescriptor.close();

        return image;
    }

    public void showImgView(Uri t, int ...check)
    {
        if (check.length == 0){
            tracer.add(0, mLastPhotoUri);
        }
        try {
            Bitmap bm = getBitmapFromUri(t);
            ImageView img = (ImageView)findViewById(R.id.img_View);
            img.setImageBitmap(bm);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getImageUri(File dir) {

        ImageView img = (ImageView)findViewById(R.id.img_View);
        Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();

        String imageName = getNewImageName()+".png";
        File currImageDir;
        currImageDir = new File(dir, imageName);
        FileOutputStream fout;

        try {
            fout = new FileOutputStream(currImageDir);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Can't save Image", Toast.LENGTH_SHORT).show();
            return;
        }
        //format PNG, 0 -> no black background
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, fout);

        try{
            fout.flush();
            fout.close();
        }
        catch(IOException e){
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dir == MainActivity.tempFile){
            mLastPhotoUri = Uri.fromFile(currImageDir);
        }
    }

    private Boolean mergeImages() {

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.rl_image);
        ImageView img = (ImageView)findViewById(R.id.img_View);

        rl.clearFocus();
        rl.setPressed(false);

        boolean oldWillNotCacheDrawing = rl.willNotCacheDrawing();
        rl.setWillNotCacheDrawing(false);

        int color = rl.getDrawingCacheBackgroundColor();
        rl.setDrawingCacheBackgroundColor(0);
        if(color != 0){
            rl.destroyDrawingCache();
        }

        rl.buildDrawingCache();

        Bitmap cacheBitmap = rl.getDrawingCache();
        if(cacheBitmap == null)
            return false;

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        img.setImageBitmap(bitmap);

        rl.destroyDrawingCache();
        rl.setWillNotCacheDrawing(oldWillNotCacheDrawing);
        rl.setDrawingCacheBackgroundColor(color);

        //hide text view
        for(int i = 0; i < edt_id; i++){
            txt_addText[i].setVisibility(View.GONE);
            rl.removeViewInLayout(txt_addText[i]);
        }
        edt_id = 0;

        return true;
    }

    public void merge(){

        if (mLastPhotoUri==null)
            return;

        /*Bitmap temp;
        try {
            temp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mLastPhotoUri);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Can't merge", Toast.LENGTH_SHORT).show();
            return;
        }*/
        if(onMerge){
            if(!mergeImages()){
                Toast.makeText(this, "Can't add text", Toast.LENGTH_SHORT).show();
                return;
            }
            getImageUri(MainActivity.tempFile);

            useForHistory(getString(R.string.merge));
            showImgView(mLastPhotoUri);
            onMerge = false;
        }
    }

    /*---------------------- event at activity_edit_pic ----------------------*/
    public void screenOnClick(View v){

        LinearLayout ll = (LinearLayout)findViewById(R.id.ll_listButtonAbove);
        TableLayout tbl = (TableLayout)findViewById(R.id.tbl_edit_text);
        if(!screenClick){
            ll.setVisibility(v.INVISIBLE);
            if(paintClick){
                ImageView imgPaint = (ImageView) findViewById(R.id.imgPaint);
                //ImageView imgCompare = (ImageView)findViewById(R.id.imgCompare);
                float yp = imgPaint.getY();
                imgPaint.setY(yp + tbl.getHeight());
                //float yc = imgCompare.getY();
                //imgCompare.setY(yc + tbl.getHeight());
                paintClick = false;
            }
            //hide HorizontalScrollView
            tbl.setVisibility(v.INVISIBLE);
            //hide icon Paint
            findViewById(R.id.imgPaint).setVisibility(v.INVISIBLE);

            //findViewById(R.id.imgCompare).setVisibility(v.INVISIBLE);

            TableLayout[] table = new TableLayout[3];

            table[0] = (TableLayout)findViewById(R.id.tblSize);
            table[1] = (TableLayout)findViewById(R.id.tblStyle);
            table[2] = (TableLayout)findViewById(R.id.tblRotate);
            for(int i = 0; i < 3; i++){
                if(i == id)
                    table[i].setVisibility(View.INVISIBLE);
            }

            screenClick = true;
        }
        else{

            ll.setVisibility(v.VISIBLE);
            //findViewById(R.id.imgCompare).setVisibility(v.VISIBLE);
            screenClick = false;
        }
    }

    public void save(View v){

        if (mLastPhotoUri!=null)
        {
            if(onMerge){
                merge();
                saveDirectory(MainActivity.file);
                onMerge = false;
            }
        }
        else
            Toast.makeText(this, "Error in saving image", Toast.LENGTH_SHORT).show();
    }

    public void crop(View v){

    }

    static float coordX, coordY;
    boolean initdrap = false;
    public void addText(View v)
    {
        RelativeLayout rl =  findViewById(R.id.rl_image);
        TextView text = new TextView(this);
        //create new button to verify if user finish their text
        RelativeLayout.LayoutParams mRparams = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mRparams.addRule(RelativeLayout.CENTER_IN_PARENT);

        text.setLayoutParams(mRparams);
        text.setGravity(Gravity.CENTER);
        size = 20;
        text.setTextSize(size);//------------------------------------------------
        text.setText(R.string.same_text, TextView.BufferType.SPANNABLE);
        text.setTextColor(Color.parseColor("#ffffff"));
        text.setPadding(10, 10, 20, 20);
        text.setId(edt_id);

        /*Typeface myFace = Typeface.createFromAsset(getAssets(), "fonts/28_days later.ttf");
        text.setTypeface(myFace);*/

        if(Build.VERSION.SDK_INT >= 17){
            text.setTextAlignment(v.TEXT_ALIGNMENT_CENTER);
        }

        TextView[] textTemp;
        boolean edit_ok;
        if(edt_id > 0){
            if(txt_addText[edt_id - 1].length() > 0)
            {
                textTemp = new TextView[edt_id];
                for(int i = 0; i < edt_id; i++){
                    textTemp[i] = txt_addText[i];
                }
                edt_id++;
                txt_addText = new TextView[edt_id];
                for(int i = 0; i < edt_id - 1; i++){
                    txt_addText[i] = textTemp[i];
                }
                edit_ok = true;
            }
            else{
                if(edt_id > 0){
                    edt_id--;
                }
                edit_ok = false;
            }
        }
        else{
            edt_id++;
            txt_addText = new TextView[edt_id];
            edit_ok = true;
        }

        if(edit_ok){
            txt_addText[edt_id - 1] = text;
            rl.addView(txt_addText[edt_id - 1]);
            cur_edt_id = edt_id - 1;
        }
        DialogText dialogText = new DialogText(this);
        dialogText.show();

        if(edit_ok){
            txt_addText[edt_id - 1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    keyDrag = 1;

                    findViewById(R.id.imgPaint).setVisibility(v.VISIBLE);
                    LinearLayout ll = findViewById(R.id.ll_listButtonAbove);
                    ll.setVisibility(v.VISIBLE);
                    cur_edt_id = v.getId();

                    //merge and save history

                }
            });

            txt_addText[edt_id - 1].setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View view) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                            view);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        view.startDragAndDrop(data, shadowBuilder, view, 0);
                    else
                        view.startDrag(data, shadowBuilder, view, 0);
                    view.setVisibility(View.INVISIBLE);

                    cur_edt_id = view.getId();
                    keyDrag = 1;
                    return true;
                }
            });

            //just parent can onDragListener
            rl.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View v, DragEvent event) {

                    if(event.getX() != 0 && event.getY() != 0){
                        coordX = event.getX();
                        coordY = event.getY();
                    }
                    else if (event.getAction() == DragEvent.ACTION_DRAG_ENDED)
                    {
                        int widText, heiText;
                        widText = txt_addText[cur_edt_id].getWidth() / 2;
                        heiText = txt_addText[cur_edt_id].getHeight() / 2;
                        txt_addText[cur_edt_id].setX(coordX - widText);
                        txt_addText[cur_edt_id].setY(coordY - heiText);
                        txt_addText[cur_edt_id].setVisibility(View.VISIBLE);

                        //merge and save history

                        initdrap = false;
                    }
                    return true;
                }
            });

        }
    }

    public void paintClick(View v){

        TableLayout tbl = findViewById(R.id.tbl_edit_text);
        ImageView imgPaint = findViewById(R.id.imgPaint);
        if(!paintClick){
            tbl.setVisibility(v.VISIBLE);
            float yp = imgPaint.getY();
            imgPaint.setY(yp - tbl.getHeight());
            paintClick = true;
        }
        else{
            tbl.setVisibility(v.INVISIBLE);
            float yp = imgPaint.getY();
            imgPaint.setY(yp + tbl.getHeight());
            paintClick = false;
        }
    }

    private void setPage(){
        TableLayout[] table = new TableLayout[3];

        table[0] = findViewById(R.id.tblSize);
        table[1] = findViewById(R.id.tblStyle);
        table[2] = findViewById(R.id.tblRotate);
        for(int i = 0; i < 3; i++){
            if(i == id)
                table[i].setVisibility(View.VISIBLE);
            else table[i].setVisibility(View.INVISIBLE);
        }
    }

    public void chooseText(View v){
        id = -1;
        setPage();
        DialogText dialogText = new DialogText(this);
        dialogText.show();
    }
    public void chooseColor(View v){
        //set id
        id = -1;
        setPage();
        DialogColor dialogColor = new DialogColor(EditPicActivity.this);
        dialogColor.show();

    }

    public void chooseFont(View v){
        id = -1;

        setPage();
        DialogFont dialogFont = new DialogFont(EditPicActivity.this);
        dialogFont.show();
    }
    public void chooseSize(final View v){
        id = 0;
        setPage();
        SeekBar seek = findViewById(R.id.seekBarSize);
        final TextView txtSize = findViewById(R.id.txtSize);
        seek.setProgress(size);
        txtSize.setText(Integer.toString(size));

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                //hide editView, hien textview thay the
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
                size = progress;

                txt_addText[cur_edt_id].setTextSize(size);

                txtSize.setText(Integer.toString(size));
            }
        });
    }

    public void chooseStyle(View v){
        id = 1;
        setPage();
    }

    static boolean bold = false;
    static boolean italic = false;
    public void textBold(View v){
        Typeface curTF = txt_addText[cur_edt_id].getTypeface();
        Typeface resultTF;
        if(!bold){
            bold = true;
            if(italic)
                resultTF = Typeface.create(curTF, Typeface.BOLD_ITALIC);
            else
                resultTF = Typeface.create(curTF, Typeface.BOLD);
        }
        else{
            bold = false;
            if(italic)
                resultTF = Typeface.create(curTF, Typeface.ITALIC);
            else
                resultTF = Typeface.create(curTF, Typeface.NORMAL);
        }
        txt_addText[cur_edt_id].setTypeface(resultTF);
    }

    public void textItalic(View v){
        Typeface curTF = txt_addText[cur_edt_id].getTypeface();
        Typeface resultTF;
        if(!italic){
            italic = true;
            if(bold)
                resultTF = Typeface.create(curTF, Typeface.BOLD_ITALIC);
            else
                resultTF = Typeface.create(curTF, Typeface.ITALIC);
        }
        else{
            italic = false;
            if(bold)
                resultTF = Typeface.create(curTF, Typeface.BOLD);
            else
                resultTF = Typeface.create(curTF, Typeface.NORMAL);
        }
        txt_addText[cur_edt_id].setTypeface(resultTF);
    }

    public void textNormal(View v){
        bold = false;
        italic = false;
        Typeface curTF = txt_addText[cur_edt_id].getTypeface();
        Typeface resultTF = Typeface.create(curTF, Typeface.NORMAL);
        txt_addText[cur_edt_id].setTypeface(resultTF);
    }

    public void chooseSpacing(View v){
        id = -1;
        setPage();
        DialogSpacing dialogSpacing = new DialogSpacing(EditPicActivity.this);
        dialogSpacing.show();
    }

    public void chooseBackground(View v){
        id = -1;
        setPage();
        DialogBackground dialogBackground = new DialogBackground(EditPicActivity.this);
        dialogBackground.show();
    }

    public void chooseRotate(View v){
        id = 2;
        setPage();
        SeekBar seek = findViewById(R.id.seekBarRotate);
        final TextView txtSize = findViewById(R.id.txtRotate);

        seek.setProgress(rotate + 180);
        txtSize.setText(Integer.toString(rotate));

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                rotate = progress - 180;
                TextView txtView = findViewById(R.id.txtRotate);
                txtView.setText(Integer.toString(rotate));
                txt_addText[cur_edt_id].setRotation(rotate);
            }
        });
    }

    public void chooseShadow(View v){
        id = -1;
        setPage();
        DialogShadow dialogShadow = new DialogShadow(EditPicActivity.this);
        dialogShadow.show();
    }

    public void chooseExample(View v){
        id = -1;
        setPage();
        Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show();
    }

    /*---------- magic ------------*/
    ImageView img_bl_wh;
    Bitmap bitmap_before_magic;  // when not change

    public void magic(View v) {
        //get bitmap
        ImageView img = findViewById(R.id.img_View);
        bitmap_before_magic = ((BitmapDrawable) img.getDrawable()).getBitmap();

        setContentView(R.layout.activity_filter_bl_wh);
        img_bl_wh =findViewById(R.id.img_bl_wh);

        img_bl_wh.setImageBitmap(bitmap_before_magic);

        SeekBar seek = findViewById(R.id.seekBarMagic);
        seek.setVisibility(View.VISIBLE);

        seek.setProgress(magic);
        setFilter(img_bl_wh);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
                magic = progress;
                setFilter(img_bl_wh);
            }
        });
    }

    private void setFilter(ImageView imgView){
        float[] colorMatrix = {
                0.33f, 0.33f, 0.33f, 0, magic,
                0.33f, 0.33f, 0.33f, 0, magic,
                0.33f, 0.33f, 0.33f, 0, magic,
                0, 0, 0, 1, 0
        };

        ColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        imgView.setColorFilter(colorFilter);
    }

    public void checked(View v){

        onSave = true;

        ImageView img_bw = findViewById(R.id.img_bl_wh);
        Bitmap bitmap = ((BitmapDrawable) img_bw.getDrawable()).getBitmap();

        setContentView(R.layout.activity_edit_pic);

        ImageView img = findViewById(R.id.img_View);
        img.setImageBitmap(bitmap);
        setFilter(img);

        getImageUri(MainActivity.tempFile);
        curMagic.put(mLastPhotoUri, magic);


        useForHistory(getString(R.string.b_and_w));
        showImgView(mLastPhotoUri);
    }

    public void back(View v){
        setContentView(R.layout.activity_edit_pic);
        ImageView img = (ImageView)findViewById(R.id.img_View);

        img.setImageBitmap(bitmap_before_magic);

        String s = getString(R.string.b_and_w);
        if(history.get(curPos) == s){
            magic = curMagic.get(mLastPhotoUri);
            setFilter(img);
        }
        getImageUri(MainActivity.tempFile);
        showImgView(mLastPhotoUri);
    }

    public void imageOnClick(View v){
        RelativeLayout rl = findViewById(R.id.rl_group);
        if(rl.getVisibility() == v.INVISIBLE){
            rl.setVisibility(v.VISIBLE);
        }
        else{
            rl.setVisibility(v.INVISIBLE);
        }
    }
}

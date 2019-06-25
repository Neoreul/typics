package com.neoress.typics;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static com.neoress.typics.EditPicActivity.cur_edt_id;
import static com.neoress.typics.EditPicActivity.txt_addText;

public class DialogFont extends Dialog implements View.OnClickListener {
    public Activity c;
    public Button done, cancel;
    TextView textView;
    ScrollView listView;
    String fileList[] = null;

    public DialogFont(Activity a){
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_font);

        done = findViewById(R.id.btnFontDone);
        cancel = findViewById(R.id.btnFontCancel);
        textView = findViewById(R.id.textFontView);
        listView = findViewById(R.id.listViewFont);
        done.setOnClickListener(this);
        cancel.setOnClickListener(this);


        textView.setText(txt_addText[cur_edt_id].getText().toString());
        textView.setTextColor(txt_addText[cur_edt_id].getTextColors());
        textView.setTypeface(txt_addText[cur_edt_id].getTypeface());
        if(Build.VERSION.SDK_INT >= 17){
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

        Resources res = c.getResources();
        AssetManager am = res.getAssets();

        try {
            fileList = am.list("fonts");
            if (fileList != null)
            {
                LinearLayout ll  = (LinearLayout)findViewById(R.id.llScroll);

                for ( int i = 0;i<fileList.length;i++)
                {
                    final TextView font = new TextView(getContext());
                    LinearLayout.LayoutParams mRparams = new LinearLayout.LayoutParams
                            (LinearLayout.LayoutParams.MATCH_PARENT, 150);
                    mRparams.setMargins(10, 0, 0, 0);
                    font.setLayoutParams(mRparams);

                    font.setBackgroundResource(R.drawable.text_font_theme);

                    String str = fileList[i];
                    String fontFamily = str;
                    int firstPoint = str.indexOf(".");
                    if(firstPoint != -1){
                        fontFamily = str.substring(0, firstPoint);
                    }
                    font.setText(fontFamily);
                    font.setTextSize(18);
                    font.setTextColor(Color.WHITE);
                    Typeface custom_font = Typeface.createFromAsset(c.getAssets(), "fonts/" + fileList[i]);
                    font.setTypeface(custom_font);
                    font.setId(i);

                    ll.addView(font);

                    font.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String s = fileList[v.getId()];
                            Typeface custom_font = Typeface.createFromAsset(c.getAssets(), "fonts/" + s);
                            textView.setTypeface(custom_font);
                        }
                    });
                }
                listView.addView(ll);
            }
        }catch (Exception e){

        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnFontDone:
                txt_addText[cur_edt_id].setTypeface(textView.getTypeface());
                break;
            case R.id.btnFontCancel:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}

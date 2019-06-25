package com.neoress.typics;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import static com.neoress.typics.EditPicActivity.cur_edt_id;
import static com.neoress.typics.EditPicActivity.edt_id;
import static com.neoress.typics.EditPicActivity.onMerge;
import static com.neoress.typics.EditPicActivity.onSave;
import static com.neoress.typics.EditPicActivity.txt_addText;

/**
 * Created by Huy on 10/29/2016.
 */

public class DialogText extends Dialog implements View.OnClickListener{
    public Activity c;
    public Button done, cancel;
    EditText text;

    public DialogText(Activity a) {
        super(a);
        this.c = a;
    }
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_text);

        done = findViewById(R.id.btnTextDone);
        done.setOnClickListener(this);
        cancel = findViewById(R.id.btnTextCancel);
        cancel.setOnClickListener(this);

        text = findViewById(R.id.edtViewText);
        text.setText(txt_addText[cur_edt_id].getText());

        (new Handler()).postDelayed(new Runnable() {

            public void run() {
                text.requestFocus();
                text.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN , 0, 0, 0));
                text.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP , 0, 0, 0));
            }
        }, 200);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTextDone:
                if (text.getText().length() > 0) {
                    txt_addText[cur_edt_id].setText(text.getText().toString());
                    onMerge = true;
                    onSave = true;
                }
                else{
                    if(edt_id > 0){
                        edt_id--;
                    }
                }
                dismiss();
                break;
            case R.id.btnTextCancel:
                if(edt_id > 0){
                    edt_id--;
                }
                break;
            default:
                break;
        }
        dismiss();
    }
}

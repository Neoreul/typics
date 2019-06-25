package com.neoress.typics;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import static com.neoress.typics.EditPicActivity.cur_edt_id;
import static com.neoress.typics.EditPicActivity.txt_addText;

/**
 * Created by neoreul on 3/28/17.
 */

public class DialogBackground extends Dialog implements View.OnClickListener {
    public Activity c;
    public Button done, cancel;
    public static int[] background = new int[7];
    private int[] backgroundCur = new int[7];
    public static int textCurWid, textCurHei;
    TextView textView;
    TextView[] textBackground = new TextView[7];

    public DialogBackground(Activity a) {
        super(a);
        this.c = a;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_background);

        done = findViewById(R.id.btnBackgroundDone);
        cancel = findViewById(R.id.btnBackgroundCancel);
        textView = findViewById(R.id.textBackgroundView);
        done.setOnClickListener(this);
        cancel.setOnClickListener(this);

        textView.setText(txt_addText[cur_edt_id].getText().toString());
        textView.setTextColor(txt_addText[cur_edt_id].getTextColors());
        if(Build.VERSION.SDK_INT >= 17){
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

        setBackground();

        textCurWid = textView.getWidth();
        textCurHei = textView.getHeight();

        for(int i = 0; i < 7; i++)
            backgroundCur[i] = background[i];

        SeekBar seekR = findViewById(R.id.seekBarRSBackground);
        textBackground[0] = findViewById(R.id.txtRBackground);
        textBackground[0].setText(Integer.toString(background[0]));
        seekR.setProgress(background[0]);
        seekR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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
                background[0] = progress;
                textBackground[0].setText(Integer.toString(progress));
                setBackground();
            }
        });
        SeekBar seekG = findViewById(R.id.seekBarGBackground);
        textBackground[1] = findViewById(R.id.txtGBackground);
        textBackground[1].setText(Integer.toString(background[1]));
        seekG.setProgress(background[1]);
        seekG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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
                background[1] = progress;
                textBackground[1].setText(Integer.toString(progress));
                setBackground();
            }
        });
        SeekBar seekB = findViewById(R.id.seekBarBBackground);
        textBackground[2] = findViewById(R.id.txtBBackground);
        textBackground[2].setText(Integer.toString(background[2]));
        seekB.setProgress(background[2]);
        seekB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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
                background[2] = progress;
                textBackground[2].setText(Integer.toString(progress));
                setBackground();
            }
        });
        SeekBar seekAlpha = findViewById(R.id.seekBarAlphaBackground);
        textBackground[3] = findViewById(R.id.txtAlphaBackground);
        textBackground[3].setText(Integer.toString(background[3]));
        seekAlpha.setProgress(background[3]);
        seekAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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
                background[3] = progress;
                textBackground[3].setText(Integer.toString(progress));
                setBackground();
            }
        });
        //------------------------------------------
        SeekBar seekWid = findViewById(R.id.seekBarWidBackground);
        textBackground[4] = findViewById(R.id.txtWidBackground);
        textBackground[4].setText(Integer.toString(background[4]));
        seekWid.setProgress(background[4]);
        seekWid.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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
                background[4] = progress;
                textBackground[4].setText(Integer.toString(progress));
                setBackground();
            }
        });
        SeekBar seekHei = findViewById(R.id.seekBarHeiBackground);
        textBackground[5] = findViewById(R.id.txtHeiBackground);
        textBackground[5].setText(Integer.toString(background[5]));
        seekHei.setProgress(background[5]);
        seekHei.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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
                background[5] = progress;
                textBackground[5].setText(Integer.toString(progress));
                setBackground();
            }
        });
        SeekBar seekCor = findViewById(R.id.seekBarCorBackground);
        textBackground[6] = findViewById(R.id.txtCorBackground);
        textBackground[6].setText(Integer.toString(background[6]));
        seekCor.setProgress(background[6]);
        seekCor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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
                background[6] = progress;
                textBackground[6].setText(Integer.toString(progress));
                setBackground();
            }
        });
    }

    private void setBackground(){

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(background[6]);
        gradientDrawable.setColor(Color.argb(background[3], background[0], background[1], background[2]));
        gradientDrawable.setSize(background[4] + textCurWid, background[5] + textCurHei);
        if(Build.VERSION.SDK_INT >= 16)
            textView.setBackground(gradientDrawable);
        else textView.setBackgroundDrawable(gradientDrawable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBackgroundDone:
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setCornerRadius(background[6]);
                gradientDrawable.setColor(Color.argb(background[3], background[0], background[1], background[2]));
                gradientDrawable.setSize(background[4] + txt_addText[cur_edt_id].getWidth(),
                        background[5] + txt_addText[cur_edt_id].getHeight());
                if(Build.VERSION.SDK_INT >= 16)
                    txt_addText[cur_edt_id].setBackground(gradientDrawable);
                else txt_addText[cur_edt_id].setBackgroundDrawable(gradientDrawable);
                break;
            case R.id.btnBackgroundCancel:
                for(int i = 0; i < 7; i++){
                    background[i] = backgroundCur[i];
                }
                break;
            default:
                break;
        }
        dismiss();
    }
}

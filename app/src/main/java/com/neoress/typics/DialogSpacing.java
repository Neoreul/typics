package com.neoress.typics;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
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

public class DialogSpacing extends Dialog implements View.OnClickListener {
    public Activity c;
    public Button done, cancel;

    //for text spacing
    public static int line = 0, letter = 0;
    private int lineCur, letterCur;

    TextView textView, txtLetter, txtLine;

    protected DialogSpacing(Activity a){
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_spacing);

        done = findViewById(R.id.btnSpacingDone);
        cancel = findViewById(R.id.btnSpacingCancel);
        textView = findViewById(R.id.textSpacingView);

        done.setOnClickListener(this);
        cancel.setOnClickListener(this);

        letterCur = letter;
        lineCur = line;

        textView.setText(txt_addText[cur_edt_id].getText().toString());
        textView.setText(applyLetterSpacing(), TextView.BufferType.SPANNABLE);
        textView.setLineSpacing(line, 1f);

        SeekBar seekLetter = findViewById(R.id.seekBarLetter);
        txtLetter = findViewById(R.id.txtLetter);

        txtLetter.setText(Integer.toString(letter));
        seekLetter.setProgress(letter + 150);

        seekLetter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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
                letter = progress - 150;
                txtLetter.setText(Integer.toString(letter));
                textView.setText(applyLetterSpacing(), TextView.BufferType.SPANNABLE);
            }
        });
        SeekBar seekLine = findViewById(R.id.seekBarLine);
        txtLine = findViewById(R.id.txtLine);
        txtLine.setText(Integer.toString(line));
        seekLine.setProgress(line + 50);
        seekLine.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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
                line = progress - 50;
                txtLine.setText(Integer.toString(line));
                textView.setLineSpacing(line, 1f);
            }
        });
    }
    private Spannable applyLetterSpacing()
    {
        SpannableStringBuilder builder = new SpannableStringBuilder(textView.getText().toString());
        builder.setSpan(new TrackingSpan(letter), 0,
                textView.getText().toString().length(), Spannable.SPAN_PARAGRAPH);
        return builder;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSpacingDone:
                txt_addText[cur_edt_id].setLineSpacing(line, 1f);

                txt_addText[cur_edt_id].setHorizontallyScrolling(true);
                txt_addText[cur_edt_id].setText (applyLetterSpacing(), TextView.BufferType.SPANNABLE);

                break;
            case R.id.btnSpacingCancel:
                letter = letterCur;
                line = lineCur;
                break;
            default:
                break;
        }
        dismiss();
    }
}

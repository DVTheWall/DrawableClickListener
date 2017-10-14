package com.example.drawableclicklistener;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private static final int Date_Dialog_ID = 0;
    public int myear, mmonth, mday, YearSelected, MonthSelected, DaySelected;
    CustomEditText edit_year;
     boolean isopen = false;

    private DatePicker dpick;

    // Constructor
    // Register  DatePickerDialog listener
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            YearSelected = i;
            MonthSelected = i1;
            DaySelected = i2;
            edit_year.setText(" " + DaySelected + " " + "/" + " " + MonthSelected + " " + "/" + " " + YearSelected);
        }
    };

    public MainActivity() {

        final Calendar cal = Calendar.getInstance();
        myear = cal.get(Calendar.YEAR);
        mmonth = cal.get(Calendar.MONTH);
        mday = cal.get(Calendar.DAY_OF_MONTH);

    }

    //  This method is used for DatePicker
    @Override
    protected Dialog onCreateDialog(int id) {
        // create a new DatePickerDialog with values you want to show
        return new DatePickerDialog(this, mDateSetListener, myear, mmonth, mday);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_year = (CustomEditText) findViewById(R.id.acet_year);

        edit_year.setDrawableClickListener(new CustomEditText.DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        //Do something here

//                        showDialog(Date_Dialog_ID);

                        SelectDate(edit_year);


                        break;

                    default:
                        break;
                }

            }
        });
    }
    public void SelectDate(final CustomEditText tv) {
        isopen = true;
        final Dialog dialog = new Dialog(this);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.costom_date_picker, null, false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.getWindow().getAttributes().windowAnimations = R.style.PopupWindowAnimation;

        // Retrieve views from the inflated dialog layout and update their values

        TextView txtTitle = (TextView) dialog.findViewById(R.id.dialog_pick_title);
        txtTitle.setText("");

        String d = tv.getText().toString().trim();
        String[] day = d.split("-");


        dpick = (DatePicker) dialog.findViewById(R.id.datePicker1);
        dpick.setMaxDate(System.currentTimeMillis() - 1000);
        Button btnOk = (Button) dialog.findViewById(R.id.btn_dp_dialog_okay);

        // dpick.updateDate(Integer.parseInt(day[2]), Integer.parseInt(day[1]) - 1, Integer.parseInt(day[0]));

        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                int day = dpick.getDayOfMonth();
                int month = dpick.getMonth() + 1;
                int year = dpick.getYear();

                String strMonth = String.format("%02d", month);
                String strDay = String.format("%02d", day);

                StringBuffer sb = new StringBuffer();

                sb.append(strDay).append("/").append(strMonth).append("/").append(year);

                tv.setText("" + sb);

//                reset_bdate.setVisibility(View.VISIBLE);

                dpick.init(day, month, year, null);
                isopen = false;
            }
        });

        Button btnCancel = (Button) dialog.findViewById(R.id.btn_dp_dialog_cancle);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                isopen = false;
            }
        });

        dialog.show();
    }

}

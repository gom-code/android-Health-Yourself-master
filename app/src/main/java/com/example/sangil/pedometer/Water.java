package com.example.sangil.pedometer;



import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Water extends Activity {
public static final String KEY_MY_PREFERENCE = "my_preference";
	
	RelativeLayout pictureLayout;
	MyGraphicView view;
	static float scaleX = 1, scaleY = 1;

	
	Button data;
	
	ImageView pluse, minuse;
	ImageView imageView2;

	
	
	private TextView todayText;
	
    String filName;
	
	private TextView watertext;



	static Integer num1 = 0;




	private int tYear;
	private int tMonth;
	private int tDay;

	private long d;
	private long t;
	private long r;


	static final int DATE_DIALOG_ID = 0;
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_water);
		//��¥ ǥ��
				todayText = (TextView) findViewById(R.id.today);

				data=(Button)findViewById(R.id.data);
				
				view = (MyGraphicView) new MyGraphicView(this);
				pictureLayout = (RelativeLayout) findViewById(R.id.pictureLayout);
				pictureLayout.addView(view);
				
				
				imageView2 = (ImageView) findViewById(R.id.imageView2);
				
				pluse = (ImageView) findViewById(R.id.pluse);
				minuse = (ImageView) findViewById(R.id.minuse);
				watertext = (TextView) findViewById(R.id.watertext);
				


				Calendar calendar = Calendar.getInstance();
				tYear = calendar.get(Calendar.YEAR);
				tMonth = calendar.get(Calendar.MONTH);
				tDay = calendar.get(Calendar.DAY_OF_MONTH);

				Calendar dCalendar = Calendar.getInstance();
				updateDisplay();
				
				
				

				
				
				todayText.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						num1=0;
						watertext.setText(num1.toString() + "/8");
						
							scaleX = 1f;
						
							scaleY = 1f;
						view.invalidate();
						
					}
				});

				
				
				
			SharedPreferences settings = getSharedPreferences("WW", 0);

				if (settings.contains("key1")) {
			String save = settings.getString("key1", "");
			watertext.setText(save);

		}
					pluse.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
				
							num1++;

							scaleX = scaleX + 1.3f;
							scaleY = scaleY + 1.3f;
							if (scaleX >= 11.4)
								scaleX = 11.4f;
							if (scaleY >= 11.4)
								scaleY = 11.4f;
							view.invalidate();
							
							
							watertext.setText(num1.toString() + "/8");

							String str = watertext.getText().toString();
							
							SharedPreferences settings = getSharedPreferences("WW", 0);
							SharedPreferences.Editor prefEditor = settings.edit();
							prefEditor.putString("key1", str);
							// prefEditor.putInt("cnt_1",1);
							prefEditor.commit();
							
							}
								
							});
					minuse.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if(num1==0){
								imageView2.setVisibility(android.view.View.INVISIBLE);
								
							}
							
						if (num1 > 0) {
								num1--;
								if(num1<=8){
								scaleX = scaleX - 1.3f;
								scaleY = scaleY - 1.3f;
								if (scaleX <= 1)
									scaleX = 1f;
								if (scaleY <= 1)
									scaleY = 1f;
								view.invalidate();}
						
								
								watertext.setText(num1.toString() + "/8");
								
									String str = watertext.getText().toString();

									SharedPreferences settings = getSharedPreferences("WW", 0);
									SharedPreferences.Editor prefEditor = settings.edit();
									prefEditor.putString("key1", str);
									// prefEditor.putInt("cnt_1",1);
									prefEditor.commit();
									return;
								}}
						
					

						
					});
							
				

	}

	 //��¥ǥ��
		private void updateDisplay() {

			todayText
					.setText(String.format("%d. %d. %d", tYear, tMonth + 1, tDay));

		}
	    //��¥ǥ��
	
	
private static class MyGraphicView extends View {
	public MyGraphicView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int cenX = this.getWidth();
		int cenY = this.getHeight();
		canvas.scale(scaleX, scaleY, cenX, cenY);

		Bitmap picture = BitmapFactory.decodeResource(getResources(),
				R.drawable.cupwater);

		int picX = (this.getWidth() - picture.getWidth());
		int picY = (this.getHeight() - picture.getHeight()/2);
		canvas.drawBitmap(picture, picX, picY, null);

		picture.recycle();

	}
}



}

	
	

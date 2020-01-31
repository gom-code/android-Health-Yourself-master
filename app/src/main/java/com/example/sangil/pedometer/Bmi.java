package com.example.sangil.pedometer;




import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Bmi extends Activity {
	private EditText txtheight,txtage, txtweight;
	private TextView txtresult,textView4,textView5,textView6;
	private Button btncalculate;
	private Button btnreset;
	private double bmi = 0;
	private double valueheight = 0;
	private double valueweight = 0;
	private String resulttext;
	private ImageView imageView1,imageView2,imageView3;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bmi);
		initControls();

	}
	
	
	public static double round2(double num) {
		double result = num * 100;
		result = Math.round(result);
		result = result / 100;
		return result;
	}

	private void initControls() {
		txtage = (EditText) findViewById(R.id.txtage);
		txtheight = (EditText) findViewById(R.id.txtheight);
		txtweight = (EditText) findViewById(R.id.txtweight);
		txtresult = (TextView) findViewById(R.id.txtresult);
		btncalculate = (Button) findViewById(R.id.btncalculate);
		
		
		
		
		textView4 =(TextView)findViewById(R.id.textView4);
		textView5 =(TextView)findViewById(R.id.textView5);
		textView6 =(TextView)findViewById(R.id.textView6);
		imageView1 =(ImageView)findViewById(R.id.imageView1);
		imageView2 =(ImageView)findViewById(R.id.imageView2);
		imageView3 =(ImageView)findViewById(R.id.imageView3);
		
		
		
		

		btncalculate.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				calculate();
			}
		});

	}

	private void calculate() {
		valueheight = Double.parseDouble(txtheight.getText().toString());
		valueweight = Double.parseDouble(txtweight.getText().toString());
		bmi = (valueweight / ((valueheight/100) * (valueheight/100)));
		bmi = round2(bmi);

 if (bmi >= 25) {
			resulttext =  Double.toString(bmi)					;
			txtresult.setText(resulttext);
			textView4.setVisibility(android.view.View.INVISIBLE);
			textView6.setVisibility(android.view.View.VISIBLE);
			textView5.setVisibility(android.view.View.INVISIBLE);
			
			
			imageView3.setVisibility(android.view.View.VISIBLE);
			imageView1.setVisibility(android.view.View.INVISIBLE);
			imageView2.setVisibility(android.view.View.INVISIBLE);
		} else if (bmi >= 18.5) {
			resulttext = Double.toString(bmi);
			txtresult.setText(resulttext);
			textView5.setVisibility(android.view.View.VISIBLE);
			textView4.setVisibility(android.view.View.INVISIBLE);
			textView6.setVisibility(android.view.View.INVISIBLE);
			
			imageView2.setVisibility(android.view.View.VISIBLE);
			imageView1.setVisibility(android.view.View.INVISIBLE);
			imageView3.setVisibility(android.view.View.INVISIBLE);
		} else {
			resulttext = Double.toString(bmi)					;
			txtresult.setText(resulttext);
		    textView6.setVisibility(android.view.View.INVISIBLE);
			textView5.setVisibility(android.view.View.INVISIBLE);
			textView4.setVisibility(android.view.View.VISIBLE);
			imageView1.setVisibility(android.view.View.VISIBLE);
			imageView3.setVisibility(android.view.View.INVISIBLE);
			imageView2.setVisibility(android.view.View.INVISIBLE);
			
		}
	}

	private void reset() {
		txtresult.setText("Your BMI is unknown.");
		txtheight.setText("0");
		txtweight.setText("0");
	}

}
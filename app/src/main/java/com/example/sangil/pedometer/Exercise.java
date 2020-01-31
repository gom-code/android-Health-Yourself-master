package com.example.sangil.pedometer;





import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Exercise extends Activity {

	    TextView mEllapse;
	    TextView mSplit;

	    Button mBtnStart;
	    Button mBtnSplit;



	    final static int IDLE = 0;
	    final static int RUNNING = 1;
	    final static int PAUSE = 2;

	    int mStatus = IDLE;
	    long mBaseTime;
	    long mPauseTime;
	    int mSplitCount;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise);
		

    	

        mEllapse = (TextView)findViewById(R.id.ellapse);
        mSplit = (TextView)findViewById(R.id.split);
        mBtnStart = (Button)findViewById(R.id.btnstart);
        mBtnSplit = (Button)findViewById(R.id.btnsplit);
        
        
        

	}
    	


        Handler mTimer = new Handler(){

            

            //핸들러는 기본적으로 handleMessage에서 처리한다.

            public void handleMessage(android.os.Message msg) {

                mEllapse.setText(getEllapse());
                mTimer.sendEmptyMessage(0);

            };

        };

    	
    	
	



@Override

protected void onDestroy() {

    // TODO Auto-generated method stub

    mTimer.removeMessages(0);
    super.onDestroy();

}



public void mOnClick(View v){

    switch(v.getId()){

    


    case R.id.btnstart:

        switch(mStatus){


        case IDLE:


            mBaseTime = SystemClock.elapsedRealtime();

            mTimer.sendEmptyMessage(0);
            mBtnStart.setText("PAUSE");
            mBtnSplit.setEnabled(true);
            mStatus = RUNNING;
            break;

        



        case RUNNING:



            mTimer.removeMessages(0);


            mPauseTime = SystemClock.elapsedRealtime();


            mBtnStart.setText("START");
            mBtnSplit.setText("RESET");
            mStatus = PAUSE;
            break;



        case PAUSE:



            long now = SystemClock.elapsedRealtime();

            mBaseTime += (now - mPauseTime);

            mTimer.sendEmptyMessage(0);

            

            mBtnStart.setText("START");
            mBtnSplit.setText("RECORD");
            mStatus = RUNNING;
            break;

        }

        break;

        

    case R.id.btnsplit:

        switch(mStatus){



        case RUNNING:

            

            String sSplit = mSplit.getText().toString();

            


            sSplit += String.format("%d ]   %s\n", mSplitCount, getEllapse());

            


            mSplit.setText(sSplit);
            mSplitCount++;

            break;

        case PAUSE:


            mTimer.removeMessages(0);

            



            mBtnStart.setText("START");
            mBtnSplit.setText("RECORD");
            mEllapse.setText("00:00:00");
            mStatus = IDLE;
            mSplit.setText("");
            mBtnSplit.setEnabled(false);
            break;

        }

        break;

    }

}



String getEllapse(){

    long now = SystemClock.elapsedRealtime();

    long ell = now - mBaseTime;


    String sEll = String.format("%02d:%02d:%02d", ell / 1000 / 60, (ell/1000)%60, (ell %1000)/10);

    return sEll;

}

}


        	
    		

    		



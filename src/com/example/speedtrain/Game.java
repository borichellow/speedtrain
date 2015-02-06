package com.example.speedtrain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Game extends Activity implements OnClickListener {

	private int score = 0;
	private int[] carts = { R.drawable.h, R.drawable.o, R.drawable.x,
			R.drawable.ds, R.drawable.h };
	private int prevCart;
	private int actlCart;
	private int combo = 0;
	private final int roundTime = 30000;
	private int algorithm = 0;
	private int algorithmStep = 0;
	private int[] algorithmList = new int[9];
	private boolean algorithmOn = false;
	private final int comboLimit = 10;
	private TextView textTime;
	private Button textRestart2;
	private Button textFinish;
	private Button btnStart;
	private ImageView textPause;
	private Button textResume;
	private Button textRestart;
	private Button textQuit;
	private View shadow;
	private View pause;
	private View end;
	private View game;
	private View start;
	private long endTime = 0L;
	private Handler customHandler = new Handler();
	private long timeInMilliseconds = 0L;
	private long timePause = 0L;
	private long timeResume = 0L;
	private long updatedTime = 0L;
	private Button btnYes;
	private Button btnNo;
	private ImageView cartView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);

		btnStart = (Button) findViewById(R.id.btnStart);
		textTime = (TextView) findViewById(R.id.textTime);
		textRestart2 = (Button) findViewById(R.id.textRestart2);
		textFinish = (Button) findViewById(R.id.textFinish);
		textTime = (TextView) findViewById(R.id.textTime);
		shadow = (View) findViewById(R.id.shadow);
		pause = (View) findViewById(R.id.pause);
		end = (View) findViewById(R.id.end);
		game = (View) findViewById(R.id.game);
		start = (View) findViewById(R.id.start);

		textPause = (ImageView) findViewById(R.id.textPause);
		btnYes = (Button) findViewById(R.id.btnYes);
		btnNo = (Button) findViewById(R.id.btnNo);
		cartView = (ImageView) findViewById(R.id.cart);
		textResume = (Button) findViewById(R.id.textResume);
		textRestart = (Button) findViewById(R.id.textRestart);
		textQuit = (Button) findViewById(R.id.textQuit);

		// Setting Listeners
		btnNo.setOnClickListener(this);
		btnYes.setOnClickListener(this);
		textPause.setOnClickListener(this);
		textResume.setOnClickListener(this);
		textRestart.setOnClickListener(this);
		textQuit.setOnClickListener(this);
		textRestart2.setOnClickListener(this);
		textFinish.setOnClickListener(this);
		btnStart.setOnClickListener(this);

		// Start of game
		textPause.setClickable(false);
		btnYes.setClickable(false);
		btnNo.setClickable(false);
		prevCart = carts[(int) (Math.random() * ((long) carts.length - 0.001))];
		cartView.setImageResource(prevCart);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.btnStart:
			start();
			break;
		case R.id.btnNo:
			clickedButton(v.getId());
			break;
		case R.id.btnYes:
			clickedButton(v.getId());
			break;
		case R.id.textPause:
			pause();
			pause.setVisibility(View.VISIBLE);
			pause.setClickable(true);
			break;
		case R.id.textResume:
			pause.setVisibility(View.INVISIBLE);
			backToGame();
			break;
		case R.id.textRestart:
			restart();
			break;
		case R.id.textQuit:
			finish();
			break;
		case R.id.textFinish:
			DBHelper.getInstance(this).setNewRecord(score);
			finish();
			break;
		case R.id.textRestart2:
			restart();
			break;
		}
	}

	private void start() {
		start.setVisibility(View.INVISIBLE);
		endTime += roundTime;
		backToGame();
		btnStart.setClickable(false);
		actlCart = carts[(int) (Math.random() * ((long) carts.length - 0.001))];
		cartView.setImageResource(actlCart);
		cartView.startAnimation(AnimationUtils.loadAnimation(this,
				R.anim.transin));
	}

	private void restart() {
		Intent intentRestart = new Intent(this, Game.class);
		startActivity(intentRestart);
		finish();
	}

	private void backToGame() {
		shadow.setVisibility(View.INVISIBLE);
		game.setClickable(true);
		btnYes.setClickable(true);
		btnNo.setClickable(true);
		textPause.setClickable(true);
		timeResume = SystemClock.uptimeMillis();
		endTime = endTime + timeResume - timePause;
		customHandler.postDelayed(updateTimerThread, 0);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (!(start.isShown()) && !(end.isShown())) {
			pause();
			pause.setVisibility(View.VISIBLE);
			pause.setClickable(true);
		}
	}

	public void clickedButton(int btn) {
		View success = (View) findViewById(R.id.success);
		View comboImg = (View) findViewById(R.id.combo);
		View fail = (View) findViewById(R.id.fail);
		ImageView cartView = (ImageView) findViewById(R.id.cart);
		TextView textScore = (TextView) findViewById(R.id.textScore);

		if ((btn == R.id.btnNo && prevCart != actlCart)
				|| (btn == R.id.btnYes && prevCart == actlCart)) {
			success.startAnimation(AnimationUtils.loadAnimation(this,
					R.anim.scale));
			// success.setVisibility(View.VISIBLE);
			score++;
			combo++;
			textScore.setText(Integer.toString(score));
		} else {
			fail.startAnimation(AnimationUtils
					.loadAnimation(this, R.anim.scale));
			// fail.setVisibility(View.VISIBLE);
			combo = 0;
		}
		if (combo == comboLimit) {
			comboImg.startAnimation(AnimationUtils.loadAnimation(this,
					R.anim.scale_combo));
			combo = 0;
			score += 5;
			endTime += 3000;
		}
		prevCart = actlCart;
		actlCart = nextCart();
		cartView.setImageResource(actlCart);
		cartView.startAnimation(AnimationUtils.loadAnimation(this,
				R.anim.transin));
		success.setVisibility(View.INVISIBLE);
		fail.setVisibility(View.INVISIBLE);
	}

	private int nextCart() {
		int cart = 0;
		int cartI;
		if (!algorithmOn) {
			algorithm = (int) (Math.random() * 3.99);
			int algorithmCart;
			switch (algorithm) {
			case 0:
				cart = carts[(int) (Math.random() * ((long) carts.length - 0.001))];
				break;
			// yes-yes-yes algorithm
			case 1:
				algorithmCart = carts[(int) (Math.random() * ((long) carts.length - 0.001))];
				for (int i = 0; i < 9; i++) {
					algorithmList[i] = algorithmCart;
				}
				cart = algorithmList[0];
				algorithmOn = true;
				break;
			// no-no-no algorithm
			case 2:
				algorithmList[0] = carts[(int) (Math.random() * ((long) carts.length - 0.001))];
				for (int i = 1; i < 9; i++) {
					do {
						cartI = carts[(int) (Math.random() * ((long) carts.length - 0.001))];
					} while (cart == algorithmList[i - 1]);
					algorithmList[i] = cartI;
				}
				cart = algorithmList[0];
				algorithmOn = true;
				break;
			// yes-no-yes algorithm
			case 3:
				algorithmList[0] = carts[(int) (Math.random() * ((long) carts.length - 0.001))];
				for (int i = 1; i < 9; i++) {
					if (i % 2 == 0) {
						algorithmList[i] = algorithmList[i - 1];
					} else {
						do {
							cartI = carts[(int) (Math.random() * ((long) carts.length - 0.001))];
						} while (cart == algorithmList[i - 1]);
						algorithmList[i] = cartI;
					}
				}
				cart = algorithmList[0];
				algorithmOn = true;
				break;
			}
		} else {
			cart = algorithmList[algorithmStep];
			algorithmStep++;
		}
		if (algorithmStep == 9) {
			algorithmStep = 0;
			algorithmOn = false;
		}
		return cart;
	}

	private void pause() {
		timePause = SystemClock.uptimeMillis();
		customHandler.removeCallbacks(updateTimerThread);
		shadow.setVisibility(View.VISIBLE);
//		textRestart.setBackgroundResource(R.drawable.text);
//		textResume.setBackgroundResource(R.drawable.text);
		game.setClickable(false);
		textPause.setClickable(false);
		btnYes.setClickable(false);
		btnNo.setClickable(false);
	}

	private void end() {
		TextView textEnd = (TextView) findViewById(R.id.textEnd3);
		pause();
		textEnd.setText(Integer.toString(score));
		end.setVisibility(View.VISIBLE);
		end.setClickable(true);
	}

	private Runnable updateTimerThread = new Runnable() {
		public void run() {
			timeInMilliseconds = endTime - SystemClock.uptimeMillis();
			updatedTime = timeInMilliseconds;

			if (updatedTime <= 0) {
				end();
			} else {
				int secs = (int) (updatedTime / 1000);
				int mins = secs / 60;
				secs = secs % 60;
				textTime.setText("" + mins + ":" + String.format("%02d", secs));
				customHandler.postDelayed(this, 0);
			}
		}
	};
}

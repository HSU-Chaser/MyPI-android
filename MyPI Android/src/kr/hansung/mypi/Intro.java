package kr.hansung.mypi;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Intro extends BaseActivity {
	ImageView intro;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro);

		new Thread(new Runnable() {
			@Override
			public void run() {
				intro = (ImageView) findViewById(R.id.logo);
				Animation alphaAnim = AnimationUtils.loadAnimation(Intro.this,
						R.anim.alpha);
				intro.startAnimation(alphaAnim);
				try {
					Thread.sleep(2000);
					isIntro();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void isIntro() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);
		finish();
	}

	@Override
	public void onBackPressed() {
		// super.onBackPressed();
	}
}

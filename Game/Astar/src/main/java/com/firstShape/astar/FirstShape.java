package com.firstShape.astar;

import android.os.Bundle;
import android.app.Activity;

public class FirstShape extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MainGamePanel(this));
	}
}

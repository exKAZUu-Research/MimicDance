package net.exkazuu.ManekkoDance;

import java.util.HashMap;

import jp.eclipcebook.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class IconContainer {

	private Drawable leftHandUp;
	private Drawable leftHandDown;
	private Drawable rightHandUp;
	private Drawable rightHandDown;
	private Drawable leftFootUp;
	private Drawable leftFootDown;
	private Drawable rightFootUp;
	private Drawable rightFootDown;
	private Drawable iconJump;
	private Drawable iconLoop;
	private Drawable iconKokomade;
	private HashMap<Drawable, String> icon2Strings;

	public IconContainer(Context context) {
		this.leftHandUp = context.getResources().getDrawable(
				R.drawable.icon_left_hand_up);
		Log.v("", "ctr: width: " + leftHandUp.getBounds().width());
		this.leftHandDown = context.getResources().getDrawable(
				R.drawable.icon_left_hand_down);
		this.rightHandUp = context.getResources().getDrawable(
				R.drawable.icon_right_hand_up);
		this.rightHandDown = context.getResources().getDrawable(
				R.drawable.icon_right_hand_down);
		this.leftFootUp = context.getResources().getDrawable(
				R.drawable.icon_left_foot_up);
		this.leftFootDown = context.getResources().getDrawable(
				R.drawable.icon_left_foot_down);
		this.rightFootUp = context.getResources().getDrawable(
				R.drawable.icon_right_foot_up);
		this.rightFootDown = context.getResources().getDrawable(
				R.drawable.icon_right_foot_down);
		this.iconJump = context.getResources()
				.getDrawable(R.drawable.icon_jump);
		this.iconLoop = context.getResources()
				.getDrawable(R.drawable.icon_loop);
		this.iconKokomade = context.getResources().getDrawable(
				R.drawable.icon_kokomade);
		this.icon2Strings = new HashMap<Drawable, String>();

		icon2Strings.put(leftHandUp, "左腕を上げる");
		icon2Strings.put(leftHandDown, "左腕を下げる");
		icon2Strings.put(rightHandUp, "右腕を上げる");
		icon2Strings.put(rightHandDown, "右腕を下げる");
		icon2Strings.put(leftFootUp, "左足を上げる");
		icon2Strings.put(leftFootDown, "左足を下げる");
		icon2Strings.put(rightFootUp, "右足を上げる");
		icon2Strings.put(rightFootDown, "右足を下げる");
		icon2Strings.put(iconJump, "ジャンプする");
		icon2Strings.put(iconLoop, "loop");
		icon2Strings.put(iconKokomade, "ここまで");
		// ...
	}

	public void reload() {

	}

	public String getStringFromIcon(Drawable icon) {
		return icon2Strings.get(icon);
	}

	public Drawable getIconLeftHandUp() {
		Log.v("", "get: width: " + leftHandUp.getBounds().width());
		return leftHandUp;
	}

	public Drawable getIconLeftHandDown() {
		return leftHandDown;
	}

	public Drawable getIconRightHandUp() {
		return rightHandUp;
	}

	public Drawable getIconRightHandDown() {
		return rightHandDown;
	}

	public Drawable getIconLeftFootUp() {
		return leftFootUp;
	}

	public Drawable getIconLeftFootDown() {
		return leftFootDown;
	}

	public Drawable getIconRightFootUp() {
		return rightFootUp;
	}

	public Drawable getIconRightFootDown() {
		return rightFootDown;
	}

	public Drawable getIconJump() {
		return iconJump;
	}

	public Drawable getIconLoop() {
		return iconLoop;
	}

	public Drawable getIconKokomade() {
		return iconKokomade;
	}

}

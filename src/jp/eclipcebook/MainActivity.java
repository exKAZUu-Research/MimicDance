// Todoリスト
/* キーボードを出した時の画像潰れ解消法　http://d.hatena.ne.jp/Superdry/20110715/1310754502 */
/* フラグメントによるTabの実装 */
/* 絵文字の実装 */

package jp.eclipcebook;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	// private String path = "mydata.txt"; // file保存
	private String lesson;
	private String message;
	private String text_data;
	private ImageInEdit mImageEdit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("プレイヤー画面");
		setContentView(R.layout.main);

		mImageEdit = (ImageInEdit) findViewById(R.id.imageInEdit);

		/********** 音楽 **************/
		// MediaPlayer bgm1 = MediaPlayer.create(this, R.raw.ikusei_gamen); //
		// ゲーム音楽
		// bgm1.start(); // BGMスタート

		// doLoad(); // セーブデータをロード

		/********** Lesson data　の 取得 **************/
		Intent intent = getIntent();
		lesson = intent.getStringExtra("lesson");
		message = intent.getStringExtra("message");
		text_data = intent.getStringExtra("text_data");
		TextView editText1 = (TextView) findViewById(R.id.editText1);
		editText1.setText(text_data);
		Button actionButton = (Button) this.findViewById(R.id.button1);
		actionButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final Handler handler = new Handler();
				Thread trd = new Thread(new CommandExecutor(handler));
				trd.start();
			}
		});
	}

	/*
	 * @Override public void onWindowFocusChanged(boolean hasFocus) { //
	 * アプリを立ち上げた時からbasicのアニメを開始 super.onWindowFocusChanged(hasFocus);
	 * 
	 * ImageView img = (ImageView) findViewById(R.id.imageView1); //
	 * AnimationDrawableのXMLリソースを指定
	 * img.setBackgroundResource(R.drawable.default_position);
	 * 
	 * // AnimationDrawableを取得 AnimationDrawable frameAnimation =
	 * (AnimationDrawable) img .getBackground();
	 * 
	 * // アニメーションの開始 frameAnimation.start(); }
	 */

	/******************** 構文解析＆実行 *************************/
	public final class CommandExecutor implements Runnable {
		private final Handler handler;

		private CommandExecutor(Handler handler) {
			this.handler = handler;
		}

		public void run() {
			TextView editText1 = (TextView) findViewById(R.id.editText1);
			ImageView leftHand1 = (ImageView) findViewById(R.id.playerLeftHand1);
			ImageView rightHand1 = (ImageView) findViewById(R.id.playerRightHand1);
			ImageView basic = (ImageView) findViewById(R.id.playerBasic);
			ImageView leftFoot1 = (ImageView) findViewById(R.id.playerLeftFoot1);
			ImageView rightFoot1 = (ImageView) findViewById(R.id.playerRightFoot1);

			String commandsText = editText1.getText().toString(); // 1行ずつ配列に収納
			List<Integer> numberSorting = new ArrayList<Integer>();
			List<String> commands = new ArrayList<String>();
			StringCommandParser.parse(commandsText, numberSorting, commands);
			executeCommands(
					new ImageContainer(leftHand1, rightHand1, basic, leftFoot1, rightFoot1),
					commands, editText1, numberSorting);

		}

		private void executeCommands(ImageContainer images, List<String> expandedCommands,
				TextView editText1, List<Integer> numberSorting) {
			Runnable runnable = new StringCommandExecutor(images, expandedCommands, editText1,
					numberSorting);
			for (int i = 0; i < expandedCommands.size(); i++) { /* 解析&実行 */
				handler.post(runnable); /* 光らせる */

				try { /* 1秒待機 */
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				handler.post(runnable);

				try { /* 1秒待機 */
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/******************** ボタン(絵文字)の処理 *************************/
	public void doActionLeftHandUp(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("左腕を上げる");
		mImageEdit.insertResourceImage(MainActivity.this, R.drawable.icon_left_hand_up);
	}

	public void doActionLeftHandDown(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("左腕を下げる");
		mImageEdit.insertResourceImage(MainActivity.this, R.drawable.icon_left_hand_down);
	}

	public void doActionRightHandUp(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("右腕を上げる");
		mImageEdit.insertResourceImage(MainActivity.this, R.drawable.icon_right_hand_up);
	}

	public void doActionRightHandDown(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("右腕を下げる");
		mImageEdit.insertResourceImage(MainActivity.this, R.drawable.icon_right_hand_down);

	}

	public void doActionLeftFootUp(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("左足を上げる");
		mImageEdit.insertResourceImage(MainActivity.this, R.drawable.icon_left_foot_up);
	}

	public void doActionLeftFootDown(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("左足を下げる");
		mImageEdit.insertResourceImage(MainActivity.this, R.drawable.icon_left_foot_down);

	}

	public void doActionRightFootUp(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("右足を上げる");
		mImageEdit.insertResourceImage(MainActivity.this, R.drawable.icon_right_foot_up);
	}

	public void doActionRightFootDown(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("右足を下げる");
		mImageEdit.insertResourceImage(MainActivity.this, R.drawable.icon_right_foot_down);
	}

	public void doActionJump(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("ジャンプする");
		mImageEdit.insertResourceImage(MainActivity.this, R.drawable.icon_jump);
	}

	public void doActionEnter(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("\n");
		mImageEdit.append("\n");
	}

	public void doActionLoop(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("loop");
		mImageEdit.insertResourceImage(MainActivity.this, R.drawable.icon_loop);
	}

	public void doActionKoko(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("ここまで");
		mImageEdit.insertResourceImage(MainActivity.this, R.drawable.icon_kokomade);

	}

	public void doActionBackSpace(View view) {
		TextView editText1 = (TextView) this.findViewById(R.id.editText1);
		editText1.append("\b");
	}

	/******************** ファイル保存 doSave(View view) *************************/
	/*
	 * @SuppressLint("WorldReadableFiles") public void doSave() { EditText
	 * editText1 = (EditText) this.findViewById(R.id.editText1); Editable str =
	 * editText1.getText(); FileOutputStream output = null; try { output =
	 * this.openFileOutput(path, Context.MODE_WORLD_READABLE);
	 * output.write(str.toString().getBytes()); } catch (FileNotFoundException
	 * e) { e.printStackTrace(); } catch (IOException e) { e.printStackTrace();
	 * } finally { try { output.close(); } catch (Exception e) {
	 * e.printStackTrace(); } } }
	 */

	/******************** ファイルロード doLoad(View view) *************************/
	/*
	 * public void doLoad() { EditText editText1 = (EditText)
	 * this.findViewById(R.id.editText1); FileInputStream input = null; try {
	 * input = this.openFileInput(path); byte[] buffer = new byte[1000];
	 * input.read(buffer); String s = new String(buffer).trim();
	 * editText1.setText(s); } catch (FileNotFoundException e) {
	 * e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
	 * finally { try { input.close(); } catch (Exception e) {
	 * e.printStackTrace(); } } }
	 */

	/******************** メニュー作成 *************************/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu);
		MenuItem item1 = menu.add("実行");
		MenuItem item2 = menu.add("クリア");
		MenuItem item3 = menu.add("お手本");

		item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				changeActionScreen();
				return false;
			}
		});
		item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				TextView editText1 = (TextView) findViewById(R.id.editText1);
				editText1.getEditableText().clear();
				return false;
			}
		});
		item3.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				// doSave();
				changePartnerScreen();
				return false;
			}
		});
		return true;
	}

	/************************* インテント（画面遷移） *****************************/
	private void changeActionScreen() {
		Intent intent = new Intent(this, jp.eclipcebook.ActionActivity.class);
		TextView editText1 = (TextView) findViewById(R.id.editText1);
		intent.putExtra("text_data", editText1.getText().toString());
		intent.putExtra("lesson", lesson);
		intent.putExtra("message", message);
		this.startActivity(intent);
	}

	private void changePartnerScreen() { // お手本画面へ遷移
		Intent intent = new Intent(this, jp.eclipcebook.PartnerActivity.class);
		TextView editText1 = (TextView) findViewById(R.id.editText1);
		intent.putExtra("lesson", lesson);
		intent.putExtra("message", message);
		intent.putExtra("text_data", editText1.getText().toString());
		this.startActivity(intent);
	}

}

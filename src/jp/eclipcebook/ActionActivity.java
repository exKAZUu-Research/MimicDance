package jp.eclipcebook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.eclipcebook.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ActionActivity extends Activity {

	private String path = "mydata2.txt";
	private String lesson;
	private String message;
	private String text_data;
	public boolean answerCheckEnd = false;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("実行画面");
		setContentView(R.layout.action_screen);
		// doLoad();

		TextView playerEditText = (TextView) findViewById(R.id.editTextActionScreen1);
		TextView partnerEditText = (TextView) findViewById(R.id.editTextActionScreen2);
		Intent intent = getIntent();
		lesson = intent.getStringExtra("lesson");
		message = intent.getStringExtra("message");
		text_data = intent.getStringExtra("text_data");
		String playerCommandsText = text_data;
		String partnerCommandsText = lesson;

		playerEditText.setText(playerCommandsText);
		partnerEditText.setText(partnerCommandsText);

		Button btn1 = (Button) this.findViewById(R.id.button1);
		btn1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final Handler handler = new Handler();
				Thread trd = new Thread(new CommandExecutor(handler));
				trd.start();
			}
		});

	}

	public final class CommandExecutor implements Runnable {
		private final Handler handler;

		private CommandExecutor(Handler handler) {
			this.handler = handler;
		}

		public void run() {
			ImageView playerImage1 = (ImageView) findViewById(R.id.playerLeftHand1);
			ImageView playerImage4 = (ImageView) findViewById(R.id.playerRightHand1);
			ImageView playerImage7 = (ImageView) findViewById(R.id.playerBasic);
			ImageView playerImage8 = (ImageView) findViewById(R.id.playerLeftFoot1);
			ImageView playerImage11 = (ImageView) findViewById(R.id.playerRightFoot1);

			ImageView partnerImage1 = (ImageView) findViewById(R.id.partnerLeftHand1);
			ImageView partnerImage4 = (ImageView) findViewById(R.id.partnerRightHand1);
			ImageView partnerImage7 = (ImageView) findViewById(R.id.partnerBasic);
			ImageView partnerImage8 = (ImageView) findViewById(R.id.partnerLeftFoot1);
			ImageView partnerImage11 = (ImageView) findViewById(R.id.partnerRightFoot1);

			TextView playerEditText = (TextView) findViewById(R.id.editTextActionScreen1);
			TextView partnerEditText = (TextView) findViewById(R.id.editTextActionScreen2);

			String playerCommandsText = playerEditText.getText().toString();
			String partnerCommandsText = partnerEditText.getText().toString();
			List<Integer> playerNumberSorting = new ArrayList<Integer>();
			List<String> playerCommands = new ArrayList<String>();
			List<Integer> partnerNumberSorting = new ArrayList<Integer>();
			List<String> partnerCommands = new ArrayList<String>();

			final AnswerCheck answer;

			StringCommandParser.parse(playerCommandsText, playerNumberSorting, playerCommands);
			StringCommandParser.parse(partnerCommandsText, partnerNumberSorting, partnerCommands);

			Runnable playerAction = new StringCommandExecutor(new ImageContainer(playerImage1,
					playerImage4, playerImage7, playerImage8, playerImage11), playerCommands,
					playerEditText, playerNumberSorting);
			Runnable partnerAction = new StringCommandExecutor(new ImageContainer(partnerImage1,
					partnerImage4, partnerImage7, partnerImage8, partnerImage11), partnerCommands);

			answer = new AnswerCheck(playerCommands, partnerCommands);

			// 解析&実行
			for (int i = 0; i < Math.max(playerCommands.size(), partnerCommands.size()); i++) {

				if (i < playerCommands.size())
					handler.post(playerAction); /* 光らせる */
				if (i < partnerCommands.size())
					handler.post(partnerAction);

				try { /* 1秒待機 */
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (i < playerCommands.size())
					handler.post(playerAction);
				if (i < partnerCommands.size())
					handler.post(partnerAction);
				try { /* 1秒待機 */
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

			handler.post(new Runnable() {
				public void run() {
					AlertDialog.Builder builder = new AlertDialog.Builder(ActionActivity.this);
					builder.setTitle(answer.show());
					builder.setMessage(answer.show());
					if (answer.judge)
						builder.setIcon(R.drawable.answer_ture);
					if (!answer.judge)
						builder.setIcon(R.drawable.answer_false);
					builder.show();
				}
			});
		}

	}

	class SampleTask extends AsyncTask<Void, Void, Void> {

		/**
		 * executeが実行された後に実行される。
		 * 
		 * @return
		 */
		@Override
		protected Void doInBackground(Void... params) {
			// DB登録等のUIに関与しない処理
			return null;
		}

		/**
		 * doInBackgroundの後に実行される。 このメソッド内ではUIを操作できる。
		 */
		public void onPostExecute(Void... params) {

			AlertDialog.Builder builder = new AlertDialog.Builder(ActionActivity.this);
			builder.setMessage("hoge");
			builder.show();
		}
	}

	@SuppressLint("WorldReadableFiles")
	public void doSave() {
		EditText editText2 = (EditText) this.findViewById(R.id.editTextActionScreen2);
		Editable str = editText2.getText();
		FileOutputStream output = null;
		try {
			output = this.openFileOutput(path, Context.MODE_WORLD_READABLE);
			output.write(str.toString().getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

//	public void doLoad() {
//		EditText editText2 = (EditText) this.findViewById(R.id.editTextActionScreen2);
//		FileInputStream input = null;
//		try {
//			input = this.openFileInput(path);
//			byte[] buffer = new byte[1000];
//			input.read(buffer);
//			String s = new String(buffer).trim();
//			editText2.setText(s);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				input.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		super.onCreateOptionsMenu(menu);
		MenuItem item1 = menu.add("実行");
		MenuItem item2 = menu.add("戻る");

		item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {

				return false;
			}
		});
		item2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				doSave();
				changeMainScreen();
				return false;
			}
		});
		return true;
	}

	private void changeMainScreen() {
		Intent intent = new Intent(this, jp.eclipcebook.MainActivity.class);
		TextView playerEditText = (TextView) findViewById(R.id.editTextActionScreen1);
		TextView partnerEditText = (TextView) findViewById(R.id.editTextActionScreen2);
		intent.putExtra("text_data", playerEditText.getText().toString());
		intent.putExtra("lesson", partnerEditText.getText().toString());
		intent.putExtra("message", message);
		this.startActivity(intent);
	}

}

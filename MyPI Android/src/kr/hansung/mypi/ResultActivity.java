package kr.hansung.mypi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import kr.list.ChildItem;
import kr.list.GroupItem;
import kr.object.SearchResult;
import kr.object.StaticItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ResultActivity extends BaseActivity {
	protected static final int REQUEST_CODE_ANOTHER = 101;

	RelativeLayout layout;
	ProgressBar mProgress;
	ProgressDialog mDialog;

	ArrayList<String> imageResult;
	ArrayList<StaticItem> staticResult;
	ArrayList<SearchResult> dynamicResult;
	ViewGroup.LayoutParams params;

	TextView tv;
	TextView gradeText, gradeExpText;
	Button resultBtn;

	ImageView[] imgResultArray;
	ImageView[] expUrlArray;

	Drawable[] riskImgArray;
	Drawable riskImg;

	String grade = null;
	String gradeExp = null;
	JSONArray imageSearch = null;
	JSONArray staticSearch = null;
	JSONArray dynamicSearch = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
		setContentView(R.layout.activity_result);

		params = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		// Intent intent = getIntent();
		// tv = (TextView) findViewById(R.id.plain);
		// mProgress = (ProgressBar) findViewById(R.id.progress_bar);

		// ListView resultList = (ListView) findViewById(R.id.result_list);

		resultBtn = (Button) findViewById(R.id.resultBtn);
		gradeText = (TextView) findViewById(R.id.gradeText);
		gradeExpText = (TextView) findViewById(R.id.gradeExpText);

		riskImgArray = new Drawable[3];

		riskImgArray[0] = getResources().getDrawable(R.drawable.risk_low);
		riskImgArray[1] = getResources().getDrawable(R.drawable.risk_mid);
		riskImgArray[2] = getResources().getDrawable(R.drawable.risk_high);

		imgResultArray = new ImageView[10];
		imgResultArray[0] = (ImageView) findViewById(R.id.imgResult0);
		imgResultArray[1] = (ImageView) findViewById(R.id.imgResult1);
		imgResultArray[2] = (ImageView) findViewById(R.id.imgResult2);
		imgResultArray[3] = (ImageView) findViewById(R.id.imgResult3);
		imgResultArray[4] = (ImageView) findViewById(R.id.imgResult4);
		imgResultArray[5] = (ImageView) findViewById(R.id.imgResult5);
		imgResultArray[6] = (ImageView) findViewById(R.id.imgResult6);
		imgResultArray[7] = (ImageView) findViewById(R.id.imgResult7);
		imgResultArray[8] = (ImageView) findViewById(R.id.imgResult8);
		imgResultArray[9] = (ImageView) findViewById(R.id.imgResult9);

		expUrlArray = new ImageView[10];
		expUrlArray[0] = (ImageView) findViewById(R.id.expUrl0);
		expUrlArray[1] = (ImageView) findViewById(R.id.expUrl1);
		expUrlArray[2] = (ImageView) findViewById(R.id.expUrl2);
		expUrlArray[3] = (ImageView) findViewById(R.id.expUrl3);
		expUrlArray[4] = (ImageView) findViewById(R.id.expUrl4);
		expUrlArray[5] = (ImageView) findViewById(R.id.expUrl5);
		expUrlArray[6] = (ImageView) findViewById(R.id.expUrl6);
		expUrlArray[7] = (ImageView) findViewById(R.id.expUrl7);
		expUrlArray[8] = (ImageView) findViewById(R.id.expUrl8);
		expUrlArray[9] = (ImageView) findViewById(R.id.expUrl9);

		new ResultTask().execute();

		resultBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(getBaseContext(),
						DynamicResultActivity.class);
				startActivityForResult(intent, REQUEST_CODE_ANOTHER);
			}
		});
	}

	class ResultTask extends AsyncTask<Void, String, JSONObject> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mDialog = new ProgressDialog(ResultActivity.this);
			mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mDialog.setTitle("MyPI");
			mDialog.setMessage("결과를 분석중입니다...");
			mDialog.show();
		}

		@Override
		protected JSONObject doInBackground(Void... params) {
			HttpURLConnection conn = null;
			StringBuffer buffer = null;
			JSONObject object = null;

			// Parse JSON
			try {
				// Build URL
				URL url = new URL("http://mypi.co.kr/mobileSearch.jsp");

				// Connect
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setUseCaches(false);

				// Set Cookie
				String cookie = CookieManager.getInstance().getCookie(
						"http://mypi.co.kr");
				Log.i("Set Cookie", cookie);
				conn.setRequestProperty("Cookie", cookie);

				// Read
				InputStreamReader isr = new InputStreamReader(
						conn.getInputStream(), "UTF-8");
				BufferedReader br = new BufferedReader(isr);
				// Save
				String read;
				buffer = new StringBuffer();
				while ((read = br.readLine()) != null) {
					Log.i("Data", read);
					buffer.append(read);
				}
				object = new JSONObject(buffer.toString());
				// Close
				br.close();
				isr.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				conn.disconnect();
			}

			parseJSON(object);

			// Get Images From URL
			publishProgress("image");

			// Image Search
			for (int i = 0; i < imageResult.size(); i++) {
				try {
					URL url = new URL(imageResult.get(i));
					Bitmap bitmap = BitmapFactory
							.decodeStream(url.openStream());
					imgResultArray[i].setImageBitmap(bitmap);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// Static Search
			for (int i = 0; i < staticResult.size(); i++) {
				try {
					StaticItem si = staticResult.get(i);
					URL url = new URL(si.getSiteURL());
					Bitmap bitmap = BitmapFactory
							.decodeStream(url.openStream());
					si.setSiteImage(bitmap);
					staticResult.set(i, si);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return object;
		}

		// Dialog 메시지 설정
		@Override
		protected void onProgressUpdate(String... values) {
			if (values[0].equals("image"))
				mDialog.setMessage("이미지를 불러오는 중입니다...");
		}

		// 받아오는것이 완료된 시점
		@Override
		protected void onPostExecute(JSONObject object) {
			super.onPostExecute(object);
			mDialog.dismiss();

			// UI Update
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						public void run() {
							// Grade UI
							gradeText.setText(Html.fromHtml("<b>" + grade
									+ "</b>"));
							gradeExpText.setText(gradeExp);

							// ImageSearch UI
							for (int i = imageResult.size(); i < imgResultArray.length; i++) {
								imgResultArray[i].setVisibility(View.INVISIBLE);
							}

							// StaticSearch UI
							for (int i = 0; i < staticResult.size(); i++) {
								try {
									// 사이트 이미지 세팅
									expUrlArray[i].setImageBitmap(staticResult
											.get(i).getSiteImage());
									expUrlArray[i].setTag(i);
									expUrlArray[i]
											.setOnClickListener(new OnClickListener() {
												@Override
												public void onClick(View v) {
													Log.d("TEST",
															"로그 : "
																	+ v.getTag()
																	+ "  "
																	+ staticResult
																			.get((Integer) v
																					.getTag())
																			.getUrl());
													Intent intent = new Intent(
															Intent.ACTION_VIEW,
															Uri.parse(staticResult
																	.get((Integer) v
																			.getTag())
																	.getUrl()));
													startActivity(intent);
												}
											});
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							for (int i = staticResult.size(); i < expUrlArray.length; i++) {
								expUrlArray[i].setVisibility(View.INVISIBLE);
							}
						}
					});
				}
			});
			t.start();
		}
	}

	private void parseJSON(JSONObject object) {
		try {
			grade = object.getString("grade");
			gradeExp = object.getString("gradeExp");
			imageSearch = object.getJSONArray("image");
			staticSearch = object.getJSONArray("static");
			dynamicSearch = object.getJSONArray("dynamic");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// Image Search
		imageResult = new ArrayList<String>();
		for (int i = 0; i < imageSearch.length(); i++) {
			try {
				String item = imageSearch.getString(i);
				imageResult.add(item);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Static Search
		staticResult = new ArrayList<StaticItem>();
		for (int i = 0; i < staticSearch.length(); i++) {
			try {
				JSONObject item = (JSONObject) staticSearch.get(i);

				staticResult.add(new StaticItem(item.getString("siteImage"),
						item.getString("siteName"), item.getString("url")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Dynamic Search
		dynamicResult = new ArrayList<SearchResult>();
		DynamicResultActivity.mGroupList.clear();
		DynamicResultActivity.mChildListContent.clear();

		for (int i = 0; i < dynamicSearch.length(); i++) {
			try {
				JSONObject item = (JSONObject) dynamicSearch.get(i);

				dynamicResult.add(new SearchResult(item.getString("engine"),
						item.getString("title"), item.getString("URL"), item
								.getString("snippet"), item
								.getString("searchPage"), item
								.getDouble("exposure")));

				double exposure = dynamicResult.get(i).getExposure();
				if (exposure >= 120)
					riskImg = riskImgArray[2];
				else if (exposure < 120 && exposure >= 20)
					riskImg = riskImgArray[1];
				else if (exposure < 20)
					riskImg = riskImgArray[0];

				DynamicResultActivity.mGroupList.add(new GroupItem(i + 1 + "",
						dynamicResult.get(i).getTitle(), riskImg));

				String[] solution = {,};
				solution = getSolution(dynamicResult.get(i).getEngine(),
						dynamicResult.get(i).getURL());

				DynamicResultActivity.mChildListContent.add(new ChildItem(
						dynamicResult.get(i).getSnippet(), solution[0],
						solution[1]));

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		// DynamicResultActivity 에서 사용할 재료들을 저장시켜준

	}

	public String[] getSolution(String engine, String url) {
		// String solution1 = null, solution2 = null;
		String solution[] = { "", "" };

		if (engine.matches(".*Naver.*") == true) {
			solution[0] = "<p>결과가 개인의 금융정보 유출이나 심각한 개인정보 침해를 야기할 수 있다면, 위 링크를 통해 삭제 요청을 할 수 있습니다.</p>"
					+ "<p>검색 결과 삭제 요청을 통해 결과 삭제를 진행하세요.</p>";
		} else if (engine.matches(".*Daum.*") == true) {
			solution[0] = "<p>결과가 내가아닌 타인에 의해 개인의 금융정보 유출이나 심각한 개인정보 피해를 야기할 수 있게 되었다면,</p>"
					+ "<p>위 링크를 통해 처리과정을 참고한 후 게시중단요청을 진행하시기 바랍니다.</p> <h5>* 참고 : Daum 로그인이 필요합니다. 아이디가 없을경우 따로 본인인증을 거쳐야 합니다.</h5>";
		} else if (engine.matches(".*Google.*") == true) {
			solution[0] = "<p>결과가 개인의 금융정보 유출이나 심각한 개인정보 침해를 야기할 수 있다면, 위 링크를 통해 삭제 요청을 할 수 있습니다.</p>"
					+ "<p>검색 결과 삭제 요청을 통해 결과 삭제를 진행하세요.</p>";
		}

		if (url.matches(".*blog.naver.*") == true) {
			solution[1] = "<p>결과가 개인의 금융정보 유출이나 심각한 개인정보 침해를 야기할 수 있다면, "
					+ "위 링크를 통해 처리과정을 참고한 후 </p><p>게시중단요청을 진행하시기 바랍니다.</p>"
					+ "<h5>*참고 : 네이버 블로그의 권리침해 신고는 신고서와 증빙서류를 갖추어야 회사에 당해 게시물에 대한 게재 중단 조치를 요청할 수 있습니다.</h5>"
					+ "<p>- 직접 삭제 -</p><p>만약 자신이 직접 과거에 올렸던 자료가 개인정보 침해를 야기한다면 NaverBlog에 직접 로그인하여 정보를 삭제하십시오. </p>";
		} else if (url.matches(".*kin.naver.*") == true) {
			solution[1] = "<p>결과가 개인의 금융정보 유출이나 심각한 개인정보 침해를 야기할 수 있다면, 위 링크를 통해 처리과정을 참고한 후 </p>"
					+ "<p>게시중단요청을 진행하시기 바랍니다.</p>"
					+ "<h5>*참고 : 네이버 지식인의 권리침해 신고는 신고서와 증빙서류를 갖추어야 회사에 당해 게시물에 대한 게재 중단 조치를 요청할 수 있습니다.</h5>"
					+ "<p>- 직접 삭제 -</p><p>만약 자신이 직접 과거에 올렸던 자료가 개인정보 침해를 야기한다면 NaverKIN에 직접 로그인하여 정보를 삭제하십시오. </p>";
		} else if (url.matches(".*blog.cyworld.*") == true) {
			solution[1] = "<p>결과가 개인의 금융정보 유출이나 심각한 개인정보 침해를 야기할 수 있다면, 위 링크를 통해 삭제 요청을 할 수 있습니다.</p>"
					+ "<p>검색 결과 삭제 요청을 통해 결과 삭제를 진행하세요.</p>"
					+ "<h5>*참고 : 싸이월드 블로그의 권리침해 신고는 신고서와 증빙서류를 갖추어야 회사에 당해 게시물에 대한 게재 중단 조치를 요청할 수 있습니다.</h5>"
					+ "<p>직접 삭제</p>"
					+ "<p>만약 자신이 직접 과거에 올렸던 자료가 개인정보 침해를 야기한다면 CyworldBlog에 직접 로그인하여 정보를 삭제하십시오. </p>";
		} else if (url.matches(".*cyworld.*") == true) {
			solution[1] = "<p>결과가 개인의 금융정보 유출이나 심각한 개인정보 침해를 야기할 수 있다면, 위 링크를 통해 삭제 요청을 할 수 있습니다.</p>"
					+ "<p>검색 결과 삭제 요청을 통해 결과 삭제를 진행하세요.</p>"
					+ "<h5>*참고 : 싸이월드의 권리침해 신고는 신고서와 증빙서류를 갖추어야 회사에 당해 게시물에 대한 게재 중단 조치를 요청할 수 있습니다.</h5>"
					+ "<p>직접 삭제</p>"
					+ "<p>만약 자신이 직접 과거에 올렸던 자료가 개인정보 침해를 야기한다면 Cyworld에 직접 로그인하여 정보를 삭제하십시오. </p>";
		} else if (url.matches(".*blog.daum.*") == true) {
			solution[1] = "<p>결과가 내가아닌 타인에 의해 개인의 금융정보 유출이나 심각한 개인정보 피해를 야기할 수 있게 되었다면,</p>"
					+ "<p>위 링크를 통해 링크 내에 있는 권리침해 신고 처리절차를 숙지하신 후 신고하여 정보 삭제를 진행하십시오.</p>"
					+ "<p>더 사세한 사항은 <a href='http://cs.daum.net/faq/list/95,7580.html' target='_blank'>Daum 명예훼손 신고 FAQ<a> 를 확인하시기 바랍니다.</p>"
					+ "<h5>* 참고 : Daum 로그인이 필요합니다. 아이디가 없을경우 따로 본인인증을 거쳐야 합니다.</h5>"
					+ "<p>- 직접 삭제 -</p>"
					+ "<p>만약 자신이 직접 과거에 올렸던 자료가 개인정보 침해를 야기한다면 DaumBlog에 직접 로그인하여 정보를 삭제하십시오. </p>";
		} else if (url.matches(".*dreamwiz.*") == true) {
			solution[1] = "<p>결과가 개인의 금융정보 유출이나 심각한 개인정보 침해를 야기할 수 있다면, 위 링크를 통해 삭제 요청을 할 수 있습니다.</p>"
					+ "<p>검색 결과 삭제 요청을 통해 결과 삭제를 진행하세요.</p>"
					+ "<h5>*참고 : 드림위즈의 침해신고는 반드시 로그인 과정을 거쳐야 가능합니다. 아이디가 없을 경우 회원 가입 후 신고를 진행하십시오.</h5>"
					+ "<p>- 직접 삭제 -</p>"
					+ "<p>만약 자신이 직접 과거에 올렸던 자료가 개인정보 침해를 야기한다면 Dreamwiz에 직접 로그인하여 정보를 삭제하십시오. </p>";
		} else if (url.matches(".*egloos.*") == true) {
			solution[1] = "<p>결과가 개인의 금융정보 유출이나 심각한 개인정보 침해를 야기할 수 있다면, 위 링크를 통해 침해 내용을 작성한 후 </p>"
					+ "<p>보내기 버튼을 눌러 신고를 진행하십시오.</p>"
					+ "<p>더 자세한 사항은 <a href='http://help.egloos.com/5870' target='_blank'>이글루스 권리침해 처리 절차 및 신고방법</a>"
					+ "을 확인하시기 바랍니다.</p>"
					+ "<h5>*참고 : 이글루스의 권리침해 신고는 신고서와 증빙서류를 갖추어야 회사에 당해 게시물에 대한 게재 중단 조치를 요청할 수 있습니다.</h5>"
					+ "<p>- 직접 삭제 -</p>"
					+ "<p>만약 자신이 직접 과거에 올렸던 자료가 개인정보 침해를 야기한다면 Egloos에 직접 로그인하여 정보를 삭제하십시오. </p>";
		} else if (url.matches(".*gallog.*") == true) {
			solution[1] = "<p>결과가 개인의 금융정보 유출이나 심각한 개인정보 침해를 야기할 수 있다면, 위 링크를 통해 침해 내용을 작성한 후 </p>"
					+ "<p>신고를 진행하십시오.</p>"
					+ "<p>- 직접 삭제 -</p>"
					+ "<p>만약 자신이 직접 과거에 올렸던 자료가 개인정보 침해를 야기한다면 DCinside에 직접 로그인하여 정보를 삭제하십시오. </p>";
		} else if (url.matches(".*me2day.*") == true) {
			solution[1] = "<p>결과가 개인의 금융정보 유출이나 심각한 개인정보 침해를 야기할 수 있다면, 위 링크를 통해 처리과정을 참고한 후 </p>"
					+ "<p>게시중단요청을 진행하시기 바랍니다.</p>"
					+ "<h5>*참고 : 네이버 미투데이의 권리침해 신고는 신고서와 증빙서류를 갖추어야 회사에 당해 게시물에 대한 게재 중단 조치를 요청할 수 있습니다.</h5>"
					+ "<p>- 직접 삭제 -</p>"
					+ "<p>만약 자신이 직접 과거에 올렸던 자료가 개인정보 침해를 야기한다면 NaverMe2day에 직접 로그인하여 정보를 삭제하십시오. </p>";
		} else if (url.matches(".*tistory.*") == true) {
			solution[1] = "<p>결과가 내가아닌 타인에 의해 개인의 금융정보 유출이나 심각한 개인정보 피해를 야기할 수 있게 되었다면,</p>"
					+ "<p>위 링크를 통해 링크 내에 있는 권리침해 신고 처리절차를 숙지하신 후 신고하여 정보 삭제를 진행하십시오.</p>"
					+ "<p>더 사세한 사항은 <a href='http://cs.daum.net/faq/list/95,7580.html' target='_blank'>Daum 명예훼손 신고 FAQ<a> 를 확인하시기 바랍니다.</p>"
					+ "<h5>* 참고 : Daum 로그인이 필요합니다. 아이디가 없을경우 따로 본인인증을 거쳐야 합니다.</h5>"
					+ "<p>- 직접 삭제 -</p>"
					+ "<p>만약 자신이 직접 과거에 올렸던 자료가 개인정보 침해를 야기한다면 Tistory에 직접 로그인하여 정보를 삭제하십시오.</p>";
		} else if (url.matches(".*todayhumor.*") == true) {
			solution[1] = "<p>결과가 개인의 금융정보 유출이나 심각한 개인정보 침해를 야기할 수 있다면, 위 링크를 통해 침해 내용을 작성한 후 </p><p>신고를 진행하십시오.</p>"
					+ "<h5>* Todayhumor 로그인이 필요합니다.</h5>"
					+ "<p>- 직접 삭제 -</p>"
					+ "<p>만약 자신이 직접 과거에 올렸던 자료가 개인정보 침해를 야기한다면 Todayhumor에 직접 로그인하여 정보를 삭제하십시오. </p>";
		} else if (url.matches(".*twitter.*") == true) {
			solution[1] = "<p>결과가 개인의 금융정보 유출이나 심각한 개인정보 침해를 야기할 수 있다면, 위 링크를 통해 다른 사용자에게 </p>"
					+ "<p>컨텐츠 삭제를 요청하거나 위 링크의 과정을 따라가며 위반 행위를 신고하십시오.</p>"
					+ "<p>- 직접 삭제 -</p>"
					+ "<p>만약 자신이 직접 과거에 올렸던 자료가 개인정보 침해를 야기한다면 Twitter에 직접 로그인하여 정보를 삭제하십시오. </p>";
		}

		return solution;
	}

	// Action Bar Menu Control
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case android.R.id.home:
			intent = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.change_info:
			intent = new Intent(getApplicationContext(),
					ChangeInfoActivity.class);
			startActivity(intent);
			break;
		case R.id.signout:
			// 로그아웃
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return false;
	}
}

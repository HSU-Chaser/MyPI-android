package kr.hansung.mypi;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeInfoActivity extends BaseActivity {
	EditText nameEdit, handphoneEdit, homephoneEdit, homeaddressEdit,
			birthEdit, jobplaceEdit, jobEdit, schoolEdit;

	String name, handphone, homephone, homeaddress, birth, jobplace, job,
			school;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);

		setContentView(R.layout.change_info);

		// accept
		Button okBtn = (Button) findViewById(R.id.okbtn);
		okBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				nameEdit = (EditText) findViewById(R.id.nameEdit);
				handphoneEdit = (EditText) findViewById(R.id.handphoneEdit);
				homephoneEdit = (EditText) findViewById(R.id.homephoneEdit);
				homeaddressEdit = (EditText) findViewById(R.id.homeaddressEdit);
				birthEdit = (EditText) findViewById(R.id.birthEdit);
				jobplaceEdit = (EditText) findViewById(R.id.jobplaceEdit);
				jobEdit = (EditText) findViewById(R.id.jobEdit);
				schoolEdit = (EditText) findViewById(R.id.schoolEdit);
				
				name = nameEdit.getText().toString();
				handphone = handphoneEdit.getText().toString();
				homephone = homephoneEdit.getText().toString();
				homeaddress = homeaddressEdit.getText().toString();
				birth = birthEdit.getText().toString();
				jobplace = jobplaceEdit.getText().toString();
				job = jobEdit.getText().toString();
				school = schoolEdit.getText().toString();
				
				Intent intent = new Intent(getApplicationContext(),
						SearchActivity.class);
				startActivity(intent);
				
				Toast.makeText(getApplicationContext(), "이름은 " + name + "입니다.", Toast.LENGTH_SHORT).show();
			}
		});
	}

	// Action Bar Menu Control
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case android.R.id.home:
			intent = new Intent(getApplicationContext(), SearchActivity.class);
			startActivity(intent);
			finish();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return false;
	}
}

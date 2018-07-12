package com.zoho.crm_field_buddy.listActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zoho.crm.library.crud.ZCRMRecord;
import com.zoho.crm.library.exception.ZCRMException;
import com.zoho.crm_field_buddy.R;

import java.util.Iterator;

/**
 * Created by sruthi-4404 on 05/10/17.
 */

public class CompletedAppointmentsListActivity extends ListViewHandler{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public void recordList() throws ZCRMException
    {
        ZCRMRecord zcrmRecord;
        Iterator itr = ListViewAdapter.records.iterator();
        while (itr.hasNext())
        {
            zcrmRecord = (ZCRMRecord) itr.next();
            super.addRecordToList(zcrmRecord,new RecordListAdapter(this));
        }

        super.setPageRefreshingOff();
    }

    private class RecordListAdapter extends ArrayAdapter<ZCRMRecord>
    {
        private final Activity context;
        public RecordListAdapter(Activity context)
        {
            super( context, R.layout.list_item, ListViewAdapter.storeList);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, View rowView, @NonNull ViewGroup parent) {

            if (rowView == null)
            {
                rowView = context.getLayoutInflater().inflate(R.layout.list_item, parent, false);
            }

            ZCRMRecord record = ListViewAdapter.storeList.get(position);

            try {
                TextView name = rowView.findViewById(R.id.textView7);
                name.setText( String.valueOf(record.getFieldValue("Name"))); //No I18N
                TextView contact = rowView.findViewById(R.id.textView8);
                if(((ZCRMRecord)record.getFieldValue("Contact"))!=null) {
                    contact.setText(((ZCRMRecord) record.getFieldValue("Contact")).getLookupLabel());
                }else {
                    contact.setText("---");
                }
            } catch (ZCRMException e) {
                e.printStackTrace();
            }

            return rowView;
        }
    }

}

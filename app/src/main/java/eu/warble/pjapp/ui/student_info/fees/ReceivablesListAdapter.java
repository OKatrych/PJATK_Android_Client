package eu.warble.pjapp.ui.student_info.fees;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import eu.warble.pjapp.R;
import eu.warble.pjapp.data.model.OplatyItem;
import eu.warble.pjapp.data.model.Student;

public class ReceivablesListAdapter extends BaseAdapter {

    private List<OplatyItem> receivables;
    private LayoutInflater inflater;

    public ReceivablesListAdapter(@NonNull Context context, Student studentData) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.receivables = studentData.getOplaty();
        Collections.reverse(receivables);
    }

    @Override
    public int getCount() {
        return receivables.size();
    }

    @Override
    public OplatyItem getItem(int i) {
        return receivables.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = inflater.inflate(R.layout.list_item_receivables, parent, false);
        }
        OplatyItem oplatyItem = getItem(position);
        ((TextView) view.findViewById(R.id.receivable_item_name))
                .setText(oplatyItem.getNazwa());
        ((TextView) view.findViewById(R.id.receivable_item_amount))
                .setText(String.format("%s zł", oplatyItem.getKwota()));
        ((TextView) view.findViewById(R.id.receivable_item_date))
                .setText(oplatyItem.getTerminPlatnosci());
        ((TextView) view.findViewById(R.id.receivable_item_instalment))
                .setText(String.valueOf(oplatyItem.getNrRaty()));
        ((TextView) view.findViewById(R.id.receivable_item_total_instalment))
                .setText(String.valueOf(oplatyItem.getLiczbaRat()));
        return view;
    }
}

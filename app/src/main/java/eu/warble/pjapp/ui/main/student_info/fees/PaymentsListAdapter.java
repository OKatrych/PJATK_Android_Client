package eu.warble.pjapp.ui.main.student_info.fees;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import eu.warble.pjapp.R;
import eu.warble.pjapp.data.model.PlatnosciItem;
import eu.warble.pjapp.data.model.Student;


public class PaymentsListAdapter extends BaseAdapter {
    private List<PlatnosciItem> payments;
    private LayoutInflater inflater;

    public PaymentsListAdapter(@NonNull Context context, Student studentData) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.payments = studentData.getPlatnosci();
    }

    @Nullable
    @Override
    public PlatnosciItem getItem(int position) {
        return payments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return payments.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = inflater.inflate(R.layout.list_item_payments, parent, false);
        }

        PlatnosciItem platnosciItem = getItem(position);
        if (platnosciItem != null) {
            ((TextView) view.findViewById(R.id.payments_list_date))
                    .setText(platnosciItem.getDataWplaty());
            ((TextView) view.findViewById(R.id.payments_list_amount))
                    .setText(String.valueOf(platnosciItem.getKwota()));
            ((TextView) view.findViewById(R.id.payments_list_name))
                    .setText(platnosciItem.getWplacajacy());
            ((TextView) view.findViewById(R.id.payments_list_description))
                    .setText(platnosciItem.getTytulWplaty());
        }

        return view;
    }
}

package eu.warble.pjapp.ui.main.student_info.marks;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.sql.Date;
import java.text.DateFormat;
import java.util.List;

import eu.warble.pjapp.R;
import eu.warble.pjapp.data.model.OcenyItem;
import eu.warble.pjapp.data.model.Student;


public class MarksListAdapter extends BaseAdapter {
    private List<OcenyItem> marks;
    private Context context;
    private LayoutInflater inflater;

    public MarksListAdapter(@NonNull Context context, Student studentData) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.marks = studentData.getOceny();
    }

    @Override
    public int getCount() {
        return marks.size();
    }

    @Override
    public OcenyItem getItem(int i) {
        return marks.get(i);
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
            view = inflater.inflate(R.layout.list_item_marks, parent, false);
        }
        OcenyItem mark = getItem(position);
        long date = Long.valueOf(mark.getData().substring(mark.getData().indexOf('(')+1, mark.getData().indexOf('+')));
        ((TextView) view.findViewById(R.id.marks_date)).setText(DateFormat.getDateInstance().format(new Date(date)));
        if (mark.getZaliczenie().equals("Egzamin"))
            ((TextView) view.findViewById(R.id.marks_exam_pass)).setText(context.getString(R.string.exam));
        else
            ((TextView) view.findViewById(R.id.marks_exam_pass)).setText(context.getString(R.string.pass));
        ((TextView) view.findViewById(R.id.marks_mark)).setText(String.valueOf(mark.getOcena()));
        ((TextView) view.findViewById(R.id.marks_name)).setText(mark.getKodPrzedmiotu());
        ((TextView) view.findViewById(R.id.marks_teacher)).setText(mark.getProwadzacy());
        return view;
    }
}

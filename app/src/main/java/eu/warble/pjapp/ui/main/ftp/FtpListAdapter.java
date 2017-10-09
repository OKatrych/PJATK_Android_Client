package eu.warble.pjapp.ui.main.ftp;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcraft.jsch.ChannelSftp;

import java.util.Vector;

import eu.warble.pjapp.R;

public class FtpListAdapter extends BaseAdapter implements Filterable{

    private Vector<ChannelSftp.LsEntry> allFiles;
    private Vector<ChannelSftp.LsEntry> displayedFiles;
    private LayoutInflater inflater;
    private Filter filter;

    public FtpListAdapter(@NonNull Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.allFiles = new Vector<>();
        this.displayedFiles = new Vector<>();
        initFilter();
    }

    public void updateList(Vector<ChannelSftp.LsEntry> update){
        allFiles.clear();
        displayedFiles.clear();
        if (!update.isEmpty()){
            update.remove(0);
            allFiles.addAll(update);
            displayedFiles.addAll(update);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return displayedFiles.size();
    }

    @Override
    public ChannelSftp.LsEntry getItem(int i) {
        return displayedFiles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        if (view == null){
            view = inflater.inflate(R.layout.list_item_ftp, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.fileName = view.findViewById(R.id.file_name);
            viewHolder.date = view.findViewById(R.id.file_editing_date);
            viewHolder.icon = view.findViewById(R.id.file_image);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ChannelSftp.LsEntry file = getItem(i);
        if (file.getAttrs().isDir())
            viewHolder.icon.setImageResource(R.drawable.folder);
        else
            setFileIcon(file.getFilename(), viewHolder.icon);
        viewHolder.fileName.setText(file.getFilename());
        viewHolder.date.setText(file.getAttrs().getMtimeString());
        viewHolder.position = i;

        return view;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private void initFilter(){
        filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                Vector<ChannelSftp.LsEntry> tmpFiles = new Vector<>(allFiles.capacity());
                if (charSequence == null || charSequence.length() == 0) {
                    results.count = allFiles.size();
                    results.values = allFiles;
                }else {
                    charSequence = charSequence.toString().toLowerCase();
                    for (ChannelSftp.LsEntry file : allFiles){
                        if (file.getFilename().toLowerCase().startsWith(charSequence.toString())){
                            tmpFiles.add(file);
                        }
                    }
                    results.count = tmpFiles.size();
                    results.values = tmpFiles;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                displayedFiles.clear();
                displayedFiles.addAll((Vector<ChannelSftp.LsEntry>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    private void setFileIcon(String file, ImageView view){
        file = file.toLowerCase();
        if (file.endsWith("doc") || file.endsWith("docx"))
            view.setImageResource(R.drawable.file_word);
        else if (file.endsWith("pdf"))
            view.setImageResource(R.drawable.file_pdf);
        else if (file.endsWith("xls") || file.endsWith("xlsx"))
            view.setImageResource(R.drawable.file_excel);
        else if (file.endsWith("ppt") || file.endsWith("pptx") || file.endsWith("pps"))
            view.setImageResource(R.drawable.file_powerpoint);
        else if (file.endsWith("jpg") || file.endsWith("bmp") || file.endsWith("png") || file.endsWith("tiff"))
            view.setImageResource(R.drawable.file_powerpoint);
        else if (file.endsWith("mp3") || file.endsWith("flac") || file.endsWith("wav") || file.endsWith("ogg") || file.endsWith("aac") || file.endsWith("snd"))
            view.setImageResource(R.drawable.file_music);
        else if (file.endsWith("avi") || file.endsWith("mp4") || file.endsWith("mkw") || file.endsWith("flv") || file.endsWith("mov"))
            view.setImageResource(R.drawable.file_video);
        else
            view.setImageResource(R.drawable.file);
    }

    private static class ViewHolder {
        TextView fileName;
        TextView date;
        ImageView icon;
        int position;
    }
}
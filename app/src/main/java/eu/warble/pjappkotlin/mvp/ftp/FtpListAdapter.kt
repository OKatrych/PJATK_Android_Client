package eu.warble.pjappkotlin.mvp.ftp

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import eu.warble.getter.model.GetterFile
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.utils.inflate
import android.widget.TextView
import kotlinx.android.synthetic.main.list_item_ftp.view.file_editing_date
import kotlinx.android.synthetic.main.list_item_ftp.view.file_image
import kotlinx.android.synthetic.main.list_item_ftp.view.file_name

class FtpListAdapter(
        ftpData: List<GetterFile>,
        private val onItemClick: (GetterFile) -> Unit
) : RecyclerView.Adapter<FtpListAdapter.ViewHolder>(), Filterable {

    private var items = ArrayList<GetterFile>(ftpData)
    private var displayedItems = ArrayList<GetterFile>(ftpData)

    fun updateList(newData: ArrayList<GetterFile>) {
        items.clear()
        displayedItems.clear()
        if (newData.isNotEmpty()) {
            newData.removeAt(0)
            displayedItems.addAll(newData)
            items.addAll(newData)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return displayedItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(displayedItems[position], onItemClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.list_item_ftp))
    }

    private fun getFileIcon(fileName: String): Int {
        var file = fileName
        file = file.toLowerCase()
        return when {
            file.endsWith("pdf") -> R.drawable.file_pdf
            file.endsWith("doc") || file.endsWith("docx") -> R.drawable.file_word
            file.endsWith("xls") || file.endsWith("xlsx") -> R.drawable.file_excel
            file.endsWith("ppt") || file.endsWith("pptx") || file.endsWith("pps") -> R.drawable.file_powerpoint
            file.endsWith("jpg") || file.endsWith("bmp") || file.endsWith("png") || file.endsWith("tiff") -> R.drawable.file_image
            file.endsWith("avi") || file.endsWith("mp4") || file.endsWith("mkw") || file.endsWith("flv") || file.endsWith("mov") -> R.drawable.file_video
            file.endsWith("mp3") || file.endsWith("flac") || file.endsWith("wav") || file.endsWith("ogg") || file.endsWith("aac") || file.endsWith("snd") -> R.drawable.file_music
            else -> R.drawable.file
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val results = FilterResults()
                val tmpFiles = ArrayList<GetterFile>(items.size)
                if (charSequence == null || charSequence.isEmpty()) {
                    results.count = items.size
                    results.values = items
                } else {
                    val searchedSequence = charSequence.toString().toLowerCase()
                    items.filterTo(tmpFiles) { item ->
                        item.name.toLowerCase().startsWith(searchedSequence)
                    }
                    results.count = tmpFiles.size
                    results.values = tmpFiles
                }
                return results
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                displayedItems.clear()
                displayedItems.addAll(filterResults.values as ArrayList<GetterFile>)
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fileName: TextView = itemView.file_name
        private val date: TextView = itemView.file_editing_date
        private val icon: ImageView = itemView.file_image

        fun bind(file: GetterFile, onItemClick: (GetterFile) -> Unit) {
            if (file.isDirectory()) {
                icon.setImageResource(R.drawable.folder)
            } else {
                icon.setImageResource(getFileIcon(file.name))
            }
            fileName.text = file.name
            date.text = file.time()
            itemView.setOnClickListener {
                onItemClick(file)
            }
        }
    }
}
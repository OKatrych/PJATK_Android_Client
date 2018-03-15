package eu.warble.pjappkotlin.mvp.map.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.indoorway.android.common.sdk.model.IndoorwayObjectId
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.utils.inflate
import kotlinx.android.synthetic.main.list_item_map.view.map_name

class MapsListAdapter(
        maps: List<IndoorwayObjectId>,
        private val onItemClick: (IndoorwayObjectId) -> Unit
) : RecyclerView.Adapter<MapsListAdapter.ViewHolder>() {

    private var items = ArrayList<IndoorwayObjectId>(maps)

    fun updateList(newData: List<IndoorwayObjectId>) {
        items.clear()
        items.addAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], onItemClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.list_item_map))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mapName: TextView = itemView.map_name

        fun bind(map: IndoorwayObjectId, onItemClick: (IndoorwayObjectId) -> Unit) {
            itemView.setOnClickListener { onItemClick(map) }
            mapName.text = map.name
        }
    }
}
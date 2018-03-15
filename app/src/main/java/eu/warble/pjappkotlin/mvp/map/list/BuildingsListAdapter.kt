package eu.warble.pjappkotlin.mvp.map.list

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.indoorway.android.common.sdk.model.IndoorwayBuilding
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.utils.inflate
import kotlinx.android.synthetic.main.list_item_map_building.view.building_name
import kotlinx.android.synthetic.main.list_item_map_building.view.building_tag
import kotlinx.android.synthetic.main.list_item_map_building.view.color_container

class BuildingsListAdapter(
        buildings: List<IndoorwayBuilding>,
        private val onItemClick: (IndoorwayBuilding) -> Unit
) : RecyclerView.Adapter<BuildingsListAdapter.ViewHolder>() {

    private var items = ArrayList<IndoorwayBuilding>(buildings)

    fun updateList(newData: List<IndoorwayBuilding>) {
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
        return ViewHolder(parent.inflate(R.layout.list_item_map_building))
    }

    private fun getBuildingTag(buildingName: String?): String {
        return when {
            buildingName != null && buildingName.isNotBlank() -> {
                buildingName.substring(buildingName.length - 1, buildingName.length)
            }
            else -> "TAG"
        }
    }

    private fun getBuildingColor(context: Context, buildingTag: String): Int {
        return when (buildingTag) {
            "A" -> ContextCompat.getColor(context, R.color.buildingA)
            "C" -> ContextCompat.getColor(context, R.color.buildingC)
            "G" -> ContextCompat.getColor(context, R.color.buildingG)
            else -> ContextCompat.getColor(context, R.color.building)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val buildingTag: TextView = itemView.building_tag
        private val buildingName: TextView = itemView.building_name
        private val coloredSquare: View = itemView.color_container

        fun bind(building: IndoorwayBuilding, onItemClick: (IndoorwayBuilding) -> Unit) {
            itemView.setOnClickListener { onItemClick(building) }
            val tag = getBuildingTag(building.name)
            buildingTag.text = tag
            buildingName.text = building.name
            coloredSquare.setBackgroundColor(getBuildingColor(itemView.context, tag))
        }
    }
}
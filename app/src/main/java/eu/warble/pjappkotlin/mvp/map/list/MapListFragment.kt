package eu.warble.pjappkotlin.mvp.map.list

import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.indoorway.android.common.sdk.model.IndoorwayBuilding
import com.indoorway.android.common.sdk.model.IndoorwayObjectId
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BaseActivity
import eu.warble.pjappkotlin.mvp.BaseFragment
import eu.warble.pjappkotlin.utils.Constants.EMPTY_STRING
import kotlinx.android.synthetic.main.fragment_map_list.view.btn_current_location
import kotlinx.android.synthetic.main.fragment_map_list.view.items_list
import kotlinx.android.synthetic.main.fragment_map_list.view.loading_screen

class MapListFragment : BaseFragment(), MapListContact.View {

    override val TAG: String = "MapListFragment"

    override lateinit var presenter: MapListContact.Presenter
    override val applicationNavigator: ApplicationNavigator by lazy {
        ApplicationNavigator(activity as BaseActivity)
    }
    private lateinit var itemsList: RecyclerView
    private lateinit var locationFAB: FloatingActionButton
    private lateinit var loadingScreen: View
    private lateinit var buildingsAdapter: BuildingsListAdapter
    private lateinit var mapsAdapter: MapsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_map_list, container, false)
        initViews(view)
        initAdapter()
        initListener()
        initPresenter()
        return view
    }

    private fun initViews(view: View) {
        itemsList = view.items_list
        locationFAB = view.btn_current_location
        loadingScreen = view.loading_screen
    }

    private fun initListener() {
        locationFAB.setOnClickListener {
            findMyLocation()
        }
    }

    private fun initAdapter() {
        buildingsAdapter = BuildingsListAdapter(emptyList(), { buildingItem: IndoorwayBuilding ->
            presenter.loadMaps(buildingItem)
        })
        mapsAdapter = MapsListAdapter(emptyList(), { mapItem: IndoorwayObjectId ->
            showMap(presenter.selectedBuilding?.uuid ?: EMPTY_STRING, mapItem)
        })
    }

    override fun updateMapsListItems(items: List<IndoorwayObjectId>) {
        mapsAdapter.updateList(items)
        itemsList.adapter = mapsAdapter
        itemsList.layoutManager = LinearLayoutManager(mContext)
        itemsList.invalidate()
    }

    override fun updateBuildingsListItems(items: List<IndoorwayBuilding>) {
        buildingsAdapter.updateList(items)
        itemsList.adapter = buildingsAdapter
        itemsList.layoutManager = GridLayoutManager(mContext, 2)
        itemsList.invalidate()
    }

    override fun showMap(buildingUUID: String, map: IndoorwayObjectId) {
        applicationNavigator.goToMapActivity(buildingUUID, map)
    }

    private fun findMyLocation() {
        applicationNavigator.goToMapActivity()
    }

    override fun showLoadingScreen(show: Boolean) {
        loadingScreen.visibility = if (show) View.VISIBLE else View.GONE
        locationFAB.visibility = if (show) View.GONE else View.VISIBLE
    }

    override fun onBack(): Boolean {
        return when (itemsList.adapter) {
            is MapsListAdapter -> {
                presenter.loadBuildings()
                true
            }
            else -> false
        }
    }

    private fun initPresenter() {
        presenter = MapListPresenter(
                this,
                mContext as Context
        )
        presenter.start()
    }

    companion object {

        fun newInstance() = MapListFragment()

    }
}
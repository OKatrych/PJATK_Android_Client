package eu.warble.pjappkotlin.data

import android.content.Context
import eu.warble.pjappkotlin.data.local.ScheduleLocalDataSource
import eu.warble.pjappkotlin.data.model.ZajeciaItem
import eu.warble.pjappkotlin.data.remote.PjatkAPI
import org.threeten.bp.LocalDate

class ScheduleDataRepository private constructor(
        private val scheduleRemoteDataSource: ScheduleDataSource
) : ScheduleDataSource {

    private val scheduleLocalDataSource = ScheduleLocalDataSource

    private var cachedData: List<ZajeciaItem>? = null

    private var cacheIsDirty = false

    override fun getScheduleData(
            appContext: Context,
            from: LocalDate,
            to: LocalDate,
            callback: ScheduleDataSource.LoadScheduleDataCallback
    ) {
        // Respond immediately with cache if available and not dirty
        val mCachedData = cachedData
        if (mCachedData != null && !cacheIsDirty) {
            callback.onDataLoaded(mCachedData)
            return
        }
        if (cacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getScheduleDataFromRemoteDataSource(appContext, from, to, callback)
        } else {
            // Query the local storage if available. If not, query the network.
            scheduleLocalDataSource.getScheduleData(appContext, from, to, object : ScheduleDataSource.LoadScheduleDataCallback {
                override fun onDataLoaded(scheduleData: List<ZajeciaItem>) {
                    refreshCache(scheduleData)
                    callback.onDataLoaded(scheduleData)
                }

                override fun onDataNotAvailable(error: String) {
                    getScheduleDataFromRemoteDataSource(appContext, from, to, callback)
                }
            })
        }
    }

    fun saveScheduleData(appContext: Context, scheduleData: List<ZajeciaItem>) {
        scheduleLocalDataSource.saveScheduleData(appContext, scheduleData)
        // Do in memory cache update to keep the app UI up to date
        if (cachedData == null) {
            cachedData = scheduleData
        }
    }

    fun refreshScheduleData() {
        cacheIsDirty = true
    }

    fun deleteAllLocalScheduleData(appContext: Context) {
        scheduleLocalDataSource.deleteAllScheduleData(appContext)
        cachedData = null
    }

    private fun getScheduleDataFromRemoteDataSource(
            appContext: Context,
            from: LocalDate,
            to: LocalDate,
            callback: ScheduleDataSource.LoadScheduleDataCallback
    ) {
        scheduleRemoteDataSource.getScheduleData(appContext, from, to, object : ScheduleDataSource.LoadScheduleDataCallback {
            override fun onDataLoaded(scheduleData: List<ZajeciaItem>) {
                refreshCache(scheduleData)
                refreshLocalDataSource(appContext, scheduleData)
                callback.onDataLoaded(scheduleData)
            }

            override fun onDataNotAvailable(error: String) {
                callback.onDataNotAvailable(error)
            }
        })
    }

    private fun refreshCache(scheduleData: List<ZajeciaItem>) {
        cachedData = null
        cachedData = scheduleData
        cacheIsDirty = false
    }

    private fun refreshLocalDataSource(appContext: Context, scheduleData: List<ZajeciaItem>) {
        scheduleLocalDataSource.deleteAllScheduleData(appContext)
        scheduleLocalDataSource.saveScheduleData(appContext, scheduleData)
    }

    companion object {
        private var INSTANCE: ScheduleDataRepository? = null

        @JvmStatic
        fun getInstance(scheduleRemoteDataSource: ScheduleDataSource): ScheduleDataRepository {
            return INSTANCE ?: ScheduleDataRepository(scheduleRemoteDataSource).apply {
                INSTANCE = this
            }
        }

        @JvmStatic
        fun destroyInstance() {
            PjatkAPI.destroyInstance()
            INSTANCE = null
        }
    }
}
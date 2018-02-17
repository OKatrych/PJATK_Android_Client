package eu.warble.pjappkotlin.mvp.list

import android.content.Context
import eu.warble.pjappkotlin.data.StudentDataRepository
import eu.warble.pjappkotlin.data.StudentDataSource
import eu.warble.pjappkotlin.data.model.Student
import eu.warble.pjappkotlin.mvp.studentinfo.fees.adapter.PaymentsListAdapter
import eu.warble.pjappkotlin.mvp.studentinfo.fees.adapter.ReceivablesListAdapter
import eu.warble.pjappkotlin.mvp.studentinfo.marks.MarksListAdapter
import eu.warble.pjappkotlin.utils.Constants.ListActivityAdapterType.MARKS
import eu.warble.pjappkotlin.utils.Constants.ListActivityAdapterType.RECEIVABLE
import eu.warble.pjappkotlin.utils.Constants.ListActivityAdapterType.PAYMENT


class ListPresenter(
        private val studentDataRepository: StudentDataRepository?,
        val view: ListContract.View,
        private val appContext: Context,
        private val adapterType: String?
) : ListContract.Presenter {

    override fun start() {
        studentDataRepository?.getStudentData(appContext, object : StudentDataSource.LoadStudentDataCallback {
            override fun onDataLoaded(studentData: Student) {
                initAdapter(studentData)
            }
            override fun onDataNotAvailable(error: String) {
                view.showError(error)
            }
        })
    }

    private fun initAdapter(studentData: Student) {
        when (adapterType) {
            null -> view.showError("getIntent() - AdapterType is null")
            MARKS -> view.setAdapter(MarksListAdapter(studentData))
            RECEIVABLE -> view.setAdapter(ReceivablesListAdapter(studentData))
            PAYMENT -> view.setAdapter(PaymentsListAdapter(studentData))
            else -> view.showError("getIntent() - AdapterType is wrong")
        }
    }

}
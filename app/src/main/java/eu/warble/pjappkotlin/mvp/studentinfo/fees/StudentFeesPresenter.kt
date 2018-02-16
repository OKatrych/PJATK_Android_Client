package eu.warble.pjappkotlin.mvp.studentinfo.fees

import android.content.Context
import eu.warble.pjappkotlin.data.StudentDataRepository
import eu.warble.pjappkotlin.data.StudentDataSource
import eu.warble.pjappkotlin.data.model.Student

class StudentFeesPresenter(
        private val studentDataRepository: StudentDataRepository?,
        val view: StudentFeesContract.View,
        private val appContext: Context
) : StudentFeesContract.Presenter {

    override fun start() {
        studentDataRepository?.getStudentData(appContext, object : StudentDataSource.LoadStudentDataCallback {
            override fun onDataLoaded(studentData: Student) {
                fillViews(studentData)
            }

            override fun onDataNotAvailable(error: String) {
                view.showError(error)
            }
        })
    }

    private fun fillViews(studentData: Student) {
        with(view) {
            //balance card
            setBalance(studentData.saldo.toString())
            setBalancePayments(studentData.kwotaWplat.toString())
            setBalanceReceivables(studentData.kwotaNaleznosci.toString())
            setBalanceWithdrawals(studentData.kwotaWyplat.toString())
            //payments card
            val platnosci = studentData.platnosci
            if (platnosci?.isNotEmpty() == true) {
                setPaymentsDate1(platnosci[platnosci.size - 1].dataWplaty)
                setPaymentsDate1Amount(platnosci[platnosci.size - 1].kwota.toString())
                if (platnosci.size >= 2) {
                    setPaymentsDate2(platnosci[platnosci.size - 2].dataWplaty)
                    setPaymentsDate2Amount(platnosci[platnosci.size - 2].kwota.toString())
                }
            }
            //receivables card
            val oplaty = studentData.oplaty
            if (oplaty?.isNotEmpty() == true) {
                setReceivablesItem1(oplaty[oplaty.size - 1].nazwa)
                setReceivablesItem1Date(oplaty[oplaty.size - 1].terminPlatnosci)
                setReceivablesItem1Amount(oplaty[oplaty.size - 1].kwota.toString())
                if (oplaty.size >= 2) {
                    setReceivablesItem2(oplaty[oplaty.size - 2].nazwa)
                    setReceivablesItem2Date(oplaty[oplaty.size - 2].terminPlatnosci)
                    setReceivablesItem2Amount(oplaty[oplaty.size - 2].kwota.toString())
                }
            }
            //balance card
            setAccount(studentData.kontoWplat)
        }
    }

}
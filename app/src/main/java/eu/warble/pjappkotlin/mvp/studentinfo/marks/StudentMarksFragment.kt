package eu.warble.pjappkotlin.mvp.studentinfo.marks

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.mvp.ApplicationNavigator
import eu.warble.pjappkotlin.mvp.BaseActivity
import eu.warble.pjappkotlin.mvp.BaseFragment
import eu.warble.pjappkotlin.utils.Constants
import eu.warble.pjappkotlin.utils.Injection
import kotlinx.android.synthetic.main.card_student_marks.marks_mark1
import kotlinx.android.synthetic.main.card_student_marks.marks_mark2
import kotlinx.android.synthetic.main.card_student_marks.marks_subject1
import kotlinx.android.synthetic.main.card_student_marks.marks_subject2
import kotlinx.android.synthetic.main.card_student_marks.marks_type1
import kotlinx.android.synthetic.main.card_student_marks.marks_type2
import kotlinx.android.synthetic.main.card_student_marks.view.btn_marks_history
import kotlinx.android.synthetic.main.card_student_marks_average.average_studies
import kotlinx.android.synthetic.main.card_student_marks_average.average_year


class StudentMarksFragment : BaseFragment(), StudentMarksContract.View {

    override lateinit var presenter: StudentMarksContract.Presenter
    override val TAG: String = "student_fragment_marks"

    override val applicationNavigator by lazy {
        ApplicationNavigator(activity as BaseActivity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_student_marks, container, false)
        initPresenter()
        initListeners(view)
        return view
    }

    private fun initPresenter() {
        presenter = StudentMarksPresenter(
                Injection.provideStudentDataRepository(mContext as Context),
                this,
                mContext as Context
        )
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    private fun initListeners(view: View) {
        view.btn_marks_history.setOnClickListener {
            applicationNavigator.goToListActivity(Constants.ListActivityAdapterType.MARKS)
        }
    }

    override fun setMarksSubject1(marksSubject1: String?) {
        marks_subject1.text = marksSubject1
    }

    override fun setMarksType1(marksType1: String?) {
        marks_type1.text = marksType1
    }

    override fun setMarksMark1(marksMark1: String?) {
        marks_mark1.text = marksMark1
    }

    override fun setMarksSubject2(marksSubject2: String?) {
        marks_subject2.text = marksSubject2
    }

    override fun setMarksType2(marksType2: String?) {
        marks_type2.text = marksType2
    }

    override fun setMarksMark2(marksMark2: String?) {
        marks_mark2.text = marksMark2
    }

    override fun setAverageYear(averageYear: String?) {
        average_year.text = averageYear
    }

    override fun setAverageStudies(averageStudies: String?) {
        average_studies.text = averageStudies
    }

    companion object {

        fun newInstance() = StudentMarksFragment()

    }
}
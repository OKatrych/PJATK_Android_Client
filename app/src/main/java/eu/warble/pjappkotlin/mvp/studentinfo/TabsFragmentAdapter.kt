package eu.warble.pjappkotlin.mvp.studentinfo

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.SparseArray
import eu.warble.pjappkotlin.R
import eu.warble.pjappkotlin.mvp.BaseFragment

class TabsFragmentAdapter constructor(
        private val context: Context?,
        private val fragments: SparseArray<BaseFragment>,
        fm: FragmentManager
) : FragmentPagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context?.getString(R.string.tab_student_info)
            1 -> context?.getString(R.string.tab_student_fees)
            2 -> context?.getString(R.string.tab_student_marks)
            else -> "Fragment"
        }
    }

    override fun getItem(position: Int): BaseFragment {
        return fragments.get(position)
    }

    override fun getCount(): Int {
        return fragments.size()
    }
}

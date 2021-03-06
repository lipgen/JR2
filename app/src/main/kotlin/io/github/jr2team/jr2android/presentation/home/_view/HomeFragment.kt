package io.github.jr2team.jr2android.presentation.home._view

import io.github.jr2team.jr2android.R
import io.github.jr2team.jr2android.presentation._base._view.BaseFragment
import io.github.jr2team.jr2android.presentation.home._viewmodel.HomeViewModel

class HomeFragment : BaseFragment<HomeViewModel>() {
    override var viewModel = HomeViewModel()
    override val layoutRes: Int = R.layout.fragment_home
    override val titleDefault: String
        get() = getString(R.string.main_fragment_title)

    override fun initContent() {
        showMainToolbar(false)
    }
}
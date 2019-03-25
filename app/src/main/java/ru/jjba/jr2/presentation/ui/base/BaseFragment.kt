package ru.jjba.jr2.presentation.ui.base

import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.support.v4.act
import ru.jjba.jr2.App
import ru.jjba.jr2.presentation.ui.util.InstanceStateProvider
import ru.jjba.jr2.presentation.ui.util.inflate
import ru.jjba.jr2.presentation.ui.util.isVisible
import ru.jjba.jr2.presentation.viewmodel.util.createFactory

abstract class BaseFragment<VT : ViewModel> : Fragment(), LifecycleObserver {
    internal open lateinit var viewModel: VT
    internal abstract val layoutRes: Int
    internal abstract val titleDefault: String

    private val textToSpeech: TextToSpeech = App.instance.tts
    private val savable = Bundle()

    protected fun <T> instanceState() =
            InstanceStateProvider.Nullable<T>(savable)

    protected fun <T> instanceState(defaultValue: T) =
            InstanceStateProvider.NotNull(savable, defaultValue)

    // TODO: Temporary solution, provide DI
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun injectViewModel() {
        ViewModelProviders
                .of(this/*, viewModel.createFactory()*/)
                .get(viewModel::class.java)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun initContent() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun observeData() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun saveInstanceState() {
    }

    fun setTitle(title: String) {
        act.tbMain.title = title
    }

    fun setTitle(spannableTitle: SpannableStringBuilder) {
        act.tbMain.title = spannableTitle
    }

    fun showMainToolbar(isShown: Boolean = true) {
        act.tbMain.isVisible = isShown
    }

    fun showBottomNavigation(isShown: Boolean = true) {
        act.bottomNavigation.isVisible = isShown
    }

    fun speakOut(text: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            savable.putAll(savedInstanceState.getBundle("_state"))
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            state: Bundle?
    ): View? = container?.inflate(layoutRes).also {
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(titleDefault)
        showMainToolbar()
        showBottomNavigation()
        lifecycle.addObserver(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBundle("_state", savable)
        super.onSaveInstanceState(outState)
    }

    override fun onPause() {
        lifecycle.removeObserver(this)
        super.onPause()
    }
}
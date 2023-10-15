//package com.example.time_late.views.base
//
//
//import android.annotation.SuppressLint
//import android.os.Build
//import android.os.Bundle
//import android.view.*
//import androidx.activity.OnBackPressedCallback
//import androidx.annotation.LayoutRes
//import androidx.appcompat.widget.Toolbar
//import androidx.core.content.ContextCompat
//import androidx.core.view.doOnPreDraw
//import androidx.databinding.DataBindingUtil
//import androidx.databinding.ViewDataBinding
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProvider
//import androidx.navigation.NavBackStackEntry
//import androidx.navigation.NavDirections
//import androidx.navigation.fragment.findNavController
//
//@Suppress("MemberVisibilityCanBePrivate", "RedundantOverride")
//abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel> : Fragment() {
//
//    protected lateinit var ui: UiManager<V, VM>
//    protected lateinit var actViewModel: BaseViewModel
//    protected open val needSpaceBottomNavigation = false
//    protected val viewModel: VM get() = ui.viewModel
//
//    private val onGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
//        ui.setNavigationBarSafeArea(ui.viewDataBinding.root, activity)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        val viewLifecycleOwner = this.viewLifecycleOwner
//        ui = UiManager(
//            DataBindingUtil.inflate(inflater, layoutRes, container, false),
//            ViewModelProvider(this, App.instance!!.viewModelFactory).get(viewModelClass)
//        )
//        actViewModel = ViewModelProvider(requireActivity(), App.instance!!.viewModelFactory).get(actViewModelClass)
//        d(viewModel, actViewModel)
//
//        ui.viewDataBinding.lifecycleOwner = viewLifecycleOwner
//
//        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
//            object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
//                    if (!viewModel.isOnLoading) {
//                        viewModel.onBackPressed()
//                    }
//                }
//            })
//
//        activity?.window?.let {
//            windowSoftInputMode(it)
//        }
//
//        return ui.viewDataBinding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        null.setActionBar()
//        initViews(savedInstanceState)
//        addObservers()
//        addLoadingObservers()
//        viewModel.onBackPressed.observeEvent(::onBackPressed)
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//    }
//
//
//    protected fun NavDirections.navigate() {
//        if (findNavController().currentDestination?.id == navId && navId != null) {
//            findNavController().navigate(this)
//        }
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
//        R.id.home -> {
//            onBackPressed()
//            true
//        }
//        else -> super.onOptionsItemSelected(item)
//    }
//
//    protected fun <T> LiveData<T>.observe(observer: (value: T) -> Unit) {
//        observe(viewLifecycleOwner, Observer(observer))
//    }
//
//    protected fun <T> LiveData<Event<T>>.observeEvent(observer: (value: T) -> Unit) {
//        observe(viewLifecycleOwner, EventObserver(observer))
//    }
//
//    protected fun LiveData<Event<Unit>>.observeEvent(observer: () -> Unit) {
//        observe(viewLifecycleOwner, EventObserver { observer.invoke() })
//    }
//
//    protected open fun setFullScreenTheme(isSet: Boolean = true) {
//        val activity = activity ?: return
//        val w = activity.window ?: return
//        val v = view ?: return
//        if (isSet) {
//            w.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//            v.systemUiVisibility = FLAG_FULLSCREEN
//        } else {
//            w.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//            v.systemUiVisibility = v.systemUiVisibility and FLAG_FULLSCREEN.inv()
//        }
//        displayCutoutShortMode(w)
//    }
//
//    protected open fun setTransparentLightTheme() {
//        val activity = activity ?: return
//        val w = activity.window ?: return
//        w.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        )
//        w.decorView.systemUiVisibility = 0
//        displayCutoutShortMode(w)
//    }
//
//    @SuppressLint("ObsoleteSdkInt")
//    protected open fun setDarkTheme() {
//        val activity = activity ?: return
//        val w = activity.window ?: return
//
//        w.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
//        w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        w.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        w.clearFlags(View.SYSTEM_UI_FLAG_LOW_PROFILE)
//        w.clearFlags(View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            w.clearFlags(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            w.clearFlags(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
//        }
//        w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
//        w.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
//            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
//        )
//        w.statusBarColor = ContextCompat.getColor(activity, R.color.defaultBg)
//        w.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//    }
//
//    protected open fun setTransparentDarkTheme() {
//        val activity = activity ?: return
//        val w = activity.window
//        w.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        )
//        w.decorView.systemUiVisibility = FLAG_TRANSPARENT_DARK_MODE
//        displayCutoutShortMode(w)
//    }
//
//    protected fun hideStatusBar() {
//        val activity = activity ?: return
//        val w = activity.window
//        w.decorView.systemUiVisibility = w.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_FULLSCREEN
//        displayCutoutShortMode(w)
////    ui.viewDataBinding.root.fitsSystemWindows = true
//    }
//
//    protected open fun windowSoftInputMode(w: Window) {
//        //w.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
//    }
//
//    protected open fun onBackPressed() {
//        if (!findNavController().navigateUp()) {
//            activity?.finish()
//        }
//    }
//
//    private fun addLoadingObservers() {
//        viewModel.onLoading.observeEvent { (actViewModel as? ILoadingHandler)?.requestLoading() }
//        viewModel.onFinishLoading.observeEvent { (actViewModel as? ILoadingHandler)?.requestFinishLoading() }
//    }
//
//    private fun displayCutoutShortMode(w: Window?) {
//        if (w == null) {
//            return
//        }
//        w.attributes.layoutInDisplayCutoutMode = FLAG_DISPLAY_CUTOUT_SHORT_EDGES
//    }
//
//    @get:LayoutRes
//    protected abstract val layoutRes: Int
//    protected abstract val navId: Int?
//    protected abstract val viewModelClass: Class<VM>
//    protected abstract val actViewModelClass: Class<out BaseViewModel>
//    protected abstract fun initViews(savedInstanceState: Bundle?)
//    protected abstract fun addObservers()
//
//    protected fun Toolbar?.setActionBar(
//        enableNavigationUp: Boolean = false,
//        navIcon: Int? = null/* = R.drawable.ic_nav_up*/,
//        navUpListener: () -> Unit = ::onBackPressed
//    ) {
//        this ?: return
//        if (enableNavigationUp) {
//            navIcon?.let(this::setNavigationIcon)
//            setNavigationOnClickListener { navUpListener.invoke() }
//        }
//
//    }
//
//    protected fun Fragment.waitForTransition(targetView: View) {
//        postponeEnterTransition()
//        targetView.doOnPreDraw { startPostponedEnterTransition() }
//    }
//
//    protected fun <T> navResultObserve(
//        key: String = "result",
//        clearValue: T? = null,
//        acceptValue: T? = null,
//        observer: (T) -> Unit = {}
//    ) {
//        getNavigationResult<T?>(key)?.observe {
//            it ?: return@observe
//            ui.viewDataBinding.root.post {
//                clearNavigationResult(clearValue, key)
//                if (acceptValue == null || it == acceptValue) {
//                    observer.invoke(it)
//                }
//            }
//        }
//    }
//
//    protected fun <T> Fragment.getNavigationResult(key: String = "result") =
//        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)
//
//    protected fun <T> Fragment.setNavigationResult(result: T, key: String = "result") {
//        findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
//    }
//}
//
//private val Fragment.currentBackStackEntry: NavBackStackEntry?
//    get() {
//        val navController = findNavController()
//        return navController.currentBackStackEntry?.takeUnless {
//            it.destination.navigatorName.contains("dialog")
//        } ?: let {
//            navController.previousBackStackEntry
//        }
//    }
//
//fun <T> Fragment.getNavigationResult(key: String = "result") =
//    currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)
//
//fun <T> Fragment.clearNavigationResult(clearValue: T, key: String = "result") =
//    currentBackStackEntry?.savedStateHandle?.set(key, clearValue)
//
//fun <T> Fragment.setNavigationResult(result: T, key: String = "result") {
//    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
//}

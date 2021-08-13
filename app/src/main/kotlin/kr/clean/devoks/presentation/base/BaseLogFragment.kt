package kr.clean.devoks.presentation.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.clean.devoks.core.logging.OKLogger.LoggerLife

/**
 * Created by devoks
 * Description :
 */
open class BaseLogFragment : Fragment(){

    /**
     *  Fragment Lifecycle Start
     */
    // @Deprecated
    override fun onAttachFragment(childFragment: Fragment) {
        LoggerLife.v("onAttachFragment - childFragment $childFragment")
        super.onAttachFragment(childFragment)
    }

    override fun onAttach(context: Context) {
        LoggerLife.v("$this onAttach ")
        super.onAttach(context)
    }

    // @Deprecated
    override fun onAttach(activity: Activity) {
         LoggerLife.v("$this onAttach on activity $activity")
        super.onAttach(activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
         LoggerLife.v(" $this onCreate  / $savedInstanceState")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         LoggerLife.v("$this onCreateView / $savedInstanceState")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         LoggerLife.v("$this onViewCreated  / $savedInstanceState")
        super.onViewCreated(view, savedInstanceState)
    }

    // @Deprecated
    override fun onActivityCreated(savedInstanceState: Bundle?) {
         LoggerLife.v("$this onActivityCreated / $savedInstanceState")
        super.onActivityCreated(savedInstanceState)
    }

    // State Save
    override fun onSaveInstanceState(outState: Bundle) {
        LoggerLife.v("$this onSaveInstanceState / $outState")
        super.onSaveInstanceState(outState)
    }

    // State Restore
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
         LoggerLife.v("$this onViewStateRestored / $savedInstanceState")
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onStart() {
         LoggerLife.v("$this onStart ")
        super.onStart()
    }

    override fun onResume() {
         LoggerLife.v(" $this onResume")
        super.onResume()
    }

    override fun onPause() {
         LoggerLife.v("$this onPause")
        super.onPause()
    }

    override fun onStop() {
         LoggerLife.v("$this onStop ")
        super.onStop()
    }

    override fun onDestroyView() {
         LoggerLife.v("$this onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        LoggerLife.v("$this onDestroy ")
        super.onDestroy()
    }

    override fun onDetach() {
         LoggerLife.v("$this onDetach")
        super.onDetach()
    }

    /**
     *  Fragment Lifecycle Finish
     */

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         LoggerLife.v("$this onActivityResult requestCode:$requestCode resultCode:$resultCode data=$data")
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onHiddenChanged(hidden: Boolean) {
         LoggerLife.v("$this onHiddenChanged hidden:$hidden")
        super.onHiddenChanged(hidden)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
         LoggerLife.v("$this onRequestPermissionsResult requestCode:$requestCode permissions:$permissions grantResults:$grantResults")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

//    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
//         LoggerLife.v("onCreateContextMenu - $menu")
//        super.onCreateContextMenu(menu, v, menuInfo)
//    }
//    override fun onContextItemSelected(item: MenuItem): Boolean {
//         LoggerLife.v("onContextItemSelected  - $item")
//        return super.onContextItemSelected(item)
//    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//         LoggerLife.v("onCreateOptionsMenu  - $menu")
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//         LoggerLife.v("onOptionsItemSelected  - $item")
//        return super.onOptionsItemSelected(item)
//    }
//
//    override fun onPrepareOptionsMenu(menu: Menu) {
//         LoggerLife.v("onPrepareOptionsMenu  - $menu")
//        super.onPrepareOptionsMenu(menu)
//    }
//
//    override fun onOptionsMenuClosed(menu: Menu) {
//         LoggerLife.v("onOptionsMenuClosed  - $menu")
//        super.onOptionsMenuClosed(menu)
//    }
//
//    override fun onDestroyOptionsMenu() {
//         LoggerLife.v("onDestroyOptionsMenu ")
//        super.onDestroyOptionsMenu()
//    }
//
//    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
//         LoggerLife.v("onCreateAnimation $this $transit")
//        return super.onCreateAnimation(transit, enter, nextAnim)
//    }
//
//    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? {
//         LoggerLife.v("onCreateAnimator $this $transit")
//        return super.onCreateAnimator(transit, enter, nextAnim)
//    }

    override fun onLowMemory() {
         LoggerLife.v("$this onLowMemory  -")
        super.onLowMemory()
    }

    override fun toString(): String {
        super.toString()
        val sb = StringBuilder(128)
        val cls: Class<*> = javaClass
        sb.append(cls.simpleName)
        sb.append("{")
        sb.append(Integer.toHexString(System.identityHashCode(this)))
        sb.append("}")
        return sb.toString()
    }

}
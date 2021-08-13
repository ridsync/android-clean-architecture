package kr.clean.devoks.presentation.widget

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.*
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import kr.clean.devoks.R

/**
 * Created by devoks
 * Description :
 */
private val defaultNavOptions = navOptions {
    anim {
        enter = R.anim.enter_slide_in_right
        exit = R.anim.exit_slide_out_left
        popEnter = R.anim.pop_enter_slide_in_left
        popExit = R.anim.pop_exit_slide_out_right
    }
}

private val emptyNavOptions = navOptions {}

class NavHostFragmentExtension : NavHostFragment() {

    override fun onCreateNavController(navController: NavController) {
        super.onCreateNavController(navController)
        navController.navigatorProvider.addNavigator(
            // this replaces FragmentNavigator
            FragmentNavigatorWithDefaultAnimations(requireContext(), childFragmentManager, id)
        )
    }

}

/**
 * Needs to replace FragmentNavigator and replacing is done with name in annotation.
 * Navigation method will use defaults for fragments transitions animations.
 */
@Navigator.Name("fragment")
class FragmentNavigatorWithDefaultAnimations(
    context: Context,
    manager: FragmentManager,
    containerId: Int
) : FragmentNavigator(context, manager, containerId) {

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        // this will try to fill in empty animations with defaults when no shared element transitions are set
        // https://developer.android.com/guide/navigation/navigation-animate-transitions#shared-element
        val shouldUseTransitionsInstead = navigatorExtras != null
        val lastNavOptions = if (shouldUseTransitionsInstead) navOptions
        else navOptions.fillEmptyAnimationsWithDefaults()
        return super.navigate(destination, args, lastNavOptions, navigatorExtras)
    }

    private fun NavOptions?.fillEmptyAnimationsWithDefaults(): NavOptions =
        this?.copyNavOptionsWithDefaultAnimations() ?: defaultNavOptions

    private fun NavOptions.copyNavOptionsWithDefaultAnimations(): NavOptions =
        let { originalNavOptions ->
            navOptions {
                launchSingleTop = originalNavOptions.shouldLaunchSingleTop()
                popUpTo(originalNavOptions.popUpTo) {
                    inclusive = originalNavOptions.isPopUpToInclusive
                }
                anim {
                    enter =
                        if (originalNavOptions.enterAnim == emptyNavOptions.enterAnim) defaultNavOptions.enterAnim
                        else originalNavOptions.enterAnim
                    exit =
                        if (originalNavOptions.exitAnim == emptyNavOptions.exitAnim) defaultNavOptions.exitAnim
                        else originalNavOptions.exitAnim
                    popEnter =
                        if (originalNavOptions.popEnterAnim == emptyNavOptions.popEnterAnim) defaultNavOptions.popEnterAnim
                        else originalNavOptions.popEnterAnim
                    popExit =
                        if (originalNavOptions.popExitAnim == emptyNavOptions.popExitAnim) defaultNavOptions.popExitAnim
                        else originalNavOptions.popExitAnim
                }
            }
        }

}
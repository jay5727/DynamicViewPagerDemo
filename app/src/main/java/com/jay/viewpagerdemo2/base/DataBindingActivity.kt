package com.jay.viewpagerdemo2.base

import android.content.DialogInterface
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.jay.viewpagerdemo2.model.EventDialog
import com.jay.viewpagerdemo2.utils.UtilityDialogs

/**
 * DataBindingActivity is an abstract class for providing [DataBindingUtil].
 * provides implementations of only [ViewDataBinding] from an abstract information.
 * Do not modify this class. This is a first-level abstraction class.
 * If you want to add more specifications, make another class which extends [DataBindingActivity].
 */
abstract class DataBindingActivity : AppCompatActivity() {

    private val utilityDialogs by lazy { UtilityDialogs(this) }

    protected inline fun <reified T : ViewDataBinding> binding(
        @LayoutRes resId: Int
    ): Lazy<T> = lazy { DataBindingUtil.setContentView<T>(this, resId) }

    /**
     * This method show the utility dialog for error
     * @param eventDialog  instance
     * @param primaryLambda function to be called on primary button click
     * @param secondaryLambda function to be called on secondary button click
     * @param onDismissListener listener for dismissal of alert dialog
     * @param isCancelable true to dismiss,false to not
     * */
    fun showDialog(
        eventDialog: EventDialog?,
        primaryLambda: (String) -> (Any) = {},
        secondaryLambda: () -> (Any) = {},
        onDismissListener: DialogInterface.OnDismissListener? = null,
        isCancelable: Boolean = true
    ) {
        eventDialog?.run {
            val dialog = utilityDialogs.dialogBuilder(
                title = eventDialog.textRefTitle,
                message = eventDialog.textRefMessage,
                secondaryLambda = secondaryLambda,
                primaryLambda = primaryLambda,
                secondaryBtnText = eventDialog.textRefButtonSecondary,
                primaryBtnText = eventDialog.textRefButtonPrimary,
                isCancelable = isCancelable
            )
            dialog?.show()
            dialog?.setOnDismissListener(onDismissListener)
        }
    }
}

package com.jay.viewpagerdemo2.utils

import android.app.AlertDialog
import android.content.Context
import android.text.InputFilter
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import com.jay.viewpagerdemo2.R


/**
 * A Utility class to display the Dialogs to the User
 */
class UtilityDialogs(val context: Context) {

    val TAG = UtilityDialogs::class.java.simpleName

    /**
     * Returns the AlertDialog
     * @param message the message to be displayed to the User
     * @param primaryLambda the lambda function to be executed if the user selects Yes
     * @param secondaryLambda the lambda function to be executed if the user selects No
     * @param primaryBtnText the text for the positive button
     * @param secondaryBtnText the text for the negative button
     * @param showSingleButton if only primary button on dialog
     * @param isCancelable true to dismiss,false to not
     */
    fun dialogBuilder(
        message: String? = null,
        primaryLambda: (String) -> (Any) = {},
        secondaryLambda: () -> (Any) = {},
        primaryBtnText: String? = context.getString(R.string.yes),
        secondaryBtnText: String? = context.getString(R.string.no), view: Int? = null,
        title: String? = null, showSingleButton: Boolean = false,
        isCancelable: Boolean = true
    ): AlertDialog {
        val builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)

        builder.setTitle(title)
        view?.let {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            builder.setView(inflater.inflate(it, null))
        }
        builder.setMessage(message)

        val blockCharacterSet = "^[^<>{}\"/|;:.,~!?@#$%^=&*\\]\\\\()\\[¿§«»ω⊙¤°℃℉€¥£¢¡®©0-9_+]*$"
        val filter = InputFilter { source, start, end, dest, dstart, dend ->
            if (source != null && blockCharacterSet.contains("" + source)) {
                ""
            } else null
        }

        val editText = EditText(context)
        editText.hint = context.getString(R.string.enter_name)
        editText.maxLines = 1
        editText.filters = arrayOf(filter);
        val layout = FrameLayout(context)

        //set padding in parent layout
        layout.setPaddingRelative(45, 15, 45, 0)
        layout.addView(editText)

        builder.setView(layout)
        builder.setPositiveButton(primaryBtnText) { dialog, _ ->
            if (primaryLambda == {})
                dialog.cancel()
            else {
                if (!editText.text.isNullOrEmpty()) {
                    val tabName = editText.text.toString()
                    primaryLambda.invoke(tabName)
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.please_enter_tab_name),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        if (!showSingleButton)
            builder.setNegativeButton(secondaryBtnText) { dialog, _ ->
                if (secondaryLambda == {})
                    dialog.cancel()
                else
                    run(secondaryLambda)
            }

        val alertDialog = builder.create()
        alertDialog.setCancelable(isCancelable)
        return alertDialog
    }
}
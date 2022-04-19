package com.app.base.ui

import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.app.base.R
import com.app.base.extensions.hideProgress
import com.app.base.extensions.showProgress
import com.app.base.utils.NetworkStateDialogCallback
import com.app.base.utils.ProgressDialogCallback
import com.app.interfaces.models.common.Message
import com.google.android.material.dialog.MaterialAlertDialogBuilder

abstract class BaseFragment : Fragment(), ProgressDialogCallback, NetworkStateDialogCallback {

    override fun onErrorDialogRetryButtonClicked(endpointTag: String) {
        hideErrorDialog()
    }

    override fun onErrorDialogClosed(endpointTag: String) {
        hideErrorDialog()
    }

    override fun onProgressDialogCancelled() {

    }

    public fun isUsingNightModeResources(): Boolean {
        return when (resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> true
            Configuration.UI_MODE_NIGHT_NO -> false
            Configuration.UI_MODE_NIGHT_UNDEFINED -> false
            else -> false
        }
    }

    fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun onErrorSimple(code: Int = 0, message: String, endpointTag: String? = null) {
        if (message.isNotEmpty() && message.isNotBlank())
            showErrorDialog(code, message, endpointTag)
    }

    private fun showErrorDialog(code: Int = 0, message: String = "", endpointTag: String?) {
        try {
            NetworkStateDialogFragment.newInstance(code, message, endpointTag)
                .show(childFragmentManager, "NetworkStateDialogFragment")
        } catch (e: Exception) {

        }

    }

    private fun hideErrorDialog() {
        try {
            childFragmentManager.findFragmentByTag("NetworkStateDialogFragment")?.let {
                (it as DialogFragment).dismissAllowingStateLoss()
            }
        } catch (e: Exception) {

        }
    }

    protected fun showPopupMenu(
        context: Context,
        ivMenuIcon: View, @MenuRes menuRes: Int,
        onEdit: () -> Unit,
        onDelete: () -> Unit
    ) {
        val popup = PopupMenu(context, ivMenuIcon)
        popup.inflate(menuRes)
        popup.setOnMenuItemClickListener { item: MenuItem? ->
            /* when (item!!.itemId) {
                 R.id.delete -> {
                     onDelete()
                 }
                 R.id.edit -> {
                     onEdit()
                 }
                 else -> onDelete()
             }*/
            true
        }
        popup.show()
    }

    protected fun showDeleteAlertDialog(
        @StringRes titleResId: Int,
        @StringRes messageResId: Int = R.string.are_you_sure_you_want_to_delete_this_item,
        callAPI: () -> Unit
    ) {
        context?.let { context ->
            MaterialAlertDialogBuilder(context)
                .setTitle(titleResId)
                .setMessage(messageResId)
                .setPositiveButton(R.string.yes) { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                    callAPI()
                }
                .setNegativeButton(R.string.no) { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                }
                .show()
        }
    }


    protected fun showDeleteAlertDialog(
        title: String,
        message: String = getString(R.string.are_you_sure_you_want_to_delete_this_item),
        callAPI: () -> Unit
    ) {
        context?.let { context ->
            MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.yes) { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                    callAPI()
                }
                .setNegativeButton(R.string.no) { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                }
                .show()
        }
    }

    protected fun showDeleteAlertDialog(
        @StringRes titleResId: Int,
        @StringRes messageResId: Int = R.string.are_you_sure_you_want_to_delete_this_item,
        @StringRes positiveButtonText: Int = R.string.yes,
        @StringRes negitiveButtonText: Int = R.string.no,
        callAPI: () -> Unit
    ) {
        context?.let { context ->
            MaterialAlertDialogBuilder(context)
                .setTitle(titleResId)
                .setMessage(messageResId)
                .setPositiveButton(positiveButtonText) { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                    callAPI()
                }
                .setNegativeButton(negitiveButtonText) { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                }
                .show()
        }
    }

    protected fun showDeleteAlertDialog(
        @StringRes titleResId: Int,
        messageResId: String = getString(R.string.are_you_sure_you_want_to_delete_this_item),
        @StringRes positiveButtonText: Int = R.string.yes,
        @StringRes negitiveButtonText: Int = R.string.no,
        callAPI: () -> Unit
    ) {
        context?.let { context ->
            val d = AlertDialog.Builder(context, R.style.Base_Theme_MyApp_Home)
            d.setTitle(titleResId)
            d.setMessage(messageResId)
            d.setPositiveButton(positiveButtonText) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
                callAPI()
            }
            d.setNegativeButton(negitiveButtonText) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }
            d.show()
        }
    }

    fun onLoading(isLoading: Boolean) {
        if (isAdded)
            if (isLoading) {
                showProgress()
            } else {
                hideProgress()
            }
    }

    fun onErrorDialog(error: Message?, tag: String? = null) {
        error?.let {
            onErrorSimple(it.code, it.message, tag)
        }
    }

    fun onErrorToast(error: Message?) {
        error?.let {
            toast(error.code.toString() + "\n" + it.message)
        }
    }


    override fun onDestroyView() {
        hideProgress()
        hideErrorDialog()
        super.onDestroyView()
    }
}
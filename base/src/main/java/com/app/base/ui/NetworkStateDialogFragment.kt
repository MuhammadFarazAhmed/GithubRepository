package com.app.base.ui

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.app.base.databinding.NetworkStateDialogLayoutBinding
import com.app.base.utils.NetworkStateDialogCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NetworkStateDialogFragment : DialogFragment() {

    private var code: Int = 0
    private var message: String = ""
    private var endpointTag: String = ""
    lateinit var binding: NetworkStateDialogLayoutBinding
    private var mListener: NetworkStateDialogCallback? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(true)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        }
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            code = it.getInt(ARG_PARAM1, 0)
            message = it.getString(ARG_PARAM2, "")
            endpointTag = it.getString(ARG_PARAM3, "")
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onButtonPressed()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NetworkStateDialogLayoutBinding.inflate(inflater, container, false)
        binding.errorType = code
        binding.error = message
        binding.endpointTag = endpointTag
        binding.callback = mListener
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (endpointTag.isEmpty()) {
            binding.bRetryButton.visibility = View.GONE
        } else {
            binding.bRetryButton.visibility = View.VISIBLE
        }
    }

    private fun onButtonPressed() {
        mListener?.onErrorDialogClosed(endpointTag)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val parent = parentFragment
        if (parent is NetworkStateDialogCallback) {
            mListener = parent
        } else if (context is NetworkStateDialogCallback) {
            mListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    companion object {

        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"
        private val ARG_PARAM3 = "param3"

        fun newInstance(code: Int = 0, message: String = "", endpointTag: String?): NetworkStateDialogFragment {
            val fragment = NetworkStateDialogFragment()
            val args = Bundle()
            args.putInt(ARG_PARAM1, code)
            args.putString(ARG_PARAM2, message)
            if (endpointTag != null)
                args.putString(ARG_PARAM3, endpointTag)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor

package com.farooq.core.components.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.farooq.core.R
import com.farooq.core.databinding.FragmentErrorSheetListDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

// TODO: Customize parameter argument names
const val ARG_TITLE = "title_error"
const val ARG_MESSAGE = "message_error"

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    ErrorSheetFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
@AndroidEntryPoint
class ErrorSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentErrorSheetListDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var title: String = ""
    private var message: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialogTheme
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentErrorSheetListDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        title = requireArguments().getString(ARG_TITLE) ?: ""
        message = requireArguments().getString(ARG_MESSAGE) ?: ""

        binding.titleTxt.text = title.ifEmpty { "Oop,s" }
        binding.descTxt.text = message.ifEmpty { "Something went wrong" }
        binding.dismissBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    companion object {
        fun bundle(title: String, message: String) = Bundle().apply {
            putString(ARG_TITLE, title)
            putString(ARG_MESSAGE, message)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
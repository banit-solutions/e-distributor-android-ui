package ke.co.banit.e_distributor_android.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ke.co.banit.e_distributor_android.R

/**
 * @Author: Angatia Benson
 * @Date: 08/08/2022
 * Copyright (c) 2022 Bantechnis
 */


class ProgressDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.loading_view, container)
    }

    companion object {
        fun newInstance(): ProgressDialogFragment {
            return ProgressDialogFragment()
        }
    }
}
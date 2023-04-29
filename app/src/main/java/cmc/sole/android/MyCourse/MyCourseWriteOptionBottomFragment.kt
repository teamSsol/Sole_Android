package cmc.sole.android.MyCourse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cmc.sole.android.databinding.BottomFragmentMyCourseWriteOptionBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyCourseWriteOptionBottomFragment: BottomSheetDialogFragment() {

    lateinit var binding: BottomFragmentMyCourseWriteOptionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomFragmentMyCourseWriteOptionBinding.inflate(inflater, container, false)

        initClickListener()

        return binding.root
    }

    private fun initClickListener() {

    }
}
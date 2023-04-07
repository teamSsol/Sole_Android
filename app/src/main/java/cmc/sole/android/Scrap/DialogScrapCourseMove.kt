package cmc.sole.android.Scrap

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.Scrap.Retrofit.*
import cmc.sole.android.databinding.DialogScrapMoveFolderBinding

class DialogScrapCourseMove: DialogFragment(), ScrapCourseMoveView, ScrapFolderView {

    lateinit var binding: DialogScrapMoveFolderBinding
    private var scrapFolderId: Int = -1
    private var moveCourseId = ArrayList<Int>()
    private lateinit var scrapCourseMoveRVAdapter: ScrapCourseMoveRVAdapter
    private var folderList = ArrayList<ScrapFolderDataResult>()
    lateinit var scrapService: ScrapService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogScrapMoveFolderBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setGravity(Gravity.CENTER)

        scrapFolderId = requireArguments().getInt("scrapFolderId")
        arguments?.getIntegerArrayList("courseId")?.let { moveCourseId.addAll(it) }

        initAdapter()
        initService()
        initClickListener()

        return binding.root
    }

    private fun initAdapter() {
        scrapCourseMoveRVAdapter = ScrapCourseMoveRVAdapter(folderList)
        binding.scrapMoveFolderRv.adapter = scrapCourseMoveRVAdapter
        binding.scrapMoveFolderRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        scrapCourseMoveRVAdapter.setOnItemClickListener(object: ScrapCourseMoveRVAdapter.OnItemClickListener {
            override fun onItemClick(data: ScrapFolderDataResult, position: Int) {
                Log.d("API-TEST", "data.scrapFolderId = ${data.scrapFolderId}, moveCourseId = $moveCourseId")
                scrapService.moveScrapCourse(data.scrapFolderId, moveCourseId)
            }
        })
    }

    private fun initService() {
        scrapService = ScrapService()
        scrapService.setScrapFolderView(this)
        scrapService.setScrapCourseMoveView(this)
        scrapService.getScrapFolder()
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL)
    }

    private fun initClickListener() {

    }

    override fun scrapCourseMoveSuccessView(scrapCourseMoveResult: ScrapCourseMoveResult) {
        Log.d("API-TEST", "scrapCourseMoveResult = $scrapCourseMoveResult")
    }

    override fun scrapCourseMoveFailureView() {

    }

    override fun scrapFolderSuccessView(scrapFolderDataResult: ArrayList<ScrapFolderDataResult>) {
        scrapCourseMoveRVAdapter.addAllItems(scrapFolderDataResult)
    }

    override fun scrapFolderFailureView() {

    }
}
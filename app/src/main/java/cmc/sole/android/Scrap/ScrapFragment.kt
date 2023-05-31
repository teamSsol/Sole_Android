package cmc.sole.android.Scrap

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import cmc.sole.android.R
import cmc.sole.android.Scrap.Retrofit.ScrapFolderDataResult
import cmc.sole.android.Scrap.Retrofit.ScrapFolderView
import cmc.sole.android.Scrap.Retrofit.ScrapService
import cmc.sole.android.Scrap.Retrofit.addFolder

import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.databinding.FragmentScrapBinding
import androidx.fragment.app.Fragment
import cmc.sole.android.databinding.FragmentFollowerFollowerBinding

class ScrapFragment: Fragment(),
    ScrapFolderView {

    private var _binding: FragmentScrapBinding? = null
    private val binding get() = _binding!!

    private lateinit var scrapService: ScrapService
    
    private lateinit var scrapFolderRVAdapter: ScrapFolderRVAdapter
    private var scrapFolderList = ArrayList<ScrapFolderDataResult>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScrapBinding.inflate(inflater, container, false)
        
        initService()
        initAdapter()
        
        return binding.root
    }

    private fun initService() {
        scrapService = ScrapService()
        scrapService.setScrapFolderView(this)
        scrapService.getScrapFolder()
    }

    private fun initAdapter() {
        scrapFolderRVAdapter = ScrapFolderRVAdapter(scrapFolderList)
        binding.scrapFolderRv.adapter = scrapFolderRVAdapter
        val gridLayoutManager = GridLayoutManager(context, 2)
        binding.scrapFolderRv.layoutManager = gridLayoutManager
        scrapFolderRVAdapter.setOnItemClickListener(object: ScrapFolderRVAdapter.OnItemClickListener {
            override fun onItemClick(scrapFolder: ScrapFolderDataResult, position: Int) {
                if (scrapFolder.scrapFolderName == "") {
                    val scrapFolderNewDialog = DialogScrapFolderNew()
                    scrapFolderNewDialog.show(activity!!.supportFragmentManager, "FolderNewDialog")
                    scrapFolderNewDialog.setOnFinishListener(object: DialogScrapFolderNew.OnFinishListener {
                        override fun finish() {
                            scrapService.getScrapFolder()
                        }
                    })
                } else {
                    val scrapFolderDetailFragment = ScrapFolderDetailFragment()
                    var bundle = Bundle()
                    bundle.putString("title", scrapFolder.scrapFolderName)
                    bundle.putInt("scrapFolderId", scrapFolder.scrapFolderId)
                    scrapFolderDetailFragment.arguments = bundle
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.main_fl, scrapFolderDetailFragment)?.addToBackStack("ScrapFolderDetail")?.commit()
                }
            }
        })
    }

    override fun scrapFolderSuccessView(scrapFolderDataResult: ArrayList<ScrapFolderDataResult>) {
        scrapFolderRVAdapter.clearItems()
        scrapFolderRVAdapter.addAllItems(scrapFolderDataResult)
        scrapFolderRVAdapter.addItem(ScrapFolderDataResult(0, -1, "", "", addFolder))
    }

    override fun scrapFolderFailureView() {
        var message = "스크랩 폴더 조회 실패"
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
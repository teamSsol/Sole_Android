package cmc.sole.android.Scrap

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import cmc.sole.android.R
import cmc.sole.android.Scrap.Retrofit.ScrapFolderDataResult
import cmc.sole.android.Scrap.Retrofit.ScrapFolderView
import cmc.sole.android.Scrap.Retrofit.ScrapService
import cmc.sole.android.Scrap.Retrofit.addFolder
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.databinding.FragmentScrapBinding

class ScrapFragment: BaseFragment<FragmentScrapBinding>(FragmentScrapBinding::inflate),
    ScrapFolderView {

    private lateinit var scrapService: ScrapService
    
    private lateinit var scrapFolderRVAdapter: ScrapFolderRVAdapter
    private var scrapFolderList = ArrayList<ScrapFolderDataResult>()

    override fun initAfterBinding() {
        initService()
        initAdapter()
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
        Log.d("API-TEST", "scrapFolderDataResult = ${scrapFolderDataResult}")
    }

    override fun scrapFolderFailureView() {
        showToast("스크랩 폴더 조회 실패")
    }
}
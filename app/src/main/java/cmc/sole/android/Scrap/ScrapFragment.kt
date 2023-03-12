package cmc.sole.android.Scrap

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import cmc.sole.android.Home.MyPage.DialogMyPageLogout
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.databinding.FragmentScrapBinding

class ScrapFragment: BaseFragment<FragmentScrapBinding>(FragmentScrapBinding::inflate) {
    lateinit var scrapFolderRVAdapter: ScrapFolderRVAdapter
    private var scrapFolderList = ArrayList<ScrapFolderData>()
    override fun initAfterBinding() {
        initAdapter()
    }

    private fun initAdapter() {
        scrapFolderRVAdapter = ScrapFolderRVAdapter(scrapFolderList)
        binding.scrapFolderRv.adapter = scrapFolderRVAdapter
        val gridLayoutManager = GridLayoutManager(context, 2)
        binding.scrapFolderRv.layoutManager = gridLayoutManager
        scrapFolderRVAdapter.setOnItemClickListener(object: ScrapFolderRVAdapter.OnItemClickListener {
            override fun onItemClick(scrapFolder: ScrapFolderData, position: Int) {
                if (scrapFolder.title == null) {
                    val scrapFolderNewDialog = DialogScrapFolderNew()
                    scrapFolderNewDialog.show(activity!!.supportFragmentManager, "FolderNewDialog")
                } else {
                    val scrapFolderDefaultFragment = ScrapFolderDefaultFragment()
                    var bundle = Bundle()
                    bundle.putString("title", scrapFolder.title)
                    scrapFolderDefaultFragment.arguments = bundle
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.main_fl, scrapFolderDefaultFragment)?.addToBackStack("ScrapFolderDetail")?.commit()
                }
            }
        })

        scrapFolderList.add(ScrapFolderData("기본 폴더 1", 1))
        scrapFolderList.add(ScrapFolderData("기본 폴더 2", 1))
        scrapFolderList.add(ScrapFolderData("기본 폴더 3", 1))
        scrapFolderList.add(ScrapFolderData("기본 폴더 4", 1))
        scrapFolderList.add(ScrapFolderData(null, 2))
    }
}
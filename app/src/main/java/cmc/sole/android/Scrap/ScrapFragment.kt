package cmc.sole.android.Scrap

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewHorizontalDecoration
import cmc.sole.android.Utils.RecyclerViewDecoration.RecyclerViewVerticalDecoration
import cmc.sole.android.databinding.FragmentScrapBinding

class ScrapFragment: BaseFragment<FragmentScrapBinding>(FragmentScrapBinding::inflate) {
    private lateinit var scrapFolderRVAdapter: ScrapFolderRVAdapter
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

                    scrapFolderNewDialog.setOnClickListener(object: DialogScrapFolderNew.OnItemClickListener {
                        override fun itemClick(data: ScrapFolderData) {
                            scrapFolderList.removeAt(scrapFolderList.size - 1)
                            scrapFolderList.add(data)
                            scrapFolderList.add(ScrapFolderData(null, 2))
                        }
                    })
                } else {
                    val scrapFolderDetailFragment = ScrapFolderDetailFragment()
                    var bundle = Bundle()
                    bundle.putString("title", scrapFolder.title)
                    scrapFolderDetailFragment.arguments = bundle
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.main_fl, scrapFolderDetailFragment)?.addToBackStack("ScrapFolderDetail")?.commit()
                }
            }
        })

        scrapFolderList.add(ScrapFolderData("기본 폴더", 1))
        scrapFolderList.add(ScrapFolderData("가족", 1))
        scrapFolderList.add(ScrapFolderData("친구", 1))
        scrapFolderList.add(ScrapFolderData(null, 2))
    }
}
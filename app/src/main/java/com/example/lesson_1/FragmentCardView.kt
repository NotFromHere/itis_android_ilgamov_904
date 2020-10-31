package com.example.lesson_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.fragment_card_view.*

class FragmentCardView: Fragment() {

    var adapter: ListCardViewAdapter? = null

    var list = cardViewRepository.list

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_card_view, container, false )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        adapter = ListCardViewAdapter()
        recycle_view_pager.adapter = adapter
        recycle_view_pager.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        adapter?.submitList(list)

        super.onActivityCreated(savedInstanceState)
    }




}
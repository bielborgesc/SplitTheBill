package br.edu.ifsp.scl.ads.pdm.splitthebill.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.scl.ads.pdm.splitthebill.R
import br.edu.ifsp.scl.ads.pdm.splitthebill.model.Member

class MemberAdapter(
    context: Context,
    private val memberList: MutableList<Member>
) : ArrayAdapter<Member>(context, R.layout.tile_member, memberList) {
    private data class TileMemberHolder(val nameTv: TextView, val finalValueTv: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val contact = memberList[position]
        var memberTileView = convertView
        if (memberTileView == null) {
            memberTileView =
                (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                    R.layout.tile_member, parent, false
                )

            val tileMemberHolder = TileMemberHolder(
                memberTileView.findViewById(R.id.nameTv),
                memberTileView.findViewById(R.id.finalValueTv),
            )
            memberTileView.tag = tileMemberHolder
        }

        with(memberTileView?.tag as TileMemberHolder) {
            nameTv.text = contact.name
            finalValueTv.text = contact.valuePaid.toString()
        }

        return memberTileView
    }
}
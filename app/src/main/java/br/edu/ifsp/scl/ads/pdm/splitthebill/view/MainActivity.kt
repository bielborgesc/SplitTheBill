package br.edu.ifsp.scl.ads.pdm.splitthebill.view

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.pdm.splitthebill.R
import br.edu.ifsp.scl.ads.pdm.splitthebill.adapter.MemberAdapter
import br.edu.ifsp.scl.ads.pdm.splitthebill.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.pdm.splitthebill.model.Constant.EXTRA_MEMBER
import br.edu.ifsp.scl.ads.pdm.splitthebill.model.Constant.VIEW_MEMBER
import br.edu.ifsp.scl.ads.pdm.splitthebill.model.Member

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var memberAdapter: MemberAdapter

    private val memberList: MutableList<Member> = mutableListOf()

    private lateinit var carl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)


        memberAdapter = MemberAdapter(this, memberList)
        amb.membersLv.adapter = memberAdapter

        carl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val member = result.data?.getParcelableExtra<Member>(EXTRA_MEMBER)

                member?.let { _member ->
                    val position = memberList.indexOfFirst { it.id == _member.id }
                    if (position != -1) {
                        memberList[position] = _member
                    }
                    else {
                        memberList.add(_member)
                    }
                    memberAdapter.notifyDataSetChanged()
                }
            }
        }

        registerForContextMenu(amb.membersLv)

        amb.membersLv.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val member = memberList[position]
                val memberIntent = Intent(this@MainActivity, MemberActivity::class.java)
                memberIntent.putExtra(EXTRA_MEMBER, member)
                memberIntent.putExtra(VIEW_MEMBER, true)
                startActivity(memberIntent)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.addMembertMi -> {
                carl.launch(Intent(this, MemberActivity::class.java))
                true
            }
            else -> { false }
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        return when(item.itemId) {
            R.id.removeMemberMi -> {
                memberList.removeAt(position)
                memberAdapter.notifyDataSetChanged()
                true
            }
            R.id.editMemberMi -> {
                val member = memberList[position]
                val memberIntent = Intent(this, MemberActivity::class.java)
                memberIntent.putExtra(EXTRA_MEMBER, member)
                memberIntent.putExtra(VIEW_MEMBER, false)
                carl.launch(contactIntent)
                true
            }
            else -> { false }
        }
    }

}

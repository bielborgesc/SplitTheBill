package br.edu.ifsp.scl.ads.pdm.splitthebill.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.pdm.splitthebill.databinding.ActivityMemberBinding
import br.edu.ifsp.scl.ads.pdm.splitthebill.model.Constant.EXTRA_MEMBER
import br.edu.ifsp.scl.ads.pdm.splitthebill.model.Constant.VIEW_MEMBER
import br.edu.ifsp.scl.ads.pdm.splitthebill.model.Member
import kotlin.random.Random

class MemberActivity : AppCompatActivity() {
    private val acb: ActivityMemberBinding by lazy {
        ActivityMemberBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(acb.root)

        val receivedMember = intent.getParcelableExtra<Member>(EXTRA_MEMBER)
        receivedMember?.let{ _receivedContact ->
            with(acb) {
                with(_receivedContact) {
                    nameEt.setText(name)
                    valueEt.setText(valuePaid.toString())
                    descriptionEt.setText(description)
                }
            }
        }
        val viewMember = intent.getBooleanExtra(VIEW_MEMBER, false)
        if (viewMember) {
            acb.nameEt.isEnabled = false
            acb.valueEt.isEnabled = false
            acb.descriptionEt.isEnabled = false
            acb.saveBt.visibility = View.GONE
        }

        acb.saveBt.setOnClickListener {
            val member = Member(
                id = receivedMember?.id?: Random(System.currentTimeMillis()).nextInt(),
                name = acb.nameEt.text.toString(),
                valuePaid = acb.valueEt.text.toString().toDouble(),
                description = acb.descriptionEt.text.toString(),
            )

            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_MEMBER, member)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
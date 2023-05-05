package com.example.jobwave

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class ReviewSupportServiceRequest : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvConcern: TextView
    private lateinit var editbtn: Button
    private lateinit var deletebtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_support_service_request)

        initView()
        setValuesToViews()

        editbtn.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("cusId").toString(),
                intent.getStringExtra("name_f").toString()
            )
        }

        deletebtn.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("cusId").toString()
            )
        }


    }

    private fun deleteRecord(
        id: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Requests").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Data record deleted", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SupportServiceRequest::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Error while deleting ${error.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openUpdateDialog(
        cusId: String,
        name_f: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.editrequests, null)

        mDialog.setView(mDialogView)

        val etName = mDialogView.findViewById<EditText>(R.id.etName)
        val etEmail = mDialogView.findViewById<EditText>(R.id.etEmail)
        val etConcern = mDialogView.findViewById<EditText>(R.id.etConcern)
        val btnEditData = mDialogView.findViewById<EditText>(R.id.btnEditData)

        etName.setText(intent.getStringExtra("name_f").toString())
        etEmail.setText(intent.getStringExtra("email_f").toString())
        etConcern.setText(intent.getStringExtra("concern_f").toString())

        mDialog.setTitle("Updating $name_f Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnEditData.setOnClickListener {
            updateRequestData(
                cusId,
                etName.text.toString(),
                etEmail.text.toString(),
                etConcern.text.toString()
            )

            //Toast message about the successful updating of dara
            Toast.makeText(applicationContext, "Data Updated Successfully", Toast.LENGTH_LONG).show()

            //Setting updated data to textviews
            tvName.text = etName.text.toString()
            tvEmail.text = etEmail.text.toString()
            tvConcern.text = etConcern.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateRequestData(
        id:String,
        name:String,
        email:String,
        concern:String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Requests").child(id)
        val reqInfo = SupportServicesModel(id, name, email, concern)
        dbRef.setValue(reqInfo)
    }

    private fun setValuesToViews() {
        TODO("Not yet implemented")
    }

    private fun initView() {
        tvName.text = intent.getStringExtra("name_f")
        tvEmail.text = intent.getStringExtra("email_f")
        tvConcern.text = intent.getStringExtra("concern_f")
    }

    override fun onBackPressed() {
        val setIntent = Intent(Intent.ACTION_MAIN)
        setIntent.addCategory(Intent.CATEGORY_HOME)
        setIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(setIntent)
    }
}
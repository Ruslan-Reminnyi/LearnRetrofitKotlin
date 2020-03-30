package com.mycompany.learnretrofitkotlin.Dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.gson.GsonBuilder
import com.mycompany.learnretrofitkotlin.*
import kotlinx.android.synthetic.main.fragment_dialog_add.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

class AddDialog : DialogFragment(),
    View.OnClickListener,
    DialogsInterface
//    DialogInterface.OnClickListener
{
    var mainActivity: MainActivity? = null
    var presenter : DialogsPresenter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        presenter = DialogsPresenter(this)
    }

    override fun onResume() {
        super.onResume()

        dialog!!.setCanceledOnTouchOutside(false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.button_add.setOnClickListener(this)
        dialog!!.button_cancel.setOnClickListener(this)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder =
            AlertDialog.Builder(activity)
        val inflater = activity!!.layoutInflater
        builder.setView(inflater.inflate(R.layout.fragment_dialog_add, null))
//            .setTitle(R.string.title_add)
//            .setPositiveButton(R.string.button_add, this)
//            .setNegativeButton(R.string.button_cancel, this)

        return builder.create()
    }

//    override fun onClick(dialogInterface: DialogInterface, which: Int) {
//        when (which) {
//            Dialog.BUTTON_POSITIVE -> addContact()
//            Dialog.BUTTON_NEGATIVE -> this@AddDialog.dialog?.cancel()
//        }
//    }

    override fun onClick(v: View?) {
                when (v?.id) {
            R.id.button_add -> addContact()
            R.id.button_cancel -> this@AddDialog.dialog?.cancel()
        }
    }

    fun addContact() {
        val editTextName = dialog!!.findViewById<View>(R.id.edit_text_name) as EditText
        val editTextAge = dialog!!.findViewById<View>(R.id.edit_text_age) as EditText
        val editTextPosition = dialog!!.findViewById<View>(R.id.edit_text_position) as EditText

        // check if all fields are filled out:
        if (editTextName.text.toString().isEmpty()
            || editTextAge.text.toString().isEmpty()
            || editTextPosition.text.toString().isEmpty()
        ) {
            editTextName.error = "Enter name"
            editTextAge.error = "Enter age"
            editTextPosition.error = "Enter position"
            Toast.makeText(activity, R.string.not_all_fields, Toast.LENGTH_LONG).show()
            return
        }

        val name = editTextName.text.toString()
        var age = 0
        try {
            age = editTextAge.text.toString().toInt()
        } catch (e: NumberFormatException) {
            println(e.message)
        }
        val position = editTextPosition.text.toString()

        presenter?.sendDataToServer(name, age, position)

        dismiss()
    }

    override fun showToast(message : String?) {
        Toast.makeText(
            mainActivity
            , message
            , Toast.LENGTH_LONG
        ).show()
    }

}

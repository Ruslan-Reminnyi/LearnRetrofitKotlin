package com.mycompany.learnretrofitkotlin.Dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.gson.GsonBuilder
import com.mycompany.learnretrofitkotlin.*
import kotlinx.android.synthetic.main.fragment_dialog_add.*
import kotlinx.android.synthetic.main.fragment_dialog_add.button_cancel
import kotlinx.android.synthetic.main.fragment_dialog_edit.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditDialog(context: Context) : DialogFragment()
//    , DialogInterface.OnClickListener
    , View.OnClickListener
    , DialogsInterface {

    var editTextName: EditText? = null
    var editTextAge:EditText? = null
    var editTextPosition:EditText? = null
    var entity: Entity? = null

    var mainActivity: MainActivity? = null
    var presenter : DialogsPresenter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity?
        presenter = DialogsPresenter(this)
    }

    override fun onResume() {
        super.onResume()

        editTextName = getDialog()?.findViewById(R.id.edit_text_edit_name)
        editTextAge = getDialog()?.findViewById(R.id.edit_text_edit_age) as EditText
        editTextPosition = getDialog()?.findViewById(R.id.edit_text_edit_position) as EditText
        entity = Entity()
        val currentIndex: Int = mainActivity!!.currentIndex
        entity = mainActivity!!.entityList!![currentIndex]
        val age: Int? = entity?.getAge()

        editTextName?.setText(entity?.getName())
        editTextAge!!.setText(age.toString())
        editTextPosition!!.setText(entity?.getPosition())

        getDialog()?.setCanceledOnTouchOutside(false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.button_edit.setOnClickListener(this)
        dialog!!.button_cancel.setOnClickListener(this)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder =
            AlertDialog.Builder(getActivity())
        val inflater: LayoutInflater = getActivity()!!.getLayoutInflater()
        builder.setView(inflater.inflate(R.layout.fragment_dialog_edit, null))
//            .setTitle(R.string.title_edit)
//            .setPositiveButton(R.string.button_edit, this)
//            .setNegativeButton(R.string.button_cancel, this)
        return builder.create()
    }

//    override fun onClick(dialogInterface: DialogInterface?, which: Int) {
//        when (which) {
//            Dialog.BUTTON_POSITIVE -> editContact()
//            Dialog.BUTTON_NEGATIVE -> this@EditDialog.getDialog()?.cancel()
//        }
//    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_edit -> editContact()
            R.id.button_cancel -> this@EditDialog.getDialog()?.cancel()
        }
    }

    fun editContact() {

        // check if all fields are filled out:
        if (editTextName!!.text.toString().isEmpty()
            || editTextAge?.getText().toString().isEmpty()
            || editTextPosition?.getText().toString().isEmpty()
        ) {
            editTextName!!.error = "Enter name"
            editTextAge?.setError("Enter age")
            editTextPosition?.setError("Enter position")
            Toast.makeText(getActivity(), R.string.not_all_fields, Toast.LENGTH_LONG).show()
            return
        }

        val id: Int = entity!!.getId()
        val name = editTextName!!.text.toString()
        var age = 0
        try {
            age = editTextAge?.getText().toString().toInt()
        } catch (e: NumberFormatException) {
            println(e.message)
        }
        val position: String = editTextPosition?.getText().toString()

        presenter?.editDataOnServer(id, name, age, position)

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
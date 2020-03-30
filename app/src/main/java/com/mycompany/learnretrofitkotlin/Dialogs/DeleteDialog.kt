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
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.gson.GsonBuilder
import com.mycompany.learnretrofitkotlin.*
import kotlinx.android.synthetic.main.fragment_dialog_add.*
import kotlinx.android.synthetic.main.fragment_dialog_add.button_cancel
import kotlinx.android.synthetic.main.fragment_dialog_delete.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DeleteDialog(context: Context) : DialogFragment()
    , View.OnClickListener
    , DialogsInterface
//    DialogInterface.OnClickListener
{
    var mainActivity: MainActivity? = null
    var selectId = 0
    var presenter : DialogsPresenter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity?
        presenter = DialogsPresenter(this)
    }

    override fun onResume() {
        super.onResume()

        val currentIndex: Int = mainActivity!!.currentIndex
        selectId = mainActivity?.entityList!![currentIndex]!!.getId()

        getDialog()?.setCanceledOnTouchOutside(false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.button_delete.setOnClickListener(this)
        dialog!!.button_cancel.setOnClickListener(this)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder =
            AlertDialog.Builder(getActivity())
        val inflater = activity!!.layoutInflater
        builder.setView(inflater.inflate(R.layout.fragment_dialog_delete, null))
//        val inflater: LayoutInflater = context?.getLayoutInflater()
//        builder.setMessage(R.string.message_delete)
//            .setPositiveButton(R.string.button_delete, this)
//            .setNegativeButton(R.string.button_cancel, this)
        return builder.create()
    }

//    override fun onClick(dialogInterface: DialogInterface?, which: Int) {
//        when (which) {
//            Dialog.BUTTON_POSITIVE -> deleteContact()
//            Dialog.BUTTON_NEGATIVE -> this@DeleteDialog.getDialog()?.cancel()
//        }
//    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_delete -> deleteContact(selectId)
            R.id.button_cancel -> this@DeleteDialog.dialog?.cancel()
        }
    }

    fun deleteContact(selectId : Int) {
        presenter?.deleteContact(selectId)
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
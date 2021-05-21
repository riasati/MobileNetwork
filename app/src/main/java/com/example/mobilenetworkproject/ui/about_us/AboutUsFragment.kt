package com.example.mobilenetworkproject.ui.about_us

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mobilenetworkproject.R

class AboutUsFragment : Fragment() {

    private lateinit var aboutUsViewModel: AboutUsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        aboutUsViewModel =
                ViewModelProviders.of(this).get(AboutUsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_about_us, container, false)
        val textView_studnet1_name: TextView = root.findViewById(R.id.text_student1_name)
        val textView_studnet1_email: TextView = root.findViewById(R.id.text_student1_email)
        val textView_studnet1_id: TextView = root.findViewById(R.id.text_student1_id)

        val textView_studnet2_name: TextView = root.findViewById(R.id.text_student2_name)
        val textView_studnet2_email: TextView = root.findViewById(R.id.text_student2_email)
        val textView_studnet2_id: TextView = root.findViewById(R.id.text_student2_id)
//        aboutUsViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        aboutUsViewModel.studentModel.observe(viewLifecycleOwner, Observer {
            textView_studnet1_name.text = it[0].name
            textView_studnet1_email.text = it[0].emailAddress
            textView_studnet1_id.text = it[0].studentID
            textView_studnet2_name.text = it[1].name
            textView_studnet2_email.text = it[1].emailAddress
            textView_studnet2_id.text = it[1].studentID
        })
        return root
    }
}
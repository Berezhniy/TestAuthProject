package ru.startandroid.testauthproject.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import com.kalashnyk.denys.defaultproject.presentation.activities.auth.flow.AuthFlowModel
import ru.startandroid.testauthproject.presentation.base.BaseFragment

abstract class BaseAuthFragment: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View =  inflater.inflate(getLayout(), container, false)
        viewLogic(v)
        return v
    }

    abstract fun viewLogic(view : View)
}
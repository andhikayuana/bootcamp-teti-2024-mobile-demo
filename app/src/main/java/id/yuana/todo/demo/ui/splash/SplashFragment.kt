package id.yuana.todo.demo.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import id.yuana.todo.demo.R
import id.yuana.todo.demo.TodoApp
import id.yuana.todo.demo.util.UiEffect

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = SplashViewModel(
            (requireActivity().application as TodoApp).appModule.provideAuthRepository()
        )

        viewModel.uiEffect.observe(this, Observer { effect ->
            when (effect) {
                is UiEffect.Navigate -> findNavController().navigate(effect.directions)
                else -> {}
            }
        })
        viewModel.onEvent(SplashEvent.OnLoad)
    }
}
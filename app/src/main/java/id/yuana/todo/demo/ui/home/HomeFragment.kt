package id.yuana.todo.demo.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.yuana.todo.demo.TodoApp
import id.yuana.todo.demo.databinding.FragmentHomeBinding
import id.yuana.todo.demo.util.UiEffect

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val todoAdapter by lazy {
        TodoAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appModule = (requireActivity().application as TodoApp).appModule
        viewModel = HomeViewModel(
            appModule.provideAuthRepository(),
            appModule.provideTodoRepository()
        )
        viewModel.homeState.observe(this, Observer { state ->
            Log.d("YUANA", state.toString())
            todoAdapter.submitList(state.todos)
        })
        viewModel.uiEffect.observe(this, Observer { effect ->
            when (effect) {
                is UiEffect.Navigate -> findNavController().navigate(effect.directions)
                UiEffect.PopBackStack -> findNavController().popBackStack()
                is UiEffect.ShowToast -> Toast.makeText(
                    requireContext(),
                    effect.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener {
            viewModel.onEvent(HomeEvent.OnLogoutClick)
        }
        binding.rvTodos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = todoAdapter
        }
        binding.btnCreate.setOnClickListener {
            viewModel.onEvent(HomeEvent.OnCreateTodo)
        }
        viewModel.onEvent(HomeEvent.OnLoadTodos)
    }

}
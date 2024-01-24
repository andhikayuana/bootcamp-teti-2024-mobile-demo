package id.yuana.todo.demo.ui.create_or_update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import id.yuana.todo.demo.TodoApp
import id.yuana.todo.demo.databinding.FragmentCreateOrUpdateBinding
import id.yuana.todo.demo.util.UiEffect

class CreateOrUpdateFragment : Fragment() {

    private lateinit var viewModel: CreateOrUpdateViewModel

    private var _binding: FragmentCreateOrUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateOrUpdateBinding.inflate(inflater, container, false)
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
        viewModel = CreateOrUpdateViewModel(
            appModule.provideTodoRepository()
        )
        viewModel.uiEffect.observe(this, Observer { effect ->
            when (effect) {
                UiEffect.PopBackStack -> findNavController().popBackStack()
                else -> {}
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tieTitle.addTextChangedListener {
            viewModel.onEvent(CreateOrUpdateEvent.OnTitleChange(it.toString()))
        }
        binding.tieDescription.addTextChangedListener {
            viewModel.onEvent(CreateOrUpdateEvent.OnDescriptionChange(it.toString()))
        }
        binding.btnCreateOrUpdate.setOnClickListener {
            viewModel.onEvent(CreateOrUpdateEvent.OnCreateOrUpdateClick)
        }
    }

}
package jp.co.cyberagent.dojo2020.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import jp.co.cyberagent.dojo2020.R
import jp.co.cyberagent.dojo2020.data.model.Memo
import jp.co.cyberagent.dojo2020.databinding.FragmentHomeBinding
import jp.co.cyberagent.dojo2020.ui.TextAdapter
import jp.co.cyberagent.dojo2020.util.Left
import jp.co.cyberagent.dojo2020.util.Right

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(this, Bundle(), requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            createMemoFloatingActionButton.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_memoCreateFragment)
            }

            val linearLayoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )

            val textAdapter = TextAdapter() {
                val action = HomeFragmentDirections.actionHomeFragmentToMemoEditFragment("test_id")
                findNavController().navigate(action)
            }

            homeViewModel.draftListLiveData.observe(viewLifecycleOwner) { drafts ->
                val memoList = drafts.map {
                    Right(Memo(it.id, it.title, it.content, it.startTime, it.category))
                }

                val draftList = drafts.map { Left(it) }

                textAdapter.textList = (memoList + draftList)
            }

            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = linearLayoutManager
                adapter = textAdapter
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home_profile_icon_id -> {
                findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
                true
            }
            R.id.home_create_icon_id -> {
                findNavController().navigate(R.id.action_homeFragment_to_memoCreateFragment)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

}
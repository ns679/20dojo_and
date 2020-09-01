package jp.co.cyberagent.dojo2020.ui.main

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import jp.co.cyberagent.dojo2020.DI
import jp.co.cyberagent.dojo2020.data.model.Memo
import jp.co.cyberagent.dojo2020.databinding.ActivityMainBinding
import jp.co.cyberagent.dojo2020.ui.home.HomeViewModel
import jp.co.cyberagent.dojo2020.ui.home.HomeViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(this, Bundle(), this)
    }

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            viewModel.liveData.observe(this@MainActivity) {
                val memo = it.randomOrNull() ?: return@observe

                helloWorld.text = memo.title
            }

            reloadButton.setOnClickListener { viewModel.reload() }
            saveButton.setOnClickListener {
                val title = titleEditText.text.toString()

                viewModel.save(createWithTitle(title))
            }
        }

    }

    private fun createWithTitle(title: String): Memo {
        return Memo(title, "contents", 0.256)
    }
}

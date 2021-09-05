package ru.myacademyhomework.tinkoff.gif

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ru.myacademyhomework.tinkoff.ErrorLoad
import ru.myacademyhomework.tinkoff.Loading
import ru.myacademyhomework.tinkoff.R
import ru.myacademyhomework.tinkoff.SuccessLoad
import ru.myacademyhomework.tinkoff.model.GifModel


class GifFragment : Fragment() {

    private val viewModel: GifViewModel by viewModels()
    private lateinit var ivGif: ImageView
    private lateinit var btnPrev: Button
    private lateinit var btnNext: Button
    private lateinit var tvDescription: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorView: View
    private lateinit var btnError: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gif, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivGif = view.findViewById(R.id.iv_gif)
        btnPrev = view.findViewById(R.id.but_prev)
        btnNext = view.findViewById(R.id.but_next)
        tvDescription = view.findViewById(R.id.tv_description)
        progressBar = view.findViewById((R.id.progress_bar))
        errorView = view.findViewById(R.id.errorView)
        btnError = view.findViewById(R.id.btn_error)

        btnNext.setOnClickListener {
            viewModel.getNextGif()
        }

        btnPrev.setOnClickListener {
            viewModel.getPrevGif()
        }

        btnError.setOnClickListener {
            viewModel.getNextGif()
        }



        viewModel.gifLiveData.observe(this.viewLifecycleOwner, {
            showGif(it)
        })

        viewModel.loadStateLiveData.observe(this.viewLifecycleOwner, {
            when (it) {
                is Loading -> showLoading()
                is ErrorLoad -> showError()
                is SuccessLoad -> showSuccess()
            }
        })

        viewModel.btnEnabledLiveData.observe(this.viewLifecycleOwner, {
            btnPrev.isEnabled = it
        })
    }

    private fun showLoading() {
        tvDescription.visibility = View.GONE
        ivGif.visibility = View.GONE
        errorView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun showSuccess() {
        // progressBar.visibility = View.GONE
        errorView.visibility = View.GONE
        tvDescription.visibility = View.VISIBLE
        ivGif.visibility = View.VISIBLE

    }

    private fun showError() {
        tvDescription.visibility = View.GONE
        ivGif.visibility = View.GONE
        progressBar.visibility = View.GONE
        errorView.visibility = View.VISIBLE
    }

    private fun showGif(gifModel: GifModel) {
        tvDescription.text = gifModel.description
        Glide.with(this)
            .load(gifModel.gifURL)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    showError()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }
            })
            .into(ivGif)
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            GifFragment()
    }
}
package com.ameen.whiteboard

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.ameen.whiteboard.adapter.ColorPalletAdapter
import com.ameen.whiteboard.databinding.ActivityMainBinding
import com.ameen.whiteboard.model.PaintSelectedType

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = "MainActivity"

    private var binding: ActivityMainBinding? = null
    private lateinit var colorPalletAdapter: ColorPalletAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding!!.root)

        binding?.apply {
            buttonArrow.setOnClickListener(this@MainActivity)
            buttonCircle.setOnClickListener(this@MainActivity)
            buttonColorPalette.setOnClickListener(this@MainActivity)
            buttonPencil.setOnClickListener(this@MainActivity)
            buttonRectangle.setOnClickListener(this@MainActivity)
        }

        setupColorPalletRecycler()
    }

    override fun onClick(clickedView: View?) {
        when (clickedView?.id) {
            R.id.buttonPencil -> {

                Log.i(TAG, "onClick: Pencil")

                binding?.apply {
                    buttonPencil.setImageResource(R.drawable.ic_pencil_selected)
                    buttonRectangle.setImageResource(R.drawable.ic_rectangle)
                    buttonArrow.setImageResource(R.drawable.ic_arrow)
                    buttonCircle.setImageResource(R.drawable.ic_ellipse)
                    buttonColorPalette.setImageResource(R.drawable.ic_color_palette)
                }
                binding?.customWhiteBoard?.setType(PaintSelectedType.HAND_DRAWING)
            }

            R.id.buttonArrow -> {

                Log.i(TAG, "onClick: Arrow")

                binding?.apply {
                    buttonArrow.setImageResource(R.drawable.ic_arrow_selected)
                    buttonRectangle.setImageResource(R.drawable.ic_rectangle)
                    buttonPencil.setImageResource(R.drawable.ic_pencil)
                    buttonCircle.setImageResource(R.drawable.ic_ellipse)
                    buttonColorPalette.setImageResource(R.drawable.ic_color_palette)
                }

                binding?.customWhiteBoard?.setType(PaintSelectedType.ARROW_DRAWING)
            }

            R.id.buttonRectangle -> {

                Log.i(TAG, "onClick: Rectangle")

                binding?.apply {
                    buttonRectangle.setImageResource(R.drawable.ic_rectangle_selected)
                    buttonPencil.setImageResource(R.drawable.ic_pencil)
                    buttonArrow.setImageResource(R.drawable.ic_arrow)
                    buttonCircle.setImageResource(R.drawable.ic_ellipse)
                    buttonColorPalette.setImageResource(R.drawable.ic_color_palette)
                }

                //whiteBoard.setupPaint(PaintSelectedType.RECTANGLE_DRAWING)
            }

            R.id.buttonCircle -> {

                Log.i(TAG, "onClick: Circle")

                binding?.apply {
                    buttonCircle.setImageResource(R.drawable.ic_ellipse_selected)
                    buttonRectangle.setImageResource(R.drawable.ic_rectangle)
                    buttonArrow.setImageResource(R.drawable.ic_arrow)
                    buttonPencil.setImageResource(R.drawable.ic_pencil)
                    buttonColorPalette.setImageResource(R.drawable.ic_color_palette)
                }

                //whiteBoard.setupPaint(PaintSelectedType.CIRCLE_DRAWING)
            }

            R.id.buttonColorPalette -> isColorPalletVisible()
        }
    }

    /**
     * Setting up the color from selected color pallet.
     * @param color -> New Selected Color.
     * @return Unit -> Void
     */
    private fun setupNewSelectedColor(color: Int) =
        binding?.customWhiteBoard?.setColor(color)


    //Setting  up recycler to show the color pallet.
    private fun setupColorPalletRecycler() {
        colorPalletAdapter = ColorPalletAdapter(this)
        colorPalletAdapter.onColorPalletSelected { newSelectedColor ->
            setupNewSelectedColor(newSelectedColor)
            isColorPalletVisible()
        }
        colorPalletAdapter.diff.submitList(resources.getIntArray(R.array.color_pallet).toList())
        binding?.colorPalletRecycler?.apply {
            adapter = colorPalletAdapter
            hasFixedSize()
        }
    }

    // true --> Visible
    // False --> Gone.
    private fun isColorPalletVisible() {
        if (!(binding!!.colorPalletView.isVisible))
            binding?.colorPalletView?.visibility = View.VISIBLE
        else
            binding?.colorPalletView?.visibility = View.GONE
    }
}
package com.dev.sample.features.dialogImage

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v4.view.PagerAdapter
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.heinhtet.gallaryview.dialog.DialogImageData
import com.example.heinhtet.gallaryview.dialog.DialogRecyclerAdapter
import com.example.heinhtet.gallaryview.dialog.dialogImage.MyLinearManagerWithSmoothScroller
import com.heinhtet.deevd.deevdimagepreview.R


class DeevDImagePreview : DialogFragment(), DialogRecyclerAdapter.ClickListener, View.OnClickListener {
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.exist -> {
                this.dismiss()
            }
        }
    }


    override fun imageClick(position: Int) {
        setCurrentItem(position)
        displayMetaInfo(position)
    }

    private val TAG = DeevDImagePreview::class.java.getSimpleName()
    private var viewPager: ViewPager? = null
    private var myViewPagerAdapter: MyViewPagerAdapter? = null
    private var imageTotalTv: TextView? = null
    lateinit var desTv: TextView
    lateinit var recyclerView: RecyclerView
    lateinit var dAdapter: DialogRecyclerAdapter
    private var selectedPosition = 0
    lateinit var fragmentActivity: FragmentActivity
    var dialogAnimationStyle: Int? = null

    var getImages = ArrayList<DialogImageData>()

    companion object Instance {
        private val TAG = DeevDImagePreview::class.java.getSimpleName()
        var DIALOG_IMAGE_LIST = "image_list"
        var DIALOG_ANIMATION = "animation"
        var DIALOG_POSITION = "image_position"
        var DEEVD = "deevd"
        private var mImageList: ArrayList<DialogImageData>? = null
        private var mPostion: Int = 0
        private var mAnimation: Int? = null
        @SuppressLint("StaticFieldLeak")
        private var mActivity: Context? = null

        private fun newInstance(): DeevDImagePreview {
            return DeevDImagePreview()
        }

        fun setImageList(imageList: ArrayList<DialogImageData>): Instance {
            mImageList = ArrayList<DialogImageData>()
            this.mImageList!!.addAll(imageList)
            return this
        }

        fun setAnimationRes(animationRes: Int?): Instance {
            if (animationRes != null) {
                this.mAnimation = animationRes
            } else {
                this.mAnimation = R.style.pulseDialogAnimation_Style
            }
            return this
        }

        fun setDefaultPosition(position: Int): Instance {
            this.mPostion = position
            return this
        }

        fun buildImagePreview(context: Context) {

        }

        fun createImageDialog(context: Context?) {
            if (context == null) {
                Log.e(TAG, "null context")
                return
            }
            if (mImageList != null) {
                if (mImageList!!.size < 0) {
                    Log.e(TAG, "image list need")
                }
            }
            this.mActivity = context as FragmentActivity?
            val bundle = Bundle()
            bundle.putInt(DeevDImagePreview.DIALOG_POSITION, 0)
            bundle.putInt(DeevDImagePreview.DIALOG_ANIMATION, mAnimation!!)
            bundle.putSerializable(DeevDImagePreview.DIALOG_IMAGE_LIST, this.mImageList)
            var ft = (mActivity as FragmentActivity).supportFragmentManager.beginTransaction()
            var newFragment = DeevDImagePreview.newInstance()
            newFragment.arguments = bundle
            newFragment.show(ft, DeevDImagePreview.DEEVD)
        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        fragmentActivity = context as FragmentActivity

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.dialog_layout, container, false)
        getData()
        initView(v)
        initRv()
        initViewPager()
        setCurrentItem(selectedPosition)
        return v
    }

    private fun getData() {
        getImages = arguments?.getSerializable(DIALOG_IMAGE_LIST) as ArrayList<DialogImageData>
        selectedPosition = arguments?.getInt(DIALOG_POSITION)!!
        dialogAnimationStyle = arguments?.getInt(DIALOG_ANIMATION)

    }

    private fun initView(v: View) {
        viewPager = v.findViewById(R.id.viewpager)
        imageTotalTv = v.findViewById<TextView>(R.id.image_total_count_tv)
        desTv = v.findViewById(R.id.description_tv)
        var exist = v.findViewById<ImageView>(R.id.exist) as ImageView
        exist.setOnClickListener(this)
        recyclerView = v.findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView

    }

    private fun initViewPager() {
        myViewPagerAdapter = MyViewPagerAdapter()
        viewPager?.adapter = myViewPagerAdapter
        viewPager?.addOnPageChangeListener(viewPagerPageChangeListener)
    }

    private fun initRv() {
        dAdapter = DialogRecyclerAdapter()
        recyclerView.layoutManager = MyLinearManagerWithSmoothScroller(fragmentActivity)
        recyclerView.adapter = dAdapter
        dAdapter.ClickListener(this)
        dAdapter.addAll(getImages)

    }

    override fun onStart() {
        super.onStart()
        if (dialog == null) {
            return
        } else {
            dialog.window!!.setWindowAnimations(
                    this.dialogAnimationStyle!!)
        }

    }


    private fun setCurrentItem(position: Int) {
        viewPager!!.setCurrentItem(position, false)
        displayMetaInfo(position)
        dAdapter.setPosition(position)
    }

    var viewPagerPageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageSelected(position: Int) {
            displayMetaInfo(position)
            recyclerView.smoothScrollToPosition(position)
            dAdapter.setPosition(position)
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {

        }

        override fun onPageScrollStateChanged(arg0: Int) {

        }
    }

    private fun displayMetaInfo(position: Int) {
        imageTotalTv!!.text = (position + 1).toString() + " of " + getImages!!.size
        desTv.text = "${getImages[position].des} of $position"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    inner class MyViewPagerAdapter : PagerAdapter() {
        private var layoutInflater: LayoutInflater? = null
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            layoutInflater = fragmentActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = layoutInflater?.inflate(R.layout.dialog_image, container, false)
            val imageViewPreview = view?.findViewById<ImageView>(R.id.dialog_iv) as ImageView
            Glide.with(fragmentActivity).load(getImages[position].imagePath).into(imageViewPreview)
            container.addView(view)
            return view
        }

        override fun getCount(): Int {
            return getImages.size
        }

        override
        fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj as View
        }


        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }
}
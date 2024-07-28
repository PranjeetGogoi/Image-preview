package com.heinhtet.deevd.deevdimagepreviewsimple

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dev.sample.features.dialogImage.DeevDImagePreview
import com.example.heinhtet.gallaryview.dialog.DialogImageData
import com.heinhtet.deevd.deevdimagepreview.dialog.DeevDAnimation
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        show_gallary.setOnClickListener {

            DeevDImagePreview
                    .setImageList(getPersonList())
                    .setAnimationRes(DeevDAnimation.PULSE_ANIMATION)
                    .setDefaultPosition(0)
                    .createImageDialog(this)
        }
    }

    fun getPersonList(): ArrayList<DialogImageData> {
        val arr = ArrayList<DialogImageData>()
        arr.add(DialogImageData
        (4, "DeevD Image Preview",
         "https://cdn.ndtv.com/tech/images/gadgets/pikachu_hi_pokemon.jpg?output-quality=80"

                ,
                false))
        arr.add(DialogImageData(9, "DeevD Image Preview",
                "https://i2-prod.mirror.co.uk/incoming/article7731571.ece/ALTERNATES/s298/Pokemon-charmander.png", false))
        arr.add(DialogImageData(8, "DeevD Image Preview",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQIKLJAQfu44rdug8X3JT2GIJbj9WhawrTQ4Gmw_xIk4G-frd5clw", false))
        arr.add(DialogImageData(7,
                "DeevD Image Preview",
                "https://cdn4.dualshockers.com/wp-content/uploads/2016/07/pokemon-4-1200x0.jpg", false))
        arr.add(DialogImageData(5,
                "DeevD Image Preview",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfG9QocSo2_IkIqf5RNZt0Zd_ypCU7AxLbA83DCrO35YJ4w_yqdA", false))
        arr.add(DialogImageData(5,
                "DeevD Image Preview",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTpDAdsqSI7jGLH5pjhOLeLH2PILNGcwyyD2qNuCVQx2H6hcbphDA", false))
        arr.add(DialogImageData(43,
                "DeevD Image Preview", "https://i.pinimg.com/736x/ee/0f/f7/ee0ff7d8f4983094676b214ce2a59321--pokemon-funny-chibi-pokemon.jpg", false))
        arr.add(DialogImageData(43,
                "DeevD Image Preview", "https://static.pokebattler.com/pokemon/raikou.svg", false))
        arr.add(DialogImageData(1,
                "DeevD Image Preview", "http://i1.hdslb.com/user/3039/303946/myface.jpg", false))
        return arr
    }
}

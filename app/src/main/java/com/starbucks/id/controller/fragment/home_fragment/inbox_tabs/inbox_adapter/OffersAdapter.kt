//package com.starbucks.id.controller.fragment.home_fragment.inbox_tabs.inbox_adapter
//
//import android.content.Context
//import android.graphics.Typeface
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.ProgressBar
//import android.widget.TextView
//
//import androidx.recyclerview.widget.RecyclerView
//
//import com.squareup.picasso.Picasso
//import com.starbucks.id.R
//import com.starbucks.id.controller.activity.MainActivity
//import com.starbucks.id.helper.UserDefault
//import com.starbucks.id.helper.utils.DataUtil
//import com.starbucks.id.model.inbox.MessageModel
//
//import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
//
///**
// * Created by Angga on 23/1/2019.
// */
//
//class OffersAdapter(private val rowLayout: Int, private val context: Context) : RecyclerView.Adapter<OffersAdapter.Holder>() {
//    private val picasso: Picasso = Picasso.with(context)
//    private var clickListener: ItemClickListener? = null
//    private val userDefault: UserDefault = (context as MainActivity).userDefault
//
//
//    inner class Holder constructor(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
////        var tvTitle: TextView = v.findViewById(R.id.tvTitle)
////        var tvBody: TextView = v.findViewById(R.id.tvBody)
////        var tvTS: TextView = v.findViewById(R.id.tvTS)
////        var iv: ImageView = v.findViewById(R.id.iv)
////        var ic: ImageView = v.findViewById(R.id.ic)
////        var pb: ProgressBar = v.findViewById(R.id.pb)
//
//        init {
//            v.setOnClickListener(this)
//        }
//
//        override fun onClick(v: View) {
//            if (clickListener != null) clickListener!!.onClick(v, adapterPosition)
//        }
//
//        fun bindView(item: MessageModel){
//
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//        val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
//        return Holder(view)
//    }
//
//    override fun onBindViewHolder(holder: OffersAdapter.Holder, position: Int) {
////        holder.bindView(data[position])
//    }
//
//    override fun getItemCount(): Int {
////        return data.size
//        return 2
//    }
//
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//
//    fun setClickListener(itemClickListener: ItemClickListener) {
//        this.clickListener = itemClickListener
//    }
//
//    fun getdata(pos: Int): MessageModel {
////        return data[pos]
//        return MessageModel()
//    }
//
//    interface ItemClickListener {
//        fun onClick(view: View, position: Int)
//    }
//
//}
//
//

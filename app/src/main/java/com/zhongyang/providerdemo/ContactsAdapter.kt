package com.zhongyang.providerdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * @项目名称 ProviderDemo
 * @类名 ContactsAdapter
 * @包名 com.zhongyang.providerdemo
 * @创建时间 2021/1/19 14:14
 * @作者 钟阳
 * @描述 联系人适配器
 */
class ContactsAdapter(val mData: ArrayList<Contacts>) :
    RecyclerView.Adapter<ContactsAdapter.InnerHolder>() {

    inner class InnerHolder(view: View) : RecyclerView.ViewHolder(view) {
        /*初始化控件*/
        val friendName: TextView = view.findViewById(R.id.tv_friendName)
        val friendCall: TextView = view.findViewById(R.id.tv_friendCall)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        /*载布局*/
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_friends_item, parent, false)
        return InnerHolder(view)
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        /*绑定数据*/
        val friends = mData[position]//获取当前条目实例
        //将数据设置到控件上
        holder.friendName.text = friends.name
        holder.friendCall.text = friends.call
    }

    override fun getItemCount(): Int {
        /*返回条目个数*/
        return mData.size
    }
}
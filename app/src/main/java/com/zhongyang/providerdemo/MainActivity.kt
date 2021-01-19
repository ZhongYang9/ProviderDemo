package com.zhongyang.providerdemo

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val tag = "MainActivity"
    private val mContacts = ArrayList<Contacts>()//联系人集合，泛型指定为Contacts，用于存放联系人数据
    private val mAuthority = "content://com.zhongyang.maillist.provider/t_call"
    private var mCallId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*初始化监听*/
        initEvent()
    }

    private fun initEvent() {
        /*添加按钮点击事件*/
        btn_add.setOnClickListener {
            /*解析内容URI*/
            val uri = Uri.parse(mAuthority)
            /*添加数据操作*/
            val values = contentValuesOf("name" to "喜羊羊", "call" to "111")//封装添加数据
            val newUri = contentResolver.insert(uri, values)//调用insert方法，并返回给一个新的uri
            mCallId = newUri?.pathSegments?.get(1)//获取这条新加数据的id，并制成成员变量
            /*提示*/
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show()
        }
        /*删除按钮点击事件*/
        btn_del.setOnClickListener {
            /*删除刚添加的数据*/
            mCallId?.let {
                /*解析内容URI*/
                val uri = Uri.parse("$mAuthority/$it")
                /*删除操作*/
                contentResolver.delete(uri, null, null)
                /*提示*/
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show()
            }
        }
        /*修改按钮点击事件*/
        btn_upd.setOnClickListener {
            /*修改刚添加的数据*/
            mCallId?.let {
                /*解析内容URI*/
                val uri = Uri.parse("$mAuthority/$it")
                /*封装修改数据*/
                val values = contentValuesOf("name" to "小猪佩琪", "call" to "666666")
                /*更新操作*/
                contentResolver.update(uri, values, null, null)
                /*提示*/
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show()
            }
        }
        /*查询按钮点击事件*/
        btn_query.setOnClickListener {
            val uri = Uri.parse(mAuthority)//解析内容URI
            /*查询操作*/
            queryLogic(uri)
            /*初始化适配器相关*/
            initAdapter()
        }
    }

    private fun initAdapter() {
        /*设置布局管理器*/
        rv_contacts.layoutManager = LinearLayoutManager(this)
        /*设置适配器*/
        val contactsAdapter = ContactsAdapter(mContacts)
        rv_contacts.adapter = contactsAdapter
    }

    private fun queryLogic(uri: Uri) {
        /*添加数据前先清空集合*/
        mContacts.clear()
        /*调用查询方法查询数据*/
        contentResolver.query(uri, null, null, null, null)?.apply {
            while (moveToNext()) {
                val name = getString(getColumnIndex("name"))//获取联系人姓名
                val call = getString(getColumnIndex("call"))//获取联系人电话
//                Log.d(tag, "联系人 ==> $name；$call")
                /*添加到集合*/
                val contacts = Contacts(name, call)
                mContacts.add(contacts)
            }
            /*关闭游标*/
            close()
        }
    }
}
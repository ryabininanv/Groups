package com.example.groups.fragment.authorization
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.groups.R
import com.example.groups.activity.group.GroupListActivity
import com.example.groups.models.UrlList
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.CompletableFuture


class AuthorizationFragment: Fragment() {

    //Объявление переменных
    private lateinit var username_input: EditText
    private lateinit var password_input: EditText
    private val client = OkHttpClient()
    private lateinit var authorizationButton: Button

    //Инициализация перменных
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.authorization_fragment, container, false)
            username_input = v.findViewById(R.id.username)
            password_input = v.findViewById(R.id.password)
            authorizationButton = v.findViewById(R.id.authorizationButton)
            authorizationButton.setOnClickListener {
                val callBack = MyCallback()

                //Создание тела запроса
                val body = MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("username", username_input.text.toString())
                    .addFormDataPart("password", password_input.text.toString())
                    .build()

                //Создание запроса
                val request = Request.Builder().url(UrlList.authorization).post(body).build()

                //Отправка запроса
                client.newCall(request).enqueue(callBack)
                //Обработка ответа
                val objectc = JSONObject(callBack.get()?.body()!!.string())
                val statusa: String = objectc.getString("result")
                when (statusa) {
                    "ok" -> {auth()}
                    "invalid" -> {
                        Toast.makeText(context, "Неправильные данные!", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Toast.makeText(context, "Сервис не доступен повторите попытку позже!", Toast.LENGTH_LONG).show()
                    }
                }
        }
        return v
    }

    internal class MyCallback() : CompletableFuture<Response?>(),Callback {
        override fun onFailure(call: Call, e: IOException) {super.completeExceptionally(e) }
        override fun onResponse(call: Call, response: Response) {super.obtrudeValue(response)}
    }

    //Авторизация
    fun auth(){
        val intent = GroupListActivity.newIntent(context)
        context?.startActivity(intent)
    }
}
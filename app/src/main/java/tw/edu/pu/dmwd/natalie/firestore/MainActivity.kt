package tw.edu.pu.dmwd.natalie.firestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var edtName: EditText = findViewById(R.id.edtName)

        var edtWeight:EditText = findViewById(R.id.edtWeight)
        var btnUpdate: Button = findViewById(R.id.btnUpdate)

        btnUpdate.setOnClickListener({

            val user = hashMapOf(

                "名字" to edtName.text.toString(),

                "初生體重" to edtWeight.text.toString().toInt()

            )

            db.collection("users")

                //.add(user)
                .document(edtName.text.toString())

                .set(user)

                .addOnSuccessListener { documentReference ->

                    Toast.makeText(this, "新增/異動資料成功",

                        Toast.LENGTH_LONG).show()

                }

                .addOnFailureListener { e ->

                    Toast.makeText(this, "新增/異動資料失敗：" + e.toString(),

                        Toast.LENGTH_LONG).show()

                }

        })
        var txv: TextView = findViewById(R.id.txv)

        var btnQuery:Button = findViewById(R.id.btnQuery)

        btnQuery.setOnClickListener({

            db.collection("users")

                //.whereEqualTo("名字", edtName.text.toString())
                //.whereLessThan("初生體重", edtWeight.text.toString().toInt())
            .orderBy("初生體重", Query.Direction.DESCENDING)

            .limit(2)

                .get()

                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        var msg: String = ""

                        for (document in task.result!!) {

                            msg += "文件id：" + document.id + ":\n名字：" + document.data["名字"] +

                                    "\n初生體重：" + document.data["初生體重"].toString() + "\n\n"

                        }

                        if (msg != "") {

                            txv.text = msg

                        } else {

                            txv.text = "查無資料"

                        }

                    }

                }

        })

    }
    }

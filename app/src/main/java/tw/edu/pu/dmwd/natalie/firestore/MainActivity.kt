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
        var edtPrice: EditText = findViewById(R.id.edtPrice)
        var edtSize:EditText = findViewById(R.id.edtSize)
        var btnUpdate: Button = findViewById(R.id.btnUpdate)

        btnUpdate.setOnClickListener({

            val drink = hashMapOf(

                "Drink" to edtName.text.toString(),
                "Size" to edtSize.text.toString(),

                "Price" to edtPrice.text.toString().toInt()

            )

            db.collection("drinks")

                //.add(drink)
                .document(edtName.text.toString())

                .set(drink)

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

            db.collection("drinks")

                //.whereEqualTo("Drink", edtName.text.toString())
                //.whereLessThan("Price", edtWeight.text.toString().toInt())
            .orderBy("Price", Query.Direction.DESCENDING)

            .limit(3)

                .get()

                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        var msg: String = ""

                        for (document in task.result!!) {

                            msg += "文件id：" + document.id + ":\n名字：" + document.data["Drink"] +

                                    "\nSize：" + document.data["Size"].toString() +
                                    "\nPrice：" + document.data["Price"].toString()+ "\n\n"

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

package com.anmol.coinpanda

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.anmol.coinpanda.Helper.COL_ID
import com.anmol.coinpanda.Helper.Dbcoinshelper
import com.anmol.coinpanda.Helper.Dbhelper
import com.anmol.coinpanda.Helper.TABLE_NAME
import com.anmol.coinpanda.Model.Sqlcoin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        val dcb = Dbcoinshelper(this)
        val dtb = Dbhelper(this)
        val data = dcb.readData()
        val dataquery = "Select * from $TABLE_NAME ORDER BY $COL_ID DESC"
        val tweetdata = dtb.readData(dataquery)
        if(data.isEmpty()){
            loadcoins(dcb)
        }
        else{
            if(tweetdata.isEmpty()){

            }
            else{

            }
        }
    }

    private fun loadcoins(dcb: Dbcoinshelper) {
        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        db.collection("users").document(auth.currentUser!!.uid).collection("portfolio").get().addOnCompleteListener {
            task ->
            val documentSnapshot = task.result
            val s = documentSnapshot.size()
            if(s!=0){
                for(doc in documentSnapshot){
                    val coinname = doc.getString("coin_name")
                    val coinsymbol = doc.id
                    val coinpage = doc.getString("coinPage")
                    val sqlcoin = Sqlcoin(coinname,coinsymbol,coinpage)
                    dcb.insertData(sqlcoin)
                }
            }

        }
    }


}
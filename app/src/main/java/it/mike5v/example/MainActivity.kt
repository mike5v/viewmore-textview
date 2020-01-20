package it.mike5v.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewMore.text = "sadf sajidajd iosaido ajsio owiasjoijd ioas/nashsakjhsjkahsjkhaskaknjsanjdnjkdnajkndjandjnsajkdnakjnjkdsa" +
                "knjasdnkjndabjndjkasndjasnkdsnaknd" +
                "nkadnsaldakn" +
                "sakdojisjodjsjiaojdosadosaidjsiaojdoasjiosdjoidajijoidasjiodasjioadsjopidasjiodasjio"

        viewMore.setOnClickListener {
            viewMore.toggle()
        }
    }
}
